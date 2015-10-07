package com.appiancorp.ps.automatedtest.fixture;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.fields.TempoAction;
import com.appiancorp.ps.automatedtest.fields.TempoButton;
import com.appiancorp.ps.automatedtest.fields.TempoDateField;
import com.appiancorp.ps.automatedtest.fields.TempoDatetimeField;
import com.appiancorp.ps.automatedtest.fields.TempoFileUploadField;
import com.appiancorp.ps.automatedtest.fields.TempoGrid;
import com.appiancorp.ps.automatedtest.fields.TempoField;
import com.appiancorp.ps.automatedtest.fields.TempoIntegerField;
import com.appiancorp.ps.automatedtest.fields.TempoLinkField;
import com.appiancorp.ps.automatedtest.fields.TempoLogin;
import com.appiancorp.ps.automatedtest.fields.TempoMenu;
import com.appiancorp.ps.automatedtest.fields.TempoNews;
import com.appiancorp.ps.automatedtest.fields.TempoParagraphField;
import com.appiancorp.ps.automatedtest.fields.TempoRadioField;
import com.appiancorp.ps.automatedtest.fields.TempoRecordItem;
import com.appiancorp.ps.automatedtest.fields.TempoRecordList;
import com.appiancorp.ps.automatedtest.fields.TempoReport;
import com.appiancorp.ps.automatedtest.fields.TempoSelectField;
import com.appiancorp.ps.automatedtest.fields.TempoTask;
import com.appiancorp.ps.automatedtest.fields.TempoTextField;
import com.appiancorp.ps.automatedtest.fields.TempoUserPickerField;

public class AppianFitnesseProcessTempoFixture extends AppianFitnesseProcessBaseFixture {
	    
	public AppianFitnesseProcessTempoFixture() {
		super();
	}
	
	/** TEMPO **/
	
	// Tempo Menu
	public boolean clickOnTempoMenu(String tempoMenu) {
		if(!TempoMenu.waitFor(tempoMenu)) return false;
		
		return TempoMenu.click(tempoMenu);
	}
	
	// Logout
	public boolean logoutFromTempo() {
        if(!TempoLogin.waitForLogout()) return false;
        
        return TempoLogin.logout();
    }
	
	/** NEWS **/
    
    public boolean verifyNewsFeedContainingTextIsPresent(String newsText) {
        return TempoNews.refreshAndWaitFor(newsText);
    }
    
    public boolean verifyNewsFeedContainingTextIsNotPresent(String newsText) {
        return !TempoNews.waitFor(newsText);
    }
    
    public boolean toggleMoreInfoForNewsFeedContainingText(String newsText) {
        if(!TempoNews.waitForMoreInfo(newsText)) return false;
        
        return TempoNews.toggleMoreInfo(newsText);
    }
    
    public boolean verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(String newsText, String label, String value) {     
        if (!TempoNews.refreshAndWaitFor(newsText)) return false;
        
        return TempoNews.waitForLabelAndValue(newsText, label, value);
    }
    
    public boolean verifyNewsFeedContainingTextAndMoreInfoWithLabelsAndValuesArePresent(String newsText, String[] labels, String[] values) {        
        return TempoNews.waitForLabelsAndValues(newsText, labels, values);
    }
    
    public boolean verifyNewsFeedContainingTextTaggedWithIsPresent(String newsText, String newsTag) {
        if (!TempoNews.refreshAndWaitFor(newsText)) return false;
        
        return TempoNews.waitForTag(newsText, newsTag);
    }
    
    public boolean verifyNewsFeedContainingTextCommentedWithIsPresent(String newsText, String newsComment) {
        return TempoNews.refreshAndWaitForComment(newsText, newsComment);
    }
    
    /** TASKS **/
    
    public boolean clickOnTempoTask(String taskName) {
       if(!TempoTask.refreshAndWaitFor(taskName)) return false;
       
       return TempoTask.click(taskName);
    }
    
    public boolean verifyTempoTaskIsPresent(String taskName) {
        return TempoTask.refreshAndWaitFor(taskName);
    }
    
