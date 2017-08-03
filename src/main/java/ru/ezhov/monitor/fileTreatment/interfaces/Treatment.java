package ru.ezhov.monitor.fileTreatment.interfaces;

/**
 * Обработчик
 *
 * @param <T> - обрабатываемый объект
 */
public interface Treatment<T> {

    /**
     * Обработка объекта
     *
     * @param treatmentObject - объект
     */
    void treatment(T treatmentObject);
}
