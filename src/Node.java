import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class Node {

    private final int id;
    private final Set<Node> linkedNodes;
    private byte color;

    public Node(int id) {
        this.id = id;
        this.linkedNodes = new LinkedHashSet<>();
        this.color = 0;
    }

    public int getId() {
        return id;
    }

    public Set<Node> getLinkedNodes() {
        return linkedNodes;
    }

    public byte getColor() {
        return color;
    }

    public void linkWith(Node other) {
        linkedNodes.add(other);
    }

    public boolean hasColor() {
        return color != 0;
    }

    public boolean hasColor(byte color) {
        return hasColor() && this.color == color;
    }

    public void color() {
        byte color = 1;

        colorSelection:
        while (true) {
            for (Node linkedNode : linkedNodes) {
                if (linkedNode.hasColor(color)) {
                    color++;
                    continue colorSelection;
                }
            }

            break;
        }

        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;
        return id == node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", linkedNodes=[" + linkedNodes.stream().map(Node::getId).map(String::valueOf).collect(Collectors.joining(", ")) + ']' +
                ", color=" + color +
                '}';
    }

}
