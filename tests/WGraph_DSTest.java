package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    weighted_graph getgraph(){
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);

        return g;

    }




    @Test
    void getNode()
    {
        weighted_graph g = getgraph();

        assertEquals(g.getNode(0).getKey(),0);
        assertEquals(g.getNode(4).getInfo(),"");
        assertNotEquals(g.getNode(0).getInfo(),"helo");

    }

    @Test
    void hasEdge() {
        weighted_graph g =  getgraph();

                assertTrue(g.hasEdge(0,1));
                assertTrue(g.hasEdge(1,0));
        assertTrue(g.hasEdge(0,2));
        assertTrue(g.hasEdge(2,0));
        assertTrue(g.hasEdge(0,3));
        assertTrue(g.hasEdge(3,0));
        assertFalse(g.hasEdge(2,1));
        assertFalse(g.hasEdge(1,2));

    }

    @Test
    void getEdge()
    {
        weighted_graph g = getgraph();

    assertEquals(g.getEdge(0,1),1);
        assertNotEquals( g.getEdge(0,1),-1);

    }

    @Test
    void addNode() {
        weighted_graph g = getgraph();

        g.addNode(12);
        assertEquals(12,g.getNode(12).getKey());
        g.addNode(20);
        assertEquals(20,20);
    }

    @Test
    void connect() {
        weighted_graph g = getgraph();
        g.connect(1,2,2);
        assertTrue(g.hasEdge(1,2));

    }

    @Test
    void getV() {
        weighted_graph g = getgraph();
        Collection<node_info> v = g.getV();
        Iterator<node_info> iter = v.iterator();
        while (iter.hasNext()) {
            node_info n = iter.next();
            assertNotNull(n);
        }



    }

    @Test
    void testGetV() {
        weighted_graph g = getgraph();

        Collection<node_info> v = g.getV(0);
        Iterator<node_info> iter = v.iterator();
        while (iter.hasNext()) {
            node_info n = iter.next();
            assertNotNull(n);

        }
        Collection<node_info> y = g.getV(1);
        Iterator<node_info> itera = v.iterator();
        while (itera.hasNext()) {
            node_info n = itera.next();
            assertNotNull(n);
        }
    }

    @Test
    void removeNode() {
    weighted_graph g = getgraph();
    g.removeNode(1);
        assertFalse(g.hasEdge(0,1));
    }

    @Test
    void removeEdge() {
        weighted_graph g = getgraph();
        g.removeEdge(0,1);
        assertFalse(g.hasEdge(0,1));
        g.removeEdge(2,3);
        assertFalse(g.hasEdge(2,3));
    }

    @Test
    void nodeSize() {
        weighted_graph g = getgraph();
        assertEquals(4,g.nodeSize());
        g.addNode(4);
        assertEquals(5,g.nodeSize());
    }

    @Test
    void edgeSize() {
        weighted_graph g = getgraph() ;
        assertEquals(3,g.edgeSize());
        g.connect(2,3,5);
        assertEquals(4,g.edgeSize());
    }

    @Test
    void getMC() {
        weighted_graph g = getgraph();
        assertEquals(7,g.getMC());
        g.addNode(5);
        g.connect(1,3,4);
        assertEquals(9,g.getMC());

    }
}