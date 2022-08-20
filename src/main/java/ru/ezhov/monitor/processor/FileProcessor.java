package ru.ezhov.monitor.processor;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.processor.interfaces.Processor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс, который отвечает за обработку файлов
 *
 * @author ezhov_da
 */
public class FileProcessor implements Processor<Runnable> {
    private static final Logger LOG = Logger
            .getLogger(FileProcessor.class.getName());

    private ExecutorService executor = Executors.newFixedThreadPool(4);

    @Override
    public final void process(Runnable processingObject) {
        executor.execute(processingObject);
    }
}
