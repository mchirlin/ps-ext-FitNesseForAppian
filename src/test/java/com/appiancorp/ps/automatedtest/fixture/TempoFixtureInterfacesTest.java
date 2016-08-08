package com.appiancorp.ps.automatedtest.fixture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.exception.IllegalArgumentTestException;
import com.appiancorp.ps.automatedtest.test.AbstractLoginTest;

public class TempoFixtureInterfacesTest extends AbstractLoginTest {

  @BeforeClass
  public static void setUpInterface() {
    fixture.clickOnMenu("Actions");
    fixture.clickOnAction("All Fields");
  }

  /**** Form ****/

  @Test
  public void testFormTitle() throws Exception {
    assertEquals(fixture.getFormTitle(), "All Fields");
  }

  @Test
  public void testGetRegexGroupFromFormTitle() throws Exception {
    assertEquals(fixture.getRegexGroupFromFormTitle("([a-zA-Z ]+)", 1), "All Fields");
  }

  @Test
  public void testFormInstructions() throws Exception {
    assertEquals(fixture.getFormInstructions(), "Instructions");
  }

  /**** Text Field ****/

  @Test
  public void testTextField() throws Exception {
    fixture.populateFieldWith("TextField", new String[] { "text" });
    assertTrue(fixture.verifyFieldContains("TextField", new String[] { "text" }));
    assertEquals("text", fixture.getFieldValue("TextField"));

    assertTrue(fixture.verifyFieldContains("ROTextField", new String[] { "text" }));
    assertEquals("text", fixture.getFieldValue("ROTextField"));

    fixture.populateFieldWith("Field'With'Apostrophe", new String[] { "value" });
    assertTrue(fixture.verifyFieldContains("TextField", new String[] { "text" }));
    assertEquals("text", fixture.getFieldValue("TextField"));

    // TODO Fix this issue
    Assume.assumeFalse(isBrowser("CHROME"));

    // Editable Grid
    fixture.populateGridColumnRowWith("[1]", "Textfield", "[1]", new String[] { "gridText" });
    assertTrue(fixture.verifyGridColumnRowContains("[1]", "TextField", "[1]", new String[] { "gridText" }));
    assertEquals("gridText", fixture.getGridColumnRowValue("[1]", "TextField", "[1]"));

    // Read Only Grid
    assertTrue(fixture.verifyGridColumnRowContains("[2]", "TextField", "[1]", new String[] { "gridText" }));
    assertEquals("gridText", fixture.getGridColumnRowValue("[2]", "TextField", "[1]"));

    // Regex
    assertEquals("text", fixture.getRegexGroupFromFieldValue("([a-zA-Z0-9]{4})", 1, "TextField"));
    assertEquals("grid", fixture.getRegexGroupFromGridColumnRowValue("([a-zA-Z0-9]{4})", 1, "[2]", "TextField", "[1]"));

    fixture.clearField("TextField");
    assertEquals("", fixture.getFieldValue("TextField"));
  }

  @Test
  public void testTextFieldIndex() throws Exception {
    fixture.populateFieldWith("TextField[2]", new String[] { "text2" });
    assertTrue(fixture.verifyFieldContains("TextField[2]", new String[] { "text2" }));
    assertEquals(fixture.getFieldValue("TextField[2]"), "text2");
  }

  @Test
  public void testTextFieldPopulateType() throws Exception {
    fixture.populateFieldWith("TEXT", "[1]", new String[] { "Text index" });
    assertTrue(fixture.verifyFieldContains("Textfield", new String[] { "Text index" }));
  }

  /**** Encrypted Text Field ****/

  @Test
  public void testEncryptedTextField() throws Exception {
    fixture.populateFieldWithValue("[5]", "encrypted");
    assertTrue(fixture.verifyFieldContainsValue("[5]", "encrypted"));
    assertEquals("encrypted", fixture.getFieldValue("EncryptedTextField"));

    assertTrue(fixture.verifyFieldContains("ROEncryptedTextField", new String[] { "encrypted" }));
    assertEquals("encrypted", fixture.getFieldValue("ROEncryptedTextField"));

    // Editable Grid
    fixture.populateGridColumnRowWith("EditableGrid", "encryptedTextField", "[1]", new String[] { "gridEncrypted" });
    assertTrue(fixture.verifyGridColumnRowContains("EditableGrid", "EncryptedTextField", "[1]", new String[] { "gridEncrypted" }));
    assertEquals("gridEncrypted", fixture.getGridColumnRowValue("EditableGrid", "EncryptedTextField", "[1]"));

    // Read Only Grid
    assertTrue(fixture.verifyGridColumnRowContains("ReadOnlyGrid", "EncryptedTextField", "[1]", new String[] { "gridEncrypted" }));
    assertEquals("gridEncrypted", fixture.getGridColumnRowValue("ReadOnlyGrid", "EncryptedTextField", "[1]"));

    fixture.clearField("[5]");
    assertTrue(fixture.verifyFieldContains("[5]", new String[] { "" }));
  }

  /**** Paragraph Field ****/

