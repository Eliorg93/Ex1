package ex1.src;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class WGraph_Algo implements weighted_graph_algorithms {

    weighted_graph graph;

    private HashMap<Integer, Double> dist;
    private HashMap<Integer, Integer> prev;

    @Override
    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    public void init(weighted_graph g) {
        this.graph = g;
        this.dist = new HashMap<>();
        this.prev = new HashMap<>();
    }

    @Override
    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    public weighted_graph getGraph() {
        return graph;
    }

    @Override
    /**
     * Compute a deep copy of this weighted graph.
     * @return
     */
    public weighted_graph copy() {

        weighted_graph new_graph = new WGraph_DS();

        for (node_info node : this.graph.getV()) {

            new_graph.addNode(node.getKey());

            new_graph.getNode(node.getKey()).setInfo(node.getInfo());
            new_graph.getNode(node.getKey()).setTag(node.getTag());

        }

        for (node_info node : this.graph.getV()) {

            for (node_info neighbor : this.graph.getV(node.getKey())) {

                new_graph.connect(node.getKey(), neighbor.getKey(), this.graph.getEdge(node.getKey(), neighbor.getKey()));

            }

        }

        return new_graph;
    }

    /**
     * implement djikstra algorithm to find the shortest path(by weight)
     * @param source
     */
    public void djikstra(int source) {

        PriorityQueue<node_info> q = new PriorityQueue<>();

        for (node_info vertex : this.graph.getV()) {

            if (vertex.getKey() != source) {

                vertex.setTag(Double.MAX_VALUE);
                this.dist.put(vertex.getKey(), Double.MAX_VALUE);
                this.prev.put(vertex.getKey(), null);
            }
            else {
                this.dist.put(vertex.getKey(), 0.0);
                vertex.setTag(0.0);
            }

            q.add(vertex);

        }

        while (!q.isEmpty()) {

            node_info u = q.poll();

            for (node_info v : this.graph.getV(u.getKey())) {

                double distance = this.dist.get(u.getKey()) + this.graph.getEdge(u.getKey(), v.getKey());

                if (distance < this.dist.get(v.getKey())) {

                    this.dist.put(v.getKey(), distance);
                    this.prev.put(v.getKey(), u.getKey());

                    v.setTag(distance); //update priority

                    q.remove(v);
                    q.add(v); //decrease priority in q

                }

            }

        }

    }

    @Override
    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node.
     * @return
     */
    public boolean isConnected() {


        if (this.graph.nodeSize() == 0 || this.graph.nodeSize() == 1)
            return true;

        List<node_info> list = new ArrayList<node_info>(this.graph.getV());
        djikstra(list.get(0).getKey());

        return !this.dist.containsValue(Double.MAX_VALUE);
    }

    @Override
    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    public double shortestPathDist(int src, int dest) {
        djikstra(src);
        return this.dist.get(dest);
    }

    @Override
    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * Note if no such path --> returns null;
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    public List<node_info> shortestPath(int src, int dest) {

        djikstra(src);

        List<node_info> result = new ArrayList<>();

        int father = dest;

        result.add(this.graph.getNode(dest));

        while (true) {

            if (father == src) break;

            result.add(this.graph.getNode(this.prev.get(father)));
            father = this.prev.get(father);

        }

        List<node_info> back_result = new ArrayList<>();

        for (int i = result.size() - 1; i >= 0; i--) {
            back_result.add(result.get(i));
        }

        return back_result;
    }

    @Override
    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    public boolean save(String file) {

        try {

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream ous = new ObjectOutputStream(fos);

            ous.writeObject(this.graph);
            ous.close();

            return true;
        }
        catch (IOException e) {
            System.out.println("failed");
            return false;
        }

    }

    @Override
    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    public boolean load(String file) {

        try {

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.graph = (weighted_graph) ois.readObject();
            ois.close();
        }
        catch (IOException | ClassNotFoundException e) {

        }
        return false;
    }
}
