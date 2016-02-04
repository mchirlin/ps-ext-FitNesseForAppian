package com.appiancorp.ps.automatedtest.fixture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.object.TempoObject;

public class BaseFixtureTest {

    private static BaseFixture bFixture;
    private static TempoFixture tFixture;
    
    @BeforeClass
    public static void setUp() throws Exception {
      bFixture = new BaseFixture();
      tFixture = new TempoFixture();
    }
    
    @Test
    public void testSetAppianUrlTo() throws Exception {
        bFixture.setAppianUrlTo("https://apacdemo.appiancloud.com");
        assertEquals(TempoObject.getUrl(), "https://apacdemo.appiancloud.com");
    }
    
    @Test
    public void testSetStartDatetime() throws Exception {
        bFixture.setStartDatetime();
    }
    
    @Test
    public void testSetDateFormatStringTo() throws Exception {
        bFixture.setDateFormatStringTo("dd/MM/yyyy");
        assertEquals(TempoObject.DATE_FORMAT_STRING, "dd/MM/yyyy");
    }
    
    @Test
    public void testSetTimeFormatStringTo() throws Exception {
        bFixture.setTimeFormatStringTo("HH:mm");
        assertEquals(TempoObject.TIME_FORMAT_STRING, "HH:mm");
    }
    
    @Test
    public void testSetDateDisplayFormatStringTo() throws Exception {
        bFixture.setDateDisplayFormatStringTo("d MM yyyy");
        assertEquals(TempoObject.DATE_DISPLAY_FORMAT_STRING, "d MM yyyy");
    }
    
    @Test
    public void testSetTimeDisplayFormatStringTo() throws Exception {
        bFixture.setTimeDisplayFormatStringTo("HH:mm");
        assertEquals(TempoObject.TIME_DISPLAY_FORMAT_STRING, "HH:mm");
    }
    
    @Test
    public void testSetTimeoutSecondsTo() throws Exception {
        bFixture.setTimeoutSecondsTo("10");
        assertEquals(TempoObject.getTimeoutSeconds(), 10);
    }
    
    @Test
    public void testSetScreenshotPathTo() throws Exception {
        bFixture.setScreenshotPathTo("C:\\AutomatedTesting\\screenshots\\");
    }
    
    @Test
    public void testOpen() throws Exception {
        bFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
        assertTrue(bFixture.open("http://google.com"));
    }
    
    @Test
    public void testLoginIntoWithUsernameAndPassword() throws Exception {        
        bFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
        assertTrue(bFixture.loginIntoWithUsernameAndPassword("https://apacdemo.appiancloud.com", "michael.chirlin@appian.com", "password1"));
        assertTrue(tFixture.logout());
    }
    
    @Test
    public void testLoginWithUsernameAndPassword() throws Exception {
        bFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
        bFixture.setAppianUrlTo("https://apacdemo.appiancloud.com");
        assertTrue(bFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com","password1"));
        assertTrue(tFixture.logout());
    }
    
    @Test
    public void testLoginWithTermsWithUsernameAndPassword() throws Exception {
        //TODO Find test site with terms
    }
    
    @Test
    public void testWaitForSecondsMinutesHours(){
        //TODO Create test case
    }
    
    @Test
    public void testWaitForWorking(){
        //TODO Create test case
    }
    
    @Test
    public void testGetRandomString(){
        assertTrue(bFixture.getRandomString(7).length() == 7);
    }
    @Test
    public void testGetRandomIntFromTo(){
        int randomInt = bFixture.getRandomIntegerFromTo(0, 10);
        assertTrue((randomInt<10) && (randomInt>=0));
    }
    @Test
    public void testGetRandomDecimalFromTo(){
        double randomDec = bFixture.getRandomDecimalFromTo(0, 10);
        assertTrue((randomDec<10) && (randomDec>=0));
    }

    @Test
    public void testGetRandomDecimalFromToWith(){
        double randomDec = bFixture.getRandomDecimalFromToWith(0.1, 10.01, 6);
        assertTrue((randomDec<10.01) && (randomDec>=0.1));
    }
    
    
    @AfterClass
    public static void tearDown() throws Exception {
        bFixture.tearDownSeleniumWebDriver();
    }
}