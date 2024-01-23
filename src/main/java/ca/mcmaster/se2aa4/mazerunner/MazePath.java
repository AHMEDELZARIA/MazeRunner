package ca.mcmaster.se2aa4.mazerunner;

import java.util.logging.LogManager;

public class MazePath {
    
    private final String directions;

    public MazePath(String directions) {
        this.directions = directions;
    }

    @Override
    public String toString() { return directions; }

}
