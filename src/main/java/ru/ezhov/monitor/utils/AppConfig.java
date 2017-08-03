package ru.ezhov.monitor.utils;

import org.aeonbits.owner.Config;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Конфигуратор приложения
 */

@Config.Sources("classpath:config.properties")
public interface AppConfig extends Config {

    String fileExtension();

    String folderExceptionFile();

    String folderErrorFile();

    int attemptsCount();

    long timeMillisecondsCheckErrorFiles();

    String patternDtFile();

    String delimeterPattern();

    String regExpCheckIp();

    String regExpOutIp();

}