  @Test
  public void testParagraphField() throws Exception {
    fixture.populateFieldWith("[3]", new String[] { "paragraph" });
    assertTrue(fixture.verifyFieldContains("[3]", new String[] { "paragraph" }));
    assertEquals("paragraph", fixture.getFieldValue("[3]"));

    assertTrue(fixture.verifyFieldContains("RoParagraphField", new String[] { "paragraph" }));
    assertEquals("paragraph", fixture.getFieldValue("ROParagraphField"));

    // Editable Grid
    fixture.populateGridColumnRowWith("EditableGrid", "[3]", "[1]", new String[] { "gridParagraph" });
    assertTrue(fixture.verifyGridColumnRowContains("EditableGrid", "[3]", "[1]", new String[] { "gridParagraph" }));
    assertEquals("gridParagraph", fixture.getGridColumnRowValue("EditableGrid", "[3]", "[1]"));

    // Read Only Grid
    assertTrue(fixture.verifyGridColumnRowContains("ReadOnlyGrid", "[2]", "[1]", new String[] { "gridParagraph" }));
    assertEquals("gridParagraph", fixture.getGridColumnRowValue("ReadOnlyGrid", "[2]", "[1]"));

    fixture.clearField("ParagraphField[1]");
    assertEquals("", fixture.getFieldValue("ParagraphField[1]"));
  }

  @Test
  public void testParagraphPopulateFieldType() throws Exception {
    fixture.populateFieldWith("PARAGRAPH", "[1]", new String[] { "Paragraph index" });
    assertTrue(fixture.verifyFieldContains("Paragraphfield", new String[] { "Paragraph index" }));
  }

  /**** Integer Field ****/

  @Test
  public void testIntegerField() throws Exception {
    fixture.populateFieldWith("IntegerField", new String[] { "1" });
    assertTrue(fixture.verifyFieldContains("IntegerField", new String[] { "1" }));
    assertEquals("1", fixture.getFieldValue("IntegerField"));

    assertTrue(fixture.verifyFieldContains("ROIntegerField", new String[] { "1" }));
    assertEquals("1", fixture.getFieldValue("ROIntegerField"));

    // Editable Grid
    fixture.populateGridColumnRowWith("[1]", "[5]", "[1]", new String[] { "1" });
    assertTrue(fixture.verifyGridColumnRowContains("[1]", "[5]", "[1]", new String[] { "1" }));
    assertEquals("1", fixture.getGridColumnRowValue("[1]", "[5]", "[1]"));

    // Read Only Grid
    assertTrue(fixture.verifyGridColumnRowContains("[2]", "[4]", "[1]", new String[] { "1" }));
    assertEquals("1", fixture.getGridColumnRowValue("[2]", "[4]", "[1]"));

    // TODO Clear
    // fixture.clearField("IntegerField");
    // assertTrue(fixture.verifyFieldContains("IntegerField", new String[]{""}));
  }

  /**** Decimal Field ****/

  @Test
  public void testDecimalField() throws Exception {
    fixture.populateFieldWith("DecimalField", new String[] { "2.2" });
    assertTrue(fixture.verifyFieldContains("decimalField", new String[] { "2.2" }));
    assertEquals("2.2", fixture.getFieldValue("DecimalField"));

    assertTrue(fixture.verifyFieldContains("RODecimalField", new String[] { "2.2" }));
    assertEquals("2.2", fixture.getFieldValue("RODecimalField"));

    // TODO Fix this issue
    Assume.assumeFalse(isBrowser("CHROME"));

    // Editable Grid
    fixture.populateGridColumnRowWith("[1]", "DecimalField", "[1]", new String[] { "2.2" });
    assertTrue(fixture.verifyGridColumnRowContains("[1]", "DecimalField", "[1]", new String[] { "2.2" }));
    assertEquals("2.2", fixture.getGridColumnRowValue("[1]", "DecimalField", "[1]"));

    // Read Only Grid
    assertTrue(fixture.verifyGridColumnRowContains("[1]", "DecimalField", "[1]", new String[] { "2.2" }));
    assertEquals("2.2", fixture.getGridColumnRowValue("[1]", "DecimalField", "[1]"));

    // TODO Clear
    // fixture.clearField("DecimalField");
    // assertTrue(fixture.verifyFieldContains("DecimalField", new String[]{""}));
  }

  /**** Dropdown Field ****/

  @Test
  public void testDropdownField() throws Exception {
    fixture.populateFieldWithValue("Dropdownfield[1]", "Option 2");
    assertTrue(fixture.verifyFieldContainsValue("DropdownField[1]", "Option 2"));
    assertEquals("Option 2", fixture.getFieldValue("Dropdownfield"));

    // Grid
    fixture.populateGridColumnRowWith("EditableGrid[2]", "[2]", "[1]", new String[] { "[1]" });
    assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[2]", "[2]", "[1]", new String[] { "[1]" }));
    assertEquals("Option 1", fixture.getGridColumnRowValue("EditableGrid[2]", "[2]", "[1]"));

    // fixture.populateGridColumnRowWith("EditableGrid[2]", "DropdownField", "[2]", new String[]{"Option 2"});
    // assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[2]", "DropdownField", "[2]", new String[]{"Option 2"}));
    // assertEquals("Option 2", fixture.getGridColumnRowValue("EditableGrid[2]", "DropdownField", "[2]"));

    // TODO Clear
  }

