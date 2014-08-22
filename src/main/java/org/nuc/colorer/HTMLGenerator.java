package org.nuc.colorer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HTMLGenerator {
    final StringBuilder htmlBuilder;

    public HTMLGenerator(List<Line> lines, SimpleDateFormat dateFormat) {
        htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>");
        htmlBuilder.append("<style> font {white-space:nowrap} </style>");
        for (Line line : lines) {
            htmlBuilder.append(String.format("<font color=\"%s\">%s %s</font><br>", line.getColor(), dateFormat.format(new Date(line.getDate())), line.getContent()));
        }
        htmlBuilder.append("</html>");
    }

    public String generate() {
        return htmlBuilder.toString();
    }
}
