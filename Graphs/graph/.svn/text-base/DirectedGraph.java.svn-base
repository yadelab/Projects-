package graph;
import java.util.ArrayList;
/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
*  positive integers. Graphs may have self edges.
*
*  @author Yadel Abraham
*/
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        int counter = 0;
        if (!(myGraph.containsKey(v))) {
            return 0;
        }
        for (int hs : myGraph.keySet()) {
            if (myGraph.get(hs) != null && myGraph.get(hs).contains(v)) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public int predecessor(int v, int k) {
        if (contains(v)) {
            ArrayList<int[]> isPred2 = new ArrayList<int[]>();
            for (int[] x: storeEdge) {
                if (x[1] == v) {
                    isPred2.add(x);
                }
            }
            if (isPred2.size() > k) {
                return isPred2.get(k)[0];
            }
        }
        return 0;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {

        if (!contains(v)) {
            ArrayList<Integer> itr = new ArrayList<Integer>();
            return Iteration.iteration(itr);
        }
        ArrayList<Integer> generalArray = new ArrayList<Integer>();
        for (int[] x: storeEdge) {
            if (x[1] == v) {
                generalArray.add(x[0]);
            }
        }


        return Iteration.iteration(generalArray);

    }





}
