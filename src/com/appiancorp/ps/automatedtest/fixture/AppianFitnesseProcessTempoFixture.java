package com.appiancorp.ps.automatedtest.fixture;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import com.appiancorp.ps.automatedtest.exception.MissingObjectException;
import com.appiancorp.ps.automatedtest.object.TempoAction;
import com.appiancorp.ps.automatedtest.object.TempoButton;
import com.appiancorp.ps.automatedtest.object.TempoField;
import com.appiancorp.ps.automatedtest.object.TempoGrid;
import com.appiancorp.ps.automatedtest.object.TempoLinkField;
import com.appiancorp.ps.automatedtest.object.TempoLogin;
import com.appiancorp.ps.automatedtest.object.TempoMenu;
import com.appiancorp.ps.automatedtest.object.TempoNews;
import com.appiancorp.ps.automatedtest.object.TempoRadioButtonOption;
import com.appiancorp.ps.automatedtest.object.TempoRecordItem;
import com.appiancorp.ps.automatedtest.object.TempoRecordList;
import com.appiancorp.ps.automatedtest.object.TempoReport;
import com.appiancorp.ps.automatedtest.object.TempoTask;

public class AppianFitnesseProcessTempoFixture extends AppianFitnesseProcessBaseFixture {
	
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(AppianFitnesseProcessTempoFixture.class);
    
	public AppianFitnesseProcessTempoFixture() {
		super();
	}
	
	/** TEMPO **/
	
