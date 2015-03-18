package graph;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Traversal class.
*  checks if depthFirst and BreadthFirst work appropriately
*  @author Yadel Abraham
*/
public class TraversalTesting {


    class Bds extends BreadthFirstTraversal {
        protected ArrayList<Integer> checker = new ArrayList<Integer>();

        protected Bds(Graph G) {
            super(G);

        }

        @Override
        protected boolean visit(int v) {
            checker.add(v);
            return super.visit(v);
        }
        protected ArrayList<Integer> getChecker() {
            return checker;
        }


    }
    class Dps extends DepthFirstTraversal {
        ArrayList<Integer> ckr = new ArrayList<Integer>();
        ArrayList<Integer> postCkr = new ArrayList<Integer>();
        protected Dps(Graph G) {
            super(G);
        }
        @Override
        protected boolean visit(int v) {
            ckr.add(v);
            return super.visit(v);
        }
        @Override
        protected boolean postVisit(int v) {
            postCkr.add(v);
            return super.postVisit(v);
        }
        protected ArrayList<Integer> getCkr() {
            return ckr;
        }
        protected ArrayList<Integer> getPostCkr() {
            return postCkr;
        }


    }

    @Test
    public void traversalTest() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(5, 4);
        g.add(5, 3);
        g.add(4, 1);
        g.add(3, 2);
        g.add(1, 5);
        Bds one = new Bds(g);
        List<Integer> oneThroughtFive = asList(5, 4, 3, 1, 2);
        one.traverse(5);
        assertEquals(oneThroughtFive, one.getChecker());

        DirectedGraph p = new DirectedGraph();
        p.add();
        p.add();
        p.add();
        p.add();
        p.add();
        p.add(5, 4);
        p.add(5, 3);
        p.add(4, 1);
        p.add(3, 2);
        p.add(1, 5);
        Dps two = new Dps(p);
        List<Integer> otf = asList(5, 4, 1, 3, 2);
        List<Integer> fff = asList(5, 3, 2, 4, 1);
        List<Integer> wtf = asList(1, 4, 2, 3, 5);
        two.traverse(5);
        assertTrue(otf.equals(two.getCkr()) || fff.equals(two.getCkr()));
        assertEquals(wtf, two.getPostCkr());



    }
}
