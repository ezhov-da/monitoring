package ru.ezhov.monitor.fileTreatment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.ezhov.monitor.processor.FileMonitorIml;
import ru.ezhov.monitor.processor.interfaces.Processor;
import ru.ezhov.monitor.config.AppConfigInstance;

import java.io.File;

public class FileMonitorImlTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public final void run() throws Exception {

        final String nameTextFile = "test.json";

        final File folderWait = temporaryFolder.newFolder(AppConfigInstance
                .getConfig().folderExceptionFile());

        final Processor<Runnable> processor = new Processor<Runnable>() {
            @Override
            public void process(Runnable treatmentObject) {
                System.out.println("test");
            }
        };

        final FileMonitorIml fileMonitorIml = new FileMonitorIml(
                folderWait.getAbsolutePath(), processor);
        final Thread thread = new Thread(fileMonitorIml);
        thread.start();

        final File fileNew = temporaryFolder.newFile(
                folderWait.getName()
                        + File.separator
                        + nameTextFile);

        fileMonitorIml.stop();
    }

}