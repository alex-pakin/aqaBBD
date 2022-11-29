package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private SelenideElement heading1 = $("[data-test-id='dashboard']");
    private SelenideElement heading2 = $("div#root h1");
    private SelenideElement transactionAmount = $("[data-test-id='amount'] input");
    private SelenideElement transactionFrom = $("[data-test-id='from'] input");
    private SelenideElement transactionButton = $("[data-test-id='action-transfer'] span");
    private SelenideElement errorAlert = $("[data-test-id='error-notification'] .notification__content");


    public MoneyTransferPage moneyTransfer(int amount, String cardNumber) {
        transactionAmount.setValue(String.valueOf(amount));
        transactionFrom.setValue(cardNumber);
        transactionButton.click();
        return new MoneyTransferPage();
    }

    public void errorAlert() {
        errorAlert.shouldHave(exactText("Ошибка!")).shouldBe(visible);
    }
}