package ru.ezhov.monitor.fileTreatment;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.interfaces.FileMover;
import ru.ezhov.monitor.utils.AppConfig;
import ru.ezhov.monitor.utils.AppConfigInstance;

import java.io.File;

/**
 * Класс декоратор, который в случае невозможности повторной обработки,
 * перемещает файл в папку с ошибкой
 */
public class FileMoverError implements FileMover {

    private static final Logger LOG = Logger.getLogger(FileMoverError.class);

    private File src;
    private File dist;
    private int countAttempt;
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
            this.move();
        }
    }

    private void move() {
        final File fileDist = new File(
                this.src.getParentFile().getAbsolutePath()
                        + File.separator
                        + this.appConfig.folderErrorFile()
                        + this.dist.getName()
        );

        try {
            fileMover.move(this.src, fileDist, this.countAttempt);
        } catch (Exception e) {
            LOG.fatal(
                    "Can't treatment file  to "
                            + this.appConfig.folderErrorFile()
                            + " [" + this.src.getAbsolutePath()
                            + "]", e
                    );
        }
    }
}