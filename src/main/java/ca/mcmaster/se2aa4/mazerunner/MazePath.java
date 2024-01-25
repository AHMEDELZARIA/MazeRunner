package ca.mcmaster.se2aa4.mazerunner;

import java.util.logging.LogManager;

public class MazePath {
    
    private final String directions;

    public MazePath(String directions) {
        this.directions = directions;
    }

    @Override
    public String toString() { return factorized_path(directions); }

    private String factorized_path(String path) {
        
        path = path.replaceAll("\\s", "");
        String fact_path = "";
        int occur = 0;
        
        int i = 0, j;
        while (i < path.length()) {
            
            for (j = i; j < path.length(); j++) {
                if (path.charAt(j) == path.charAt(i)) {
                    occur++;
                } else {
                    break;
                }
            }
            
            if (occur > 1) {
                fact_path += String.valueOf(occur) + String.valueOf(path.charAt(i)) + " ";
            } else {
                fact_path += String.valueOf(path.charAt(i)) + " ";
            }
            
            i = j;
            occur = 0;
        }
        
        return fact_path;
    
    }

}