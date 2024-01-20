package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Maze {
    
    private static final Logger logger = LogManager.getLogger();

    public Maze(File maze_file) {
        // PARSE FILE AND PLACE MAZE IN A DATA STRUCTURE
    }

    public MazePath path() {
        MazeExplorer maze_exp = new RightHand();
        return maze_exp.find_path(this);
    }

    private int start() { return 10; }
}
