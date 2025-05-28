import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Style;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.*;

public class GraphvizTreeBuilder {
    private static int nodeId = 0;
    private static List<String> terminalSymbols;

    /**
     * Genera una imagen del árbol sintáctico simplificado.
     * La raíz 'E' siempre se dibuja, y las producciones unitarias intermedias se colapsan.
     *
     * @param root El nodo raíz del árbol sintáctico.
     * @param outputFilePath La ruta donde se guardará la imagen PNG.
     * @param terminals Una lista de símbolos terminales definidos en la gramática.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public static void generateImage(TreeNode root, String outputFilePath, List<String> terminals) throws IOException {
        terminalSymbols = terminals;
        nodeId = 0; // Reinicia el contador de IDs para cada ejecución

        // Crear el nodo Graphviz para la raíz 'E'. La raíz siempre se dibuja.
        // Se le asigna un ID y la etiqueta correspondiente.
        String rootGraphvizId = "n" + (nodeId++);
        Node rootGraphvizNode = node(rootGraphvizId).with(Label.of(root.getLabel()));

        // Recopilar los nodos hijos efectivos para la raíz, aplicando el colapso.
        List<Node> effectiveChildrenForRoot = new ArrayList<>();
        for (TreeNode child : root.getChildren()) {
            Node childGraphNode = buildFilteredTreeInternal(child); // Llamada recursiva para los hijos
            if (childGraphNode != null) {
                effectiveChildrenForRoot.add(childGraphNode);
            }
        }

        // Si la raíz no tiene hijos efectivos (por ejemplo, el árbol está vacío o no lleva a terminales),
        // podría no haber nada que dibujar más allá de la raíz misma.
        if (effectiveChildrenForRoot.isEmpty()) {
            System.out.println("No se encontraron nodos terminales para representar desde la raíz.");
            // Si la raíz no tiene hijos efectivos, dibujamos solo la raíz si es necesario.
            // Para este caso, si la raíz no conecta a nada, la omitimos como en el comportamiento original.
            return;
        }

        // Conectar la raíz a sus hijos efectivos.
        for (Node childNode : effectiveChildrenForRoot) {
            rootGraphvizNode = rootGraphvizNode.link(childNode);
        }

        // Construir el grafo y renderizar la imagen.
        Graph g = graph("tree").directed().with(rootGraphvizNode);
        Graphviz.fromGraph(g).width(800).render(Format.PNG).toFile(new File(outputFilePath));
        System.out.println("Árbol exportado a: " + outputFilePath);
    }

    /**
     * Construye recursivamente los nodos de Graphviz, colapsando las producciones unitarias
     * (cadenas de nodos no terminales con un solo hijo efectivo).
     *
     * @param node El nodo del árbol sintáctico actual que se está procesando.
     * @return El nodo de Graphviz efectivo que representa este subárbol (puede ser un nodo colapsado, un terminal o un nodo de bifurcación), o null si no hay terminales debajo.
     */
    private static Node buildFilteredTreeInternal(TreeNode node) {
        // Paso 1: Si es un símbolo terminal, siempre se dibuja. Se le asigna un color celeste.
        boolean isTerminal = terminalSymbols.contains(node.getLabel());
        if (isTerminal) {
            String id = "n" + (nodeId++);
            return node(id).with(
                    Label.of(node.getLabel()),
                    Color.BLACK,
                    Style.FILLED,
                    Color.rgb("ADD8E6").fill() // celeste
            );
        }

        // Paso 2: Si es un nodo no terminal, procesa recursivamente a sus hijos
        // para obtener sus representaciones efectivas en Graphviz.
        List<Node> effectiveChildGraphNodes = new ArrayList<>();
        for (TreeNode child : node.getChildren()) {
            Node childGraphNode = buildFilteredTreeInternal(child); // Llamada recursiva
            if (childGraphNode != null) {
                effectiveChildGraphNodes.add(childGraphNode);
            }
        }

        // Paso 3: Si este nodo no terminal no tiene hijos efectivos (es decir, su subárbol
        // no lleva a ningún terminal), entonces este nodo no debe ser dibujado.
        if (effectiveChildGraphNodes.isEmpty()) {
            return null;
        }

        // Paso 4: Decisión para dibujar o saltar el nodo actual (`node`).
        // Si el nodo actual es un no terminal y tiene *exactamente un hijo efectivo*,
        // entonces este nodo es parte de una producción unitaria y puede ser colapsado.
        // En este caso, simplemente retornamos el nodo Graphviz efectivo de su único hijo,
        // saltando el nodo actual.
        if (effectiveChildGraphNodes.size() == 1) {
            return effectiveChildGraphNodes.get(0); // Colapsa el nodo actual, retornando el hijo efectivo
        }

        // Paso 5: Si llegamos aquí, significa que el nodo actual debe ser dibujado.
        // Esto ocurre si:
        //   - Es un nodo no terminal con MÚLTIPLES hijos efectivos (es un punto de bifurcación en el árbol simplificado).
        //   - (La raíz se maneja explícitamente en `generateImage`).

        // Crea el nodo Graphviz para el nodo actual.
        String id = "n" + (nodeId++);
        Node graphNode = node(id).with(Label.of(node.getLabel()));

        // Conecta el nodo actual con sus hijos efectivos.
        for (Node childNode : effectiveChildGraphNodes) {
            graphNode = graphNode.link(childNode);
        }

        return graphNode;
    }
}