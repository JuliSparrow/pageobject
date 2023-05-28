package ru.netology.web.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV2;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

  @Test
  void shouldLoginAndOpenDashboardPage() {
    prepare();
  }

  private DashboardPage prepare() {
    open("http://localhost:9999");
    var loginPage = new LoginPageV2();
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    return verificationPage.validVerify(verificationCode);
  }

  @Test
  void shouldTransferMoneyFromSecondToFirst() {
    var dashboardPage = prepare();
    int amount = 200;
    int firstExpectedBalance = dashboardPage.getFirstBalance() + amount;
    int secondExpectedBalance = dashboardPage.getSecondBalance() - amount;

    var chargePage = dashboardPage.selectFirst();
    dashboardPage = chargePage.charge(DataHelper.getSecondCard(), amount);

    int firstActualBalance = dashboardPage.getFirstBalance();
    int secondActualBalance = dashboardPage.getSecondBalance();
    Assertions.assertEquals(firstExpectedBalance, firstActualBalance);
    Assertions.assertEquals(secondExpectedBalance, secondActualBalance);
  }

  @Test
  void shouldTransferMoneyFromFirstToSecond() {
    var dashboardPage = prepare();
    int amount = 200;
    int firstExpectedBalance = dashboardPage.getFirstBalance() - amount;
    int secondExpectedBalance = dashboardPage.getSecondBalance() + amount;

    var chargePage = dashboardPage.selectSecond();
    dashboardPage = chargePage.charge(DataHelper.getFirstCard(), amount);

    int firstActualBalance = dashboardPage.getFirstBalance();
    int secondActualBalance = dashboardPage.getSecondBalance();
    Assertions.assertEquals(firstExpectedBalance, firstActualBalance);
    Assertions.assertEquals(secondExpectedBalance, secondActualBalance);
  }

  @Test
  void shouldNoChangesWhenTransferMoneyFromFirstToFirst() {
    var dashboardPage = prepare();
    int amount = 300;
    int firstExpectedBalance = dashboardPage.getFirstBalance();
    int secondExpectedBalance = dashboardPage.getSecondBalance();

    var chargePage = dashboardPage.selectFirst();
    dashboardPage = chargePage.charge(DataHelper.getFirstCard(), amount);

    int firstActualBalance = dashboardPage.getFirstBalance();
    int secondActualBalance = dashboardPage.getSecondBalance();
    Assertions.assertEquals(firstExpectedBalance, firstActualBalance);
    Assertions.assertEquals(secondExpectedBalance, secondActualBalance);
  }

  @Test
  void shouldNoChangesWhenTransferMoneyFromSecondToSecond() {
    var dashboardPage = prepare();
    int amount = 400;
    int firstExpectedBalance = dashboardPage.getFirstBalance();
    int secondExpectedBalance = dashboardPage.getSecondBalance();

    var chargePage = dashboardPage.selectSecond();
    dashboardPage = chargePage.charge(DataHelper.getSecondCard(), amount);

    int firstActualBalance = dashboardPage.getFirstBalance();
    int secondActualBalance = dashboardPage.getSecondBalance();
    Assertions.assertEquals(firstExpectedBalance, firstActualBalance);
    Assertions.assertEquals(secondExpectedBalance, secondActualBalance);
  }

  @Test
  void shouldNoChangesWhenCancel() {
    var dashboardPage = prepare();
    int amount = 400;
    int firstExpectedBalance = dashboardPage.getFirstBalance();
    int secondExpectedBalance = dashboardPage.getSecondBalance();

    var chargePage = dashboardPage.selectFirst();
    dashboardPage = chargePage.fillForm(DataHelper.getFirstCard(), amount).cancel();

    int firstActualBalance = dashboardPage.getFirstBalance();
    int secondActualBalance = dashboardPage.getSecondBalance();
    Assertions.assertEquals(firstExpectedBalance, firstActualBalance);
    Assertions.assertEquals(secondExpectedBalance, secondActualBalance);
  }

  @Test
  void shouldChargeFirstThenChargeSecond() {
    var dashboardPage = prepare();
    int amount = 100;
    int firstExpectedBalance = dashboardPage.getFirstBalance() + amount;
    int secondExpectedBalance = dashboardPage.getSecondBalance() - amount;

    var chargePage = dashboardPage.selectFirst();
    dashboardPage = chargePage.charge(DataHelper.getSecondCard(), amount);

    int firstActualBalance = dashboardPage.getFirstBalance();
    int secondActualBalance = dashboardPage.getSecondBalance();
    Assertions.assertEquals(firstExpectedBalance, firstActualBalance);
    Assertions.assertEquals(secondExpectedBalance, secondActualBalance);

    firstExpectedBalance = firstActualBalance - amount;
    secondExpectedBalance = secondActualBalance + amount;

    chargePage = dashboardPage.selectSecond();
    dashboardPage = chargePage.charge(DataHelper.getFirstCard(), amount);

    firstActualBalance = dashboardPage.getFirstBalance();
    secondActualBalance = dashboardPage.getSecondBalance();
    Assertions.assertEquals(firstExpectedBalance, firstActualBalance);
    Assertions.assertEquals(secondExpectedBalance, secondActualBalance);
  }

}

