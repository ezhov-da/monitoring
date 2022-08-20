package ru.ezhov.monitor.processor;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.processor.interfaces.FileMover;

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

        this.moveWithAttempts(1);
    }

    private void moveWithAttempts(final int currentAttempts) throws Exception {
        if (currentAttempts < this.countAttempt) {
            int nextAttempt = currentAttempts + 1;
            try {
                LOG.info(
                        "try treatment file: ["
                                + this.src.getAbsolutePath()
                                + "] to ["
                                + this.dist.getAbsolutePath()
                                + "] attempt № "
                                + currentAttempts);

                Files.move(src.toPath(), dist.toPath(), REPLACE_EXISTING);

                LOG.info(
                        "treatment file complete: ["
                                + this.src.getAbsolutePath()
                                + "] to ["
                                + this.dist.getAbsolutePath()
                                + "] attempt № "
                                + currentAttempts);

                return;

            } catch (IOException e) {
                LOG.error("error treatment file: ["
                                + this.src.getAbsolutePath()
                                + "] attempt № "
                                + currentAttempts,
                        e
                );
                this.moveWithAttempts(nextAttempt);
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
