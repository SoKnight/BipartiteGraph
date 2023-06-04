import static java.lang.String.format;

public final class BipartiteGraphException extends Exception {

    private final NodeChain chain;

    public BipartiteGraphException(NodeChain chain) {
        super(format("Found odd chain: '%s'", chain));
        this.chain = chain;
    }

    public NodeChain getChain() {
        return chain;
    }

}
