package gameClient;


import dataStructure.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.*;

import java.util.Collection;
import java.util.Iterator;

public class Fruit {
    private Point3D location;
    private double value;
    private edge_data current_edge;


    public Fruit(double v, Point3D p, edge_data e) {
        this.value = v;
        this.location = new Point3D(p);
        this.current_edge = e ;
    }

    private EdgeData findEdge(DGraph g, int type) {
        Iterator<node_data> nit = g.getV().iterator();
        while (nit.hasNext()) {
            Collection<edge_data> e = g.getE(nit.next().getKey());
            if (e != null) {
                Iterator<edge_data> eit = e.iterator();
                while (eit.hasNext()) {
                    edge_data current = eit.next();
                    double ps = g.getNode(current.getSrc()).getLocation().distance2D(this.location);
                    double pd = g.getNode(current.getDest()).getLocation().distance2D(this.location);
                    double sd = g.getNode(current.getSrc()).getLocation().distance2D(g.getNode(current.getDest()).getLocation());
                    if (ps + pd - sd < 0.0000001) {
                        if ((current.getDest() - current.getSrc()) * type > 0)
                            return (EdgeData) current;
                    }
                }
            }
        }
        return null;
    }

    public Fruit(DGraph g,String jsonString){
        try {
            JSONObject fruit = new JSONObject(jsonString);
            fruit = fruit.getJSONObject("Fruit");
            String loc = fruit.getString("pos");
            Double val = fruit.getDouble("value");
            int type = fruit.getInt("type");

            this.location = new Point3D(loc);
            this.value = val;
            this.current_edge = findEdge(g,type);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getType() {
        int ans = this.current_edge.getDest() - this.current_edge.getSrc();
        return ans;
    }

    public Point3D getLocation() {
        return new Point3D(this.location);
    }

    public double getValue() {
        return this.value;
    }

    public String toString() {
        return this.toJSON();
    }

    public String toJSON() {
        int d = 1;
        if (this.current_edge.getSrc() > this.current_edge.getDest()) {
            d = -1;
        }

        String ans = "{\"Fruit\":{\"value\":" + this.value + "," + "\"type\":" + d + "," + "\"pos\":\"" + this.location.toString() + "\"" + "}" + "}";
        return ans;
    }



    public double grap(Robot r, double dist) {
        double ans = 0.0D;
        if (this.current_edge != null && r != null) {
            int d = r.getNextNode();
            if (this.current_edge.getDest() == d) {
                Point3D rp = r.getLocation();
                if (dist > rp.distance2D(this.location)) {
                    ans = this.value;
                }
            }
        }

        return ans;
    }

}
