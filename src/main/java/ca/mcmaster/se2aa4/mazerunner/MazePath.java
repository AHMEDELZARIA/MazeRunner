package ca.mcmaster.se2aa4.mazerunner;

import java.util.logging.LogManager;

public class MazePath {
    
    private final Integer length;
    private final String directions;

    public MazePath(Integer length, String directions) {
        this.length = length;
        this.directions = directions;
    }

    @Override
    public String toString() { return directions; }

}
