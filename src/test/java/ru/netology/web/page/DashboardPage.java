package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.CacheStorage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.netology.web.data.CacheStorage.*;
import static ru.netology.web.data.DataHelper.getFirstCard;
import static ru.netology.web.data.DataHelper.getSecondCard;

public class DashboardPage {
  private final SelenideElement heading = $("[data-test-id=dashboard]");
  private final SelenideElement pageDescription = $("h1.heading");
  private final String cardsPageText = "Ваши карты";
  private final SelenideElement firstCard = $$(".list__item").first();
  private final SelenideElement secondCard = $$(".list__item").last();
  private final SelenideElement chargeFirstButton = $$(".list__item [data-test-id=action-deposit]").first();
  private final SelenideElement chargeSecondButton = $$(".list__item [data-test-id=action-deposit]").last();

  public DashboardPage() {
    heading.shouldBe(visible);
    pageDescription.shouldBe(visible).shouldHave(text(cardsPageText));
  }

  public ChargePage selectFirst() {
    setToCardNumber(getFirstCard());
    fillBalances();
    chargeFirstButton.click();
    return new ChargePage();
  }

  public ChargePage selectSecond() {
    setToCardNumber(getSecondCard());
    fillBalances();
    chargeSecondButton.click();
    return new ChargePage();
  }

  private void fillBalances() {
    CacheStorage.setBalance(getFirstCard(), getBalance(firstCard));
    CacheStorage.setBalance(getSecondCard(), getBalance(secondCard));
  }

  private int getBalance(SelenideElement card) {
    String text = card.getText();
    String temp = text.split(":")[1].trim();
    String balance = temp.substring(0, temp.indexOf(" р."));
    return Integer.parseInt(balance);
  }

  public void checkBalances() {
    if (isClear) {
      return;
    }

    if (getFirstCard().equals(getFromCardNumber())) {
      firstCard.shouldHave(text(String.valueOf(CacheStorage.getBalance(getFromCardNumber()) - getAmount())));
      secondCard.shouldHave(text(String.valueOf(CacheStorage.getBalance(getToCardNumber()) + getAmount())));
    } else {
      secondCard.shouldHave(text(String.valueOf(CacheStorage.getBalance(getFromCardNumber()) - getAmount())));
      firstCard.shouldHave(text(String.valueOf(CacheStorage.getBalance(getToCardNumber()) + getAmount())));
    }
    CacheStorage.clear();
  }

  public void checkNoChanges() {
    firstCard.shouldHave(text(String.valueOf(getFirstBalance())));
    secondCard.shouldHave(text(String.valueOf(getSecondBalance())));
  }

}
