package graph;
import java.util.ArrayDeque;
import java.util.Collections;


/* See restrictions in Graph.java. */

/** Implements a depth-first traversal of a graph.  Generally, the
*  client will extend this class, overriding the visit and
*  postVisit methods, as desired (by default, they do nothing).
*  @author Yadel Abraham
*/
public class DepthFirstTraversal extends Traversal {

    /** A depth-first Traversal of G, using FRINGE as the fringe. */
    protected DepthFirstTraversal(Graph G) {
        super(G, Collections.asLifoQueue(new ArrayDeque<Integer>()));
    }

    @Override
    protected boolean visit(int v) {
        return super.visit(v);
    }

    @Override
    protected boolean postVisit(int v) {
        return super.postVisit(v);
    }

}