  @Test
  public void testDropdownFieldPopulateWithValue() throws Exception {
    fixture.populateFieldWithValue("DropdownField", "Option, 1");
    assertTrue(fixture.verifyFieldContainsValue("dropdownField", "Option, 1"));
    assertEquals("Option, 1", fixture.getFieldValue("DropdownField"));
  }

  @Test
  public void testDropdownFieldPopulateType() throws Exception {
    try {
      fixture.populateFieldWith("DROPDOWN", "DropdownField", new String[] { "Value" });
      fail("Should have thrown illegal argument exception");
    } catch (IllegalArgumentTestException e) {
    }
  }

  /**** Multiple Dropdown Field ****/

  @Test
  public void testMultipleDropdownField() throws Exception {
    fixture.populateFieldWith("MultipleDropdownField", new String[] { "Option 1", "Option 2" });
    assertTrue(fixture.verifyFieldContains("MultipleDropdownField", new String[] { "Option 1", "Option 2" }));
    assertEquals("Option 1,Option 2", fixture.getFieldValue("MultipleDropdownField"));

    // TODO Clear
  }

  /**** Checkbox Field ****/

  @Test
  public void testCheckboxField() throws Exception {
    fixture.populateFieldWith("CheckboxField", new String[] { "Option 1", "Option 2" });
    assertTrue(fixture.verifyFieldContains("CheckboxField", new String[] { "Option 1", "Option 2" }));
    assertEquals("Option 1,Option 2", fixture.getFieldValue("CheckboxField"));

    // Grid
    fixture.populateGridColumnRowWith("[3]", "[4]", "[1]", new String[] { "Option 1", "[2]" });
    assertTrue(fixture.verifyGridColumnRowContains("[3]", "[4]", "[1]", new String[] { "Option 1", "Option 2" }));
    assertEquals("Option 1,Option 2", fixture.getGridColumnRowValue("EditableGrid[2]", "[4]", "[1]"));

    fixture.populateGridColumnRowWith("EditableGrid[2]", "CheckboxField", "[2]", new String[] { "[1]" });
    assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[2]", "CheckboxField", "[2]", new String[] { "[1]" }));

    // TODO Read Only
    // TODO Clear
  }

  @Test
  public void testCheckboxFieldOptions() throws Exception {
    fixture.clickOnCheckboxOption("Click Me Too!");
    fixture.clickOnCheckboxOption("Click Me[2]");
  }

  /**** Radio Field ****/

  @Test
  public void testRadioField() throws Exception {
    fixture.populateFieldWith("RadioField", new String[] { "[1]" });
    assertTrue(fixture.verifyFieldContains("RadioField", new String[] { "[1]" }));
    assertEquals("Option 1", fixture.getFieldValue("RadioField"));

    // TODO Fix this issue
    Assume.assumeFalse(isBrowser("CHROME"));

    // Grid
    fixture.populateGridColumnRowWith("EditableGrid[2]", "RadioField", "[1]", new String[] { "Option 1" });
    assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[2]", "RadioField", "[1]", new String[] { "Option 1" }));
    assertEquals("Option 1", fixture.getGridColumnRowValue("EditableGrid[2]", "RadioField", "[1]"));

    // fixture.populateGridColumnRowWith("EditableGrid[2]", "[3]", "[2]", new String[]{"Option 2"});
    // assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[2]", "[3]", "[2]", new String[]{"Option 2"}));
    // assertEquals("Option 2", fixture.getGridColumnRowValue("EditableGrid[2]", "[3]", "[2]"));
  }

  @Test
  public void testRadioFieldOptions() throws Exception {
    fixture.clickOnRadioOption("Click Me!");
    fixture.clickOnRadioOption("Click Me[2]");
  }

  /**** Date Field ****/

