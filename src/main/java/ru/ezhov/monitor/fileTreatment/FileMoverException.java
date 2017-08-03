package ru.ezhov.monitor.fileTreatment;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.interfaces.FileMover;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Класс, который переносит файлы из основной папки обработки в папку с ошибочными файлами
 */

public class FileMoverException implements FileMover {

    private static final Logger LOG = Logger.getLogger(FileMoverException
                    .class.getName());

    private File src;
    private File dist;
    private int countAttempt;

    public final void move(final File src, final File dist, final int countAttempt) throws Exception {
        this.src = src;
        this.dist = dist;
        this.countAttempt = countAttempt;

        final int attempts = 1;
        this.moveWithAttempts(attempts);
    }

    private void moveWithAttempts(final int attemptsNow) throws Exception {
        if (attemptsNow < this.countAttempt) {
            try {
                LOG.info(
                        "try treatment file: ["
                                + this.src.getAbsolutePath()
                                + "] to ["
                                + this.dist.getAbsolutePath()
                                + "] attempt № "
                                + attemptsNow);

                Files.move(src.toPath(), dist.toPath(), REPLACE_EXISTING);

                LOG.info(
                        "treatment file complete: ["
                                + this.src.getAbsolutePath()
                                + "] to ["
                                + this.dist.getAbsolutePath()
                                + "] attempt № "
                                + attemptsNow);

                return;

            } catch (IOException e) {
                LOG.error("error treatment file: ["
                        + this.src.getAbsolutePath()
                        + "] attempt № "
                        + attemptsNow, e);
                this.moveWithAttempts(attemptsNow);
            }
        } else {
            LOG.fatal("fatal treatment file: ["
                    + this.src.getAbsolutePath()
                    + "] after attempts ");
            throw new Exception("Can't treatment file ["
                    + this.src.getAbsolutePath() + "]");
        }
    }

}
