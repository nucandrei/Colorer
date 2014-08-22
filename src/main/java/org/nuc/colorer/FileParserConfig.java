package org.nuc.colorer;

public class FileParserConfig {

    private final String color;
    private final String filePath;
    private final String dateStyle;
    private final String from;
    private final String to;
    private boolean compressed;

    public FileParserConfig(String color, String filePath, String dateStyle, String from, String to, boolean compressed) {
        this.color = color;
        this.filePath = filePath;
        this.dateStyle = dateStyle;
        this.from = from;
        this.to = to;
        this.compressed = compressed;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public String getColor() {
        return color;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getDateStyle() {
        return dateStyle;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
