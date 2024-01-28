package ca.mcmaster.se2aa4.mazerunner;

/**
 * This interface defines the behaviour of a maze explorer.
 * Defines finding a path and validating a path to a maze.
 * Implementations of this interface should provide the methods below.
 */
public interface IMazeExplorer {

    /**
     * Finds a path to a maze, assuming atleast 1 path exists with an entry and exit point
     * @param maze holds the maze to search in
     * @return MazePath, holding the path to the maze
     */
    MazePath path(Maze maze);

    /**
     * Given a path, will validate whether the path is a valid path to the maze or not
     * @param maze holds the maze to search in
     * @param user_path MazePath, holding path that will be checked
     * @return true if valid, false otherwise
     */
    boolean validPath(Maze maze, MazePath user_path);
    
}
