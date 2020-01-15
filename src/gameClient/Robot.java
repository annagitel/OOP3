package gameClient;

import org.json.JSONObject;
import utils.Point3D;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import dataStructure.*;

public class Robot {

    public static final double EPS;
    public static final double StartMoney = 0.0;
    public static final double EC = 1.0;
    public static final double DEFAULT_SPEED = 1.0;
    private static int _count;
    private static int _seed;

    private int id;
    private Point3D location;
    private double speed;
    private edge_data current_edge;
    private node_data current_node;
    private graph g;
    private long _start_move;
    private double money;
    private String type;

    static {
        EPS = Math.pow(1.0E-4, 2.0);
        _count = 0;
        _seed = 3331;
    }
    public Robot(String jsonString){
        try{
            JSONObject robot = new JSONObject(jsonString);
            int idd = robot.getInt("id");
            double value = robot.getDouble("value");
            int src = robot.getInt("src");
            int dest = robot.getInt("dest");
            double sped = robot.getDouble("speed");
            String loc = robot.getString("pos");

            this.id = idd;
            this.money = value;
            this.current_edge = g.getEdge(src,dest);
            this.speed = sped;
            this.location = new Point3D(loc);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public Robot(graph g, int start_node) {
        this(g, start_node, 50.0D, 100.0D);
    }

    public Robot(graph g, int start_node, double ds, double ts) {
        this.g = g;
        this.setMoney(0.0D);
        this.current_node = this.g.getNode(start_node);
        this.location = this.current_node.getLocation();
        this.id = _count++;
        this.setSpeed(1.0D);
    }
     public Robot(String type, int startNode){

    }

    public int getSrcNode() {
        return this.current_node.getKey();
    }

    public String toJSON() {
        int d = this.getNextNode();
        String ans = "{\"Robot\":{\"id\":" + this.id + "," +
                "\"value\":" + this.money + "," +
                "\"src\":" + this.current_node.getKey() + "," +
                "\"dest\":" + d + "," +
                "\"speed\":" + this.getSpeed() + "," +
                "\"pos\":\"" + this.location.toString() + "\"" + "}" + "}";
        return ans;
    }

    void setMoney(double v) {
        this.money = v;
    }

    public void addMoney(double d) {
        this.money += d;
    }

    private long getKey(int id) {
        long k0 = (new Random((long)this.id)).nextLong();
        long k1 = (new Random((long)_seed)).nextLong();
        long key = k0 ^ k1;
        return key;
    }

    public boolean setNextNode(int dest) {
        boolean ans = false;
        int src = this.current_node.getKey();
        boolean reset_time = !this.isMoving();
        this.current_edge = this.g.getEdge(src, dest);
        if (this.current_edge != null) {
            ans = true;
            if (reset_time) {
                this._start_move = (new Date()).getTime();
            }
        }

        return ans;
    }

    public boolean isMoving() {
        return this.current_edge != null;
    }

    public boolean move() {
        boolean ans = false;
        if (this.current_edge != null) {
            long now = (new Date()).getTime();
            double dt = (double)(now - this._start_move) / 1000.0;
            double v = this.getSpeed();
            double pr = v * dt / this.current_edge.getWeight();
            int dest = this.current_edge.getDest();
            node_data dd = this.g.getNode(dest);
            Point3D ddd = dd.getLocation();
            if (pr >= 1.0D) {
                this.location = ddd;
                this.current_node = dd;
                this.current_edge = null;
                ans = true;
            } else {
                Point3D src = this.current_node.getLocation();
                double dx = ddd.x() - src.x();
                double dy = ddd.y() - src.y();
                double dz = ddd.z() - src.z();
                double x = src.x() + dx * pr;
                double y = src.y() + dy * pr;
                double z = src.z() + dz * pr;
                Point3D cr = new Point3D(x, y, z);
                if (ddd.distance2D(cr) < ddd.distance2D(this.location)) {
                    this.location = cr;
                    ans = true;
                }
            }
        }

        return ans;
    }


    public void randomWalk() {
        if (!this.isMoving()) {
            Collection<edge_data> ee = this.g.getE(this.current_node.getKey());
            int t = ee.size();
            int ii = (int)(Math.random() * (double)t);
            Iterator<edge_data> itr = ee.iterator();

            for(int i = 0; i < ii; ++i) {
                itr.next();
            }

            this.setNextNode(((edge_data)itr.next()).getDest());
        } else {
            this.move();
        }

    }

    public String toString() {
        return this.toJSON();
    }

    public int getID() {
        return this.id;
    }

    public Point3D getLocation() {
        return this.location;
    }

    public double getMoney() {
        return this.money;
    }

    public double getBatLevel() {
        return 0.0D;
    }

    public int getNextNode() {
        int ans;
        if (this.current_edge == null) {
            ans = -1;
        } else {
            ans = this.current_edge.getDest();
        }

        return ans;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double v) {
        this.speed = v;
    }

}
