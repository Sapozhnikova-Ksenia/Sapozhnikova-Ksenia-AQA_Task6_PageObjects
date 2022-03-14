package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DTransferPage {
    private final SelenideElement fieldAmount = $("[data-test-id=amount] input");
    private final SelenideElement fieldFrom = $("[data-test-id=from] input");
    private final SelenideElement fieldTo = $("[data-test-id=to] input");
    private final SelenideElement buttonAction = $("[data-test-id=action-transfer]");

    public DTransferPage() {
        SelenideElement headingTransferPage = $("[data-test-id=dashboard]+[class*='heading']");
        headingTransferPage.shouldBe(visible, Duration.ofSeconds(5));
        headingTransferPage.shouldBe(Condition.text("Пополнение карты"));
    }

    public void setAmount(int amount) {
        fieldAmount.clear();
        fieldAmount.setValue(String.valueOf(amount));
    }

    public void setFromCardNumber(String cardNumber) {
        fieldFrom.setValue(cardNumber);
    }

    public void doTransfer() {
        fieldTo.shouldBe(disabled);
        buttonAction.click();
    }
}
