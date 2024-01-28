package ca.mcmaster.se2aa4.mazerunner;

/**
 * Enum representing directions that can be faced in a maze.
 */
public enum Direction {
    NORTH('N'),
    SOUTH('S'),
    EAST('E'),
    WEST('W');

    private final char dir;

    Direction(char dir) {
        this.dir = dir;
    }

    @Override
    public String toString() { return dir + ""; }
}