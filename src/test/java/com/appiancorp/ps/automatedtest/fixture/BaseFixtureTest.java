package com.appiancorp.ps.automatedtest.fixture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testSetAppianLocaleTo() throws Exception {
        bFixture.setAppianLocaleTo("en_GB");
        assertEquals(TempoObject.getDateFormat(), "dd/MM/yyyy");
        assertEquals(TempoObject.getDateDisplayFormat(), "d MMM yyyy");
        assertEquals(TempoObject.getTimeFormat(), "HH:mm");
        assertEquals(TempoObject.getTimeDisplayFormat(), "HH:mm");
        assertEquals(TempoObject.getDatetimeFormat(), "dd/MM/yyyy HH:mm");
        assertEquals(TempoObject.getDatetimeDisplayFormat(), "d MMM yyyy HH:mm");
        
        bFixture.setAppianLocaleTo("en_US");
        assertEquals(TempoObject.getDateFormat(), "M/d/yyyy");
        assertEquals(TempoObject.getDateDisplayFormat(), "MMM d, yyyy");
        assertEquals(TempoObject.getTimeFormat(), "h:mm aa");
        assertEquals(TempoObject.getTimeDisplayFormat(), "h:mm aa");
        assertEquals(TempoObject.getDatetimeFormat(), "M/d/yyyy h:mm aa");
        assertEquals(TempoObject.getDatetimeDisplayFormat(), "MMM d, yyyy, h:mm aa");
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
        bFixture.tearDownSeleniumWebDriver();
    }
    
    @Test
    public void testLoginIntoWithUsernameAndPassword() throws Exception {        
        bFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
        assertTrue(bFixture.loginIntoWithUsernameAndPassword("https://apacdemo.appiancloud.com", "michael.chirlin@appian.com", "password1"));
        assertTrue(tFixture.logout());
        bFixture.tearDownSeleniumWebDriver();
    }
    
    @Test
    public void testLoginWithUsernameAndPassword() throws Exception {
        bFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
        bFixture.setAppianUrlTo("https://apacdemo.appiancloud.com");
        assertTrue(bFixture.loginWithUsernameAndPassword("michael.chirlin@appian.com","password1"));
        assertTrue(tFixture.logout());
        bFixture.tearDownSeleniumWebDriver();
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
        
    }
}