package ru.ezhov.monitor.beans;

import com.google.common.base.MoreObjects;
import org.apache.log4j.Logger;

public class DataJsonObjectMonitor {
    private static final Logger LOG = Logger
            .getLogger(DataJsonObjectMonitor.class.getName());

    private String ip;
    private String uptime;
    private double loadaverage;
    private String sizeHDDFree;
    private int memSize;

    public DataJsonObjectMonitor() {
    }

    public DataJsonObjectMonitor(
            final String ip,
            final String uptime,
            final double loadaverage,
            final String sizeHDDFree,
            final int memSize
    ) {
        this.ip = ip;
        this.uptime = uptime;
        this.loadaverage = loadaverage;
        this.sizeHDDFree = sizeHDDFree;
        this.memSize = memSize;
    }

    final public String getIp() {
        return this.ip;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }

    final public String getUptime() {
        return this.uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    final public double getLoadaverage() {
        return this.loadaverage;
    }

    public void setLoadaverage(double loadaverage) {
        this.loadaverage = loadaverage;
    }

    final public String getSizeHDDFree() {
        return this.sizeHDDFree;
    }

    public void setSizeHDDFree(String sizeHDDFree) {
        this.sizeHDDFree = sizeHDDFree;
    }

    final public int getMemSize() {
        return this.memSize;
    }

    public void setMemSize(int memSize) {
        this.memSize = memSize;
    }

    @Override
    final public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ip", this.ip)
                .add("uptime", this.uptime)
                .add("loadaverage", this.loadaverage)
                .add("sizeHDDFree", this.sizeHDDFree)
                .add("memSize", this.memSize).toString();
    }
}
