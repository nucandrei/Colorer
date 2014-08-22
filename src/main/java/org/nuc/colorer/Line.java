package org.nuc.colorer;

import org.apache.commons.lang3.StringUtils;

public class Line implements Comparable<Line> {
    private String lineContent;
    private final String lineColor;
    private final long date;

    public Line(String content, long date, String color) {
        this.lineContent = content;
        this.date = date;
        this.lineColor = color;
    }

    public int compareTo(Line that) {
        if (this.date < that.date) {
            return -1;

        } else if (this.date == that.date) {
            return this.lineContent.compareTo(that.lineContent);

        } else {
            return 1;
        }
    }

    public String getContent() {
        return this.lineContent;
    }

    public String getColor() {
        return this.lineColor;
    }

    public long getDate() {
        return this.date;
    }

    public void append(String line, int offset) {
        if (!"".equals(line)) {
            this.lineContent = this.lineContent + "<br>" + getOffset(offset) + line;
        }
    }

    private String getOffset(int offset) {
        return StringUtils.repeat(' ', offset);
    }
}