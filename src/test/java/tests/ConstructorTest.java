package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import utils.BrowserManager;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Раздел Конструктор")
class ConstructorTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    @DisplayName("Настройка тестового окружения")
    void setUp() {

        driver = BrowserManager.getDriver();

        mainPage = new MainPage(driver);

    }

    @Test
    @DisplayName("Переход к Булкам после Соусов с проверкой контента")
    void switchToBunsAfterSaucesWithContentTest() {
        // Arrange
        mainPage.open();
        assertTrue(mainPage.isTitleVisible(), "Страница должна быть загружена");

        // Act - переходим на Соусы
        mainPage.clickSaucesTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        // Проверяем, что Соусы активны
        assertTrue(mainPage.isSaucesActive(), "Соусы должны быть активны");

        // Act - переходим обратно на Булки
        mainPage.clickBunsTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        // Assert - проверяем состояние
        assertAll("Проверка возврата к Букам",
                () -> assertTrue(mainPage.isBunsActive(), "Булки должны быть активны"),
                () -> assertFalse(mainPage.isSaucesActive(), "Соусы должны быть неактивны"),
                () -> assertTrue(mainPage.isBunsContentDisplayed(), "Контент Булок должен отображаться"),
                () -> assertTrue(mainPage.getVisibleBunsCount() > 0, "Должны отображаться булки")
        );

        System.out.println("✓ Успешный переход к Букам после Соусов");
    }

    @Test
    @DisplayName("Переход к Булкам после Начинок с проверкой контента")
    @Description("Тест проверяет переход к Букам после Начинок и отображение контента")
    void switchToBunsAfterFillingsWithContentTest() {
        // Arrange
        mainPage.open();
        assertTrue(mainPage.isTitleVisible(), "Страница должна быть загружена");

        // Act - переходим на Начинки
        mainPage.clickFillingsTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        // Проверяем, что Начинки активны
        assertTrue(mainPage.isFillingsActive(), "Начинки должны быть активны");

        // Act - переходим обратно на Булки
        mainPage.clickBunsTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        // Assert - проверяем состояние
        assertAll("Проверка возврата к Букам",
                () -> assertTrue(mainPage.isBunsActive(), "Булки должны быть активны"),
                () -> assertFalse(mainPage.isFillingsActive(), "Начинки должны быть неактивны"),
                () -> assertTrue(mainPage.isBunsContentDisplayed(), "Контент Булок должен отображаться"),
                () -> assertTrue(mainPage.getVisibleBunsCount() > 0, "Должны отображаться булки")
        );

        System.out.println("✓ Успешный переход к Букам после Начинок");
    }


    @Test
    @DisplayName("Переход к Соусам после Булок с проверкой контента")
    @Description("Тест проверяет переход к Соусам после Булок и отображение контента")
    void switchToSaucesAfterBunsWithContentTest() {
        // Arrange
        mainPage.open();
        assertTrue(mainPage.isTitleVisible(), "Страница должна быть загружена");

        // Убеждаемся, что Булки активны
        if (!mainPage.isBunsActive()) {
            mainPage.clickBunsTab();
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
            }
        }
        assertTrue(mainPage.isBunsActive(), "Булки должны быть активны");

        // Act - переходим на Соусы
        mainPage.clickSaucesTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        // Assert - проверяем состояние
        assertAll("Проверка перехода к Соусам",
                () -> assertTrue(mainPage.isSaucesActive(), "Соусы должны быть активны"),
                () -> assertFalse(mainPage.isBunsActive(), "Булки должны быть неактивны"),
                () -> assertTrue(mainPage.isSaucesContentDisplayed(), "Контент Соусов должен отображаться"),
                () -> assertTrue(mainPage.getVisibleSaucesCount() > 0, "Должны отображаться соусы")
        );

        System.out.println("✓ Успешный переход к Соусам после Булок");
    }

    @Test
    @DisplayName("Переход к Соусам после Начинок с проверкой контента")
    @Description("Тест проверяет переход к Соусам после Начинок и отображение контента")
    void switchToSaucesAfterFillingsWithContentTest() {
        // Arrange
        mainPage.open();
        assertTrue(mainPage.isTitleVisible(), "Страница должна быть загружена");

        // Act - переходим на Начинки
        mainPage.clickFillingsTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        // Проверяем, что Начинки активны
        assertTrue(mainPage.isFillingsActive(), "Начинки должны быть активны");

        // Act - переходим на Соусы
        mainPage.clickSaucesTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        // Assert - проверяем состояние
        assertAll("Проверка перехода к Соусам",
                () -> assertTrue(mainPage.isSaucesActive(), "Соусы должны быть активны"),
                () -> assertFalse(mainPage.isFillingsActive(), "Начинки должны быть неактивны"),
                () -> assertTrue(mainPage.isSaucesContentDisplayed(), "Контент Соусов должен отображаться"),
                () -> assertTrue(mainPage.getVisibleSaucesCount() > 0, "Должны отображаться соусы")
        );

        System.out.println("✓ Успешный переход к Соусам после Начинок");
    }


    @Test
    @DisplayName("Переход к Начинкам после Булок с проверкой контента")
    @Description("Тест проверяет переход к Начинкам после Булок и отображение контента")
    void switchToFillingsAfterBunsWithContentTest() {
        // Arrange
        mainPage.open();
        assertTrue(mainPage.isTitleVisible(), "Страница должна быть загружена");

        // Убеждаемся, что Булки активны
        if (!mainPage.isBunsActive()) {
            mainPage.clickBunsTab();
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
            }
        }
        assertTrue(mainPage.isBunsActive(), "Булки должны быть активны");

        // Act - переходим на Начинки
        mainPage.clickFillingsTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        // Assert - проверяем состояние
        assertAll("Проверка перехода к Начинкам",
                () -> assertTrue(mainPage.isFillingsActive(), "Начинки должны быть активны"),
                () -> assertFalse(mainPage.isBunsActive(), "Булки должны быть неактивны"),
                () -> assertTrue(mainPage.isFillingsContentDisplayed(), "Контент Начинок должен отображаться"),
                () -> assertTrue(mainPage.getVisibleFillingsCount() > 0, "Должны отображаться начинки")
        );

        System.out.println("✓ Успешный переход к Начинкам после Булок");
    }

    @Test
    @DisplayName("Переход к Начинкам после Соусов с проверкой контента")
    @Description("Тест проверяет переход к Начинкам после Соусов и отображение контента")
    void switchToFillingsAfterSaucesWithContentTest() {
        // Arrange
        mainPage.open();
        assertTrue(mainPage.isTitleVisible(), "Страница должна быть загружена");

        // Act - переходим на Соусы
        mainPage.clickSaucesTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        // Проверяем, что Соусы активны
        assertTrue(mainPage.isSaucesActive(), "Соусы должны быть активны");

        // Act - переходим на Начинки
        mainPage.clickFillingsTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        // Assert - проверяем состояние
        assertAll("Проверка перехода к Начинкам",
                () -> assertTrue(mainPage.isFillingsActive(), "Начинки должны быть активны"),
                () -> assertFalse(mainPage.isSaucesActive(), "Соусы должны быть неактивны"),
                () -> assertTrue(mainPage.isFillingsContentDisplayed(), "Контент Начинок должен отображаться"),
                () -> assertTrue(mainPage.getVisibleFillingsCount() > 0, "Должны отображаться начинки")
        );

        System.out.println("✓ Успешный переход к Начинкам после Соусов");
    }


    @Test
    @DisplayName("Циклическое переключение всех табов с проверкой контента")
    @Description("Тест проверяет последовательное переключение всех табов и отображение контента")
    void cyclicSwitchingAllTabsTest() {
        // Arrange
        mainPage.open();
        assertTrue(mainPage.isTitleVisible(), "Страница должна быть загружена");

        System.out.println("=== ЦИКЛИЧЕСКОЕ ПЕРЕКЛЮЧЕНИЕ ===");

        // 1. Сохраняем начальное состояние (Булки)
        int initialBunsCount = mainPage.getVisibleBunsCount();
        assertTrue(initialBunsCount > 0, "Должны отображаться булки изначально");

        // 2. Булки → Соусы
        System.out.println("Шаг 1: Булки → Соусы");
        mainPage.clickSaucesTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        assertAll("Проверка перехода к Соусам",
                () -> assertTrue(mainPage.isSaucesActive(), "Соусы должны быть активны"),
                () -> assertTrue(mainPage.getVisibleSaucesCount() > 0, "Должны отображаться соусы")
        );

        // 3. Соусы → Начинки
        System.out.println("Шаг 2: Соусы → Начинки");
        mainPage.clickFillingsTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        assertAll("Проверка перехода к Начинкам",
                () -> assertTrue(mainPage.isFillingsActive(), "Начинки должны быть активны"),
                () -> assertTrue(mainPage.getVisibleFillingsCount() > 0, "Должны отображаться начинки")
        );

        // 4. Начинки → Булки
        System.out.println("Шаг 3: Начинки → Булки");
        mainPage.clickBunsTab();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }

        assertAll("Проверка возврата к Букам",
                () -> assertTrue(mainPage.isBunsActive(), "Булки должны быть активны"),
                () -> assertTrue(mainPage.getVisibleBunsCount() > 0, "Должны снова отображаться булки")
        );

        System.out.println("✓ Циклическое переключение выполнено успешно");
    }


    @AfterEach
    @DisplayName("Завершение теста")
    void tearDown() {

        if (driver != null) {
            driver.quit();
            System.out.println("Драйвер закрыт");
        }

    }
}