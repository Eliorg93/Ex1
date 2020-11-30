package ex1.tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {
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
    void init() {
        weighted_graph g = getgraph() ;
        WGraph_Algo t = new WGraph_Algo();
        t.init(g);
        assertEquals(t.getGraph().nodeSize(),4);

    }

    @Test
    void getGraph() {

        weighted_graph g = getgraph() ;
        WGraph_Algo t = new WGraph_Algo();
        t.init(g);
        assertEquals(t.getGraph().edgeSize(),3);
    }

    @Test
    void copy() {
        weighted_graph g = getgraph() ;
         WGraph_Algo d = new WGraph_Algo() ;
          d.init(g);
          WGraph_DS g2 =(WGraph_DS)(d.copy());
          assertEquals(g2.edgeSize(),g.edgeSize());
          assertEquals(g2.nodeSize(),g.nodeSize());




    }



    @Test
    void isConnected() {
        weighted_graph b = new WGraph_DS();
        WGraph_Algo c = new WGraph_Algo() ;
        c.init(b);

        b.addNode(0);
        b.addNode(1);
        b.addNode(2);
        b.addNode(3);
        b.connect(0,1,1);
        b.connect(0,2,2);
        b.connect(0,3,3);
        b.connect(1,0,1);
        b.connect(1,2,1);
        b.connect(1,3,1);
        b.connect(2,1,1);
        b.connect(2,0,1);
        b.connect(2,3,1);
        b.connect(3,0,1);
        b.connect(3,2,1);
        b.connect(3,1,1);
        assertTrue(c.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g = getgraph();
        WGraph_Algo b = new WGraph_Algo() ;
        b.init(g);
        assertEquals(b.shortestPathDist(0,2),2);
        g.addNode(4);
        g.connect(0,4,2);
        assertEquals(b.shortestPathDist(0,4),2);


    }

    @Test
    void shortestPath() {
        weighted_graph g = getgraph();
        WGraph_Algo b = new WGraph_Algo() ;
        b.init(g);
        Iterator<node_info> iter = b.shortestPath(0,2).iterator();
        assertEquals(iter.next().getKey(),0);
        assertEquals(iter.next().getKey(),2);
    }

    @Test
    void save() {
        weighted_graph g = getgraph() ;
         WGraph_Algo d = new WGraph_Algo() ;
         d.init(g);
         assertTrue(d.save("Elior"));
        WGraph_Algo d2 = new WGraph_Algo();
        d2.load("Elior");
        assertEquals(d.getGraph().nodeSize(),d2.getGraph().nodeSize());
    }

    @Test
    void load() {
        weighted_graph g = getgraph() ;
        WGraph_Algo d = new WGraph_Algo() ;
        d.init(g);
        assertTrue(d.save("Elior"));
        WGraph_Algo d2 = new WGraph_Algo();
        d2.load("Elior");
        assertEquals(d.getGraph().nodeSize(),d2.getGraph().nodeSize());

    }
}