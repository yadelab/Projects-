package graph;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;

/* See restrictions in Graph.java. */

import java.util.List;

/** The shortest paths through an edge-weighted labeled graph of type GRAPHTYPE.
*  By overrriding methods getWeight, setWeight, getPredecessor, and
*  setPredecessor, the client can determine how to get parameters of the
*  search and to return results.  By overriding estimatedDistance, clients
*  can search for paths to specific destinations using A* search.
*  @author Yadel Abraham
*/
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;

    }
    /** This class overrides the comparator class.*/
    private class OrderGetter implements Comparator<Integer> {
        @Override
        public int compare(Integer obj1, Integer obj2) {
            double h1 = getWeight(obj1) + estimatedDistance(obj1);
            double h2 = getWeight(obj2) + estimatedDistance(obj2);
            if (h1 > h2) {
                return 1;
            }
            if (h1 < h2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /** Initialize the shortest paths.  Must be called before using
    *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        storeFringe = new PriorityQueue<Integer>(_G.vertexSize(),
            new OrderGetter());
        for (Integer v : _G.vertices()) {
            if (v != getSource()) {
                setWeight(v, Double.POSITIVE_INFINITY);
                storeFringe.add(v);
            } else if (v == getSource()) {
                setWeight(v, 0.0);
                storeFringe.add(v);

            }
        }
        while (!storeFringe.isEmpty()) {
            int vtx = storeFringe.remove();
            for (int succ : _G.successors(vtx)) {
                if (getWeight(vtx) + getWeight(vtx, succ) < getWeight(succ)) {
                    setWeight(succ, (getWeight(vtx) + getWeight(vtx, succ)));
                    setPredecessor(succ, vtx);
                    storeFringe.add(succ);
                }
            }
        }
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
    *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);
    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
    *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
    *  V to the destination vertex (if any).  This is assumed to be less
    *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
    *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
    *  at V that represents a shortest path to V.  Invalid if there is a
    *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        pathData = new ArrayList<Integer>();
        while (getPredecessor(v) != 0) {
            pathData.add(v);
            v = getPredecessor(v);
        }
        pathData.add(v);
        Collections.reverse(pathData);
        return pathData;
    }

    /** Returns a list of vertices starting at the source and ending at the
    *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** Adds Data.*/
    protected ArrayList<Integer> pathData;
    /** Main Priority Queue to store shortest path.*/
    protected PriorityQueue<Integer> storeFringe;

}
