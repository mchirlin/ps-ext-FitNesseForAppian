package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoMilestoneField extends AbstractTempoField {

  private static final Logger LOG = Logger.getLogger(TempoMilestoneField.class);
  private static final String XPATH_ABSOLUTE_MILESTONE_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteMilestoneFieldLabel");
  private static final String XPATH_ABSOLUTE_MILESTONE_FIELD_INDEX = Settings.getByConstant("xpathAbsoluteMilestoneFieldIndex");
  private static final String XPATH_ABSOLUTE_MILESTONE_FIELD_LABEL_INDEX = "(" + XPATH_ABSOLUTE_MILESTONE_FIELD_LABEL + ")[%2$d]";
  private static final String XPATH_RELATIVE_STEP = Settings.getByConstant("xpathRelativeMilestoneStepGeneral");
  private static final String XPATH_RELATIVE_STEP_SELECTED = Settings.getByConstant("xpathRelativeMilestoneStepSelected");

  public static TempoMilestoneField getInstance(Settings settings) {
    return new TempoMilestoneField(settings);
  }

  protected TempoMilestoneField(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String fieldName = params[0];

    if (isFieldIndex(fieldName)) {
      int index = getIndexFromFieldIndex(fieldName);
      String name = getFieldFromFieldIndex(fieldName);
      if (StringUtils.isBlank(name)) {
        return xpathFormat(XPATH_ABSOLUTE_MILESTONE_FIELD_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, index);
      } else {
        return xpathFormat(XPATH_ABSOLUTE_MILESTONE_FIELD_LABEL_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, name,
          index);
      }

    } else {
      return xpathFormat(XPATH_ABSOLUTE_MILESTONE_FIELD_LABEL + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, fieldName);
    }
  }

  @Override
  public void waitFor(String... params) {
    String fieldName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR MILESTONE [" + fieldName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Milestone Field", fieldName);
    }
  }

  @Override
  public void populate(WebElement fieldLayout, String... params) throws Exception {
    // NOOP
  }

  @Override
  public String capture(WebElement fieldLayout, String... params) {
    WebElement selectedStep = fieldLayout.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_STEP_SELECTED)));
    String currentStep = selectedStep.getText().replace("Current step: ", "");
    if (LOG.isDebugEnabled())
      LOG.debug("MILESTONE FIELD VALUE [" + currentStep + "]");

    return currentStep;
  }

  @Override
  public boolean contains(WebElement fieldLayout, String... params) throws Exception {
    String fieldValue = params[0];

    WebElement selectedStep = fieldLayout.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_STEP_SELECTED)));
    String compareString = selectedStep.getText().replace("Current step: ", "");
    if (LOG.isDebugEnabled())
      LOG.debug("MILESTONE FIELD COMPARISON : Step [" + fieldValue + "] compared to current step [" + compareString + "]");

    return fieldValue.equals(compareString);
  }

  @Override
  public void clear(WebElement fieldLayout, String... params) {

  }

  public static boolean isType(WebElement fieldLayout) {
    try {
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_STEP));
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
