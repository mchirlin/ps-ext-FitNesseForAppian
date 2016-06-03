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
import com.appiancorp.ps.automatedtest.object.TempoRecordGrid;
import com.appiancorp.ps.automatedtest.object.TempoRecordType;
import com.appiancorp.ps.automatedtest.object.TempoReport;
import com.appiancorp.ps.automatedtest.object.TempoSearch;
import com.appiancorp.ps.automatedtest.object.TempoSection;
import com.appiancorp.ps.automatedtest.object.TempoTask;

/**
 * This is the tempo class for integrating Appian and FitNesse.
 * This class contains fixture commands which are specific to the Tempo interface
 * 
 * @author michael.chirlin
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
   * @param tempoMenu
   *          Name of tempo menu, e.g. Records, Tasks, News
   */
  public void clickOnMenu(String tempoMenu) {
    TempoMenu.waitFor(tempoMenu, settings);
    TempoMenu.click(tempoMenu, settings);
  }

  /**
   * Populates search fields in News, Reports, and Records. <br>
   * <br>
   * Example: <code>| search for | SEARCH_TERM |</code>
   * 
   * @param searchTerm
   *          The term to search for News, Reports, and Records
   */
  public void searchFor(String searchTerm) {
    TempoSearch.waitFor(searchTerm, settings);
    TempoSearch.searchFor(searchTerm, settings);
  }

  /**
   * Logs out of Appian <br>
   * Example: <code>| logout |</code>
   */
  public void logout() {
    TempoLogin.waitForLogout(settings);
    TempoLogin.logout(settings);
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
   * @param newsText
   *          Text to search for in the news feed
   * @return True, if post is located with specific text
   */
  public boolean verifyNewsFeedContainingTextIsPresent(String newsText) {
    TempoNews.refreshAndWaitFor(newsText, settings);
    return true;
  }

  /**
   * Verifies there is not a news post containing specific text.<br>
   * <br>
   * FitNesse Example: <code>| verify news feed containing text | NEWS_TITLE | is present |</code><br>
   * <br>
   * The reason to use than <code>| reject | verify news feed containing text | NEWS_TEXT | is present |</code> is that this method will not
   * refresh and wait.
   * 
   * @param newsText
   *          Text to search for in the news feed
   * @return True, if no post is located with specific text
   */
  public boolean verifyNewsFeedContainingTextIsNotPresent(String newsText) {
    return !TempoNews.waitForReturn(newsText, settings.getNotPresentTimeoutSeconds(), settings);
  }

  /**
   * Toggles the more info on a news feed post containing specific text.<br>
   * <br>
   * FitNesse Example: <code>| toggle more info for news feed containing text | NEWS_TEXT |</code>
   * 
   * @param newsText
   *          Text to search for in the news feed
   */
  public void toggleMoreInfoForNewsFeedContainingText(String newsText) {
    TempoNews.waitForMoreInfo(newsText, settings);
    TempoNews.toggleMoreInfo(newsText, settings);
  }

  /**
   * Deletes a news post.<br>
   * <br>
   * FitNesse Example: <code>| delete news post | NEWS_TEXT |</code>
   * 
   * @param newsText
   *          Text to search for in the news feed
   */
  public void deleteNewsPost(String newsText) {
    TempoNews.waitFor(newsText, settings);
    TempoNews.deleteNewsPost(newsText, settings);
  }

  /**
   * Verifies there is a news post containing specific text with a specific label and value is present.<br>
   * <br>
   * FitNesse Example:
   * <code>| verify news feed containing text | NEWS_TEXT | and more info with label | LABEL | and value | VALUE | is present |</code>
   * 
   * @param newsText
   *          Text to search for in the news feed
   * @param label
   *          Label to search for in the more info
   * @param value
   *          Value to search for in the more info
   * @return True, if a news post with the specific text, label, and value is located
   */
  public boolean verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(String newsText, String label, String value) {
    TempoNews.refreshAndWaitFor(newsText, settings);
    TempoNews.waitForLabelAndValue(newsText, label, value, settings);
    return true;
  }

  /**
   * Verifies there is a news post containing specific text with a specific tag is present.<br>
   * <br>
   * FitNesse Example: <code>| verify news feed containing text | NEWS_TEXT | tagged with | RECORD_TAG | is present |</code>
   * 
   * @param newsText
   *          Text to search for in the news feed
   * @param newsTag
   *          Tag to search for on the news post
   * @return True, if a news post with the specific text and tag is located
   */
  public boolean verifyNewsFeedContainingTextTaggedWithIsPresent(String newsText, String newsTag) {
    TempoNews.refreshAndWaitFor(newsText, settings);
    TempoNews.waitForTag(newsText, newsTag, settings);
    return true;
  }

  /**
   * Verifies there is a news post containing specific text with a specific comment is present.<br>
   * <br>
   * FitNesse Example: <code>| verify news feed containing text | NEWS_TEXT | commented with | COMMENT | is present |</code>
   * 
   * @param newsText
   *          Text to search for in the news feed
   * @param newsComment
   *          Text to search for in the comments
   * @return True, if a news post with the specific text and comment is located
   */
  public boolean verifyNewsFeedContainingTextCommentedWithIsPresent(String newsText, String newsComment) {
    TempoNews.refreshAndWaitForComment(newsText, newsComment, settings);
    return true;
  }

  /**
   * Returns a string that matches the regex, this could be useful in extracting a system generated value from the news feed.<br>
   * <br>
   * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from news feed containing text | NEWS_TEXT |</code>
   * <code>| $regex= | get regex | [A-z]{3}-[0-9]{4} | group | GROUP |  </code> - Stores the regex value, which can later be accessed using
   * $error<br>
   * 
   * @param regex
   *          Regular expression string to search for within the news text
   * @param group
   *          Regular expression group to return
   * @param newsText
   *          Text to search for in the news feed
   * @return String that matches the regular expression
   */
  public String getRegexGroupFromNewsFeedContainingText(String regex, Integer group, String newsText) {
    TempoNews.refreshAndWaitFor(newsText, settings);
    return TempoNews.getRegexForNewsPost(regex, group, newsText, settings);
  }

  /**
   * Returns a string that matches the regex from a comment, this could be useful in extracting a system generated value from the news feed.
   * <br>
   * <br>
   * FitNesse Example:
   * <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from news feed containing text | NEWS_TEXT | commented with | COMMENTS |</code>
   * <code>| $regex= | get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from news feed containing text | NEWS_TEXT | commented with | COMMENTS |  </code>
   * - Stores the regex value, which can later be accessed using $error<br>
   * 
   * @param regex
   *          Regular expression string to search for within the news text
   * @param group
   *          Regular expression group to return
   * @param newsText
   *          Text to search for in the news feed
   * @param newsComment
   *          Text to search for in the comments
   * @return String that matches the regular expression
   */
  public String getRegexGroupFromNewsFeedContainingTextCommentedWith(String regex, Integer group, String newsText, String newsComment) {
    TempoNews.refreshAndWaitForComment(newsText, newsComment, settings);

    return TempoNews.getRegexForNewsPostComment(regex, group, newsText, newsComment, settings);
  }

  /**
   * Clicks on a record tag to navigate to a record summary dashboard<br>
   * <br>
   * FitNesse Example: <code>| click on news feed | NEWS_TEXT | record tag | RECORD_TAG |</code>
   * 
   * @param newsText
   *          Text to search for in the news feed
   * @param recordTag
   *          Record tag text
   */
  public void clickOnNewsFeedRecordTag(String newsText, String recordTag) {
    TempoNews.refreshAndWaitForTag(newsText, recordTag, settings);
    TempoNews.clickOnRecordTag(newsText, recordTag, settings);
  }

  /*
   * Tasks
   */

  /**
   * Clicks on the associated task.<br>
   * <br>
   * FitNesse Example: <code>| click on task | TASK_NAME |</code>
   * 
   * @param taskName
   *          Name of task to click (partial names are acceptable)
   *          If multiple task contain the same name the first will be selected
   */
  public void clickOnTask(String taskName) {
    TempoTask.refreshAndWaitFor(taskName, settings);
    TempoTask.click(taskName, settings);
  }

  /**
   * Verifies if task is present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify task | TASK_NAME | is present |</code>
   * 
   * @param taskName
   *          Name of the task
   * @return True, if task is located
   */
  public boolean verifyTaskIsPresent(String taskName) {
    TempoTask.refreshAndWaitFor(taskName, settings);
    return true;
  }

  /**
   * Verifies if task is not present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify report | TASK_NAME | is not present |</code><br>
   * <br>
   * Use this rather than <code>| reject | verify task | TASK_NAME | is present |</code> as it will not refresh and wait.
   * 
   * @param taskName
   *          Name of the task
   * @return True, if task is located
   */
  public boolean verifyTaskIsNotPresent(String taskName) {
    return !TempoTask.waitForReturn(taskName, settings.getNotPresentTimeoutSeconds(), settings);
  }

  /**
   * Verify a task with a specific name has a specific deadline<br>
   * <br>
   * FitNesse Example: <code>| verify task | TASK_NAME | has deadline of | DEADLINE |</code>
   * 
   * @param taskName
   *          Name of the task
   * @param deadline
   *          Deadline matching the Appian interface, e.g. 8d
   * @return True, if task with particular deadline is located
   */
  public boolean verifyTaskHasDeadlineOf(String taskName, String deadline) {
    TempoTask.waitFor(taskName, settings);
    TempoTask.hasDeadlineOf(taskName, deadline, settings);
    return true;
  }

  /**
   * Click on a task report<br>
   * <br>
   * FitNesse Example: <code>| click on task report | TASK_REPORT |</code>
   * 
   * @param taskReport
   *          Name of task report
   */
  public void clickOnTaskReport(String taskReport) {
    TempoTask.waitForTaskReport(taskReport, settings);
    TempoTask.clickOnTaskReport(taskReport, settings);
  }

  /*
   * Records
   */

  /**
   * @deprecated Replaced by {@link #clickOnRecordType(String)}
   * @param listName
   *          List name
   */
  @Deprecated
  public void clickOnRecordList(String listName) {
    clickOnRecordType(listName);
  }

  /**
   * Clicks on the record type.<br>
   * <br>
   * FitNesse Example: <code>| click on record type | RECORD_TYPE |</code> <code>| click on record type | RECORD_TYPE[INDEX] |</code>
   * 
   * @param typeName
   *          Name of record type to click (partial names are acceptable)
   *          If multiple record types contain the same name, then the first will be selected
   */
  public void clickOnRecordType(String typeName) {
    TempoRecordType.waitFor(typeName, settings);
    TempoRecordType.click(typeName, settings);
  }

  /**
   * @deprecated Replaced by {@link #clickOnRecordTypeUserFilter(String)}
   * @param facetOption
   *          Facet option
   */
  @Deprecated
  public void clickOnRecordListFacetOption(String facetOption) {
    clickOnRecordTypeUserFilter(facetOption);
  }

  /**
   * Clicks on the record type user filter.<br>
   * <br>
   * FitNesse Example: <code>| click on record type user filter | USER_FILTER |</code>
   * 
   * @param userFilter
   *          User Filter to click (partial names are acceptable)
   *          If multiple user filters contain the same name, then the first will be selected
   */
  public void clickOnRecordTypeUserFilter(String userFilter) {
    TempoRecordType.waitForUserFilter(userFilter, settings);
    TempoRecordType.clickOnUserFilter(userFilter, settings);
  }

  /**
   * @deprecated Replaced by {@link #verifyRecordTypeUserFilterIsPresent(String)}
   * @param facetOption
   *          Facet option
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
   * @param userFilter
   *          Name of user filter
   * @return True, if user filter is located
   */
  public boolean verifyRecordTypeUserFilterIsPresent(String userFilter) {
    TempoRecordType.waitForUserFilter(userFilter, settings);
    return true;
  }

  /**
   * @deprecated Replaced by {@link #clickOnRecord(String)}
   * @param itemName
   *          Item name
   */
  @Deprecated
  public void clickOnRecordItem(String itemName) {
    clickOnRecord(itemName);
  }

  /**
   * Clicks on the associated record.<br>
   * <br>
   * FitNesse Example: <code>| click on record | RECORD_NAME |</code>
   * 
   * @param recordName
   *          Name of record to click (partial names are acceptable)
   *          If multiple records contain the same name, then the first will be selected
   */
  public void clickOnRecord(String recordName) {
    TempoRecord.refreshAndWaitFor(recordName, settings);
    TempoRecord.click(recordName, settings);
  }

  /**
   * @deprecated Replaced by {@link #verifyRecordIsPresent(String)}
   * @param itemName
   *          Item name
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
   * @param recordName
   *          Name of record
   * @return True, if record is located
   */
  public boolean verifyRecordIsPresent(String recordName) {
    TempoRecord.waitFor(recordName, settings);
    return true;
  }

  /**
   * @deprecated Replaced by {@link #verifyRecordIsNotPresent(String)}
   * @param itemName
   *          Item name
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
   * @param recordName
   *          Name of record
   * @return True, if record is not located
   */
  public boolean verifyRecordIsNotPresent(String recordName) {
    return !TempoRecord.waitForReturn(recordName, settings.getNotPresentTimeoutSeconds(), settings);
  }

  /**
   * @deprecated Replaced by {@link #clickOnRecordView(String)}
   * @param facetName
   *          Facet name
   */
  @Deprecated
  public void clickOnRecordItemFacet(String facetName) {
    clickOnRecordView(facetName);
  }

  /**
   * Clicks on the associated record view.<br>
   * <br>
   * FitNesse Example: <code>| click on record view | VIEW_NAME |</code>
   * 
   * @param viewName
   *          Name of view (e.g. Summary, News, Related Actions, etc.) to click (partial names are acceptable)
   *          If multiple views contain the same name, then the first will be selected
   */
  public void clickOnRecordView(String viewName) {
    TempoRecord.waitForView(viewName, settings);
    TempoRecord.clickOnView(viewName, settings);
  }

  /**
   * @deprecated Replaced by {@link #clickOnRecordRelatedAction(String)}
   * @param relatedActionName
   *          Related action name
   */
  @Deprecated
  public void clickOnRecordItemRelatedAction(String relatedActionName) {
    clickOnRecordRelatedAction(relatedActionName);
  }

  /**
   * Clicks on the associated related action.<br>
   * <br>
   * FitNesse Example: <code>| click on record related action | RELATED_ACTION_NAME |</code>
   * 
   * @param relatedActionName
   *          Name of related action to click (partial names are acceptable)
   *          If multiple related actions contain the same name, then the first will be selected
   */
  public void clickOnRecordRelatedAction(String relatedActionName) {
    TempoRecord.refreshAndWaitForRelatedAction(relatedActionName, settings);
    TempoRecord.clickOnRelatedAction(relatedActionName, settings);
  }

  /**
   * @deprecated Replaced by {@link #verifyRecordRelatedActionIsPresent(String)}
   * @param relatedActionName
   *          Related action name
   * @return boolean
   */
  @Deprecated
  public boolean verifyRecordItemRelatedActionIsPresent(String relatedActionName) {
    return verifyRecordRelatedActionIsPresent(relatedActionName);
  }

  /**
   * Verifies if record related action is present in the user interface. This is useful for determining if security is applied correctly.
   * <br>
   * <br>
   * FitNesse Example: <code>| verify record related action | RELATED_ACTION_NAME | is present |</code>
   * 
   * @param relatedActionName
   *          Name of the related action
   * @return True, if related action is located
   */
  public boolean verifyRecordRelatedActionIsPresent(String relatedActionName) {
    TempoRecord.refreshAndWaitForRelatedAction(relatedActionName, settings);
    return true;
  }

  /**
   * @deprecated Replaced by {@link #verifyRecordRelatedActionIsNotPresent(String)}
   * @param relatedActionName
   *          Related action name
   * @return boolean
   */
  @Deprecated
  public boolean verifyRecordItemRelatedActionIsNotPresent(String relatedActionName) {
    return verifyRecordRelatedActionIsNotPresent(relatedActionName);
  }

  /**
   * Verifies if record related action is not present in the user interface. This is useful for determining if security is applied
   * correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify record related action | RELATED_ACTION_NAME | is not present |</code><br>
   * <br>
   * Use this rather than <code>| reject | verify record related action | RELATED_ACTION_NAME | is present |</code> as it will not refresh
   * and wait.
   * 
   * @param relatedActionName
   *          Name of related action
   * @return True, if related action is not located
   */
  public boolean verifyRecordRelatedActionIsNotPresent(String relatedActionName) {
    return !TempoRecord.waitForRelatedActionReturn(relatedActionName, settings.getNotPresentTimeoutSeconds(), settings);
  }

  /**
   * Sorts Record Grid view by a specific column <br>
   * <br>
   * FitNesse Example: <code>| sort record grid by column | COLUMN_NAME |</code><br>
   * 
   * @param columnName
   *          Name of column
   */

  public void sortRecordGridByColumn(String columnName) {
    TempoRecordGrid.waitForRecordGridColumn(columnName, settings);
    TempoRecordGrid.clickOnRecordGridColumn(columnName, settings);
  }

  /**
   * Clicks on record grid navigation option<br>
   * <br>
   * FitNesse Example: <code>| click on record grid view navigation | NAVIGATION_OPTION |</code> Navigation option can only be "First",
   * "Previous", "Next", or "Last"<br>
   * 
   * @param navOption
   *          Navigation option
   */
  public void clickOnRecordGridNavigation(String navOption) {
    TempoRecordGrid.waitForRecordGridNavigation(navOption, settings);
    TempoRecordGrid.clickOnRecordGridNavigation(navOption, settings);
  }

  /*
   * Reports
   */

  /**
   * Clicks on the associated report.<br>
   * <br>
   * FitNesse Example: <code>| click on report | REPORT_NAME |</code>
   * 
   * @param reportName
   *          Name of report to click (partial names are acceptable)
   *          If multiple reports contain the same name the first will be selected
   */
  public void clickOnReport(String reportName) {
    TempoReport.waitFor(reportName, settings);
    TempoReport.click(reportName, settings);
  }

  /**
   * Verifies if report is present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify report | REPORT_NAME | is present |</code>
   * 
   * @param reportName
   *          Name of the report
   * @return True, if report is located
   */
  public boolean verifyReportIsPresent(String reportName) {
    TempoReport.waitFor(reportName, settings);
    return true;
  }

  /**
   * Verifies if report is not present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify report | REPORT_NAME | is not present |</code><br>
   * <br>
   * Use this rather than <code>| reject | verify report | REPORT_NAME | is present |</code> as it will not refresh and wait.
   * 
   * @param reportName
   *          Name of the report
   * @return True, if report is not located
   */
  public boolean verifyReportIsNotPresent(String reportName) {
    TempoReport.waitForReturn(reportName, settings.getNotPresentTimeoutSeconds(), settings);
    return true;
  }

  /*
   * Actions
   */

  /**
   * Clicks on the associated action.<br>
   * <br>
   * FitNesse Example: <code>| click on action | ACTION_NAME |</code>
   * 
   * @param actionName
   *          Name of action to click (partial names are acceptable)
   *          If multiple actions contain the same name the first will be selected
   */
  public void clickOnAction(String actionName) {
    TempoAction.waitFor(actionName, settings);
    TempoAction.click(actionName, settings);
  }

  /**
   * Returns true if the 'Action Completed successfully' is currently being displayed in the interface.<br>
   * This only applies to Appian 7.11 and below <br>
   * FitNesse Example: <code>| verify action completed |</code>
   * 
   * @deprecated
   * @return True if the 'Action Completed successfully' is currently being displayed in the interface.
   */
  @Deprecated
  public boolean verifyActionCompleted() {
    TempoAction.isCompleted(settings);
    return true;
  }

  /**
   * Verifies if action is present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify action | ACTION_NAME | is present |</code>
   * 
   * @param actionName
   *          Name of the action
   * @return True, if action is located
   */
  public boolean verifyActionIsPresent(String actionName) {
    TempoAction.waitFor(actionName, settings);
    return true;
  }

  /**
   * Verifies if action is not present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify action | ACTION_NAME | is not present |</code><br>
   * <br>
   * Use this rather than <code>| reject | verify action | ACTION_NAME | is present |</code> as it will not refresh and wait.
   * 
   * @param actionName
   *          Name of the action
   * @return True, if action is not located
   */
  public boolean verifyActionIsNotPresent(String actionName) {
    return !TempoAction.waitForReturn(actionName, settings.getNotPresentTimeoutSeconds(), settings);
  }

  /**
   * Clicks on an actions application filter.<br>
   * <br>
   * FitNesse Example: <code>| click on application filter | APP_FILTER |</code>
   * 
   * @param appFilter
   *          App Filter to click (partial names are acceptable)
   *          If multiple application filters contain the same name, then the first will be selected
   */
  public void clickOnApplicationFilter(String appFilter) {
    TempoAction.waitForAppFilter(appFilter, settings);
    TempoAction.clickOnAppFilter(appFilter, settings);
  }

  /*
   * Interfaces
   */

  /**
   * Returns the title of the form.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get form title |</code> - Simply returns a string<br>
   * <code>| $title= | get form title | </code> - Stores the title in titleVariable, which can later be accessed using
   * $title<br>
   * <code>| check | get form title | FORM_TITLE |</code> - Returns true if form title matches FORM_TITLE input
   * 
   * @{titleVariable <br>
   *                 <code>| check | get form title | FORM_TITLE |</code> - Returns true if form title matches FORM_TITLE input
   * @return The title string
   */
  public String getFormTitle() {
    TempoForm.waitForTitle(settings);
    return TempoForm.getFormTitle(settings);
  }

  /**
   * Returns the instructions of the form.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get form instructions |</code> - Simply returns a string<br>
   * <code>| $inst= | get form instructions | </code> - Stores the title in instructionsVariable, which can later be
   * accessed using $inst<br>
   * <code>| check | get form instructions | FORM_INSTRUCTIONS |</code> - Returns true if form instructions matches FORM_INSTRUCTIONS input
   * 
   * @return The instructions string
   */
  public String getFormInstructions() {
    TempoForm.waitForInstructions(settings);
    return TempoForm.getFormInstructions(settings);
  }

  /**
   * Populates a field with specific values.<br>
   * <br>
   * This method can populate the following types of fields: Text, Paragraph, EncryptedText, Integer, Decimal, Date, Datetime, Select,
   * MultipleSelect, Radio, Checkbox, FileUpload, UserPicker, GroupPicker, UserGroupPicker, DocumentPicker, FolderPicker,
   * DocumentFolderPicker, CustomPicker. The only fields that support population of multiple items are Checkbox, MultipleSelect, and all
   * Pickers.
   * When populating a username, use the display value rather than the username itself.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| populate field | FIELD_NAME | with | FIELD_VALUE |</code><br>
   * <code>| populate field | FIELD_NAME | with | VALUE_1,VALUE_2 |</code> - For fields that allow multiple inputs (), comma separate the
   * field values.<br>
   * <code>| populate field | FIELD_NAME[2] | with | FIELD_VALUES |</code> - For fields with duplicate labels, an index of the field label
   * on the page can be used in the format of [1], [2], or [5]. As with Appian, indexing starts with 1.<br>
   * <code>| populate field | FIELD_NAME | with | +4 hours |</code> - For date and datetime fields, relative times can be entered such as +1
   * minute, +2 hours, +3 days. To use these relative times, the startDatetime must be initialized: see
   * {@link com.appiancorp.ps.automatedtest.fixture.BaseFixture#setStartDatetime()}
   * 
   * @param fieldName
   *          Can either be the label of the field or a label with an index
   * @param fieldValues
   *          An array of strings containing the values to populate into the interface
   */
  public void populateFieldWith(String fieldName, String[] fieldValues) {
    TempoField.waitFor(fieldName, settings);
    TempoField.populate(fieldName, fieldValues, settings);
  }

  /**
   * Populates a field of a particular type with specific values.<br>
   * <br>
   * This method can populate the following types of fields: TEXT, PARAGRAPH, and FILE_UPLOAD. <br>
   * Warning: Integer, Decimal, and Encrypted text fields will all be counted as TEXT fields.<br>
   * FitNesse Examples:<br>
   * <code>| populate | FIELD_TYPE | field | FIELD_NAME[INDEX] | with | FIELD_VALUE |</code><br>
   * 
   * @param fieldType
   *          Can only currently accept TEXT, PARAGRAPH, and FILE_UPLOAD
   * @param fieldName
   *          Can either be the label of the field or a label with an index
   * @param fieldValues
   *          An array of strings containing the values to populate into the interface
   */
  public void populateFieldWith(String fieldType, String fieldName, String[] fieldValues) {
    TempoField.waitFor(fieldType, fieldName, settings);
    TempoField.populate(fieldType, fieldName, fieldValues, settings);
  }

  /**
   * Populates a field with a single value that may contain a comma. This is useful is selecting an option that contains a comma.<br>
   * <br>
   * When populating a username, use the display value rather than the username itself.<br>
   * <br>
   * FitNesse Example: <code>| populate field | FIELD_NAME | with value | VALUE |</code>
   * 
   * @param fieldName
   *          Field label or label and index
   * @param fieldValue
   *          Field value, can contain a comma.
   */
  public void populateFieldWithValue(String fieldName, String fieldValue) {
    populateFieldWith(fieldName, new String[] { fieldValue });
  }

  /**
   * Populates a field in a section with specific values.<br>
   * <br>
   * This method is useful for populating fields without labels and can populate the following types of fields: Text, Paragraph,
   * EncryptedText, Integer, Decimal, Date, Datetime, Select, MultipleSelect, Radio, Checkbox, FileUpload, UserPicker, GroupPicker,
   * UserGroupPicker, DocumentPicker, FolderPicker, DocumentFolderPicker, CustomPicker.<br>
   * When populating a username, use the display value rather than the username itself.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| populate field | FIELD_NAME | in section | SECTION_NAME | with | FIELD_VALUE |</code><br>
   * <code>| populate field | FIELD_NAME | in section | SECTION_NAME | with | VALUE_1,VALUE_2 |</code> - For fields that allow multiple
   * inputs (), comma separate the field values.<br>
   * <code>| populate field | FIELD_NAME | in section | SECTION_NAME[1] | with | FIELD_VALUES |</code> - For fields in sections with
   * duplicate labels, an index of the section label on the page can be used in the format of [1], [2], or [5]. As with Appian, indexing
   * starts with 1.<br>
   * <code>| populate field | FIELD_NAME | in section | SECTION_NAME | with | +4 hours |</code> - For date and datetime fields, relative
   * times can be entered such as +1 minute, +2 hours, +3 days. To use these relative times, the startDatetime must be initialized: see
   * {@link com.appiancorp.ps.automatedtest.fixture.BaseFixture#setStartDatetime()}
   * 
   * @param fieldName
   *          Can either be the label of the field or label with an index
   * @param sectionName
   *          Can either be the label of the section or an label with an index
   * @param fieldValues
   *          An array of strings containing the values to populate into the interface
   */
  public void populateFieldInSectionWith(String fieldName, String sectionName, String[] fieldValues) {
    TempoSection.waitFor(fieldName, sectionName, settings);
    TempoSection.populate(fieldName, sectionName, fieldValues, settings);
  }

  /**
   * Expand a section that is hidden.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| expand section | SECTION_NAME |</code><br>
   * 
   * @param sectionName
   *          Label of the section
   */
  public void expandSection(String sectionName) {
    TempoSection.waitFor(sectionName, settings);
    TempoSection.clickExpandSection(sectionName, settings);
  }

  /**
   * Collapse a section that is visible.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| collapse section | SECTION_NAME |</code><br>
   * 
   * @param sectionName
   *          Label of the section
   */
  public void collapseSection(String sectionName) {
    TempoSection.waitFor(sectionName, settings);
    TempoSection.clickCollapseSection(sectionName, settings);
  }

  /**
   * Used to clear a field.<br>
   * <br>
   * This method currently works for <br>
   * FitNesse Example: <code>| clear field | FIELD_NAME | of | VALUES |</code><br>
   * 
   * @param fieldName
   *          Field to clear
   */
  public void clearField(String fieldName) {
    TempoField.waitFor(fieldName, settings);
    TempoField.clear(fieldName, settings);
  }

  /**
   * Used to clear a field of specific values.<br>
   * <br>
   * This method is only useful for picker objects to unselect an item. <br>
   * FitNesse Example: <code>| clear field | FIELD_NAME | of | VALUES |</code><br>
   * 
   * @param fieldName
   *          Field to clear
   * @param fieldValues
   *          Values to unselect
   */
  public void clearFieldOf(String fieldName, String[] fieldValues) {
    TempoField.waitFor(fieldName, settings);
    TempoField.clearOf(fieldName, fieldValues, settings);
  }

  /**
   * Returns the value of a field.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get field | FIELD_NAME | value |</code> - Simply returns a string<br>
   * <code>| $fieldValue= | get field | FIELD_NAME | value | </code> - Stores the field value in fieldValue, which can later be accessed
   * using $fieldValue<br>
   * <code>| check | get field | FIELD_NAME | FIELD_VALUE |</code> - Returns true if the field value title matches the FIELD_VALUE input.
   * For file upload fields, do not include the full path. This will not work for relative date and datetime fields.
   * 
   * @param fieldName
   *          Name of name and index of the field
   * @return The field value
   */
  public String getFieldValue(String fieldName) {
    TempoField.waitFor(fieldName, settings);
    return TempoField.getValue(fieldName, settings);
  }

  /**
   * Returns the value of a field in a section.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get field | FIELD_NAME | in section | SECTION_ NAME | value |</code> - Simply returns a string<br>
   * <code>| $fieldValue= | get field | FIELD_NAME | in section | SECTION_NAME | value |</code> - Stores the field value in fieldValue,
   * which can later be accessed using $fieldValue<br>
   * <code>| check | get field | FIELD_NAME | in section | SECTION_NAME | value | FIELD_VALUE |</code> - Returns true if the field value
   * title matches the FIELD_VALUE input. For file upload fields, do not include the full path. This will not work for relative date and
   * datetime fields.
   * 
   * @param fieldName
   *          Field label or label and index
   * @param sectionName
   *          Section label or label and index
   * @return The field value
   */
  public String getFieldInSectionValue(String fieldName, String sectionName) {
    TempoSection.waitFor(fieldName, sectionName, settings);
    return TempoSection.getValue(fieldName, sectionName, settings);
  }

  /**
   * Verifies a field contains a specific value.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| verify field | FIELD_NAME | contains | VALUES |</code> <code>| verify field | FIELD_NAME | contains | +4 hours |</code> - For
   * date and datetime fields, relative times can be entered such as +1 minute, +2 hours, +3 days. To use these relative times, the
   * startDatetime must be initialized: see {@link com.appiancorp.ps.automatedtest.fixture.BaseFixture#setStartDatetime()}
   * 
   * @param fieldName
   *          Can either be a name or a name and index, e.g. 'Text Field' or 'Text Field[1]'
   * @param fieldValues
   *          Values to compare field value against
   * @return True, if the field contains the value
   */
  public boolean verifyFieldContains(String fieldName, String[] fieldValues) {
    TempoField.waitFor(fieldName, settings);
    return TempoField.contains(fieldName, fieldValues, settings);
  }

  /**
   * Verifies a field with a single value that may contain a comma. This is useful in verifying that an option that contains a comma has
   * been selected.<br>
   * <br>
   * FitNesse Example: <code>| verify field | FIELD_NAME | contains value | VALUE |</code>
   * 
   * @param fieldName
   *          Field label or label and index
   * @param fieldValue
   *          Field value, can contain a comma.
   * @return True, if fields contains value
   */
  public boolean verifyFieldContainsValue(String fieldName, String fieldValue) {
    return verifyFieldContains(fieldName, new String[] { fieldValue });
  }

  /**
   * Verifies a field contains a specific value.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| verify field | FIELD_NAME | in section | SECTION_NAME | contains | VALUES |</code><br>
   * <code>| verify field | FIELD_NAME | in section | SECTION_NAME | contains | +4 hours |</code> - For date and datetime fields, relative
   * times can be entered such as +1 minute, +2 hours, +3 days. To use these relative times, the startDatetime must be initialized: see
   * {@link com.appiancorp.ps.automatedtest.fixture.BaseFixture#setStartDatetime()}
   * 
   * @param fieldName
   *          Can either be a field label or a label and index, e.g. 'Text Field' or 'Text Field[2]'
   * @param sectionName
   *          Can either be a section label or a label and index, e.g. 'Section Name' or 'Section Name[2]'
   * @param fieldValues
   *          Values to compare field value against
   * @return True, if the field contains the value
   */
  public boolean verifyFieldInSectionContains(String fieldName, String sectionName, String[] fieldValues) {
    TempoSection.waitFor(fieldName, sectionName, settings);
    return TempoSection.contains(fieldName, sectionName, fieldValues, settings);
  }

  /**
   * Verifies a field contains a validation message.<br>
   * <br>
   * FitNesse Example: <code>| verify field | FIELD_NAME | contains validations | VALIDATION_MESSAGES |</code>
   * 
   * @param fieldName
   *          Field label or label and index
   * @param validationMessages
   *          Validation messages, separated by a comma.
   * @return True, if fields contains value
   */
  public boolean verifyFieldContainsValidationMessage(String fieldName, String[] validationMessages) {
    TempoField.waitFor(fieldName, settings);
    TempoField.waitForValidationMessages(fieldName, validationMessages, settings);
    return true;
  }

  /**
   * Returns the validation message from a field.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get field | FIELD_NAME | validation message |</code> - Simply returns a string<br>
   * <code>| $error= | get field | FIELD_NAME | validation message | </code> - Stores the validation message in error, which can later be
   * accessed using $error<br>
   * <code>| check | get field | FIELD_NAME | validation message | VALIDATION_MESSAGE |</code> - Returns true if the validation message
   * matches the VALIDATION_MESSAGE input.
   * 
   * @param fieldName
   *          Name of name and index of the field
   * @return Validation message
   */
  public String getFieldValidationMessage(String fieldName) {
    TempoField.waitFor(fieldName, settings);
    return TempoField.getValidationMessages(fieldName, settings);
  }

  /**
   * Verifies if field is displayed in the interface. This is useful to test dynamic forms.<br>
   * <br>
   * FitNesse Example: <code>| verify field | FIELD_NAME | is present |</code>
   * 
   * @param fieldName
   *          Field name to find in interface
   * @return True, if field is located in the interface
   */
  public boolean verifyFieldIsPresent(String fieldName) {
    TempoField.waitFor(fieldName, settings);
    return true;
  }

  /**
   * Verifies if field is not displayed in the interface. This is useful to test dynamic forms.<br>
   * <br>
   * FitNesse Example: <code>| verify field | FIELD_NAME | is not present |</code>
   * 
   * @param fieldName
   *          Field name to not find in interface
   * @return True, if field is not located in the interface
   */
  public boolean verifyFieldIsNotPresent(String fieldName) {
    return !TempoField.waitForReturn(fieldName, settings.getNotPresentTimeoutSeconds(), settings);
  }

  /**
   * Populates fields in a grid. This method is useful for populating the following types of fields: Text, Paragraph, EncryptedText,
   * Integer, Decimal, Date, Datetime, Select, MultipleSelect, Radio, Checkbox, FileUpload, UserPicker, GroupPicker, UserGroupPicker,
   * DocumentPicker, FolderPicker, DocumentFolderPicker, CustomPicker.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| populate grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | with | VALUE |</code><br>
   * <code>| populate grid | [1] | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | with | VALUE |</code> - If the grid does not have a
   * title, an index can be used to select the grid, e.g. [1] would be the first grid in the interface.<br>
   * <code>| populate grid | GRID_NAME | column | [2] | row | ROW_INDEX | with | VALUE |</code> - The column index can be used if the
   * columns do not have titles. As with Appian indexing starts with [1] and if the table has checkboxes, they are considered to be in the
   * first column.<br>
   * <code>| populate grid | GRID_NAME[3] | column | COLUMN_NAME | row | ROW_INDEX | with | VALUE |</code> - If there are multiple grids
   * with the same name then use an index, e.g. 'Grid Name[3]' with select the third grid with the name of 'Grid Name' in the interface
   * 
   * @param gridName
   *          Name of grid
   * @param columnName
   *          Name or index of the column
   * @param rowNum
   *          Index of the row
   * @param fieldValues
   *          Values to populate
   */
  public void populateGridColumnRowWith(String gridName, String columnName, String rowNum, String[] fieldValues) {
    TempoGrid.waitFor(gridName, columnName, rowNum, settings);
    TempoGrid.populate(gridName, columnName, rowNum, fieldValues, settings);
  }

  /**
   * Returns the value of a field.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | value |</code> - Simply returns a string<br>
   * <code>| $fieldValue= | get grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | value | </code> - Stores
   * the field value in fieldValue, which can later be accessed using $fieldValue<br>
   * <code>| check | get grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | value | FIELD_VALUE |</code> -
   * Returns true if the field value title matches the FIELD_VALUE input. For file upload fields, do not include the full path. This will
   * not work for relative date and datetime fields.
   * 
   * @param gridName
   *          Name of the grid
   * @param columnName
   *          Name or index of the column
   * @param rowNum
   *          Index of the row
   * @return The field value
   */
  public String getGridColumnRowValue(String gridName, String columnName, String rowNum) {
    TempoGrid.waitFor(gridName, columnName, rowNum, settings);
    return TempoGrid.getValue(gridName, columnName, rowNum, settings);
  }

  /**
   * Verifies a field contains a specific value.<br>
   * <br>
   * FitNesse Example:
   * <code>| verify grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | contains | VALUES |</code>
   * 
   * @param gridName
   *          Name of the grid
   * @param columnName
   *          Name or index of the column
   * @param rowNum
   *          Index of the row
   * @param fieldValues
   *          Field values to compare against
   * @return True, if the field contains the value
   */
  public boolean verifyGridColumnRowContains(String gridName, String columnName, String rowNum, String[] fieldValues) {
    TempoGrid.waitFor(gridName, columnName, rowNum, settings);
    return TempoGrid.contains(gridName, columnName, rowNum, fieldValues, settings);
  }

  /**
   * Selects a row in an editable or paging grid.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| select grid | GRID_NAME_OR_INDEX | row | [1] |</code><br>
   * <code>| select grid | GRID_NAME[2] | row | [2] |</code> - If there are multiple grids with the same name, use the index to select the
   * correct one. As with Appian, indexing starts with [1].
   * 
   * @param gridName
   *          Can either be the grid name or grid name with index, e.g. 'Grid Name' or 'Grid Name[2]'
   * @param rowNum
   *          Index of row to select, e.g. [2]
   */
  public void selectGridRow(String gridName, String rowNum) {
    TempoGrid.waitFor(gridName, rowNum, settings);
    TempoGrid.selectRow(gridName, rowNum, settings);
  }

  /**
   * Verifies if a grid row is selected<br>
   * <br>
   * FitNesse Example: <code>| verify grid | GRID_NAME_OR_INDEX | row | ROW_NUMBER | is selected |</code>
   * 
   * @param gridName
   *          Name or name and index of grid
   * @param rowNum
   *          Row number
   * @return True, if row is selected
   */
  public boolean verifyGridRowIsSelected(String gridName, String rowNum) {
    TempoGrid.waitFor(gridName, rowNum, settings);
    return TempoGrid.verifyGridRowIsSelected(gridName, rowNum, settings);
  }

  /**
   * Clicks on the add row link for a grid<br>
   * <br>
   * FitNesse Example: <code>| click on grid | GRID_NAME_OR_INDEX | add row link |</code>
   * 
   * @param gridName
   *          Name or name and index of grid
   */
  public void clickOnGridAddRowLink(String gridName) {
    TempoGrid.waitFor(gridName, settings);
    TempoGrid.clickOnAddRowLink(gridName, settings);
  }

  /**
   * Clicks on the page link below a paging grid<br>
   * <br>
   * FitNesse Example: <code>| click on grid | GRID_NAME_OR_INDEX | navigation | NAV_REFERENCE |</code> - nav reference only takes "first",
   * previous, next, or "last"
   * 
   * @param gridName
   *          Name or name and index of grid
   * @param navOption
   *          "first", previous, next, or "last"
   */

  public void clickOnGridNavigation(String gridName, String navOption) {
    TempoGrid.waitFor(gridName, settings);
    TempoGrid.clickOnNavigationOption(gridName, navOption, settings);
  }

  /**
   * Sort a grid by a column<br>
   * <br>
   * FitNesse Example: <code>| sort grid | GRID_NAME_OR_INDEX | by column | COLUMN_NAME_OR_INDEX |</code> -
   * 
   * @param gridName
   *          Name or name and index of grid
   * @param columnName
   *          Name or index of column
   */
  public void sortGridByColumn(String gridName, String columnName) {
    TempoGrid.waitFor(gridName, settings);
    TempoGrid.sortByColumn(gridName, columnName, settings);
  }

  /**
   * Clicks on the first link that matches the linkName.<br>
   * <br>
   * FitNesse Example: <code>| click on link | LINK_NAME |</code>
   * 
   * @param linkName
   *          Name of link to click
   */
  public void clickOnLink(String linkName) {
    TempoLinkField.waitFor(linkName, settings);
    TempoLinkField.click(linkName, settings);
  }

  /**
   * Verifies there is a link field with the specified name.
   * The method will wait for the timeout period and refresh up to the configured number of refresh times before failing.<br>
   * <br>
   * FitNesse Example: <code>| verify link field | LINK_NAME | is present |</code>
   * 
   * @param linkName
   *          Name of link to verify is present
   * @return True, if link field is located
   */
  public boolean verifyLinkFieldIsPresent(String linkName) {
    TempoLinkField.waitFor(linkName, settings);
    return true;
  }

  /**
   * Verifies if a link field's URL contains a specific value.<br>
   * <br>
   * FitNesse Example: <code>| verify link | LINK_NAME | URL contains | URL_TEXT |</code>
   * 
   * @param linkName
   *          Name of link to look for URL
   * @param linkURL
   *          Values to verify that the link URL contains
   * @return True, if the link URL does contain the value
   */
  public boolean verifyLinkURLContains(String linkName, String URLText) {
    TempoLinkField.waitFor(linkName, settings);
    return TempoLinkField.containsURLValue(linkName, URLText, settings);
  }

  /**
   * Returns the URL of a link field.<br>
   * <br>
   * FitNesse example: <code>| $VARIABLE_NAME= | get link | LINK_NAME | URL |<code> Use $VARIABLE_NAME to access the variable
   * containing the link URL of the link field specified
   * 
   * @param length
   *          Length of random string
   * @return Random alphanumeric string
   */
  public String getLinkURL(String linkName) {
    return TempoLinkField.getLinkURL(linkName, settings);
  }

  /**
   * Clicks on the first button that matches the buttonName.<br>
   * <br>
   * FitNesse Example: <code>| click on button | BUTTON_NAME |</code>
   * 
   * @param buttonName
   *          Name of button to click
   */
  public void clickOnButton(String buttonName) {
    TempoButton.waitFor(buttonName, settings);
    TempoButton.click(buttonName, settings);
  }

  /**
   * Verifies if button with given label is present in the user interface. This is useful for determining if conditionals to show buttons
   * have worked correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify button with label | LABEL | is present |</code>
   * 
   * @param buttonName
   *          Name of the button
   * @return True, if button is located
   */
  public boolean verifyButtonIsPresent(String buttonName) {
    TempoButton.waitFor(buttonName, settings);
    return true;
  }

  /**
   * Verifies if button with given label is not present in the user interface. This is useful for determining if conditionals to hide
   * buttons have worked correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify button with label | LABEL | is not present |</code><br>
   * <br>
   * Use this rather than <code>| reject | verify button with label| LABEL | is present |</code> as it will not refresh and wait.
   * 
   * @param buttonName
   *          Name of the button
   * @return True, if button is not located
   */
  public boolean verifyButtonIsNotPresent(String buttonName) {
    return !TempoButton.waitForReturn(buttonName, settings.getNotPresentTimeoutSeconds(), settings);
  }

  /**
   * Clicks on the first radio option that matches the optionName. This is useful if the radio field does not have a label.<br>
   * <br>
   * FitNesse Example: <code>| click on radio option | OPTION_NAME |</code> <code>| click on radio option | OPTION_NAME[INDEX] |</code>
   * 
   * @param optionName
   *          Name of radio option to click
   */
  public void clickOnRadioOption(String optionName) {
    TempoRadioField.waitForOption(optionName, settings);
    TempoRadioField.clickOption(optionName, settings);
  }

  /**
   * Clicks on the first checkbox option that matches the optionName. This is useful if the checkbox field does not have a label.<br>
   * <br>
   * FitNesse Example: <code>| click on checkbox option | OPTION_NAME |</code>
   * <code>| click on checkbox option | OPTION_NAME[INDEX] |</code>
   * 
   * @param optionName
   *          Name of checkbox option to click
   */
  public void clickOnCheckboxOption(String optionName) {
    TempoCheckboxField.waitForOption(optionName, settings);
    TempoCheckboxField.clickOption(optionName, settings);
  }

  /**
   * Verifies if a section contains a specific error. Useful for testing section validation.<br>
   * <br>
   * FitNesse Example: <code>| verify section | SECTION_NAME | contains validation message | VALIDATION_MESSAGE(S) |</code>
   * 
   * @param sectionName
   *          Name of section to look for error
   * @param validationMessages
   *          Validation messages to verify
   * @return True, if error is located
   */
  public boolean verifySectionContainsValidationMessage(String sectionName, String[] validationMessages) {
    TempoSection.waitForValidationMessages(sectionName, validationMessages, settings);
    return true;
  }

  /**
   * Returns the validation message from a section.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get section | SECTION_NAME | validation message |</code> - Simply returns a string<br>
   * <code>| $error= | get section | SECTION_NAME | validation message | </code> - Stores the validation message in error, which can later
   * be accessed using $error<br>
   * <code>| check | get section | SECTION_NAME | validation message | VALIDATION_MESSAGE |</code> - Returns true if the section validation
   * message matches the VALIDATION_MESSAGE input.
   * 
   * @param sectionName
   *          Name of section
   * @return Validation message
   */
  public String getSectionValidationMessage(String sectionName) {
    TempoSection.waitFor(sectionName, settings);
    return TempoSection.getValidationMessages(sectionName, settings);
  }

}