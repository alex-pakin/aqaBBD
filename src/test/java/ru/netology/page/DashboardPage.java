package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id='dashboard']");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceOnStart = "баланс: ";
    private final String balanceOnFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceOnStart);
        val finish = text.indexOf(balanceOnFinish);
        val value = text.substring(start + balanceOnStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int getCardBalance(int id) {
        val text = cards.get(id).text();
        return extractBalance(text);
    }

    public MoneyTransferPage cardReplenishment(int id) {
        cards.get(id).$(byText("Пополнить")).click();
        return new MoneyTransferPage();
    }
}
