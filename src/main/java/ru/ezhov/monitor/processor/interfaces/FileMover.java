package ru.ezhov.monitor.processor.interfaces;

import java.io.File;

/**
 * Интерфейс, который перемещает файлы
 */
public interface FileMover {
    void move(File src, File dist, int countAttempt) throws Exception;
}
