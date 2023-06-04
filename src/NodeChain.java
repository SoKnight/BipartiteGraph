import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class NodeChain {

    private final List<Node> chainedNodes;

    public NodeChain(Node head) {
        this.chainedNodes = new ArrayList<>();
        this.chainedNodes.add(head);
    }

    private NodeChain(List<Node> chain) {
        this.chainedNodes = new ArrayList<>(chain);
    }

    public NodeChain duplicate() {
        return new NodeChain(chainedNodes);
    }

    public NodeChain chainFrom(Node node) {
        int nodeIndex = chainedNodes.indexOf(node);
        return nodeIndex > 0 ? new NodeChain(chainedNodes.subList(nodeIndex, chainedNodes.size())) : duplicate();
    }

    public int length() {
        return chainedNodes.size();
    }

    public List<NodeChain> walkAround() throws BipartiteGraphException {
        List<NodeChain> nextChains = new ArrayList<>();

        Node lastNode = chainedNodes.get(chainedNodes.size() - 1);
        Node prevNode = chainedNodes.size() > 1 ? chainedNodes.get(chainedNodes.size() - 2) : null;

        lastNode.color();

        for (Node linkedNode : lastNode.getLinkedNodes()) {
            if (linkedNode == prevNode)
                continue;

            if (chainedNodes.contains(linkedNode)) {
                NodeChain subChain = chainFrom(linkedNode);
                if (subChain.length() % 2 != 0) {
                    throw new BipartiteGraphException(subChain);
                }
            } else {
                NodeChain newChain = duplicate();
                newChain.chainedNodes.add(linkedNode);
                nextChains.add(newChain);
            }
        }

        return nextChains;
    }

    @Override
    public String toString() {
        return chainedNodes.stream()
                .map(Node::getId)
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
    }

}
