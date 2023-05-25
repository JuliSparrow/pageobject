package ru.netology.web.test;

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
    var chargePage = dashboardPage.selectFirst();
    dashboardPage = chargePage.chargeFromSecond(100);
    dashboardPage.checkBalances();
  }

  @Test
  void shouldTransferMoneyFromFirstToSecond() {
    var dashboardPage = prepare();
    var chargePage = dashboardPage.selectSecond();
    dashboardPage = chargePage.chargeFromFirst(200);
    dashboardPage.checkBalances();
  }

  @Test
  void shouldNoChangesWhenTransferMoneyFromFirstToFirst() {
    var dashboardPage = prepare();
    var chargePage = dashboardPage.selectFirst();
    dashboardPage = chargePage.chargeFromFirst(300);
    dashboardPage.checkNoChanges();
  }

  @Test
  void shouldNoChangesWhenTransferMoneyFromSecondToSecond() {
    var dashboardPage = prepare();
    var chargePage = dashboardPage.selectSecond();
    dashboardPage = chargePage.chargeFromSecond(400);
    dashboardPage.checkNoChanges();
  }

  @Test
  void shouldNoChangesWhenCancel() {
    var dashboardPage = prepare();
    var chargePage = dashboardPage.selectFirst();
    dashboardPage = chargePage.cancel();
    dashboardPage.checkNoChanges();
  }

  @Test
  void shouldChargeFirstThenChargeSecond() {
    var dashboardPage = prepare();
    var chargePage = dashboardPage.selectFirst();
    dashboardPage = chargePage.chargeFromSecond(100);
    dashboardPage.checkBalances();

    chargePage = dashboardPage.selectSecond();
    dashboardPage = chargePage.chargeFromFirst(100);
    dashboardPage.checkBalances();

  }

}

