package ru.ezhov.monitor.utils.jsonCreator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class FileJsonCreatorTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public final void create() throws Exception {

       final File fileFolder = this.temporaryFolder.newFolder();

       final File file = new FileJsonCreator(fileFolder.getAbsolutePath()).create(10);

        assertTrue(fileFolder.listFiles().length > 0);

    }

}