    public boolean verifyTempoTaskIsNotPresent(String taskName) {
        return !TempoTask.waitFor(taskName);
    }
    
    public boolean verifyTempoTaskHasDeadlineOf(String taskName, String deadline) {
        if(!TempoTask.waitFor(taskName)) return false;
        
        return TempoTask.hasDeadlineOf(taskName, deadline);
    }
    
    /** RECORDS **/
    
    // Record Item
    public boolean clickOnTempoRecordItem(String itemName) {
        if(!TempoRecordItem.refreshAndWaitFor(itemName)) return false;
        
        return TempoRecordItem.click(itemName);
    }
    
    public boolean verifyTempoRecordItemIsPresent(String itemName) {
        return TempoRecordItem.waitFor(itemName);
    }
    
    public boolean verifyTempoRecordItemIsNotPresent(String itemName) {
        return !TempoRecordItem.waitFor(itemName);
    }
    
    public boolean clickOnTempoRecordItemFacet(String facetName) {
        if(!TempoRecordItem.waitForFacet(facetName)) return false;
        
        return TempoRecordItem.clickOnFacet(facetName);
    }
    
    public boolean clickOnTempoRecordItemRelatedAction(String relatedActionName) {
        if(!TempoRecordItem.refreshAndWaitForRelatedAction(relatedActionName)) return false;
        
        return TempoRecordItem.clickOnRelatedAction(relatedActionName);
    }
    
    public boolean verifyTempoRecordItemRelatedActionIsPresent(String relatedActionName) {
        return TempoRecordItem.refreshAndWaitForRelatedAction(relatedActionName);
    }
    
    public boolean verifyTempoRecordItemRelatedActionIsNotPresent(String relatedActionName) {
        return !TempoRecordItem.waitForRelatedAction(relatedActionName);
    }
    
    // Record List  
    public boolean clickOnTempoRecordList(String listName) {
        if(!TempoRecordList.waitFor(listName)) return false;
        
        return TempoRecordList.click(listName);
    }
    
    public boolean clickOnTempoRecordListFacetOption(String facetName) {
        if(!TempoRecordList.waitForFacetOption(facetName)) return false;
        
        return TempoRecordList.clickOnFacetOption(facetName);       
    }
    
    public boolean verifyTempoRecordListFacetOptionIsPresent(String facetName) {
        return TempoRecordList.waitForFacetOption(facetName);
    }
    
    /** REPORTS **/
    
    public boolean clickOnTempoReport(String reportName) {
        if(!TempoReport.waitFor(reportName)) return false;
        
        return TempoReport.click(reportName);
    }
	
    /** ACTIONS **/
    
	public boolean clickOnTempoAction(String actionName) {
        if(!TempoAction.waitFor(actionName)) return false;
        
        return TempoAction.click(actionName);
    }
	
	public boolean verifyTempoActionCompleted() {
	    return TempoAction.isCompleted();
	}
	
	/** FIELDS **/
	
	// Generic Field
	public boolean populateTempoFieldWith(String fieldName, String[] fieldValues) {
	    if(!TempoField.waitFor(fieldName)) return false;
	    
	    TempoField.populate(fieldName, fieldValues);
        
	    int attempt = 0;
	    
	    while(attempt < 2) {
	        if (TempoField.contains(fieldName, fieldValues)) {
	            new Actions(driver).sendKeys(Keys.TAB).perform();
	            return true;
	        }
	        
	        TempoField.waitFor(fieldName);
	        TempoField.clear(fieldName);
	        TempoField.populate(fieldName, fieldValues);
	        attempt++;
	    }
	    
	    return false;
	}
	
	public boolean clearTempoFieldOf(String fieldName, String[] fieldValues) {
	    if(!TempoField.waitFor(fieldName)) return false;
        
        return TempoField.clearOf(fieldName, fieldValues);
	}
	
	public boolean verifyTempoFieldContains(String fieldName, String[] fieldValues) {
        if(!TempoField.waitFor(fieldName)) return false;
        
        return TempoField.contains(fieldName, fieldValues);
    }
	
