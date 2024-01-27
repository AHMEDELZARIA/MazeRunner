package ca.mcmaster.se2aa4.mazerunner;

public interface IMazeExplorer {

    MazePath path(Maze maze);

    boolean valid_path(Maze maze, MazePath user_path);

}
