package ru.ezhov.monitor;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.processor.AppFileMonitorRunner;
import ru.ezhov.monitor.processor.FileProcessor;

import java.io.File;

public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        LOG.info("start app...");
        //Папка для обработки файлов
        final String folderAsString = args[0];
        if (folderAsString == null) {
            throw new IllegalArgumentException("Should be first argument folder and exists");
        }

        File folder = new File(folderAsString);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Should be '" + folderAsString + "' exists and directory");
        }

        final AppFileMonitorRunner appFileMonitorRunner =
                new AppFileMonitorRunner(folder, new FileProcessor());
        appFileMonitorRunner.runApp();
    }
}
