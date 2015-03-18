package graph;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class PathTesting {
    class VideoGraphPaths extends SimpleShortestPaths {
        public VideoGraphPaths(LabeledGraph<Double, Double> G,
                               int source, int dest) {
            super(G, source, dest);
            clientGraph = G;
            estm = new HashMap<Integer, Double>();
        }
        @Override
        public double getWeight(int u, int v) {
            if (clientGraph.contains(v)) {
                return clientGraph.getLabel(u, v);
            }
            return Double.POSITIVE_INFINITY;
        }
        @Override
        public double estimatedDistance(int v) {
            return estm.get(v);
        }
        public void estimateSetter(int v, double u) {
            estm.put(v, u);
        }
        HashMap<Integer, Double> estm;
        LabeledGraph<Double, Double> clientGraph;
    }

    @Test
    public void testingPath() {
        DirectedGraph grafh = new DirectedGraph();
        LabeledGraph<Double, Double> lbl;
        lbl = new LabeledGraph<Double, Double>(grafh);
        lbl.add();
        lbl.add();
        lbl.add();
        lbl.add();
        lbl.add();
        lbl.add();
        lbl.add();
        lbl.add();
        lbl.add(2, 3, 6.5);
        lbl.add(4, 2, 12.2);
        lbl.add(4, 5, 11.2);
        lbl.add(5, 6, 30.0);
        lbl.add(5, 3, 9.1);
        lbl.add(1, 8, 10.0);
        lbl.add(6, 7, 100000.0);
        lbl.add(4, 1, 19.9);
        VideoGraphPaths vdo = new VideoGraphPaths(lbl, 4, 3);
        vdo.estimateSetter(2, 4.0);
        vdo.estimateSetter(3, 0.0);
        vdo.estimateSetter(4, 102.0);
        vdo.estimateSetter(5, 5.1);
        vdo.estimateSetter(7, 8.0);
        vdo.estimateSetter(1, 9999.99);
        vdo.estimateSetter(6, 40.0);
        vdo.estimateSetter(8, 10.0);
        vdo.setPaths();
        vdo.setWeight(4, 0.0);
        vdo.setWeight(2, 12.2);
        vdo.setWeight(5, 11.2);
        vdo.setWeight(6, 41.2);
        vdo.setWeight(3, 18.7);
        assertEquals(9.1, vdo.getWeight(5, 3), 0.0);
        assertEquals(30.0, vdo.getWeight(5, 6), 0.0);
        assertEquals(4, vdo.getPredecessor(2), 0.1);
        assertEquals(1, vdo.getPredecessor(8));
        List<Integer> order = vdo.pathTo(3);
        assertEquals(4, order.get(0), 0.0);
        assertEquals(2, order.get(1), 0.0);
        assertEquals(3, order.get(2), 0.0);
        assertTrue(!order.contains(5));
        assertTrue(!order.contains(6));
    }
}
