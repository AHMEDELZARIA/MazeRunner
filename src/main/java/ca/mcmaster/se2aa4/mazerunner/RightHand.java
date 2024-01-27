package ca.mcmaster.se2aa4.mazerunner;

import java.util.Arrays;

public class RightHand implements IMazeExplorer {

    public MazePath path(Maze maze) {

        int[][] entry_points = maze.getEntryExit();
        int[] exit_pos = entry_points[0];
        int[] curr_pos = entry_points[1];
        char dir = 'W';
        boolean wall_to_right = false;
        boolean wall_infront = false;
        String path = "";

        while (!Arrays.equals(curr_pos, exit_pos)) {
            
            // Is there a wall to the right?
            wall_to_right = check_right(maze, curr_pos, dir);
            
            // If yes
            if (wall_to_right) {
                
                // Is there a wall in front?
                wall_infront = check_infront(maze,curr_pos, dir);
                
                // If yes
                if (wall_infront) {
                    // Turn left
                    dir = turn_left(dir);
                    path += "L ";
                } else {
                    // Else move forward
                    curr_pos = move_forward(curr_pos, dir);
                    path += "F ";
                }

            } else {
                
                // Turn right and move forward
                dir = turn_right(dir);
                curr_pos = move_forward(curr_pos, dir);
                path += "R F ";
                
            }
            
        }
    
        return new MazePath(path);
    }

    public boolean valid_path(Maze maze, MazePath path) {
    
        String user_path = path.toCanonical();
        System.out.println(user_path);
        boolean wall_infront = false;
        int[] current_pos = maze.getEntryExit()[1];
        int[] final_pos = maze.getEntryExit()[0];
        int[] end = {current_pos[0], current_pos[1]};
        String[] path_results = new String[2];
        
        for (int i = 0; i < 2; i++) {
            
            char dir = (i == 0) ? 'W' : 'E';
            current_pos = (i == 0) ? current_pos: final_pos;
            final_pos = (i == 0) ? final_pos: end;
            boolean invalid_dir = false;
            
            // Check path from right
            for (int j = 0; j < user_path.length(); j++) {
                
                try {
                    
                    switch (user_path.charAt(j)) {
                        case 'F':
                            wall_infront = check_infront(maze, current_pos, dir);
                            if (wall_infront) {
                                throw new Exception();
                            } else {
                                current_pos = move_forward(current_pos, dir);
                            }
                            break;
                        case 'R':
                            dir = turn_right(dir);
                            break;
                        case 'L':
                            dir = turn_left(dir);
                            break;
                        default:
                            throw new Exception();
                    }
                    
                } catch (Exception e) {
                    invalid_dir = true;
                    path_results[i] = "Incorrect Path";
                    break;
                }
                
            }
            
            if (Arrays.equals(current_pos, final_pos) && !invalid_dir) {
                path_results[i] = "Correct Path";
            } else {
                path_results[i] = "Incorrect Path";
            }
        }
        
        if (path_results[0].equals("Correct Path") || path_results[1].equals("Correct Path")) {
            return true;
        }
        else {
            return false;
        }
        
    }

    private int[] move_forward(int[] current_pos, char dir) {
            
        switch (dir) {
            case 'W':
                current_pos[1] -= 1;
                return current_pos;
            case 'E':
                current_pos[1] += 1;
                return current_pos;
            case 'N':
                current_pos[0] -= 1;
                return current_pos;
            case 'S':
                current_pos[0] += 1;
                return current_pos;
            default:
                return current_pos;
        }
        
    }
    
    private char turn_right(char dir) {
        
        switch (dir) {
            case 'W':
                return 'N';
            case 'E':
                return 'S';
            case 'N':
                return 'E';
            case 'S':
                return 'W';
            default:
                return dir;
        }
        
    } 
    
    private char turn_left(char dir) {
        
        switch (dir) {
            case 'W':
                return 'S';
            case 'E':
                return 'N';
            case 'N':
                return 'W';
            case 'S':
                return 'E';
            default:
                return dir;
        }

    } 
    
    private boolean check_right(Maze maze, int[] current_pos, char dir) {
        
        switch (dir) {
            case 'W':
                
                if (maze.tileAt(current_pos[0] - 1, current_pos[1]) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            case 'E':
                
                if (maze.tileAt(current_pos[0] + 1, current_pos[1]) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            case 'N':
                
                if (maze.tileAt(current_pos[0], current_pos[1] + 1) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            case 'S':
                
                if (maze.tileAt(current_pos[0], current_pos[1] - 1) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }

            default:
                return false;
        }
        
    }
    
    private boolean check_infront(Maze maze, int[] current_pos, char dir) {
        
        switch (dir) {
            case 'W':
                
                if (maze.tileAt(current_pos[0], current_pos[1] - 1) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            case 'E':
                
                if (maze.tileAt(current_pos[0], current_pos[1] + 1) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            case 'N':
                
                if (maze.tileAt(current_pos[0] - 1, current_pos[1]) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            case 'S':
                
                if (maze.tileAt(current_pos[0] + 1, current_pos[1]) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            default:
                return false;
        } 
    }

}
