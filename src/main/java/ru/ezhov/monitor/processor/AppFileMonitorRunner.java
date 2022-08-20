package ru.ezhov.monitor.processor;

import ru.ezhov.monitor.processor.interfaces.Processor;
import ru.ezhov.monitor.config.AppConfigInstance;
import ru.ezhov.monitor.utils.ErrorFolder;

import java.io.File;
import java.util.Timer;
/**
 * Объект с информацией
 * @author ezhov_da
 */
public class AppFileMonitorRunner {
    private File folder;

    private Processor<Runnable> fileProcessor;

    public AppFileMonitorRunner(
            final File folder,
            final Processor<Runnable> fileProcessor) {
        this.fileProcessor = fileProcessor;
        this.folder = folder;
    }

    public final void runApp() {
        this.createFoldersExceptionAndError();
        this.startOldFilesTreatment();
        this.startTimerTreatmentFiles();
        this.startMonitorFiles();
    }

    protected void createFoldersExceptionAndError() {
        final ErrorFolder errorFolder = new ErrorFolder(this.folder);
        errorFolder.checkAndCreateFolderExceptionFiles();
        errorFolder.checkAndCreateFolderErrorFiles();
    }

    protected void startOldFilesTreatment() {
        final FileOldProcessor fileOldTreatment = new FileOldProcessor();
        fileOldTreatment.process(this.folder);
    }

    protected void startTimerTreatmentFiles() {
        final Timer timer = new Timer();
        timer.schedule(
                new FileRepeatedProcessor(this.folder, this.fileProcessor),
                0,
                AppConfigInstance.getConfig().timeMillisecondsCheckErrorFiles());
    }

    protected void startMonitorFiles() {
        final Thread thread = new Thread(new FileMonitorIml(
                this.folder,
                this.fileProcessor));
        thread.start();
    }
}
