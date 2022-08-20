package ru.ezhov.monitor.processor;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.config.AppConfig;
import ru.ezhov.monitor.config.AppConfigInstance;
import ru.ezhov.monitor.processor.interfaces.FileMover;

import java.io.File;

/**
 * Класс декоратор, который в случае невозможности повторной обработки,
 * перемещает файл в папку с ошибкой
 */
public class FileMoverError implements FileMover {

    private static final Logger LOG = Logger.getLogger(FileMoverError.class);

    private FileMover fileMover;
    private AppConfig appConfig;

    public FileMoverError(final FileMover fileMover) {
        this.fileMover = fileMover;
        this.appConfig = AppConfigInstance.getConfig();
    }

    @Override
    public final void move(final File src, final File dist, final int countAttempt) throws Exception {
        try {
            this.fileMover.move(src, dist, countAttempt);
        } catch (Exception ex) {
            LOG.error("error treatment file ["
                    + src.getAbsolutePath()
                    + "] to "
                    + this.appConfig.folderExceptionFile()
                    + " folder. Try treatment to "
                    + appConfig.folderErrorFile()
                    + " folder.");
            this.movePrivate(src, dist, countAttempt);
        }
    }

    private void movePrivate(final File src, final File dist, final int countAttempt) {
        final File fileDist = new File(
                src.getParentFile().getParentFile().getAbsolutePath()
                        + File.separator
                        + this.appConfig.folderErrorFile(),
                dist.getName()
        );

        try {
            fileMover.move(src, fileDist, countAttempt);
        } catch (Exception e) {
            LOG.fatal(
                    "Can't treatment file  to "
                            + this.appConfig.folderErrorFile()
                            + " [" + src.getAbsolutePath()
                            + "]", e
            );
        }
    }
}