package com.appiancorp.ps.automatedtest.tempo.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Captureable;
import com.appiancorp.ps.automatedtest.properties.WaitForMultiple;

public class TempoSectionValidation extends TempoSection implements WaitForMultiple, Captureable {

  private static final Logger LOG = Logger.getLogger(TempoSectionValidation.class);

  protected static final String XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT = Settings.getByConstant("xpathAbsoluteSectionFieldLayout");
  protected static final String XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT_INDEX = Settings.getByConstant("xpathAbsoluteSectionFieldLayoutIndex");
  protected static final String XPATH_RELATIVE_SECTION_VALIDATION_MESSAGE_SPECIFIC_VALUE = Settings.getByConstant("xpathRelativeSectionValidationMessageSpecificValue");
  protected static final String XPATH_RELATIVE_SECTION_VALIDATION_MESSAGE = Settings.getByConstant("xpathRelativeSectionValidationMessage");

  protected static final String XPATH_ABSOLUTE_SECTION_LAYOUT = Settings.getByConstant("xpathAbsoluteSectionLayout");

  public static TempoSectionValidation getInstance(Settings settings) {
    return new TempoSectionValidation(settings);
  }

  protected TempoSectionValidation(Settings settings) {
    super(settings);
  }

  @Override
  public void waitForMultiple(String[] validationMessages, String... params) {
    String sectionName = params[0];

    if (LOG.isDebugEnabled())
      LOG.debug("WAIT FOR SECTION [" + sectionName + "] VALIDATIONS [" + String.join(",", validationMessages) + "]");

    WebElement section = getWebElement(sectionName);
    String xpathLocator = getXpathLocator(section);

    try {
      for (String validationMessage : validationMessages) {
        (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath("(" +
          xpathLocator + ")" + xpathFormat(XPATH_RELATIVE_SECTION_VALIDATION_MESSAGE_SPECIFIC_VALUE, validationMessage))));
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Section Field", sectionName, String.join(",", validationMessages));
    }
  }

  @Override
  public String capture(String... params) {
    String sectionName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("GET SECTION VALIDATIONS [" + sectionName + "]");

    WebElement section = getWebElement(sectionName);
    List<String> values = new ArrayList<String>();

    for (WebElement a : section.findElements(By.xpath(XPATH_RELATIVE_SECTION_VALIDATION_MESSAGE))) {
      values.add(a.getText());
    }

    // Remove bullet points
    if (values.size() > 1) {
      for (int i = 0; i < values.size(); i++) {
        String val = values.get(i);
        values.set(i, val.substring(2, val.length()));
      }
    }

    return String.join(",", values);
  }
}
