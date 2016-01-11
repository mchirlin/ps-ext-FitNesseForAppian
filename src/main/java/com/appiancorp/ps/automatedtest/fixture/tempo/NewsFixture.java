package com.appiancorp.ps.automatedtest.fixture.tempo;

import com.appiancorp.ps.automatedtest.exception.MissingObjectException;
import com.appiancorp.ps.automatedtest.object.TempoNews;

public class NewsFixture extends TempoFixture {
    
    /** 
     * Verifies there is a news post containing specific text. 
     * The method will wait for the timeout period and refresh up to the configured number of refresh times before failing.<br>
     * <br>
     * FitNesse Example: <code>| verify news feed containing text | NEWS_TITLE | is present |</code>
     * 
     * @param newsText Text to search for in the news feed
     * @return True, if text is located in the news feed
     */
    public boolean verifyNewsFeedContainingTextIsPresent(String newsText) {
        return returnHandler(TempoNews.refreshAndWaitFor(newsText));
    }
    
    /**
     * Verifies there is not a news post containing specific text.<br>
     * <br>
     * FitNesse Example: <code>| verify news feed containing text | NEWS_TITLE | is present |</code><br>
     * <br>
     * The reason to use than <code>| reject | verify news feed containing text | NEWS_TEXT | is present |</code> is that this method will not refresh and wait. 
     * 
     * @param newsText Text to search for in the news feed
     * @return True, if no post is located with specific text
     */
    public boolean verifyNewsFeedContainingTextIsNotPresent(String newsText) {
        return returnHandler(!TempoNews.waitFor(newsText));
    }
    
    /**
     * Toggles the more info on a news feed post containing specific text.<br>
     * <br>
     * FitNesse Example: <code>| toggle more info for news feed containing text | NEWS_TEXT |</code>
     * 
     * @param newsText Text to search for in the news feed
     * @return True, if completed successfully
     */
    public boolean toggleMoreInfoForNewsFeedContainingText(String newsText) {
        if(!TempoNews.waitForMoreInfo(newsText)) {
            throw new MissingObjectException("News Post with More Info", newsText);
        }
        
        return returnHandler(TempoNews.toggleMoreInfo(newsText));
    }
    
    /**
     * Verifies there is a news post containing specific text with a specific label and value is present.<br>
     * <br>
     * FitNesse Example: <code>| verify news feed containing text | NEWS_TEXT | and more info with label | LABEL | and value | VALUE | is present |</code>
     * 
     * @param newsText Text to search for in the news feed
     * @param label Label to search for in the more info
     * @param value Value to search for in the more info
     * @return True, if a news post with the specific text, label, and value is located
     */
    public boolean verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(String newsText, String label, String value) {     
        if (!TempoNews.refreshAndWaitFor(newsText)) {
            throw new MissingObjectException("News Post", newsText);
        }
        
        return returnHandler(TempoNews.waitForLabelAndValue(newsText, label, value));
    }
    
    /**
     * Verifies there is a news post containing specific text with a specific tag is present.<br>
     * <br>
     * FitNesse Example: <code>| verify news feed containing text | NEWS_TEXT | tagged with | RECORD_TAG | is present |</code>
     * 
     * @param newsText Text to search for in the news feed
     * @param newsTag Tag to search for on the news post
     * @return True, if a news post with the specific text and tag is located
     */
    public boolean verifyNewsFeedContainingTextTaggedWithIsPresent(String newsText, String newsTag) {
        if (!TempoNews.refreshAndWaitFor(newsText)) {
            throw new MissingObjectException("News Post", newsText);
        }
        
        return returnHandler(TempoNews.waitForTag(newsText, newsTag));
    }
    
    /**
     * Verifies there is a news post containing specific text with a specific comment is present.<br>
     * <br>
     * FitNesse Example: <code>| verify news feed containing text | NEWS_TEXT | commented with | COMMENT | is present |</code>
     * 
     * @param newsText Text to search for in the news feed
     * @param newsComment Text to search for in the comments
     * @return True, if a news post with the specific text and comment is located
     */
    public boolean verifyNewsFeedContainingTextCommentedWithIsPresent(String newsText, String newsComment) {
        return returnHandler(TempoNews.refreshAndWaitForComment(newsText, newsComment));
    }
    
    /**
     * Returns a string that matches the regex, this could be useful in extracting a system generated value from the news feed.<br>
     * <br>
     * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | from news feed containing text | NEWS_TEXT |</code>
     * 
     * @param regex Regular expression string to search for within the news text
     * @param newsText Text to search for in the news feed
     * @return String that matches the regular expression
     */
    public String getRegexFromNewsFeedContainingText(String regex, String newsText) {
        if (!TempoNews.refreshAndWaitFor(newsText)) {
            throw new MissingObjectException("News Post", newsText);
        }
        
        return TempoNews.getRegexForNewsPost(regex, newsText);
    }
    
    /**
     * Returns a string that matches the regex from a comment, this could be useful in extracting a system generated value from the news feed.<br>
     * <br> 
     * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | from news feed containing text | NEWS_TEXT | commented with | COMMENTS |</code>
     * 
     * @param regex Regular expression string to search for within the news text
     * @param newsText Text to search for in the news feed
     * @param newsComment Text to search for in the comments
     * @return String that matches the regular expression
     */
    public String getRegexFromNewsFeedContainingTextCommentedWith(String regex, String newsText, String newsComment) {
        if (!TempoNews.refreshAndWaitForComment(newsText, newsComment)) {
            throw new MissingObjectException("News Post", newsText);
        }
        
        return TempoNews.getRegexForNewsPostComment(regex, newsText, newsComment);
    }
}