  @Test
  public void testDateField() throws Exception {
    SimpleDateFormat df = new SimpleDateFormat(fixture.getSettings().getDateFormat());
    SimpleDateFormat ddf = new SimpleDateFormat(fixture.getSettings().getDateDisplayFormat());

    fixture.populateFieldWith("DateField", new String[] { "+1 month" });
    assertTrue(fixture.verifyFieldContains("DateField", new String[] { "+1 month" }));
    assertEquals(df.format(DateUtils.addMonths(fixture.getSettings().getStartDatetime(), 1)), fixture.getFieldValue("DateField"));

    assertTrue(fixture.verifyFieldContains("RODateField", new String[] { "+1 month" }));
    assertEquals(ddf.format(DateUtils.addMonths(fixture.getSettings().getStartDatetime(), 1)), fixture.getFieldValue("RODateField"));

    fixture.populateFieldWith("DateField[2]", new String[] { "2015-11-15" });
    assertTrue(fixture.verifyFieldContains("DateField[2]", new String[] { "2015-11-15" }));
    assertEquals(df.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2015-11-15")),
      fixture.getFieldValue("DateField[2]"));

    fixture.populateFieldWith("DateField[3]", new String[] { "+5 days" });
    assertTrue(fixture.verifyFieldContains("DateField[3]", new String[] { "+5 days" }));
    assertEquals(df.format(DateUtils.addDays(fixture.getSettings().getStartDatetime(), 5)), fixture.getFieldValue("DateField[3]"));

    // Editable Grid
    fixture.populateGridColumnRowWith("EditableGrid", "[7]", "[1]", new String[] { "+1 year" });
    assertTrue(fixture.verifyGridColumnRowContains("EditableGrid", "[7]", "[1]", new String[] { "+1 year" }));
    assertEquals(df.format(DateUtils.addYears(fixture.getSettings().getStartDatetime(), 1)),
      fixture.getGridColumnRowValue("EditableGrid", "[7]", "[1]"));

    // Read Only Grid
    assertTrue(fixture.verifyGridColumnRowContains("ReadOnlyGrid", "[6]", "[1]", new String[] { "+1 year" }));
    assertEquals(ddf.format(DateUtils.addYears(fixture.getSettings().getStartDatetime(), 1)),
      fixture.getGridColumnRowValue("ReadOnlyGrid", "[6]", "[1]"));

    // TODO Clear
  }

  /**** Datetime Field ****/

  @Test
  public void testDatetimeField() throws Exception {
    SimpleDateFormat dtf = new SimpleDateFormat(fixture.getSettings().getDatetimeFormat());
    SimpleDateFormat ddtf = new SimpleDateFormat(fixture.getSettings().getDatetimeDisplayFormat());

    // Check with relative time
    fixture.populateFieldWith("DatetimeField", new String[] { "+1 hour" });
    assertTrue(fixture.verifyFieldContains("DatetimeField", new String[] { "+1 hour" }));
    assertEquals(dtf.format(DateUtils.addHours(fixture.getSettings().getStartDatetime(), 1)), fixture.getFieldValue("DatetimeField"));

    assertTrue(fixture.verifyFieldContains("RODatetimeField", new String[] { "+1 hour" }));
    assertEquals(ddtf.format(DateUtils.addHours(fixture.getSettings().getStartDatetime(), 1)), fixture.getFieldValue("RODatetimeField"));

    // Check with absolute time
    fixture.populateFieldWith("DatetimeField", new String[] { "2015-11-15 14:00" });
    assertTrue(fixture.verifyFieldContains("DatetimeField", new String[] { "2015-11-15 14:00" }));
    assertEquals(dtf.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2015-11-15 14:00")),
      fixture.getFieldValue("DatetimeField"));

    assertTrue(fixture.verifyFieldContains("RODatetimeField", new String[] { "2015-11-15 14:00" }));
    assertEquals(ddtf.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2015-11-15 14:00")),
      fixture.getFieldValue("RODatetimeField"));

    fixture.populateFieldWith("DatetimeField[2]", new String[] { "2015-11-15" });
    assertTrue(fixture.verifyFieldContains("DatetimeField[2]", new String[] { "2015-11-15" }));
    assertEquals(dtf.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2015-11-15")),
      fixture.getFieldValue("DatetimeField[2]"));

    fixture.populateFieldWith("DatetimeField[3]", new String[] { "2015-11-15 14:00" });
    assertTrue(fixture.verifyFieldContains("DatetimeField[3]", new String[] { "2015-11-15 14:00" }));
    assertEquals(dtf.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2015-11-15 14:00")),
      fixture.getFieldValue("DatetimeField[3]"));

    fixture.populateFieldWith("DatetimeField[4]", new String[] { "+5 minutes" });
    assertTrue(fixture.verifyFieldContains("DatetimeField[4]", new String[] { "+5 minutes" }));
    assertEquals(dtf.format(DateUtils.addMinutes(fixture.getSettings().getStartDatetime(), 5)),
      fixture.getFieldValue("DatetimeField[4]"));

    fixture.populateFieldWith("DatetimeField[5]", new String[] { "+5 hours" });
    assertTrue(fixture.verifyFieldContains("DatetimeField[5]", new String[] { "+5 hours" }));
    assertEquals(dtf.format(DateUtils.addHours(fixture.getSettings().getStartDatetime(), 5)), fixture.getFieldValue("DatetimeField[5]"));

    fixture.populateFieldWith("DatetimeField[6]", new String[] { "+5 days" });
    assertTrue(fixture.verifyFieldContains("DatetimeField[6]", new String[] { "+5 days" }));
    assertEquals(dtf.format(DateUtils.addDays(fixture.getSettings().getStartDatetime(), 5)), fixture.getFieldValue("DatetimeField[6]"));

    // TODO Fix this issue
    Assume.assumeFalse(isBrowser("CHROME"));

    // Grid
    fixture.populateGridColumnRowWith("EditableGrid[2]", "DatetimeField", "[1]", new String[] { "+1 hour" });
    assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[2]", "DatetimeField", "[1]", new String[] { "+1 hour" }));
    assertEquals(dtf.format(DateUtils.addHours(fixture.getSettings().getStartDatetime(), 1)),
      fixture.getGridColumnRowValue("EditableGrid[2]", "DatetimeField", "[1]"));

    // fixture.populateGridColumnRowWith("EditableGrid[2]", "[1]", "[2]", new String[]{"+1 minute"});
    // assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[2]", "[1]", "[2]", new String[]{"+1 minute"}));
    // assertEquals(dtf.format(DateUtils.addMinutes(fixture.getSettings().getStartDatetime(), 1)),
    // fixture.getGridColumnRowValue("EditableGrid[2]", "[1]", "[2]"));

    // TODO Clear
  }

