package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id='login'] input");
    private final SelenideElement passwordField = $("[data-test-id='password'] input");
    private final SelenideElement actionLogin = $("[data-test-id='action-login']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        invalidUser(authInfo);
        return new VerificationPage();
    }

    public void invalidUser(DataHelper.AuthInfo authInfo) {
        loginField.setValue(authInfo.getLogin());
        passwordField.setValue(authInfo.getPassword());
        actionLogin.click();
    }

    public void error(String expectedText) {
        errorNotification.shouldHave(Condition.exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);

    }
}