	// Text Field
    public boolean populateTempoTextFieldWith(String fieldName, String fieldValue) {
        if(!TempoTextField.waitFor(fieldName)) return false;
        
        return TempoTextField.populate(fieldName, fieldValue);
    }
    
	// Paragraph Field
	public boolean populateTempoParagraphFieldWith(String fieldName, String fieldValue) {
	    if(!TempoParagraphField.waitFor(fieldName)) return false;
        
        return TempoParagraphField.populate(fieldName, fieldValue);
	}
	
	// Integer Field
    public boolean populateTempoIntegerFieldWith(String fieldName, String fieldValue) {
        if(!TempoIntegerField.waitFor(fieldName)) return false;
        
        return TempoIntegerField.populate(fieldName, fieldValue);
    }
    
    // Radio Field
    public boolean populateTempoRadioFieldWith(String fieldName, String fieldValue) {
        if(!TempoRadioField.waitFor(fieldName)) return false;
        
        return TempoRadioField.populate(fieldName, fieldValue);  
    }
    
    // Select Field
    public boolean populateTempoSelectFieldWith(String fieldName, String fieldValue) {
        if(!TempoSelectField.waitFor(fieldName)) return false;
        
        return TempoSelectField.populate(fieldName, fieldValue);  
    }
    
    // Group Picker Field   
    public boolean populateTempoUserPickerFieldWith(String fieldName, String[] fieldValues) { 
        if(!TempoUserPickerField.waitFor(fieldName)) return false;
        
        return TempoUserPickerField.populate(fieldName, fieldValues);      
    }
    
    // Datetime Field
    public boolean populateTempoDatetimeFieldWithDateAndTime(String fieldName, String fieldValue) {
        if(!TempoDatetimeField.waitFor(fieldName)) return false;
        
        return TempoDatetimeField.populate(fieldName, fieldValue);  
    }
    
    // Date Field
    public boolean populateTempoDateFieldWith(String fieldName, String fieldValue) {
        if(!TempoDateField.waitFor(fieldName)) return false;
        
        return TempoDateField.populate(fieldName, fieldValue);  
    }
    
    // File Upload Field
    public boolean populateTempoFileUploadFieldWith(String fieldName, String fieldValue) {
        //if(!TempoFileUpload.waitFor(fieldName)) return false;
        
        return TempoFileUploadField.populate(fieldName, fieldValue);  
    }
    
    // Grid Field   
    public boolean populateTempoEditableGridColumnRowWith(String gridName, String columnName, String rowNum, String[] fieldValues) {
        if(!TempoGrid.waitFor(gridName, columnName, rowNum)) return false;
        
        TempoGrid.populate(gridName, columnName, rowNum, fieldValues);
        
        int attempt = 0;
        
        while(attempt < 2) {
            if (TempoGrid.contains(gridName, columnName, rowNum, fieldValues)) {
                new Actions(driver).sendKeys(Keys.TAB).perform();
                return true;
            }
            
            TempoGrid.waitFor(gridName, columnName, rowNum);
            TempoGrid.clear(gridName, columnName, rowNum);
            TempoGrid.populate(gridName, columnName, rowNum, fieldValues);
            attempt++;
        }
        
        return false;
    }
    
    public boolean verifyTempoEditableGridColumnRowContains(String gridName, String columnName, String rowNum, String[] fieldValues) {
        if(!TempoGrid.waitFor(gridName, columnName, rowNum)) return false;
        
        return TempoGrid.contains(gridName, columnName, rowNum, fieldValues); 
    }
	
    // Link
    public boolean clickOnTempoLink(String linkName) {
        if(!TempoLinkField.waitFor(linkName)) return false;
        
        return TempoLinkField.click(linkName); 
    }
    
    public boolean clickTempoLink(String linkName) {
        return clickOnTempoLink(linkName); 
    }
    
	// Button	
	public boolean clickOnTempoButton(String buttonName) {
		if (!TempoButton.waitFor(buttonName)) return false;
		
		return TempoButton.click(buttonName);
	}
	
	public boolean clickTempoButton(String buttonName) {
        return clickTempoButton(buttonName);
    }
}
 ;