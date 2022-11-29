package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFromFirstToSecond () {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstBeforeTransfer = dashboardPage.getCardBalance(0);
        int balanceSecondBeforeTransfer = dashboardPage.getCardBalance(1);
        var moneyTransferPage = dashboardPage.cardReplenishment(1);
        int amount = 5000;
        moneyTransferPage.moneyTransfer(amount, DataHelper.getFirstCardInfo().getCardNumber());
        Assertions.assertEquals(balanceFirstBeforeTransfer - amount, dashboardPage.getCardBalance(0));
        Assertions.assertEquals(balanceSecondBeforeTransfer + amount, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst () {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstCard = dashboardPage.getCardBalance(0);
        int balanceSecondCard = dashboardPage.getCardBalance(1);
        var moneyTransferPage = dashboardPage.cardReplenishment(0);
        int amount = 10000;
        moneyTransferPage.moneyTransfer(amount, DataHelper.getSecondCardInfo().getCardNumber());
        Assertions.assertEquals(balanceFirstCard + amount, dashboardPage.getCardBalance(0));
        Assertions.assertEquals(balanceSecondCard - amount, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldTransferNullMoney () {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstCard = dashboardPage.getCardBalance(0);
        int balanceSecondCard = dashboardPage.getCardBalance(1);
        var moneyTransferPage = dashboardPage.cardReplenishment(0);
        int amount = 0;
        moneyTransferPage.moneyTransfer(amount, DataHelper.getSecondCardInfo().getCardNumber());
        Assertions.assertEquals(balanceFirstCard + amount, dashboardPage.getCardBalance(0));
        Assertions.assertEquals(balanceSecondCard - amount, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldNotTransferIFNotEnoughMoney () {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstCard = dashboardPage.getCardBalance(0);
        int balanceSecondCard = dashboardPage.getCardBalance(1);
        var moneyTransferPage = dashboardPage.cardReplenishment(0);
        int amount = 100000;
        moneyTransferPage.moneyTransfer(balanceSecondCard + amount, DataHelper.getSecondCardInfo().getCardNumber());
        moneyTransferPage.errorAlert();
        Assertions.assertEquals(balanceFirstCard, dashboardPage.getCardBalance(0));
        Assertions.assertEquals(balanceSecondCard, dashboardPage.getCardBalance(1));
    }

}

