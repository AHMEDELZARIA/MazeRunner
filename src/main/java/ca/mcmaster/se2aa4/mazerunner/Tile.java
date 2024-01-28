package ca.mcmaster.se2aa4.mazerunner;

/**
 * Enum representing a tile of the maze.
 */
public enum Tile {
    WALL('#'),
    EMPTY(' ');

    private final char symbol;

    Tile(char symbol) { this.symbol = symbol; }

    @Override
    public String toString() { return symbol + ""; }
}
