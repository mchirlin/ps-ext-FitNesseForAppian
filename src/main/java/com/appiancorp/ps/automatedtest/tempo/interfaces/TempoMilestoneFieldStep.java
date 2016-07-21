package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoMilestoneFieldStep extends TempoMilestoneField implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoMilestoneFieldStep.class);
  private static final String XPATH_RELATIVE_MILESTONE_STEP = Settings.getByConstant("xpathRelativeMilestoneStep");
  private static final String XPATH_RELATIVE_MILESTONE_STEP_INDEX = Settings.getByConstant("xpathRelativeMilestoneStepIndex");

  public static TempoMilestoneFieldStep getInstance(Settings settings) {
    return new TempoMilestoneFieldStep(settings);
  }

  private TempoMilestoneFieldStep(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String step = getParam(1, params);

    if (isFieldIndex(step)) {
      int rNum = getIndexFromFieldIndex(step);
      return xpathFormat(XPATH_RELATIVE_MILESTONE_STEP_INDEX, rNum);
    } else {
      return xpathFormat(XPATH_RELATIVE_MILESTONE_STEP, step);
    }
  }

  @Override
  public void click(String... params) {
    String milestone = getParam(0, params);
    String step = getParam(1, params);

    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON MILESTONE [" + milestone + "] STEP [" + step + "]");

    try {
      TempoMilestoneField tm = TempoMilestoneField.getInstance(settings);
      WebElement milestoneField = tm.getWebElement(params);
      WebElement stepElement = milestoneField.findElement(By.xpath(getXpath(params)));
      clickElement(stepElement);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Click on Milestone Step", milestone, step);
    }
  }
}
