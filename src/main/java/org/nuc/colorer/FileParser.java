package org.nuc.colorer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class FileParser {
    private static final Logger LOGGER = Logger.getLogger(FileParser.class);
    private final List<Line> lines = new ArrayList<>();

    public FileParser(FileParserConfig fileParserConfig) throws Exception {
        LOGGER.info("Started processing " + fileParserConfig.getFilePath());
        final SimpleDateFormat fileDateFormat = new SimpleDateFormat(fileParserConfig.getDateStyle());
        final int dateStyleLength = fileParserConfig.getDateStyle().length();
        final String color = fileParserConfig.getColor();

        final long lowerTimeLimit = parseTimeLimit(fileParserConfig.getFrom(), Long.MIN_VALUE, fileDateFormat);
        final long upperTimeLimit = parseTimeLimit(fileParserConfig.getTo(), Long.MAX_VALUE, fileDateFormat);
        final String timeRegex = extractRegexHeadRuleFromText(fileParserConfig.getDateStyle());

        final File file = new File(fileParserConfig.getFilePath());
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            LOGGER.error("Failed to use specified file : " + fileParserConfig.getFilePath());
            return;
        }

        final BufferedReader bufferedReader;
        if (fileParserConfig.isCompressed()) {
            final FileInputStream fis = new FileInputStream(file);
            final BufferedInputStream bis = new BufferedInputStream(fis);
            final CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
            bufferedReader = new BufferedReader(new InputStreamReader(input));

        } else {
            bufferedReader = new BufferedReader(new FileReader(file));
        }

        String line;
        Line currentLine = null;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.matches(timeRegex)) {
                final long unixTime = getUNIXTime(line, dateStyleLength, fileDateFormat);
                if (lower(upperTimeLimit, unixTime)) {
                    break;
                }
                if (greater(unixTime, lowerTimeLimit)) {
                    currentLine = extractLineFromString(line, dateStyleLength, color, fileDateFormat);
                    lines.add(currentLine);

                } else {
                    currentLine = null;
                }

            } else {
                if (currentLine != null) {
                    currentLine.append(line, dateStyleLength);
                }
            }
        }

        bufferedReader.close();
        LOGGER.info("Finished processing " + fileParserConfig.getFilePath());
    }

    public Collection<Line> getLines() {
        return lines;
    }

    private long parseTimeLimit(String timeAsString, long defaultValue, SimpleDateFormat dateFormat) throws ParseException {
        if (StringUtils.isBlank(timeAsString)) {
            return defaultValue;
        }

        return dateFormat.parse(timeAsString).getTime();
    }

    private long getUNIXTime(String line, int dateSize, SimpleDateFormat dateFormat) throws ParseException {
        final String date = line.substring(0, dateSize);
        return dateFormat.parse(date).getTime();
    }

    private String extractRegexHeadRuleFromText(String text) {
        final StringBuilder builder = new StringBuilder("^");
        final String anyCharMatch = "[a-zA-Z0-9]";

        for (Character character : text.toCharArray()) {
            if (Character.isLetter(character) || Character.isDigit(character)) {
                builder.append(anyCharMatch);
            } else {
                if (character.equals('.') || character.equals('-')) {
                    builder.append("\\");
                }
                builder.append(character);
            }
        }
        builder.append(".*");
        return builder.toString();
    }

    private boolean greater(long value, long limit) {
        return value >= limit;
    }

    private boolean lower(long value, long limit) {
        return value <= limit;
    }

    private Line extractLineFromString(String line, int dateSize, String color, SimpleDateFormat dateFormat) throws ParseException {
        final long time = getUNIXTime(line, dateSize, dateFormat);
        String lineContent = line.substring(dateSize + 1);
        return new Line(lineContent, time, color);
    }
}
