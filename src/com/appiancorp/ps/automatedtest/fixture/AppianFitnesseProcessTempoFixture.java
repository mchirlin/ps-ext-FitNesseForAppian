package com.appiancorp.ps.automatedtest.fixture;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import com.appiancorp.ps.automatedtest.exception.MissingObjectException;
import com.appiancorp.ps.automatedtest.object.TempoAction;
import com.appiancorp.ps.automatedtest.object.TempoButton;
import com.appiancorp.ps.automatedtest.object.TempoCheckboxField;
import com.appiancorp.ps.automatedtest.object.TempoField;
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
		
		return returnHandler(TempoMenu.click(tempoMenu));
	}
	
	// Logout
	public boolean logout() {
        if(!TempoLogin.waitForLogout()) {
            throw new MissingObjectException("Logout Menu");
        }
        
        return returnHandler(TempoLogin.logout());
    }
	
	/** NEWS **/
    
    public boolean verifyNewsFeedContainingTextIsPresent(String newsText) {
        return returnHandler(TempoNews.refreshAndWaitFor(newsText));
    }
    
    public boolean verifyNewsFeedContainingTextIsNotPresent(String newsText) {
        return returnHandler(!TempoNews.waitFor(newsText));
    }
    
    public boolean toggleMoreInfoForNewsFeedContainingText(String newsText) {
        if(!TempoNews.waitForMoreInfo(newsText)) {
            throw new MissingObjectException("News Post with More Info", newsText);
        }
        
        return returnHandler(TempoNews.toggleMoreInfo(newsText));
    }
    
    public boolean verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(String newsText, String label, String value) {     
        if (!TempoNews.refreshAndWaitFor(newsText)) {
            throw new MissingObjectException("News Post", newsText);
        }
        
        return returnHandler(TempoNews.waitForLabelAndValue(newsText, label, value));
    }
    
    public boolean verifyNewsFeedContainingTextTaggedWithIsPresent(String newsText, String newsTag) {
        if (!TempoNews.refreshAndWaitFor(newsText)) {
            throw new MissingObjectException("News Post", newsText);
        }
        
        return returnHandler(TempoNews.waitForTag(newsText, newsTag));
    }
    
    public boolean verifyNewsFeedContainingTextCommentedWithIsPresent(String newsText, String newsComment) {
        return returnHandler(TempoNews.refreshAndWaitForComment(newsText, newsComment));
    }
    
    public String getRegexFromNewsFeedContainingText(String regex, String newsText) {
        if (!TempoNews.refreshAndWaitFor(newsText)) {
            throw new MissingObjectException("News Post", newsText);
        }
        
        return TempoNews.getRegexForNewsPost(regex, newsText);
    }
    
    public String getRegexFromNewsFeedContainingTextCommentedWith(String regex, String newsText, String newsComment) {
        if (!TempoNews.refreshAndWaitForComment(newsText, newsComment)) {
            throw new MissingObjectException("News Post", newsText);
        }
        
        return TempoNews.getRegexForNewsPostComment(regex, newsText, newsComment);
    }
    
    /** TASKS **/
    
    public boolean clickOnTask(String taskName) {
       if(!TempoTask.refreshAndWaitFor(taskName)) {
           throw new MissingObjectException("Task", taskName);
       }
       
       return returnHandler(TempoTask.click(taskName));
    }
    
    public boolean verifyTaskIsPresent(String taskName) {
        return returnHandler(TempoTask.refreshAndWaitFor(taskName));
    }
    
    public boolean verifyTaskIsNotPresent(String taskName) {
        return returnHandler(!TempoTask.waitFor(taskName));
    }
    
    public boolean verifyTaskHasDeadlineOf(String taskName, String deadline) {
        if(!TempoTask.waitFor(taskName)) {
            throw new MissingObjectException("Task", taskName);
        }
        
        return returnHandler(TempoTask.hasDeadlineOf(taskName, deadline));
    }
    
    /** RECORDS **/
    
    // Record Item
    public boolean clickOnRecordItem(String itemName) {
        if(!TempoRecordItem.refreshAndWaitFor(itemName)) {
            throw new MissingObjectException("Tempo Record Item", itemName);
        }
        
        return returnHandler(TempoRecordItem.click(itemName));
    }
    
    public boolean verifyRecordItemIsPresent(String itemName) {
        return returnHandler(TempoRecordItem.waitFor(itemName));
    }
    
    public boolean verifyRecordItemIsNotPresent(String itemName) {
        return returnHandler(!TempoRecordItem.waitFor(itemName));
    }
    
    public boolean clickOnRecordItemFacet(String facetName) {
        if(!TempoRecordItem.waitForFacet(facetName)) {
            throw new MissingObjectException("Record Item Facet", facetName);
        }
        
        return returnHandler(TempoRecordItem.clickOnFacet(facetName));
    }
    
    public boolean clickOnRecordItemRelatedAction(String relatedActionName) {
        if(!TempoRecordItem.refreshAndWaitForRelatedAction(relatedActionName)) {
            throw new MissingObjectException("Related Action", relatedActionName);
        }
        
        return returnHandler(TempoRecordItem.clickOnRelatedAction(relatedActionName));
    }
    
    public boolean verifyRecordItemRelatedActionIsPresent(String relatedActionName) {
        return returnHandler(TempoRecordItem.refreshAndWaitForRelatedAction(relatedActionName));
    }
    
    public boolean verifyRecordItemRelatedActionIsNotPresent(String relatedActionName) {
        return returnHandler(!TempoRecordItem.waitForRelatedAction(relatedActionName));
    }
    
    // Record List  
    public boolean clickOnRecordList(String listName) {
        if(!TempoRecordList.waitFor(listName)) {
            throw new MissingObjectException("Record List", listName);
        }
        
        return returnHandler(TempoRecordList.click(listName));
    }
    
    public boolean clickOnRecordListFacetOption(String facetName) {
        if(!TempoRecordList.waitForFacetOption(facetName)) {
            throw new MissingObjectException("Record List Facet", facetName);
        }
        
        return returnHandler(TempoRecordList.clickOnFacetOption(facetName));       
    }
    
    public boolean verifyRecordListFacetOptionIsPresent(String facetName) {
        return returnHandler(TempoRecordList.waitForFacetOption(facetName));
    }
    
    /** REPORTS **/
    
    public boolean clickOnReport(String reportName) {
        if(!TempoReport.waitFor(reportName)) {
            throw new MissingObjectException("Report", reportName);
        }
        
        return returnHandler(TempoReport.click(reportName));
    }
    
    public boolean verifyReportIsPresent(String reportName) {
        return returnHandler(TempoReport.waitFor(reportName));
    }
    
    public boolean verifyReportIsNotPresent(String reportName) {
        return returnHandler(!TempoReport.waitFor(reportName));
    }
	
    /** ACTIONS **/
    
	public boolean clickOnAction(String actionName) {
        if(!TempoAction.waitFor(actionName)) {
            throw new MissingObjectException("Action", actionName);
        }
        
        return returnHandler(TempoAction.click(actionName));
    }
	
	public boolean verifyActionCompleted() {
	    return returnHandler(TempoAction.isCompleted());
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

	    return returnHandler(false);
	}
	
	public boolean populateFieldWithValue(String fieldName, String fieldValue) {
	  return returnHandler(populateFieldWith(fieldName, new String[]{fieldValue}));
	}
	
	public boolean populateFieldWithValues(String fieldName, String[] fieldValues) {
	  return returnHandler(populateFieldWith(fieldName, fieldValues));
	}
	
	public boolean populateFieldInSectionWith(String fieldName, String sectionName, String[] fieldValues) {
	    if(!TempoSection.waitFor(fieldName, sectionName)) {
            throw new MissingObjectException("Field", sectionName + fieldName);
        }
        
	    int attempt = 0;
        while (attempt < attemptTimes) {
            if (TempoSection.populate(fieldName, sectionName, fieldValues)) {
                new Actions(driver).sendKeys(Keys.TAB).perform();
                return true;
            }
            attempt++;
        }

        return returnHandler(false);
	}
	
	public boolean clearFieldOf(String fieldName, String[] fieldValues) {
	    if(!TempoField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return returnHandler(TempoField.clearOf(fieldName, fieldValues));
	}
	
	public boolean verifyFieldContains(String fieldName, String[] fieldValues) {
        if(!TempoField.waitFor(fieldName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return returnHandler(TempoField.contains(fieldName, fieldValues));
    }
	
	public boolean verifyFieldContainsValue(String fieldName, String fieldValue) {
	    return verifyFieldContains(fieldName, new String[]{fieldValue});
	}
	
	public boolean verifyFieldContainsValues(String fieldName, String[] fieldValues) {
        return verifyFieldContains(fieldName, fieldValues);
    }
	
	public boolean verifyFieldInSectionContains(String fieldName, String sectionName, String[] fieldValues) {
        if(!TempoSection.waitFor(fieldName, sectionName)) {
            throw new MissingObjectException("Field", fieldName);
        }
        
        return returnHandler(TempoSection.contains(fieldName, sectionName, fieldValues));
    }
	
	public boolean verifyFieldIsPresent(String fieldName) {
	    return returnHandler(TempoField.waitFor(fieldName));
	}
	
	public boolean verifyFieldIsNotPresent(String fieldName) {
        return returnHandler(!TempoField.waitFor(fieldName));
    }
	
    // Grid Field   
    public boolean populateGridColumnRowWith(String gridName, String columnName, String rowNum, String[] fieldValues) {
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
        
        return returnHandler(false);
    }
    
    public boolean verifyGridColumnRowContains(String gridName, String columnName, String rowNum, String[] fieldValues) {
        if(!TempoGrid.waitFor(gridName, columnName, rowNum)) {
            throw new MissingObjectException("Grid", gridName);
        }
        
        return returnHandler(TempoGrid.contains(gridName, columnName, rowNum, fieldValues)); 
    }
    
    public boolean selectGridRow(String gridName, String rowNum) {
        if(!TempoGrid.waitFor(gridName, rowNum)) {
            throw new MissingObjectException("Grid", gridName);
        }
        
        return returnHandler(TempoGrid.selectRow(gridName, rowNum)); 
    }
	
    // Link
    public boolean clickOnLink(String linkName) {
        if(!TempoLinkField.waitFor(linkName)) {
            throw new MissingObjectException("Link", linkName);
        }
        
        return returnHandler(TempoLinkField.click(linkName)); 
    }

	// Button	
	public boolean clickOnButton(String buttonName) {
		if (!TempoButton.waitFor(buttonName)) {
            throw new MissingObjectException("Button", buttonName);
        }
		
		return returnHandler(TempoButton.click(buttonName));
	}
	
	// Radio button option  
    public boolean clickOnRadioOption(String optionName) {
        if (!TempoRadioField.waitForOption(optionName)) {
            throw new MissingObjectException("Radio Button Option", optionName);
        }
        
        return returnHandler(TempoRadioField.clickOption(optionName));
    }
    
    // Checkbox option  
    public boolean clickOnCheckboxOption(String optionName) {
        if (!TempoCheckboxField.waitForOption(optionName)) {
            throw new MissingObjectException("Checkbox Option", optionName);
        }
        
        return returnHandler(TempoCheckboxField.clickOption(optionName));
    }
    
    // Section Error
    public boolean verifySectionContainingErrorIsPresent(String sectionName, String error) {
        return returnHandler(TempoSection.waitForError(sectionName, error));
    }
}