package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.cssSelector;

public class FormTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequestValidUser() {
        UserRegistration user = DataGenerator.generateValidActiveUser();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=login] input")).sendKeys(user.getLogin());
        form.$(cssSelector("[data-test-id=password] input")).sendKeys(user.getPassword());
        form.$(cssSelector("[data-test-id=action-login] ")).click();
        $(byText("Личный кабинет")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotSubmitRequestBlockedUser() {
        UserRegistration user = DataGenerator.generateValidBlockedUser();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=login] input")).sendKeys(user.getLogin());
        form.$(cssSelector("[data-test-id=password] input")).sendKeys(user.getPassword());
        form.$(cssSelector("[data-test-id=action-login] ")).click();
        $(byText("Ошибка")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotSubmitRequestInvalidLogin() {
        UserRegistration user = DataGenerator.generateUserInvalidLogin();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=login] input")).sendKeys(user.getLogin());
        form.$(cssSelector("[data-test-id=password] input")).sendKeys(user.getPassword());
        form.$(cssSelector("[data-test-id=action-login]")).click();
        $(byText("Ошибка")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotSubmitRequestInvalidPassword() {
        UserRegistration user = DataGenerator.generateUserInvalidPassword();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=login] input")).sendKeys(user.getLogin());
        form.$(cssSelector("[data-test-id=password] input")).sendKeys(user.getPassword());
        form.$(cssSelector("[data-test-id=action-login] ")).click();
        $(byText("Ошибка")).waitUntil(Condition.visible, 15000);
    }
}


