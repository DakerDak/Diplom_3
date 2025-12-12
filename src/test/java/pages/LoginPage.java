package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    ;
    private final By loginTitle = By.xpath("//h2[text()='Вход']");
    private final By registrationButton = By.xpath("//a[@class='Auth_link__1fOlj' and text()='Зарегистрироваться']");
    private final By loginOnFormRegistration = By.xpath("//a[@class='Auth_link__1fOlj' and text()='Войти']");
    private final By loginLinkForgotPassword = By.cssSelector("a.Auth_link__1fOlj");
    private final By logoutButton = By.xpath("//button[text()='Выход']");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Нажимаем на ссылку 'Зарегистрироваться'")
    public void clickRegisterLink() {
        driver.findElement(registerLink).click();
    }


    @Step("Нажимаем кнопку 'Зарегистрироваться'")
    public void clickRegistrationButton() {

        driver.findElement(registrationButton).click();
    }

    @Step("Нажимаем кнопку 'Выйти'")
    public void clicklogoutButton() {

        driver.findElement(logoutButton).click();
    }

    @Step("Нажимаем кнопку 'Войти'")
    public void clickloginOnFormRegistration() {

        driver.findElement(loginOnFormRegistration).click();
    }


    @Step("Нажимаем кнопку 'Восстановить пароль'")
    public void clickloginLinkForgotPassword() {

        driver.findElement(loginLinkForgotPassword).click();
    }


    @Step("Вводим email в поле 'Email'")
    public void enterEmail(String email) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
            element.clear();
            element.sendKeys(email);
            System.out.println("Введен email: " + email);
        } catch (Exception e) {
            System.out.println("Ошибка при вводе email: " + e.getMessage());

            // Дебаг: выводим HTML страницы для понимания структуры
            System.out.println("Текущий URL: " + driver.getCurrentUrl());
            System.out.println("Текущий HTML (первые 2000 символов):");
            System.out.println(driver.getPageSource().substring(0, Math.min(2000, driver.getPageSource().length())));

            throw e;
        }
    }

    @Step("Вводим пароль в поле 'Пароль'")
    public void enterPassword(String password) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
            element.clear();
            element.sendKeys(password);
            System.out.println("Введен пароль");
        } catch (Exception e) {
            System.out.println("Ошибка при вводе пароля: " + e.getMessage());
            System.out.println("Локатор: " + passwordInput);

            // Дополнительная информация для отладки
            System.out.println("Текущий URL: " + driver.getCurrentUrl());
            System.out.println("Поле найдено на странице: " +
                    driver.findElements(passwordInput).size() + " элементов");

            throw e;
        }
    }

    @Step("Нажимаем на кнопку'Войти'")
    public void clickLoginButton() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            element.click();
            System.out.println("Нажата кнопка 'Войти'");
        } catch (Exception e) {
            System.out.println("Ошибка при нажатии кнопки 'Войти': " + e.getMessage());
            throw e;
        }
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isPageLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginTitle)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


}