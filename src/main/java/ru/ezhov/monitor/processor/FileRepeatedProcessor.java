package ru.ezhov.monitor.processor;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.config.AppConfig;
import ru.ezhov.monitor.config.AppConfigInstance;
import ru.ezhov.monitor.processor.interfaces.Processor;
import ru.ezhov.monitor.utils.PathConstructor;

import java.io.File;
import java.util.TimerTask;

/**
 * Класс, который проверяет наличие файлов,
 * которые по каким либо причинам не обработались
 * и находятся в папке повтора и либо повторно обрабатывает, либо отправляет в папку с ошибками
 * <p>
 *
 * @author ezhov_da
 */
public class FileRepeatedProcessor extends TimerTask {
    private static final Logger LOG = Logger.getLogger(FileRepeatedProcessor
            .class.getName());

    private final File folderCheck;
    private final Processor<Runnable> pathProcessor;

    private final AppConfig appConfig;

    public FileRepeatedProcessor(
            final File folder,
            final Processor<Runnable> pathProcessor
    ) {
        this.folderCheck = folder;
        this.pathProcessor = pathProcessor;

        this.appConfig = AppConfigInstance.getConfig();
    }

    @Override
    public final void run() {
        LOG.info("start repeat task load");

        final File file = new PathConstructor(folderCheck)
                .constructExceptionPathFolder();
        final File[] files = file.listFiles((dir, name)
                -> name.endsWith(appConfig.fileExtension()));

        for (File f : files) {
            LOG.info("execute loader: "
                    + f.getAbsolutePath());
            pathProcessor.process(
                    new FileProcessorRunnable(
                            f.toPath(),
                            new FileMoverError(
                                    new FileMoverException()
                            )
                    )
            );
        }
        LOG.info("end repeat task loader");
    }
}
