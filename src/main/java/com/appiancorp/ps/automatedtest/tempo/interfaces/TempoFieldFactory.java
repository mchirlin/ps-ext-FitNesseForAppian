package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Captureable;
import com.appiancorp.ps.automatedtest.properties.Clearable;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutCaptureable;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutClearable;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutPopulateable;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutRegexCaptureable;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutVerifiable;
import com.appiancorp.ps.automatedtest.properties.PopulateableMultiple;
import com.appiancorp.ps.automatedtest.properties.RegexCaptureable;
import com.appiancorp.ps.automatedtest.properties.VerifiableMultiple;

public class TempoFieldFactory extends AppianObject implements
  FieldLayoutVerifiable, VerifiableMultiple,
  FieldLayoutClearable, Clearable,
  FieldLayoutPopulateable, PopulateableMultiple,
  FieldLayoutCaptureable, Captureable,
  FieldLayoutRegexCaptureable, RegexCaptureable {

  private static final Logger LOG = Logger.getLogger(TempoFieldFactory.class);
  public static final String XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT = Settings.getByConstant("xpathConcatAncestorFieldLayout");

  public static TempoFieldFactory getInstance(Settings settings) {
    return new TempoFieldFactory(settings);
  }

  protected TempoFieldFactory(Settings settings) {
    super(settings);
  }

  public void populateMultiple(String[] fieldValues, String... params) {
    if (params.length == 1) {
      String fieldName = params[0];

      for (String fieldValue : fieldValues) {
        WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);
        populate(fieldLayout, fieldName, fieldValue);
      }
    } else {
      String fieldType = params[0];
      String fieldName = params[1];

      AbstractTempoField tempoField = getFieldTypeFromString(fieldType);
      for (String fieldValue : fieldValues) {
        WebElement fieldLayout = tempoField.getWebElement(fieldName);
        populate(fieldLayout, fieldName, fieldValue);
      }
    }
  }

  @Override
  public void populate(WebElement fieldLayout, String... params) {
    String fieldName = params[0];
    String fieldValue = params[1];

    try {
      AbstractTempoField tempoField = getFieldType(fieldLayout);
      fieldValue = parseVariable(fieldValue);

      scrollIntoView(fieldLayout);
      tempoField.populate(fieldLayout, fieldName, fieldValue);
      unfocus();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Populate Field", fieldName, fieldValue);
    }
  }

  public void waitFor(String... params) {
    if (params.length == 1) {
      TempoField.getInstance(settings).waitFor(params);
    } else {
      String fieldType = params[0];
      String fieldName = params[1];

      try {
        AbstractTempoField tempoField = getFieldTypeFromString(fieldType);

        tempoField.waitFor(fieldName);
      } catch (Exception e) {
        throw ExceptionBuilder.build(e, settings, "Wait for Field Type", fieldType, fieldName);
      }
    }
  }

  @Override
  public void clear(String... params) {
    WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);
    clear(fieldLayout, params);
  }

  @Override
  public void clear(WebElement fieldLayout, String... params) {
    String fieldName = params[0];

    try {
      AbstractTempoField tempoField = getFieldType(fieldLayout);

      scrollIntoView(fieldLayout);
      tempoField.clear(fieldLayout);
      unfocus();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Clear Field", fieldName);
    }
  }

  public void clearOf(String[] fieldValues, String... params) {
    WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);
    clearOf(fieldLayout, fieldValues);
  }

  public void clearOf(WebElement fieldLayout, String[] fieldValues) {
    try {
      AbstractTempoField tempoField = getFieldType(fieldLayout);

      if (tempoField instanceof TempoPickerField) {
        scrollIntoView(fieldLayout);
        ((TempoPickerField) tempoField).clearOf(fieldLayout, fieldValues);
        unfocus();
      } else {
        throw new IllegalArgumentException("A PICKER field is the only valid option for 'clear of'");
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Clear Field of");
    }
  }

  @Override
  public String capture(String... params) {
    WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);

    return capture(fieldLayout, params);
  }

  @Override
  public String capture(WebElement fieldLayout, String... params) {
    String fieldName = params[0];

    try {
      AbstractTempoField tempoField = getFieldType(fieldLayout);
      return tempoField.capture(fieldLayout);

    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Get field value", fieldName);
    }
  }

  @Override
  public String regexCapture(String regex, Integer group, String... params) {
    WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);

    return regexCapture(fieldLayout, regex, group, params);
  }

  @Override
  public String regexCapture(WebElement fieldLayout, String regex, Integer group, String... params) {
    if (LOG.isDebugEnabled()) LOG.debug("REGEX FOR FIELD VALUE [" + regex + "]");

    try {
      AbstractTempoField tempoField = getFieldType(fieldLayout);
      String text = tempoField.capture(fieldLayout);
      if (LOG.isDebugEnabled()) LOG.debug("FIELD VALUE [" + text + "]");
      return getRegexResults(regex, group, text);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Field value regex", regex);
    }
  }

  public boolean containsMultiple(String[] fieldValues, String... params) {
    String fieldName = params[0];

    for (String fieldValue : fieldValues) {
      WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);
      if (!contains(fieldLayout, fieldName, fieldValue)) return false;
    }

    return true;
  }

  @Override
  public boolean contains(WebElement fieldLayout, String... params) {
    String fieldName = params[0];
    String fieldValue = params[1];

    try {
      AbstractTempoField tempoField = getFieldType(fieldLayout);
      fieldValue = parseVariable(fieldValue);
      return tempoField.contains(fieldLayout, fieldValue);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Field contains", fieldName, fieldValue);
    }
  }

  public AbstractTempoField getFieldType(WebElement fieldLayout) {
    if (TempoReadOnlyField.isType(fieldLayout))
      return TempoReadOnlyField.getInstance(settings);
    else if (TempoTextField.isType(fieldLayout))
      return TempoTextField.getInstance(settings);
    else if (TempoParagraphField.isType(fieldLayout))
      return TempoParagraphField.getInstance(settings);
    else if (TempoDropdownField.isType(fieldLayout))
      return TempoDropdownField.getInstance(settings);
    else if (TempoRadioField.isType(fieldLayout))
      return TempoRadioField.getInstance(settings);
    else if (TempoCheckboxField.isType(fieldLayout))
      return TempoCheckboxField.getInstance(settings);
    else if (TempoFileUploadField.isType(fieldLayout))
      return TempoFileUploadField.getInstance(settings);
    // Datetime must be before Date
    else if (TempoDatetimeField.isType(fieldLayout))
      return TempoDatetimeField.getInstance(settings);
    else if (TempoDateField.isType(fieldLayout))
      return TempoDateField.getInstance(settings);
    else if (TempoPickerField.isType(fieldLayout))
      return TempoPickerField.getInstance(settings);
    throw new IllegalArgumentException("Unrecognized field type");
  }

  public AbstractTempoField getFieldTypeFromString(String fieldType) {
    fieldType = fieldType.toUpperCase();
    if (fieldType.equals("TEXT"))
      return TempoTextField.getInstance(settings);
    else if (fieldType.equals("PARAGRAPH"))
      return TempoParagraphField.getInstance(settings);
    else if (fieldType.equals("FILE_UPLOAD"))
      return TempoFileUploadField.getInstance(settings);
    throw new IllegalArgumentException("Unrecognized field type");
  }
}
