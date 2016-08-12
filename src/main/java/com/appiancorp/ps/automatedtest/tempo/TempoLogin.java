package com.appiancorp.ps.automatedtest.tempo;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoLogin extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoLogin.class);
  private static final String XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON = Settings.getByConstant("xpathAbsoluteLoginSubmitButton");
  private static final String XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON = Settings.getByConstant("xpathAbsoluteLoginAgreeButton");
  private static final String XPATH_ABSOLUTE_LOGOUT_LINK = Settings.getByConstant("xpathAbsoluteLogoutLink");

  public static TempoLogin getInstance(Settings settings) {
    return new TempoLogin(settings);
  }

  private TempoLogin(Settings settings) {
    super(settings);
  }

  public void logout() {
    if (LOG.isDebugEnabled()) LOG.debug("LOG OUT");

    ((JavascriptExecutor) settings.getDriver()).executeScript("document.evaluate(\"" + XPATH_ABSOLUTE_LOGOUT_LINK +
      "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click()");
  }

  public void waitFor(String... params) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR LOGIN");

    try {
      (new WebDriverWait(settings.getDriver(), 60)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_LOGOUT_LINK)));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Logout link");
    }
  }

  public void login(String url, String userName, String password) {
    if (LOG.isDebugEnabled()) LOG.debug("LOGIN [" + userName + "]");

    try {
      WebElement userNameElement = settings.getDriver().findElement(By.id("un"));
      userNameElement.clear();
      userNameElement.sendKeys(userName);

      WebElement passwordElement = settings.getDriver().findElement(By.id("pw"));
      passwordElement.clear();
      passwordElement.sendKeys(password);

      /* Have to be specific as there is a hidden button for accepting terms */
      WebElement submitButton = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON));
      submitButton.click();

      waitFor();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Login page", userName);
    }
  }

  public void loginWithTerms(String url, String userName, String password) {
    try {
      WebElement agreeButton = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON));
      agreeButton.click();
    } catch (Exception e) {
      LOG.error("Terms", e);
      throw ExceptionBuilder.build(e, settings, "Terms");
    }

    waitForLogin();
    login(url, userName, password);
  }

  public void waitForLogin() {
    try {
      (new WebDriverWait(settings.getDriver(), 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON)));
      WebElement submitButton = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON));
      // Needs to align the button to top to prevent it by being covered by the copyright div
      scrollIntoView(submitButton, true);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Login");
    }
  }

  public void waitForTerms() {
    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON)));
      WebElement agreeButton = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON));
      // Needs to align the button to top to prevent it by being covered by the copyright div
      scrollIntoView(agreeButton, true);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Login");
    }
  }

  public void navigateToLoginPage(String url) {
    // If we are already logged in, log out
    try {
      (new WebDriverWait(settings.getDriver(), 1)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_LOGOUT_LINK)));
    } catch (Exception e) {
      settings.getDriver().get(url);
      return;
    }
    logout();
  }
}
