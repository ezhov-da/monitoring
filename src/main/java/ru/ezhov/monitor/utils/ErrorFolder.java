package ru.ezhov.monitor.utils;

import java.io.File;

public class ErrorFolder {
    private final File folder;

    public ErrorFolder(final File folder) {
        this.folder = folder;
    }

    public final void checkAndCreateFolderExceptionFiles() {
        checkAndCreate(new PathConstructor(this.folder)
                .constructExceptionPathFolder());
    }

    public final void checkAndCreateFolderErrorFiles() {
        checkAndCreate(new PathConstructor(this.folder)
                .constructErrorPathFolder());
    }

    private final void checkAndCreate(File checkAndCreatePathFolder) {
        if (!checkAndCreatePathFolder.exists()) {
            checkAndCreatePathFolder.mkdirs();
        }
    }
}
