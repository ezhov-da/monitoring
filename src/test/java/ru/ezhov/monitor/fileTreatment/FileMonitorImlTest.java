package ru.ezhov.monitor.fileTreatment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;
import ru.ezhov.monitor.utils.AppConfigInstance;

import java.io.File;

public class FileMonitorImlTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public final void run() throws Exception {

        final String nameTextFile = "test.json";

        final File folderWait = temporaryFolder.newFolder(AppConfigInstance
                .getConfig().folderExceptionFile());

        final Treatment<Runnable> treatment = new Treatment<Runnable>() {
            @Override
            public void treatment(Runnable treatmentObject) {
                System.out.println("test");
            }
        };

        final FileMonitorIml fileMonitorIml = new FileMonitorIml(
                folderWait.getAbsolutePath(), treatment);
        final Thread thread = new Thread(fileMonitorIml);
        thread.start();

        final File fileNew = temporaryFolder.newFile(
                folderWait.getName()
                        + File.separator
                        + nameTextFile);

        fileMonitorIml.stop();
    }

}