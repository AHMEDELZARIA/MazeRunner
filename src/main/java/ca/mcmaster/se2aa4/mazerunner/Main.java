package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.lang.module.Configuration;
import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.tools.picocli.CommandLine.ParameterException;
import org.apache.commons.cli.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.ALL);

        // Set up configuration
        try {
            Configuration config = configure(args);
            Maze maze = new Maze(config.maze_file);
            maze.print_maze();
            MazePath maze_path = maze.path();
            System.out.println(maze_path);
            System.out.println(maze.valid_path(config.user_path));
        } catch(ParseException pe) {
            System.err.println(pe.getMessage());
            System.exit(1);
        } catch(NullPointerException npe) {
            System.err.println(npe.getMessage());
            System.exit(1);
        } catch(IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
            System.exit(1);
        }

        logger.info("**** Computing path");
        logger.error("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }



    private static Configuration configure(String[] args) throws ParseException, NullPointerException {
        // Add option to input a .txt file holding maze
        Options cli_options = new Options();
        cli_options.addOption("i", "input", true, "specifies a .txt file holding maze");
        cli_options.addOption("p", true, "specifies a maze path for validation");
        
        // Parse the command line input for input option
        CommandLineParser cli_parser = new DefaultParser();
        CommandLine cmd = cli_parser.parse(cli_options, args);
        File maze_file = new File(cmd.getOptionValue("i"));
        MazePath user_path = new MazePath(cmd.getOptionValue("p"));
        logger.info("Input file path is: " + maze_file);

        return new Configuration(maze_file, user_path);
    }

    private record Configuration(File maze_file, MazePath user_path) {
        Configuration {
            if (!maze_file.exists()) {
                throw new IllegalArgumentException("Maze .txt file not found: " + maze_file);
            }
        }
    }
}