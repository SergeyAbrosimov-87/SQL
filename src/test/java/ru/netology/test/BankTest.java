package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SqlHelper.cleanAuthCode;
import static ru.netology.data.SqlHelper.cleanDatabase;

public class BankTest {
    LoginPage loginPage;

    @AfterEach
    void tableCleanAuthCode() {
        cleanAuthCode();
    }

    @AfterAll
    static void cleanAllTables() {
        cleanDatabase();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    void sussesFullLogin() {
        var authInfo = DataHelper.getUser();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = SqlHelper.getVerifaicationCode();
        verificationPage.validCode(verificationCode);
    }

    @Test
    void invalidUser() {
        var authInfo = DataHelper.getRandomUser();
        loginPage.invalidUser(authInfo);
        loginPage.error("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void invalidAuthCode() {
        var authInfo = DataHelper.getUser();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.randomVerifai();
        verificationPage.virify(verificationCode.getCode());
        verificationPage.errorMessage("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }
}