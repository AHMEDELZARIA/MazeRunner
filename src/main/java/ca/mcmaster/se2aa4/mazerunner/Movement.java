package ca.mcmaster.se2aa4.mazerunner;

public enum Movement {
    FORWARD('F'),
    LEFT('L'),
    RIGHT('R');

    private final char move;

    Movement(char move) { this.move = move; }

    @Override
    public String toString() { return move + ""; }
}