  /**** Picker Field ****/

  @Test
  public void testPickerFields() throws Exception {
    fixture.populateFieldWith("UserPicker", new String[] { "Test User", "test twoser" });
    assertTrue(fixture.verifyFieldContains("Userpicker", new String[] { "Test User", "Test Twoser" }));
    assertEquals("Test User,Test Twoser", fixture.getFieldValue("UserPicker"));

    assertTrue(fixture.verifyFieldContains("ROUserPicker", new String[] { "Test User", "Test Twoser" }));
    assertEquals("Test User,Test Twoser", fixture.getFieldValue("ROUserPicker"));

    fixture.clearFieldOf("UserPicker", new String[] { "test user" });
    assertFalse(fixture.verifyFieldContains("UserPicker", new String[] { "Test User" }));
    fixture.clearFieldOf("UserPicker[1]", new String[] { "Test Twoser" });
    assertFalse(fixture.verifyFieldContains("UserPicker[1]", new String[] { "Test Twoser" }));

    // fixture.populateFieldWith("GroupPicker", new String[]{"AcqDemo"});
    // assertTrue(fixture.verifyFieldContains("GroupPicker", new String[]{"AcqDemo"}));
    // assertEquals("AcqDemo", fixture.getFieldValue("GroupPicker"));

    // fixture.populateFieldWith("UserGroupPicker", new String[]{"Test User"};
    // assertTrue(fixture.verifyFieldContains("UserGroupPicker", new String[]{"Test User"}));
    // assertEquals("Test User", fixture.getFieldValue("UserGroupPicker"));

    // fixture.populateFieldWith("DocumentPicker", new String[]{"Jellyfish"});
    // assertTrue(fixture.verifyFieldContains("DocumentPicker", new String[]{"Jellyfish.jpg"}));
    // assertEquals("Jellyfish.jpg", fixture.getFieldValue("DocumentPicker")); // Note I need the extension here

    // fixture.populateFieldWith("FolderPicker", new String[]{"Acceptance Letters"});
    // assertTrue(fixture.verifyFieldContains("FolderPicker", new String[]{"Acceptance Letters"}));
    // assertEquals("Acceptance Letters", fixture.getFieldValue("FolderPicker"));

    // fixture.populateFieldWith("DocumentFolderPicker", new String[]{"Jellyfish"});
    // assertTrue(fixture.verifyFieldContains("DocumentFolderPicker", new String[]{"Jellyfish"}));
    // assertEquals("Jellyfish.jpg", fixture.getFieldValue("DocumentFolderPicker"));

    // fixture.populateFieldWith("CustomPicker", new String[]{"5"});
    // assertTrue(fixture.verifyFieldContains("CustomPicker", new String[]{"5"}));
    // assertEquals("5", fixture.getFieldValue("CustomPicker"));

    // Grid
    fixture.populateGridColumnRowWith("EditableGrid[4]", "[1]", "[1]", new String[] { "Test User", "Test Twoser" });
    assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[4]", "[1]", "[1]", new String[] { "Test User", "Test Twoser" }));
    assertEquals("Test User,Test Twoser", fixture.getGridColumnRowValue("EditableGrid[4]", "[1]", "[1]"));

    // fixture.populateGridColumnRowWith("EditableGrid[4]", "[2]", "[1]", new String[]{"AcqDemo"});
    // assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[4]", "[2]", "[1]", new String[]{"AcqDemo"}));
    // assertEquals("AcqDemo", fixture.getGridColumnRowValue("EditableGrid[4]", "[2]", "[1]"));

    // fixture.populateGridColumnRowWith("EditableGrid[4]", "[3]", "[1]", new String[]{"Test User"});
    // assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[4]", "[3]", "[1]", new String[]{"Test User"}));
    // assertEquals("Test User", fixture.getGridColumnRowValue("EditableGrid[4]", "[3]", "[1]"));

    // fixture.populateGridColumnRowWith("EditableGrid[4]", "[4]", "[1]", new String[]{"Jellyfish"});
    // assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[4]", "[4]", "[1]", new String[]{"Jellyfish"}));
    // assertEquals("Jellyfish.jpg", fixture.getGridColumnRowValue("EditableGrid[4]", "[4]", "[1]"));

    // fixture.populateGridColumnRowWith("EditableGrid[4]", "[1]", "[2]", new String[]{"Acceptance Letters"});
    // assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[4]", "[1]", "[2]", new String[]{"Acceptance Letters"}));
    // assertEquals("Acceptance Letters", fixture.getGridColumnRowValue("EditableGrid[4]", "[1]", "[2]"));

    // fixture.populateGridColumnRowWith("EditableGrid[4]", "[2]", "[2]", new String[]{"Jellyfish"});
    // assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[4]", "[2]", "[2]", new String[]{"Jellyfish"}));
    // assertEquals("Jellyfish.jpg", fixture.getGridColumnRowValue("EditableGrid[4]", "[2]", "[2]"));

    // fixture.populateGridColumnRowWith("EditableGrid[4]", "[3]", "[2]", new String[]{"10"});
    // assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[4]", "[3]", "[2]", new String[]{"10"}));
    // assertEquals("10", fixture.getGridColumnRowValue("EditableGrid[4]", "[3]", "[2]"));

    // TODO Clear
    // fixture.clearField("UserPicker");
    // assertTrue(fixture.verifyFieldContains("UserPicker", new String[]{""}));
  }

