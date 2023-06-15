import static java.lang.String.format;

public final class BipartiteCheckFailedException extends Exception {

    private final NodeChain chain;

    public BipartiteCheckFailedException(NodeChain chain) {
        super(format("Found odd chain: '%s'", chain));
        this.chain = chain;
    }

    public NodeChain getChain() {
        return chain;
    }

}
