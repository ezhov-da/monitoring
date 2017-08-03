package ru.ezhov.monitor.fileTreatment;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;
import ru.ezhov.monitor.utils.AppConfig;
import ru.ezhov.monitor.utils.AppConfigInstance;
import ru.ezhov.monitor.utils.PathConstructor;

import java.io.File;

/**
 * Класс, который перемещает файлы в папку исключений,если они были до
 * обработки, чтоб отдельный поток их обработал
 *
 * @author ezhov_da
 */
public class FileOldTreatment implements Treatment<String> {
    private static final Logger LOG = Logger
            .getLogger(FileOldTreatment.class.getName());

    private final AppConfig appConfig;

    public FileOldTreatment() {
        this.appConfig = AppConfigInstance.getConfig();
    }

    @Override
    public final void treatment(final String folderTreatmentAbsolutePath) {
        LOG.info("treatment start files in folder: "
                + folderTreatmentAbsolutePath
        );

        final File file = new File(folderTreatmentAbsolutePath);
        final File[] files = file.listFiles((dir, name)
                -> name.endsWith(this.appConfig.fileExtension())
        );

        LOG.info("find " + files.length + " files");

        for (final File f : files) {
            final File newFile =
                    new File(
                            new PathConstructor(folderTreatmentAbsolutePath)
                                    .constructExceptionPathFolder()
                                    + File.separator
                                    + f.getName());

            final FileMoverException fileMoverException =
                    new FileMoverException();

            try {
                fileMoverException.move(f, newFile, this.appConfig.attemptsCount());
            } catch (Exception e) {
                LOG.error("File ["
                        + f.getAbsolutePath()
                        + "] don't treatment", e
                );
            }
        }

        LOG.info("treatment stop");
    }
}