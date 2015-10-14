package com.appiancorp.ps.automatedtest.fixture;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

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
	
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(AppianFitnesseProcessTempoFixture.class);
    
	public AppianFitnesseProcessTempoFixture() {
		super();
	}
	
	/** TEMPO **/
	
	// Tempo Menu
	public boolean clickOnTempoMenu(String tempoMenu) {
		if(!TempoMenu.waitFor(tempoMenu)) {
		    throw new MissingObjectException("Tempo Menu", tempoMenu);
		}
		
		return TempoMenu.click(tempoMenu);
	}
	
	// Logout
	public boolean logoutFromTempo() {
        if(!TempoLogin.waitForLogout()) {
            throw new MissingObjectException("Logout Menu");
        }
        
        return TempoLogin.logout();
    }
	
	/** NEWS **/
    
    public boolean verifyTempoNewsFeedContainingTextIsPresent(String newsText) {
        return TempoNews.refreshAndWaitFor(newsText);
    }
    
    public boolean verifyTempoNewsFeedContainingTextIsNotPresent(String newsText) {
        return !TempoNews.waitFor(newsText);
    }
    
    public boolean toggleMoreInfoForNewsFeedContainingText(String newsText) {
        if(!TempoNews.waitForMoreInfo(newsText)) {
            throw new MissingObjectException("News Post with More Info", newsText);
        }
        
        return TempoNews.toggleMoreInfo(newsText);
    }
    
    public boolean verifyTempoNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(String newsText, String label, String value) {     
        if (!TempoNews.refreshAndWaitFor(newsText)) {
            throw new MissingObjectException("News Post", newsText);
        }
        
        return TempoNews.waitForLabelAndValue(newsText, label, value);
    }
    
    public boolean verifyTempoNewsFeedContainingTextAndMoreInfoWithLabelsAndValuesArePresent(String newsText, String[] labels, String[] values) {        
        return TempoNews.waitForLabelsAndValues(newsText, labels, values);
    }
    
    public boolean verifyTempoNewsFeedContainingTextTaggedWithIsPresent(String newsText, String newsTag) {
        if (!TempoNews.refreshAndWaitFor(newsText)) {
            throw new MissingObjectException("News Post", newsText);
        }
        
        return TempoNews.waitForTag(newsText, newsTag);
    }
    
    public boolean verifyTempoNewsFeedContainingTextCommentedWithIsPresent(String newsText, String newsComment) {
        return TempoNews.refreshAndWaitForComment(newsText, newsComment);
    }
    
    /** TASKS **/
    
    public boolean clickOnTempoTask(String taskName) {
       if(!TempoTask.refreshAndWaitFor(taskName)) {
           throw new MissingObjectException("Task", taskName);
       }
       
       return TempoTask.click(taskName);
    }
    
    public boolean verifyTempoTaskIsPresent(String taskName) {
        return TempoTask.refreshAndWaitFor(taskName);
    }
    
    public boolean verifyTempoTaskIsNotPresent(String taskName) {
        return !TempoTask.waitFor(taskName);
    }
    
    public boolean verifyTempoTaskHasDeadlineOf(String taskName, String deadline) {
        if(!TempoTask.waitFor(taskName)) {
            throw new MissingObjectException("Task", taskName);
        }
        
        return TempoTask.hasDeadlineOf(taskName, deadline);
    }
    
    /** RECORDS **/
    
    // Record Item
    public boolean clickOnTempoRecordItem(String itemName) {
        if(!TempoRecordItem.refreshAndWaitFor(itemName)) {
            throw new MissingObjectException("Tempo Record Item", itemName);
        }
        
        return TempoRecordItem.click(itemName);
    }
    
    public boolean verifyTempoRecordItemIsPresent(String itemName) {
        return TempoRecordItem.waitFor(itemName);
    }
    
    public boolean verifyTempoRecordItemIsNotPresent(String itemName) {
        return !TempoRecordItem.waitFor(itemName);
    }
    
    public boolean clickOnTempoRecordItemFacet(String facetName) {
        if(!TempoRecordItem.waitForFacet(facetName)) {
            throw new MissingObjectException("Record Item Facet", facetName);
        }
        
        return TempoRecordItem.clickOnFacet(facetName);
    }
    
    public boolean clickOnTempoRecordItemRelatedAction(String relatedActionName) {
        if(!TempoRecordItem.refreshAndWaitForRelatedAction(relatedActionName)) {
            throw new MissingObjectException("Related Action", relatedActionName);
        }
        
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
        if(!TempoRecordList.waitFor(listName)) {
            throw new MissingObjectException("Record List", listName);
        }
        
        return TempoRecordList.click(listName);
    }
    
    public boolean clickOnTempoRecordListFacetOption(String facetName) {
        if(!TempoRecordList.waitForFacetOption(facetName)) {
            throw new MissingObjectException("Record List Facet", facetName);
        }
        
        return TempoRecordList.clickOnFacetOption(facetName);       
    }
    
    public boolean verifyTempoRecordListFacetOptionIsPresent(String facetName) {
        return TempoRecordList.waitForFacetOption(facetName);
    }
    
    /** REPORTS **/
    
    public boolean clickOnTempoReport(String reportName) {
        if(!TempoReport.waitFor(reportName)) {
            throw new MissingObjectException("Report", reportName);
        }
        
        return TempoReport.click(reportName);
    }
	
    /** ACTIONS **/
    
	public boolean clickOnTempoAction(String actionName) {
        if(!TempoAction.waitFor(actionName)) {
            throw new MissingObjectException("Action", actionName);
        }
        
        return TempoAction.click(actionName);
    }
	
	public boolean verifyTempoActionCompleted() {
	    return TempoAction.isCompleted();
	}
	
	/** FORMS **/
	
	// Generic Field
	public boolean populateTempoFieldWith(String fieldName, String[] fieldValues) {
	    if(!TempoField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
	    TempoField.populate(fieldName, fieldValues);
	    int attempt = 0;
	    
	    while(attempt < 2) {
	        new Actions(driver).sendKeys(Keys.TAB).perform();
	        if (TempoField.contains(fieldName, fieldValues)) {     
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
	    if(!TempoField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoField.clearOf(fieldName, fieldValues);
	}
	
	public boolean verifyTempoFieldContains(String fieldName, String[] fieldValues) {
        if(!TempoField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoField.contains(fieldName, fieldValues);
    }
	
	// Text Field
    public boolean populateTempoTextFieldWith(String fieldName, String fieldValue) {
        if(!TempoTextField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoTextField.populate(fieldName, fieldValue);
    }
    
	// Paragraph Field
	public boolean populateTempoParagraphFieldWith(String fieldName, String fieldValue) {
	    if(!TempoParagraphField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoParagraphField.populate(fieldName, fieldValue);
	}
	
	// Integer Field
    public boolean populateTempoIntegerFieldWith(String fieldName, String fieldValue) {
        if(!TempoIntegerField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoIntegerField.populate(fieldName, fieldValue);
    }
    
    // Radio Field
    public boolean populateTempoRadioFieldWith(String fieldName, String fieldValue) {
        if(!TempoRadioField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoRadioField.populate(fieldName, fieldValue);  
    }
    
    // Select Field
    public boolean populateTempoSelectFieldWith(String fieldName, String fieldValue) {
        if(!TempoSelectField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoSelectField.populate(fieldName, fieldValue);  
    }
    
    // Group Picker Field   
    public boolean populateTempoUserPickerFieldWith(String fieldName, String[] fieldValues) { 
        if(!TempoUserPickerField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoUserPickerField.populate(fieldName, fieldValues);      
    }
    
    // Datetime Field
    public boolean populateTempoDatetimeFieldWith(String fieldName, String fieldValue) {
        if(!TempoDatetimeField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoDatetimeField.populate(fieldName, fieldValue);  
    }
    
    // Date Field
    public boolean populateTempoDateFieldWith(String fieldName, String fieldValue) {
        if(!TempoDateField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoDateField.populate(fieldName, fieldValue);  
    }
    
    // File Upload Field
    public boolean populateTempoFileUploadFieldWith(String fieldName, String fieldValue) {
        if(!TempoFileUploadField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoFileUploadField.populate(fieldName, fieldValue);  
    }
    
    // Grid Field   
    public boolean populateTempoEditableGridColumnRowWith(String gridName, String columnName, String rowNum, String[] fieldValues) {
        if(!TempoGrid.waitFor(gridName, columnName, rowNum)) {
            throw new MissingObjectException("Grid", gridName);
        }
        
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
        if(!TempoGrid.waitFor(gridName, columnName, rowNum)) {
            throw new MissingObjectException("Grid", gridName);
        }
        
        return TempoGrid.contains(gridName, columnName, rowNum, fieldValues); 
    }
	
    // Link
    public boolean clickOnTempoLink(String linkName) {
        if(!TempoLinkField.waitFor(linkName)) {
            throw new MissingObjectException("Link", linkName);
        }
        
        return TempoLinkField.click(linkName); 
    }
    
    public boolean clickTempoLink(String linkName) {
        return clickOnTempoLink(linkName); 
    }
    
	// Button	
	public boolean clickOnTempoButton(String buttonName) {
		if (!TempoButton.waitFor(buttonName)) {
            throw new MissingObjectException("Button", buttonName);
        }
		
		return TempoButton.click(buttonName);
	}
	
	public boolean clickTempoButton(String buttonName) {
        return clickTempoButton(buttonName);
    }
}
 ;