package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.Captureable;
import com.appiancorp.ps.automatedtest.properties.Clearable;
import com.appiancorp.ps.automatedtest.properties.Populateable;
import com.appiancorp.ps.automatedtest.properties.PopulateableMultiple;
import com.appiancorp.ps.automatedtest.properties.RegexCaptureable;
import com.appiancorp.ps.automatedtest.properties.Verifiable;
import com.appiancorp.ps.automatedtest.properties.VerifiableMultiple;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoSectionField extends TempoSection implements
  VerifiableMultiple, Verifiable,
  PopulateableMultiple, Populateable,
  Captureable, RegexCaptureable, Clearable, WaitFor {

  @SuppressWarnings("unused")
  private static final Logger LOG = Logger.getLogger(TempoSectionField.class);

  protected static final String XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT = Settings.getByConstant("xpathAbsoluteSectionFieldLayout");
  protected static final String XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT_INDEX = Settings.getByConstant("xpathAbsoluteSectionFieldLayoutIndex");
  protected static final String XPATH_RELATIVE_SECTION_VALIDATION_MESSAGE_SPECIFIC_VALUE = Settings.getByConstant("xpathRelativeSectionValidationMessageSpecificValue");
  protected static final String XPATH_RELATIVE_SECTION_VALIDATION_MESSAGE = Settings.getByConstant("xpathRelativeSectionValidationMessage");

  protected static final String XPATH_ABSOLUTE_SECTION_LAYOUT = Settings.getByConstant("xpathAbsoluteSectionLayout");

  public static TempoSectionField getInstance(Settings settings) {
    return new TempoSectionField(settings);
  }

  protected TempoSectionField(Settings settings) {
    super(settings);
  }

  public WebElement getWebElement(String... params) {
    return settings.getDriver().findElement(By.xpath(getXpath(params)));
  }

  @Override
  public String getXpath(String... params) {
    String fieldName = params[0];
    String sectionName = params[1];

    // Scroll the field layout into view
    if (isFieldIndex(fieldName)) {
      int index = getIndexFromFieldIndex(fieldName);
      return xpathFormat(XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT_INDEX, sectionName, index);
    } else {
      return xpathFormat(XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT, sectionName, fieldName);
    }
  }

  @Override
  public void populateMultiple(String[] fieldValues, String... params) {
    String fieldName = params[0];
    String fieldSection = params[1];

    for (String fieldValue : fieldValues) {
      params = new String[] { fieldName, fieldSection, fieldValue };
      populate(params);
    }
  }

  @Override
  public void populate(String... params) {
    String fieldName = params[0];
    String fieldSection = params[1];
    String fieldValue = params[2];

    WebElement fieldLayout = getWebElement(fieldName, fieldSection);
    TempoFieldFactory.getInstance(settings).populate(fieldLayout, fieldName, fieldValue);
  }

  @Override
  public void clear(String... params) {
    String fieldName = params[0];
    String sectionName = params[1];

    WebElement fieldLayout = getWebElement(fieldName, sectionName);
    TempoFieldFactory.getInstance(settings).clear(fieldLayout, fieldName);
  }

  @Override
  public String capture(String... params) {
    WebElement fieldLayout = getWebElement(params);
    return TempoFieldFactory.getInstance(settings).capture(fieldLayout, params);
  }

  @Override
  public String regexCapture(String regex, Integer group, String... params) {
    WebElement fieldLayout = getWebElement(params);
    return TempoFieldFactory.getInstance(settings).regexCapture(fieldLayout, regex, group, params);
  }

  @Override
  public boolean containsMultiple(String[] fieldValues, String... params) {
    String fieldName = params[0];
    String sectionName = params[1];

    for (String fieldValue : fieldValues) {
      params = new String[] { fieldName, sectionName, fieldValue };
      if (!contains(params)) return false;
    }
    return true;
  }

  @Override
  public boolean contains(String... params) {
    String fieldName = params[0];
    String sectionName = params[1];
    String fieldValue = params[2];

    WebElement fieldLayout = getWebElement(fieldName, sectionName);
    if (!TempoFieldFactory.getInstance(settings).contains(fieldLayout, fieldName, fieldValue)) return false;
    return true;
  }
}
