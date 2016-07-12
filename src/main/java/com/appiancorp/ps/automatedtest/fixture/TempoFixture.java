package com.appiancorp.ps.automatedtest.fixture;

import org.apache.log4j.Logger;

import com.appiancorp.ps.automatedtest.tempo.TempoError;
import com.appiancorp.ps.automatedtest.tempo.TempoLogin;
import com.appiancorp.ps.automatedtest.tempo.TempoMenu;
import com.appiancorp.ps.automatedtest.tempo.TempoSearch;
import com.appiancorp.ps.automatedtest.tempo.action.TempoAction;
import com.appiancorp.ps.automatedtest.tempo.action.TempoActionApplicationFilter;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoButton;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoChart;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoCheckboxFieldOption;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoField;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoFieldFactory;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoFieldValidation;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoFormInstructions;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoFormTitle;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoGrid;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoGridAddRow;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoGridCell;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoGridColumn;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoGridNavigation;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoGridRow;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoGridRowCount;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoGridSelectAll;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoGridTotalCount;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoLinkField;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoLinkFieldUrl;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoMilestoneFieldStep;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoRadioFieldOption;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoSaveChanges;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoSection;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoSectionField;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoSectionToggle;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoSectionValidation;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItem;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItemComment;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItemMoreInfo;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItemMoreInfoLabelValue;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItemTag;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecord;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordGridColumn;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordGridNavigation;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordRelatedAction;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordType;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordTypeUserFilter;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordView;
import com.appiancorp.ps.automatedtest.tempo.report.TempoReport;
import com.appiancorp.ps.automatedtest.tempo.task.TempoTask;
import com.appiancorp.ps.automatedtest.tempo.task.TempoTaskDeadline;
import com.appiancorp.ps.automatedtest.tempo.task.TempoTaskReport;

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
    TempoMenu.getInstance(settings).waitFor(tempoMenu);
    TempoMenu.getInstance(settings).click(tempoMenu);
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
    TempoSearch.getInstance(settings).waitFor(searchTerm);
    TempoSearch.getInstance(settings).populate(searchTerm);
  }

  /**
   * Logs out of Appian <br>
   * Example: <code>| logout |</code>
   */
  public void logout() {
    TempoLogin.getInstance(settings).waitFor();
    TempoLogin.getInstance(settings).logout();
  }

  /*
   * News
   */

  /**
   * Verifies a news post containing specific text is present.
   * The method will wait for the timeout period and refresh up to the configured number of refresh times before failing.<br>
   * <br>
   * FitNesse Example: <code>| verify news feed containing text | NEWS_TEXT | is present |</code>
   * 
   * @param newsText
   *          Text to search for in the news feed
   * @return True, if post is located with specific text
   */
  public boolean verifyNewsFeedContainingTextIsPresent(String newsText) {
    TempoNewsItem.getInstance(settings).refreshAndWaitFor(newsText);
    return true;
  }

  /**
   * Verifies a news item containing specific text is not present.<br>
   * <br>
   * FitNesse Example: <code>| verify news feed containing text | NEWS_TITLE | is not present |</code><br>
   * <br>
   * The reason to use than <code>| reject | verify news feed containing text | NEWS_TEXT | is present |</code> is that this method will not
   * refresh and wait.
   * 
   * @param newsText
   *          Text to search for in the news feed
   * @return True, if no post is located with specific text
   */
  public boolean verifyNewsFeedContainingTextIsNotPresent(String newsText) {
    return !TempoNewsItem.getInstance(settings).waitForReturn(settings.getNotPresentTimeoutSeconds(), newsText);
  }

  /**
   * Toggles the more info on a news item containing specific text.<br>
   * <br>
   * FitNesse Example: <code>| toggle more info for news feed containing text | NEWS_TEXT |</code>
   * 
   * @param newsText
   *          Text to search for in the news feed
   */
  public void toggleMoreInfoForNewsFeedContainingText(String newsText) {
    TempoNewsItemMoreInfo.getInstance(settings).waitFor(newsText);
    TempoNewsItemMoreInfo.getInstance(settings).click(newsText);
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
    TempoNewsItem.getInstance(settings).waitFor(newsText);
    TempoNewsItem.getInstance(settings).clear(newsText);
  }

  /**
   * Verifies a news item containing specific text with a specific label and value is present.<br>
   * Deletes a news post.<br>
   * <br>
   * FitNesse Example: <code>| delete news post | NEWS_TEXT |</code>
   * 
   * @param newsText
   *          Text to search for in the news feed
   */
  public void deleteAllNewsPosts() {
    TempoNewsItem.getInstance(settings).clearAll();
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
    TempoNewsItemMoreInfoLabelValue.getInstance(settings).refreshAndWaitFor(newsText);
    TempoNewsItemMoreInfoLabelValue.getInstance(settings).waitFor(newsText, label, value);
    return true;
  }

  /**
   * Verifies a news item containing specific text with a specific tag is present.<br>
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
    TempoNewsItemTag.getInstance(settings).refreshAndWaitFor(newsText, newsTag);
    TempoNewsItemTag.getInstance(settings).waitFor(newsText, newsTag);
    return true;
  }

  /**
   * Verifies a news item containing specific text with a specific comment is present.<br>
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
    TempoNewsItemComment.getInstance(settings).refreshAndWaitFor(newsText, newsComment);
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
    TempoNewsItem.getInstance(settings).refreshAndWaitFor(newsText);
    return TempoNewsItem.getInstance(settings).regexCapture(regex, group, newsText);
  }

  /**
   * Returns a string that matches the regex from a comment, this could be useful in extracting a system generated value from the news feed. <br>
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
    TempoNewsItemComment.getInstance(settings).refreshAndWaitFor(newsText, newsComment);
    return TempoNewsItemComment.getInstance(settings).regexCapture(regex, group, newsText, newsComment);
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
    TempoNewsItemTag.getInstance(settings).refreshAndWaitFor(newsText, recordTag);
    TempoNewsItemTag.getInstance(settings).click(newsText, recordTag);
  }

  /*
   * Tasks
   */

  /**
   * Clicks on the associated task.<br>
   * <br>
   * FitNesse Example: <code>| click on task | TASK_NAME or TASK_NAME[INDEX] |</code>
   * 
   * @param taskName
   *          Name of task to click (partial names are acceptable)
   *          If multiple task contain the same name the first will be selected
   */
  public void clickOnTask(String taskName) {
    TempoTask.getInstance(settings).refreshAndWaitFor(taskName);
    TempoTask.getInstance(settings).click(taskName);
  }

  /**
   * Returns a string that matches the regex, this could be useful in extracting a system generated value from a task's name.<br>
   * <br>
   * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from task name containing text | TASK_TEXT | </code>
   * 
   * @param regex
   *          Regular expression string to search for within the form
   * @param group
   *          Regular expression group to return
   * @param taskText
   *          Text to find within the tasks' names
   * @return String that matches the regular expression
   */
  public String getRegexGroupFromTaskNameContainingText(String regex, Integer group, String taskText) {
    TempoTask.getInstance(settings).refreshAndWaitFor(taskText);
    return TempoTask.getInstance(settings).regexCapture(regex, group, taskText);
  }

  /**
   * Verifies if task is present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify task | TASK_NAME or TASK_NAME[INDEX] | is present |</code>
   * 
   * @param taskName
   *          Name of the task
   * @return True, if task is located
   */
  public boolean verifyTaskIsPresent(String taskName) {
    TempoTask.getInstance(settings).refreshAndWaitFor(taskName);
    return true;
  }

  /**
   * Verifies if task is not present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify task | TASK_NAME or TASK_NAME[INDEX] | is not present |</code><br>
   * <br>
   * Use this rather than <code>| reject | verify task | TASK_NAME | is present |</code> as it will not refresh and wait.
   * 
   * @param taskName
   *          Name of the task
   * @return True, if task is located
   */
  public boolean verifyTaskIsNotPresent(String taskName) {
    return !TempoTask.getInstance(settings).waitForReturn(settings.getNotPresentTimeoutSeconds(), taskName);
  }

  /**
   * Verify a task with a specific name has a specific deadline.<br>
   * <br>
   * FitNesse Example: <code>| verify task | TASK_NAME or TASK_NAME[INDEX] | has deadline of | DEADLINE |</code>
   * 
   * @param taskName
   *          Name of the task
   * @param deadline
   *          Deadline matching the Appian interface, e.g. 8d
   * @return True, if task with particular deadline is located
   */
  public boolean verifyTaskHasDeadlineOf(String taskName, String deadline) {
    TempoTaskDeadline.getInstance(settings).waitFor(taskName, deadline);
    return true;
  }

  /**
   * Click on a task report.<br>
   * <br>
   * FitNesse Example: <code>| click on task report | TASK_REPORT_NAME |</code>
   * 
   * @param taskReport
   *          Name of task report
   */
  public void clickOnTaskReport(String taskReport) {
    TempoTaskReport.getInstance(settings).waitFor(taskReport);
    TempoTaskReport.getInstance(settings).click(taskReport);
  }

  /**
   * Clicks on the record type.<br>
   * <br>
   * FitNesse Example: <code>| click on record type | RECORD_TYPE_NAME or RECORD_TYPE_NAME[INDEX] |</code>
   * 
   * @param typeName
   *          Name of record type to click (partial names are acceptable)
   *          If multiple record types contain the same name, then the first will be selected
   */
  public void clickOnRecordType(String typeName) {
    TempoRecordType.getInstance(settings).waitFor(typeName);
    TempoRecordType.getInstance(settings).click(typeName);
  }

  /**
   * Clicks on the record type user filter.<br>
   * <br>
   * FitNesse Example: <code>| click on record type user filter | USER_FILTER_NAME |</code>
   * 
   * @param userFilter
   *          User Filter to click (partial names are acceptable)
   *          If multiple user filters contain the same name, then the first will be selected
   */
  public void clickOnRecordTypeUserFilter(String userFilter) {
    TempoRecordTypeUserFilter.getInstance(settings).waitFor(userFilter);
    TempoRecordTypeUserFilter.getInstance(settings).click(userFilter);
  }

  /**
   * Verifies if user filter is present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify record type user filter | USER_FILTER_NAME | is present |</code>
   * 
   * @param userFilter
   *          Name of user filter
   * @return True, if user filter is located
   */
  public boolean verifyRecordTypeUserFilterIsPresent(String userFilter) {
    TempoRecordTypeUserFilter.getInstance(settings).waitFor(userFilter);
    return true;
  }

  /**
   * Clicks on the associated record.<br>
   * <br>
   * FitNesse Example: <code>| click on record | RECORD_NAME or RECORD_NAME[INDEX] |</code>
   * 
   * @param recordName
   *          Name of record to click (partial names are acceptable)
   *          If multiple records contain the same name, then the first will be selected
   */
  public void clickOnRecord(String recordName) {
    TempoRecord.getInstance(settings).refreshAndWaitFor(recordName);
    TempoRecord.getInstance(settings).click(recordName);
  }

  /**
   * Returns a string that matches the regex, this could be useful in extracting a system generated value from a record whose name, i.e.,
   * title field matches. <br>
   * <br>
   * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from record name containing text | RECORD_TEXT | </code>
   * 
   * @param regex
   *          Regular expression string to search for within the form
   * @param group
   *          Regular expression group to return
   * @param recordText
   *          Name of text contained within record name
   * @return String that matches the regular expression
   */
  public String getRegexGroupFromRecordNameContainingText(String regex, Integer group, String recordText) {
    TempoRecord.getInstance(settings).refreshAndWaitFor(recordText);
    return TempoRecord.getInstance(settings).regexCapture(regex, group, recordText);
  }

  /**
   * Verifies if record is present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify record | RECORD_NAME or RECORD_NAME[INDEX] | is present |</code>
   * 
   * @param recordName
   *          Name of record
   * @return True, if record is located
   */
  public boolean verifyRecordIsPresent(String recordName) {
    TempoRecord.getInstance(settings).waitFor(recordName);
    return true;
  }

  /**
   * Verifies if record is not present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify record | RECORD_NAME or RECORD_NAME[INDEX] | is not present |</code><br>
   * <br>
   * Use this rather than <code>| reject | verify record | RECORD_NAME | is present |</code> as it will not refresh and wait.
   * 
   * @param recordName
   *          Name of record
   * @return True, if record is not located
   */
  public boolean verifyRecordIsNotPresent(String recordName) {
    return !TempoRecord.getInstance(settings).waitForReturn(settings.getNotPresentTimeoutSeconds(), recordName);
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
    TempoRecordView.getInstance(settings).waitFor(viewName);
    TempoRecordView.getInstance(settings).click(viewName);
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
    TempoRecordRelatedAction.getInstance(settings).refreshAndWaitFor(relatedActionName);
    TempoRecordRelatedAction.getInstance(settings).click(relatedActionName);
  }

  /**
   * Verifies if record related action is present in the user interface. This is useful for determining if security is applied correctly. <br>
   * <br>
   * FitNesse Example: <code>| verify record related action | RELATED_ACTION_NAME | is present |</code>
   * 
   * @param relatedActionName
   *          Name of the related action
   * @return True, if related action is located
   */
  public boolean verifyRecordRelatedActionIsPresent(String relatedActionName) {
    TempoRecordRelatedAction.getInstance(settings).refreshAndWaitFor(relatedActionName);
    return true;
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
    return !TempoRecord.getInstance(settings).waitForReturn(settings.getNotPresentTimeoutSeconds(), relatedActionName);
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
    TempoRecordGridColumn.getInstance(settings).waitFor(columnName);
    TempoRecordGridColumn.getInstance(settings).click(columnName);
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
    TempoRecordGridNavigation.getInstance(settings).waitFor(navOption);
    TempoRecordGridNavigation.getInstance(settings).click(navOption);
  }

  /*
   * Reports
   */

  /**
   * Clicks on the associated report.<br>
   * <br>
   * FitNesse Example: <code>| click on report | REPORT_NAME or REPORT_NAME[INDEX] |</code>
   * 
   * @param reportName
   *          Name of report to click (partial names are acceptable)
   *          If multiple reports contain the same name the first will be selected
   */
  public void clickOnReport(String reportName) {
    TempoReport.getInstance(settings).waitFor(reportName);
    TempoReport.getInstance(settings).click(reportName);
  }

  /**
   * Verifies if report is present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify report | REPORT_NAME or REPORT_NAME[INDEX] | is present |</code>
   * 
   * @param reportName
   *          Name of the report
   * @return True, if report is located
   */
  public boolean verifyReportIsPresent(String reportName) {
    TempoReport.getInstance(settings).waitFor(reportName);
    return true;
  }

  /**
   * Verifies if report is not present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify report | REPORT_NAME or REPORT_NAME[INDEX] | is not present |</code><br>
   * <br>
   * Use this rather than <code>| reject | verify report | REPORT_NAME | is present |</code> as it will not refresh and wait.
   * 
   * @param reportName
   *          Name of the report
   * @return True, if report is not located
   */
  public boolean verifyReportIsNotPresent(String reportName) {
    TempoReport.getInstance(settings).waitForReturn(settings.getNotPresentTimeoutSeconds(), reportName);
    return true;
  }

  /*
   * Actions
   */

  /**
   * Clicks on the associated action.<br>
   * <br>
   * FitNesse Example: <code>| click on action | ACTION_NAME or ACTION_NAME[INDEX] |</code>
   * 
   * @param actionName
   *          Name or Name and index of action to click (partial names are acceptable)
   *          If multiple actions contain the same name the first will be selected
   */
  public void clickOnAction(String actionName) {
    TempoAction.getInstance(settings).waitFor(actionName);
    TempoAction.getInstance(settings).click(actionName);
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
    TempoAction.getInstance(settings).complete();
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
    TempoAction.getInstance(settings).waitFor(actionName);
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
    return !TempoAction.getInstance(settings).waitForReturn(settings.getNotPresentTimeoutSeconds(), actionName);
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
    TempoActionApplicationFilter.getInstance(settings).waitFor(appFilter);
    TempoActionApplicationFilter.getInstance(settings).click(appFilter);
  }

  /**
   * Verifies if action is present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify application filter| APPLICATION_NAME | is present |</code>
   * 
   * @param applicationName
   *          Name of the action
   * @return True, if action is located
   */
  public boolean verifyApplicationFilterIsPresent(String applicationName) {
    TempoAction.getInstance(settings).waitFor(applicationName);
    return true;
  }

  /**
   * Verifies if action is not present in the user interface. This is useful for determining if security is applied correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify application filter| APPLICATION_NAME | is not present |</code><br>
   * <br>
   * Use this rather than <code>| reject | verify application filter | APPLICATION_NAME | is present |</code> as it will not refresh and
   * wait.
   * 
   * @param applicationName
   *          Name of the action
   * @return True, if action is not located
   */
  public boolean verifyApplicationFilterIsNotPresent(String applicationName) {
    return !TempoAction.getInstance(settings).waitForReturn(settings.getNotPresentTimeoutSeconds(), applicationName);
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
   * @return The title string
   */
  public String getFormTitle() {
    TempoFormTitle.getInstance(settings).waitFor();
    return TempoFormTitle.getInstance(settings).capture();
  }

  /**
   * Returns a string that matches the regex, this could be useful in extracting a system generated value from a form's title.<br>
   * <br>
   * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from form title |</code>
   * 
   * @param regex
   *          Regular expression string to search for within the form
   * @param group
   *          Regular expression group to return
   * @return String that matches the regular expression
   */
  public String getRegexGroupFromFormTitle(String regex, Integer group) {
    TempoFormTitle.getInstance(settings).waitFor();
    return TempoFormTitle.getInstance(settings).regexCapture(regex, group);
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
    TempoFormInstructions.getInstance(settings).waitFor();
    return TempoFormInstructions.getInstance(settings).capture();
  }

  /**
   * Populates a field with specific values.<br>
   * <br>
   * FitNesse Example: <code>| populate field | FIELD_LABEL or [INDEX] or FIELD_LABEL[INDEX] | with | VALUE(S) |</code>
   * 
   * @param fieldName
   *          Can either be the label of the field or a label with an index
   * @param fieldValues
   *          An array of strings containing the values to populate into the interface
   */
  public void populateFieldWith(String fieldName, String[] fieldValues) {
    TempoField.getInstance(settings).waitFor(fieldName);
    TempoFieldFactory.getInstance(settings).populateMultiple(fieldValues, fieldName);
  }

  /**
   * Populates a field of a particular type with specific values.<br>
   * <br>
   * FitNesse Example: <code>| populate | FIELD_TYPE | field | [FIELD_INDEX] | with | VALUE(S) ||</code><br>
   * 
   * @param fieldType
   *          Can only currently accept TEXT, PARAGRAPH, and FILE_UPLOAD
   * @param fieldName
   *          Can either be the label of the field or a label with an index
   * @param fieldValues
   *          An array of strings containing the values to populate into the interface
   */
  public void populateFieldWith(String fieldType, String fieldName, String[] fieldValues) {
    TempoFieldFactory.getInstance(settings).waitFor(fieldType, fieldName);
    TempoFieldFactory.getInstance(settings).populateMultiple(fieldValues, fieldType, fieldName);
  }

  /**
   * Populates a field with a single value that may contain a comma. This is useful is selecting an option that contains a comma.<br>
   * <br>
   * FitNesse Example: <code>| populate field | FIELD_LABEL or [INDEX] or FIELD_LABEL[INDEX] | with value | VALUE |</code>
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
   * FitNesse Example: <code>| populate field | FIELD_LABEL OR [FIELD_INDEX] | in section | SECTION_NAME | with | VALUE(S) |</code> <br>
   * 
   * @param fieldName
   *          Can either be the label of the field or label with an index
   * @param sectionName
   *          Can either be the label of the section or an label with an index
   * @param fieldValues
   *          An array of strings containing the values to populate into the interface
   */
  public void populateFieldInSectionWith(String fieldName, String sectionName, String[] fieldValues) {
    TempoSection.getInstance(settings).waitFor(fieldName, sectionName);
    TempoSectionField.getInstance(settings).populateMultiple(fieldValues, fieldName, sectionName);
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
  @Deprecated
  public void expandSection(String sectionName) {
    TempoSectionToggle.getInstance(settings).waitFor(sectionName);
    TempoSectionToggle.getInstance(settings).click(sectionName);
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
  @Deprecated
  public void collapseSection(String sectionName) {
    TempoSectionToggle.getInstance(settings).waitFor(sectionName);
    TempoSectionToggle.getInstance(settings).click(sectionName);
  }

  /**
   * Expand or collapse a section.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| toggle section | SECTION_NAME | visibility |</code><br>
   * 
   * @param sectionName
   *          Label of the section
   */
  public void toggleSectionVisibility(String sectionName) {
    TempoSectionToggle.getInstance(settings).waitFor(sectionName);
    TempoSectionToggle.getInstance(settings).click(sectionName);
  }

  /**
   * Used to clear a field.<br>
   * <br>
   * This method currently works for <br>
   * FitNesse Example: <code>| clear field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] |</code><br>
   * 
   * @param fieldName
   *          Field to clear
   */
  public void clearField(String fieldName) {
    TempoField.getInstance(settings).waitFor(fieldName);
    TempoFieldFactory.getInstance(settings).clear(fieldName);
  }

  /**
   * Used to clear a field of specific values.<br>
   * <br>
   * This method is only useful for picker objects to unselect an item. <br>
   * FitNesse Example: <code>| clear field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | of | VALUES |</code><br>
   * 
   * @param fieldName
   *          Field to clear
   * @param fieldValues
   *          Values to unselect
   */
  public void clearFieldOf(String fieldName, String[] fieldValues) {
    TempoField.getInstance(settings).waitFor(fieldName);
    TempoFieldFactory.getInstance(settings).clearOf(fieldValues, fieldName);
  }

  /**
   * Returns the value of a field.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | value |</code> - Simply returns a string<br>
   * <code>| $fieldValue= | get field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | value | </code> - Stores the field value in
   * fieldValue, which can later be accessed
   * using $fieldValue<br>
   * <code>| check | get field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | FIELD_VALUE |</code> - Returns true if the field value
   * title matches the FIELD_VALUE input.
   * For file upload fields, do not include the full path. This will not work for relative date and datetime fields.
   * Image fields return the alt text of the image.
   * 
   * @param fieldName
   *          Name of name and index of the field
   * @return The field value
   */
  public String getFieldValue(String fieldName) {
    TempoField.getInstance(settings).waitFor(fieldName);
    return TempoFieldFactory.getInstance(settings).capture(fieldName);
  }

  /**
   * Returns a string that matches the regex, this could be useful in extracting a system generated value from a field's value.<br>
   * <br>
   * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from field | FIELD_NAME | value |</code>
   * 
   * @param regex
   *          Regular expression string to search for within the form
   * @param group
   *          Regular expression group to return
   * @param fieldName
   *          Name of field
   * @return String that matches the regular expression
   */
  public String getRegexGroupFromFieldValue(String regex, Integer group, String fieldName) {
    TempoField.getInstance(settings).waitFor(fieldName);
    return TempoFieldFactory.getInstance(settings).regexCapture(regex, group, fieldName);

  }

  /**
   * Returns the value of a field in a section.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get field | FIELD_LABEL or [FIELD_INDEX] | in section | SECTION_NAME | value |</code> - Simply returns a string<br>
   * <code>| $fieldValue= | get field | FIELD_LABEL or [FIELD_INDEX] | in section | SECTION_NAME | value |</code> - Stores the field value
   * in fieldValue,
   * which can later be accessed using $fieldValue<br>
   * <code>| check | get field | FIELD_LABEL or [FIELD_INDEX] | in section | SECTION_NAME | value | FIELD_VALUE |</code> - Returns true if
   * the field value
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
    TempoSection.getInstance(settings).waitFor(fieldName, sectionName);
    return TempoSectionField.getInstance(settings).capture(fieldName, sectionName);
  }

  /**
   * Returns a string that matches the regex, this could be useful in extracting a system generated value from a field's value in a
   * specified section.<br>
   * <br>
   * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from field | FIELD_NAME | in section | SECTION_NAME | </code>
   * 
   * @param regex
   *          Regular expression string to search for within the form
   * @param group
   *          Regular expression group to return
   * @param fieldName
   *          Name of field
   * @param sectionName
   *          Section label or label and index
   * @return String that matches the regular expression
   */
  public String getRegexGroupFromFieldInSectionValue(String regex, Integer group, String fieldName, String sectionName) {
    TempoSectionField.getInstance(settings).waitFor(fieldName, sectionName);
    return TempoSectionField.getInstance(settings).regexCapture(regex, group, fieldName, sectionName);
  }

  /**
   * Verifies a field contains a specific value.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| verify field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | contains | VALUES() |</code> - For
   * date and datetime fields, relative times can be entered such as +1 minute, +2 hours, +3 days. To use these relative times, the
   * startDatetime must be initialized: see {@link com.appiancorp.ps.automatedtest.fixture.BaseFixture#setStartDatetime()} Image fields
   * return the alt text of the image.
   * 
   * @param fieldName
   *          Can either be a name or a name and index, e.g. 'Text Field' or 'Text Field[1]'
   * @param fieldValues
   *          Values to compare field value against
   * @return True, if the field contains the value
   */
  public boolean verifyFieldContains(String fieldName, String[] fieldValues) {
    TempoField.getInstance(settings).waitFor(fieldName);
    return TempoFieldFactory.getInstance(settings).containsMultiple(fieldValues, fieldName);
  }

  /**
   * Verifies a field with a single value that may contain a comma. This is useful in verifying that an option that contains a comma has
   * been selected.<br>
   * <br>
   * FitNesse Example: <code>| verify field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | contains value | VALUE |</code>
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
   * <code>| verify field | FIELD_NAME or [FIELD_INDEX] | in section | SECTION_NAME | contains | VALUES |</code><br>
   * <code>| verify field | FIELD_NAME or [FIELD_INDEX] | in section | SECTION_NAME | contains | +4 hours |</code> - For date and datetime
   * fields, relative
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
    TempoSection.getInstance(settings).waitFor(fieldName, sectionName);
    return TempoSectionField.getInstance(settings).containsMultiple(fieldValues, fieldName, sectionName);
  }

  /**
   * Verifies a field contains a validation message.<br>
   * <br>
   * FitNesse Example: <code>| verify field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | contains validation messages |</code>
   * 
   * @param fieldName
   *          Field label or label and index
   * @param validationMessages
   *          Validation messages, separated by a comma.
   * @return True, if fields contains value
   */
  public boolean verifyFieldContainsValidationMessage(String fieldName, String[] validationMessages) {
    TempoFieldValidation.getInstance(settings).waitForMultiple(validationMessages, fieldName);
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
    TempoField.getInstance(settings).waitFor(fieldName);
    return TempoFieldValidation.getInstance(settings).capture(fieldName);
  }

  /**
   * Verifies if field is displayed in the interface. This is useful to test dynamic forms.<br>
   * <br>
   * FitNesse Example: <code>| verify field | FIELD_LABEL or [FIELD_INEDX] or FIELD_LABEL[INDEX] | is present |</code>
   * 
   * @param fieldName
   *          Field name to find in interface
   * @return True, if field is located in the interface
   */
  public boolean verifyFieldIsPresent(String fieldName) {
    TempoField.getInstance(settings).waitFor(fieldName);
    return true;
  }

  /**
   * Verifies if field is not displayed in the interface. This is useful to test dynamic forms.<br>
   * <br>
   * FitNesse Example: <code>| verify field | FIELD_LABEL or [FIELD_INEDX] or FIELD_LABEL[INDEX] | is not present |</code>
   * 
   * @param fieldName
   *          Field name to not find in interface
   * @return True, if field is not located in the interface
   */
  public boolean verifyFieldIsNotPresent(String fieldName) {
    return !TempoField.getInstance(settings).waitForReturn(settings.getNotPresentTimeoutSeconds(), fieldName);
  }

  /**
   * Populates fields in a grid. This method is useful for populating the following types of fields: Text, Paragraph, EncryptedText,
   * Integer, Decimal, Date, Datetime, Select, MultipleSelect, Radio, Checkbox, FileUpload, UserPicker, GroupPicker, UserGroupPicker,
   * DocumentPicker, FolderPicker, DocumentFolderPicker, CustomPicker.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| populate grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | column | COLUMN_NAME or [COLUMN_INDEX] | row | [ROW_INDEX] | with | VALUE |</code>
   * <br>
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
    TempoGridCell.getInstance(settings).waitFor(gridName, columnName, rowNum);
    TempoGridCell.getInstance(settings).populateMultiple(fieldValues, gridName, columnName, rowNum);
  }

  /**
   * Returns the value of a field.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | column | COLUMN_NAME or [COLUMN_INDEX] | row | [ROW_INDEX] | value |</code>
   * - Simply returns a string<br>
   * <code>| $fieldValue= | get grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | value | </code> - Stores
   * the field value in fieldValue, which can later be accessed using $fieldValue<br>
   * <code>| check | get grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | value | FIELD_VALUE |</code> -
   * Returns true if the field value title matches the FIELD_VALUE input. For file upload fields, do not include the full path. This will
   * not work for relative date and datetime fields. Image field cells return the alt text of the image.
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
    TempoGridCell.getInstance(settings).waitFor(gridName, columnName, rowNum);
    return TempoGridCell.getInstance(settings).capture(gridName, columnName, rowNum);
  }

  /**
   * Returns a string that matches the regex, this could be useful in extracting a system generated value from a specific grid cell.<br>
   * <br>
   * FitNesse Example:
   * <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from grid | GRID_NAME | cell in column | COLUMN_NAME_OR_INDEX | Row | [Row_Number] | value | </code>
   * 
   * @param regex
   *          Regular expression string to search for within the form
   * @param group
   *          Regular expression group to return
   * @param gridName
   *          Name of grid
   * @param columnName
   *          Name or index of column to retrieve the cell from
   * @param rowNum
   *          Index of column to retrieve the cell from
   * @return String that matches the regular expression
   */
  public String getRegexGroupFromGridColumnRowValue(String regex, Integer group, String gridName, String columnName, String rowNum) {
    TempoGrid.getInstance(settings).waitFor(gridName, columnName, rowNum);
    return TempoGridCell.getInstance(settings).regexCapture(regex, group, gridName, columnName, rowNum);
  }

  /**
   * Verifies a field contains a specific value.<br>
   * <br>
   * FitNesse Example:
   * <code>| verify grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | column | COLUMN_NAME or [COLUMN_INDEX] | row | [ROW_INDEX] | contains | VALUES |</code>
   * Image field cells return the alt text of the image.
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
    TempoGridCell.getInstance(settings).waitFor(gridName, columnName, rowNum);
    return TempoGridCell.getInstance(settings).containsMultiple(fieldValues, gridName, columnName, rowNum);
  }

  /**
   * Selects a row in an editable or paging grid.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| select grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | row | [1] |</code><br>
   * 
   * @param gridName
   *          Can either be the grid name or grid name with index, e.g. 'Grid Name' or 'Grid Name[2]'
   * @param rowNum
   *          Index of row to select, e.g. [2]
   */
  public void selectGridRow(String gridName, String rowNum) {
    TempoGrid.getInstance(settings).waitFor(gridName, rowNum);
    TempoGridRow.getInstance(settings).click(gridName, rowNum);
  }

  /**
   * Verifies if a grid row is selected<br>
   * <br>
   * FitNesse Example: <code>| verify grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | row | ROW_NUMBER | is selected |</code>
   * 
   * @param gridName
   *          Name or name and index of grid
   * @param rowNum
   *          Row number
   * @return True, if row is selected
   */
  public boolean verifyGridRowIsSelected(String gridName, String rowNum) {
    TempoGrid.getInstance(settings).waitFor(gridName, rowNum);
    return TempoGridRow.getInstance(settings).contains(gridName, rowNum);
  }

  /**
   * Count rows in an editable or paging grid.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| count grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | rows |</code><br>
   * 
   * @param gridName
   *          Can either be the grid name or grid name with index, e.g. 'Grid Name' or 'Grid Name[2]'
   */
  public Integer countGridRows(String gridName) {
    TempoGrid.getInstance(settings).waitFor(gridName);
    return TempoGridRow.getInstance(settings).count(gridName);
  }

  /**
   * Returns the value of a field.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get grid | GRID_NAME_OR_INDEX | total count |</code> - Simply returns the chosen grid's total count<br>
   * <code>| $gridTotalCount= | get grid | GRID_NAME_OR_INDEX | total count | </code> - Stores
   * the grid's total count value in $gridTotalCount<br>
   * 
   * @param gridName
   *          Name or index of the grid
   * @return The grid's total count
   */
  public int getGridTotalCount(String gridName) {
    TempoGrid.getInstance(settings).waitFor(gridName);
    return TempoGridTotalCount.getInstance(settings).capture(gridName);
  }

  /**
   * Returns the value of a field.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get grid | GRID_NAME_OR_INDEX | row count |</code> - Simply returns the chosen grid's current page row count<br>
   * <code>| $gridRowCount= | get grid | GRID_NAME_OR_INDEX | row count | </code> - Stores
   * the grid's row count value in $gridRowCount<br>
   * 
   * @param gridName
   *          Name or index of the grid
   * @return The grid's current page row count
   */
  public int getGridRowCount(String gridName) {
    TempoGrid.getInstance(settings).waitFor(gridName);
    return TempoGridRowCount.getInstance(settings).capture(gridName);
  }

  /**
   * Clicks on the add row link for a grid<br>
   * <br>
   * FitNesse Example: <code>| click on grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | add row link |</code>
   * 
   * @param gridName
   *          Name or name and index of grid
   */
  public void clickOnGridAddRowLink(String gridName) {
    TempoGrid.getInstance(settings).waitFor(gridName);
    TempoGridAddRow.getInstance(settings).click(gridName);
  }

  /**
   * Clicks on the page link below a paging grid<br>
   * <br>
   * FitNesse Example: <code>| click on grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | navigation | NAV_REFERENCE |</code> - nav
   * reference only takes "first",
   * previous, next, or "last"
   * 
   * @param gridName
   *          Name or name and index of grid
   * @param navOption
   *          "first", previous, next, or "last"
   */

  public void clickOnGridNavigation(String gridName, String navOption) {
    TempoGrid.getInstance(settings).waitFor(gridName);
    TempoGridNavigation.getInstance(settings).click(gridName, navOption);
  }

  /**
   * Select all rows in a grid<br>
   * <br>
   * FitNesse Example: <code>| select all rows in grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] |</code>
   * 
   * @param gridName
   *          Name or name and index of grid
   */

  public void selectAllRowsInGrid(String gridName) {
    TempoGridSelectAll.getInstance(settings).waitFor(gridName);
    TempoGridSelectAll.getInstance(settings).click(gridName);
  }

  /**
   * Sort a grid by a column<br>
   * <br>
   * FitNesse Example:
   * <code>| sort grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | by column | COLUMN_NAME or [COLUMN_INDEX] |</code> -
   * 
   * @param gridName
   *          Name or name and index of grid
   * @param columnName
   *          Name or index of column
   */
  public void sortGridByColumn(String gridName, String columnName) {
    TempoGrid.getInstance(settings).waitFor(gridName);
    TempoGridColumn.getInstance(settings).click(gridName, columnName);
  }

  /**
   * Clicks on the first link that matches the linkName.<br>
   * <br>
   * FitNesse Example: <code>| click on link | LINK_NAME or LINK_NAME[INDEX] |</code>
   * 
   * @param linkName
   *          Name of link to click
   */
  public void clickOnLink(String linkName) {
    TempoLinkField.getInstance(settings).waitFor(linkName);
    TempoLinkField.getInstance(settings).click(linkName);
  }

  /**
   * Verifies there is a link with the specified name.
   * The method will wait for the timeout period and refresh up to the configured number of refresh times before failing.<br>
   * <br>
   * FitNesse Example: <code>| verify link | LINK_NAME or LINK_NAME[INDEX] | is present |</code>
   * 
   * @param linkName
   *          Name of link to verify is present
   * @return True, if link field is located
   */
  public boolean verifyLinkIsPresent(String linkName) {
    TempoLinkField.getInstance(settings).waitFor(linkName);
    return true;
  }

  /**
   * Verifies if a link field's URL contains a specific value.<br>
   * <br>
   * FitNesse Example: <code>| verify link | LINK_NAME or LINK_NAME[INDEX] | URL contains | URL_TEXT |</code>
   * 
   * @param linkName
   *          Name of link to look for URL
   * @param URLText
   *          Values to verify that the link URL contains
   * @return True, if the link URL does contain the value
   */
  public boolean verifyLinkURLContains(String linkName, String URLText) {
    TempoLinkField.getInstance(settings).waitFor(linkName);
    return TempoLinkFieldUrl.getInstance(settings).contains(linkName, URLText);
  }

  /**
   * Returns the URL of a link field.<br>
   * <br>
   * FitNesse example: <code>| $VARIABLE_NAME= | get link | LINK_NAME or LINK_NAME[INDEX] | URL |</code> Use $VARIABLE_NAME to access the
   * variable
   * containing the link URL of the link field specified
   * 
   * @param linkName
   *          Name of link
   * @return Link URL
   */
  public String getLinkURL(String linkName) {
    return TempoLinkFieldUrl.getInstance(settings).capture(linkName);
  }

  /**
   * Clicks on the first button that matches the buttonName.<br>
   * <br>
   * FitNesse Example: <code>| click on button | BUTTON_NAME or BUTTON_NAME[INDEX] |</code>
   * 
   * @param buttonName
   *          Name of button to click
   */
  public void clickOnButton(String buttonName) {
    TempoButton.getInstance(settings).waitFor(buttonName);
    TempoButton.getInstance(settings).click(buttonName);
  }

  /**
   * Verifies if button with given label is present in the user interface. This is useful for determining if conditionals to show buttons
   * have worked correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify button | BUTTON_NAME or BUTTON_NAME[INDEX] | is present |</code>
   * 
   * @param buttonName
   *          Name of the button
   * @return True, if button is located
   */
  public boolean verifyButtonIsPresent(String buttonName) {
    TempoButton.getInstance(settings).waitFor(buttonName);
    return true;
  }

  /**
   * Verifies if button with given label is not present in the user interface. This is useful for determining if conditionals to hide
   * buttons have worked correctly.<br>
   * <br>
   * FitNesse Example: <code>| verify button with label | BUTTON_NAME or BUTTON_NAME[INDEX] | is not present |</code><br>
   * <br>
   * Use this rather than <code>| reject | verify button | BUTTON_NAME or BUTTON_NAME[INDEX] | is present |</code> as it will not refresh
   * and wait.
   * 
   * @param buttonName
   *          Name of the button
   * @return True, if button is not located
   */
  public boolean verifyButtonIsNotPresent(String buttonName) {
    return !TempoButton.getInstance(settings).waitForReturn(settings.getNotPresentTimeoutSeconds(), buttonName);
  }

  /**
   * Clicks on the save changes link.<br>
   * <br>
   * FitNesse Example: <code>| click on button | BUTTON_NAME or BUTTON_NAME[INDEX] |</code>
   * 
   * @param buttonName
   *          Name of button to click
   */
  public void clickOnSaveChanges() {
    TempoSaveChanges.getInstance(settings).waitFor();
    TempoSaveChanges.getInstance(settings).click();
  }

  /**
   * Clicks on a milestone step.<br>
   * <br>
   * FitNesse Example: <code>| click on milestone | MILESTONE or MILESTONE[INDEX] | step | STEP or [INDEX] |</code>
   * 
   * @param milestone
   *          Name or Name[Index] of milestone
   * @param step
   *          Name or [Index] of step
   */
  public void clickOnMilestoneStep(String milestone, String step) {
    TempoFieldFactory.getInstance(settings).waitFor("MILESTONE", milestone);
    TempoMilestoneFieldStep.getInstance(settings).click(milestone, step);
  }

  /**
   * Verifies if a milestone is currently on a particular step.<br>
   * <br>
   * FitNesse Example: <code>| verify milestone | MILESTONE or MILESTONE[INDEX] | step is | STEP or [INDEX] |</code>
   * 
   * @param milestone
   *          Name or Name[Index] of milestone
   * @param step
   *          Name or [Index] of step
   * @return True, if the current step matches
   */
  public boolean verifyMilestoneStepIs(String milestone, String[] step) {
    TempoFieldFactory.getInstance(settings).waitFor(milestone);
    TempoFieldFactory.getInstance(settings).containsMultiple(step, milestone);
    return true;
  }

  /**
   * Verifies if a milestone is currently on a particular step.<br>
   * <br>
   * FitNesse Example: <code>| get milestone | MILESTONE or MILESTONE[INDEX] | step |</code>
   * 
   * @param milestone
   *          Name or Name[Index] of milestone
   * @return Milestone step
   */
  public String getMilestoneStep(String milestone) {
    TempoFieldFactory.getInstance(settings).waitFor(milestone);
    return TempoFieldFactory.getInstance(settings).capture(milestone);
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
    TempoRadioFieldOption.getInstance(settings).waitFor(optionName);
    TempoRadioFieldOption.getInstance(settings).click(optionName);
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
    TempoCheckboxFieldOption.getInstance(settings).waitFor(optionName);
    TempoCheckboxFieldOption.getInstance(settings).click(optionName);
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
    TempoSectionValidation.getInstance(settings).waitForMultiple(validationMessages, sectionName);
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
    TempoSection.getInstance(settings).waitFor(sectionName);
    return TempoSectionValidation.getInstance(settings).capture(sectionName);
  }

  /**
   * Verifies a chart containing a specific label is present.
   * The method will wait for the timeout period and refresh up to the configured number of refresh times before failing.<br>
   * <br>
   * FitNesse Example: <code>| verify chart | CHART_LABEL | is present |</code>
   * 
   * @param chartLabel
   *          Text to search for chart label
   * @return True, if post is located with specific text
   */
  public boolean verifyChartIsPresent(String chartLabel) {
    TempoChart.getInstance(settings).waitFor(chartLabel);
    return true;
  }

  /**
   * Verifies a chart containing a specific label is not present.<br>
   * <br>
   * FitNesse Example: <code>| verify chart| CHART_LABEL | is not present |</code><br>
   * <br>
   * The reason to use than <code>| reject | verify chart | CHART_LABEL | is present |</code> is that this method will not
   * refresh and wait.
   * 
   * @param chartLabel
   *          Text to search for chart label
   * @return True, if no post is located with specific text
   */
  public boolean verifyChartIsNotPresent(String chartLabel) {
    return !TempoChart.getInstance(settings).waitForReturn(chartLabel);
  }

  /**
   * Returns true if there is an error on tempo
   * 
   * @return
   */
  public boolean errorIsPresent() {
    return TempoError.getInstance(settings).waitForReturn();
  }
}