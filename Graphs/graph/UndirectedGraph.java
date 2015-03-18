package graph;
import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *
 *  @author YadelAbraham
 */
public class UndirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return false;
    }
    @Override
    public boolean contains(int u, int v) {
        if (myGraph.containsKey(u) && myGraph.containsKey(v)) {
            ArrayList<Integer> arr = myGraph.get(u);
            if (arr.contains(v)) {
                return true;
            }
            ArrayList<Integer> arr2 = myGraph.get(v);
            if (arr2.contains(u)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public int inDegree(int v) {
        if (!contains(v)) {
            return 0;
        }
        ArrayList<int[]> isPresent = new ArrayList<int[]>();
        System.out.println(storeEdge.size());
        for (int[] x: storeEdge) {
            if (x[0] == v) {
                isPresent.add(x);
            }
        }
        return isPresent.size();

    }

    @Override
    public int predecessor(int v, int k) {
        if (contains(v)) {
            ArrayList<Integer> isPred = new ArrayList<Integer>();
            for (int[] x: storeEdge) {
                if (x[1] == v) {
                    isPred.add(x[0]);
                }
                if (x[0] == v) {
                    isPred.add(x[1]);
                }
            }
            if (isPred.size() > k) {
                return isPred.get(k);
            }
        }
        return 0;
    }
    @Override
    public int successor(int v, int k) {
        return predecessor(v, k);
    }
    @Override
    public Iteration<Integer> successors(int v) {
        return predecessors(v);
    }
    @Override
    public Iteration<Integer> predecessors(int v) {
        if (!contains(v)) {
            ArrayList<Integer> itr = new ArrayList<Integer>();
            return Iteration.iteration(itr);
        }
        ArrayList<Integer> generalArray = new ArrayList<Integer>();
        for (int[] x: storeEdge) {
            int paul = x[1];
            int paul1 = x[0];
            if (paul == v) {
                generalArray.add(x[0]);
            } else if (paul1 == v) {
                generalArray.add(x[1]);
            }
        }
        return Iteration.iteration(generalArray);
    }


}
