package ru.ezhov.monitor.utils;

import java.io.File;

public class ErrorFolderCreator {
    private final String pathBasic;

    public ErrorFolderCreator(final String pathBasic) {
        this.pathBasic = pathBasic;
    }

    public final void checkAndCreateFolderExceptionFiles() {
        checkAndCreate(new PathConstructor(this.pathBasic)
                .constructExceptionPathFolder());
    }

    public final void checkAndCreateFolderErrorFiles() {
        checkAndCreate(new PathConstructor(this.pathBasic)
                .constructErrorPathFolder());
    }

    private final void checkAndCreate(String checkAndCreatePathFolder) {
        File fileParent = new File(checkAndCreatePathFolder);
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
    }
}
