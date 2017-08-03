package ru.ezhov.monitor.fileTreatment;

import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;
import ru.ezhov.monitor.utils.AppConfigInstance;
import ru.ezhov.monitor.utils.ErrorFolderCreator;

import java.util.Timer;
/**
 * Объект с информацией
 * @author ezhov_da
 */
public class AppFileMonitorRunner {
    private String folder;

    private Treatment<Runnable> fileTreatment;

    public AppFileMonitorRunner(
            final String folder,
            final Treatment<Runnable> fileTreatment) {
        this.fileTreatment = fileTreatment;
        this.folder = folder;
    }

    public final void runApp() {
        this.createFoldersExceptionAndError();
        this.strartOldFilesTreatment();
        this.startTimerTreatmentFiles();
        this.startMonitorFiles();
    }

    protected void createFoldersExceptionAndError() {
        final ErrorFolderCreator errorFolderCreator = new ErrorFolderCreator(this.folder);
        errorFolderCreator.checkAndCreateFolderExceptionFiles();
        errorFolderCreator.checkAndCreateFolderErrorFiles();
    }

    protected void strartOldFilesTreatment() {
        final FileOldTreatment fileOldTreatment = new FileOldTreatment();
        fileOldTreatment.treatment(this.folder);
    }

    protected void startTimerTreatmentFiles() {
        final Timer timer = new Timer();
        timer.schedule(
                new FileRepeatedTreatment(this.folder, this.fileTreatment),
                0,
                AppConfigInstance.getConfig().timeMillisecondsCheckErrorFiles());
    }

    protected void startMonitorFiles() {
        final Thread thread = new Thread(new FileMonitorIml(
                this.folder,
                this.fileTreatment));
        thread.start();
    }
}
