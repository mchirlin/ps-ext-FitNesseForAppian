package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoLogin extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoLogin.class);
  private static final String XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON = Settings.getByConstant("xpathAbsoluteLoginSubmitButton");
  private static final String XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON = Settings.getByConstant("xpathAbsoluteLoginAgreeButton");
  private static final String XPATH_ABSOLUTE_LOGOUT_LINK = Settings.getByConstant("xpathAbsoluteLogoutLink");
  private static final String XPATH_ABSOLUTE_MAIN_BAR = Settings.getByConstant("xpathAbsoluteMainBar");

  public static void logout(Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("LOG OUT");

    ((JavascriptExecutor) s.getDriver()).executeScript("document.evaluate(\"" + XPATH_ABSOLUTE_LOGOUT_LINK +
      "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click()");
  }

  public static void waitForLogout(Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR LOG OUT");

    try {
      (new WebDriverWait(s.getDriver(), 30)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_ABSOLUTE_MAIN_BAR)));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Logout link");
    }
  }

  public static void login(String url, String userName, String password, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("LOGIN [" + userName + "]");

    try {
      WebElement userNameElement = s.getDriver().findElement(By.id("un"));
      userNameElement.clear();
      userNameElement.sendKeys(userName);

      WebElement passwordElement = s.getDriver().findElement(By.id("pw"));
      passwordElement.clear();
      passwordElement.sendKeys(password);

      /* Have to be specific as there is a hidden button for accepting terms */
      WebElement submitButton = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON));
      submitButton.click();

      waitForLogout(s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Login page", userName);
    }
  }

  public static void loginWithTerms(String url, String userName, String password, Settings s) {
    try {
      WebElement agreeButton = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON));
      agreeButton.click();
    } catch (Exception e) {
      LOG.error("Terms", e);
      throw ExceptionBuilder.build(e, s, "Terms");
    }

    waitForLogin(s);
    login(url, userName, password, s);
  }

  public static void waitForLogin(Settings s) {
    try {
      (new WebDriverWait(s.getDriver(), 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON)));
      WebElement submitButton = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON));
      // Needs to align the button to top to prevent it by being covered by the copyright div
      scrollIntoView(submitButton, true, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Login");
    }
  }

  public static void waitForTerms(Settings s) {
    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON)));
      WebElement agreeButton = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON));
      // Needs to align the button to top to prevent it by being covered by the copyright div
      scrollIntoView(agreeButton, true, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Login");
    }
  }

  public static void navigateToLoginPage(String url, Settings s) {
    s.getDriver().get(url);
  }
}
