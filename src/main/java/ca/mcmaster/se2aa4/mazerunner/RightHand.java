package ca.mcmaster.se2aa4.mazerunner;

import java.util.Arrays;

/**
 * Implementation of the IMazeExplorer.
 * Applies the Right Hand on Wall algorithm for maze searching.
 */
public class RightHand implements IMazeExplorer {

    /**
     * Given a maze, applies the Right Hand on Wall algorithm to determine path to maze, assuming one exists.
     * @param maze holds the maze to search in
     * @return Mazepath representing the Right Hand on Wall path to the maze
     */
    public MazePath path(Maze maze) {
        int[][] entry_points = maze.getEntryExit();
        int[] exit_pos = entry_points[0];
        int[] curr_pos = entry_points[1];
        Direction dir = Direction.WEST; // Always start from right side entry, facing west
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
                    path += Movement.LEFT + " ";
                } else {
                    // Else move forward
                    curr_pos = move_forward(curr_pos, dir);
                    path += Movement.FORWARD + " ";
                }
            } else {
                // Turn right and move forward
                dir = turn_right(dir);
                curr_pos = move_forward(curr_pos, dir);
                path += Movement.RIGHT + " " + Movement.FORWARD + " ";
            }
        }
    
        return new MazePath(path);
    }

    /**
     * Determines if a given path for a given maze, is correct or not, regardless of the start side.
     * @param maze holds the maze to search in
     * @param path MazePath representing the path to be verified
     * @return true if path is valid and false otherwise
     */
    public boolean valid_path(Maze maze, MazePath path) {
        String user_path = path.toCanonical(); // First convert path to Canonical form
        int[] current_pos = maze.getEntryExit()[1]; // Initially equal to the entry on the right
        int[] final_pos = maze.getEntryExit()[0]; // Initially equal to the exit on the left
        int[] end = {current_pos[0], current_pos[1]}; // Keep track of coordinates of initial exit position, as it will be the entry for second test
        boolean wall_infront = false;
        boolean[] path_results = new boolean[2]; // Holds the result [[path valid from right-left?],[path valid from left-right]]
        
        // Test the path from left-right, then right-left
        for (int i = 0; i < 2; i++) {
            // Determine initial positions and directions
            Direction dir = (i == 0) ? Direction.WEST : Direction.EAST;
            current_pos = (i == 0) ? current_pos: final_pos;
            final_pos = (i == 0) ? final_pos: end;
            boolean invalid_instr = false; // Holds whether an invalid instruction was encountered in path
            
            // Go through path instructions and apply them
            for (int j = 0; j < user_path.length(); j++) {    
                try {
                    switch (user_path.charAt(j)) {
                        case 'F':
                            wall_infront = check_infront(maze, current_pos, dir);
                            if (wall_infront) {
                                // If commanded to move forward, but a wall is present, path is immediately invalid
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
                    invalid_instr = true;
                    path_results[i] = false; // Path from left-right or right-left is invalid
                    break;
                }
            }
            
            // Path is correct if we ended up in the final position and didn't encounter an invalid instruction
            if (Arrays.equals(current_pos, final_pos) && !invalid_instr) {
                path_results[i] = true;
            } else {
                path_results[i] = false;
            }
        }
        
        // Path is valid if either left-right or right-left is valid
        if (path_results[0] || path_results[1]) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Relative to a direction and current tile, returns the tile infront.
     * @param current_pos int[], holding [x,y] position of current tile
     * @param dir Direction, holds which direction is being faced
     * @return int[], holding the tile infronts [x,y] position
     */
    private int[] move_forward(int[] current_pos, Direction dir) { 
        switch (dir) {
            case WEST:
                current_pos[1] -= 1;
                return current_pos;
            case EAST:
                current_pos[1] += 1;
                return current_pos;
            case NORTH:
                current_pos[0] -= 1;
                return current_pos;
            case SOUTH:
                current_pos[0] += 1;
                return current_pos;
            default:
                return current_pos;
        }
    }
    
    /**
     * Relative to a direction, determines the direction to the right.
     * @param dir Direction, holds which direction is being faced  
     * @return Direction to the right of passed in Direction. Ex., if facing NORTH, EAST is returned.
     */
    private Direction turn_right(Direction dir) {
        switch (dir) {
            case WEST:
                return Direction.NORTH;
            case EAST:
                return Direction.SOUTH;
            case NORTH:
                return Direction.EAST;
            case SOUTH:
                return Direction.WEST;
            default:
                return dir;
        }
    } 
    
    /**
     * Relative to a direction, determines the direction to the left.
     * @param dir Direction, holds which direction is being faced  
     * @return Direction to the left of passed in Direction. Ex., if facing NORTH, WEST is returned.
     */
    private Direction turn_left(Direction dir) {
        switch (dir) {
            case WEST:
                return Direction.SOUTH;
            case EAST:
                return Direction.NORTH;
            case NORTH:
                return Direction.WEST;
            case SOUTH:
                return Direction.EAST;
            default:
                return dir;
        }
    } 
    
    /**
     * Relative to a direction, checks whether the tile on the right of current tile is a wall or empty space.
     * @param maze holds the maze to search in
     * @param current_pos int[], holding [x,y] position of current tile
     * @param dir Direction, holds which direction is being faced 
     * @return true if wall to right, false otherwise
     */
    private boolean check_right(Maze maze, int[] current_pos, Direction dir) {
        switch (dir) {
            case WEST:
                if (maze.tileAt(current_pos[0] - 1, current_pos[1]) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
            case EAST:
                if (maze.tileAt(current_pos[0] + 1, current_pos[1]) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
            case NORTH:
                if (maze.tileAt(current_pos[0], current_pos[1] + 1) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
            case SOUTH:
                if (maze.tileAt(current_pos[0], current_pos[1] - 1) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    /**
     * Relative to a current tile and direction, determines whether the tile infront is a wall or empty space.
     * @param maze holds the maze to search in
     * @param current_pos int[], holding [x,y] position of current tile
     * @param dir Direction, holds which direction is being faced
     * @return true if wall infront and false otherwise
     */
    private boolean check_infront(Maze maze, int[] current_pos, Direction dir) {
        switch (dir) {
            case WEST:
                if (maze.tileAt(current_pos[0], current_pos[1] - 1) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
            case EAST:
                if (maze.tileAt(current_pos[0], current_pos[1] + 1) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
            case NORTH:
                if (maze.tileAt(current_pos[0] - 1, current_pos[1]) == Tile.WALL) {
                    return true;
                } else {
                    return false;
                }
            case SOUTH:
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
