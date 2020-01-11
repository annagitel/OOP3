package algorithms;

import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author
 *
 */
public class Graph_Algo implements graph_algorithms, Serializable {
    private graph graph = new DGraph();

    private Map<node_data, Double> distance = new HashMap<>();
    public Graph_Algo(){};
    public Graph_Algo(graph graph) {
        init(graph);
    }

    /**
     *
     init our graph from givven graph
     */
    @Override
    public void init(graph g) {
        this.graph = g;
    }

    /**
     * int our graph from givven string
     * @param file_name
     */

    @Override
    public void init(String file_name) {
        try {
            FileInputStream file = new FileInputStream(file_name);
            ObjectInputStream in = new ObjectInputStream(file);
            graph g = (graph) in.readObject();
            init(g);
            in.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * save to file from givven string
     * @param file_name
     */

    @Override
    public void save(String file_name) {
        try {
            FileOutputStream file = new FileOutputStream(file_name);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(graph);
            out.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * check if the grapg is full connecteg
     * @return
     */
    public boolean isConnected() {
        for (node_data node: graph.getV()) {
            if (!isCon(node.getKey()))
                return false;
            this.zeroTags();
        }
        return true;
    }

    /**
     * check if we been throw all nodes by checking their tags
     * @param node_key
     * @return
     */

    private boolean isCon (int node_key){
        changeTags(node_key);
        for (node_data node: graph.getV()) {
            if (node.getTag()!= 1)
                return false;
        }
        return true;
    }

    /**
     * change all node's tags
     * @param node_key
     */
    private void changeTags (int node_key){
        for (node_data node: graph.getV()) {
            int key = node.getKey();
            if (graph.getE(key)!=null) {
                for (edge_data edge : graph.getE(key)) {
                    int d = edge.getDest();
                    if (graph.getNode(d).getTag() != 1) {
                        graph.getNode(d).setTag(1);
                        changeTags(d);
                    }
                }
            }
        }
    }

    /**
     * init all node's tags to zero
     */
    private void zeroTags(){
        Collection<node_data> n = graph.getV();
        Iterator<node_data> it = n.iterator();
        while (it.hasNext()){
            it.next().setTag(0);
        }

        Iterator<node_data> nit = graph.getV().iterator();
        while (nit.hasNext()){
            Collection<edge_data> e = graph.getE(nit.next().getTag());
            if(e != null){
                Iterator<edge_data> eit = e.iterator();
                while (eit.hasNext()) {
                    eit.next().setTag(0);
                }
            }
        }
    }

    /**
     *
     check the shortest path between src and dest
     */

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if (graph.getNode(src) == null || graph.getNode(dest) == null)
            throw new RuntimeException("src or dst dose not exist");
        if (src == dest) {
            List<node_data> t = new LinkedList<>();
            t.add(graph.getNode(src));
            return t;
        }
        HashMap<Integer, Double> myBoard = new LinkedHashMap<>();
        HashMap<Integer, LinkedList<node_data>> myBoardList = new LinkedHashMap<>();
        Queue<Integer> myQueue = new LinkedList<>();
        int current;
        boolean desH = false;
        myQueue.add(src);
        myBoard.put(src, 0.0);
        LinkedList<node_data> temp = new LinkedList<>();
        temp.add(graph.getNode(src));
        myBoardList.put(src, temp);
        while (!myQueue.isEmpty()) {
            current = myQueue.poll();
            if (current == dest) {
                desH = true;
            } else desH = false;
            if (graph.getE(current) != null) {
                ArrayList<edge_data> myEData = new ArrayList<>(graph.getE(current));
                boolean flag = true;
                while (!desH && flag) {
                    int minIndex = minInArray(myEData);
                    if (minIndex != -1) {
                        edge_data minE = myEData.remove(minIndex);
                        if (updateBoard(myBoard, minE, myBoardList))
                            myQueue.add(minE.getDest());
                    } else flag = false;
                }
            }
        }
        return myBoardList.get(dest);
    }

    /**
     * update the board
     * @param board
     * @param myedge
     * @param map
     * @return
     */
    private boolean updateBoard(HashMap<Integer, Double> board, edge_data myedge, HashMap<Integer, LinkedList<node_data>> map) {
        int dest = myedge.getDest();
        int src = myedge.getSrc();
        LinkedList<node_data> list = map.get(src);
        double amount = myedge.getWeight() + board.get(src);
        if (!board.containsKey(dest)) {
            LinkedList<node_data> tempList = new LinkedList<>(list);
            tempList.add(graph.getNode(dest));
            board.put(dest, amount);
            map.put(dest, tempList);
        } else {
            if (board.get(dest) > amount) {
                LinkedList<node_data> tempList = new LinkedList<>(list);
                tempList.add(graph.getNode(dest));
                board.put(dest, amount);
                map.put(dest, tempList);
            } else return false;
        }
        return true;
    }

    /**
     * return the shortest path between src and dest
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (graph.getNode(src) == null || graph.getNode(dest) == null)
            throw new RuntimeException("src or dst dose not exist");
        if (src == dest) return 0;
        HashMap<Integer, Double> myBoard = new LinkedHashMap<>();
        Queue<Integer> myQueue = new LinkedList<>();
        int current;
        boolean desH = false;
        myQueue.add(src);
        myBoard.put(src, 0.0);
        while (!myQueue.isEmpty()) {
            current = myQueue.poll();
            if (current == dest) {
                desH = true;
            } else desH = false;
            if (graph.getE(current) != null) {

                ArrayList<edge_data> myEData = new ArrayList<>(graph.getE(current));
                boolean flag = true;
                while (!desH && flag) {
                    int minIndex = minInArray(myEData);
                    if (minIndex != -1) {
                        edge_data minE = myEData.remove(minIndex);
                        if (updateBoard(myBoard, minE))
                            myQueue.add(minE.getDest());
                    } else flag = false;
                }
            }
        }
        return myBoard.get(dest);
    }

    /**
     * update givven board
     * @param board
     * @param myedge
     * @return
     */
    private boolean updateBoard(HashMap<Integer, Double> board, edge_data myedge) {
        int dest = myedge.getDest();
        int src = myedge.getSrc();
        double amount = myedge.getWeight() + board.get(src);
        if (!board.containsKey(dest)) {
            board.put(dest, amount);
        } else {
            if (board.get(dest) > amount) {
                board.put(dest, amount);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * set the smallest weight
     * @param myList
     * @return
     */
    private int minInArray(List<edge_data> myList) {
        if (!myList.isEmpty()) {
            int minWE = 0;
            for (int i = 1; i < myList.size(); i++) {
                if (myList.get(minWE).getWeight() > myList.get(i).getWeight()) {
                    minWE = i;
                }
            }
            return minWE;
        }
        return -1;
    }

    /**
     * return the shortest path between all targets
     * @param targets
     * @return
     */

    @Override
    public List<node_data> TSP(List<Integer> targets) {

        List<node_data> TSP = new LinkedList<node_data>();
        Iterator<Integer> i = targets.iterator();
        int src=i.next();
        TSP.add(0,graph.getNode(src));
        while(i.hasNext())
        {
            int dest=i.next();
            List<node_data> temp = shortestPath(src,dest);
            if (temp==null) return null;
            temp.remove(0);//avoid duplicates
            TSP.addAll(temp);
            src=dest;
        }
        return TSP;

    }

    /**
     * deep copy to our graph
     * @return
     */
    @Override
    public graph copy() {
        graph copyGraph = new DGraph();
        if (this.graph != null) {
            Collection<node_data> colleOfNodes = graph.getV();
            for (node_data node : colleOfNodes) {
                copyGraph.addNode(node);
                Collection<edge_data> edges = graph.getE(node.getKey());
                if (edges != null) {
                    for (edge_data edge : edges) {
                        copyGraph.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
                    }
                }
            }

        }
        return copyGraph;
    }

}