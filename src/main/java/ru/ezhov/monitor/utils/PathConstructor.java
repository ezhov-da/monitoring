package ru.ezhov.monitor.utils;

import java.io.File;

/**
 * Конструктор путей к папкам исключений и ошибок
 */
public class PathConstructor {

    private AppConfig appConfig;
    private final String basicPath;

    public PathConstructor(String basicPath) {
        this.basicPath = basicPath;
        this.appConfig = AppConfigInstance.getConfig();
    }

    public final String constructExceptionPathFolder() {
        return construct(this.appConfig.folderExceptionFile());

    }

    public final String constructErrorPathFolder() {
        return construct(this.appConfig.folderErrorFile());
    }

    private String construct(final String folder) {
        return this.basicPath + File.separator + folder;
    }
}
