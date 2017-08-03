package ru.ezhov.monitor.utils.jsonCreator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class FileJSonCreateRunnerTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public final void run() throws IOException {

        final File file = this.temporaryFolder.newFolder();

        final File fileFolder = new File(file.getAbsolutePath());

        final int howManyFilesCreate = 20;
        final FileJSonCreateRunner fileJSonCreateRunner =
                new FileJSonCreateRunner(howManyFilesCreate, fileFolder.getAbsolutePath());
        fileJSonCreateRunner.run();

        assertTrue(fileFolder.listFiles().length > 0);

    }

}