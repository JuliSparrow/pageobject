package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ChargePage {
  private final SelenideElement heading = $("[data-test-id=dashboard]");
  private final SelenideElement pageDescription = $("h1.heading");
  private final String chargePageText = "Пополнение карты";
  private final SelenideElement amountInput = $("[data-test-id=amount] .input__control");
  private final SelenideElement fromInput = $("[data-test-id=from] .input__control");
  private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
  private final SelenideElement cancelButton = $("[data-test-id=action-cancel]");

  public ChargePage() {
    heading.shouldBe(visible);
    pageDescription.shouldBe(visible).shouldHave(text(chargePageText));
  }

  public DashboardPage charge(String fromCard, int amount) {
//    clearForm();
    amountInput.setValue(String.valueOf(amount));
    fromInput.setValue(fromCard);
    transferButton.click();
    return new DashboardPage();
  }

  public ChargePage fillForm(String fromCard, int amount) {
//    clearForm();
    amountInput.setValue(String.valueOf(amount));
    fromInput.setValue(fromCard);
    return this;
  }

  public DashboardPage cancel() {
    cancelButton.click();
    return new DashboardPage();
  }

  private void clearForm() {
    amountInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
    fromInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
  }

}
