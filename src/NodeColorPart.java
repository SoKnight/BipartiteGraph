import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class NodeColorPart {

    private final List<Node> nodes;

    public NodeColorPart(Node node) {
        this.nodes = new ArrayList<>();
        this.nodes.add(node);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return nodes.stream().map(Node::getId).map(String::valueOf).collect(Collectors.joining(" "));
    }

}
