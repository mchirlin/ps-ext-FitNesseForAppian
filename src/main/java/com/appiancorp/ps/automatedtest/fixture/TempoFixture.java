package com.appiancorp.ps.automatedtest.fixture;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import com.appiancorp.ps.automatedtest.exception.MissingObjectException;
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
import com.appiancorp.ps.automatedtest.object.TempoRecordItem;
import com.appiancorp.ps.automatedtest.object.TempoRecordList;
import com.appiancorp.ps.automatedtest.object.TempoReport;
import com.appiancorp.ps.automatedtest.object.TempoSection;
import com.appiancorp.ps.automatedtest.object.TempoTask;

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
		if(!TempoMenu.waitFor(tempoMenu)) {
		    throw new MissingObjectException("Tempo Menu", tempoMenu);
		}
		
		return returnHandler(TempoMenu.click(tempoMenu));
	}
	
	/**
    * Logs out of Appian
    * <br>
    * Example: <code>| logout |</code>
    * 
    * @return True if action completed
    */
	public boolean logout() {
        if(!TempoLogin.waitForLogout()) {
            throw new MissingObjectException("Logout Menu");
        }
        
        return returnHandler(TempoLogin.logout());
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
        if(!TempoTask.refreshAndWaitFor(taskName)) {
            throw new MissingObjectException("Task", taskName);
        }
        
        return returnHandler(TempoTask.click(taskName));
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
        return returnHandler(TempoTask.refreshAndWaitFor(taskName));
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
        return returnHandler(!TempoTask.waitFor(taskName));
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
        if(!TempoTask.waitFor(taskName)) {
            throw new MissingObjectException("Task", taskName);
        }
        
        return returnHandler(TempoTask.hasDeadlineOf(taskName, deadline));
    }
    
    /*
     * Records
     */
    
    /**
     * Clicks on the record item list.<br>
     * <br>
     * FitNesse Example: <code>| click on record list | RECORD_LIST |</code>
     * 
     * @param listName Name of record list to click (partial names are acceptable)
     * If multiple record lists contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordList(String listName) {
        if(!TempoRecordList.waitFor(listName)) {
            throw new MissingObjectException("Record List", listName);
        }
        
        return returnHandler(TempoRecordList.click(listName));
    }
    
    /**
     * Clicks on the record list facet option.<br>
     * <br>
     * FitNesse Example: <code>| click on record list facet option | FACET_OPTION |</code>
     * 
     * @param facetOption Facet option to click (partial names are acceptable)
     * If multiple facet options contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordListFacetOption(String facetOption) {
        if(!TempoRecordList.waitForFacetOption(facetOption)) {
            throw new MissingObjectException("Record List Facet", facetOption);
        }
        
        return returnHandler(TempoRecordList.clickOnFacetOption(facetOption));       
    }
    
    /** 
     * Verifies if facet option is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record list facet option | FACET_OPTION | is present |</code>
     * 
     * @param facetOption Name of facet option
     * @return True, if facet option is located
     */
    public boolean verifyRecordListFacetOptionIsPresent(String facetOption) {
        return returnHandler(TempoRecordList.waitForFacetOption(facetOption));
    }
    
    /**
     * Clicks on the associated record item.<br>
     * <br>
     * FitNesse Example: <code>| click on record item | REPORT_ITEM_NAME |</code>
     * 
     * @param itemName Name of record item to click (partial names are acceptable)
     * If multiple record items contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordItem(String itemName) {
        if(!TempoRecordItem.refreshAndWaitFor(itemName)) {
            throw new MissingObjectException("Tempo Record Item", itemName);
        }
        
        return returnHandler(TempoRecordItem.click(itemName));
    }
    
    /** 
     * Verifies if record item is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record item | RECORD_ITEM_NAME | is present |</code>
     * 
     * @param itemName Name of record item
     * @return True, if record item is located
     */
    public boolean verifyRecordItemIsPresent(String itemName) {
        return returnHandler(TempoRecordItem.waitFor(itemName));
    }
    
    /** 
     * Verifies if record item is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record item | RECORD_ITEM_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify record item | RECORD_ITEM_NAME | is present |</code> as it will not refresh and wait.
     * 
     * @param itemName Name of record item
     * @return True, if record item is not located
     */
    public boolean verifyRecordItemIsNotPresent(String itemName) {
        return returnHandler(!TempoRecordItem.waitFor(itemName));
    }
     
    /**
     * Clicks on the associated record item facet.<br>
     * <br>
     * FitNesse Example: <code>| click on record item facet | FACET_NAME |</code>
     * 
     * @param facetName Name of facet to click (partial names are acceptable)
     * If multiple facet items contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordItemFacet(String facetName) {
        if(!TempoRecordItem.waitForFacet(facetName)) {
            throw new MissingObjectException("Record Item Facet", facetName);
        }
        
        return returnHandler(TempoRecordItem.clickOnFacet(facetName));
    }
     
    /**
     * Clicks on the associated related action.<br>
     * <br>
     * FitNesse Example: <code>| click on record item related action | RELATED_ACTION_NAME |</code>
     * 
     * @param relatedActionName Name of related action to click (partial names are acceptable)
     * If multiple related actions contain the same name, then the first will be selected
     * @return True, if completed successfully
     */
    public boolean clickOnRecordItemRelatedAction(String relatedActionName) {
        if(!TempoRecordItem.refreshAndWaitForRelatedAction(relatedActionName)) {
            throw new MissingObjectException("Related Action", relatedActionName);
        }
        
        return returnHandler(TempoRecordItem.clickOnRelatedAction(relatedActionName));
    }
     
    /** 
     * Verifies if record item related action is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record item related action | RELATED_ACTION_NAME | is present |</code>
     * 
     * @param relatedActionName Name of the related action
     * @return True, if related action is located
     */
    public boolean verifyRecordItemRelatedActionIsPresent(String relatedActionName) {
        return returnHandler(TempoRecordItem.refreshAndWaitForRelatedAction(relatedActionName));
    }
     
    /** 
     * Verifies if record item related action is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record item related action | RELATED_ACTION_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify record item related action | RELATED_ACTION_NAME | is present |</code> as it will not refresh and wait.
     * 
     * @param relatedActionName Name of related action
     * @return True, if related action is not located
     */
    public boolean verifyRecordItemRelatedActionIsNotPresent(String relatedActionName) {
        return returnHandler(!TempoRecordItem.waitForRelatedAction(relatedActionName));
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
        if(!TempoReport.waitFor(reportName)) {
            throw new MissingObjectException("Report", reportName);
        }
        
        return returnHandler(TempoReport.click(reportName));
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
        return returnHandler(TempoReport.waitFor(reportName));
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
        return returnHandler(!TempoReport.waitFor(reportName));
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
        if(!TempoAction.waitFor(actionName)) {
            throw new MissingObjectException("Action", actionName);
        }
        
        return returnHandler(TempoAction.click(actionName));
    }
    
    /**
     * Returns true if the 'Action Completed successfully' is currently being displayed in the interface.<br>
     * <br>
     * FitNesse Example: <code>| verify action completed |</code>
     * 
     * @return True if the 'Action Completed successfully' is currently being displayed in the interface.
     */
    public boolean verifyActionCompleted() {
        return returnHandler(TempoAction.isCompleted());
    }
    
    /*
     * Interfaces
     */
    
    /**
     * Returns the title of the form.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get title |</code> - Simply returns a string<br>
     * <code>| set | get title | titleVariable |</code> - Stores the title in titleVariable, which can later be accessed using @{titleVariable}<br>
     * <code>| check | get title | FORM_TITLE |</code> - Returns true if form title matches FORM_TITLE input
     * @return The title string
     */
    public String getTitle() {
        if(!TempoForm.waitForTitle()) {
            throw new MissingObjectException("Title");
        }
        
        return TempoForm.getTitle();
    }
    
    /**
     * Returns the instructions of the form.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get instructions |</code> - Simply returns a string<br>
     * <code>| set | get instructions | instructionsVariable |</code> - Stores the title in instructionsVariable, which can later be accessed using @{instructionsVariable}<br>
     * <code>| check | get instructions | FORM_INSTRUCTIONS |</code> - Returns true if form instructions matches FORM_INSTRUCTIONS input
     * @return The instructions string
     */
    public String getInstructions() {
        if(!TempoForm.waitForInstructions()) {
            throw new MissingObjectException("Instructions");
        }
        
        return TempoForm.getInstructions();
    }

    /**
     * Populates a field with specific values.<br>
     * <br>
     * This method can populate the following types of fields: Text, Paragraph, EncryptedText, Integer, Decimal, Date, Datetime, Select, MultipleSelect, Radio, Checkbox, FileUpload, UserPicker, GroupPicker, UserGroupPicker, DocumentPicker, FolderPicker, DocumentFolderPicker, CustomPicker. The only fields that support population of multiple items are Checkbox, MultipleSelect, and all Pickers.
     * The method will attempt to fill the field the number of attemptTimes (default 3) and if failing all three times will return false.<br> 
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
        if(!TempoField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        // Populate the field
        int attempt = 0;
        while (attempt < attemptTimes) {
            if (TempoField.populate(fieldName, fieldValues)) {
                new Actions(getDriver()).sendKeys(Keys.TAB).perform();
                return true;
            }
            attempt++;
        }

        return returnHandler(false);
    }
    
    /**
     * Populates a field with a single value that may contain a comma. This is useful is selecting an option that contains a comma.<br>
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
        if(!TempoSection.waitFor(fieldName, sectionName)) {
            throw new MissingObjectException("Field", sectionName + fieldName);
        }
        
        int attempt = 0;
        while (attempt < attemptTimes) {
            if (TempoSection.populate(fieldName, sectionName, fieldValues)) {
                new Actions(getDriver()).sendKeys(Keys.TAB).perform();
                return true;
            }
            attempt++;
        }

        return returnHandler(false);
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
        if(!TempoField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return returnHandler(TempoField.clearOf(fieldName, fieldValues));
    }
    
    /**
     * Returns the value of a field.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get field | FIELD_NAME | value |</code> - Simply returns a string<br>
     * <code>| set | get field | FIELD_NAME | value | fieldValue |</code> - Stores the field value in fieldValue, which can later be accessed using @{fieldValue}
     * <code>| check | get field | FIELD_NAME | FIELD_VALUE |</code> - Returns true if the field value title matches the FIELD_VALUE input. For file upload fields, do not include the full path. This will not work for relative date and datetime fields.
     * @param fieldName Name of name and index of the field
     * @return The field value
     */
    public String getFieldValue(String fieldName) {
        if(!TempoField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoField.getValue(fieldName);
    }
    
    /**
     * Returns the value of a field in a section.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get field | FIELD_NAME | in section | SECTION_ NAME | value |</code> - Simply returns a string<br>
     * <code>| set | get field | FIELD_NAME | in section | SECTION_NAME | value | fieldValue |</code> - Stores the field value in fieldValue, which can later be accessed using @{fieldValue}
     * <code>| check | get field | FIELD_NAME | in section | SECTION_NAME | value | FIELD_VALUE |</code> - Returns true if the field value title matches the FIELD_VALUE input. For file upload fields, do not include the full path. This will not work for relative date and datetime fields.
     * @param fieldName Field label or label and index
     * @param sectionName Section label or label and index
     * @return The field value
     */
    public String getFieldInSectionValue(String fieldName, String sectionName) {
        if(!TempoSection.waitFor(fieldName, sectionName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoSection.getValue(fieldName, sectionName);
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
        if(!TempoField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return returnHandler(TempoField.contains(fieldName, fieldValues));
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
     * <code>| verify field | FIELD_NAME | in section | SECTION_NAME | contains | VALUES |</code>
     * <code>| verify field | FIELD_NAME | in section | SECTION_NAME | contains | +4 hours |</code> - For date and datetime fields, relative times can be entered such as +1 minute, +2 hours, +3 days.  To use these relative times, the startDatetime must be initialized: see {@link com.appiancorp.ps.automatedtest.fixture.BaseFixture#setStartDatetime()}
     * @param fieldName Can either be a field label or a label and index, e.g. 'Text Field' or 'Text Field[2]'
     * @param sectionName Can either be a section label or a label and index, e.g. 'Section Name' or 'Section Name[2]'
     * @param fieldValues Values to compare field value against
     * @return True, if the field contains the value
     */
    public boolean verifyFieldInSectionContains(String fieldName, String sectionName, String[] fieldValues) {
        if(!TempoSection.waitFor(fieldName, sectionName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return returnHandler(TempoSection.contains(fieldName, sectionName, fieldValues));
    }
    
    /**
     * Verifies if field is displayed in the interface. This is useful to test dynamic forms.<br>
     * <br>
     * FitNesse Example: <code>| verify field | FIELD_NAME | is present |</code>
     * @param fieldName Field name to find in interface
     * @return True, if field is located in the interface
     */
    public boolean verifyFieldIsPresent(String fieldName) {
        return returnHandler(TempoField.waitFor(fieldName));
    }
    
    /**
     * Verifies if field is not displayed in the interface. This is useful to test dynamic forms.<br>
     * <br>
     * FitNesse Example: <code>| verify field | FIELD_NAME | is not present |</code>
     * @param fieldName Field name to not find in interface
     * @return True, if field is not located in the interface
     */
    public boolean verifyFieldIsNotPresent(String fieldName) {
        return returnHandler(!TempoField.waitFor(fieldName));
    }
    
    /**
     * Populates fields in a grid. This method is useful for populating the following types of fields: Text, Paragraph, EncryptedText, Integer, Decimal, Date, Datetime, Select, MultipleSelect, Radio, Checkbox, FileUpload, UserPicker, GroupPicker, UserGroupPicker, DocumentPicker, FolderPicker, DocumentFolderPicker, CustomPicker.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| populate grid | GRID_NAME | column | COLUMN_NAME | row | ROW_INDEX | with | VALUE |</code><br>
     * <code>| populate grid | GRID_NAME | column | COLUMN_INDEX | row | ROW_INDEX | with | VALUE |</code> - The column index can be used if the columns do not have titles. As with Appian indexing starts with [1] and if the table has checkboxes, they are considered to be in the first column.<br>
     * <code>| populate grid | GRID_NAME[3] | column | COLUMN_NAME | row | ROW_INDEX | with | VALUE |</code> - If there are multiple grids with the same name then use an index, e.g. 'Grid Name[3]' with select the third grid with the name of 'Grid Name' in the interface
     * @param gridName Name of grid
     * @param columnName Name or index of the column
     * @param rowNum Index of the row
     * @param fieldValues Values to populate
     * @return True, if action completed successfully
     */
    public boolean populateGridColumnRowWith(String gridName, String columnName, String rowNum, String[] fieldValues) {
        if(!TempoGrid.waitFor(gridName, columnName, rowNum)) {
            throw new MissingObjectException("Grid", gridName);
        }
        
        int attempt = 0;
        while (attempt < attemptTimes) {
            if (TempoGrid.populate(gridName, columnName, rowNum, fieldValues)) {
                new Actions(getDriver()).sendKeys(Keys.TAB).perform();
                return true;
            }
            attempt++;
        }
        
        return returnHandler(false);
    }
   
    /**
     * Returns the value of a field.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get grid | GRID_NAME | column | COLUMN_NAME | row | ROW_INDEX| value |</code> - Simply returns a string<br>
     * <code>| set | get grid | GRID_NAME | column | COLUMN_NAME | row | ROW_INDEX| value | fieldValue |</code> - Stores the field value in fieldValue, which can later be accessed using @{fieldValue}
     * <code>| check | get grid | GRID_NAME | column | COLUMN_NAME | row | ROW_INDEX| value | FIELD_VALUE |</code> - Returns true if the field value title matches the FIELD_VALUE input. For file upload fields, do not include the full path. This will not work for relative date and datetime fields.
     * @param gridName Name of the grid
     * @param columnName Name or index of the column
     * @param rowNum Index of the row
     * @return The field value
     */
    public String getGridColumnRowValue(String gridName, String columnName, String rowNum) {
        if(!TempoGrid.waitFor(gridName, columnName, rowNum)) {
            throw new MissingObjectException("Grid", gridName);
        }
        
        return TempoGrid.getValue(gridName, columnName, rowNum);
    }
    
    /** 
     * Verifies a field contains a specific value.<br>
     * <br>
     * FitNesse Example: <code>| verify grid | GRID_NAME | column | COLUMN_NAME | row | ROW_INDEX | contains | VALUES |</code>
     * @param gridName Name of the grid
     * @param columnName Name or index of the column
     * @param rowNum Index of the row
     * @param fieldValues Field values to compare against
     * @return True, if the field contains the value
     */
    public boolean verifyGridColumnRowContains(String gridName, String columnName, String rowNum, String[] fieldValues) {
        if(!TempoGrid.waitFor(gridName, columnName, rowNum)) {
            throw new MissingObjectException("Grid", gridName);
        }
        
        return returnHandler(TempoGrid.contains(gridName, columnName, rowNum, fieldValues)); 
    }
    
    /**
     * Selects a row in an editable or paging grid.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| select grid | GRID_NAME | row | [1] |</code><br>
     * <code>| select grid | GRID_NAME[2] | row | [2] |</code> - If there are multiple grids with the same name, use the index to select the correct one. As with Appian, indexing starts with [1].
     * @param gridName Can either be the grid name or grid name with index, e.g. 'Grid Name' or 'Grid Name[2]'
     * @param rowNum Index of row to select, e.g. [2]
     * @return True, if action completed successfully
     */
    public boolean selectGridRow(String gridName, String rowNum) {
        if(!TempoGrid.waitFor(gridName, rowNum)) {
            throw new MissingObjectException("Grid", gridName);
        }
        
        return returnHandler(TempoGrid.selectRow(gridName, rowNum)); 
    }
    
    /**
     * Clicks on the first link that matches the linkName.<br>
     * <br>
     * FitNesse Example: <code>| click on link | LINK_NAME |</code>
     * @param linkName Name of link to click
     * @return True, if action completed successfully
     */
    public boolean clickOnLink(String linkName) {
        if(!TempoLinkField.waitFor(linkName)) {
            throw new MissingObjectException("Link", linkName);
        }
        
        return returnHandler(TempoLinkField.click(linkName)); 
    }

    /**
     * Clicks on the first button that matches the buttonName.<br>
     * <br>
     * FitNesse Example: <code>| click on button | BUTTON_NAME |</code>
     * @param buttonName Name of button to click
     * @return True, if action completed successfully
     */
    public boolean clickOnButton(String buttonName) {
        if (!TempoButton.waitFor(buttonName)) {
            throw new MissingObjectException("Button", buttonName);
        }
        
        return returnHandler(TempoButton.click(buttonName));
    }
    
    /**
     * Clicks on the first radio option that matches the optionName. This is useful if the radio field does not have a label.<br>
     * <br>
     * FitNesse Example: <code>| click on radio option | OPTION_NAME |</code>
     * @param optionName Name of radio option to click
     * @return True, if action completed successfully
     */
    public boolean clickOnRadioOption(String optionName) {
        if (!TempoRadioField.waitForOption(optionName)) {
            throw new MissingObjectException("Radio Button Option", optionName);
        }
        
        return returnHandler(TempoRadioField.clickOption(optionName));
    }
    
    /**
     * Clicks on the first checkbox option that matches the optionName. This is useful if the checkbox field does not have a label.<br>
     * <br>
     * FitNesse Example: <code>| click on checkbox option | OPTION_NAME |</code>
     * @param optionName Name of checkbox option to click
     * @return True, if action completed successfully
     */
    public boolean clickOnCheckboxOption(String optionName) {
        if (!TempoCheckboxField.waitForOption(optionName)) {
            throw new MissingObjectException("Checkbox Option", optionName);
        }
        
        return returnHandler(TempoCheckboxField.clickOption(optionName));
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
        return returnHandler(TempoSection.waitForError(sectionName, error));
    }
}