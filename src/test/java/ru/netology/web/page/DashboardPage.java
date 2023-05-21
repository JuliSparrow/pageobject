package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private final SelenideElement heading = $("[data-test-id=dashboard]");
  private final SelenideElement pageDescription = $("h1.heading");
  private final String cardsPageText = "Ваши карты";
  private final String chargePageText = "Пополнение карты";
  private final ElementsCollection cardList = $$(".list__item");
  private final String chargeButtonSelector = "[data-test-id=action-deposit]";
  private final SelenideElement amountInput = $("[data-test-id=amount] .input__control");
  private final SelenideElement fromInput = $("[data-test-id=from] .input__control");
  private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
  private final SelenideElement cancelButton = $("[data-test-id=action-cancel]");
  private static final String first = "5559 0000 0000 0001";
  private static final String second = "5559 0000 0000 0002";
  private String fromCardNumber;
  private String toCardNumber;
  private int amount;
  private final Map<String, Integer> balances = new HashMap<>();

  public DashboardPage() {
    heading.shouldBe(visible);
    pageDescription.shouldBe(visible).shouldHave(text(cardsPageText));
  }

  public DashboardPage selectFirst() {
    return selectCard(first);
  }

  public DashboardPage selectSecond() {
    return selectCard(second);
  }

  private DashboardPage selectCard(String card) {
    toCardNumber = card;
    fillStartBalances();
    getChargeButton(card).click();
    pageDescription.shouldBe(visible).shouldHave(text(chargePageText));
    return this;
  }

  public DashboardPage chargeFromFirst(int amount) {
    return charge(first, amount);
  }

  public DashboardPage chargeFromSecond(int amount) {
    return charge(second, amount);
  }

  private DashboardPage charge(String fromCard, int amount) {
    fromCardNumber = fromCard;
    this.amount = amount;
    amountInput.setValue(String.valueOf(amount));
    fromInput.setValue(fromCard);
    transferButton.click();
    heading.shouldBe(visible);
    pageDescription.shouldHave(text(cardsPageText));
    checkBalances();
    clear();
    return this;
  }

  public DashboardPage cancel() {
    cancelButton.click();
    heading.shouldBe(visible);
    pageDescription.shouldHave(text(cardsPageText));
    checkNoChanges();
    clear();
    return this;
  }

  private SelenideElement getChargeButton(String cardNumber) {
    String mask = getCardMask(cardNumber);
    return cardList.find(text(mask)).$(chargeButtonSelector);
  }

  private String getCardMask(String cardNumber) {
    String[] parts = cardNumber.split(" ");
    for(int i = 0; i < parts.length; i++) {
      if (i != parts.length - 1) {
        parts[i] = parts[i].replaceAll("\\d", "*");
      }
    }
    return String.join(" ", parts);
  }

  private void fillStartBalances() {
    balances.put(first, getBalance(first));
    balances.put(second, getBalance(second));
  }

  private int getBalance(String cardNumber) {
    String text = getCard(cardNumber).getText();
    String temp = text.split(":")[1].trim();
    String balance = temp.substring(0, temp.indexOf(" р."));
    return Integer.parseInt(balance);
  }

  private SelenideElement getCard(String number) {
     String mask = getCardMask(number);
    return cardList.find(text(mask));
  }

  private void checkBalances() {
    if (fromCardNumber.equals(toCardNumber)) {
      checkNoChanges();
    } else {
      getCard(fromCardNumber).shouldHave(text(String.valueOf(balances.get(fromCardNumber) - amount)));
      getCard(toCardNumber).shouldHave(text(String.valueOf(balances.get(toCardNumber) + amount)));
    }
  }

  private void checkNoChanges() {
    getCard(fromCardNumber).shouldHave(text(String.valueOf(balances.get(fromCardNumber))));
    getCard(toCardNumber).shouldHave(text(String.valueOf(balances.get(toCardNumber))));
  }

  private void clear() {
    fromCardNumber = null;
    toCardNumber = null;
    amount = 0;
    balances.clear();
  }


}
