package gameClient;
import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import utils.StdDraw;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyGameGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        Object[] possibilities = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
        String s = (String)JOptionPane.showInputDialog(frame,
                "Choose a scenario", "Start game ",JOptionPane.PLAIN_MESSAGE, null, possibilities,"1");

        Object[] options = {"Automatic", "Manual"};
        int n = JOptionPane.showOptionDialog(frame, "choose game type",
                "Start game", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

        game_service game = Game_Server.getServer(Integer.parseInt(s));  /**the fucking game server**/
        String graph_string = game.getGraph();
        DGraph g = new DGraph(graph_string);

        ArrayList<Fruit> fruits = new ArrayList<>();
        HashMap<Integer, Robot> robots = new HashMap<>();
        List<String> robots_string = game.getRobots();
        List<String> fruit_string = game.getFruits();
        for (String fruit : fruit_string) {
            System.out.println(fruit);
            fruits.add(new Fruit(g, fruit));
        }

        for (String robot : robots_string) {
            Robot r = new Robot(robot);
            robots.put(robots.size()+1, r);
        }

        StdDraw.drawFrame(g,robots, fruits); //main call

    }
}
