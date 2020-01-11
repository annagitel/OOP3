package dataStructure;

import java.io.Serializable;
 /**
  * this class represents edge in the graph
  * the edge has source node, destinaton node and weight
  *
  **/
public class EdgeData implements edge_data , Serializable {
    /********** private vars *************/
    private int source;
    private int destination;
    private double weight;
    private String info="";
    private int tag=0;
    /************ constructors ****************/
    public EdgeData(int s, int d, double w){
        this.source = s;
        this.destination = d;
        this.weight = w;
    }
    public EdgeData(){ //default constructor
        this.tag=0;
    }
    public EdgeData(int src, int dest, double weight, String info, int tag){
        this.source=src;
        this.destination=dest;
        this.weight=weight;
        this.info=info;
        this.tag=tag;
    }

    /*********** public methods ****************/
    public int getSrc() { //returns source key
        return this.source;
    }

    public int getDest() { //returns destination key
        return this.destination;
    }

    public double getWeight() { //returns edge weight
        return this.weight;
    }

    public int getTag() { //returns edge tag
        return this.tag;
    }

    public String getInfo() { //returns edge info
        return this.info;
    }

    public void setInfo(String s) { //sets info
      this.info=s;
    }

    public void setTag(int t) { //sets tag
        this.tag = t;
    }


    @Override
    public String toString(){ //returns a string representing the edge
        return "Src: "+this.source+ " Weight: "+this.weight+" Dest: "+this.destination+" Info: "+this.info+" Tag: "+this.tag;
    }
}
