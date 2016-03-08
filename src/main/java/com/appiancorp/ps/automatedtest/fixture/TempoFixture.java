package com.appiancorp.ps.automatedtest.fixture;

import org.apache.log4j.Logger;

import com.appiancorp.ps.automatedtest.object.TempoAction;
import com.appiancorp.ps.automatedtest.object.TempoButton;
import com.appiancorp.ps.automatedtest.object.TempoCheckboxField;
import com.appiancorp.ps.automatedtest.object.TempoField;
import com.appiancorp.ps.automatedtest.object.TempoForm;
import com.appiancorp.ps.automatedtest.object.TempoGrid;
import com.appiancorp.ps.automatedtest.object.TempoLinkField;
import com.appiancorp.ps.automatedtest.object.TempoLogin;
import com.appiancorp.ps.automatedtest.object.TempoMenu;
import com.appiancorp.ps.automatedtest.object.TempoNews;
import com.appiancorp.ps.automatedtest.object.TempoRadioField;
import com.appiancorp.ps.automatedtest.object.TempoRecord;
import com.appiancorp.ps.automatedtest.object.TempoRecordType;
import com.appiancorp.ps.automatedtest.object.TempoRecordGrid;
import com.appiancorp.ps.automatedtest.object.TempoReport;
import com.appiancorp.ps.automatedtest.object.TempoSearch;
import com.appiancorp.ps.automatedtest.object.TempoSection;
import com.appiancorp.ps.automatedtest.object.TempoTask;

/** 
 * This is the tempo class for integrating Appian and Fitnesse.
 * This class contains fixture commands which are specific to the Tempo interface
 * @author michael.chirlin
 *
 */
