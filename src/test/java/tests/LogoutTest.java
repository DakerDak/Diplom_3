package tests;
import api.UserApiClient;
import dto.UserRegistrationData;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import pages.LoginPage;
import pages.MainPage;

import static org.junit.jupiter.api.Assertions.*;
import utils.BrowserManager;


@DisplayName("Выход из аккаунта")
public class LogoutTest {
    private WebDriver driver;
    private MainPage mainPage;
    private UserApiClient userApiClient;
    private UserRegistrationData testUserData;
    private LoginPage loginPage;

    @BeforeEach
    @DisplayName("Настройка WebDriver и страниц")
    void setUpWebDriver() {

        System.setProperty("browser", "yandex");
        driver = BrowserManager.getDriver();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
    }

    @BeforeEach
    @DisplayName("Создание тестового пользователя через API")
    void setUpTestUser() {
        // 2. Создаем API клиент и тестового пользователя
        userApiClient = new UserApiClient();
        testUserData = userApiClient.createTestUser();

        System.out.println("Создан тестовый пользователь:");
        System.out.println("Email: " + testUserData.getEmail());
        System.out.println("Пароль: " + testUserData.getPassword());
        System.out.println("Имя: " + testUserData.getName());
    }

    @AfterEach
    @DisplayName("Завершение теста и очистка")
    void tearDown() {

        // 1. Удаляем тестового пользователя через API
        if (testUserData != null && userApiClient != null) {
            try {
                userApiClient.deleteUser(testUserData);
                System.out.println("Тестовый пользователь удален: " + testUserData.getEmail());
            } catch (Exception e) {
                System.err.println("Не удалось удалить пользователя через API: " + e.getMessage());
                // Логируем информацию для ручного удаления
                userApiClient.logUserInfo(testUserData);
                System.out.println("Пользователь останется в системе: " + testUserData.getEmail());
            }
        }

        // 2. Закрываем браузер
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Браузер закрыт");
            } catch (Exception e) {
                System.err.println("Ошибка при закрытии браузера: " + e.getMessage());
            }
        }
    }


    @Test
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете")
    public void logoutTest() {
        // 1. Открываем главную страницу
        mainPage.open();

        // 2. Нажимаем кнопку "Войти в аккаунт"
        mainPage.clickAccountButton();

        // 3. Ждем загрузки страницы входа
        assertTrue(loginPage.isPageLoaded(), "Страница входа должна загрузиться");

        // 4. Вводим данные созданного пользователя
        loginPage.enterEmail(testUserData.getEmail());
        loginPage.enterPassword(testUserData.getPassword());

        // 5. Нажимаем кнопку "Войти"
        loginPage.clickLoginButton();

        // 6. Ждем перехода на главную страницу
        mainPage.waitForPageLoad();

        assertTrue(mainPage.isUserLoggedIn());
        // 7.Нажимаем на кнопку Личный кабинет
        mainPage.clickPersonalAccountButton();
        // 7.Нажимаем на кнопку Выйти
        loginPage.clicklogoutButton();
        assertTrue(loginPage.isPageLoaded());

    }

}
