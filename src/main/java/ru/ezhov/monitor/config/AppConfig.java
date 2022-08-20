package ru.ezhov.monitor.config;

import org.aeonbits.owner.Config;

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

    String delimiterPattern();

    String regExpCheckIp();

    String regExpOutIp();

}
