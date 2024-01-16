package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        System.out.println("** Starting Maze Runner");
        Options cli_options = new Options();
        cli_options.addOption("i", "input", true, "specifies a .txt file holding matrix");
        CommandLineParser cli_parser = new DefaultParser();

        try {
            
            CommandLine cmd = cli_parser.parse(cli_options, args);
            System.out.println("**** Reading the maze from file " + cmd.getOptionValue("i"));

            BufferedReader reader = new BufferedReader(new FileReader(cmd.getOptionValue("i")));
            String line;
            while ((line = reader.readLine()) != null) {
                for (int idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        System.out.print("WALL ");
                    } else if (line.charAt(idx) == ' ') {
                        System.out.print("PASS ");
                    }
                }
                System.out.print(System.lineSeparator());
            }
        } catch(Exception e) {
            System.err.println("/!\\ An error has occured /!\\");
        }
        System.out.println("**** Computing path");
        System.out.println("PATH NOT COMPUTED");
        System.out.println("** End of MazeRunner");
    }
}
