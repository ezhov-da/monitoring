package ru.ezhov.monitor.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.ezhov.monitor.config.AppConfigInstance;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class PathConstructorTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private String basicPath = "basic";

    @Test
    public final void constructExceptionPathFolder() throws Exception {
        final File file = this.temporaryFolder.newFolder(this.basicPath);

        final PathConstructor pathConstructor = new PathConstructor(file.getAbsolutePath());
        final String pathHolder = pathConstructor.constructExceptionPathFolder();

        final String pathToFolderException =
                file
                        .getAbsolutePath()
                        + File.separator
                        + AppConfigInstance.getConfig().folderExceptionFile();

        assertEquals("Folders for exception",
                pathToFolderException, pathHolder);
    }

    @Test
    public final void constructErrorPathFolder() throws Exception {
        final File file = this.temporaryFolder.newFolder(this.basicPath);

        final PathConstructor pathConstructor = new PathConstructor(file.getAbsolutePath());
        final String pathHolder = pathConstructor.constructErrorPathFolder();

        final String pathToFolderError =
                file
                        .getAbsolutePath()
                        + File.separator
                        + AppConfigInstance.getConfig().folderErrorFile();

        assertEquals("Folders for errors", pathToFolderError, pathHolder);

    }

}