package utils;  // Тот же пакет

import org.openqa.selenium.WebDriver;

/**
 * Управляет выбором браузера.
 * Читает настройки из системы.
 */
public class BrowserManager {

    /**
     * Получает тип браузера из настроек
     */
    public static String getBrowserType() {
        // Смотрим, есть ли параметр "browser" при запуске
        String browser = System.getProperty("browser");

        // Если параметр не указан - используем Chrome
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
            System.out.println("Браузер не указан. Используем Chrome.");
        }

        System.out.println("Запуск тестов в браузере: " + browser.toUpperCase());
        return browser.toLowerCase();  // Возвращаем "chrome" или "yandex"
    }

    /**
     * Простой метод для получения драйвера
     */
    public static WebDriver getDriver() {
        String browserType = getBrowserType();  // Получаем "chrome" или "yandex"
        return WebDriverFactory.createDriver(browserType);  // Создаем драйвер
    }
}