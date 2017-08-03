package ru.ezhov.monitor.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppConfigInstanceTest {
    @Test
    public void getConfig() throws Exception {
        final AppConfig appConfig = AppConfigInstance.getConfig();
        assertEquals(3, appConfig .attemptsCount());
    }

}