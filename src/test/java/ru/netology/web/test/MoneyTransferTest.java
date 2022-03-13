package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.CDashboardPage;
import ru.netology.web.page.ALoginPage;

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
    public void shouldTransferMoneyCard0002toCard001() {
        amount = 10000;
        CDashboardPage page = openDashboard();
        page.updateBalance();
        int expectedRecipient = page.getBalance(DataHelper.getFirstCardNumber()) + amount;
        int expectedSender = page.getBalance(DataHelper.getSecondCardNumber()) - amount;
        page.moneyTransfer(DataHelper.getSecondCardNumber(), DataHelper.getFirstCardNumber(), amount);
        page.updateBalance();
        assertEquals(expectedRecipient, page.getBalance(DataHelper.getFirstCardNumber()));
        assertEquals(expectedSender, page.getBalance(DataHelper.getSecondCardNumber()));
    }

    @Test
    public void shouldNotTransferMoneyCard0002toCard001() {
        amount = 25000;
        CDashboardPage page = openDashboard();
        page.updateBalance();
        page.moneyTransfer(DataHelper.getSecondCardNumber(), DataHelper.getFirstCardNumber(), amount);
        page.updateBalance();
        assertTrue(page.getBalance(DataHelper.getSecondCardNumber()) >= 0);

    }
}


