package ca.mcmaster.se2aa4.mazerunner;

public class RightHand implements MazeExplorer {

    public MazePath find_path(Tile[][] maze, int[][] entry_exit) {

        String path = "";

        int[] current_pos = entry_exit[0];
        int[] final_pos = entry_exit[1];

        while ((current_pos[0] == final_pos[0]) && (current_pos[1] != final_pos[1])) {
            current_pos[1] = current_pos[1] + 1;
            path += "F";
        } 
    
        return new MazePath(path);
    }

    public String validate_path(int[][] entry_exit, MazePath user_path) {

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
}
