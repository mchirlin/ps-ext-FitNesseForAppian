package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.exception.IllegalArgumentTestException;
import com.appiancorp.ps.automatedtest.fixture.TempoFixtureTest;
import com.appiancorp.ps.automatedtest.object.AppianObject;

public class InterfaceFixtureTest extends TempoFixtureTest {

  @BeforeClass
  public static void setUpInterface() throws Exception {
    tFixture.clickOnMenu("Actions");
    tFixture.clickOnAction("All Fields");
  }

  @Test
  public void testGetFormTitle() throws Exception {
    assertEquals(tFixture.getFormTitle(), "Form");
  }

  @Test
  public void testGetFormInstructions() throws Exception {
    assertEquals(tFixture.getFormInstructions(), "Instructions");
  }

  @Test
  public void testTextField() throws Exception {
    tFixture.populateFieldWith("TextField", new String[] { "text" });
    assertTrue(tFixture.verifyFieldContains("TextField", new String[] { "text" }));
    assertEquals("text", tFixture.getFieldValue("TextField"));

    assertTrue(tFixture.verifyFieldContains("ROTextField", new String[] { "text" }));
    assertEquals("text", tFixture.getFieldValue("ROTextField"));

    // Grid
    tFixture.populateGridColumnRowWith("[1]", "TextField", "[1]", new String[] { "gridText" });
    assertTrue(tFixture.verifyGridColumnRowContains("[1]", "TextField", "[1]", new String[] { "gridText" }));
    assertEquals("gridText", tFixture.getGridColumnRowValue("[1]", "TextField", "[1]"));

    assertTrue(tFixture.verifyGridColumnRowContains("[1]", "TextField", "[2]", new String[] { "gridText" }));
    assertEquals("gridText", tFixture.getGridColumnRowValue("[1]", "TextField", "[2]"));

    // TODO Clear
    // tFixture.clearField("TextField");
    // assertTrue(tFixture.verifyFieldContains("TextField", new String[]{""}));
  }

