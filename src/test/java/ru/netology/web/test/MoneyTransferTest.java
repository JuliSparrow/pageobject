package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.LoginPageV2;
import ru.netology.web.page.LoginPageV3;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
      open("http://localhost:9999");
      var loginPage = new LoginPageV1();
//    var loginPage = open("http://localhost:9999", LoginPageV1.class);
      var authInfo = DataHelper.getAuthInfo();
      var verificationPage = loginPage.validLogin(authInfo);
      var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
      var dashboardPage = verificationPage.validVerify(verificationCode);
      dashboardPage.selectFirst().chargeFromSecond(100)
              .selectSecond().chargeFromFirst(100)
              .selectSecond().cancel()
              .selectFirst().chargeFromFirst(100)
              .selectSecond().chargeFromSecond(100);
    }

  @Test
  void shouldTransferMoneyBetweenOwnCardsV2() {
    open("http://localhost:9999");
    var loginPage = new LoginPageV2();
//    var loginPage = open("http://localhost:9999", LoginPageV2.class);
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    var dashboardPage = verificationPage.validVerify(verificationCode);
    dashboardPage.selectFirst().chargeFromSecond(100)
            .selectSecond().chargeFromFirst(100)
            .selectSecond().cancel()
            .selectFirst().chargeFromFirst(100)
            .selectSecond().chargeFromSecond(100);
  }

  @Test
  void shouldTransferMoneyBetweenOwnCardsV3() {
    var loginPage = open("http://localhost:9999", LoginPageV3.class);
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    verificationPage.validVerify(verificationCode);
    var dashboardPage = verificationPage.validVerify(verificationCode);
    dashboardPage.selectFirst().chargeFromSecond(100)
            .selectSecond().chargeFromFirst(100)
            .selectSecond().cancel()
            .selectFirst().chargeFromFirst(100)
            .selectSecond().chargeFromSecond(100);
  }
}

