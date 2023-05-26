package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

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
    chargeFirstButton.click();
    return new ChargePage();
  }

  public ChargePage selectSecond() {
    chargeSecondButton.click();
    return new ChargePage();
  }

  public int getFirstBalance() {
    return getBalance(firstCard);
  }

  public int getSecondBalance() {
    return getBalance(secondCard);
  }

  private int getBalance(SelenideElement card) {
    String text = card.getText();
    String temp = text.split(":")[1].trim();
    String balance = temp.substring(0, temp.indexOf(" р."));
    return Integer.parseInt(balance);
  }

}