public class TempoFixture extends BaseFixture {
	
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoFixture.class);
    
	public TempoFixture() {
		super();
	}

	/**
     * Clicks on the associated tempo menu.<br>
     * <br>
     * Example: <code>| click on menu | MENU_NAME |</code>
     * 
     * @param tempoMenu Name of tempo menu, e.g. Records, Tasks, News
     * @return True if action completed
     */
	public boolean clickOnMenu(String tempoMenu) {
		if(!TempoMenu.waitFor(tempoMenu, settings)) {
		    exceptionHandler(ERROR_MISSING, "Tempo Menu", tempoMenu);
		}
		
		return returnHandler(TempoMenu.click(tempoMenu, settings));
	}
	   /**
     * Populates search fields in News, Reports, and Records. <br>
     * <br>
     * Example: <code>| search for | SEARCH_TERM |</code>
     * 
     * @param searchTerm The term to search for News, Reports, and Records
     * @return True if action completed
     */
	public boolean searchFor(String searchTerm){
        if(!TempoSearch.waitFor(searchTerm, settings)) {
            exceptionHandler(ERROR_MISSING, "Field", searchTerm);
        }
        
        return returnHandler(TempoSearch.searchFor(searchTerm, settings));
    }
	
	/**
    * Logs out of Appian
    * <br>
    * Example: <code>| logout |</code>
    * 
    * @return True if action completed
    */
	public boolean logout() {
        if(!TempoLogin.waitForLogout(settings)) {
            exceptionHandler(ERROR_MISSING, "Logout Menu");
        }
        
        return returnHandler(TempoLogin.logout(settings));
    }
	
	/*
	 * News
	 */
	
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
        return returnHandler(TempoNews.refreshAndWaitFor(newsText, settings));
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
        return returnHandler(!TempoNews.waitFor(newsText, settings.getNotPresentTimeoutSeconds(), settings));
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
        if(!TempoNews.waitForMoreInfo(newsText, settings)) {
            exceptionHandler(ERROR_MISSING, "News Post with More Info", newsText);
        }
        
        return returnHandler(TempoNews.toggleMoreInfo(newsText, settings));
    }
    
    /**
     * Deletes a news post.<br>
     * <br>
     * FitNesse Example: <code>| delete news post | NEWS_TEXT |</code>
     * 
     * @param newsText Text to search for in the news feed
     * @return True, if completed successfully
     */
    public boolean deleteNewsPost(String newsText) {
        if(!TempoNews.waitFor(newsText, settings)) {
            exceptionHandler(ERROR_MISSING, "News Post", newsText);
        }
        
        return returnHandler(TempoNews.deleteNewsPost(newsText, settings));
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
        if (!TempoNews.refreshAndWaitFor(newsText, settings)) {
            exceptionHandler(ERROR_MISSING, "News Post", newsText);
        }
        
        return returnHandler(TempoNews.waitForLabelAndValue(newsText, label, value, settings));
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
        if (!TempoNews.refreshAndWaitFor(newsText, settings)) {
            exceptionHandler(ERROR_MISSING, "News Post", newsText);
        }
        
        return returnHandler(TempoNews.waitForTag(newsText, newsTag, settings));
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
        return returnHandler(TempoNews.refreshAndWaitForComment(newsText, newsComment, settings));
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
        if (!TempoNews.refreshAndWaitFor(newsText, settings)) {
            exceptionHandler(ERROR_MISSING, "News Post", newsText);
        }
        
        return TempoNews.getRegexForNewsPost(regex, newsText, settings);
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
        if (!TempoNews.refreshAndWaitForComment(newsText, newsComment, settings)) {
            exceptionHandler(ERROR_MISSING, "News Post", newsText);
        }
        
        return TempoNews.getRegexForNewsPostComment(regex, newsText, newsComment, settings);
    }
    
    /**
     * Clicks on a record tag to navigate to a record summary dashboard<br>
     * <br>
     * FitNesse Example: <code>| click on news feed | NEWS_TEXT | record tag | RECORD_TAG |</code>
     * @param newsText Text to search for in the news feed
     * @param recordTag Record tag text
     * @return True, if action completed successfully
     */
    public boolean clickOnNewsFeedRecordTag(String newsText, String recordTag) {
        if (!TempoNews.refreshAndWaitForTag(newsText, recordTag, settings)) {
            exceptionHandler(ERROR_MISSING, "Record Tag", recordTag);
        }
        
        return returnHandler(TempoNews.clickOnRecordTag(newsText, recordTag, settings));
    }
    
    /*
     * Tasks
     */
    
    /**
     * Clicks on the associated task.<br>
     * <br>
     * FitNesse Example: <code>| click on task | TASK_NAME |</code>
     * 
     * @param taskName Name of task to click (partial names are acceptable)
     * If multiple task contain the same name the first will be selected
     * @return True if action completed
     */
    public boolean clickOnTask(String taskName) {
        if(!TempoTask.refreshAndWaitFor(taskName, settings)) {
            exceptionHandler(ERROR_MISSING, "Task", taskName);
        }
        
        return returnHandler(TempoTask.click(taskName, settings));
     }
     
    /** 
     * Verifies if task is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify task | TASK_NAME | is present |</code>
     * 
     * @param taskName Name of the task
     * @return True, if task is located
     */
    public boolean verifyTaskIsPresent(String taskName) {
        return returnHandler(TempoTask.refreshAndWaitFor(taskName, settings));
    }
     
    /** 
     * Verifies if task is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify report | TASK_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify task | TASK_NAME | is present |</code> as it will not refresh and wait.
     * 
     * @param taskName Name of the task
     * @return True, if task is located
     */
    public boolean verifyTaskIsNotPresent(String taskName) {
        return returnHandler(!TempoTask.waitFor(taskName, settings.getNotPresentTimeoutSeconds(), settings));
    }
    
    /**
     * Verify a task with a specific name has a specific deadline<br>
     * <br>
     * FitNesse Example: <code>| verify task | TASK_NAME | has deadline of | DEADLINE |</code>
     * 
     * @param taskName Name of the task
     * @param deadline Deadline matching the Appian interface, e.g. 8d
     * @return True, if task with particular deadline is located
     */
    public boolean verifyTaskHasDeadlineOf(String taskName, String deadline) {
        if(!TempoTask.waitFor(taskName, settings)) {
            exceptionHandler(ERROR_MISSING, "Task", taskName);
        }
        
        return returnHandler(TempoTask.hasDeadlineOf(taskName, deadline, settings));
    }
    
    /**
     * Click on a task report<br>
     * <br>
     * FitNesse Example: <code>| click on task report | TASK_REPORT |</code>
     * @param taskReport Name of task report
     * @return True, if action completed successfully
     */
    public boolean clickOnTaskReport(String taskReport) {
        if(!TempoTask.waitForTaskReport(taskReport, settings)) {
            exceptionHandler(ERROR_MISSING, "Task Report", taskReport);
        }
        
        return returnHandler(TempoTask.clickOnTaskReport(taskReport, settings));
    }
    
    /*
     * Records
     */
    
    /**
     * @deprecated Replaced by {@link #clickOnRecordType(String)}
     * @param listName List name
     * @return boolean
     */
    @Deprecated
    public boolean clickOnRecordList(String listName) { 
        return clickOnRecordType(listName); 
    }

    /**
     * Clicks on the record type.<br>
     * <br>
     * FitNesse Example: <code>| click on record type | RECORD_TYPE |</code>
     * <code>| click on record type | RECORD_TYPE[INDEX] |</code>
     * 
     * @param typeName Name of record type to click (partial names are acceptable)
     * If multiple record types contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordType(String typeName) {
       if(!TempoRecordType.waitFor(typeName, settings)) {
           exceptionHandler(ERROR_MISSING, "Record Type", typeName);
       }
       
       return returnHandler(TempoRecordType.click(typeName, settings));
    }
    
    /**
     * @deprecated Replaced by {@link #clickOnRecordTypeUserFilter(String)}
     * @param facetOption Facet option
     * @return boolean
     */
    @Deprecated
    public boolean clickOnRecordListFacetOption(String facetOption) {
        return  clickOnRecordTypeUserFilter(facetOption);
    }
    
    /**
     * Clicks on the record type user filter.<br>
     * <br>
     * FitNesse Example: <code>| click on record type user filter | USER_FILTER |</code>
     * 
     * @param userFilter User Filter to click (partial names are acceptable)
     * If multiple user filters contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordTypeUserFilter(String userFilter) {
        if(!TempoRecordType.waitForUserFilter(userFilter, settings)) {
            exceptionHandler(ERROR_MISSING, "Record User Filter", userFilter);
        }
        
        return returnHandler(TempoRecordType.clickOnUserFilter(userFilter, settings)); 
    }
    
    /** 
     * @deprecated Replaced by {@link #verifyRecordTypeUserFilterIsPresent(String)}
     * @param facetOption Facet option
     * @return boolean
     */
    @Deprecated
    public boolean verifyRecordListFacetOptionIsPresent(String facetOption) {
        return verifyRecordTypeUserFilterIsPresent(facetOption);
    }
    
    /** 
     * Verifies if user filter is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record type user filter | USER_FILTER | is present |</code>
     * 
     * @param userFilter Name of user filter
     * @return True, if user filter is located
     */
    public boolean verifyRecordTypeUserFilterIsPresent(String userFilter) {
        return returnHandler(TempoRecordType.waitForUserFilter(userFilter, settings));
    }
    
    /**
     * @deprecated Replaced by {@link #clickOnRecord(String)}
     * @param itemName Item name
     * @return boolean
     */
    @Deprecated
    public boolean clickOnRecordItem(String itemName) {
        return clickOnRecord(itemName);
    }

    /**
     * Clicks on the associated record.<br>
     * <br>
     * FitNesse Example: <code>| click on record | RECORD_NAME |</code>
     * 
     * @param recordName Name of record to click (partial names are acceptable)
     * If multiple records contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecord(String recordName) {
        if(!TempoRecord.refreshAndWaitFor(recordName, settings)) {
            exceptionHandler(ERROR_MISSING, "Record", recordName);
        }
        
        return returnHandler(TempoRecord.click(recordName, settings));
    }
    
    /** 
     * @deprecated Replaced by {@link #verifyRecordIsPresent(String)}
     * @param itemName Item name
     * @return boolean
     */
    @Deprecated
    public boolean verifyRecordItemIsPresent(String itemName) {
        return verifyRecordIsPresent(itemName);
    }
    
    /** 
     * Verifies if record is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record | RECORD_NAME | is present |</code>
     * 
     * @param recordName Name of record 
     * @return True, if record is located
     */
    public boolean verifyRecordIsPresent(String recordName) {
        return returnHandler(TempoRecord.waitFor(recordName, settings));
    }
   
    /** 
     * @deprecated Replaced by {@link #verifyRecordIsNotPresent(String)}
     * @param itemName Item name
     * @return boolean
     */
    @Deprecated
    public boolean verifyRecordItemIsNotPresent(String itemName) {
        return verifyRecordIsNotPresent(itemName);
    }
  
    /** 
     * Verifies if record is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record | RECORD_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify record | RECORD_NAME | is present |</code> as it will not refresh and wait.
     * 
     * @param recordName Name of record
     * @return True, if record is not located
     */
    public boolean verifyRecordIsNotPresent(String recordName) {
        return returnHandler(!TempoRecord.waitFor(recordName, settings.getNotPresentTimeoutSeconds(), settings));
    }
 
    /**
     * @deprecated Replaced by {@link #clickOnRecordView(String)}
     * @param facetName Facet name
     * @return boolean
     */
    @Deprecated
    public boolean clickOnRecordItemFacet(String facetName) {
        return clickOnRecordView(facetName);
    }
    
    /**
     * Clicks on the associated record view.<br>
     * <br>
     * FitNesse Example: <code>| click on record view | VIEW_NAME |</code>
     * 
     * @param viewName Name of view (e.g. Summary, News, Related Actions, etc.) to click (partial names are acceptable)
     * If multiple views contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordView(String viewName) {
        if(!TempoRecord.waitForView(viewName, settings)) {
            exceptionHandler(ERROR_MISSING, "Record Item Facet", viewName);
        }
        
        return returnHandler(TempoRecord.clickOnView(viewName, settings));
    }
    
    /**
     * @deprecated Replaced by {@link #clickOnRecordRelatedAction(String)}
     * @param relatedActionName Related action name
     * @return boolean
     */
    @Deprecated
    public boolean clickOnRecordItemRelatedAction(String relatedActionName) {
        return clickOnRecordRelatedAction(relatedActionName);
    }
    
    /**
     * Clicks on the associated related action.<br>
     * <br>
     * FitNesse Example: <code>| click on record related action | RELATED_ACTION_NAME |</code>
     * 
     * @param relatedActionName Name of related action to click (partial names are acceptable)
     * If multiple related actions contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordRelatedAction(String relatedActionName) {
        if(!TempoRecord.refreshAndWaitForRelatedAction(relatedActionName, settings)) {
            exceptionHandler(ERROR_MISSING, "Related Action", relatedActionName);
        }
        
        return returnHandler(TempoRecord.clickOnRelatedAction(relatedActionName, settings));
    }
    
   
    /** 
     * @deprecated Replaced by {@link #verifyRecordRelatedActionIsPresent(String)}
     * @param relatedActionName Related action name
     * @return boolean
     */
    @Deprecated
    public boolean verifyRecordItemRelatedActionIsPresent(String relatedActionName) {
        return verifyRecordRelatedActionIsPresent(relatedActionName);
    }
    
    /** 
     * Verifies if record related action is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record related action | RELATED_ACTION_NAME | is present |</code>
     * 
     * @param relatedActionName Name of the related action
     * @return True, if related action is located
     */
    public boolean verifyRecordRelatedActionIsPresent(String relatedActionName) {
        return returnHandler(TempoRecord.refreshAndWaitForRelatedAction(relatedActionName, settings));
    }
   
    /** 
     * @deprecated Replaced by {@link #verifyRecordRelatedActionIsNotPresent(String)}
     * @param relatedActionName Related action name
     * @return boolean
     */
    @Deprecated
    public boolean verifyRecordItemRelatedActionIsNotPresent(String relatedActionName) {
        return verifyRecordRelatedActionIsNotPresent(relatedActionName);
    }
    
    /** 
     * Verifies if record related action is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record related action | RELATED_ACTION_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify record related action | RELATED_ACTION_NAME | is present |</code> as it will not refresh and wait.
     * 
     * @param relatedActionName Name of related action
     * @return True, if related action is not located
     */
    public boolean verifyRecordRelatedActionIsNotPresent(String relatedActionName) {
        return returnHandler(!TempoRecord.waitForRelatedAction(relatedActionName, settings.getNotPresentTimeoutSeconds(), settings));
    }

    /** 
     * Sorts Record Grid view by a specific column <br>
     * <br>
     * FitNesse Example: <code>| sort record grid by column | COLUMN_NAME |</code><br>
     * 
     * @param columnName Name of column
     * @return True, if the column is found and clicked
     */
    
    public boolean sortRecordGridByColumn(String columnName){
        if(!TempoRecordGrid.waitForRecordGridColumn(columnName, settings)) {
            exceptionHandler(ERROR_MISSING, "Record Grid Column", columnName);
        }
        return returnHandler(TempoRecordGrid.clickOnRecordGridColumn(columnName, settings));
    }
    
    /** 
     * Clicks on record grid navigation option<br>
     * <br>
     * FitNesse Example: <code>| click on record grid view navigation | NAVIGATION_OPTION |</code> Navigation option can only be "First", "Previous", "Next", or "Last"<br>
     * @param navOption Navigation option
     * @return True, if clicked
     */
    public boolean clickOnRecordGridNaviation(String navOption){
        if(!TempoRecordGrid.waitForRecordGridNavigation(navOption, settings)){
            exceptionHandler(ERROR_MISSING, "Navigation option", navOption);
        }
        return returnHandler(TempoRecordGrid.clickOnRecordGridNavigation(navOption, settings));
    }
    
    /*
     * Reports
     */
    
    /**
     * Clicks on the associated report.<br>
     * <br>
     * FitNesse Example: <code>| click on report | REPORT_NAME |</code>
     * 
     * @param reportName Name of report to click (partial names are acceptable)
     * If multiple reports contain the same name the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnReport(String reportName) {
        if(!TempoReport.waitFor(reportName, settings)) {
            exceptionHandler(ERROR_MISSING, "Report", reportName);
        }
        
        return returnHandler(TempoReport.click(reportName, settings));
    }
    
    /** 
     * Verifies if report is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify report | REPORT_NAME | is present |</code>
     * 
     * @param reportName Name of the report
     * @return True, if report is located
     */
    public boolean verifyReportIsPresent(String reportName) {
        return returnHandler(TempoReport.waitFor(reportName, settings));
    }
    
    /** 
     * Verifies if report is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify report | REPORT_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify report | REPORT_NAME | is present |</code> as it will not refresh and wait.
     * 
     * @param reportName Name of the report
     * @return True, if report is not located
     */
    public boolean verifyReportIsNotPresent(String reportName) {
        return returnHandler(!TempoReport.waitFor(reportName, settings.getNotPresentTimeoutSeconds(), settings));
    }
    
    /* 
     * Actions
     */
    
    /**
     * Clicks on the associated action.<br>
     * <br>
     * FitNesse Example: <code>| click on action | ACTION_NAME |</code>
     * 
     * @param actionName Name of action to click (partial names are acceptable)
     * If multiple actions contain the same name the first will be selected
     * @return True if action completed
     */
    public boolean clickOnAction(String actionName) {
        if(!TempoAction.waitFor(actionName, settings)) {
            exceptionHandler(ERROR_MISSING, "Action", actionName);
        }
        
        return returnHandler(TempoAction.click(actionName, settings));
    }
    
    /**
     * Returns true if the 'Action Completed successfully' is currently being displayed in the interface.<br>
     * This only applies to Appian 7.11 and below
     * <br>
     * FitNesse Example: <code>| verify action completed |</code>
     * @deprecated
     * @return True if the 'Action Completed successfully' is currently being displayed in the interface.
     */
    @Deprecated
    public boolean verifyActionCompleted() {
        return returnHandler(TempoAction.isCompleted(settings));
    }
    
    /** 
     * Verifies if action is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify action | ACTION_NAME | is present |</code>
     * 
     * @param actionName Name of the action
     * @return True, if action is located
     */
    public boolean verifyActionIsPresent(String actionName) {
        return returnHandler(TempoAction.waitFor(actionName, settings));
    }
    
    /** 
     * Verifies if action is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify action | ACTION_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify action | ACTION_NAME | is present |</code> as it will not refresh and wait.
     * 
     * @param actionName Name of the action
     * @return True, if action is not located
     */
    public boolean verifyActionIsNotPresent(String actionName) {
        return returnHandler(!TempoAction.waitFor(actionName, settings.getNotPresentTimeoutSeconds(), settings));
    }
    
    /*
     * Interfaces
     */
    
    /**
     * Returns the title of the form.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get form title |</code> - Simply returns a string<br>
     * <code>| set | titleVariable | get form title | </code> - Stores the title in titleVariable, which can later be accessed using @{titleVariable}<br>
     * <code>| check | get form title | FORM_TITLE |</code> - Returns true if form title matches FORM_TITLE input
     * @return The title string
     */
    public String getFormTitle() {
        waitForWorking();
        if(!TempoForm.waitForTitle(settings)) {
            exceptionHandler(ERROR_MISSING, "Title");
        }
        
        return TempoForm.getFormTitle(settings);
    }
    
    /**
     * Returns the instructions of the form.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get form instructions |</code> - Simply returns a string<br>
     * <code>| set | instructionsVariable | get form instructions | </code> - Stores the title in instructionsVariable, which can later be accessed using @{instructionsVariable}<br>
     * <code>| check | get form instructions | FORM_INSTRUCTIONS |</code> - Returns true if form instructions matches FORM_INSTRUCTIONS input
     * @return The instructions string
     */
    public String getFormInstructions() {
        waitForWorking();
        if(!TempoForm.waitForInstructions(settings)) {
            exceptionHandler(ERROR_MISSING, "Instructions");
        }
        
        return TempoForm.getFormInstructions(settings);
    }

    /**
     * Populates a field with specific values.<br>
     * <br>
     * This method can populate the following types of fields: Text, Paragraph, EncryptedText, Integer, Decimal, Date, Datetime, Select, MultipleSelect, Radio, Checkbox, FileUpload, UserPicker, GroupPicker, UserGroupPicker, DocumentPicker, FolderPicker, DocumentFolderPicker, CustomPicker. The only fields that support population of multiple items are Checkbox, MultipleSelect, and all Pickers.
     * The method will attempt to fill the field the number of attemptTimes (default 3) and if failing all three times will return false.<br>
     * When populating a username, use the display value rather than the username itself.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| populate field | FIELD_NAME | with | FIELD_VALUE |</code><br>
     * <code>| populate field | FIELD_NAME | with | VALUE_1,VALUE_2 |</code> - For fields that allow multiple inputs (), comma separate the field values.<br>
     * <code>| populate field | FIELD_NAME[2] | with | FIELD_VALUES |</code> - For fields with duplicate labels, an index of the field label on the page can be used in the format of [1], [2], or [5]. As with Appian, indexing starts with 1.<br>
     * <code>| populate field | FIELD_NAME | with | +4 hours |</code> - For date and datetime fields, relative times can be entered such as +1 minute, +2 hours, +3 days.  To use these relative times, the startDatetime must be initialized: see {@link com.appiancorp.ps.automatedtest.fixture.BaseFixture#setStartDatetime()}
     * @param fieldName Can either be the label of the field or a label with an index
     * @param fieldValues An array of strings containing the values to populate into the interface
     * @return True, if action completed successfully
     */
    public boolean populateFieldWith(String fieldName, String[] fieldValues) {
        if(!TempoField.waitFor(fieldName, settings)) {
            exceptionHandler(ERROR_MISSING, "Field", fieldName);
        }
        
        // Populate the field
        int attempt = 0;
        while (attempt < settings.getAttemptTimes()) {
            if (TempoField.populate(fieldName, fieldValues, settings)) return true;
            attempt++;
        }

        return returnHandler(false);
    }
    
    /**
     * Populates a field of a particular type with specific values.<br>
     * <br>
     * This method can populate the following types of fields: FileUpload.
     * The method will attempt to fill the field the number of attemptTimes (default 3) and if failing all three times will return false.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| populate | FILE_UPLOAD | field | FIELD_NAME[INDEX] | with | FIELD_VALUE |</code><br>
     * @param fieldType Can only currently accept FILE_UPLOAD
     * @param fieldName Can either be the label of the field or a label with an index
     * @param fieldValues An array of strings containing the values to populate into the interface
     * @return True, if action completed successfully
     */
    public boolean populateFieldWith(String fieldType, String fieldName, String[] fieldValues) {
        if(!TempoField.waitFor(fieldType, fieldName, settings)) {
            exceptionHandler(ERROR_MISSING, "Field", fieldName, "Field Type", fieldType);
        }
        
        // Populate the field
        int attempt = 0;
        while (attempt < settings.getAttemptTimes()) {
            if (TempoField.populate(fieldType, fieldName, fieldValues, settings)) return true;
            attempt++;
        }

        return returnHandler(false);
    }
    
    /**
     * Populates a field with a single value that may contain a comma. This is useful is selecting an option that contains a comma.<br><br>
     * When populating a username, use the display value rather than the username itself.<br>
     * <br>
     * FitNesse Example: <code>| populate field | FIELD_NAME | with value | VALUE |</code>
     * @param fieldName Field label or label and index
     * @param fieldValue Field value, can contain a comma.
     * @return True, if action completes successfully
     */
    public boolean populateFieldWithValue(String fieldName, String fieldValue) {
        return populateFieldWith(fieldName, new String[]{fieldValue});
    }
    
    /**
     * Populates a field in a section with specific values.<br>
     * <br>
     * This method is useful for populating fields without labels and can populate the following types of fields: Text, Paragraph, EncryptedText, Integer, Decimal, Date, Datetime, Select, MultipleSelect, Radio, Checkbox, FileUpload, UserPicker, GroupPicker, UserGroupPicker, DocumentPicker, FolderPicker, DocumentFolderPicker, CustomPicker.<br>
     * The method will attempt to fill the field the number of attemptTimes (default 3) and if failing all three times will return false.<br>
     * When populating a username, use the display value rather than the username itself.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| populate field | FIELD_NAME | in section | SECTION_NAME | with | FIELD_VALUE |</code><br>
     * <code>| populate field | FIELD_NAME | in section | SECTION_NAME | with | VALUE_1,VALUE_2 |</code> - For fields that allow multiple inputs (), comma separate the field values.<br>
     * <code>| populate field | FIELD_NAME | in section | SECTION_NAME[1] | with | FIELD_VALUES |</code> - For fields in sections with duplicate labels, an index of the section label on the page can be used in the format of [1], [2], or [5]. As with Appian, indexing starts with 1.<br>
     * <code>| populate field | FIELD_NAME | in section | SECTION_NAME | with | +4 hours |</code> - For date and datetime fields, relative times can be entered such as +1 minute, +2 hours, +3 days.  To use these relative times, the startDatetime must be initialized: see {@link com.appiancorp.ps.automatedtest.fixture.BaseFixture#setStartDatetime()}
     * @param fieldName Can either be the label of the field or label with an index
     * @param sectionName Can either be the label of the section or an label with an index
     * @param fieldValues An array of strings containing the values to populate into the interface
     * @return True, if action completed successfully
     */
    public boolean populateFieldInSectionWith(String fieldName, String sectionName, String[] fieldValues) {
        if(!TempoSection.waitFor(fieldName, sectionName, settings)) {
            exceptionHandler(ERROR_MISSING, "Field", sectionName + fieldName);
        }
        
        int attempt = 0;
        while (attempt < settings.getAttemptTimes()) {
            if (TempoSection.populate(fieldName, sectionName, fieldValues, settings)) return true;
            attempt++;
        }

        return returnHandler(false);
    }
    
    /**
     * Expand a section that is hidden.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| expand section | SECTION_NAME |</code><br>
     * @param sectionName Label of the section 
     * @return True, if section is expanded
     */    
    public boolean expandSection(String sectionName){
        if(!TempoSection.waitFor(sectionName, settings)){
            exceptionHandler(ERROR_MISSING, "Section", sectionName);
        }
       return returnHandler(TempoSection.clickExpandSection(sectionName, settings));
    }
    
    /**
     * Collapse a section that is visible.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| collapse section | SECTION_NAME |</code><br>
     * @param sectionName Label of the section 
     * @return True, if section is collapsed
     */    
    public boolean collapseSection(String sectionName){
        if(!TempoSection.waitFor(sectionName, settings)){
            exceptionHandler(ERROR_MISSING, "Section", sectionName);
        }
       return returnHandler(TempoSection.clickCollapseSection(sectionName, settings));
      }
    
    /**
     * Used to clear a field.<br>
     * <br>
     * This method currently works for
     * <br>
     * FitNesse Example: <code>| clear field | FIELD_NAME | of | VALUES |</code><br>
     * @param fieldName Field to clear
     * @param fieldValues Values to unselect
     * @return True, if action completed successfully
     */
    public boolean clearField(String fieldName) {
        if(!TempoField.waitFor(fieldName, settings)) {
            exceptionHandler(ERROR_MISSING, "Field", fieldName);
        }
        
        return returnHandler(TempoField.clear(fieldName, settings));
    }
    
    /**
     * Used to clear a field of specific values.<br>
     * <br>
     * This method is only useful for picker objects to unselect an item.
     * <br>
     * FitNesse Example: <code>| clear field | FIELD_NAME | of | VALUES |</code><br>
     * @param fieldName Field to clear
     * @param fieldValues Values to unselect
     * @return True, if action completed successfully
     */
    public boolean clearFieldOf(String fieldName, String[] fieldValues) {
        if(!TempoField.waitFor(fieldName, settings)) {
            exceptionHandler(ERROR_MISSING, "Field", fieldName);
        }
        
        return returnHandler(TempoField.clearOf(fieldName, fieldValues, settings));
    }
    
    /**
     * Returns the value of a field.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get field | FIELD_NAME | value |</code> - Simply returns a string<br>
     * <code>| set | fieldValue | get field | FIELD_NAME | value | </code> - Stores the field value in fieldValue, which can later be accessed using @{fieldValue}<br>
     * <code>| check | get field | FIELD_NAME | FIELD_VALUE |</code> - Returns true if the field value title matches the FIELD_VALUE input. For file upload fields, do not include the full path. This will not work for relative date and datetime fields.
     * @param fieldName Name of name and index of the field
     * @return The field value
     */
    public String getFieldValue(String fieldName) {
        if(!TempoField.waitFor(fieldName, settings)) {
            exceptionHandler(ERROR_MISSING, "Field", fieldName);
        }
        
        return TempoField.getValue(fieldName, settings);
    }
    
    /**
     * Returns the value of a field in a section.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get field | FIELD_NAME | in section | SECTION_ NAME | value |</code> - Simply returns a string<br>
     * <code>| set | fieldValue | get field | FIELD_NAME | in section | SECTION_NAME | value |</code> - Stores the field value in fieldValue, which can later be accessed using @{fieldValue}<br>
     * <code>| check | get field | FIELD_NAME | in section | SECTION_NAME | value | FIELD_VALUE |</code> - Returns true if the field value title matches the FIELD_VALUE input. For file upload fields, do not include the full path. This will not work for relative date and datetime fields.
     * @param fieldName Field label or label and index
     * @param sectionName Section label or label and index
     * @return The field value
     */
    public String getFieldInSectionValue(String fieldName, String sectionName) {
        if(!TempoSection.waitFor(fieldName, sectionName, settings)) {
            exceptionHandler(ERROR_MISSING, "Field", fieldName);
        }
        
        return TempoSection.getValue(fieldName, sectionName, settings);
    }
    
    /** 
     * Verifies a field contains a specific value.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| verify field | FIELD_NAME | contains | VALUES |</code>
     * <code>| verify field | FIELD_NAME | contains | +4 hours |</code> - For date and datetime fields, relative times can be entered such as +1 minute, +2 hours, +3 days.  To use these relative times, the startDatetime must be initialized: see {@link com.appiancorp.ps.automatedtest.fixture.BaseFixture#setStartDatetime()}
     * @param fieldName Can either be a name or a name and index, e.g. 'Text Field' or 'Text Field[1]'
     * @param fieldValues Values to compare field value against
     * @return True, if the field contains the value
     */
    public boolean verifyFieldContains(String fieldName, String[] fieldValues) {
        if(!TempoField.waitFor(fieldName, settings)) {
            exceptionHandler(ERROR_MISSING, "Field", fieldName);
        }
        
        return returnHandler(TempoField.contains(fieldName, fieldValues, settings));
    }
    
    /**
     * Verifies a field with a single value that may contain a comma. This is useful in verifying that an option that contains a comma has been selected.<br>
     * <br>
     * FitNesse Example: <code>| verify field | FIELD_NAME | contains value | VALUE |</code>
     * @param fieldName Field label or label and index
     * @param fieldValue Field value, can contain a comma.
     * @return True, if fields contains value
     */
    public boolean verifyFieldContainsValue(String fieldName, String fieldValue) {
        return verifyFieldContains(fieldName, new String[]{fieldValue});
    }
    
    /** 
     * Verifies a field contains a specific value.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| verify field | FIELD_NAME | in section | SECTION_NAME | contains | VALUES |</code><br>
     * <code>| verify field | FIELD_NAME | in section | SECTION_NAME | contains | +4 hours |</code> - For date and datetime fields, relative times can be entered such as +1 minute, +2 hours, +3 days.  To use these relative times, the startDatetime must be initialized: see {@link com.appiancorp.ps.automatedtest.fixture.BaseFixture#setStartDatetime()}
     * @param fieldName Can either be a field label or a label and index, e.g. 'Text Field' or 'Text Field[2]'
     * @param sectionName Can either be a section label or a label and index, e.g. 'Section Name' or 'Section Name[2]'
     * @param fieldValues Values to compare field value against
     * @return True, if the field contains the value
     */
    public boolean verifyFieldInSectionContains(String fieldName, String sectionName, String[] fieldValues) {
        if(!TempoSection.waitFor(fieldName, sectionName, settings)) {
            exceptionHandler(ERROR_MISSING, "Field", fieldName);
        }
        
        return returnHandler(TempoSection.contains(fieldName, sectionName, fieldValues, settings));
    }
    
    /**
     * Verifies if field is displayed in the interface. This is useful to test dynamic forms.<br>
     * <br>
     * FitNesse Example: <code>| verify field | FIELD_NAME | is present |</code>
     * @param fieldName Field name to find in interface
     * @return True, if field is located in the interface
     */
    public boolean verifyFieldIsPresent(String fieldName) {
        return returnHandler(TempoField.waitFor(fieldName, settings));
    }
    
    /**
     * Verifies if field is not displayed in the interface. This is useful to test dynamic forms.<br>
     * <br>
     * FitNesse Example: <code>| verify field | FIELD_NAME | is not present |</code>
     * @param fieldName Field name to not find in interface
     * @return True, if field is not located in the interface
     */
    public boolean verifyFieldIsNotPresent(String fieldName) {
        return returnHandler(!TempoField.waitFor(fieldName, settings.getNotPresentTimeoutSeconds(), settings));
    }
    
    /**
     * Populates fields in a grid. This method is useful for populating the following types of fields: Text, Paragraph, EncryptedText, Integer, Decimal, Date, Datetime, Select, MultipleSelect, Radio, Checkbox, FileUpload, UserPicker, GroupPicker, UserGroupPicker, DocumentPicker, FolderPicker, DocumentFolderPicker, CustomPicker.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| populate grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | with | VALUE |</code><br>
     * <code>| populate grid | [1] | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | with | VALUE |</code> - If the grid does not have a title, an index can be used to select the grid, e.g. [1] would be the first grid in the interface.<br>
     * <code>| populate grid | GRID_NAME | column | [2] | row | ROW_INDEX | with | VALUE |</code> - The column index can be used if the columns do not have titles. As with Appian indexing starts with [1] and if the table has checkboxes, they are considered to be in the first column.<br>
     * <code>| populate grid | GRID_NAME[3] | column | COLUMN_NAME | row | ROW_INDEX | with | VALUE |</code> - If there are multiple grids with the same name then use an index, e.g. 'Grid Name[3]' with select the third grid with the name of 'Grid Name' in the interface
     * @param gridName Name of grid
     * @param columnName Name or index of the column
     * @param rowNum Index of the row
     * @param fieldValues Values to populate
     * @return True, if action completed successfully
     */
    public boolean populateGridColumnRowWith(String gridName, String columnName, String rowNum, String[] fieldValues) {
        if(!TempoGrid.waitFor(gridName, columnName, rowNum, settings)) {
            exceptionHandler(ERROR_MISSING, "Grid/Column/Row", gridName+"/"+columnName+"/"+rowNum);
        }
        
        int attempt = 0;
        while (attempt < settings.getAttemptTimes()) {
            if (TempoGrid.populate(gridName, columnName, rowNum, fieldValues, settings)) return true;
            attempt++;
        }
        
        return returnHandler(false);
    }
   
    /**
     * Returns the value of a field.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | value |</code> - Simply returns a string<br>
     * <code>| set | fieldValue | get grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | value | </code> - Stores the field value in fieldValue, which can later be accessed using @{fieldValue}<br>
     * <code>| check | get grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | value | FIELD_VALUE |</code> - Returns true if the field value title matches the FIELD_VALUE input. For file upload fields, do not include the full path. This will not work for relative date and datetime fields.
     * @param gridName Name of the grid
     * @param columnName Name or index of the column
     * @param rowNum Index of the row
     * @return The field value
     */
    public String getGridColumnRowValue(String gridName, String columnName, String rowNum) {
        if(!TempoGrid.waitFor(gridName, columnName, rowNum, settings)) {
            exceptionHandler(ERROR_MISSING, "Grid/Column/Row", gridName+"/"+columnName+"/"+rowNum);
        }
        
        return TempoGrid.getValue(gridName, columnName, rowNum, settings);
    }
    
    
    /** 
     * Verifies a field contains a specific value.<br>
     * <br>
     * FitNesse Example: <code>| verify grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | contains | VALUES |</code>
     * @param gridName Name of the grid
     * @param columnName Name or index of the column
     * @param rowNum Index of the row
     * @param fieldValues Field values to compare against
     * @return True, if the field contains the value
     */
    public boolean verifyGridColumnRowContains(String gridName, String columnName, String rowNum, String[] fieldValues) {
        if(!TempoGrid.waitFor(gridName, columnName, rowNum, settings)) {
            exceptionHandler(ERROR_MISSING, "Grid/Column/Row", gridName+"/"+columnName+"/"+rowNum);
        }
        
        return returnHandler(TempoGrid.contains(gridName, columnName, rowNum, fieldValues, settings)); 
    }
    
    /**
     * Selects a row in an editable or paging grid.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| select grid | GRID_NAME_OR_INDEX | row | [1] |</code><br>
     * <code>| select grid | GRID_NAME[2] | row | [2] |</code> - If there are multiple grids with the same name, use the index to select the correct one. As with Appian, indexing starts with [1].
     * @param gridName Can either be the grid name or grid name with index, e.g. 'Grid Name' or 'Grid Name[2]'
     * @param rowNum Index of row to select, e.g. [2]
     * @return True, if action completed successfully
     */
    public boolean selectGridRow(String gridName, String rowNum) {
        if(!TempoGrid.waitFor(gridName, rowNum, settings)) {
            exceptionHandler(ERROR_MISSING, "Grid/Row", gridName+"/"+rowNum);
        }
        
        return returnHandler(TempoGrid.selectRow(gridName, rowNum, settings)); 
    }
    
    /**
     * Verifies if a grid row is selected<br>
     * <br>
     * FitNesse Example: <code>| verify grid | GRID_NAME_OR_INDEX | row | ROW_NUMBER | is selected |</code>
     * @param gridName Name or name and index of grid
     * @param rowNum Row number
     * @return True, if row is selected
     */
    public boolean verifyGridRowIsSelected(String gridName, String rowNum) {
        if(!TempoGrid.waitFor(gridName, rowNum, settings)) {
            exceptionHandler(ERROR_MISSING, "Grid/Row", gridName+"/"+rowNum);
        }
        
        return returnHandler(TempoGrid.verifyGridRowIsSelected(gridName, rowNum, settings)); 
    }
    
    /**
     * Clicks on the add row link for a grid<br>
     * <br>
     * FitNesse Example: <code>| click on grid | GRID_NAME_OR_INDEX | add row link |</code>
     * @param gridName Name or name and index of grid
     * @return True, if link is clicked
     */

    public boolean clickOnGridAddRowLink(String gridName){
        if(!TempoGrid.waitFor(gridName, settings)) {
            exceptionHandler(ERROR_MISSING, "Grid", gridName);
        }
        
        return returnHandler(TempoGrid.clickOnAddRowLink(gridName, settings)); 
    }
   
    /**
     * Clicks on the page link below a paging grid<br>
     * <br>
     * FitNesse Example: <code>| click on grid | GRID_NAME_OR_INDEX | navigation | NAV_REFERENCE |</code> - nav reference only takes "first", previous, next, or "last"
     * @param gridName Name or name and index of grid
     * @param navOption "first", previous, next, or "last"
     * @return True, if grid page link is clicked
     */

    public boolean clickOnGridNavigation(String gridName, String navOption){
        if(!TempoGrid.waitFor(gridName, settings)){
            exceptionHandler(ERROR_MISSING, "Grid", gridName);
        }
        navOption = navOption.toLowerCase();
        
        return TempoGrid.clickOnNavigationOption(gridName, navOption, settings);
      
    }
    /**
     * Sort a grid by a column<br>
     * <br>
     * FitNesse Example: <code>| sort grid | GRID_NAME_OR_INDEX | by column | COLUMN_NAME_OR_INDEX |</code> - 
     * @param gridName Name or name and index of grid
     * @param columnName Name or index of column
     * @return True, if column name is clicked
     */    
    public boolean sortGridByColumn(String gridName, String columnName){
        if(!TempoGrid.waitFor(gridName, settings)){
            exceptionHandler(ERROR_MISSING, "Grid", gridName);
        }        
        
        return returnHandler(TempoGrid.sortByColumn(gridName, columnName, settings));
    }
    
    /**
     * Clicks on the first link that matches the linkName.<br>
     * <br>
     * FitNesse Example: <code>| click on link | LINK_NAME |</code>
     * @param linkName Name of link to click
     * @return True, if action completed successfully
     */
    public boolean clickOnLink(String linkName) {
        if(!TempoLinkField.waitFor(linkName, settings)) {
            exceptionHandler(ERROR_MISSING, "Link", linkName);
        }
        
        return returnHandler(TempoLinkField.click(linkName, settings)); 
    }

    /**
     * Clicks on the first button that matches the buttonName.<br>
     * <br>
     * FitNesse Example: <code>| click on button | BUTTON_NAME |</code>
     * @param buttonName Name of button to click
     * @return True, if action completed successfully
     */
    public boolean clickOnButton(String buttonName) {
        if (!TempoButton.waitFor(buttonName, settings)) {
            exceptionHandler(ERROR_MISSING, "Button", buttonName);
        }
        
        return returnHandler(TempoButton.click(buttonName, settings));
    }
    
    /**
     * Clicks on the first radio option that matches the optionName. This is useful if the radio field does not have a label.<br>
     * <br>
     * FitNesse Example: <code>| click on radio option | OPTION_NAME |</code>
     * @param optionName Name of radio option to click
     * @return True, if action completed successfully
     */
    public boolean clickOnRadioOption(String optionName) {
        if (!TempoRadioField.waitForOption(optionName, settings)) {
            exceptionHandler(ERROR_MISSING, "Radio Button Option", optionName);
        }
        
        return returnHandler(TempoRadioField.clickOption(optionName, settings));
    }
    
    /**
     * Clicks on the first checkbox option that matches the optionName. This is useful if the checkbox field does not have a label.<br>
     * <br>
     * FitNesse Example: <code>| click on checkbox option | OPTION_NAME |</code>
     * @param optionName Name of checkbox option to click
     * @return True, if action completed successfully
     */
    public boolean clickOnCheckboxOption(String optionName) {
        if (!TempoCheckboxField.waitForOption(optionName, settings)) {
            exceptionHandler(ERROR_MISSING, "Checkbox Option", optionName);
        }
        
        return returnHandler(TempoCheckboxField.clickOption(optionName, settings));
    }
    
    /**
     * Verifies if a section contains a specific error. Useful for testing section validation.<br>
     * <br>
     * FitNesse Example: <code>| verify section | SECTION_NAME | containing error | ERROR | is present |</code>
     * @param sectionName Name of section to look for error
     * @param error Error to search for in section
     * @return True, if error is located
     */
    public boolean verifySectionContainingErrorIsPresent(String sectionName, String error) {
        return returnHandler(TempoSection.waitForError(sectionName, error, settings));
    }
}