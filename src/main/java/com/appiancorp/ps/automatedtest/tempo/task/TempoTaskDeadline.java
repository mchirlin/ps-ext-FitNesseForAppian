package com.appiancorp.ps.automatedtest.tempo.task;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoTaskDeadline extends AppianObject implements WaitFor {

  private static final Logger LOG = Logger.getLogger(TempoTaskDeadline.class);
  private static final String XPATH_ABSOLUTE_TASK_DEADLINE = Settings.getByConstant("xpathAbsoluteTaskDeadline");

  public static TempoTaskDeadline getInstance(Settings settings) {
    return new TempoTaskDeadline(settings);
  }

  private TempoTaskDeadline(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String taskName = params[0];
    String deadline = params[1];

    return xpathFormat(XPATH_ABSOLUTE_TASK_DEADLINE, taskName, deadline);
  }

  @Override
  public void waitFor(String... params) {
    String taskName = params[0];
    String deadline = params[1];

    if (LOG.isDebugEnabled()) LOG.debug("TASK [" + taskName + "] HAS DEADLINE [" + deadline + "]");

    try {
      settings.getDriver().findElement(By.xpath(getXpath(params)));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Task deadline", taskName, deadline);
    }
  }
}
