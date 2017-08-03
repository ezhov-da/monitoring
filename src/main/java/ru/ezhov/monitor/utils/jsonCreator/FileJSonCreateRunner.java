package ru.ezhov.monitor.utils.jsonCreator;

import org.apache.log4j.Logger;

import java.io.IOException;

public class FileJSonCreateRunner {

    private static final Logger LOG = Logger
            .getLogger(FileJSonCreateRunner.class.getName());

    private final int countFiles;
    private final String pathToFolderCreate;

    public FileJSonCreateRunner(final int countFiles, final String pathToFolderCreate) {
        this.countFiles = countFiles;
        this.pathToFolderCreate = pathToFolderCreate;
    }

    public final void run() {

        LOG.info("start json creator");

        for (int i = 0; i < this.countFiles; i++) {

            try {
                new FileJsonCreator(this.pathToFolderCreate).create(i);
                LOG.info("create file №: " + (i + 1));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOG.info("stop json creator");
    }
}
