package ru.ezhov.monitor.fileTreatment;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;
import ru.ezhov.monitor.utils.AppConfig;
import ru.ezhov.monitor.utils.AppConfigInstance;
import ru.ezhov.monitor.utils.PathConstructor;

import java.io.File;
import java.util.TimerTask;

/**
 * Класс, который проверяет наличие файлов,
 * которые по каким либо причинам не обработались
 * и находятся в папке повтора  и либо повторно обрабатывает, либо отправляет в папку с ошибками
 * <p>
 *
 * @author ezhov_da
 */
public class FileRepeatedTreatment extends TimerTask {
    private static final Logger LOG = Logger.getLogger(FileRepeatedTreatment
            .class.getName());

    private final String folderCheck;
    private final Treatment<Runnable> pathTreatment;

    private final AppConfig appConfig;

    public FileRepeatedTreatment(final String folderCheck,
                                 final Treatment<Runnable> pathTreatment) {
        this.folderCheck = folderCheck;
        this.pathTreatment = pathTreatment;

        this.appConfig = AppConfigInstance.getConfig();
    }

    @Override
    public final void run() {
        LOG.info("start repeat task load");

        final File file = new File(new PathConstructor(folderCheck)
                .constructExceptionPathFolder());
        final File[] files = file.listFiles((dir, name)
                -> name.endsWith(appConfig.fileExtension()));

        for (File f : files) {
            LOG.info("execute loader: "
                    + f.getAbsolutePath());
            pathTreatment.treatment(
                    new FileTreatmentRunnable(
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
