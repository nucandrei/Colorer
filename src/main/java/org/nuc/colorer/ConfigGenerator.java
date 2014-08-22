package org.nuc.colorer;

import java.io.FileWriter;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ConfigGenerator {

    private static final Logger LOGGER = Logger.getLogger(ConfigGenerator.class);

    public static void generate(int noFiles) {
        final Element root = new Element("colorer");
        final Document doc = new Document(root);

        final Element destination = new Element("destination");
        destination.setText("output.html");
        root.addContent(destination);

        final Element dateFormat = new Element("dateformat");
        dateFormat.setText("yyyyMMdd-HH:mm:ss.SSS");
        root.addContent(dateFormat);

        for (int i = 0; i < noFiles; i++) {
            final Element file = new Element("file");
            final Element ownDateFormat = new Element("dateformat");
            ownDateFormat.setText("yyyyMMdd-HH:mm:ss.SSS");
            file.addContent(ownDateFormat);

            final Element compressed = new Element("compressed");
            compressed.setText("false");
            file.addContent(compressed);

            final Element color = new Element("color");
            color.setText("x");
            file.addContent(color);

            final Element path = new Element("path");
            path.setText("x");
            file.addContent(path);

            final Element from = new Element("from");
            from.setText("x");
            file.addContent(from);

            final Element to = new Element("to");
            to.setText("x");
            file.addContent(to);

            root.addContent(file);
        }

        final XMLOutputter xmlOutput = new XMLOutputter();
        try {
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter("colorer.xml"));
            
        } catch (Exception e) {
            LOGGER.error("Could not save config file", e);
        }
    }
}
