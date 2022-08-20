package ru.ezhov.monitor.config;

import org.aeonbits.owner.ConfigFactory;

/**
 * Получаем конфигурацию приложения
 */
public class AppConfigInstance {
    private static AppConfig appConfig;

    public synchronized static AppConfig getConfig() {
        if (appConfig == null) {
            appConfig = ConfigFactory.create(AppConfig.class);
        }
        return appConfig;
    }
}
