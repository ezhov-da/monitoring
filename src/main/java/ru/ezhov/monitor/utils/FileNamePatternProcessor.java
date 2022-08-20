package ru.ezhov.monitor.utils;

import ru.ezhov.monitor.config.AppConfig;
import ru.ezhov.monitor.config.AppConfigInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNamePatternProcessor {
    private int countPartName;
    private AppConfig appConfig;
    private final String nameFile;

    public FileNamePatternProcessor(String nameFile) {
        this.nameFile = nameFile;
        this.appConfig = AppConfigInstance.getConfig();
        this.countPartName = 2;
    }

    public FileJsonName process() throws ParseException, IllegalArgumentException {
        final String[] names = this.nameFile.split(this.appConfig.delimiterPattern());

        if (names.length != this.countPartName) {
            throw getException();
        }

        final Date date = getDate(names[0]);
        final String ip = getIp(names[1]);

        return new FileJsonName(date, ip);
    }

    private RuntimeException getException() {
        return new IllegalArgumentException(
                "[" + this.nameFile
                        + "] "
                        + "- Not correct name file for treatment. Use "
                        + this.appConfig.patternDtFile()
                        + this.appConfig.delimiterPattern()
                        + String.format("%s.%s.%s.%s", "ip", "ip", "ip", "ip")
                        + " template.");
    }

    private Date getDate(String partDate) throws ParseException {
        return new SimpleDateFormat(this.appConfig.patternDtFile()).parse(partDate);
    }

    private String getIp(final String partIp) {
        //сначала смотрим корректность ip с точкой от расширения файла
        Pattern pattern = Pattern.compile(this.appConfig.regExpCheckIp());
        Matcher matcher = pattern.matcher(partIp);

        if (!matcher.find()) throw getException(); {}

        //затем берем только ip
        pattern = Pattern.compile(this.appConfig.regExpOutIp());
        matcher = pattern.matcher(partIp);

        if (!matcher.find()) throw getException(); {}

        return matcher.group();
    }

}
