package tests;

import api.UserApiClient;
import dto.UserRegistrationData;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import utils.BrowserManager;


import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Вход в личный кабинет")

public class LoginUserTest {
    private WebDriver driver;
    private MainPage mainPage;
    private UserApiClient userApiClient;
    private UserRegistrationData testUserData;
    private LoginPage loginPage;

    @BeforeEach
    @DisplayName("Настройка WebDriver и страниц")
    void setUpWebDriver() {

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

        //  удаляем тестового пользователя через API
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
    @DisplayName("Вход по кнопке 'Войти в аккаунт'")
    @Description("Тест проверяет вход через кнопку 'Войти в аккаунт' на главной странице")
    public void loginAccountButtonTest() {
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

        // 7. Проверяем успешный вход
        assertTrue(mainPage.isUserLoggedIn(),
                "После ввода правильных данных пользователь должен быть авторизован");

        System.out.println("Тест успешно завершен: пользователь авторизован");
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Тест проверяет вход через кнопку 'Личный кабинет'")
    public void loginPersonalAccountButtonTest() {
        mainPage.open();

        // Нажимаем кнопку "Личный кабинет"
        mainPage.clickPersonalAccountButton();

        // Ждем загрузки страницы входа
        assertTrue(loginPage.isPageLoaded(), "Страница входа должна загрузиться");

        // Вводим данные
        loginPage.enterEmail(testUserData.getEmail());
        loginPage.enterPassword(testUserData.getPassword());
        loginPage.clickLoginButton();

        // Ждем перехода на главную
        mainPage.waitForPageLoad();

        // Проверяем успешный вход
        assertTrue(mainPage.isUserLoggedIn());

        System.out.println("Тест успешно завершен: вход через личный кабинет");
    }

    @Test
    @DisplayName("Вход через раздел Зарегистрироваться")

    public void loginRegistrationButtonTest() {
        mainPage.open();

        // Нажимаем кнопку "Личный кабинет"
        mainPage.clickPersonalAccountButton();

        // Ждем загрузки страницы входа
        assertTrue(loginPage.isPageLoaded(), "Страница входа должна загрузиться");

        loginPage.clickRegistrationButton();
        loginPage.clickloginOnFormRegistration();

        // Вводим данные
        loginPage.enterEmail(testUserData.getEmail());
        loginPage.enterPassword(testUserData.getPassword());
        loginPage.clickLoginButton();

        // Ждем перехода на главную
        mainPage.waitForPageLoad();

        // Проверяем успешный вход
        assertTrue(mainPage.isUserLoggedIn());

        System.out.println("Тест успешно завершен: вход через личный кабинет");
    }

    @Test
    @DisplayName("Вход через раздел Восстановление пароля")

    public void loginPasswordRecovery() {
        mainPage.open();

        // Нажимаем кнопку "Личный кабинет"
        mainPage.clickPersonalAccountButton();

        // Ждем загрузки страницы входа
        assertTrue(loginPage.isPageLoaded(), "Страница входа должна загрузиться");

        loginPage.clickRegistrationButton();
        loginPage.clickloginLinkForgotPassword();

        // Вводим данные
        loginPage.enterEmail(testUserData.getEmail());
        loginPage.enterPassword(testUserData.getPassword());
        loginPage.clickLoginButton();

        // Ждем перехода на главную
        mainPage.waitForPageLoad();

        // Проверяем успешный вход
        assertTrue(mainPage.isUserLoggedIn());

        System.out.println("Тест успешно завершен: вход через личный кабинет");
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на «Конструктор»")

    public void SwitchingPersonalAccountConstructor() {
        mainPage.open();

        // Нажимаем кнопку "Личный кабинет"
        mainPage.clickPersonalAccountButton();

        // Ждем загрузки страницы входа
        assertTrue(loginPage.isPageLoaded(), "Страница входа должна загрузиться");

        loginPage.clickRegistrationButton();
        loginPage.clickloginLinkForgotPassword();

        // Вводим данные
        loginPage.enterEmail(testUserData.getEmail());
        loginPage.enterPassword(testUserData.getPassword());
        loginPage.clickLoginButton();

        // Ждем перехода на главную
        mainPage.waitForPageLoad();

        // Проверяем успешный вход
        assertTrue(mainPage.isUserLoggedIn());
        mainPage.clickPersonalAccountButton();
        mainPage.clickConstructorTab();
        assertTrue(mainPage.isUserLoggedIn());

        System.out.println("Тест успешно завершен: вход через личный кабинет");
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику логотип Stellar Burgers")

    public void SwitchingPersonalAccountConstructorStellarBurgers() {
        mainPage.open();

        // Нажимаем кнопку "Личный кабинет"
        mainPage.clickPersonalAccountButton();

        // Ждем загрузки страницы входа
        assertTrue(loginPage.isPageLoaded(), "Страница входа должна загрузиться");



        // Вводим данные
        loginPage.enterEmail(testUserData.getEmail());
        loginPage.enterPassword(testUserData.getPassword());
        loginPage.clickLoginButton();

        // Ждем перехода на главную
        mainPage.waitForPageLoad();

        // Проверяем успешный вход
        assertTrue(mainPage.isUserLoggedIn());
        mainPage.clickPersonalAccountButton();
        mainPage.clickStellarBurgersLogo();
        assertTrue(mainPage.isUserLoggedIn());

        System.out.println("Тест успешно завершен: вход через личный кабинет");
    }

}