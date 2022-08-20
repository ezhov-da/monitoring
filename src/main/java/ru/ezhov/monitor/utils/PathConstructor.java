package ru.ezhov.monitor.utils;

import ru.ezhov.monitor.config.AppConfig;
import ru.ezhov.monitor.config.AppConfigInstance;

import java.io.File;

/**
 * Конструктор путей к папкам исключений и ошибок
 */
public class PathConstructor {

    private AppConfig appConfig;
    private final File folder;

    public PathConstructor(File folder) {
        this.folder = folder;
        this.appConfig = AppConfigInstance.getConfig();
    }

    public final File constructExceptionPathFolder() {
        return construct(this.appConfig.folderExceptionFile());
    }

    public final File constructErrorPathFolder() {
        return construct(this.appConfig.folderErrorFile());
    }

    private File construct(final String folder) {
        return new File(this.folder, folder);
    }
}
