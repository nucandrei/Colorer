package org.nuc.colorer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class ConfigParser {
    private static final Logger LOGGER = Logger.getLogger(ConfigParser.class);
    private final List<FileParserConfig> fileParserConfigs = new ArrayList<>();
    private final SimpleDateFormat prefferedDateFormat;
    private final String destination;

    public ConfigParser(File configFile) throws Exception {
        LOGGER.info("Started process for config file : " + configFile);
        final SAXBuilder builder = new SAXBuilder();
        final Document doc = builder.build(configFile);
        final Element root = doc.getRootElement();
        final Element destinationElement = root.getChild("destination");
        destination = destinationElement.getValue();
        final String prefferedDateFormatString = root.getChild("dateformat").getValue();
        prefferedDateFormat = new SimpleDateFormat(prefferedDateFormatString);
        final List<Element> elements = root.getChildren("file");
        for (Element e : elements) {
            final String color = e.getChildText("color");
            final String filePath = e.getChildText("path");
            final String dateStyle = e.getChildText("dateformat");
            final String from = e.getChildText("from");
            final String to = e.getChildText("to");
            final boolean compressed = Boolean.parseBoolean(e.getChildText("compressed"));
            fileParserConfigs.add(new FileParserConfig(color, filePath, dateStyle, from, to, compressed));
        }
        LOGGER.info("Finished process for config file : " + configFile);
    }

    public List<FileParserConfig> getFileParserConfigs() {
        return fileParserConfigs;
    }

    public String getDestinationPath() {
        return destination;
    }

    public SimpleDateFormat getPreferredDateFormat() {
        return prefferedDateFormat;
    }
}
