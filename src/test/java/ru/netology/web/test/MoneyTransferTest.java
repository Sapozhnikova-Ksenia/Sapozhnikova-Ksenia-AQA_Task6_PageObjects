package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.ALoginPage;
import ru.netology.web.page.CDashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyTransferTest {
    int amount;


    @BeforeEach
    void setup() {
        Configuration.headless = true;
    }

    public static CDashboardPage openDashboard() {
        val loginPage = open("http://localhost:9999", ALoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode();
        return verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferMoneyCard0001toCard002() {
        amount = 5000;
        var page = openDashboard();
        page.updateBalance();
        int expectedRecipient = page.getBalance(DataHelper.getSecondCardNumber()) + amount;
        int expectedSender = page.getBalance(DataHelper.getFirstCardNumber()) - amount;
        var transferPage = page.openTransferForm(DataHelper.getSecondCardNumber());
        transferPage.setAmount(amount);
        transferPage.setFromCardNumber(DataHelper.getFirstCardNumber());
        transferPage.doTransfer();
        page.updateBalance();
        assertEquals(expectedRecipient, page.getBalance(DataHelper.getSecondCardNumber()));
        assertEquals(expectedSender, page.getBalance(DataHelper.getFirstCardNumber()));
    }

    @Test
    public void shouldTransferMoneyCard0002toCard001() {
        amount = 7000;
        var page = openDashboard();
        page.updateBalance();
        int expectedRecipient = page.getBalance(DataHelper.getFirstCardNumber()) + amount;
        int expectedSender = page.getBalance(DataHelper.getSecondCardNumber()) - amount;
        var transferPage = page.openTransferForm(DataHelper.getFirstCardNumber());
        transferPage.setAmount(amount);
        transferPage.setFromCardNumber(DataHelper.getSecondCardNumber());
        transferPage.doTransfer();
        page.updateBalance();
        assertEquals(expectedRecipient, page.getBalance(DataHelper.getFirstCardNumber()));
        assertEquals(expectedSender, page.getBalance(DataHelper.getSecondCardNumber()));
    }

        @Test
        public void shouldNotTransferMoneyCard0001toCard002() {
        amount = 250_000;
        var page = openDashboard();
        page.updateBalance();
        var transferPage = page.openTransferForm(DataHelper.getSecondCardNumber());
        transferPage.setAmount(amount);
        transferPage.setFromCardNumber(DataHelper.getFirstCardNumber());
        transferPage.doTransfer();
        page.updateBalance();
        assertTrue(page.getBalance(DataHelper.getFirstCardNumber()) >= 0);
    }
}


