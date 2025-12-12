package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static api.AppConfig.MAIN_PAGE;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final String ACTIVE_TAB_CLASS = "tab_tab_type_current";
    private final By constructorTitle = By.xpath("//h1[contains(text(), 'Соберите')]");
    private final By bunsTabDiv = By.xpath("//span[text()='Булки']/parent::div");
    private final By saucesTabDiv = By.xpath("//span[text()='Соусы']/parent::div");
    private final By fillingsTabDiv = By.xpath("//span[text()='Начинки']/parent::div");
    private final By saucesContent = By.xpath("//section[contains(@class, 'sauces')] | //div[contains(@class, 'sauces')] | //h2[text()='Соусы']");
    private final By fillingsContent = By.xpath("//section[contains(@class, 'fillings')] | //div[contains(@class, 'fillings')] | //h2[text()='Начинки']");
    private final By bunsContent = By.xpath("//section[contains(@class, 'buns')] | //div[contains(@class, 'buns')] | //h2[text()='Булки']");
    private final By saucesList = By.xpath("//*[contains(@class, 'sauce') or contains(@class, 'ingredient')]");
    private final By fillingsList = By.xpath("//*[contains(@class, 'filling') or contains(@class, 'ingredient')]");
    private final By bunsList = By.xpath("//*[contains(@class, 'bun') or contains(@class, 'ingredient')]");
    private final By personalAccountButton = By.xpath("//p[contains(@class, 'AppHeader_header__linkText__3q_va') and contains(text(), 'Личный Кабинет')]");
    private final By loginAccountButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By createOrderButton = By.xpath("//button[text()='Оформить заказ' or contains(text(), 'order')]");
    private final By constructorTab = By.xpath("//p[contains(text(), 'Конструктор')]");
    private final By stellarBurgersLogo = By.xpath("//a[contains(@class, 'logo') or contains(@href, '/')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Step("Открытие главной страницы")
    public void open() {
        System.out.println("Opening URL: " + MAIN_PAGE);
        driver.get(MAIN_PAGE);
        waitForPageLoad();
    }

    public void clickStellarBurgersLogo() {

        driver.findElement(stellarBurgersLogo).click();
    }


    @Step("Ожидание загрузки главной страницы")
    public void waitForPageLoad() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(constructorTitle));
            System.out.println("Страница загрузилась, заголовок найден");
        } catch (Exception e) {
            System.err.println("Ошибка загрузки страницы: " + e.getMessage());
            throw e;
        }
    }

    @Step("Нажатие кнопки 'Войти в аккаунт'")
    public void clickAccountButton() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginAccountButton));
            button.click();
            System.out.println("Нажата кнопка 'Войти в аккаунт'");
        } catch (Exception e) {
            System.err.println("Ошибка при нажатии кнопки 'Войти в аккаунт': " + e.getMessage());
            throw e;
        }
    }

    @Step("Нажатие кнопки 'Личный кабинет'")
    public void clickPersonalAccountButton() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton));
            button.click();
            System.out.println("Нажата кнопка 'Личный кабинет'");
        } catch (Exception e) {
            System.err.println("Ошибка при нажатии кнопки 'Личный кабинет': " + e.getMessage());
            throw e;
        }
    }

    @Step("Нажатие на вкладку 'Конструктор'")
    public void clickConstructorTab() {
        try {
            WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(constructorTab));
            tab.click();
            System.out.println("Нажата вкладка 'Конструктор'");
        } catch (Exception e) {
            System.err.println("Ошибка при нажатии вкладки 'Конструктор': " + e.getMessage());
        }
    }


    @Step("Клик на вкладку Булки")
    public void clickBunsTab() {
        clickTabDiv(bunsTabDiv, "Булки");
    }

    @Step("Клик на вкладку Соусы")
    public void clickSaucesTab() {
        clickTabDiv(saucesTabDiv, "Соусы");
    }

    @Step("Клик на вкладку Начинки")
    public void clickFillingsTab() {
        clickTabDiv(fillingsTabDiv, "Начинки");
    }

    private void clickTabDiv(By tabDivLocator, String tabName) {
        try {
            WebElement tabDiv = wait.until(ExpectedConditions.elementToBeClickable(tabDivLocator));

            // Проверяем структуру элемента
            System.out.println("Найден элемент для клика: " + tabDiv.getTagName() +
                    ", классы: " + tabDiv.getAttribute("class"));

            // Используем Actions для более надежного клика
            Actions actions = new Actions(driver);
            actions.moveToElement(tabDiv).click().perform();

            System.out.println("Клик выполнен на " + tabName);

            // Ждем обновления состояния
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Игнорируем
            }

        } catch (Exception e) {
            System.err.println("Ошибка при клике на " + tabName + ": " + e.getMessage());

            // Пробуем альтернативный способ - JavaScript клик
            try {
                WebElement tabDiv = driver.findElement(tabDivLocator);
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", tabDiv);
                System.out.println("Клик выполнен через JavaScript на " + tabName);
            } catch (Exception jsException) {
                System.err.println("JavaScript клик также не удался: " + jsException.getMessage());
                throw e;
            }
        }
    }


    @Step("Проверка активности раздела Булки")
    public boolean isBunsActive() {
        return isTabActive(bunsTabDiv, "Булки");
    }

    @Step("Проверка активности раздела Соусы")
    public boolean isSaucesActive() {
        return isTabActive(saucesTabDiv, "Соусы");
    }

    @Step("Проверка активности раздела Начинки")
    public boolean isFillingsActive() {
        return isTabActive(fillingsTabDiv, "Начинки");
    }

    private boolean isTabActive(By tabDivLocator, String tabName) {
        try {
            WebElement tabDiv = wait.until(ExpectedConditions.presenceOfElementLocated(tabDivLocator));
            String classes = tabDiv.getAttribute("class");

            System.out.println("Проверка активности " + tabName + ":");
            System.out.println("  Классы div: " + classes);
            System.out.println("  Ищем класс: " + ACTIVE_TAB_CLASS);

            // Проверяем наличие активного класса в родительском div
            boolean isActive = classes.contains(ACTIVE_TAB_CLASS);
            System.out.println("  Активен? " + isActive);

            return isActive;

        } catch (Exception e) {
            System.err.println("Ошибка при проверке активности " + tabName + ": " + e.getMessage());
            return false;
        }
    }


    @Step("Проверка отображения раздела Соусы")
    public boolean isSaucesContentDisplayed() {
        return isContentDisplayed(saucesContent, saucesList, "Соусы");
    }

    @Step("Проверка отображения раздела Начинки")
    public boolean isFillingsContentDisplayed() {
        return isContentDisplayed(fillingsContent, fillingsList, "Начинки");
    }

    @Step("Проверка отображения раздела Булки")
    public boolean isBunsContentDisplayed() {
        return isContentDisplayed(bunsContent, bunsList, "Булки");
    }

    private boolean isContentDisplayed(By titleLocator, By itemsLocator, String sectionName) {
        try {
            // 1. Пробуем найти заголовок раздела
            List<WebElement> titles = driver.findElements(titleLocator);
            boolean titleVisible = !titles.isEmpty() && titles.get(0).isDisplayed();

            // 2. Проверяем, что есть элементы в разделе
            List<WebElement> items = driver.findElements(itemsLocator);
            boolean hasItems = !items.isEmpty();

            // 3. Проверяем, что элементы видимы (хотя бы первый)
            boolean itemsVisible = false;
            if (hasItems) {
                try {
                    itemsVisible = wait.until(ExpectedConditions.visibilityOf(items.get(0))).isDisplayed();
                } catch (Exception e) {
                    itemsVisible = items.get(0).isDisplayed();
                }
            }

            System.out.println(sectionName + " контент:");
            System.out.println("  Заголовок найден: " + !titles.isEmpty());
            System.out.println("  Заголовок виден: " + titleVisible);
            System.out.println("  Найдено элементов: " + items.size());
            System.out.println("  Элементы видимы: " + itemsVisible);

            // Если не нашли специфичные элементы, используем альтернативную проверку
            if (!hasItems) {
                return isCorrectSectionDisplayed(sectionName);
            }

            return titleVisible && hasItems && itemsVisible;

        } catch (Exception e) {
            System.err.println("Ошибка проверки контента " + sectionName + ": " + e.getMessage());
            return isCorrectSectionDisplayed(sectionName);
        }
    }

    @Step("Получение количества отображаемых соусов")
    public int getVisibleSaucesCount() {
        return getVisibleItemsCount(saucesList, "Соусы");
    }

    @Step("Получение количества отображаемых начинок")
    public int getVisibleFillingsCount() {
        return getVisibleItemsCount(fillingsList, "Начинки");
    }

    @Step("Получение количества отображаемых булок")
    public int getVisibleBunsCount() {
        return getVisibleItemsCount(bunsList, "Булки");
    }

    private int getVisibleItemsCount(By itemsLocator, String sectionName) {
        try {
            List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(itemsLocator));

            // Считаем только видимые элементы
            int visibleCount = 0;
            for (WebElement item : items) {
                if (item.isDisplayed()) {
                    visibleCount++;
                }
            }

            System.out.println(sectionName + ": найдено " + items.size() + " элементов, видимо " + visibleCount);
            return visibleCount;

        } catch (Exception e) {
            System.err.println("Ошибка подсчета элементов " + sectionName + ": " + e.getMessage());
            return 0;
        }
    }


    @Step("Проверка, что пользователь авторизован")
    public boolean isUserLoggedIn() {
        try {
            //Проверяем кнопку "Оформить заказ" (она появляется после входа)
            wait.until(ExpectedConditions.visibilityOfElementLocated(createOrderButton));
            WebElement orderBtn = driver.findElement(createOrderButton);
            if (orderBtn.isDisplayed()) {
                System.out.println("Пользователь авторизован: кнопка 'Оформить заказ' видна");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Кнопка 'Оформить заказ' не найдена, пробуем другие способы проверки...");
        }


        System.out.println("Пользователь не авторизован");
        return false;
    }


    @Step("Проверка видимости заголовка конструктора")
    public boolean isTitleVisible() {
        try {
            return driver.findElement(constructorTitle).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка, что отображается правильный раздел (альтернативный метод)")
    public boolean isCorrectSectionDisplayed(String expectedSection) {
        try {
            // Получаем весь видимый текст на странице
            String pageText = driver.findElement(By.tagName("body")).getText();

            // Простая проверка по ключевым словам
            switch (expectedSection) {
                case "Соусы":
                    // Проверяем, что есть слова связанные с соусами и нет других разделов
                    boolean hasSauces = pageText.contains("Соус") || pageText.contains("соус");
                    boolean noBuns = !pageText.contains("Бул") && !pageText.contains("бул");
                    boolean noFillings = !pageText.contains("Начин") && !pageText.contains("начин");
                    return hasSauces && (noBuns || noFillings);

                case "Начинки":
                    boolean hasFillings = pageText.contains("Начин") || pageText.contains("начин");
                    boolean noBuns2 = !pageText.contains("Бул") && !pageText.contains("бул");
                    boolean noSauces2 = !pageText.contains("Соус") && !pageText.contains("соус");
                    return hasFillings && (noBuns2 || noSauces2);

                case "Булки":
                    boolean hasBuns = pageText.contains("Бул") || pageText.contains("бул");
                    boolean noSauces3 = !pageText.contains("Соус") && !pageText.contains("соус");
                    boolean noFillings3 = !pageText.contains("Начин") && !pageText.contains("начин");
                    return hasBuns && (noSauces3 || noFillings3);

                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


}