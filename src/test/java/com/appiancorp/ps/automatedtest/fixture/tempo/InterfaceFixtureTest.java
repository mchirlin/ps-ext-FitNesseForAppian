package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixture;
import com.appiancorp.ps.automatedtest.object.TempoObject;

public class InterfaceFixtureTest {    
    
    private static TempoFixture tFixture;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      
      tFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
      tFixture.setAppianUrlTo("https://apacdemo.appiancloud.com/suite");
      tFixture.setTimeoutSecondsTo("15");
      tFixture.setAppianVersionTo("16.1");
      tFixture.setAppianLocaleTo("en_GB");
      
      tFixture.setStartDatetime();
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
      
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
        assertTrue(tFixture.populateFieldWith("TextField", new String[]{"text"}));
        assertTrue(tFixture.verifyFieldContains("TextField", new String[]{"text"}));
        assertEquals("text", tFixture.getFieldValue("TextField"));
        
        assertTrue(tFixture.verifyFieldContains("ROTextField", new String[]{"text"}));
        assertEquals("text", tFixture.getFieldValue("ROTextField"));
        
        // Grid
        assertTrue(tFixture.populateGridColumnRowWith("[1]", "TextField", "[1]", new String[]{"gridText"}));
        assertTrue(tFixture.verifyGridColumnRowContains("[1]", "TextField", "[1]", new String[]{"gridText"}));
        assertEquals("gridText", tFixture.getGridColumnRowValue("[1]", "TextField", "[1]"));

