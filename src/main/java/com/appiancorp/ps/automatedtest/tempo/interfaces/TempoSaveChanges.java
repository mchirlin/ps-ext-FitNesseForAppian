package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.Completeable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoSaveChanges extends AppianObject implements WaitFor, Clickable, Completeable {

  private static final Logger LOG = Logger.getLogger(TempoSaveChanges.class);
  private static final String XPATH_ABSOLUTE_SAVE_CHANGES = Settings.getByConstant("xpathAbsoluteSaveChanges");
  private static final String XPATH_ABSOLUTE_FORM_SAVED_CONFIRMATION = Settings.getByConstant("xpathAbsoluteFormSavedConfirmation");

  public static TempoSaveChanges getInstance(Settings settings) {
    return new TempoSaveChanges(settings);
  }

  private TempoSaveChanges(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    return XPATH_ABSOLUTE_SAVE_CHANGES;
  }

  @Override
  public void waitFor(String... params) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR SAVE CHANGES");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds()))
        .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Save Changes");
    }
  }

  @Override
  public void click(String... params) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK SAVE CHANGES");

    try {
      WebElement saveChanges = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(saveChanges);
      // complete();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Click Save Changes");
    }
  }

  @Override
  public boolean complete(String... params) {
    try {
      (new WebDriverWait(settings.getDriver(), settings.getNotPresentTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(
        By.xpath(XPATH_ABSOLUTE_FORM_SAVED_CONFIRMATION)));
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
