import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public final class AdjacencyMatrix {

    private final Node[] nodes;

    public AdjacencyMatrix(int countOfNodes, boolean[][] matrix) {
        this.nodes = buildNodeSet(countOfNodes, matrix);
    }

    public void checkForBipartite() throws BipartiteGraphException {
        for (Node node : nodes) {
            List<NodeChain> chains = new ArrayList<>();
            chains.add(new NodeChain(node));

            while (true) {
                List<NodeChain> newChains = new ArrayList<>();

                for (NodeChain chain : chains) {
                    newChains.addAll(chain.walkAround());
                }

                if (newChains.isEmpty())
                    break;

                chains = newChains;
            }
        }
    }

    public SortedMap<Byte, NodeColorPart> findColorParts() {
        SortedMap<Byte, NodeColorPart> colorParts = new TreeMap<>(Byte::compare);

        for (Node node : nodes) {
            byte color = node.getColor();

            NodeColorPart part = colorParts.get(color);
            if (part != null) {
                part.getNodes().add(node);
            } else {
                part = new NodeColorPart(node);
                colorParts.put(color, part);
            }
        }

        return colorParts;
    }

    private Node[] buildNodeSet(int countOfNodes, boolean[][] matrix) {
        Node[] nodes = new Node[countOfNodes];
        for (int i = 0; i < countOfNodes; i++)
            nodes[i] = new Node(i + 1);

        for (int i = 1; i < countOfNodes; i++) {
            Node thisNode = nodes[i];
            for (int j = 0; j < i; j++) {
                if (matrix[i][j]) {
                    thisNode.linkWith(nodes[j]);
                    nodes[j].linkWith(thisNode);
                }
            }
        }

        return nodes;
    }

}