        assertTrue(tFixture.verifyGridColumnRowContains("[1]", "TextField", "[2]", new String[]{"gridText"}));
        assertEquals("gridText", tFixture.getGridColumnRowValue("[1]", "TextField", "[2]"));
    }
    
    @Test
    public void testParagraphField() throws Exception {
        assertTrue(tFixture.populateFieldWith("ParagraphField", new String[]{"paragraph"}));
        assertTrue(tFixture.verifyFieldContains("ParagraphField", new String[]{"paragraph"}));
        assertEquals("paragraph", tFixture.getFieldValue("ParagraphField"));
        
        assertTrue(tFixture.verifyFieldContains("ROParagraphField", new String[]{"paragraph"}));
        assertEquals("paragraph", tFixture.getFieldValue("ROParagraphField"));
        
        // Grid
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid", "[3]", "[1]", new String[]{"gridParagraph"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "[3]", "[1]", new String[]{"gridParagraph"}));
        assertEquals("gridParagraph", tFixture.getGridColumnRowValue("EditableGrid", "[3]", "[1]"));

        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "[3]", "[2]", new String[]{"gridParagraph"}));
        assertEquals("gridParagraph", tFixture.getGridColumnRowValue("EditableGrid", "[3]", "[2]"));
    }
    
    @Test
    public void testEncryptedTextField() throws Exception {
        assertTrue(tFixture.populateFieldWith("EncryptedTextField", new String[]{"encrypted"}));
        assertTrue(tFixture.verifyFieldContains("EncryptedTextField", new String[]{"encrypted"}));
        assertEquals("encrypted", tFixture.getFieldValue("EncryptedTextField"));
        
        assertTrue(tFixture.verifyFieldContains("ROEncryptedTextField", new String[]{"encrypted"}));
        assertEquals("encrypted", tFixture.getFieldValue("ROEncryptedTextField"));
        
        // Grid
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid", "EncryptedTextField", "[1]", new String[]{"gridEncrypted"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "EncryptedTextField", "[1]", new String[]{"gridEncrypted"}));
        assertEquals("gridEncrypted", tFixture.getGridColumnRowValue("EditableGrid", "EncryptedTextField", "[1]"));

        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "EncryptedTextField", "[2]", new String[]{"gridEncrypted"}));
        assertEquals("gridEncrypted", tFixture.getGridColumnRowValue("EditableGrid", "EncryptedTextField", "[2]"));
    }
    
    @Test
    public void testIntegerField() throws Exception {
        assertTrue(tFixture.populateFieldWith("IntegerField", new String[]{"1"}));
        assertTrue(tFixture.verifyFieldContains("IntegerField", new String[]{"1"}));
        assertEquals("1", tFixture.getFieldValue("IntegerField"));
        
        assertTrue(tFixture.verifyFieldContains("ROIntegerField", new String[]{"1"}));
        assertEquals("1", tFixture.getFieldValue("ROIntegerField"));
        
        // Grid
        assertTrue(tFixture.populateGridColumnRowWith("[1]", "[5]", "[1]", new String[]{"1"}));
        assertTrue(tFixture.verifyGridColumnRowContains("[1]", "[5]", "[1]", new String[]{"1"}));
        assertEquals("1", tFixture.getGridColumnRowValue("[1]", "[5]", "[1]"));

        assertTrue(tFixture.verifyGridColumnRowContains("[1]", "[5]", "[2]", new String[]{"1"}));
        assertEquals("1", tFixture.getGridColumnRowValue("[1]", "[5]", "[2]"));
    }
    
    @Test
    public void testDecimalField() throws Exception {
        assertTrue(tFixture.populateFieldWith("DecimalField", new String[]{"2.2"}));
        assertTrue(tFixture.verifyFieldContains("DecimalField", new String[]{"2.2"}));
        assertEquals("2.2", tFixture.getFieldValue("DecimalField"));
        
        assertTrue(tFixture.verifyFieldContains("RODecimalField", new String[]{"2.2"}));
        assertEquals("2.2", tFixture.getFieldValue("RODecimalField"));
        
        // Grid
        assertTrue(tFixture.populateGridColumnRowWith("[1]", "DecimalField", "[1]", new String[]{"2.2"}));
        assertTrue(tFixture.verifyGridColumnRowContains("[1]", "DecimalField", "[1]", new String[]{"2.2"}));
        assertEquals("2.2", tFixture.getGridColumnRowValue("[1]", "DecimalField", "[1]"));

        assertTrue(tFixture.verifyGridColumnRowContains("[1]", "DecimalField", "[2]", new String[]{"2.2"}));
        assertEquals("2.2", tFixture.getGridColumnRowValue("[1]", "DecimalField", "[2]"));
    }
    
    @Test
    public void testDateField() throws Exception {    
        SimpleDateFormat df = new SimpleDateFormat(TempoObject.getDateFormat());
        SimpleDateFormat ddf = new SimpleDateFormat(TempoObject.getDateDisplayFormat());
        
        assertTrue(tFixture.populateFieldWith("DateField", new String[]{"+1 day"}));
        assertTrue(tFixture.verifyFieldContains("DateField", new String[]{"+1 day"}));
        assertEquals(df.format(DateUtils.addDays(TempoObject.getStartDatetime(), 1)), tFixture.getFieldValue("DateField"));
        
        assertTrue(tFixture.verifyFieldContains("RODateField", new String[]{"+1 day"}));
        assertEquals(ddf.format(DateUtils.addDays(TempoObject.getStartDatetime(), 1)), tFixture.getFieldValue("RODateField"));
        
        assertTrue(tFixture.populateFieldWith("DateField[2]", new String[]{"2015-11-15"}));
        assertTrue(tFixture.verifyFieldContains("DateField[2]", new String[]{"2015-11-15"}));
        assertEquals(df.format(TempoObject.parseDate("2015-11-15")), tFixture.getFieldValue("DateField[2]"));
        
        assertTrue(tFixture.populateFieldWith("DateField[3]", new String[]{"+5 days"}));
        assertTrue(tFixture.verifyFieldContains("DateField[3]", new String[]{"+5 days"}));
        assertEquals(df.format(DateUtils.addDays(TempoObject.getStartDatetime(), 5)), tFixture.getFieldValue("DateField[3]"));
        
        // Grid
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid", "[7]", "[1]", new String[]{"+1 day"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "[7]", "[1]", new String[]{"+1 day"}));
        assertEquals(df.format(DateUtils.addDays(TempoObject.getStartDatetime(), 1)), tFixture.getGridColumnRowValue("EditableGrid", "[7]", "[1]"));
    }
    
    @Test
    public void testDatetimeField() throws Exception {
        SimpleDateFormat dtf = new SimpleDateFormat(TempoObject.getDatetimeFormat());
        SimpleDateFormat ddtf = new SimpleDateFormat(TempoObject.getDatetimeDisplayFormat());
        
        assertTrue(tFixture.populateFieldWith("DatetimeField", new String[]{"+1 hour"}));
        assertTrue(tFixture.verifyFieldContains("DatetimeField", new String[]{"+1 hour"}));
        assertEquals(dtf.format(DateUtils.addHours(TempoObject.getStartDatetime(), 1)), tFixture.getFieldValue("DatetimeField"));
        
        assertTrue(tFixture.verifyFieldContains("RODatetimeField", new String[]{"+1 hour"}));
        assertEquals(ddtf.format(DateUtils.addHours(TempoObject.getStartDatetime(), 1)), tFixture.getFieldValue("RODatetimeField"));
        
        assertTrue(tFixture.populateFieldWith("DatetimeField[2]", new String[]{"2015-11-15"}));
        assertTrue(tFixture.verifyFieldContains("DatetimeField[2]", new String[]{"2015-11-15"}));
        assertEquals(dtf.format(TempoObject.parseDate("2015-11-15")), tFixture.getFieldValue("DatetimeField[2]"));
        
        assertTrue(tFixture.populateFieldWith("DatetimeField[3]", new String[]{"2015-11-15 14:00"}));
        assertTrue(tFixture.verifyFieldContains("DatetimeField[3]", new String[]{"2015-11-15 14:00"}));
        assertEquals(dtf.format(TempoObject.parseDate("2015-11-15 14:00")), tFixture.getFieldValue("DatetimeField[3]"));
        
        assertTrue(tFixture.populateFieldWith("DatetimeField[4]", new String[]{"+5 minutes"}));
        assertTrue(tFixture.verifyFieldContains("DatetimeField[4]", new String[]{"+5 minutes"}));
        assertEquals(dtf.format(DateUtils.addMinutes(TempoObject.getStartDatetime(), 5)), tFixture.getFieldValue("DatetimeField[4]"));
        
        assertTrue(tFixture.populateFieldWith("DatetimeField[5]", new String[]{"+5 hours"}));
        assertTrue(tFixture.verifyFieldContains("DatetimeField[5]", new String[]{"+5 hours"}));
        assertEquals(dtf.format(DateUtils.addHours(TempoObject.getStartDatetime(), 5)), tFixture.getFieldValue("DatetimeField[5]"));
        
        assertTrue(tFixture.populateFieldWith("DatetimeField[6]", new String[]{"+5 days"}));
        assertTrue(tFixture.verifyFieldContains("DatetimeField[6]", new String[]{"+5 days"}));
        assertEquals(dtf.format(DateUtils.addDays(TempoObject.getStartDatetime(), 5)), tFixture.getFieldValue("DatetimeField[6]"));
        
        // Grid
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "DatetimeField", "[1]", new String[]{"+1 hour"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "DatetimeField", "[1]", new String[]{"+1 hour"}));
        assertEquals(dtf.format(DateUtils.addHours(TempoObject.getStartDatetime(), 1)), tFixture.getGridColumnRowValue("EditableGrid[2]", "DatetimeField", "[1]"));

        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "[1]", "[2]", new String[]{"+1 minute"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[1]", "[2]", new String[]{"+1 minute"}));
        assertEquals(dtf.format(DateUtils.addMinutes(TempoObject.getStartDatetime(), 1)), tFixture.getGridColumnRowValue("EditableGrid[2]", "[1]", "[2]"));
    }
    
    @Test
    public void testSelectField() throws Exception {
        assertTrue(tFixture.populateFieldWithValue("SelectField", "Option 2"));
        assertTrue(tFixture.verifyFieldContainsValue("SelectField", "Option 2"));
        assertEquals("Option 2", tFixture.getFieldValue("SelectField"));
        
        // Grid
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "[2]", "[1]", new String[]{"Option 1"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[2]", "[1]", new String[]{"Option 1"}));
        assertEquals("Option 1", tFixture.getGridColumnRowValue("EditableGrid[2]", "[2]", "[1]"));

        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "SelectField", "[2]", new String[]{"Option 2"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "SelectField", "[2]", new String[]{"Option 2"}));
        assertEquals("Option 2", tFixture.getGridColumnRowValue("EditableGrid[2]", "SelectField", "[2]"));
    }
    @Test
    public void testMultipleSelectField() throws Exception {
        assertTrue(tFixture.populateFieldWith("MultipleSelectField", new String[]{"Option 1", "Option 2"}));
        assertTrue(tFixture.verifyFieldContains("MultipleSelectField", new String[]{"Option 1","Option 2"}));
        assertEquals("Option 1,Option 2", tFixture.getFieldValue("MultipleSelectField"));
    }
    
    @Test
    public void testRadioField() throws Exception {
        assertTrue(tFixture.populateFieldWith("RadioField", new String[]{"Option 1"}));
        assertTrue(tFixture.verifyFieldContains("RadioField", new String[]{"Option 1"}));
        assertEquals("Option 1", tFixture.getFieldValue("RadioField"));
        
        // Grid
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "RadioField", "[1]", new String[]{"Option 1"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "RadioField", "[1]", new String[]{"Option 1"}));
        assertEquals("Option 1", tFixture.getGridColumnRowValue("EditableGrid[2]", "RadioField", "[1]"));
        
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "[3]", "[2]", new String[]{"Option 2"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[3]", "[2]", new String[]{"Option 2"}));
        assertEquals("Option 2", tFixture.getGridColumnRowValue("EditableGrid[2]", "[3]", "[2]"));
    }
    
    @Test
    public void testCheckboxField() throws Exception {
        assertTrue(tFixture.populateFieldWith("CheckboxField", new String[]{"Option 1", "Option 2"}));
        assertTrue(tFixture.verifyFieldContains("CheckboxField", new String[]{"Option 1","Option 2"}));
        assertEquals("Option 1,Option 2", tFixture.getFieldValue("CheckboxField"));
        
        // Grid
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "[4]", "[1]", new String[]{"Option 1","Option 2"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[4]", "[1]", new String[]{"Option 1","Option 2"}));
        assertEquals("Option 1,Option 2", tFixture.getGridColumnRowValue("EditableGrid[2]", "[4]", "[1]"));

        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "CheckboxField", "[2]", new String[]{"Option 2"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "CheckboxField", "[2]", new String[]{"Option 2"}));
        assertEquals("Option 2", tFixture.getGridColumnRowValue("EditableGrid[2]", "CheckboxField", "[2]"));
    }
    
    @Test
    public void testFileUploadField() throws Exception {
        assertTrue(tFixture.populateFieldWith("FileUploadField", new String[]{"C:\\Users\\michael.chirlin\\Documents\\Low Risk.jpg"}));
        assertTrue(tFixture.verifyFieldContains("FileUploadField", new String[]{"C:\\Users\\michael.chirlin\\Documents\\Low Risk.jpg"}));
        assertEquals("Low Risk.jpg", tFixture.getFieldValue("FileUploadField")); // Notice this doesn't include the entire path
        
        // Grid
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "FileUploadField", "[1]", new String[]{"C:\\Users\\michael.chirlin\\Documents\\Medium Risk.jpg"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "FileUploadField", "[1]", new String[]{"C:\\Users\\michael.chirlin\\Documents\\Medium Risk.jpg"}));
        assertEquals("Medium Risk.jpg", tFixture.getGridColumnRowValue("EditableGrid[2]", "FileUploadField", "[1]"));

        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "[5]", "[2]", new String[]{"C:\\Users\\michael.chirlin\\Documents\\High Risk.jpg"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[5]", "[2]", new String[]{"C:\\Users\\michael.chirlin\\Documents\\High Risk.jpg"}));
        assertEquals("High Risk.jpg", tFixture.getGridColumnRowValue("EditableGrid[2]", "[5]", "[2]"));
    }
    
    @Test
    public void testPickerFields() throws Exception {
        //TODO Add readonly test
        
        assertTrue(tFixture.populateFieldWith("UserPicker", new String[]{"Michael Chirlin", "Ray Croxon"}));
        assertTrue(tFixture.verifyFieldContains("UserPicker", new String[]{"Michael Chirlin", "Ray Croxon"}));
        assertEquals("Michael Chirlin,Ray Croxon", tFixture.getFieldValue("UserPicker"));
        
        /*
        assertTrue(tFixture.populateFieldWith("GroupPicker", new String[]{"AcqDemo"}));
        assertTrue(tFixture.verifyFieldContains("GroupPicker", new String[]{"AcqDemo"}));
        assertEquals("AcqDemo", tFixture.getFieldValue("GroupPicker"));
        
        assertTrue(tFixture.populateFieldWith("UserGroupPicker", new String[]{"Michael Chirlin"}));
        assertTrue(tFixture.verifyFieldContains("UserGroupPicker", new String[]{"Michael Chirlin"}));
        assertEquals("Michael Chirlin", tFixture.getFieldValue("UserGroupPicker"));
        
        assertTrue(tFixture.populateFieldWith("DocumentPicker", new String[]{"Jellyfish"}));
        assertTrue(tFixture.verifyFieldContains("DocumentPicker", new String[]{"Jellyfish.jpg"}));
        assertEquals("Jellyfish.jpg", tFixture.getFieldValue("DocumentPicker")); // Note I need the extension here
        
        assertTrue(tFixture.populateFieldWith("FolderPicker", new String[]{"Acceptance Letters"}));
        assertTrue(tFixture.verifyFieldContains("FolderPicker", new String[]{"Acceptance Letters"}));
        assertEquals("Acceptance Letters", tFixture.getFieldValue("FolderPicker"));
        
        assertTrue(tFixture.populateFieldWith("DocumentFolderPicker", new String[]{"Jellyfish"}));
        assertTrue(tFixture.verifyFieldContains("DocumentFolderPicker", new String[]{"Jellyfish"}));
        assertEquals("Jellyfish.jpg", tFixture.getFieldValue("DocumentFolderPicker"));
        
        assertTrue(tFixture.populateFieldWith("CustomPicker", new String[]{"5"}));
        assertTrue(tFixture.verifyFieldContains("CustomPicker", new String[]{"5"}));
        assertEquals("5", tFixture.getFieldValue("CustomPicker"));
        */
        
        // Grid
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[1]", "[1]", new String[]{"Michael Chirlin","Ray Croxon"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[1]", "[1]", new String[]{"Michael Chirlin","Ray Croxon"}));
        assertEquals("Michael Chirlin,Ray Croxon", tFixture.getGridColumnRowValue("EditableGrid[3]", "[1]", "[1]"));
        
        /*
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[2]", "[1]", new String[]{"AcqDemo"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[2]", "[1]", new String[]{"AcqDemo"}));
        assertEquals("AcqDemo", tFixture.getGridColumnRowValue("EditableGrid[3]", "[2]", "[1]"));
        
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[3]", "[1]", new String[]{"Michael Chirlin"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[3]", "[1]", new String[]{"Michael Chirlin"}));
        assertEquals("Michael Chirlin", tFixture.getGridColumnRowValue("EditableGrid[3]", "[3]", "[1]"));
        
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[4]", "[1]", new String[]{"Jellyfish"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[4]", "[1]", new String[]{"Jellyfish"}));
        assertEquals("Jellyfish.jpg", tFixture.getGridColumnRowValue("EditableGrid[3]", "[4]", "[1]"));
        
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[1]", "[2]", new String[]{"Acceptance Letters"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[1]", "[2]", new String[]{"Acceptance Letters"}));
        assertEquals("Acceptance Letters", tFixture.getGridColumnRowValue("EditableGrid[3]", "[1]", "[2]"));
        
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[2]", "[2]", new String[]{"Jellyfish"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[2]", "[2]", new String[]{"Jellyfish"}));
        assertEquals("Jellyfish.jpg", tFixture.getGridColumnRowValue("EditableGrid[3]", "[2]", "[2]"));
        
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[3]", "[2]", new String[]{"10"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[3]", "[2]", new String[]{"10"}));
        assertEquals("10", tFixture.getGridColumnRowValue("EditableGrid[3]", "[3]", "[2]"));
        */
    }   

    @Test
    public void testFieldIndex() throws Exception {
        assertTrue(tFixture.populateFieldWith("TextField[2]", new String[]{"text2"}));
        assertTrue(tFixture.verifyFieldContains("TextField[2]", new String[]{"text2"}));
        assertEquals(tFixture.getFieldValue("TextField[2]"), "text2");
    }
    
    @Test
    public void testPopulateFieldWithValue() throws Exception {
        assertTrue(tFixture.populateFieldWithValue("SelectField", "Option, 1"));
        assertTrue(tFixture.verifyFieldContainsValue("SelectField", "Option, 1"));
        assertEquals("Option, 1", tFixture.getFieldValue("SelectField"));
    }
    
    @Test
    public void testPopulateFieldInSectionWith() throws Exception {
        assertTrue(tFixture.populateFieldInSectionWith("[1]", "Section 2", new String[]{"noLabel"}));
        assertTrue(tFixture.verifyFieldInSectionContains("[1]", "Section 2", new String[]{"noLabel"}));
        assertEquals("noLabel", tFixture.getFieldInSectionValue("[1]", "Section 2"));
    }
    
    @Test
    public void testClickOnOptions() throws Exception {
        assertTrue(tFixture.clickOnRadioOption("Click Me!"));
        assertTrue(tFixture.clickOnCheckboxOption("Click Me Too!"));
    }
    
    @Test
    public void testSelectGridRow() throws Exception {
        assertFalse(tFixture.verifyGridRowIsSelected("EditableGrid", "[1]"));
        
        assertTrue(tFixture.selectGridRow("EditableGrid", "[1]"));
        assertTrue(tFixture.verifyGridRowIsSelected("EditableGrid", "[1]"));
        
        assertFalse(tFixture.verifyGridRowIsSelected("PagingGrid", "[4]"));
        
        assertTrue(tFixture.selectGridRow("PagingGrid", "[4]"));
        assertTrue(tFixture.verifyGridRowIsSelected("PagingGrid", "[4]"));
    }
    
    @Test 
    public void testGridAddRowLink() throws Exception {
        //TODO Make this button do something
        assertTrue(tFixture.clickOnGridAddRowLink("[1]"));
        assertTrue(tFixture.clickOnGridAddRowLink("EditableGrid"));
        assertTrue(tFixture.clickOnGridAddRowLink("EditableGrid[1]"));
    }
    
    @Test 
    public void testClickLink() throws Exception {
        assertTrue(tFixture.clickOnLink("Add Data to Paging Grid"));
        assertTrue(tFixture.clickOnLink("Add Data to Paging Grid[1]"));
    }
    
    @Test
    public void testGridContains() throws Exception {
        assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "[2]","[1]", new String[] {"Value 1"}));
    }
    
    @Test
    public void testGridPaging() throws Exception{
        assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1","[1]", new String[] {"Value 1"}));
        assertTrue(tFixture.clickOnGridNavigation("PagingGrid", "next"));
        assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1","[1]", new String[] {"Value 6"}));
        assertTrue(tFixture.clickOnGridNavigation("[4]", "PREVIOUS"));
        assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1","[1]", new String[] {"Value 1"}));
        assertTrue(tFixture.clickOnGridNavigation("[4]", "last"));
        assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1","[1]", new String[] {"Value 16"}));
        assertTrue(tFixture.clickOnGridNavigation("PagingGrid", "FIRST"));
        assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1","[1]", new String[] {"Value 1"}));
    }
    
    @Test
    public void testGridSort() throws Exception{
        assertTrue(tFixture.sortGridByColumn("PagingGrid", "Column Label"));
        assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1","[1]", new String[] {"Value 1"}));
        assertTrue(tFixture.sortGridByColumn("[4]", "Column Label"));
        assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "[2]","[1]", new String[] {"Value 9"}));
        assertTrue(tFixture.sortGridByColumn("PagingGrid", "Column Label 2"));
        assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "Column Label 2","[1]", new String[] {"Description 1"}));
        assertTrue(tFixture.sortGridByColumn("[4]", "Column Label 2"));
        assertTrue(tFixture.verifyGridColumnRowContains("PagingGrid", "[3]","[1]", new String[] {"Description 9"}));
    }
    
    @Test
    public void testSectionExpandCollapse() throws Exception{
        assertTrue(tFixture.collapseSection("Section 1"));
        assertTrue(tFixture.verifyFieldIsNotPresent("ROTextField"));
        assertTrue(tFixture.expandSection("Section 1"));
        assertTrue(tFixture.verifyFieldIsPresent("ROTextField"));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.clickOnButton("Cancel");
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}