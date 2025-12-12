package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static api.AppConfig.REGISTER_PAGE;


public class RegistrationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By errorMessage = By.xpath("//p[@class='input__error text_type_main-default']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открытие страницы регистрации")
    public void open() {
        driver.get(REGISTER_PAGE);
        waitForPageLoad();
    }


    @Step("Вводим имя в поле 'Имя'")
    public void enterName(String name) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
            element.clear();
            element.sendKeys(name);
            System.out.println("Введено имя: " + name);
        } catch (Exception e) {
            System.out.println("Ошибка при вводе имени: " + e.getMessage());
            throw e; // или throw new RuntimeException(e);
        }
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
            throw e; // или throw new RuntimeException(e);
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
            throw e;
        }
    }

    @Step("Нажимаем кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
            element.click();
            System.out.println("Нажата кнопка 'Зарегистрироваться'");
        } catch (Exception e) {
            System.out.println("Ошибка при нажатии кнопки регистрации: " + e.getMessage());
            throw e;
        }
    }


    @Step("Заполнение формы регистрации")
    public void register(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegisterButton();
    }

    @Step("Проверяем, что страница регистрации загружена")
    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("register"));
            boolean isButtonVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(registerButton)).isDisplayed();
            System.out.println("RegistrationPage.isPageLoaded() - кнопка регистрации видима: " + isButtonVisible);
            return isButtonVisible;
        } catch (Exception e) {
            System.out.println("RegistrationPage.isPageLoaded() - ошибка: " + e.getMessage());
            return false;
        }
    }


    @Step("Ожидает загрузки страницы регистрации")
    public void waitForPageLoad() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(registerButton));
            System.out.println("Страница регистрации загружена");
        } catch (Exception e) {
            System.out.println("Ошибка при ожидании загрузки страницы регистрации: " + e.getMessage());
        }
    }


    public boolean isErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


}