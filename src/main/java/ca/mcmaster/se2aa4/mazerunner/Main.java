package ca.mcmaster.se2aa4.mazerunner;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.Level;
import org.apache.commons.cli.*;

/**
 * Main class for the Maze Runner System: entry point and configuration.
 * Usage: -i <maze_file_path> {OPTIONAL} -p <maze_path>
 */
public class Main {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Entry point for the system.
     * @param args command line arguments from the user
     */
    public static void main(String[] args) {
        // CURRENT LOG STATUS: DISABLED FOR RELEASE 
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.OFF);

        try {
            Configuration config = configure(args);
            logger.info(config);
            Maze maze = new Maze(config.maze_file);

            if (config.path_provided) {
                System.out.println(maze.valid_path(config.user_path));
                logger.info(maze.valid_path(config.user_path));
            } else {
                System.out.println(maze.path());
                logger.info(maze.path());
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
            logger.error(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Read arguments using Apache CLI libraray and store them in a configuration.
     * @param args the arguments provided by user
     * @return Configuration with the file holding the maze, the path specified by user (if applicable), and whether user specified a path
     * @throws ParseException if command line is invalid
     * @throws NullPointerException if file does not exist or incorrect path 
     */
    private static Configuration configure(String[] args) throws ParseException, NullPointerException {
        
        Options cli_options = new Options();
        cli_options.addOption("i", "input", true, "specifies a .txt file holding maze");
        cli_options.addOption("p", true, "specifies a maze path for validation");
        
        CommandLineParser cli_parser = new DefaultParser();
        CommandLine cmd = cli_parser.parse(cli_options, args);
        
        // If -i or -input flag not specified, return usage error and exit
        if (!cmd.hasOption("i")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar ./mazerunner.target -i <maze_file_path> {OPTIONAL} -p <maze_path>", cli_options);
            logger.error("-i flag not provided");
            System.exit(1);
        }

        File maze_file = new File(cmd.getOptionValue("i"));
        MazePath user_path = new MazePath(cmd.getOptionValue("p"));
        boolean path_provided = cmd.hasOption("p") ? true : false;

        return new Configuration(maze_file, user_path, path_provided);
    }

    /**
     * Data class (record) to store the maze file and user provided path to maze.
     * @param maze_file holds the the maze file object
     * @param user_path Mazepath that holds the user provided path
     */
    private record Configuration(File maze_file, MazePath user_path, boolean path_provided) {
        Configuration { // Validating provided data
            if (!maze_file.exists()) {
                throw new IllegalArgumentException("Maze .txt file not found: " + maze_file);
            }
            if (maze_file.length() == 0) {
                throw new IllegalArgumentException("Maze is empty");
            }
        }
    }

}