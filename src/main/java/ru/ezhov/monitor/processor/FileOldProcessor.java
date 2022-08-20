package ru.ezhov.monitor.processor;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.config.AppConfig;
import ru.ezhov.monitor.config.AppConfigInstance;
import ru.ezhov.monitor.processor.interfaces.Processor;
import ru.ezhov.monitor.utils.PathConstructor;

import java.io.File;

/**
 * Класс, который перемещает файлы в папку исключений, если они были до
 * обработки, чтоб отдельный поток их обработал
 *
 * @author ezhov_da
 */
public class FileOldProcessor implements Processor<File> {
    private static final Logger LOG = Logger
            .getLogger(FileOldProcessor.class.getName());

    private final AppConfig appConfig;

    public FileOldProcessor() {
        this.appConfig = AppConfigInstance.getConfig();
    }

    @Override
    public final void process(final File folderTreatmentAbsolutePath) {
        LOG.info("processor start files in folder: "
                + folderTreatmentAbsolutePath
        );

        final File[] files = folderTreatmentAbsolutePath.listFiles((dir, name)
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
                        + "] don't process", e
                );
            }
        }

        LOG.info("processing stop");
    }
}