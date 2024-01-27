package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Maze {

    private final Tile[][] maze_grid;

    private final IMazeExplorer maze_exp = new RightHand();

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
                //System.out.println(line);
                for (int col = 0; col < cols; col++) {
                    maze[row][col] = (line.charAt(col) == '#' ? Tile.WALL : Tile.EMPTY);
                }
            }

            reader2.close();

            return maze;

        } catch (Exception e) {
            System.out.println("Error Reading Maze.");
            System.exit(1);
            return null;
        }

        

    }

    public int getHeight() { return this.maze_grid.length; }

    public int getWidth() { return this.maze_grid[0].length; }

    public Tile tileAt(int x, int y) { 

        try {
            return this.maze_grid[x][y];
        } catch (Exception e) {
            return Tile.WALL;
        }

    }

    public int[][] getEntryExit() {

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
        return this.maze_exp.path(this);
    }

    public String valid_path(MazePath user_path) {
        boolean valid = this.maze_exp.valid_path(this, user_path);

        return valid ? "correct path" : "incorrect path";
    }

}