  /**** Milestone Field ****/
  @Test
  public void testMilestoneField() throws Exception {
    assertTrue(fixture.verifyFieldIsPresent("Milestone"));

    Assume.assumeTrue(atLeastVersion(7.11));

    fixture.clickOnMilestoneStep("Milestone", "Step 1");
    assertTrue(fixture.verifyMilestoneStepIs("Milestone", new String[] { "Step 1" }));
    assertEquals("Step 1", fixture.getMilestoneStep("Milestone"));

    fixture.clickOnMilestoneStep("Milestone[1]", "[2]");
    assertTrue(fixture.verifyMilestoneStepIs("Milestone", new String[] { "Step 2" }));

    fixture.clickOnMilestoneStep("[1]", "[4]");
  }

  /**** Link Field ****/

  @Test
  public void testLinkField() throws Exception {
    assertTrue(fixture.verifyLinkIsPresent("Safe Link"));

    assertTrue(fixture.verifyLinkURLContains("Safe Link", "https://google.com/"));
    assertTrue(fixture.verifyLinkURLContains("Strong Style Link", "suite"));

    assertTrue(fixture.getLinkURL("Safe Link").equals("https://google.com/"));
    assertTrue(fixture.getLinkURL("Strong Style Link").contains("suite"));

    fixture.clickOnLink("Click link");
    assertTrue(fixture.verifyFieldContains("Link Clicked", new String[] { "Clicked" }));

    fixture.clickOnLink("Click link[2]");
    assertTrue(fixture.verifyFieldContains("Link Clicked", new String[] { "Clicked again" }));

    fixture.clickOnLink("Strong Style Link");
  }

  /**** File Upload Field ****/

  @Test
  public void testFileUploadField() throws Exception {
    fixture.populateFieldWith("FileUploadField", new String[] { fixture.getProps().getProperty(Constants.AUTOMATED_TESTING_HOME) +
      "/FitNesseRoot/files/images/Fitnesse-Start.png" });
    assertTrue(fixture.verifyFieldContains(
      "FileUploadField",
      new String[] { fixture.getProps().getProperty(Constants.AUTOMATED_TESTING_HOME) + "/FitNesseRoot/files/images/Fitnesse-Start.png" }));
    assertEquals("Fitnesse-Start.png", fixture.getFieldValue("FileUploadField")); // Notice this doesn't include the entire path

    Assume.assumeTrue(atLeastVersion(7.11));

    // TODO Fix this issue
    Assume.assumeFalse(isBrowser("CHROME"));

    // Grid
    fixture.populateGridColumnRowWith(
      "EditableGrid[3]",
      "FileUploadField",
      "[1]",
      new String[] { fixture.getProps().getProperty(Constants.AUTOMATED_TESTING_HOME) + "/FitNesseRoot/files/images/Fitnesse-Start.png" });
    assertTrue(fixture.verifyGridColumnRowContains(
      "EditableGrid[3]",
      "FileUploadField",
      "[1]",
      new String[] { fixture.getProps().getProperty(Constants.AUTOMATED_TESTING_HOME) + "/FitNesseRoot/files/images/Fitnesse-Start.png" }));
    assertEquals("Fitnesse-Start.png", fixture.getGridColumnRowValue("EditableGrid[3]", "FileUploadField", "[1]"));

    // fixture.populateGridColumnRowWith("EditableGrid[2]", "[5]", "[2]", new String[]{AUTOMATED_TESTING_HOME + "/documents/High.jpg"});
    // assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[2]", "[5]", "[2]", new
    // String[]{AUTOMATED_TESTING_HOME + "/documents/High.jpg"}));
    // assertEquals("High.jpg", fixture.getGridColumnRowValue("EditableGrid[2]", "[5]", "[2]"));

    // TODO Clear
  }

