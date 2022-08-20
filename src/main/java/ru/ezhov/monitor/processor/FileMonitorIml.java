package ru.ezhov.monitor.processor;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.config.AppConfig;
import ru.ezhov.monitor.config.AppConfigInstance;
import ru.ezhov.monitor.processor.interfaces.FileMonitor;
import ru.ezhov.monitor.processor.interfaces.Processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

/**
 * Класс, который следит за изменением в файловой системе
 * и запускает обработку файлов
 * <p>
 *
 * @author ezhov_da
 */
public class FileMonitorIml implements FileMonitor {
    private static final Logger LOG = Logger
            .getLogger(FileMonitorIml.class.getName());
    private File pathMonitor;
    private Processor<Runnable> processor;

    private AtomicBoolean isStopMonitor = new AtomicBoolean(false);

    private WatchKey watchKey;

    private AppConfig appConfig;

    /**
     * @param folder    - папка в которой ловятся файлы
     * @param processor - обработчик файлов
     */
    public FileMonitorIml(final File folder,
                          final Processor<Runnable> processor) {
        this.pathMonitor = folder;
        this.processor = processor;
        this.appConfig = AppConfigInstance.getConfig();
    }

    @SuppressWarnings("unchecked")
    public final void run() {
        try {
            LOG.info("start file monitor in folder: " + this.pathMonitor);
            final WatchService watcher = FileSystems.getDefault()
                    .newWatchService();
            final Path dir = this.pathMonitor.toPath();

            for (; ; ) {
                if (this.isStopMonitor.get()) {
                    break;
                }
                this.watchKey = dir.register(watcher, ENTRY_CREATE);
                for (final WatchEvent<?> event : this.watchKey.pollEvents()) {

                    final WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    final Path filename = ev.context();

                    final Path child = dir.resolve(filename);

                    if (child
                            .toFile()
                            .getName()
                            .endsWith(this.appConfig.fileExtension())) {

                        final Runnable runnable =
                                new FileProcessorRunnable(child,
                                        new FileMoverException());
                        this.processor.process(runnable);
                    } else {
                        LOG.info(
                                "the file ["
                                        + child.toFile().getAbsolutePath()
                                        + "] has no "
                                        + this.appConfig.fileExtension()
                                        + " extension"
                        );
                    }
                }

                final boolean valid = this.watchKey.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException ex) {
            LOG.error("file monitor error", ex);
        }
    }

    @Override
    public final void stop() {
        this.isStopMonitor.set(true);
        if (this.watchKey != null) {
            this.watchKey.cancel();
        }
        LOG.info("file monitor [" + this.pathMonitor + "] is stop");
    }
}