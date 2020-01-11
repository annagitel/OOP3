package dataStructure;

import utils.Point3D;

import java.io.Serializable;

/**
 * represents a graph node
 * the node has key and location
 * weight, tag and info used in algorithms
 */
public class NodeData implements node_data, Serializable {
    /********** private vars **************/
    private int key;
    private Point3D location;
    private double weight= Double.MAX_VALUE;
    private int tag=0;
    private String info = "false";
    /******** constarctors **********/

    public NodeData(int k, Point3D p){
        this.key = Math.abs(k);
        this.location = p;
    }

    public NodeData(){//default constructor
        this.key=0;
        this.location = new Point3D(0,0,0);
    }
    public NodeData(int key, Point3D location , double weight,String info,int tag){
        this.key=key;
        this.location=location;
        this.weight=weight;
        this.info=info;
        this.tag=tag;
    }
    public NodeData(int key, Point3D location , double weight){
        this.key=key;
        this.location=location;
        this.weight=weight;
    }


    /********* public methods ********/

    public int getKey() { //returns key
        return this.key;
    }

    public Point3D getLocation() { //returns location
        return this.location;
    }

    public double getWeight() { //returns weight
        return this.weight;
    }

    public int getTag() { //returns tag
        return this.tag;
    }

    public String getInfo() { //returns info
        return this.info;
    }

    public void setLocation(Point3D p) { //sets location
        this.location = new Point3D(p);
    }

    public void setWeight(double w) { //sets weight
        this.weight = w;
    }

    public void setInfo(String s) { //sets info
        this.info = s;
    }

    public void setTag(int t) { //sets tag
        this.tag = t;
    }
    @Override
    public  String toString(){ //returns string representing the node
        return "key: "+this.getKey()+" Location: "+this.location+" Weight: "+this.weight+ " Info: "
                +this.info +" Tag: "+this.tag;
    }



}