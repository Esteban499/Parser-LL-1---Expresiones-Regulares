import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import static guru.nidi.graphviz.model.Factory.*;

import java.io.File;
import java.io.IOException;

public class GraphvizTreeBuilder {
    private static int nodeId = 0;

    public static void generateImage(TreeNode root, String outputFilePath) throws IOException {
        Graph g = graph("tree").directed().with(buildNodes(root));
        Graphviz.fromGraph(g).width(600).render(Format.PNG).toFile(new File(outputFilePath));
        System.out.println("Árbol exportado a: " + outputFilePath);
    }

    private static guru.nidi.graphviz.model.Node buildNodes(TreeNode root) {
        String id = "n" + (nodeId++);
        guru.nidi.graphviz.model.Node graphNode = node(id).with(guru.nidi.graphviz.attribute.Label.of(root.getLabel()));
        for (TreeNode child : root.getChildren()) {
            graphNode = graphNode.link(buildNodes(child));
        }
        return graphNode;
    }
}