  @Test
  public void testFileUploadFieldPopulateType() throws Exception {
    fixture.populateFieldWith("FILE_UPLOAD", "[2]", new String[] { fixture.getProps().getProperty(Constants.AUTOMATED_TESTING_HOME) +
      "/FitNesseRoot/files/images/Fitnesse-Start.png" });
    assertTrue(fixture.verifyFieldContains(
      "FileUploadField[2]",
      new String[] { fixture.getProps().getProperty(Constants.AUTOMATED_TESTING_HOME) + "/FitNesseRoot/files/images/Fitnesse-Start.png" }));
  }

  /**** Image Field ****/

  @Test
  public void testImageField() throws Exception {
    assertTrue(fixture.verifyFieldContains("ImageField", new String[] { "not done" }));
    assertTrue(fixture.verifyFieldInSectionContains("ImageField", "Images", new String[] { "not done" }));
    assertEquals("not done", fixture.getFieldValue("ImageField"));

    // Grid
    assertEquals(fixture.getGridColumnRowValue("ImageGrid", "ImageField", "[1]"), "done");
    assertTrue(fixture.verifyGridColumnRowContains("ImageGrid", "ImageField", "[1]", new String[] { "done" }));
  }

  /**** Grid ****/

  @Test
  public void testGridContains() throws Exception {
    assertTrue(fixture.verifyGridColumnRowContains("PagingGrid", "[2]", "[1]", new String[] { "Value 1" }));
  }

