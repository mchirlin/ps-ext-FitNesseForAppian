package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.tempo.TempoFixture;
import com.appiancorp.ps.automatedtest.object.TempoObject;

public class InterfaceFixtureTest {    
    
    private static TempoFixture tFixture;
    private static ActionsFixture aFixture;
    private static InterfaceFixture iFixture;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture = new TempoFixture();
      aFixture = new ActionsFixture();
      iFixture = new InterfaceFixture();
      
      tFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
      tFixture.setAppianUrlTo("https://apacdemo.appiancloud.com/suite");
      tFixture.setTimeoutSecondsTo("10");
      tFixture.setStartDatetime();
      tFixture.setDateFormatStringTo("dd/MM/yyyy");
      tFixture.setTimeFormatStringTo("HH:mm");
      
      tFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com", "password1");
      
      tFixture.clickOnMenu("Actions");
      aFixture.clickOnAction("Automated Testing");
    }
    
    @Test
    public void testGetTitle() throws Exception {
        String getTitle = iFixture.getTitle();
        assertEquals(getTitle, "Form");
    }
    
    @Test
    public void testGetInstructions() throws Exception {
        String getInstructions = iFixture.getInstructions();
        assertEquals(getInstructions, "Instructions");
    }
        
    @Test
    public void testPopulateFieldWithGetFieldValueVerifyFieldContains() throws Exception {
        // Section 1
        assertTrue(iFixture.populateFieldWith("TextField", new String[]{"text"}));
        assertTrue(iFixture.populateFieldWith("ParagraphField", new String[]{"paragraph"}));
        assertTrue(iFixture.populateFieldWith("EncryptedTextField", new String[]{"encrypted"}));
        assertTrue(iFixture.populateFieldWith("IntegerField", new String[]{"1"}));
        assertTrue(iFixture.populateFieldWith("DecimalField", new String[]{"2.2"}));
        assertTrue(iFixture.populateFieldWith("DateField", new String[]{"+1 day"}));
        assertTrue(iFixture.populateFieldWith("DatetimeField", new String[]{"+1 hour"}));
        assertTrue(iFixture.populateFieldWith("MultipleSelectField", new String[]{"Option 1", "Option 2"}));
        assertTrue(iFixture.populateFieldWith("RadioField", new String[]{"Option 1"}));
        assertTrue(iFixture.populateFieldWith("CheckboxField", new String[]{"Option 1", "Option 2"}));
        assertTrue(iFixture.populateFieldWith("FileUploadField", new String[]{"C:\\Users\\michael.chirlin\\Documents\\Low Risk.jpg"}));
        
        assertEquals(iFixture.getFieldValue("TextField"), "text");
        assertEquals(iFixture.getFieldValue("ParagraphField"), "paragraph");
        assertEquals(iFixture.getFieldValue("EncryptedTextField"), "encrypted");
        assertEquals(iFixture.getFieldValue("IntegerField"), "1");
        assertEquals(iFixture.getFieldValue("DecimalField"), "2.2");
        assertEquals(iFixture.getFieldValue("DateField"), new SimpleDateFormat(TempoObject.DATE_FORMAT_STRING).format(DateUtils.addDays(TempoObject.getStartDatetime(), 1)));
        assertEquals(iFixture.getFieldValue("DatetimeField"), new SimpleDateFormat(TempoObject.DATETIME_FORMAT_STRING).format(DateUtils.addHours(TempoObject.getStartDatetime(), 1)));
        assertEquals(iFixture.getFieldValue("MultipleSelectField"), "Option 1,Option 2");
        assertEquals(iFixture.getFieldValue("RadioField"), "Option 1");
        assertEquals(iFixture.getFieldValue("CheckboxField"), "Option 1,Option 2");
        assertEquals(iFixture.getFieldValue("FileUploadField"), "Low Risk.jpg"); // Notice this doesn't include the entire path
        
        assertTrue(iFixture.verifyFieldContains("TextField", new String[]{"text"}));
        assertTrue(iFixture.verifyFieldContains("ParagraphField", new String[]{"paragraph"}));
        assertTrue(iFixture.verifyFieldContains("EncryptedTextField", new String[]{"encrypted"}));
        assertTrue(iFixture.verifyFieldContains("IntegerField", new String[]{"1"}));
        assertTrue(iFixture.verifyFieldContains("DecimalField", new String[]{"2.2"}));
        assertTrue(iFixture.verifyFieldContains("DateField", new String[]{"+1 day"}));
        assertTrue(iFixture.verifyFieldContains("DatetimeField", new String[]{"+1 hour"}));
        assertTrue(iFixture.verifyFieldContains("MultipleSelectField", new String[]{"Option 1","Option 2"}));
        assertTrue(iFixture.verifyFieldContains("RadioField", new String[]{"Option 1"}));
        assertTrue(iFixture.verifyFieldContains("CheckboxField", new String[]{"Option 1","Option 2"}));
        assertTrue(iFixture.verifyFieldContains("FileUploadField", new String[]{"C:\\Users\\michael.chirlin\\Documents\\Low Risk.jpg"}));
        
        // Section 1.5
        assertTrue(iFixture.populateFieldWith("DateField[2]", new String[]{"15/11/2015"}));
        assertTrue(iFixture.populateFieldWith("DateField[3]", new String[]{"+5 days"}));
        assertTrue(iFixture.populateFieldWith("DatetimeField[2]", new String[]{"15/11/2015"}));
        assertTrue(iFixture.populateFieldWith("DatetimeField[3]", new String[]{"15/11/2015 14:00"}));
        assertTrue(iFixture.populateFieldWith("DatetimeField[4]", new String[]{"+5 minutes"}));
        assertTrue(iFixture.populateFieldWith("DatetimeField[5]", new String[]{"+5 hours"}));
        assertTrue(iFixture.populateFieldWith("DatetimeField[6]", new String[]{"+5 days"}));
        
        assertEquals(iFixture.getFieldValue("DateField[2]"), "15/11/2015");
        assertEquals(iFixture.getFieldValue("DateField[3]"), new SimpleDateFormat(TempoObject.DATE_FORMAT_STRING).format(DateUtils.addDays(TempoObject.getStartDatetime(), 5)));
        assertEquals(iFixture.getFieldValue("DatetimeField[2]"), "15/11/2015 00:00");
        assertEquals(iFixture.getFieldValue("DatetimeField[3]"), "15/11/2015 14:00");
        assertEquals(iFixture.getFieldValue("DatetimeField[4]"), new SimpleDateFormat(TempoObject.DATETIME_FORMAT_STRING).format(DateUtils.addMinutes(TempoObject.getStartDatetime(), 5)));
        assertEquals(iFixture.getFieldValue("DatetimeField[5]"), new SimpleDateFormat(TempoObject.DATETIME_FORMAT_STRING).format(DateUtils.addHours(TempoObject.getStartDatetime(), 5)));
        assertEquals(iFixture.getFieldValue("DatetimeField[6]"), new SimpleDateFormat(TempoObject.DATETIME_FORMAT_STRING).format(DateUtils.addDays(TempoObject.getStartDatetime(), 5)));
        
        assertTrue(iFixture.verifyFieldContains("DateField[2]", new String[]{"15/11/2015"}));
        assertTrue(iFixture.verifyFieldContains("DateField[3]", new String[]{"+5 days"}));
        assertTrue(iFixture.verifyFieldContains("DatetimeField[2]", new String[]{"15/11/2015"}));
        assertTrue(iFixture.verifyFieldContains("DatetimeField[3]", new String[]{"15/11/2015 14:00"}));
        assertTrue(iFixture.verifyFieldContains("DatetimeField[4]", new String[]{"+5 minutes"}));
        assertTrue(iFixture.verifyFieldContains("DatetimeField[5]", new String[]{"+5 hours"}));
        assertTrue(iFixture.verifyFieldContains("DatetimeField[6]", new String[]{"+5 days"}));

        // Section 2
        assertTrue(iFixture.populateFieldWith("TextField[2]", new String[]{"text2"}));
        
        assertEquals(iFixture.getFieldValue("TextField[2]"), "text2");
        
        assertTrue(iFixture.verifyFieldContains("TextField[2]", new String[]{"text2"}));
       
        // Section 2.5
        assertTrue(iFixture.populateFieldWith("UserPicker", new String[]{"Michael Chirlin", "Ray Croxon"}));
        assertTrue(iFixture.populateFieldWith("GroupPicker", new String[]{"AcqDemo"}));
        assertTrue(iFixture.populateFieldWith("UserGroupPicker", new String[]{"Michael Chirlin"}));
        assertTrue(iFixture.populateFieldWith("DocumentPicker", new String[]{"Jellyfish"}));
        assertTrue(iFixture.populateFieldWith("FolderPicker", new String[]{"Acceptance Letters"}));
        assertTrue(iFixture.populateFieldWith("DocumentFolderPicker", new String[]{"Jellyfish"}));
        assertTrue(iFixture.populateFieldWith("CustomPicker", new String[]{"5"}));
        
        assertEquals(iFixture.getFieldValue("UserPicker"), "Michael Chirlin,Ray Croxon");
        assertEquals(iFixture.getFieldValue("GroupPicker"), "AcqDemo");
        assertEquals(iFixture.getFieldValue("UserGroupPicker"), "Michael Chirlin");
        assertEquals(iFixture.getFieldValue("DocumentPicker"), "Jellyfish.jpg"); // Note I need the extension here
        assertEquals(iFixture.getFieldValue("FolderPicker"), "Acceptance Letters");
        assertEquals(iFixture.getFieldValue("DocumentFolderPicker"), "Jellyfish.jpg");
        assertEquals(iFixture.getFieldValue("CustomPicker"), "5"); // Note I need the extension here
        
        assertTrue(iFixture.verifyFieldContains("UserPicker", new String[]{"Michael Chirlin", "Ray Croxon"}));
        assertTrue(iFixture.verifyFieldContains("GroupPicker", new String[]{"AcqDemo"}));
        assertTrue(iFixture.verifyFieldContains("UserGroupPicker", new String[]{"Michael Chirlin"}));
        assertTrue(iFixture.verifyFieldContains("DocumentPicker", new String[]{"Jellyfish.jpg"}));
        assertTrue(iFixture.verifyFieldContains("FolderPicker", new String[]{"Acceptance Letters"}));
        assertTrue(iFixture.verifyFieldContains("DocumentFolderPicker", new String[]{"Jellyfish"}));
        assertTrue(iFixture.verifyFieldContains("CustomPicker", new String[]{"5"}));
    }
    
    @Test
    public void testPopulateFieldWithValueGetFieldValueVerifyFieldContains() throws Exception {
        assertTrue(iFixture.populateFieldWithValue("SelectField", "Option, 1"));
        
        assertEquals(iFixture.getFieldValue("SelectField"), "Option, 1");
        
        assertTrue(iFixture.verifyFieldContainsValue("SelectField", "Option, 1"));
    }
    
    @Test
    public void testPopulateFieldInSectionWithGetFieldInSectionVerifyFieldInSectionContains() throws Exception {
        assertTrue(iFixture.populateFieldInSectionWith("[1]", "Section 2", new String[]{"noLabel"}));
        
        assertEquals(iFixture.getFieldInSectionValue("[1]", "Section 2"), "noLabel");
        
        assertTrue(iFixture.verifyFieldInSectionContains("[1]", "Section 2", new String[]{"noLabel"}));
    }
    
    @Test
    public void testClickOnOptions() throws Exception {
        assertTrue(iFixture.clickOnRadioOption("Click Me!"));
        assertTrue(iFixture.clickOnCheckboxOption("Click Me Too!"));
    }
    
    @Test
    public void testPopulateGridColumnRowWithGetGridColumnRowValueVerifyGridColumnRowContains() throws Exception {
        // EditableGrid
        assertTrue(iFixture.populateGridColumnRowWith("[1]", "TextField", "[1]", new String[]{"gridText"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid", "[3]", "[1]", new String[]{"gridParagraph"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid", "EncryptedTextField", "[1]", new String[]{"gridEncrypted"}));
        assertTrue(iFixture.populateGridColumnRowWith("[1]", "[5]", "[1]", new String[]{"1"}));
        assertTrue(iFixture.populateGridColumnRowWith("[1]", "DecimalField", "[1]", new String[]{"2.2"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid", "[7]", "[1]", new String[]{"+1 day"}));
        
        assertEquals(iFixture.getGridColumnRowValue("[1]", "TextField", "[1]"), "gridText");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid", "[3]", "[1]"), "gridParagraph");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid", "EncryptedTextField", "[1]"), "gridEncrypted");
        assertEquals(iFixture.getGridColumnRowValue("[1]", "[5]", "[1]"), "1");
        assertEquals(iFixture.getGridColumnRowValue("[1]", "DecimalField", "[1]"), "2.2");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid", "[7]", "[1]"), new SimpleDateFormat(TempoObject.DATE_FORMAT_STRING).format(DateUtils.addDays(TempoObject.getStartDatetime(), 1)));
        
        assertTrue(iFixture.verifyGridColumnRowContains("[1]", "TextField", "[1]", new String[]{"gridText"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid", "[3]", "[1]", new String[]{"gridParagraph"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid", "EncryptedTextField", "[1]", new String[]{"gridEncrypted"}));
        assertTrue(iFixture.verifyGridColumnRowContains("[1]", "[5]", "[1]", new String[]{"1"}));
        assertTrue(iFixture.verifyGridColumnRowContains("[1]", "DecimalField", "[1]", new String[]{"2.2"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid", "[7]", "[1]", new String[]{"+1 day"}));        
        
        // EditableGrid[2]
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[2]", "DatetimeField", "[1]", new String[]{"+1 hour"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[2]", "[1]", "[2]", new String[]{"+1 minute"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[2]", "[2]", "[1]", new String[]{"Option 1"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[2]", "SelectField", "[2]", new String[]{"Option 2"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[2]", "RadioField", "[1]", new String[]{"Option 1"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[2]", "[3]", "[2]", new String[]{"Option 2"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[2]", "[4]", "[1]", new String[]{"Option 1","Option 2"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[2]", "CheckboxField", "[2]", new String[]{"Option 2"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[2]", "FileUploadField", "[1]", new String[]{"C:\\Users\\michael.chirlin\\Documents\\Medium Risk.jpg"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[2]", "[5]", "[2]", new String[]{"C:\\Users\\michael.chirlin\\Documents\\High Risk.jpg"}));
        
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[2]", "DatetimeField", "[1]"), new SimpleDateFormat(TempoObject.DATETIME_FORMAT_STRING).format(DateUtils.addHours(TempoObject.getStartDatetime(), 1)));
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[2]", "[1]", "[2]"), new SimpleDateFormat(TempoObject.DATETIME_FORMAT_STRING).format(DateUtils.addMinutes(TempoObject.getStartDatetime(), 1)));
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[2]", "[2]", "[1]"), "Option 1");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[2]", "SelectField", "[2]"), "Option 2");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[2]", "RadioField", "[1]"), "Option 1");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[2]", "[3]", "[2]"), "Option 2");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[2]", "[4]", "[1]"), "Option 1,Option 2");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[2]", "CheckboxField", "[2]"), "Option 2");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[2]", "FileUploadField", "[1]"), "Medium Risk.jpg");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[2]", "[5]", "[2]"), "High Risk.jpg");
        
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[2]", "DatetimeField", "[1]", new String[]{"+1 hour"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[2]", "[1]", "[2]", new String[]{"+1 minute"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[2]", "[2]", "[1]", new String[]{"Option 1"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[2]", "SelectField", "[2]", new String[]{"Option 2"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[2]", "RadioField", "[1]", new String[]{"Option 1"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[2]", "[3]", "[2]", new String[]{"Option 2"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[2]", "[4]", "[1]", new String[]{"Option 1","Option 2"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[2]", "CheckboxField", "[2]", new String[]{"Option 2"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[2]", "FileUploadField", "[1]", new String[]{"C:\\Users\\michael.chirlin\\Documents\\Medium Risk.jpg"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[2]", "[5]", "[2]", new String[]{"C:\\Users\\michael.chirlin\\Documents\\High Risk.jpg"}));
        
        // EditableGrid[3]
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[3]", "[1]", "[1]", new String[]{"Michael Chirlin","Ray Croxon"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[3]", "[2]", "[1]", new String[]{"AcqDemo"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[3]", "[3]", "[1]", new String[]{"Michael Chirlin"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[3]", "[4]", "[1]", new String[]{"Jellyfish"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[3]", "[1]", "[2]", new String[]{"Acceptance Letters"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[3]", "[2]", "[2]", new String[]{"Jellyfish"}));
        assertTrue(iFixture.populateGridColumnRowWith("EditableGrid[3]", "[3]", "[2]", new String[]{"10"}));
        
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[3]", "[1]", "[1]"), "Michael Chirlin,Ray Croxon");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[3]", "[2]", "[1]"), "AcqDemo");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[3]", "[3]", "[1]"), "Michael Chirlin");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[3]", "[4]", "[1]"), "Jellyfish.jpg");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[3]", "[1]", "[2]"), "Acceptance Letters");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[3]", "[2]", "[2]"), "Jellyfish.jpg");
        assertEquals(iFixture.getGridColumnRowValue("EditableGrid[3]", "[3]", "[2]"), "10");
        
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[3]", "[1]", "[1]", new String[]{"Michael Chirlin","Ray Croxon"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[3]", "[2]", "[1]", new String[]{"AcqDemo"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[3]", "[3]", "[1]", new String[]{"Michael Chirlin"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[3]", "[4]", "[1]", new String[]{"Jellyfish"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[3]", "[1]", "[2]", new String[]{"Acceptance Letters"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[3]", "[2]", "[2]", new String[]{"Jellyfish"}));
        assertTrue(iFixture.verifyGridColumnRowContains("EditableGrid[3]", "[3]", "[2]", new String[]{"10"}));
    }
    
    @Test
    public void testSelectGridRow() throws Exception {
        assertTrue(iFixture.selectGridRow("EditableGrid", "[1]"));
        assertTrue(iFixture.selectGridRow("PagingGrid", "[4]"));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        iFixture.clickOnButton("Cancel");
        tFixture.logout();
        tFixture.tearDownSeleniumWebDriver();
    }
}