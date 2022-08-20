package ru.ezhov.monitor.processor.interfaces;

public interface Processor<T> {

    void process(T processingObject);
}
