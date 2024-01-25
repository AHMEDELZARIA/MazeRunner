package ca.mcmaster.se2aa4.mazerunner;

@FunctionalInterface
public interface MazeExplorer {

    MazePath find_path(Tile[][] maze, int[][] entry_exit);

    // String validate_path(int[][] entry_exit, MazePath user_path);
}
