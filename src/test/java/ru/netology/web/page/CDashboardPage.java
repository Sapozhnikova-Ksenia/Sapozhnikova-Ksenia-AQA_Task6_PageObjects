package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CDashboardPage {

    private final SelenideElement headingDashboard = $("[data-test-id=dashboard]+[class*='heading']");
    private static final ElementsCollection cardList = $$(".list .list__item");
    private final SelenideElement buttonActionReload = $("[data-test-id=action-reload]");

    public CDashboardPage() {
        headingDashboard.shouldBe(visible, Duration.ofSeconds(5));
        headingDashboard.shouldBe(Condition.text("Ваши карты"));
    }

    public DTransferPage openTransferForm(String cardNumber) {
        String lastFourDigit = cardNumber.substring(cardNumber.length() - 4);
        cardList.findBy(Condition.text(lastFourDigit)).$(withText("Пополнить")).click();
        return new DTransferPage();
    }

    public void updateBalance() {
        headingDashboard.shouldBe(visible, Duration.ofSeconds(5));
        headingDashboard.shouldBe(Condition.text("Ваши карты"));
        buttonActionReload.click();
    }

    public int getBalance(String cardNumber) {
        String lastFourDigit = cardNumber.substring(cardNumber.length() - 4);
        String[] cardInfo = cardList.findBy(Condition.text(lastFourDigit)).getText().split(" ");
        return Integer.parseInt(cardInfo[5]);
    }
}