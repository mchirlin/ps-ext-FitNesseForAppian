package com.appiancorp.ps.automatedtest.fixture.tempo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.appiancorp.ps.automatedtest.fixture.TempoFixtureTest;

public class NewsFixtureTest extends TempoFixtureTest {    
    
    private static String randString;
    private static Integer randInt;
    
    @BeforeClass
    public static void setUp() throws Exception {
      tFixture.clickOnMenu("Actions");
      tFixture.clickOnAction("Automated Testing Input");
      
      randString = tFixture.getRandomString(5);
      randInt = tFixture.getRandomIntegerFromTo(0, 9);
      tFixture.populateFieldWith("Text Field Test", new String[]{randString});
      tFixture.populateFieldWith("Integer Field Test", new String[]{randInt.toString()});
      tFixture.populateFieldWith("Decimal Field Test", new String[]{"123.45"});
      tFixture.populateFieldWith("Date and Time Test", new String[]{"2016-02-04 02:00"});
      
      tFixture.clickOnButton("Submit");
    }
    
    @Test 
    public void testClickOnRecordTag() throws Exception {
        tFixture.clickOnMenu("News");
        assertTrue(tFixture.verifyNewsFeedContainingTextTaggedWithIsPresent(randString, randString));
        assertTrue(tFixture.clickOnNewsFeedRecordTag(randString, randString));
    }
    
    @Test
    public void testVerifyFeed() {
        tFixture.clickOnMenu("News");        
        assertTrue(tFixture.verifyNewsFeedContainingTextIsPresent(randString));
        assertTrue(tFixture.verifyNewsFeedContainingTextIsNotPresent("Not present"));
    }
    
    @Test
    public void testNewsItemComments() {
        tFixture.clickOnMenu("News");        
        assertTrue(tFixture.verifyNewsFeedContainingTextCommentedWithIsPresent(randString, "Comment"));
    }
    
    @Test
    public void testMoreInfo() {
        tFixture.clickOnMenu("News");   
        assertTrue(tFixture.toggleMoreInfoForNewsFeedContainingText(randString));
        assertTrue(tFixture.verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(randString, "Label", "Value"));
    }
    
    @Test
    public void testRegex() throws Exception {
        tFixture.clickOnMenu("News");   
        assertEquals(tFixture.getRegexFromNewsFeedContainingText("\\[[a-zA-Z0-9]{5}\\]", randString), "[" + randString + "]");
        assertEquals(tFixture.getRegexFromNewsFeedContainingTextCommentedWith("\\[[0-9]\\]", randString, "Comment"), "[" + randInt + "]");
    }
    
    @Test
    public void testSearch() throws Exception {
        assertTrue(tFixture.clickOnMenu("News"));
        assertTrue(tFixture.searchFor(randString));
        assertTrue(tFixture.verifyNewsFeedContainingTextIsPresent(randString));
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        tFixture.clickOnMenu("News");
        tFixture.deleteNewsPost(randString);
    }
}
