package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoError extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoError.class);
  private static final String XPATH_ERROR = Settings.getByConstant("xpathAbsoluteError");

  public static void waitFor(Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR TEMPO ERROR");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ERROR)));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Tempo Error");
    }
  }

  public static boolean waitForReturn(Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR TEMPO ERROR");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ERROR)));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Tempo Error");
    }
  }
}
