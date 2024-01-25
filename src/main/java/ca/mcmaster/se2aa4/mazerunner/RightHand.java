package ca.mcmaster.se2aa4.mazerunner;

import java.util.Arrays;

public class RightHand implements MazeExplorer {

    public MazePath find_path(Tile[][] maze, int[][] entry_exit) {

        int[] curr_pos = entry_exit[1];
        int[] exit_pos = entry_exit[0];
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
                wall_infront = check_infront(maze, curr_pos, dir);
                
                // If yes
                if (wall_infront) {
                    // Turn left
                    dir = turn_left(dir);
                    path += "L ";
                } else {
                    // Else move forward
                    curr_pos = move_forward(maze, curr_pos, dir);
                    path += "F ";
                }

            } else {
                
                // Turn right and move forward
                dir = turn_right(dir);
                curr_pos = move_forward(maze, curr_pos, dir);
                path += "R F ";
                
            }
            
        }
    
        return new MazePath(path);
    }

    private int[] move_forward(Tile[][] maze, int[] current_pos, char dir) {
            
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
    
    private static boolean check_right(Tile[][] maze, int[] current_pos, char dir) {
        
        switch (dir) {
            case 'W':
                
                if (maze[current_pos[0] - 1][current_pos[1]] == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            case 'E':
                
                if (maze[current_pos[0] + 1][current_pos[1]] == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            case 'N':
                
                if (maze[current_pos[0]][current_pos[1] + 1] == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            case 'S':
                
                if (maze[current_pos[0]][current_pos[1] - 1] == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }

            default:
                return false;
        }
        
    }
    
    private static boolean check_infront(Tile[][] maze, int[] current_pos, char dir) {
        
        switch (dir) {
            case 'W':
                
                if (maze[current_pos[0]][current_pos[1] - 1] == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            case 'E':
                
                if (maze[current_pos[0]][current_pos[1] + 1] == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            case 'N':
                
                if (maze[current_pos[0] - 1][current_pos[1]] == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            case 'S':
                
                if (maze[current_pos[0] + 1][current_pos[1]] == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
                
            default:
                return false;
        }
        
    }

}
    /*public String validate_path(int[][] entry_exit, MazePath user_path) {

        int[] current_pos = entry_exit[0];
        int[] final_pos = entry_exit[1];

        for (int i = 0; i < user_path.toString().length(); i++) {
            if ((user_path.toString()).charAt(i) == 'F') {
                current_pos[1] = current_pos[1] + 1;
            }
        }

        if ((current_pos[0] == final_pos[0]) && (current_pos[1] == final_pos[1])) {
            return "Correct path";
        } else {
            return "Incorrect path";
        }
    }
    */
