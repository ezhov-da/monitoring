package ru.ezhov.monitor.fileTreatment;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;

/**
 * Класс, который отвечает за обработку файлов, а именно внесение в БД и т.д.
 * <p>
 *
 * @author ezhov_da
 */
public class FileTreatment implements Treatment<Runnable> {
    private static final Logger LOG = Logger
            .getLogger(FileTreatment.class.getName());

    @Override
    public final void treatment(Runnable treatmentObject) {
        Thread thread = new Thread(treatmentObject);
        thread.setDaemon(true);
        thread.start();
    }
}
