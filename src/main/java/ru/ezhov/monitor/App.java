package ru.ezhov.monitor;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.AppFileMonitorRunner;
import ru.ezhov.monitor.fileTreatment.FileTreatment;

/**
 * Запуск приложения
 * <p>
 *
 * @author ezhov_da
 */
public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    /**
     * Входная точка приложения
     * @param args - массив с аргументами
     */
    public static void main(String[] args) {
        LOG.info("start app...");
        //Папка для обработки файлов
        final String folder = args[0];

        final String url = "";
        final String classDriver = "";
        final String username = "";
        final String pass = "";

        final AppFileMonitorRunner appFileMonitorRunner =
                new AppFileMonitorRunner(folder, new FileTreatment());
        appFileMonitorRunner.runApp();
    }
}
