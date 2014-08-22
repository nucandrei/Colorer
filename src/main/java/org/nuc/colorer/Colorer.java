package org.nuc.colorer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

public class Colorer {
    private static final Logger LOGGER = Logger.getLogger(Colorer.class);

    public Colorer(String configPath) {
        final File configFile = new File(configPath);
        if (configFile.exists() && configFile.canRead()) {
            try {
                final List<Line> listOfLines = new ArrayList<>();

                final ConfigParser configParser = new ConfigParser(configFile);
                final List<FileParserConfig> parsedConfig = configParser.getFileParserConfigs();
                final String destinationPath = configParser.getDestinationPath();
                final SimpleDateFormat preferredDateFormat = configParser.getPreferredDateFormat();

                for (FileParserConfig fileParserConfig : parsedConfig) {
                    final FileParser fileParser = new FileParser(fileParserConfig);
                    listOfLines.addAll(fileParser.getLines());
                }

                Collections.sort(listOfLines);
                final String generatedHTML = new HTMLGenerator(listOfLines, preferredDateFormat).generate();
                writeToDisk(destinationPath, generatedHTML);

            } catch (Exception exception) {
                LOGGER.error("Failed to parse config", exception);
            }

        } else {
            LOGGER.error("The file does not exist or can't be read");
        }
    }

    public void writeToDisk(String filePath, String content) throws IOException {
        final BufferedWriter wr = new BufferedWriter(new FileWriter(filePath));
        wr.write(content);
        wr.close();
    }
}
