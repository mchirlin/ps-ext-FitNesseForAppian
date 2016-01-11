package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertEquals;
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
      tFixture.setTimeoutSecondsTo("10");
      tFixture.setStartDatetime();
      tFixture.setDateFormatStringTo("dd/MM/yyyy");
      tFixture.setTimeFormatStringTo("HH:mm");
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
      
      tFixture.clickOnMenu("Actions");
      tFixture.clickOnAction("Automated Testing");
    }
    
    @Test
    public void testGetTitle() throws Exception {
        String getTitle = tFixture.getTitle();
        assertEquals(getTitle, "Form");
    }
    
    @Test
    public void testGetInstructions() throws Exception {
        String getInstructions = tFixture.getInstructions();
        assertEquals(getInstructions, "Instructions");
    }
        
    @Test
    public void testPopulateFieldWithGetFieldValueVerifyFieldContains() throws Exception {
        // Section 1
        assertTrue(tFixture.populateFieldWith("TextField", new String[]{"text"}));
        assertTrue(tFixture.populateFieldWith("ParagraphField", new String[]{"paragraph"}));
        assertTrue(tFixture.populateFieldWith("EncryptedTextField", new String[]{"encrypted"}));
        assertTrue(tFixture.populateFieldWith("IntegerField", new String[]{"1"}));
        assertTrue(tFixture.populateFieldWith("DecimalField", new String[]{"2.2"}));
        assertTrue(tFixture.populateFieldWith("DateField", new String[]{"+1 day"}));
        assertTrue(tFixture.populateFieldWith("DatetimeField", new String[]{"+1 hour"}));
        assertTrue(tFixture.populateFieldWith("MultipleSelectField", new String[]{"Option 1", "Option 2"}));
        assertTrue(tFixture.populateFieldWith("RadioField", new String[]{"Option 1"}));
        assertTrue(tFixture.populateFieldWith("CheckboxField", new String[]{"Option 1", "Option 2"}));
        assertTrue(tFixture.populateFieldWith("FileUploadField", new String[]{"C:\\Users\\michael.chirlin\\Documents\\Low Risk.jpg"}));
        
        assertTrue(tFixture.verifyFieldContains("TextField", new String[]{"text"}));
        assertTrue(tFixture.verifyFieldContains("ParagraphField", new String[]{"paragraph"}));
        assertTrue(tFixture.verifyFieldContains("EncryptedTextField", new String[]{"encrypted"}));
        assertTrue(tFixture.verifyFieldContains("IntegerField", new String[]{"1"}));
        assertTrue(tFixture.verifyFieldContains("DecimalField", new String[]{"2.2"}));
        assertTrue(tFixture.verifyFieldContains("DateField", new String[]{"+1 day"}));
        assertTrue(tFixture.verifyFieldContains("DatetimeField", new String[]{"+1 hour"}));
        assertTrue(tFixture.verifyFieldContains("MultipleSelectField", new String[]{"Option 1","Option 2"}));
        assertTrue(tFixture.verifyFieldContains("RadioField", new String[]{"Option 1"}));
        assertTrue(tFixture.verifyFieldContains("CheckboxField", new String[]{"Option 1","Option 2"}));
        assertTrue(tFixture.verifyFieldContains("FileUploadField", new String[]{"C:\\Users\\michael.chirlin\\Documents\\Low Risk.jpg"}));
        
        assertEquals(tFixture.getFieldValue("TextField"), "text");
        assertEquals(tFixture.getFieldValue("ParagraphField"), "paragraph");
        assertEquals(tFixture.getFieldValue("EncryptedTextField"), "encrypted");
        assertEquals(tFixture.getFieldValue("IntegerField"), "1");
        assertEquals(tFixture.getFieldValue("DecimalField"), "2.2");
        assertEquals(tFixture.getFieldValue("DateField"), new SimpleDateFormat(TempoObject.DATE_FORMAT_STRING).format(DateUtils.addDays(TempoObject.getStartDatetime(), 1)));
        assertEquals(tFixture.getFieldValue("DatetimeField"), new SimpleDateFormat(TempoObject.DATETIME_FORMAT_STRING).format(DateUtils.addHours(TempoObject.getStartDatetime(), 1)));
        assertEquals(tFixture.getFieldValue("MultipleSelectField"), "Option 1,Option 2");
        assertEquals(tFixture.getFieldValue("RadioField"), "Option 1");
        assertEquals(tFixture.getFieldValue("CheckboxField"), "Option 1,Option 2");
        assertEquals(tFixture.getFieldValue("FileUploadField"), "Low Risk.jpg"); // Notice this doesn't include the entire path
        
        // Section 1.5
        assertTrue(tFixture.populateFieldWith("DateField[2]", new String[]{"15/11/2015"}));
        assertTrue(tFixture.populateFieldWith("DateField[3]", new String[]{"+5 days"}));
        assertTrue(tFixture.populateFieldWith("DatetimeField[2]", new String[]{"15/11/2015"}));
        assertTrue(tFixture.populateFieldWith("DatetimeField[3]", new String[]{"15/11/2015 14:00"}));
        assertTrue(tFixture.populateFieldWith("DatetimeField[4]", new String[]{"+5 minutes"}));
        assertTrue(tFixture.populateFieldWith("DatetimeField[5]", new String[]{"+5 hours"}));
        assertTrue(tFixture.populateFieldWith("DatetimeField[6]", new String[]{"+5 days"}));
        
        assertTrue(tFixture.verifyFieldContains("DateField[2]", new String[]{"15/11/2015"}));
        assertTrue(tFixture.verifyFieldContains("DateField[3]", new String[]{"+5 days"}));
        assertTrue(tFixture.verifyFieldContains("DatetimeField[2]", new String[]{"15/11/2015"}));
        assertTrue(tFixture.verifyFieldContains("DatetimeField[3]", new String[]{"15/11/2015 14:00"}));
        assertTrue(tFixture.verifyFieldContains("DatetimeField[4]", new String[]{"+5 minutes"}));
        assertTrue(tFixture.verifyFieldContains("DatetimeField[5]", new String[]{"+5 hours"}));
        assertTrue(tFixture.verifyFieldContains("DatetimeField[6]", new String[]{"+5 days"}));
        
        assertEquals(tFixture.getFieldValue("DateField[2]"), "15/11/2015");
        assertEquals(tFixture.getFieldValue("DateField[3]"), new SimpleDateFormat(TempoObject.DATE_FORMAT_STRING).format(DateUtils.addDays(TempoObject.getStartDatetime(), 5)));
        assertEquals(tFixture.getFieldValue("DatetimeField[2]"), "15/11/2015 00:00");
        assertEquals(tFixture.getFieldValue("DatetimeField[3]"), "15/11/2015 14:00");
        assertEquals(tFixture.getFieldValue("DatetimeField[4]"), new SimpleDateFormat(TempoObject.DATETIME_FORMAT_STRING).format(DateUtils.addMinutes(TempoObject.getStartDatetime(), 5)));
        assertEquals(tFixture.getFieldValue("DatetimeField[5]"), new SimpleDateFormat(TempoObject.DATETIME_FORMAT_STRING).format(DateUtils.addHours(TempoObject.getStartDatetime(), 5)));
        assertEquals(tFixture.getFieldValue("DatetimeField[6]"), new SimpleDateFormat(TempoObject.DATETIME_FORMAT_STRING).format(DateUtils.addDays(TempoObject.getStartDatetime(), 5)));

        // Section 2
        assertTrue(tFixture.populateFieldWith("TextField[2]", new String[]{"text2"}));
        
        assertTrue(tFixture.verifyFieldContains("TextField[2]", new String[]{"text2"}));
        
        assertEquals(tFixture.getFieldValue("TextField[2]"), "text2");
       
        // Section 2.5
        assertTrue(tFixture.populateFieldWith("UserPicker", new String[]{"Michael Chirlin", "Ray Croxon"}));
        assertTrue(tFixture.populateFieldWith("GroupPicker", new String[]{"AcqDemo"}));
        assertTrue(tFixture.populateFieldWith("UserGroupPicker", new String[]{"Michael Chirlin"}));
        assertTrue(tFixture.populateFieldWith("DocumentPicker", new String[]{"Jellyfish"}));
        assertTrue(tFixture.populateFieldWith("FolderPicker", new String[]{"Acceptance Letters"}));
        assertTrue(tFixture.populateFieldWith("DocumentFolderPicker", new String[]{"Jellyfish"}));
        assertTrue(tFixture.populateFieldWith("CustomPicker", new String[]{"5"}));
        
        assertTrue(tFixture.verifyFieldContains("UserPicker", new String[]{"Michael Chirlin", "Ray Croxon"}));
        assertTrue(tFixture.verifyFieldContains("GroupPicker", new String[]{"AcqDemo"}));
        assertTrue(tFixture.verifyFieldContains("UserGroupPicker", new String[]{"Michael Chirlin"}));
        assertTrue(tFixture.verifyFieldContains("DocumentPicker", new String[]{"Jellyfish.jpg"}));
        assertTrue(tFixture.verifyFieldContains("FolderPicker", new String[]{"Acceptance Letters"}));
        assertTrue(tFixture.verifyFieldContains("DocumentFolderPicker", new String[]{"Jellyfish"}));
        assertTrue(tFixture.verifyFieldContains("CustomPicker", new String[]{"5"}));
        
        assertEquals(tFixture.getFieldValue("UserPicker"), "Michael Chirlin,Ray Croxon");
        assertEquals(tFixture.getFieldValue("GroupPicker"), "AcqDemo");
        assertEquals(tFixture.getFieldValue("UserGroupPicker"), "Michael Chirlin");
        assertEquals(tFixture.getFieldValue("DocumentPicker"), "Jellyfish.jpg"); // Note I need the extension here
        assertEquals(tFixture.getFieldValue("FolderPicker"), "Acceptance Letters");
        assertEquals(tFixture.getFieldValue("DocumentFolderPicker"), "Jellyfish.jpg");
        assertEquals(tFixture.getFieldValue("CustomPicker"), "5"); // Note I need the extension here
    }
    
    @Test
    public void testPopulateFieldWithValueGetFieldValueVerifyFieldContains() throws Exception {
        assertTrue(tFixture.populateFieldWithValue("SelectField", "Option, 1"));
        
        assertTrue(tFixture.verifyFieldContainsValue("SelectField", "Option, 1"));
        
        assertEquals(tFixture.getFieldValue("SelectField"), "Option, 1");
    }
    
    @Test
    public void testPopulateFieldInSectionWithGetFieldInSectionVerifyFieldInSectionContains() throws Exception {
        assertTrue(tFixture.populateFieldInSectionWith("[1]", "Section 2", new String[]{"noLabel"}));
        
        assertTrue(tFixture.verifyFieldInSectionContains("[1]", "Section 2", new String[]{"noLabel"}));
        
        assertEquals(tFixture.getFieldInSectionValue("[1]", "Section 2"), "noLabel");
    }
    
    @Test
    public void testClickOnOptions() throws Exception {
        assertTrue(tFixture.clickOnRadioOption("Click Me!"));
        assertTrue(tFixture.clickOnCheckboxOption("Click Me Too!"));
    }
    
    @Test
    public void testPopulateGridColumnRowWithGetGridColumnRowValueVerifyGridColumnRowContains() throws Exception {
        // EditableGrid
        assertTrue(tFixture.populateGridColumnRowWith("[1]", "TextField", "[1]", new String[]{"gridText"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid", "[3]", "[1]", new String[]{"gridParagraph"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid", "EncryptedTextField", "[1]", new String[]{"gridEncrypted"}));
        assertTrue(tFixture.populateGridColumnRowWith("[1]", "[5]", "[1]", new String[]{"1"}));
        assertTrue(tFixture.populateGridColumnRowWith("[1]", "DecimalField", "[1]", new String[]{"2.2"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid", "[7]", "[1]", new String[]{"+1 day"}));
        
        assertTrue(tFixture.verifyGridColumnRowContains("[1]", "TextField", "[1]", new String[]{"gridText"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "[3]", "[1]", new String[]{"gridParagraph"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "EncryptedTextField", "[1]", new String[]{"gridEncrypted"}));
        assertTrue(tFixture.verifyGridColumnRowContains("[1]", "[5]", "[1]", new String[]{"1"}));
        assertTrue(tFixture.verifyGridColumnRowContains("[1]", "DecimalField", "[1]", new String[]{"2.2"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid", "[7]", "[1]", new String[]{"+1 day"}));
        
        assertEquals(tFixture.getGridColumnRowValue("[1]", "TextField", "[1]"), "gridText");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid", "[3]", "[1]"), "gridParagraph");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid", "EncryptedTextField", "[1]"), "gridEncrypted");
        assertEquals(tFixture.getGridColumnRowValue("[1]", "[5]", "[1]"), "1");
        assertEquals(tFixture.getGridColumnRowValue("[1]", "DecimalField", "[1]"), "2.2");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid", "[7]", "[1]"), new SimpleDateFormat(TempoObject.DATE_FORMAT_STRING).format(DateUtils.addDays(TempoObject.getStartDatetime(), 1)));
        
        // EditableGrid[2]
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "DatetimeField", "[1]", new String[]{"+1 hour"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "[1]", "[2]", new String[]{"+1 minute"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "[2]", "[1]", new String[]{"Option 1"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "SelectField", "[2]", new String[]{"Option 2"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "RadioField", "[1]", new String[]{"Option 1"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "[3]", "[2]", new String[]{"Option 2"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "[4]", "[1]", new String[]{"Option 1","Option 2"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "CheckboxField", "[2]", new String[]{"Option 2"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "FileUploadField", "[1]", new String[]{"C:\\Users\\michael.chirlin\\Documents\\Medium Risk.jpg"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[2]", "[5]", "[2]", new String[]{"C:\\Users\\michael.chirlin\\Documents\\High Risk.jpg"}));
        
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "DatetimeField", "[1]", new String[]{"+1 hour"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[1]", "[2]", new String[]{"+1 minute"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[2]", "[1]", new String[]{"Option 1"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "SelectField", "[2]", new String[]{"Option 2"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "RadioField", "[1]", new String[]{"Option 1"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[3]", "[2]", new String[]{"Option 2"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[4]", "[1]", new String[]{"Option 1","Option 2"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "CheckboxField", "[2]", new String[]{"Option 2"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "FileUploadField", "[1]", new String[]{"C:\\Users\\michael.chirlin\\Documents\\Medium Risk.jpg"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[2]", "[5]", "[2]", new String[]{"C:\\Users\\michael.chirlin\\Documents\\High Risk.jpg"}));
        
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[2]", "DatetimeField", "[1]"), new SimpleDateFormat(TempoObject.DATETIME_FORMAT_STRING).format(DateUtils.addHours(TempoObject.getStartDatetime(), 1)));
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[2]", "[1]", "[2]"), new SimpleDateFormat(TempoObject.DATETIME_FORMAT_STRING).format(DateUtils.addMinutes(TempoObject.getStartDatetime(), 1)));
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[2]", "[2]", "[1]"), "Option 1");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[2]", "SelectField", "[2]"), "Option 2");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[2]", "RadioField", "[1]"), "Option 1");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[2]", "[3]", "[2]"), "Option 2");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[2]", "[4]", "[1]"), "Option 1,Option 2");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[2]", "CheckboxField", "[2]"), "Option 2");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[2]", "FileUploadField", "[1]"), "Medium Risk.jpg");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[2]", "[5]", "[2]"), "High Risk.jpg");
        
        // EditableGrid[3]
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[1]", "[1]", new String[]{"Michael Chirlin","Ray Croxon"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[2]", "[1]", new String[]{"AcqDemo"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[3]", "[1]", new String[]{"Michael Chirlin"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[4]", "[1]", new String[]{"Jellyfish"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[1]", "[2]", new String[]{"Acceptance Letters"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[2]", "[2]", new String[]{"Jellyfish"}));
        assertTrue(tFixture.populateGridColumnRowWith("EditableGrid[3]", "[3]", "[2]", new String[]{"10"}));
        
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[1]", "[1]", new String[]{"Michael Chirlin","Ray Croxon"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[2]", "[1]", new String[]{"AcqDemo"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[3]", "[1]", new String[]{"Michael Chirlin"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[4]", "[1]", new String[]{"Jellyfish"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[1]", "[2]", new String[]{"Acceptance Letters"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[2]", "[2]", new String[]{"Jellyfish"}));
        assertTrue(tFixture.verifyGridColumnRowContains("EditableGrid[3]", "[3]", "[2]", new String[]{"10"}));
        
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[3]", "[1]", "[1]"), "Michael Chirlin,Ray Croxon");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[3]", "[2]", "[1]"), "AcqDemo");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[3]", "[3]", "[1]"), "Michael Chirlin");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[3]", "[4]", "[1]"), "Jellyfish.jpg");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[3]", "[1]", "[2]"), "Acceptance Letters");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[3]", "[2]", "[2]"), "Jellyfish.jpg");
        assertEquals(tFixture.getGridColumnRowValue("EditableGrid[3]", "[3]", "[2]"), "10");
    }
    
    @Test
    public void testSelectGridRow() throws Exception {
        assertTrue(tFixture.selectGridRow("EditableGrid", "[1]"));
        assertTrue(tFixture.selectGridRow("PagingGrid", "[4]"));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.clickOnButton("Cancel");
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}