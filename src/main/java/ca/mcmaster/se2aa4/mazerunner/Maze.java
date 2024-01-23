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

        int[] dim = maze_dim(maze_file);
        this.maze_grid = new Tile[dim[0]][dim[1]];
        load_maze(this.maze_grid, maze_file);

    }

    private void load_maze(Tile[][] maze_grid, File maze_file) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(maze_file));

            String line;
            int rows = maze_grid.length;
            int num_cols = maze_grid[0].length;

            for (int row = 0; row < rows; row++) {
                line = reader.readLine();
                for (int col = 0; col < num_cols; col++) {

                    maze_grid[row][col] = (col < num_cols ? (line.charAt(col) == '#' ? Tile.WALL : Tile.EMPTY) : Tile.EMPTY);

                }
            }

            reader.close();

        } catch (Exception e) {
            System.err.print("ERROR LOADING MATRIX\n");
            System.exit(1);
        }

    }

    private int[] maze_dim(File maze_file) {
        int[] dimensions = new int[2];
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(maze_file));
            int rows = 1, cols = reader.readLine().length();
            
            while (reader.readLine() != null) { rows++; }
            
            dimensions[0] = rows; dimensions[1] = cols;

            reader.close();

        } catch (Exception e) {
            System.err.print("ERROR COMPUTING MATRIX DIMENSIONS\n");
            System.exit(1);
        }

        return dimensions;
    }


    public MazePath path() {
        MazeExplorer maze_exp = new RightHand();
        return maze_exp.find_path(this);
    }

    public void print_maze() {
        for (Tile[] row : this.maze_grid) {
            for (Tile element: row) {
                System.out.print(element);
            }
            System.out.println();
        }
    }

    private int start() { return 10; }
}
