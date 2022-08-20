package ru.ezhov.monitor.processor;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import ru.ezhov.monitor.beans.DataJsonObjectMonitor;
import ru.ezhov.monitor.config.AppConfig;
import ru.ezhov.monitor.config.AppConfigInstance;
import ru.ezhov.monitor.processor.interfaces.FileMover;
import ru.ezhov.monitor.utils.FileJsonName;
import ru.ezhov.monitor.utils.FileNamePatternProcessor;
import ru.ezhov.monitor.utils.PathConstructor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;

public class FileProcessorRunnable implements Runnable {

    private static final Logger LOG = Logger.getLogger(FileProcessor.class.getName());

    private final Path processingObject;

    private AppConfig appConfig;
    private final FileMover fileMover;

    public FileProcessorRunnable(Path processingObject, FileMover fileMover) {
        this.processingObject = processingObject;
        appConfig = AppConfigInstance.getConfig();
        this.fileMover = fileMover;
    }

    @Override
    public final void run() {
        LOG.info("execute path: " + this.processingObject);

        final File file = processingObject.toFile();

        try {
            final String dataObject = new String(Files.readAllBytes(file.toPath()));

            if ("".equals(dataObject)) {
                LOG.error("file: "
                        + file.getAbsolutePath()
                        + " is empty and not be process");

                moveFileOnException(file);
                return;
            }

            LOG.info("read textFrom file:" + dataObject);

            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            final DataJsonObjectMonitor dataJsonObjectMonitor =
                    mapper.readValue(dataObject, DataJsonObjectMonitor.class);

            final String nameFile = file.getName();
            final FileNamePatternProcessor fileNamePatternProcessor
                    = new FileNamePatternProcessor(nameFile);
            final FileJsonName fileJsonName = fileNamePatternProcessor.process();

            LOG.info("from file: " + fileJsonName + "\n"
                    + "\tread object: " + dataJsonObjectMonitor);

            if (file.delete()) {
                LOG.info("delete file: " + file);
            } else {
                LOG.error("not delete file: " + file);
            }
        } catch (ParseException | IllegalArgumentException ex) {
            LOG.error("error executed file, because bad name file as : "
                    + file.getAbsolutePath(), ex);
            moveFileOnException(file);
        } catch (Exception ex) {
            LOG.error("error executed file and try process file: "
                    + file.getAbsolutePath(), ex);
            moveFileOnException(file);
        }
    }

    private void moveFileOnException(final File file) {
        LOG.info("try process");

        final File newFile =
                new File(
                        new PathConstructor(file.getParentFile()).constructExceptionPathFolder(),
                        file.getName()
                );
        try {
            this.fileMover.move(file, newFile, this.appConfig.attemptsCount());
        } catch (Exception e) {
            LOG.fatal("Don't process file ["
                    + file.getAbsolutePath()
                    + "] to "
                    + this.appConfig.folderExceptionFile()
                    + " folder", e);
        }
    }
}
