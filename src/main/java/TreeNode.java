import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private final String label;
    private final List<TreeNode> children;

    public TreeNode(String label) {
        this.label = label;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public String getLabel() {
        return label;
    }

    public List<TreeNode> getChildren() {
        return children;
    }
}