  @Test
  public void testParagraphField() throws Exception {
    tFixture.populateFieldWith("ParagraphField", new String[] { "paragraph" });
    assertTrue(tFixture.verifyFieldContains("ParagraphField", new String[] { "paragraph" }));
    assertEquals("paragraph", tFixture.getFieldValue("ParagraphField"));

    assertTrue(tFixture.verifyFieldContains("ROParagraphField", new String[] { "paragraph" }));
    assertEquals("paragraph", tFixture.getFieldValue("ROParagraphField"));

    // Grid
    tFixture.populateGridColumnRowWith("EditableGrid", "[3]", "[1]", new String[] { "gridParagraph" });
    assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "[3]", "[1]", new String[] { "gridParagraph" }));
    assertEquals("gridParagraph", tFixture.getGridColumnRowValue("EditableGrid", "[3]", "[1]"));

    assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "[3]", "[2]", new String[] { "gridParagraph" }));
    assertEquals("gridParagraph", tFixture.getGridColumnRowValue("EditableGrid", "[3]", "[2]"));

    // TODO Clear
    // tFixture.clearField("ParagraphField");
    // assertTrue(tFixture.verifyFieldContains("ParagraphField", new String[]{""}));
  }

  @Test
  public void testEncryptedTextField() throws Exception {
    tFixture.populateFieldWith("EncryptedTextField", new String[] { "encrypted" });
    assertTrue(tFixture.verifyFieldContains("EncryptedTextField", new String[] { "encrypted" }));
    assertEquals("encrypted", tFixture.getFieldValue("EncryptedTextField"));

    assertTrue(tFixture.verifyFieldContains("ROEncryptedTextField", new String[] { "encrypted" }));
    assertEquals("encrypted", tFixture.getFieldValue("ROEncryptedTextField"));

    // Grid
    tFixture.populateGridColumnRowWith("EditableGrid", "EncryptedTextField", "[1]", new String[] { "gridEncrypted" });
    assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "EncryptedTextField", "[1]", new String[] { "gridEncrypted" }));
    assertEquals("gridEncrypted", tFixture.getGridColumnRowValue("EditableGrid", "EncryptedTextField", "[1]"));

    assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "EncryptedTextField", "[2]", new String[] { "gridEncrypted" }));
    assertEquals("gridEncrypted", tFixture.getGridColumnRowValue("EditableGrid", "EncryptedTextField", "[2]"));

    // TODO Clear
    // tFixture.clearField("EncryptedTextField");
    // assertTrue(tFixture.verifyFieldContains("EncryptedTextField", new String[]{""}));
  }

  @Test
  public void testIntegerField() throws Exception {
    tFixture.populateFieldWith("IntegerField", new String[] { "1" });
    assertTrue(tFixture.verifyFieldContains("IntegerField", new String[] { "1" }));
    assertEquals("1", tFixture.getFieldValue("IntegerField"));

    assertTrue(tFixture.verifyFieldContains("ROIntegerField", new String[] { "1" }));
    assertEquals("1", tFixture.getFieldValue("ROIntegerField"));

    // Grid
    tFixture.populateGridColumnRowWith("[1]", "[5]", "[1]", new String[] { "1" });
    assertTrue(tFixture.verifyGridColumnRowContains("[1]", "[5]", "[1]", new String[] { "1" }));
    assertEquals("1", tFixture.getGridColumnRowValue("[1]", "[5]", "[1]"));

    assertTrue(tFixture.verifyGridColumnRowContains("[1]", "[5]", "[2]", new String[] { "1" }));
    assertEquals("1", tFixture.getGridColumnRowValue("[1]", "[5]", "[2]"));

    // TODO Clear
    // tFixture.clearField("IntegerField");
    // assertTrue(tFixture.verifyFieldContains("IntegerField", new String[]{""}));
  }

  @Test
  public void testDecimalField() throws Exception {
    tFixture.populateFieldWith("DecimalField", new String[] { "2.2" });
    assertTrue(tFixture.verifyFieldContains("DecimalField", new String[] { "2.2" }));
    assertEquals("2.2", tFixture.getFieldValue("DecimalField"));

    assertTrue(tFixture.verifyFieldContains("RODecimalField", new String[] { "2.2" }));
    assertEquals("2.2", tFixture.getFieldValue("RODecimalField"));

    // Grid
    tFixture.populateGridColumnRowWith("[1]", "DecimalField", "[1]", new String[] { "2.2" });
    assertTrue(tFixture.verifyGridColumnRowContains("[1]", "DecimalField", "[1]", new String[] { "2.2" }));
    assertEquals("2.2", tFixture.getGridColumnRowValue("[1]", "DecimalField", "[1]"));

    assertTrue(tFixture.verifyGridColumnRowContains("[1]", "DecimalField", "[2]", new String[] { "2.2" }));
    assertEquals("2.2", tFixture.getGridColumnRowValue("[1]", "DecimalField", "[2]"));

    // TODO Clear
    // tFixture.clearField("DecimalField");
    // assertTrue(tFixture.verifyFieldContains("DecimalField", new String[]{""}));
  }

  @Test
  public void testDateField() throws Exception {
    SimpleDateFormat df = new SimpleDateFormat(tFixture.getSettings().getDateFormat());
    SimpleDateFormat ddf = new SimpleDateFormat(tFixture.getSettings().getDateDisplayFormat());

    tFixture.populateFieldWith("DateField", new String[] { "+1 day" });
    assertTrue(tFixture.verifyFieldContains("DateField", new String[] { "+1 day" }));
    assertEquals(df.format(DateUtils.addDays(tFixture.getSettings().getStartDatetime(), 1)), tFixture.getFieldValue("DateField"));

    assertTrue(tFixture.verifyFieldContains("RODateField", new String[] { "+1 day" }));
    assertEquals(ddf.format(DateUtils.addDays(tFixture.getSettings().getStartDatetime(), 1)), tFixture.getFieldValue("RODateField"));

    tFixture.populateFieldWith("DateField[2]", new String[] { "2015-11-15" });
    assertTrue(tFixture.verifyFieldContains("DateField[2]", new String[] { "2015-11-15" }));
    assertEquals(df.format(AppianObject.parseDate("2015-11-15", tFixture.getSettings())), tFixture.getFieldValue("DateField[2]"));

    tFixture.populateFieldWith("DateField[3]", new String[] { "+5 days" });
    assertTrue(tFixture.verifyFieldContains("DateField[3]", new String[] { "+5 days" }));
    assertEquals(df.format(DateUtils.addDays(tFixture.getSettings().getStartDatetime(), 5)), tFixture.getFieldValue("DateField[3]"));

    // Grid
    tFixture.populateGridColumnRowWith("EditableGrid", "[7]", "[1]", new String[] { "+1 day" });
    assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "[7]", "[1]", new String[] { "+1 day" }));
    assertEquals(df.format(DateUtils.addDays(tFixture.getSettings().getStartDatetime(), 1)),
      tFixture.getGridColumnRowValue("EditableGrid", "[7]", "[1]"));

    // TODO Clear
  }

  @Test
  public void testDatetimeField() throws Exception {
    SimpleDateFormat dtf = new SimpleDateFormat(tFixture.getSettings().getDatetimeFormat());
    SimpleDateFormat ddtf = new SimpleDateFormat(tFixture.getSettings().getDatetimeDisplayFormat());

    // Check with relative time
    tFixture.populateFieldWith("DatetimeField", new String[] { "+1 hour" });
    assertTrue(tFixture.verifyFieldContains("DatetimeField", new String[] { "+1 hour" }));
    assertEquals(dtf.format(DateUtils.addHours(tFixture.getSettings().getStartDatetime(), 1)), tFixture.getFieldValue("DatetimeField"));

    assertTrue(tFixture.verifyFieldContains("RODatetimeField", new String[] { "+1 hour" }));
    assertEquals(ddtf.format(DateUtils.addHours(tFixture.getSettings().getStartDatetime(), 1)), tFixture.getFieldValue("RODatetimeField"));

    // Check with absolute time
    tFixture.populateFieldWith("DatetimeField", new String[] { "2015-11-15 14:00" });
    assertTrue(tFixture.verifyFieldContains("DatetimeField", new String[] { "2015-11-15 14:00" }));
    assertEquals(dtf.format(AppianObject.parseDate("2015-11-15 14:00", tFixture.getSettings())), tFixture.getFieldValue("DatetimeField"));

    assertTrue(tFixture.verifyFieldContains("RODatetimeField", new String[] { "2015-11-15 14:00" }));
    assertEquals(ddtf.format(AppianObject.parseDate("2015-11-15 14:00", tFixture.getSettings())),
      tFixture.getFieldValue("RODatetimeField"));

    tFixture.populateFieldWith("DatetimeField[2]", new String[] { "2015-11-15" });
    assertTrue(tFixture.verifyFieldContains("DatetimeField[2]", new String[] { "2015-11-15" }));
    assertEquals(dtf.format(AppianObject.parseDate("2015-11-15", tFixture.getSettings())), tFixture.getFieldValue("DatetimeField[2]"));

    tFixture.populateFieldWith("DatetimeField[3]", new String[] { "2015-11-15 14:00" });
    assertTrue(tFixture.verifyFieldContains("DatetimeField[3]", new String[] { "2015-11-15 14:00" }));
    assertEquals(dtf.format(AppianObject.parseDate("2015-11-15 14:00", tFixture.getSettings())),
      tFixture.getFieldValue("DatetimeField[3]"));

    tFixture.populateFieldWith("DatetimeField[4]", new String[] { "+5 minutes" });
    assertTrue(tFixture.verifyFieldContains("DatetimeField[4]", new String[] { "+5 minutes" }));
    assertEquals(dtf.format(DateUtils.addMinutes(tFixture.getSettings().getStartDatetime(), 5)),
      tFixture.getFieldValue("DatetimeField[4]"));

    tFixture.populateFieldWith("DatetimeField[5]", new String[] { "+5 hours" });
    assertTrue(tFixture.verifyFieldContains("DatetimeField[5]", new String[] { "+5 hours" }));
    assertEquals(dtf.format(DateUtils.addHours(tFixture.getSettings().getStartDatetime(), 5)), tFixture.getFieldValue("DatetimeField[5]"));

    tFixture.populateFieldWith("DatetimeField[6]", new String[] { "+5 days" });
    assertTrue(tFixture.verifyFieldContains("DatetimeField[6]", new String[] { "+5 days" }));
    assertEquals(dtf.format(DateUtils.addDays(tFixture.getSettings().getStartDatetime(), 5)), tFixture.getFieldValue("DatetimeField[6]"));

    // Grid
    tFixture.populateGridColumnRowWith("EditableGrid[2]", "DatetimeField", "[1]", new String[] { "+1 hour" });
    assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "DatetimeField", "[1]", new String[] { "+1 hour" }));
    assertEquals(dtf.format(DateUtils.addHours(tFixture.getSettings().getStartDatetime(), 1)),
      tFixture.getGridColumnRowValue("EditableGrid[2]", "DatetimeField", "[1]"));

    // tFixture.populateGridColumnRowWith("EditableGrid[2]", "[1]", "[2]", new String[]{"+1 minute"});
    // assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[1]", "[2]", new String[]{"+1 minute"}));
    // assertEquals(dtf.format(DateUtils.addMinutes(tFixture.getSettings().getStartDatetime(), 1)),
    // tFixture.getGridColumnRowValue("EditableGrid[2]", "[1]", "[2]"));

    // TODO Clear
  }

  @Test
  public void testSelectField() throws Exception {
    tFixture.populateFieldWithValue("SelectField", "Option 2");
    assertTrue(tFixture.verifyFieldContainsValue("SelectField", "Option 2"));
    assertEquals("Option 2", tFixture.getFieldValue("SelectField"));

    // Grid
    tFixture.populateGridColumnRowWith("EditableGrid[2]", "[2]", "[1]", new String[] { "Option 1" });
    assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[2]", "[1]", new String[] { "Option 1" }));
    assertEquals("Option 1", tFixture.getGridColumnRowValue("EditableGrid[2]", "[2]", "[1]"));

    // tFixture.populateGridColumnRowWith("EditableGrid[2]", "SelectField", "[2]", new String[]{"Option 2"});
    // assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "SelectField", "[2]", new String[]{"Option 2"}));
    // assertEquals("Option 2", tFixture.getGridColumnRowValue("EditableGrid[2]", "SelectField", "[2]"));

    // TODO Clear
  }

  @Test
  public void testMultipleSelectField() throws Exception {
    tFixture.populateFieldWith("MultipleSelectField", new String[] { "Option 1", "Option 2" });
    assertTrue(tFixture.verifyFieldContains("MultipleSelectField", new String[] { "Option 1", "Option 2" }));
    assertEquals("Option 1,Option 2", tFixture.getFieldValue("MultipleSelectField"));

    // TODO Clear
  }

  @Test
  public void testCheckboxField() throws Exception {
    tFixture.populateFieldWith("CheckboxField", new String[] { "Option 1", "Option 2" });
    assertTrue(tFixture.verifyFieldContains("CheckboxField", new String[] { "Option 1", "Option 2" }));
    assertEquals("Option 1,Option 2", tFixture.getFieldValue("CheckboxField"));

    // Grid
    tFixture.populateGridColumnRowWith("EditableGrid[2]", "[4]", "[1]", new String[] { "Option 1", "Option 2" });
    assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[4]", "[1]", new String[] { "Option 1", "Option 2" }));
    assertEquals("Option 1,Option 2", tFixture.getGridColumnRowValue("EditableGrid[2]", "[4]", "[1]"));

    // tFixture.populateGridColumnRowWith("EditableGrid[2]", "CheckboxField", "[2]", new String[]{"Option 2"});
    // assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "CheckboxField", "[2]", new String[]{"Option 2"}));
    // assertEquals("Option 2", tFixture.getGridColumnRowValue("EditableGrid[2]", "CheckboxField", "[2]"));

    // TODO Clear
  }

  @Test
  public void testRadioField() throws Exception {
    tFixture.populateFieldWith("RadioField", new String[] { "Option 1" });
    assertTrue(tFixture.verifyFieldContains("RadioField", new String[] { "Option 1" }));
    assertEquals("Option 1", tFixture.getFieldValue("RadioField"));

    // Grid
    tFixture.populateGridColumnRowWith("EditableGrid[2]", "RadioField", "[1]", new String[] { "Option 1" });
    assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "RadioField", "[1]", new String[] { "Option 1" }));
    assertEquals("Option 1", tFixture.getGridColumnRowValue("EditableGrid[2]", "RadioField", "[1]"));

    // tFixture.populateGridColumnRowWith("EditableGrid[2]", "[3]", "[2]", new String[]{"Option 2"});
    // assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[3]", "[2]", new String[]{"Option 2"}));
    // assertEquals("Option 2", tFixture.getGridColumnRowValue("EditableGrid[2]", "[3]", "[2]"));
  }

  @Test
  public void testFileUploadField() throws Exception {
    tFixture.populateFieldWith("FileUploadField", new String[] { AUTOMATED_TESTING_HOME + "\\documents\\Low.jpg" });
    assertTrue(tFixture.verifyFieldContains("FileUploadField", new String[] { AUTOMATED_TESTING_HOME + "\\documents\\Low.jpg" }));
    assertEquals("Low.jpg", tFixture.getFieldValue("FileUploadField")); // Notice this doesn't include the entire path

    // Grid
    tFixture.populateGridColumnRowWith("EditableGrid[2]", "FileUploadField", "[1]",
      new String[] { AUTOMATED_TESTING_HOME + "\\documents\\Medium.jpg" });
    assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "FileUploadField", "[1]",
      new String[] { AUTOMATED_TESTING_HOME + "\\documents\\Medium.jpg" }));
    assertEquals("Medium.jpg", tFixture.getGridColumnRowValue("EditableGrid[2]", "FileUploadField", "[1]"));

    // tFixture.populateGridColumnRowWith("EditableGrid[2]", "[5]", "[2]", new String[]{AUTOMATED_TESTING_HOME + "\\documents\\High.jpg"});
    // assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[5]", "[2]", new
    // String[]{AUTOMATED_TESTING_HOME + "\\documents\\High.jpg"}));
    // assertEquals("High.jpg", tFixture.getGridColumnRowValue("EditableGrid[2]", "[5]", "[2]"));

    // TODO Clear
  }

  @Test
  public void testPickerFields() throws Exception {
    tFixture.populateFieldWith("UserPicker", new String[] { "Michael Chirlin", "Dan Austria" });
    assertTrue(tFixture.verifyFieldContains("UserPicker", new String[] { "Michael Chirlin", "Dan Austria" }));
    assertEquals("Michael Chirlin,Dan Austria", tFixture.getFieldValue("UserPicker"));

    assertTrue(tFixture.verifyFieldContains("ROUserPicker", new String[] { "Michael Chirlin", "Dan Austria" }));
    assertEquals("Michael Chirlin,Dan Austria", tFixture.getFieldValue("ROUserPicker"));

    // tFixture.populateFieldWith("GroupPicker", new String[]{"AcqDemo"});
    // assertTrue(tFixture.verifyFieldContains("GroupPicker", new String[]{"AcqDemo"}));
    // assertEquals("AcqDemo", tFixture.getFieldValue("GroupPicker"));

    // tFixture.populateFieldWith("UserGroupPicker", new String[]{"Michael Chirlin"};
    // assertTrue(tFixture.verifyFieldContains("UserGroupPicker", new String[]{"Michael Chirlin"}));
    // assertEquals("Michael Chirlin", tFixture.getFieldValue("UserGroupPicker"));

    // tFixture.populateFieldWith("DocumentPicker", new String[]{"Jellyfish"});
    // assertTrue(tFixture.verifyFieldContains("DocumentPicker", new String[]{"Jellyfish.jpg"}));
    // assertEquals("Jellyfish.jpg", tFixture.getFieldValue("DocumentPicker")); // Note I need the extension here

    // tFixture.populateFieldWith("FolderPicker", new String[]{"Acceptance Letters"});
    // assertTrue(tFixture.verifyFieldContains("FolderPicker", new String[]{"Acceptance Letters"}));
    // assertEquals("Acceptance Letters", tFixture.getFieldValue("FolderPicker"));

    // tFixture.populateFieldWith("DocumentFolderPicker", new String[]{"Jellyfish"});
    // assertTrue(tFixture.verifyFieldContains("DocumentFolderPicker", new String[]{"Jellyfish"}));
    // assertEquals("Jellyfish.jpg", tFixture.getFieldValue("DocumentFolderPicker"));

    // tFixture.populateFieldWith("CustomPicker", new String[]{"5"});
    // assertTrue(tFixture.verifyFieldContains("CustomPicker", new String[]{"5"}));
    // assertEquals("5", tFixture.getFieldValue("CustomPicker"));

    // Grid
    tFixture.populateGridColumnRowWith("EditableGrid[3]", "[1]", "[1]", new String[] { "Michael Chirlin", "Dan Austria" });
    assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[1]", "[1]", new String[] { "Michael Chirlin", "Dan Austria" }));
    assertEquals("Michael Chirlin,Dan Austria", tFixture.getGridColumnRowValue("EditableGrid[3]", "[1]", "[1]"));

    // tFixture.populateGridColumnRowWith("EditableGrid[3]", "[2]", "[1]", new String[]{"AcqDemo"});
    // assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[2]", "[1]", new String[]{"AcqDemo"}));
    // assertEquals("AcqDemo", tFixture.getGridColumnRowValue("EditableGrid[3]", "[2]", "[1]"));

    // tFixture.populateGridColumnRowWith("EditableGrid[3]", "[3]", "[1]", new String[]{"Michael Chirlin"});
    // assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[3]", "[1]", new String[]{"Michael Chirlin"}));
    // assertEquals("Michael Chirlin", tFixture.getGridColumnRowValue("EditableGrid[3]", "[3]", "[1]"));

    // tFixture.populateGridColumnRowWith("EditableGrid[3]", "[4]", "[1]", new String[]{"Jellyfish"});
    // assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[4]", "[1]", new String[]{"Jellyfish"}));
    // assertEquals("Jellyfish.jpg", tFixture.getGridColumnRowValue("EditableGrid[3]", "[4]", "[1]"));

    // tFixture.populateGridColumnRowWith("EditableGrid[3]", "[1]", "[2]", new String[]{"Acceptance Letters"});
    // assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[1]", "[2]", new String[]{"Acceptance Letters"}));
    // assertEquals("Acceptance Letters", tFixture.getGridColumnRowValue("EditableGrid[3]", "[1]", "[2]"));

    // tFixture.populateGridColumnRowWith("EditableGrid[3]", "[2]", "[2]", new String[]{"Jellyfish"});
    // assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[2]", "[2]", new String[]{"Jellyfish"}));
    // assertEquals("Jellyfish.jpg", tFixture.getGridColumnRowValue("EditableGrid[3]", "[2]", "[2]"));

    // tFixture.populateGridColumnRowWith("EditableGrid[3]", "[3]", "[2]", new String[]{"10"});
    // assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[3]", "[2]", new String[]{"10"}));
    // assertEquals("10", tFixture.getGridColumnRowValue("EditableGrid[3]", "[3]", "[2]"));

    // TODO Clear
    // tFixture.clearFieldOf("UserPicker", new String[]{"Dan Austria"});
    // assertTrue(tFixture.verifyFieldContains("UserPicker", new String[]{"Michael Chirlin", "Dan Austria"}));

    // tFixture.clearField("UserPicker");
    // assertTrue(tFixture.verifyFieldContains("UserPicker", new String[]{""}));
  }

  @Test
  public void testFieldIndex() throws Exception {
    tFixture.populateFieldWith("TextField[2]", new String[] { "text2" });
    assertTrue(tFixture.verifyFieldContains("TextField[2]", new String[] { "text2" }));
    assertEquals(tFixture.getFieldValue("TextField[2]"), "text2");
  }

  @Test
  public void testPopulateFieldWithValue() throws Exception {
    tFixture.populateFieldWithValue("SelectField", "Option, 1");
    assertTrue(tFixture.verifyFieldContainsValue("SelectField", "Option, 1"));
    assertEquals("Option, 1", tFixture.getFieldValue("SelectField"));
  }

  @Test
  public void testPopulateFieldType() throws Exception {
    tFixture.populateFieldWith("TEXT", "[1]", new String[] { "Text index" });
    assertTrue(tFixture.verifyFieldContains("TextField", new String[] { "Text index" }));

    tFixture.populateFieldWith("PARAGRAPH", "[1]", new String[] { "Paragraph index" });
    assertTrue(tFixture.verifyFieldContains("ParagraphField", new String[] { "Paragraph index" }));

    tFixture.populateFieldWith("FILE_UPLOAD", "[2]", new String[] { AUTOMATED_TESTING_HOME + "\\documents\\Medium.jpg" });
    assertTrue(tFixture.verifyFieldContains("FileUploadField[2]", new String[] { AUTOMATED_TESTING_HOME + "\\documents\\Medium.jpg" }));

    try {
      tFixture.populateFieldWith("SELECT", "SelectField", new String[] { "Value" });
      fail("Should have thrown illegal argument exception");
    } catch (IllegalArgumentTestException e) {
    }
  }

  @Test
  public void testValidations() throws Exception {
    tFixture.clickOnButton("Submit");

    assertTrue(tFixture.verifyFieldContainsValidationMessage("RequiredField", new String[] { "A value is required" }));
    assertEquals("A value is required", tFixture.getFieldValidationMessage("RequiredField"));

    assertTrue(tFixture.verifySectionContainsValidationMessage("Section 6", new String[] { "RequiredField is required",
      "ValidationField is invalid" }));
    assertEquals("RequiredField is required,ValidationField is invalid", tFixture.getSectionValidationMessage("Section 6"));

    tFixture.populateFieldWith("ValidationField", new String[] { "-1" });
    assertTrue(tFixture.verifyFieldContainsValidationMessage("ValidationField", new String[] { "Value must be greater than 0",
      "Value must be even" }));
    assertEquals("Value must be greater than 0,Value must be even", tFixture.getFieldValidationMessage("ValidationField"));
  }

  @Test
  public void testPopulateFieldInSectionWith() throws Exception {
    tFixture.populateFieldInSectionWith("[1]", "Section 3", new String[] { "noLabel" });
    assertTrue(tFixture.verifyFieldInSectionContains("[1]", "Section 3", new String[] { "noLabel" }));
    assertEquals("noLabel", tFixture.getFieldInSectionValue("[1]", "Section 3"));
  }

  @Test
  public void testClickOnOptions() throws Exception {
    tFixture.clickOnRadioOption("Click Me!");
    tFixture.clickOnCheckboxOption("Click Me Too!");
    tFixture.clickOnRadioOption("Click Me[2]");
    tFixture.clickOnCheckboxOption("Click Me[2]");
  }

  @Test
  public void testSelectGridRow() throws Exception {
    assertFalse(tFixture.verifyGridRowIsSelected("EditableGrid", "[1]"));

    tFixture.selectGridRow("EditableGrid", "[1]");
    assertTrue(tFixture.verifyGridRowIsSelected("EditableGrid", "[1]"));

    assertFalse(tFixture.verifyGridRowIsSelected("PagingGrid", "[4]"));

    tFixture.selectGridRow("PagingGrid", "[4]");
    assertTrue(tFixture.verifyGridRowIsSelected("PagingGrid", "[4]"));
  }

  @Test
  public void testGridAddRowLink() throws Exception {
    tFixture.clickOnGridAddRowLink("[1]");
    tFixture.populateGridColumnRowWith("EditableGrid", "TextField", "[2]", new String[] { "Row 2" });
    assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "TextField", "[2]", new String[] { "Row 2" }));
  }

  @Test
  public void testVerifyButtons() throws Exception {
    assertTrue(tFixture.verifyButtonIsPresent("Cancel"));
    assertTrue(tFixture.verifyButtonIsNotPresent("FakeButton"));
    assertFalse(tFixture.verifyButtonIsNotPresent("Cancel"));
  }

  @Test
  public void testClickLink() throws Exception {
    tFixture.clickOnLink("Click link");
    assertTrue(tFixture.verifyFieldContains("Link Clicked", new String[] { "Clicked" }));

    tFixture.clickOnLink("Click link[2]");
    assertTrue(tFixture.verifyFieldContains("Link Clicked", new String[] { "Clicked again" }));
  }

  @Test
  public void testGridContains() throws Exception {
    assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "[2]", "[1]", new String[] { "Value 1" }));
  }

  @Test
  public void testGridPaging() throws Exception {
    assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1", "[1]", new String[] { "Value 1" }));
    tFixture.clickOnGridNavigation("PagingGrid", "next");
    assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1", "[1]", new String[] { "Value 6" }));
    tFixture.clickOnGridNavigation("[4]", "PREVIOUS");
    assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1", "[1]", new String[] { "Value 1" }));
    tFixture.clickOnGridNavigation("[4]", "last");
    assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1", "[1]", new String[] { "Value 16" }));
    tFixture.clickOnGridNavigation("PagingGrid", "FIRST");
    assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1", "[1]", new String[] { "Value 1" }));

    try {
      tFixture.clickOnGridNavigation("[4]", "Invalid");
      fail("Should have thrown illegal argument exception");
    } catch (IllegalArgumentTestException e) {
    }
  }

  @Test
  public void testGridSort() throws Exception {
    tFixture.sortGridByColumn("PagingGrid", "Column Label");
    assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1", "[1]", new String[] { "Value 1" }));
    tFixture.sortGridByColumn("[4]", "Column Label 1");
    assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "[2]", "[1]", new String[] { "Value 9" }));
    tFixture.sortGridByColumn("PagingGrid", "Column Label 2");
    assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 2", "[1]", new String[] { "Description 1" }));
    tFixture.sortGridByColumn("[4]", "Column Label 2");
    assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "[3]", "[1]", new String[] { "Description 9" }));
  }

  @Test
  public void testSectionExpandCollapse() throws Exception {
    tFixture.collapseSection("Section 1");
    assertTrue(tFixture.verifyFieldIsNotPresent("ROTextField"));
    tFixture.expandSection("Section 1");
    assertTrue(tFixture.verifyFieldIsPresent("ROTextField"));
  }

  @Test
  public void testVerifyLinkIsPresent() throws Exception {
    assertTrue(tFixture.verifyLinkFieldIsPresent("Add Data to Paging Grid"));
  }

  @Test
  public void testVerifyLinkURLContains() throws Exception {
    assertTrue(tFixture.verifyLinkURLContains("Add Data to Paging Grid", "appiancloud.com"));
    assertTrue(tFixture.verifyLinkURLContains("Safe Link", "https://google.com/"));
  }
  
  @Test
  public void testGetLinkURL() throws Exception {
    assertTrue(tFixture.getLinkURL("Safe Link").equals("https://google.com/"));
  }

  @AfterClass
  public static void tearDownInterface() throws Exception {
    tFixture.clickOnButton("Cancel");
  }
}