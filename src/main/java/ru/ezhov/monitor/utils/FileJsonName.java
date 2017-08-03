package ru.ezhov.monitor.utils;

import com.google.common.base.MoreObjects;

import java.util.Date;


/**
 * Объект, который получает данные из названия файла
 */
public class FileJsonName {
    private Date date;
    private String ip;

    public FileJsonName() {
    }

    public FileJsonName(final Date date, final String ip) {
        this.date = date;
        this.ip = ip;
    }

    public final Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public final String getIp() { return this.ip; }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("date", this.date)
                .add("ip", this.ip).toString();
    }
}