	// Tempo Menu	
	public boolean clickOnMenu(String tempoMenu) {
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
    
    public boolean verifyNewsFeedContainingTextIsPresent(String newsText) {
        return TempoNews.refreshAndWaitFor(newsText);
    }
    
    public boolean verifyNewsFeedContainingTextIsNotPresent(String newsText) {
        return !TempoNews.waitFor(newsText);
    }
    
    public boolean toggleMoreInfoForNewsFeedContainingText(String newsText) {
        if(!TempoNews.waitForMoreInfo(newsText)) {
            throw new MissingObjectException("News Post with More Info", newsText);
        }
        
        return TempoNews.toggleMoreInfo(newsText);
    }
    
    public boolean verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(String newsText, String label, String value) {     
        if (!TempoNews.refreshAndWaitFor(newsText)) {
            throw new MissingObjectException("News Post", newsText);
        }
        
        return TempoNews.waitForLabelAndValue(newsText, label, value);
    }
    
    public boolean verifyNewsFeedContainingTextTaggedWithIsPresent(String newsText, String newsTag) {
        if (!TempoNews.refreshAndWaitFor(newsText)) {
            throw new MissingObjectException("News Post", newsText);
        }
        
        return TempoNews.waitForTag(newsText, newsTag);
    }
    
    public boolean verifyNewsFeedContainingTextCommentedWithIsPresent(String newsText, String newsComment) {
        return TempoNews.refreshAndWaitForComment(newsText, newsComment);
    }
    
    /** TASKS **/
    
    public boolean clickOnTask(String taskName) {
       if(!TempoTask.refreshAndWaitFor(taskName)) {
           throw new MissingObjectException("Task", taskName);
       }
       
       return TempoTask.click(taskName);
    }
    
    public boolean verifyTaskIsPresent(String taskName) {
        return TempoTask.refreshAndWaitFor(taskName);
    }
    
    public boolean verifyTaskIsNotPresent(String taskName) {
        return !TempoTask.waitFor(taskName);
    }
    
    public boolean verifyTaskHasDeadlineOf(String taskName, String deadline) {
        if(!TempoTask.waitFor(taskName)) {
            throw new MissingObjectException("Task", taskName);
        }
        
        return TempoTask.hasDeadlineOf(taskName, deadline);
    }
    
    /** RECORDS **/
    
    // Record Item
    public boolean clickOnRecordItem(String itemName) {
        if(!TempoRecordItem.refreshAndWaitFor(itemName)) {
            throw new MissingObjectException("Tempo Record Item", itemName);
        }
        
        return TempoRecordItem.click(itemName);
    }
    
    public boolean verifyRecordItemIsPresent(String itemName) {
        return TempoRecordItem.waitFor(itemName);
    }
    
    public boolean verifyRecordItemIsNotPresent(String itemName) {
        return !TempoRecordItem.waitFor(itemName);
    }
    
    public boolean clickOnRecordItemFacet(String facetName) {
        if(!TempoRecordItem.waitForFacet(facetName)) {
            throw new MissingObjectException("Record Item Facet", facetName);
        }
        
        return TempoRecordItem.clickOnFacet(facetName);
    }
    
    public boolean clickOnRecordItemRelatedAction(String relatedActionName) {
        if(!TempoRecordItem.refreshAndWaitForRelatedAction(relatedActionName)) {
            throw new MissingObjectException("Related Action", relatedActionName);
        }
        
        return TempoRecordItem.clickOnRelatedAction(relatedActionName);
    }
    
    public boolean verifyRecordItemRelatedActionIsPresent(String relatedActionName) {
        return TempoRecordItem.refreshAndWaitForRelatedAction(relatedActionName);
    }
    
    public boolean verifyRecordItemRelatedActionIsNotPresent(String relatedActionName) {
        return !TempoRecordItem.waitForRelatedAction(relatedActionName);
    }
    
    // Record List  
    public boolean clickOnRecordList(String listName) {
        if(!TempoRecordList.waitFor(listName)) {
            throw new MissingObjectException("Record List", listName);
        }
        
        return TempoRecordList.click(listName);
    }
    
    public boolean clickOnRecordListFacetOption(String facetName) {
        if(!TempoRecordList.waitForFacetOption(facetName)) {
            throw new MissingObjectException("Record List Facet", facetName);
        }
        
        return TempoRecordList.clickOnFacetOption(facetName);       
    }
    
    public boolean verifyRecordListFacetOptionIsPresent(String facetName) {
        return TempoRecordList.waitForFacetOption(facetName);
    }
    
    /** REPORTS **/
    
    public boolean clickOnReport(String reportName) {
        if(!TempoReport.waitFor(reportName)) {
            throw new MissingObjectException("Report", reportName);
        }
        
        return TempoReport.click(reportName);
    }
	
    /** ACTIONS **/
    
	public boolean clickOnAction(String actionName) {
        if(!TempoAction.waitFor(actionName)) {
            throw new MissingObjectException("Action", actionName);
        }
        
        return TempoAction.click(actionName);
    }
	
	public boolean verifyActionCompleted() {
	    return TempoAction.isCompleted();
	}
	
	/** FORMS **/

	// Generic Field
	public boolean populateFieldWith(String fieldName, String[] fieldValues) {
	    if(!TempoField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
	    
	    // Populate the field
	    int attempt = 0;
	    while (attempt < attemptTimes) {
	        if (TempoField.populate(fieldName, fieldValues)) {
	            new Actions(driver).sendKeys(Keys.TAB).perform();
	            return true;
	        }
	        attempt++;
	    }

	    /*
	    // Verify population worked
	    attempt = 0;
	    while(attempt < attemptTimes) {
	        if (TempoField.contains(fieldName, fieldValues)) return true;
	        attempt++;
	    }
	    */
	    return false;
	}
	
	public boolean populateFieldInSectionWith(String fieldName, String sectionName, String[] fieldValues) {
	    if(!TempoField.waitFor(fieldName, sectionName)) {
            throw new MissingObjectException("Field", sectionName + fieldName);
        }
        
	    int attempt = 0;
        while (attempt < attemptTimes) {
            if (TempoField.populate(fieldName, sectionName, fieldValues)) {
                new Actions(driver).sendKeys(Keys.TAB).perform();
                return true;
            }
            attempt++;
        }

        /*
        // Verify population worked
        attempt = 0;
        while(attempt < attemptTimes) {
            if (TempoField.contains(fieldName, sectionName, fieldValues)) return true;
            attempt++;
        }
        */
        return false;
	}
	
	public boolean clearFieldOf(String fieldName, String[] fieldValues) {
	    if(!TempoField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoField.clearOf(fieldName, fieldValues);
	}
	
	public boolean verifyFieldContains(String fieldName, String[] fieldValues) {
        if(!TempoField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return TempoField.contains(fieldName, fieldValues);
    }
	
    // Grid Field   
    public boolean populateEditableGridColumnRowWith(String gridName, String columnName, String rowNum, String[] fieldValues) {
        if(!TempoGrid.waitFor(gridName, columnName, rowNum)) {
            throw new MissingObjectException("Grid", gridName);
        }
        
        int attempt = 0;
        while (attempt < attemptTimes) {
            if (TempoGrid.populate(gridName, columnName, rowNum, fieldValues)) {
                new Actions(driver).sendKeys(Keys.TAB).perform();
                return true;
            }
            attempt++;
        }

        /*
        // Verify population worked
        attempt = 0;
        while(attempt < attemptTimes) {
            if (TempoGrid.contains(gridName, columnName, rowNum, fieldValues)) return true;
            attempt++;
        }
        */
        
        return false;
    }
    
    public boolean verifyEditableGridColumnRowContains(String gridName, String columnName, String rowNum, String[] fieldValues) {
        if(!TempoGrid.waitFor(gridName, columnName, rowNum)) {
            throw new MissingObjectException("Grid", gridName);
        }
        
        return TempoGrid.contains(gridName, columnName, rowNum, fieldValues); 
    }
	
    // Link
    public boolean clickOnLink(String linkName) {
        if(!TempoLinkField.waitFor(linkName)) {
            throw new MissingObjectException("Link", linkName);
        }
        
        return TempoLinkField.click(linkName); 
    }

	// Button	
	public boolean clickOnButton(String buttonName) {
		if (!TempoButton.waitFor(buttonName)) {
            throw new MissingObjectException("Button", buttonName);
        }
		
		return TempoButton.click(buttonName);
	}
	
	// Button  
    public boolean clickOnRadioButton(String optionName) {
        if (!TempoRadioButtonOption.waitFor(optionName)) {
            throw new MissingObjectException("Radio Button Option", optionName);
        }
        
        return TempoRadioButtonOption.click(optionName);
    }
}