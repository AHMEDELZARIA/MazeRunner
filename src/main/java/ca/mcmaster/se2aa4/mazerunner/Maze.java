package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Holds a maze and provides it's technicalities.
 */
public class Maze {

    private final Tile[][] maze_grid;
    private final IMazeExplorer maze_exp = new RightHand();
    private static final Logger logger = LogManager.getLogger();

    /**
     * Create a maze, considering the maze file provided in the configuration from user.
     * @param maze_file File object holding the maze provided by user
     */
    public Maze(File maze_file) { this.maze_grid = load_maze(maze_file); }

    /**
     * Load a maze into a Tile[][] structure from user provided maze file.
     * @param maze_file File object holding the maze provided by user
     * @return the maze from maze_file loaded into Tile[][] structure
     */
    private Tile[][] load_maze(File maze_file) {
        try {
            // Determine maze dimensions
            BufferedReader reader = new BufferedReader(new FileReader(maze_file));
            int rows = 1, cols = reader.readLine().length(); // Row starts at 1 to account for reader.readLine() in cols init 
            
            while (reader.readLine() != null) { rows++; }
            reader.close();

            // Load maze into Tile[][] structure, open new reader to start at beginning
            BufferedReader reader2 = new BufferedReader(new FileReader(maze_file));
            Tile[][] maze = new Tile[rows][cols];
            String line;

            for (int row = 0; row < rows; row++) {
                line = reader2.readLine();
                for (int col = 0; col < cols; col++) {
                    maze[row][col] = (line.charAt(col) == '#' ? Tile.WALL : Tile.EMPTY);
                }
            }
            reader2.close();

            return maze;
        } catch (Exception e) {
            System.out.println("Error Reading Maze.");
            logger.error("Error Reading Maze.");
            System.exit(1);
            return null; // Not reachable, but here to satisfy return
        }
    }

    public int getHeight() { return this.maze_grid.length; }

    public int getWidth() { return this.maze_grid[0].length; }

    /**
     * Provides if a tile of interest at positon (x,y) in tne maze is a wall or empty space.
     * @param x int, x coordinate of tile of interest
     * @param y int, y coordiante of tile of interest
     * @return Tile at (x,y) in maze
     */
    public Tile tileAt(int x, int y) { 
        try {
            return this.maze_grid[x][y];
        } catch (Exception e) {
            // Occurs when caller specifies an out of bounds coordinate, in that case, treat is as a wall
            return Tile.WALL;
        }
    }

    /**
     * Determines the two entry tiles of the maze, assuming they are on the left and right side.
     * @return the two entry tiles coordinates [[x1,y1],[x2,y2]] stored in a int 2D array
     */
    public int[][] getEntryExit() {
        int rows = this.maze_grid.length;
        int cols = this.maze_grid[0].length;
        int[][] start_end = new int[2][2]; // Will hold 2 entry tiles coordinates

        // Once an empty tile is found in left and right most columns of maze, those are the entries
        for (int row = 0; row < rows; row++) {
            // Determines left side entry tile
            if (this.maze_grid[row][0] == Tile.EMPTY) {
                start_end[0][0] = row;
                start_end[0][1] = 0;
            }
            // Determines right side entry tile
            if (this.maze_grid[row][cols - 1] == Tile.EMPTY) {
                start_end[1][0] = row;
                start_end[1][1] = cols - 1;
            }
        }

        return start_end;
    }

    /**
     * Finds a path of the maze.
     * Delegated to RightHand Class
     * @return MazePath holding a path for the maze.
     */
    public MazePath path() { return this.maze_exp.path(this); }

    /**
     * Determines if a specified path is valid for the maze.
     * Delegated to RightHand Class
     * @param user_path MazePath representing the path to be verified
     * @return String "correct path" if valid and "incorrect path" otherwise
     */
    public String valid_path(MazePath user_path) {
        boolean valid = this.maze_exp.valid_path(this, user_path);
        return valid ? "correct path" : "incorrect path";
    }

}
