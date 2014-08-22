package org.nuc.colorer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();

        switch (args.length) {
        case 0:
            new Colorer("colorer.xml");
            break;

        case 1:
            if ("-generate".equals(args[0])) {
                LOGGER.error("Usage format : colorer.jar -generate number_of_files");

            } else {
                new Colorer(args[0]);
            }
            break;

        case 2:
            if ("-generate".equals(args[0])) {
                final String noFilesString = args[1];
                try {
                    int noFiles = Integer.parseInt(noFilesString);
                    LOGGER.info("Generate template config file");
                    ConfigGenerator.generate(noFiles);
                    LOGGER.info("Finished generating template config file");
                    break;

                } catch (Exception e) {
                    LOGGER.error("Invalid number found", e);
                    break;
                }

            } else {
                LOGGER.error("Usage format : colorer.jar config_file.xml");
                break;
            }

        default:
            LOGGER.error("Too many arguments were received. Usage : colorer.jar \ncolorer.jar config_file.xml \ncolorer.jar -generate number_of_files");
            break;
        }

        LOGGER.info("Application stopped.");

    }
}
