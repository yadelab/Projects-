
package graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Collection;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
*  directed and undirected graphs.
*
*  @author Yadel Abraham
*/
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        myGraph = new HashMap<Integer, ArrayList<Integer>>();

    }

    @Override
    public int vertexSize() {
        return myGraph.keySet().size();
    }

    @Override
    public int maxVertex() {
        if (!myGraph.isEmpty()) {
            int rachy = Integer.MIN_VALUE;
            for (int i : myGraph.keySet()) {
                rachy = Math.max(i, rachy);
            }
            return rachy;
        } else {
            return 0;
        }
    }

    @Override
    public int edgeSize() {
        Collection<ArrayList<Integer>> lst = myGraph.values();
        int count = 0;
        for (ArrayList<Integer> num : lst) {
            for (int cc : num) {
                count++;
            }
        }
        if (isDirected()) {
             return count;
        } else {
            return count/ 2;
        }

       
    }
    @Override
    public abstract boolean isDirected();
    @Override
    public int outDegree(int v) {
        if (!(myGraph.containsKey(v))) {
            return 0;
        }
        return myGraph.get(v).size();
    }
    @Override
    public abstract int inDegree(int v);
    @Override
    public boolean contains(int u) {

        return myGraph.containsKey(u);
    }
    @Override
    public boolean contains(int u, int v) {
        if (myGraph.containsKey(u) && myGraph.containsKey(v)) {
            ArrayList<Integer> arr = myGraph.get(u);
            if (arr.contains(v)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int add() {
        numVertices = numVertices + 1;
        HashSet<Integer> v = new HashSet<Integer>(myGraph.keySet());
        for (int i = 1; i < numVertices; i++) {
            if (!v.contains(i)) {
                myGraph.put(i, new ArrayList<Integer>());
                return i;
            }
        }

        return numVertices;
    }
    @Override
    public int add(int u, int v) {
        int[] zArr = new int[2];
        zArr[0] = u;
        zArr[1] = v;

        if (contains(u, v)) {
            return u;
        } else {
            if (contains(u)) {
                ArrayList<Integer> i = myGraph.get(u);
                i.add(v);
                storeEdge.add(zArr);
                return u;
            }
        }
        return u;
    }

    @Override
    public void remove(int v) {
        myGraph.remove(v);
        HashSet<Integer> w = new HashSet<Integer>(myGraph.keySet());
        for (int i : w) {
            ArrayList<Integer> edge = myGraph.get(i);
            for (int pos = 0; pos < edge.size(); pos++) {
                if (edge.contains(v)) {
                    int m = edge.indexOf(v);
                    edge.remove(m);
                }
            }
        }
        ArrayList<int[]> cop = new ArrayList<int[]>(storeEdge);
        for (int[]edg : cop) {
            if (edg[0] == v || edg[1] == v) {
                int n = storeEdge.indexOf(edg);
                storeEdge.remove(n);
            }
        }

    }

    @Override
    public void remove(int u, int v) {
        if (contains(u, v)) {
            ArrayList<Integer> r = myGraph.get(u);
            ArrayList<Integer> copy  = new ArrayList<Integer>(r);
            for (int num : copy) {
                if (num == v) {
                    int theI = copy.indexOf(num);
                    r.remove(theI);
                }
            }
            ArrayList<int[]> cop = new ArrayList<int[]>(storeEdge);
            for (int[]edg : cop) {
                int josh = edg[0];
                int josh2 = edg[1];
                if (josh == u && josh2 == v) {
                    int n = storeEdge.indexOf(edg);
                    storeEdge.remove(n);
                }
            }

        }
    }

    @Override
    public Iteration<Integer> vertices() {
        TreeSet<Integer> ts = new TreeSet<Integer>(myGraph.keySet());
        return Iteration.iteration(ts);
    }

    @Override

    public int successor(int v, int k) {

        if (!(contains(v))) {
            return 0;
        }
        if (k >= myGraph.get(v).size()) {
            return 0;
        } else {
            return myGraph.get(v).get(k);
        }

    }

    @Override
    public abstract int predecessor(int v, int k);

    @Override
    public Iteration<Integer> successors(int v) {
        if (!contains(v)) {
            ArrayList<Integer> itr = new ArrayList<Integer>();
            return Iteration.iteration(itr);
        }
        ArrayList<Integer> myLis = myGraph.get(v);
        return Iteration.iteration(myLis);
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        return Iteration.iteration(storeEdge);
    }

    @Override
    protected boolean mine(int v) {
        return this.contains(v);
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!mine(v)) {
            throw new IllegalArgumentException("vertex is not a member!");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        if (this.contains(u, v)) {
            if (isDirected()) {
                return  ((u + v) * (u + v + 1)) / 2  + v;
            }
            int m1 = Math.min(u, v);
            int m2 = Math.max(u, v);
            return ((m1 + m2) * (m1 + m2 + 1)) / 2 + m2;
        }
        return 0;
    }

    /** The main datastructure to represent my graph. */
    protected HashMap<Integer, ArrayList<Integer>> myGraph;
    /** This is the  number of vertices on the graph.*/
    private int  numVertices = 1;
    /** This array stores the edges as they are being added.*/
    protected ArrayList<int[]> storeEdge = new ArrayList<int[]>();



}