  @Test
  public void testGridSort() throws Exception {
    // TODO Fix this issue
    Assume.assumeFalse(isBrowser("CHROME"));

    fixture.sortGridByColumn("PagingGrid", "Column Label");
    assertTrue(fixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1", "[1]", new String[] { "Value 1" }));
    fixture.sortGridByColumn("PagingGrid[1]", "Column Label 1");
    assertTrue(fixture.verifyGridColumnRowContains("PagingGrid[1]", "[2]", "[1]", new String[] { "Value 9" }));
    fixture.sortGridByColumn("PagingGrid[1]", "Column Label 2");
    assertTrue(fixture.verifyGridColumnRowContains("[6]", "Column Label 2", "[1]", new String[] { "Description 1" }));
    fixture.sortGridByColumn("[6]", "Column Label 2");
    assertTrue(fixture.verifyGridColumnRowContains("[6]", "[3]", "[1]", new String[] { "Description 9" }));
  }

  @Test
  public void testGridNavigation() throws Exception {
    // TODO Fix this issue
    Assume.assumeFalse(isBrowser("CHROME"));

    assertTrue(fixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1", "[1]", new String[] { "Value 1" }));
    fixture.clickOnGridNavigation("PagingGrid", "next");
    assertTrue(fixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1", "[1]", new String[] { "Value 6" }));
    fixture.clickOnGridNavigation("PagingGrid[1]", "PREVIOUS");
    assertTrue(fixture.verifyGridColumnRowContains("PagingGrid[1]", "Column Label 1", "[1]", new String[] { "Value 1" }));
    fixture.clickOnGridNavigation("PagingGrid[1]", "last");
    assertTrue(fixture.verifyGridColumnRowContains("[6]", "Column Label 1", "[1]", new String[] { "Value 16" }));
    fixture.clickOnGridNavigation("[6]", "FIRST");
    assertTrue(fixture.verifyGridColumnRowContains("[6]", "Column Label 1", "[1]", new String[] { "Value 1" }));

    try {
      fixture.clickOnGridNavigation("[6]", "Invalid");
      fail("Should have thrown illegal argument exception");
    } catch (IllegalArgumentTestException e) {
    }
  }

  @Test
  public void testGridRowSelect() throws Exception {
    assertFalse(fixture.verifyGridRowIsSelected("EditableGrid", "[1]"));
    fixture.selectGridRow("EditableGrid", "[1]");
    assertTrue(fixture.verifyGridRowIsSelected("EditableGrid", "[1]"));

    assertFalse(fixture.verifyGridRowIsSelected("PagingGrid[1]", "[4]"));
    fixture.selectGridRow("PagingGrid[1]", "[4]");
    assertTrue(fixture.verifyGridRowIsSelected("PagingGrid[1]", "[4]"));

    assertFalse(fixture.verifyGridRowIsSelected("[6]", "[3]"));
    fixture.selectGridRow("[6]", "[3]");
    assertTrue(fixture.verifyGridRowIsSelected("[6]", "[3]"));
  }

  @Test
  public void testGridAddRow() throws Exception {
    Assume.assumeTrue(atLeastVersion(7.11));

    fixture.clickOnGridAddRowLink("Editable");
    fixture.populateGridColumnRowWith("EditableGrid", "TextField", "[2]", new String[] { "Row 2" });
    assertTrue(fixture.verifyGridColumnRowContains("EditableGrid", "TextField", "[2]", new String[] { "Row 2" }));
    fixture.clickOnGridAddRowLink("EditableGrid[1]");
    fixture.clickOnGridAddRowLink("[1]");
  }

  @Test
  public void testGridSelectAll() throws Exception {
    fixture.selectAllRowsInGrid("PagingGrid");
    assertTrue(fixture.verifyGridRowIsSelected("PagingGrid", "[1]"));
    fixture.selectAllRowsInGrid("PagingGrid[1]");
    assertFalse(fixture.verifyGridRowIsSelected("PagingGrid", "[2]"));
    fixture.selectAllRowsInGrid("[6]");
  }

  @Test
  public void testGridRowCount() throws Exception {
    Integer count = fixture.countGridRows("EditableGrid");

    Assume.assumeTrue(atLeastVersion(7.11));
    fixture.clickOnGridAddRowLink("EditableGrid");
    assertEquals((long) count + 1, (long) fixture.countGridRows("EditableGrid"));
  }

  @Test
  public void testGridGetTotalCount() throws Exception {
    assertEquals(fixture.getGridTotalCount("PagingGrid"), 20);
  }

  @Test
  public void testGridGetRowCount() throws Exception {
    assertEquals(fixture.getGridRowCount("PagingGrid"), 5);
  }

  @Test
  public void testGridRegex() throws Exception {
    // Regex
    assertEquals(fixture.getRegexGroupFromGridColumnRowValue("([a-zA-Z0-9]{5})", 1, "PagingGrid", "Column Label 1", "[2]"), "Value");
  }

  /**** Button ****/

  @Test
  public void testButton() throws Exception {
    assertTrue(fixture.verifyButtonIsPresent("Cancel"));
    assertTrue(fixture.verifyButtonIsNotPresent("FakeButton"));
    assertFalse(fixture.verifyButtonIsNotPresent("Cancel"));
  }

  /**** Section ****/

  @Test
  public void testSectionPopulate() throws Exception {
    fixture.populateFieldInSectionWith("[1]", "Edge Cases", new String[] { "noLabel" });
    assertTrue(fixture.verifyFieldInSectionContains("[1]", "Edge Cases", new String[] { "noLabel" }));
    assertEquals("noLabel", fixture.getFieldInSectionValue("[1]", "Edge Cases"));

    fixture.populateFieldInSectionWith("TextField", "Edge Cases", new String[] { "textFieldInSection" });
    assertTrue(fixture.verifyFieldInSectionContains("TextField", "Edge Cases", new String[] { "textFieldInSection" }));
    assertEquals("textFieldInSection", fixture.getFieldInSectionValue("TextField", "Edge Cases"));

    // Regex
    assertEquals("text", fixture.getRegexGroupFromFieldInSectionValue("([a-zA-Z0-9]{4})", 1, "TextField", "Edge Cases"));
  }

  @Test
  public void testSectionToggle() throws Exception {
    fixture.toggleSectionVisibility("Basic Fields");
    assertTrue(fixture.verifyFieldIsNotPresent("ROTextField"));
    fixture.toggleSectionVisibility("Basic Fields");
    assertTrue(fixture.verifyFieldIsPresent("ROTextField"));
  }

  @Test
  public void testSectionValidation() throws Exception {
    fixture.clickOnButton("Submit");

    assertTrue(fixture.verifySectionContainsValidationMessage("Validations", new String[] { "RequiredField is required",
      "ValidationField is invalid" }));
    assertEquals("RequiredField is required,ValidationField is invalid", fixture.getSectionValidationMessage("Validation"));
  }

  /**** Validation ****/

  @Test
  public void testFieldValidations() throws Exception {
    fixture.clickOnButton("Submit");

    assertTrue(fixture.verifyFieldContainsValidationMessage("RequiredField", new String[] { "A value is required" }));
    assertTrue(fixture.verifyFieldContainsValidationMessage("RequiredField[1]", new String[] { "A value is required" }));
    assertTrue(fixture.verifyFieldContainsValidationMessage("RequiredField", new String[] { "A value is required" }));
    assertEquals("A value is required", fixture.getFieldValidationMessage("RequiredField"));

    fixture.populateFieldWith("ValidationField", new String[] { "-1" });
    assertTrue(fixture.verifyFieldContainsValidationMessage("ValidationField", new String[] { "Value must be greater than 0",
      "Value must be even" }));
    assertEquals("Value must be greater than 0,Value must be even", fixture.getFieldValidationMessage("ValidationField"));
  }

  @Test
  public void testFieldPresence() throws Exception {
    assertTrue(fixture.verifyFieldIsPresent("TextField"));
    assertTrue(fixture.verifyFieldIsPresent("TextField[1]"));
    assertTrue(fixture.verifyFieldIsPresent("[1]"));

    assertTrue(fixture.verifyFieldIsNotPresent("TestField"));
    assertTrue(fixture.verifyFieldIsNotPresent("TextField[10]"));
    assertTrue(fixture.verifyFieldIsNotPresent("[1000]"));
  }

  @Test
  public void testSaveChanges() throws Exception {
    fixture.clickOnSaveChanges();
    if (fixture.errorIsPresent()) {
      fixture.clickOnButton("OK");
    }
  }

  @AfterClass
  public static void tearDownInterface() {
    fixture.clickOnButton("Cancel");
  }
}