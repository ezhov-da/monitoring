package ru.ezhov.monitor.fileTreatment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.ezhov.monitor.processor.FileMoverException;
import ru.ezhov.monitor.config.AppConfigInstance;

import java.io.File;

public class FileMoverExceptionTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public final void move() throws Exception {
        final String folderExceptionFile = AppConfigInstance
                .getConfig().folderExceptionFile();
        final int numberOfAttempt = 5;

        final File fileSrc = this.temporaryFolder.newFile("1.json");
        final File folderDist = this.temporaryFolder
                .newFolder(folderExceptionFile);
        final File fileDist = this.temporaryFolder
                .newFile(folderExceptionFile + "/1.json");
        final FileMoverException fileMoverException = new FileMoverException();
        fileMoverException.move(fileSrc, fileDist, numberOfAttempt);
    }

}