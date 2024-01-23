package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Maze {

    private static final Logger logger = LogManager.getLogger();

    private final Tile[][] maze_grid;

    public Maze(File maze_file) {
        
        this.maze_grid = load_maze(maze_file);

    }

    private Tile[][] load_maze(File maze_file) {

        try {
            
            BufferedReader reader = new BufferedReader(new FileReader(maze_file));
            int rows = 1, cols = reader.readLine().length();
            
            while (reader.readLine() != null) { rows++; }

            reader.close();

            BufferedReader reader2 = new BufferedReader(new FileReader(maze_file));

            String line;
            Tile[][] maze = new Tile[rows][cols];

            for (int row = 0; row < rows; row++) {
                line = reader2.readLine();
                for (int col = 0; col < cols; col++) {
                    maze[row][col] = (col < cols ? (line.charAt(col) == '#' ? Tile.WALL : Tile.EMPTY) : Tile.EMPTY);
                }
            }

            reader2.close();

            return maze;

        } catch (Exception e) {
            System.err.print("ERROR LOADING MATRIX\n");
            System.exit(1);
            return null;
        }

        

    }

    public void print_maze() {
        for (Tile[] row : this.maze_grid) {
            for (Tile element: row) {
                System.out.print(element);
            }
            System.out.println();
        }
    }

    public MazePath path() {
        MazeExplorer maze_exp = new RightHand();
        return maze_exp.find_path(this);
    }

    private int start() { return 10; }
}
