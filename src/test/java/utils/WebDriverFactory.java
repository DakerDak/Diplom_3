package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class WebDriverFactory {

    public static WebDriver createDriver(String browserType) {
        WebDriver driver;

        String browser = browserType.toLowerCase();

        if ("yandex".equals(browser)) {
            driver = createYandexDriver();
        } else {
            driver = createChromeDriver();
        }

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        return driver;
    }

    private static ChromeDriver createChromeDriver() {
        // Chrome работает с автоматическим драйвером
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        System.out.println("Запускаем Chrome");
        return new ChromeDriver(options);
    }

    private static ChromeDriver createYandexDriver() {

        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\paskal.nk\\Diplom\\Diplom_3\\yandexdriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        String yandexPath = "C:\\Users\\paskal.nk\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";
        options.setBinary(yandexPath);

        System.out.println("Запускаем Яндекс через YandexDriver");
        return new ChromeDriver(options);
    }
}