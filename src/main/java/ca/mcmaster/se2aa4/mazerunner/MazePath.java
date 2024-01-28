package ca.mcmaster.se2aa4.mazerunner;

/**
 * Holds the path to a maze and provides formatting operations.
 */
public class MazePath {
    
    private final String directions;

    public MazePath(String directions) { this.directions = directions; }

    @Override
    public String toString() { return this.toFactorized(); }    

    /**
     * Regardless if path is canonical or factorized, formats the path to canonical format. Ex. "3F 2R" -> "FFFRR".
     * @return String, holding the canonical path
     */
    public String toCanonical() {
        StringBuilder can_path = new StringBuilder(); // Holds new canonical path format
        String path = this.directions.toUpperCase(); 
        path = path.replaceAll("\\s", "");
        int fact_length = path.length();
        int num_inst = 0; // Will hold how many instructions we should convert. ex for 10F, it should write ten 'F'
        char inst = ' ';
        
        int i = 0;
        while (i < fact_length) {
            // Read in current char in path
            char curr_char = path.charAt(i);
            // If char is a number
            if (Character.isDigit(curr_char)) {
                // Check to see if number is more than 1 digit
                String num = ""; 
                num += curr_char;
                int j = i+1;
                while (Character.isDigit(path.charAt(j))) {
                    num+=path.charAt(j);
                    j++;
                }
                
                // Append current instruction num times
                num_inst = Integer.parseInt(num);
                inst = path.charAt(j);
                for (int k = 0; k < num_inst; k++) {
                    can_path.append(inst);
                }

                i = j+1;
            } else {
                // Add the single instruction to can_path
                can_path.append(curr_char);
                i++;
            }
        }
        
        return can_path.toString();
    }

    /**
     * Regardless if path is canonical or factorized, formats the path to factorized format. Ex. "FFFRR" -> "3F 2R".
     * @return String, holding the factorized path
     */
    public String toFactorized() {
        String path = this.directions.toUpperCase();
        path = path.replaceAll("\\s", "");
        String fact_path = ""; // Holds new factorized path format
        int occur = 0;
        
        int i = 0, j;
        while (i < path.length()) {
            // Determine how many times an instruction is repeated
            for (j = i; j < path.length(); j++) {
                if (path.charAt(j) == path.charAt(i)) {
                    occur++;
                } else {
                    break;
                }
            }
            
            // If more than once, append the number + the instruction
            if (occur > 1) {
                fact_path += String.valueOf(occur) + String.valueOf(path.charAt(i)) + " ";
            } else {
                // Else tjust the instruction
                fact_path += String.valueOf(path.charAt(i)) + " ";
            }
            
            i = j;
            occur = 0;
        }
        
        return fact_path;
    }
    
}