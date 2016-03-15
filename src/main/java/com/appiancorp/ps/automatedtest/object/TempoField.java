package com.appiancorp.ps.automatedtest.object;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.google.common.base.Strings;

public class TempoField extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoField.class);
  protected static final String XPATH_ABSOLUTE_FIELD_LAYOUT = Settings.getByConstant("xpathAbsoluteFieldLayout");
  protected static final String XPATH_ABSOLUTE_FIELD_LAYOUT_INDEX = "(" + XPATH_ABSOLUTE_FIELD_LAYOUT + ")[%d]";
  protected static final String XPATH_RELATIVE_READ_ONLY_FIELD = Settings.getByConstant("xpathRelativeReadOnlyField");
  protected static final String XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT = Settings.getByConstant("xpathConcatAncestorFieldLayout");
  protected static final String XPATH_RELATIVE_FIELD_VALIDATION_MESSAGE_SPECIFIC_VALUE = Settings.getByConstant("xpathRelativeFieldValidationMessageSpecificValue");
  protected static final String XPATH_RELATIVE_FIELD_VALIDATION_MESSAGE = Settings.getByConstant("xpathRelativeFieldValidationMessage");

  public static WebElement getFieldLayout(String fieldName, Settings s) {
    if (isFieldIndex(fieldName)) {
      String fName = getFieldFromFieldIndex(fieldName);
      int index = getIndexFromFieldIndex(fieldName);
      return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_FIELD_LAYOUT_INDEX, fName, fName, index)));
    } else {
      return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_FIELD_LAYOUT, fieldName, fieldName)));
    }
  }

  public static void populate(String fieldType, String fieldName, String[] fieldValues, Settings s) {
    TempoFieldType type = getFieldTypeFromString(fieldType);
    for (String fieldValue : fieldValues) {
      WebElement fieldLayout;

      switch (type) {
        case FILE_UPLOAD:
          fieldLayout = TempoFileUploadField.getFieldLayout(fieldName, s);
          break;

        default:
          throw new IllegalArgumentException("FILE_UPLOAD is the only valid type");
      }

      populate(fieldLayout, fieldName, fieldValue, s);
    }
  }

  public static void populate(String fieldName, String[] fieldValues, Settings s) {
    for (String fieldValue : fieldValues) {
      WebElement fieldLayout = getFieldLayout(fieldName, s);
      populate(fieldLayout, fieldName, fieldValue, s);
    }
  }

  public static void populate(WebElement fieldLayout, String fieldName, String fieldValue, Settings s) {
    TempoFieldType fieldType = getFieldType(fieldLayout);
    fieldValue = AppianObject.parseVariable(fieldValue, s);

    try {
      switch (fieldType) {
        case TEXT:
          TempoTextField.populate(fieldLayout, fieldValue, s);
          break;

        case PARAGRAPH:
          TempoParagraphField.populate(fieldLayout, fieldValue, s);
          break;

        case SELECT:
          TempoSelectField.populate(fieldLayout, fieldValue, s);
          break;

        case RADIO:
          TempoRadioField.populate(fieldLayout, fieldValue, s);
          break;

        case CHECKBOX:
          TempoCheckboxField.populate(fieldLayout, fieldValue, s);
          break;

        case FILE_UPLOAD:
          TempoFileUploadField.populate(fieldLayout, fieldValue, s);
          break;

        case DATE:
          TempoDateField.populate(fieldLayout, fieldValue, s);
          break;

        case DATETIME:
          TempoDatetimeField.populate(fieldLayout, fieldValue, s);
          break;

        case PICKER:
          TempoPickerField.populate(fieldLayout, fieldName, fieldValue, s);
          break;

        default:
          throw new IllegalArgumentException("Unrecognized field type");
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Populate Field", fieldName, fieldValue);
    }
  }

  public static void waitFor(String fieldType, String fieldName, Settings s) {
    try {
      TempoFieldType type = getFieldTypeFromString(fieldType);

      switch (type) {
        case FILE_UPLOAD:
          TempoFileUploadField.waitFor(fieldName, s);
          break;

        default:
          throw new IllegalArgumentException("FILE_UPLOAD is the only valid field type");
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Field Type", fieldType, fieldName);
    }
  }

  public static boolean waitForReturn(String fieldName, Integer timeout, Settings s) {
    try {
      // Scroll the field layout into view
      if (isFieldIndex(fieldName)) {
        String fName = getFieldFromFieldIndex(fieldName);
        int index = getIndexFromFieldIndex(fieldName);
        (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_FIELD_LAYOUT_INDEX, fName, fName, index))));
      } else {
        (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_FIELD_LAYOUT, fieldName, fieldName))));
      }
      WebElement fieldLayout = getFieldLayout(fieldName, s);
      scrollIntoView(fieldLayout, s);
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Field", fieldName);
    }
  }

  public static void waitFor(String fieldName, Settings s) {
    try {
      // Scroll the field layout into view
      if (isFieldIndex(fieldName)) {
        String fName = getFieldFromFieldIndex(fieldName);
        int index = getIndexFromFieldIndex(fieldName);
        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_FIELD_LAYOUT_INDEX, fName, fName, index))));
      } else {
        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_FIELD_LAYOUT, fieldName, fieldName))));
      }
      WebElement fieldLayout = getFieldLayout(fieldName, s);
      scrollIntoView(fieldLayout, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Field", fieldName);
    }
  }

  public static void clear(String fieldName, Settings s) {
    WebElement fieldLayout = getFieldLayout(fieldName, s);
    clear(fieldLayout, fieldName, s);
  }

  public static void clear(WebElement fieldLayout, String fieldName, Settings s) {
    TempoFieldType fieldType = getFieldType(fieldLayout);

    try {
      switch (fieldType) {
        case TEXT:
          TempoTextField.clear(fieldLayout);
          break;

        case PARAGRAPH:
          TempoParagraphField.clear(fieldLayout);
          break;

        case FILE_UPLOAD:
          TempoFileUploadField.clear(fieldLayout);
          break;

        case PICKER:
          TempoPickerField.clear(fieldLayout);
          break;

        default:
          throw new IllegalArgumentException("Invalid field type");
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Clear Field", fieldName);
    }
  }

  public static void clearOf(String fieldName, String[] fieldValues, Settings s) {
    WebElement fieldLayout = getFieldLayout(fieldName, s);
    clearOf(fieldLayout, fieldValues, s);
  }

  public static void clearOf(WebElement fieldLayout, String[] fieldValues, Settings s) {
    TempoFieldType fieldType = getFieldType(fieldLayout);

    try {
      switch (fieldType) {

        case PICKER:
          TempoPickerField.clearOf(fieldLayout, fieldValues);
          break;

        default:
          throw new IllegalArgumentException("PICKER is the only valid field type");
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Clear Field of");
    }
  }

  public static String getValue(String fieldName, Settings s) {
    WebElement fieldLayout = getFieldLayout(fieldName, s);

    return getValue(fieldLayout, fieldName, s);
  }

  public static String getValue(WebElement fieldLayout, String fieldName, Settings s) {
    TempoFieldType fieldType = getFieldType(fieldLayout);

    try {
      switch (fieldType) {

        case READ_ONLY:
          return getValue(fieldLayout);

        case TEXT:
          return TempoTextField.getValue(fieldLayout);

        case PARAGRAPH:
          return TempoParagraphField.getValue(fieldLayout);

        case SELECT:
          return TempoSelectField.getValue(fieldLayout);

        case RADIO:
          return TempoRadioField.getValue(fieldLayout);

        case CHECKBOX:
          return TempoCheckboxField.getValue(fieldLayout);

        case FILE_UPLOAD:
          return TempoFileUploadField.getValue(fieldLayout);

        case DATE:
          return TempoDateField.getValue(fieldLayout);

        case DATETIME:
          return TempoDatetimeField.getValue(fieldLayout);

        case PICKER:
          return TempoPickerField.getValue(fieldLayout);

        default:
          throw new IllegalArgumentException("Invalid field type");
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Get field value", fieldName);
    }
  }

  public static void waitForValidationMessages(String fieldName, String[] validationMessages, Settings s) {
    try {
      WebElement fieldLayout = getFieldLayout(fieldName, s);

      String xpathLocator = getXpathLocator(fieldLayout);

      for (String validationMessage : validationMessages) {
        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath("(" +
          xpathLocator + ")" + String.format(XPATH_RELATIVE_FIELD_VALIDATION_MESSAGE_SPECIFIC_VALUE, validationMessage))));
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Get field value", fieldName);
    }
  }

  public static String getValidationMessages(String fieldName, Settings s) {
    WebElement fieldLayout = getFieldLayout(fieldName, s);
    List<String> values = new ArrayList<String>();

    for (WebElement a : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_FIELD_VALIDATION_MESSAGE))) {
      values.add(a.getText());
    }

    return String.join(",", values);
  }

  public static boolean contains(String fieldName, String[] fieldValues, Settings s) {
    for (String fieldValue : fieldValues) {
      WebElement fieldLayout = getFieldLayout(fieldName, s);
      if (!contains(fieldLayout, fieldName, fieldValue, s)) return false;
    }

    return true;
  }

  public static boolean contains(WebElement fieldLayout, String fieldName, String fieldValue, Settings s) {
    TempoFieldType fieldType = getFieldType(fieldLayout);
    fieldValue = AppianObject.parseVariable(fieldValue, s);

    try {
      switch (fieldType) {

        case READ_ONLY:
          return contains(fieldLayout, fieldValue);

        case TEXT:
          return TempoTextField.contains(fieldLayout, fieldValue);

        case PARAGRAPH:
          return TempoParagraphField.contains(fieldLayout, fieldValue);

        case SELECT:
          return TempoSelectField.contains(fieldLayout, fieldValue);

        case RADIO:
          return TempoRadioField.contains(fieldLayout, fieldValue);

        case CHECKBOX:
          return TempoCheckboxField.contains(fieldLayout, fieldValue);

        case FILE_UPLOAD:
          return TempoFileUploadField.contains(fieldLayout, fieldValue, s);

        case DATE:
          return TempoDateField.contains(fieldLayout, fieldValue, s);

        case DATETIME:
          return TempoDatetimeField.contains(fieldLayout, fieldValue, s);

        case PICKER:
          return TempoPickerField.contains(fieldLayout, fieldValue);

        default:
          throw new IllegalArgumentException("Invalid field type");
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Field contains", fieldName, fieldValue);
    }
  }

  public static boolean contains(WebElement fieldLayout, String fieldValue) {
    String compareString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_READ_ONLY_FIELD)).getText();

    if (LOG.isDebugEnabled())
      LOG.debug("READ ONLY FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");

    // Necessary to compare both to handle readOnly date fields that are compared to datetimes
    // The searched for value cannot be null
    return (compareString.contains(fieldValue) && !Strings.isNullOrEmpty(fieldValue)) ||
      (fieldValue.contains(compareString) && !Strings.isNullOrEmpty(compareString));
  }

  public static String getValue(WebElement fieldLayout) {
    String value = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_READ_ONLY_FIELD))).getText();

    if (LOG.isDebugEnabled()) LOG.debug("READ ONLY FIELD VALUE: " + value);

    return value;
  }

  public static boolean isReadOnly(WebElement fieldLayout) {
    try {
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_READ_ONLY_FIELD));
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  public static TempoFieldType getFieldType(WebElement fieldLayout) {
    if (TempoField.isReadOnly(fieldLayout))
      return TempoFieldType.READ_ONLY;
    else if (TempoTextField.isType(fieldLayout))
      return TempoFieldType.TEXT;
    else if (TempoParagraphField.isType(fieldLayout))
      return TempoFieldType.PARAGRAPH;
    else if (TempoSelectField.isType(fieldLayout))
      return TempoFieldType.SELECT;
    else if (TempoRadioField.isType(fieldLayout))
      return TempoFieldType.RADIO;
    else if (TempoCheckboxField.isType(fieldLayout))
      return TempoFieldType.CHECKBOX;
    else if (TempoFileUploadField.isType(fieldLayout))
      return TempoFieldType.FILE_UPLOAD;
    // Datetime must be before Date
    else if (TempoDatetimeField.isType(fieldLayout))
      return TempoFieldType.DATETIME;
    else if (TempoDateField.isType(fieldLayout))
      return TempoFieldType.DATE;
    else if (TempoPickerField.isType(fieldLayout))
      return TempoFieldType.PICKER;
    else
      return TempoFieldType.UNKNOWN;
  }

  public static TempoFieldType getFieldTypeFromString(String fieldType) {
    if (fieldType.equals("READ_ONLY"))
      return TempoFieldType.READ_ONLY;
    else if (fieldType.equals("TEXT"))
      return TempoFieldType.TEXT;
    else if (fieldType.equals("PARAGRAPH"))
      return TempoFieldType.PARAGRAPH;
    else if (fieldType.equals("SELECT"))
      return TempoFieldType.SELECT;
    else if (fieldType.equals("RADIO"))
      return TempoFieldType.RADIO;
    else if (fieldType.equals("CHECKBOX"))
      return TempoFieldType.CHECKBOX;
    else if (fieldType.equals("FILE_UPLOAD"))
      return TempoFieldType.FILE_UPLOAD;
    else if (fieldType.equals("DATETIME"))
      return TempoFieldType.DATETIME;
    else if (fieldType.equals("DATE"))
      return TempoFieldType.DATE;
    else if (fieldType.equals("PICKER"))
      return TempoFieldType.PICKER;
    else
      return TempoFieldType.UNKNOWN;
  }
}
