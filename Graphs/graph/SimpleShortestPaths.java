package graph;
import java.util.HashMap;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Yadel Abraham
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
    }

    @Override
    public double getWeight(int v) {
        if (weightGetter.containsKey(v)) {
            return weightGetter.get(v);
        }
        return Double.POSITIVE_INFINITY;
    }

    @Override
    protected void setWeight(int v, double w) {
        weightGetter.put(v, w);
    }
    @Override
    public int getPredecessor(int v) {
        if (!predKeeper.containsKey(v) || predKeeper.get(v) == null) {
            return 0;
        }
        return predKeeper.get(v);
    }

    @Override
    protected void setPredecessor(int v, int u) {
        predKeeper.put(v, u);
    }
    /** A Separate Map to store Weight. */
    private HashMap<Integer, Double> weightGetter
        = new HashMap<Integer, Double>();
    /** Stores my Predecessor. */
    private HashMap<Integer, Integer> predKeeper
        = new HashMap<Integer, Integer>();
}
