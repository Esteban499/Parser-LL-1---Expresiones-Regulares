import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Style;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.*;

public class GraphvizTreeBuilder {
    private static int nodeId = 0;
    private static List<String> terminalSymbols;

    public static void generateImage(TreeNode root, String outputFilePath, List<String> terminals) throws IOException {
        terminalSymbols = terminals;
        nodeId = 0; // reset para cada ejecución
        Graph g = graph("tree").directed().with(buildNodes(root));
        Graphviz.fromGraph(g).width(800).render(Format.PNG).toFile(new File(outputFilePath));
        System.out.println("Árbol exportado a: " + outputFilePath);
    }

    private static Node buildNodes(TreeNode root) {
        String id = "n" + (nodeId++);
        Node graphNode;

        if (terminalSymbols.contains(root.getLabel())) {
            // Resaltar terminal real
            graphNode = node(id).with(
                    Label.of(root.getLabel()),
                    Color.BLACK,
                    Style.FILLED,
                    Color.rgb("ADD8E6").fill() // celeste
            );
        } else {
            graphNode = node(id).with(Label.of(root.getLabel()));
        }

        for (TreeNode child : root.getChildren()) {
            graphNode = graphNode.link(buildNodes(child));
        }

        return graphNode;
    }
}
