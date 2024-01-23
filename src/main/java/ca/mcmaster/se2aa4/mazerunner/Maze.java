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
                System.out.println(line);
                for (int col = 0; col < cols; col++) {
                    maze[row][col] = (line.charAt(col) == '#' ? Tile.WALL : Tile.EMPTY);
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

    public int[][] entry_exit_positions() {

        int rows = this.maze_grid.length;
        int cols = this.maze_grid[0].length;
        int [][] start_end = new int[2][2];

        for (int row = 0; row < rows; row++) {
            if (this.maze_grid[row][0] == Tile.EMPTY) {
                start_end[0][0] = row;
                start_end[0][1] = 0;
            }

            if (this.maze_grid[row][cols - 1] == Tile.EMPTY) {
                start_end[1][0] = row;
                start_end[1][1] = cols - 1;
            }
        }

        return start_end;
    }

    public MazePath path() {
        MazeExplorer maze_exp = new RightHand();
        return maze_exp.find_path(this.maze_grid, entry_exit_positions());
    }

    public String valid_path(MazePath user_path) {
        MazeExplorer maze_exp = new RightHand();
        return maze_exp.validate_path(entry_exit_positions(), user_path);
    }

}
