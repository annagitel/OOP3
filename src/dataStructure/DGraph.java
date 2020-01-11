package dataStructure;

import utils.Point3D;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
* this class represents a Directed, weighted graph
*
**/
public class DGraph implements graph, Serializable {
	private HashMap<Integer, HashMap<Integer, edge_data>> edges; //graph edges
	private HashMap<Integer, node_data> nodes; //graph nodes
	public int modeCount =0;

	/************constractors*****************/

	public DGraph(){ //empty constructor
		this.nodes = new HashMap<>();
		this.edges = new HashMap<>();
	}


	public DGraph(graph g){ //copy constructor
		Collection<node_data> n = g.getV();
		Iterator<node_data> it = n.iterator();
		while (it.hasNext()){
			this.addNode(it.next());
		}

		Iterator<node_data> nit = this.getV().iterator();
		while (nit.hasNext()){
			Collection<edge_data> e = g.getE(nit.next().getKey());
			if( e != null){
				Iterator<edge_data> eit = e.iterator();
				while (eit.hasNext()){
					edge_data current = eit.next();
					this.connect(current.getSrc(), current.getDest(), current.getWeight());
				}
			}
		}
	}

	public DGraph(node_data n){ // init with one node
		this.nodes = new HashMap<>();
		this.edges = new HashMap<>();
		this.nodes.put(n.getKey(),n);

	}

	/********** public methods ******************/
	@Override
	public node_data getNode(int key) { //returns node by key

		return nodes.get(key);
	}
	@Override
	public edge_data getEdge(int src, int dest) { //returns edge by source and dest
		if (edges.get(src)==null||edges.get(src).get(dest)==null) {
			return null;
}
		return edges.get(src).get(dest);
	}

	@Override
	public void addNode(node_data n) { // adds node to graph
		if(!nodes.containsKey(n.getKey())) {
			nodes.put(n.getKey(), n);
			modeCount++;

		}
	}

	public void connect(int src, int dest, double w) { //adds edge to graph
		EdgeData ed = new EdgeData(src,dest,w);
		if (edges.containsKey(src)){
			if (edges.get(src).containsKey(dest))
				System.out.println("The nodes are already connected.");
			else
				edges.get(src).put(dest,ed);
		}
		else {
			edges.put(src, new HashMap<>());
			edges.get(src).put(dest,ed);
		}
		modeCount++;

	}

	public Collection<node_data> getV() { //return collection of all nodes
		Collection<node_data> c = nodes.values();
		return c;
	}

	public Collection<edge_data> getE(int node_id) { //returns collection of all edges from given node
		if(this.edges.containsKey(node_id))
			return this.edges.get(node_id).values();
		else
			return null;
	}


	public node_data removeNode(int key) {
		edges.remove(key);
		for(HashMap<Integer,edge_data> tempE:edges.values()){
			if (tempE.containsValue(key)){
				tempE.remove(key);
			}
		}

		nodes.remove(key);
		modeCount++;

		return null;
	}

	public edge_data removeEdge(int src, int dest) { //removes edge
		EdgeData e = (EdgeData) edges.get(src).get(dest);
		edges.get(src).remove(dest);
		if (edges.get(src).isEmpty())
			edges.remove(src);
		modeCount++;

		return e;
	}

	public int nodeSize() { //returns number of nodes n graph
		return nodes.size();
	}

	public int edgeSize() { //returns number of edges in graph
		int num=0;
		for (Integer src: edges.keySet()) {
			num+=edges.get(src).size();
		}
		return num;
	}

	public int getMC() { //returns mode count
		return this.modeCount;
	}

}
