package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Yadel Abraham
 */
public class GraphTesting {


    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());

    }

    @Test
    public void testWeirdSizeShit() {
        UndirectedGraph g = new UndirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.remove(10);
        assertEquals(9, g.vertexSize());
        assertEquals(9, g.maxVertex());
        assertEquals(0, g.edgeSize());
        g.remove(8);
        assertEquals(8, g.vertexSize());
        g.add();
        assertEquals(9, g.maxVertex());
        assertEquals(9, g.vertexSize());
        g.remove(9);
        g.remove(8);
        g.remove(7);
        g.remove(6);
        g.remove(5);
        assertEquals(4, g.vertexSize());
        g.add(1, 2);
        g.add(2, 3);
        g.add(3, 4);
        g.add(4, 1);
        assertEquals(4, g.edgeSize());
        g.add(1, 1);
        assertEquals(5, g.edgeSize());
        g.remove(1);
        assertEquals(3, g.vertexSize());
        assertEquals(2, g.edgeSize());
    }


    @Test
    public void fundamentalTest1() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has max vertex", 0, g.maxVertex());
        DirectedGraph p = new DirectedGraph();
        p.add();
        p.add();
        p.add();
        p.add(1, 3);
        assertEquals("Max vertex is ", 3 , p.maxVertex());
        assertEquals(1, p.edgeSize());
        assertEquals(1, p.outDegree(1));
        g.add();
        g.add();
        g.add();
        g.add(2, 1);
        g.add(1, 3);
        assertEquals(true, p.contains(2));
        assertEquals(0, g.successor(3, 5));
        assertEquals(3, g.successor(1, 0));
    }
    @Test
    public void fundamentalTest2() {
        DirectedGraph p = new DirectedGraph();
        p.add();
        p.add();
        p.add();
        p.remove(3);
        assertEquals(2, p.maxVertex());
    }
    @Test
    public void fundamentalTest3() {
        DirectedGraph p = new DirectedGraph();
        p.add();
        p.add();
        p.add();
        p.add(1, 3);
        p.add(3, 2);
        p.remove(1, 3);
        assertEquals(1, p.edgeSize());
    }
    @Test
    public void fundamentalTest4() {
        DirectedGraph p = new DirectedGraph();
        p.add();
        p.add();
        p.add();
        p.add();
        p.add(2, 1);
        p.add(3, 1);
        p.add(4, 1);
        assertEquals(2, p.predecessor(1, 0));
        assertEquals(3, p.predecessor(1, 1));
        assertEquals(4, p.predecessor(1, 2));
        assertEquals(0, p.predecessor(4, 7));
    }
    @Test
    public void undirectedTest() {
        UndirectedGraph p = new UndirectedGraph();
        p.add();
        p.add();
        p.add();
        p.add(2, 1);
        p.add(3, 1);
        assertEquals(2, p.predecessor(1, 0));
    }


}
