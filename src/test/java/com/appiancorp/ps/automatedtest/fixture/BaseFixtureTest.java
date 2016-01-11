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
      
      bFixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
    }
    
    @Test
    public void testSetAppianUrlTo() throws Exception {
        assertTrue(bFixture.setAppianUrlTo("https://apacdemo.appiancloud.com"));
        assertEquals(TempoObject.getUrl(), "https://apacdemo.appiancloud.com");
    }
    
    @Test
    public void testSetStartDatetime() throws Exception {
        assertTrue(bFixture.setStartDatetime());
    }
    
    @Test
    public void testSetDateFormatStringTo() throws Exception {
        assertTrue(bFixture.setDateFormatStringTo("dd/MM/yyyy"));
        assertEquals(TempoObject.DATE_FORMAT_STRING, "dd/MM/yyyy");
    }
    
    @Test
    public void testSetTimeFormatStringTo() throws Exception {
        assertTrue(bFixture.setTimeFormatStringTo("HH:mm"));
        assertEquals(TempoObject.TIME_FORMAT_STRING, "HH:mm");
    }
    
    @Test
    public void testSetTimeoutSecondsTo() throws Exception {
        assertTrue(bFixture.setTimeoutSecondsTo("10"));
        assertEquals(TempoObject.getTimeoutSeconds(), 10);
    }
    
    @Test
    public void testSetScreenshotPathTo() throws Exception {
        assertTrue(bFixture.setScreenshotPathTo("C:\\AutomatedTesting\\screenshots\\"));
    }
    
    @Test
    public void testOpen() throws Exception {
        assertTrue(bFixture.open("http://google.com"));
    }
    
    @Test
    public void testLoginIntoWithUsernameAndPassword() throws Exception {        
        assertTrue(bFixture.loginIntoWithUsernameAndPassword("https://apacdemo.appiancloud.com", "michael.chirlin@appian.com", "password1"));
        assertTrue(tFixture.logout());
    }
    
    @Test
    public void testLoginWithUsernameAndPassword() throws Exception {
        assertTrue(bFixture.setAppianUrlTo("https://apacdemo.appiancloud.com"));
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
        //TODO Create test case
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        bFixture.tearDownSeleniumWebDriver();
    }
}