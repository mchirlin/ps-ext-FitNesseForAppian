package com.appiancorp.ps.automatedtest.tempo.record;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoRecordType extends AppianObject implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoRecordType.class);
  private static final String XPATH_ABSOLUTE_RECORD_TYPE_LINK = Settings.getByConstant("xpathAbsoluteRecordTypeLink");
  private static final String XPATH_ABSOLUTE_RECORD_TYPE_LINK_INDEX = "(" + XPATH_ABSOLUTE_RECORD_TYPE_LINK + ")[%2$d]";

  public static TempoRecordType getInstance(Settings settings) {
    return new TempoRecordType(settings);
  }

  private TempoRecordType(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String type = params[0];

    if (isFieldIndex(type)) {
      int rNum = getIndexFromFieldIndex(type);
      String rName = getFieldFromFieldIndex(type);
      return xpathFormat(XPATH_ABSOLUTE_RECORD_TYPE_LINK_INDEX, rName, rNum);
    } else {
      return xpathFormat(XPATH_ABSOLUTE_RECORD_TYPE_LINK, type);
    }
  }

  @Override
  public void click(String... params) {
    String type = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("CLICK RECORD TYPE [" + type + "]");

    try {
      WebElement recordType = settings.getDriver().findElement(By.xpath(getXpath(type)));
      clickElement(recordType);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Record Type", type);
    }
  }

  @Override
  public void waitFor(String... params) {
    String type = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR RECORD TYPE [" + type + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds()))
        .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Record Type", type);
    }
  }
}
