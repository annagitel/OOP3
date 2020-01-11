package gui;
import org.json.JSONException;
import org.json.JSONObject;
import utils.*;
import dataStructure.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * The Graph_GUI class is the main class to run the GUI frames from.
 * It has a main function where graphs are generated and the StdDraw.drawGraph(g); command is called.
 * note - the StdDraw is a singletone class which means that only one graph can be displayed
 * (only one drawGraph call ) each time
 */

public class Graph_GUI {

    public static void main(String[] args) {
        StdDraw.drawFrame(); //main call
    }
}
