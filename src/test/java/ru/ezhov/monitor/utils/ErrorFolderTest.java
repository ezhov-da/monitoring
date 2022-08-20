package ru.ezhov.monitor.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

public class ErrorFolderTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private String basicPath = "test";

    @Test
    public final void checkAndCreateFolderExceptionFiles() throws Exception {
        final File file = this.temporaryFolder.newFolder(this.basicPath);
        final ErrorFolder errorFolder =
                new ErrorFolder(file);
        errorFolder.checkAndCreateFolderExceptionFiles();
    }

    @Test
    public final void checkAndCreateFolderErrorFiles() throws Exception {
        final File file = this.temporaryFolder.newFolder(this.basicPath);
        final ErrorFolder errorFolder = new
                ErrorFolder(file);
        errorFolder.checkAndCreateFolderExceptionFiles();
    }

}