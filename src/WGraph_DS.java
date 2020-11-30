package ex1.src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class WGraph_DS implements weighted_graph, Serializable {

    private HashMap<Integer,node_info> node_list; // { n0.key = n0, n1.key = n1 .... }
    private HashMap<Integer, HashMap<Integer, Double>> ni; // { n0.key = { n1.key = w01, n2.key = w02 }, n1.key = {}, ..}
    private int MC;
    private int edge_count;

    public WGraph_DS() {
        this.node_list = new HashMap<>();
        this.ni = new HashMap<>();
        this.MC = 0;
        this.edge_count = 0;
    }

    public class Node_Inner implements node_info, Serializable, Comparable<node_info> {

        private static final long serialVersionUID = 1L;

        private int key;
        private String information;
        private double tag;

        private Node_Inner(int key){
            this.key = key;
            this.information = "";
            this.tag = 0.0;
        }
        private Node_Inner(int key,String s){
            this.key = key;
            this.information = s;
            this.tag = 0.0;
        }


        /**
         * Return the key (id) associated with this node.
         *  */
        @Override
        public int getKey() {

            return this.key;
        }
        /**
         * return the remark (meta data) associated with this node.

         */

        @Override
        public String getInfo() {
            return information;
        }
        /**
         * Allows changing the remark (meta data) associated with this node.
         * @param s
         */

        @Override
        public void setInfo(String s) {
            this.information=s;


        }
        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         * @return
         */


        @Override
        public double getTag() {
            return this.tag;
        }
        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         * @param t - the new value of the tag
         */


        @Override
        public void setTag(double t) {
            this.tag=t;

        }

        @Override
        /** method to compare between 2 nodes (by tag)
         *
         */
        public int compareTo(node_info o) {

            Double w1 = this.getTag();
            Double w2 = o.getTag();

            if (w1 > w2)
                return 1;
            else if (w1 < w2)
                return -1;

            return 0;
        }

        @Override
        /**
         * this method checks if 2 objects are equals.
         */
        public boolean equals(Object obj) {
            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            Node_Inner n = (Node_Inner) obj;

            return getKey() == n.getKey() &&
                    getInfo().equals(n.getInfo()) &&
                    getTag() == n.getTag();

        }

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        WGraph_DS g = (WGraph_DS) obj;

        return getMC() == g.getMC() &&
                edgeSize() == g.edgeSize() &&
                nodeSize() == g.nodeSize();

    }

    @Override
    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    public node_info getNode(int key) {
        return node_list.get(key);
    }

    /**
     * checks 3 things:
     * if its not the same node
     * if the node isn't null
     * if they have a different keys
     * @param node1
     * @param node2
     * @return
     */
    private boolean checker(int node1, int node2) {

        if (this.node_list.containsKey(node1) && this.node_list.containsKey(node2)) {

            if (getNode(node1) != null && getNode(node2) != null) {

                return node1 != node2;

            }

        }

        return false;

    }

    @Override
    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
    public boolean hasEdge(int node1, int node2) {

            if (checker(node1, node2))
                return ni.get(node1).containsKey(node2) && ni.get(node2).containsKey(node1);

            return false;
        }

    @Override
    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
    public double getEdge(int node1, int node2) {
        if (checker(node1, node2))
            if (hasEdge(node1, node2) && hasEdge(node2, node1))
                return ni.get(node1).get(node2);

        return -1;

    }

    /**
     * add a ************ new ************ node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     * @param key
     */
    @Override
    public void addNode(int key) {

             if(!(node_list.containsKey(key))){

                 node_list.put(key, new Node_Inner(key));
                 ni.put(key, new HashMap<>());
                 MC++;
             }
    }

    @Override
    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.

     *if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    public void connect(int node1, int node2, double w) {
        if (w >= 0) {

            if (checker(node1, node2)) {

                if (!hasEdge(node1, node2) && !hasEdge(node2, node1)) {

                    ni.get(node1).put(node2, w);
                    ni.get(node2).put(node1, w);

                    MC++;
                    edge_count++;

                }
                else {

                    ni.get(node1).put(node2, w);
                    ni.get(node2).put(node1, w);

                    MC++;

                }

            }

        }
    }

    @Override
    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * @return Collection<node_data>
     */
    public Collection<node_info> getV() {
        return node_list.values();
    }

    @Override
    /**
     *
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * @return Collection<node_data>
     */
    public Collection<node_info> getV(int node_id) {

        List<node_info> list = new ArrayList<>();

        for (Integer key : this.ni.get(node_id).keySet())
            list.add(getNode(key));

        return list;
    }

    @Override
    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * @return the data of the removed node (null if none).
     * @param key
     */
    public node_info removeNode(int key)
    {

        if (node_list.containsKey(key)) {

            for (node_info node : getV(key)) {
                removeEdge(key, node.getKey());
            }

            ni.remove(key);
            return node_list.remove(key);

        }

        return null;
    }

    @Override
    /**
     * Delete the edge from the graph,
     * @param node1
     * @param node2
     */
    public void removeEdge(int node1, int node2) {

        if (checker(node1, node2)) {

            if (hasEdge(node1, node2) && hasEdge(node2, node1)) {

                ni.get(node1).remove(node2);
                ni.get(node2).remove(node1);

                MC++;
                edge_count--;

            }

        }

    }

    @Override
    /** return the number of vertices (nodes) in the graph.
     * @return
     */
    public int nodeSize() {
        return node_list.size();
    }

    @Override
    /**
     * return the number of edges (undirectional graph).
     * @return
     */
    public int edgeSize() {
        return edge_count;
    }

    @Override
    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return
     */
    public int getMC() {
        return MC;
    }


}

