package com.appiancorp.ps.automatedtest.fixture;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.fields.TempoAction;
import com.appiancorp.ps.automatedtest.fields.TempoButton;
import com.appiancorp.ps.automatedtest.fields.TempoDatetimeField;
import com.appiancorp.ps.automatedtest.fields.TempoEditableGrid;
import com.appiancorp.ps.automatedtest.fields.TempoField;
import com.appiancorp.ps.automatedtest.fields.TempoIntegerField;
import com.appiancorp.ps.automatedtest.fields.TempoLinkField;
import com.appiancorp.ps.automatedtest.fields.TempoLogin;
import com.appiancorp.ps.automatedtest.fields.TempoNewsItem;
import com.appiancorp.ps.automatedtest.fields.TempoParagraphField;
import com.appiancorp.ps.automatedtest.fields.TempoRadioField;
import com.appiancorp.ps.automatedtest.fields.TempoRecordItem;
import com.appiancorp.ps.automatedtest.fields.TempoRecordList;
import com.appiancorp.ps.automatedtest.fields.TempoSelectField;
import com.appiancorp.ps.automatedtest.fields.TempoTextField;
import com.appiancorp.ps.automatedtest.fields.TempoUserPickerField;

public class AppianFitnesseProcessTempoFixture extends AppianFitnesseProcessBaseFixture {
	    
	public AppianFitnesseProcessTempoFixture() {
		super();
	}
	
	/** TEMPO RELATED FIXTURE COMMANDS **/
	
	// Tempo Menu
	public boolean waitUntilTempoMenuIsPresent(String tempoMenu) {
		try {
			(new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@class, 'appian-menu-item') and contains(text(),'" + tempoMenu +"')]")));
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	
	public boolean clickOnTempoMenu(String tempoMenu) {
		if(!waitUntilTempoMenuIsPresent(tempoMenu)) return false;
		
		WebElement element = driver.findElement(By.xpath("//a[starts-with(@class, 'appian-menu-item') and contains(text(),'" + tempoMenu +"')]"));
		element.click();

		return true;
	}
	
	// Record Item
	public boolean clickOnTempoRecordItem(String itemName) {
        if(!TempoRecordItem.waitFor(driver, timeOutSeconds, itemName)) return false;
        
        return TempoRecordItem.click(driver, timeOutSeconds, itemName);
    }
	
	public boolean verifyTempoRecordItemIsPresent(String itemName) {
	    return TempoRecordItem.waitFor(driver, timeOutSeconds, itemName);
	}
	
	public boolean verifyTempoRecordItemIsNotPresent(String itemName) {
        return !TempoRecordItem.waitFor(driver, timeOutSeconds, itemName);
    }
	
	public boolean clickOnTempoRecordItemFacet(String facetName) {
        if(!TempoRecordItem.waitForFacet(driver, timeOutSeconds, facetName)) return false;
        
        return TempoRecordItem.clickOnFacet(driver, timeOutSeconds, facetName);
    }
	
	public boolean clickOnTempoRecordItemRelatedAction(String relatedActionName) {
        if(!TempoRecordItem.waitForRelatedAction(driver, timeOutSeconds, relatedActionName)) return false;
        
        return TempoRecordItem.clickOnRelatedAction(driver, timeOutSeconds, relatedActionName);
    }
	
	// Record List	
    public boolean clickOnTempoRecordList(String listName) {
        if(!TempoRecordList.waitFor(driver, timeOutSeconds, listName)) return false;
        
        return TempoRecordList.click(driver, timeOutSeconds, listName);
    }
    
	public boolean clickOnTempoRecordListFacetOption(String facetName) {
		if(!TempoRecordList.waitForFacetOption(driver, timeOutSeconds, facetName)) return false;
		
		return TempoRecordList.clickOnFacetOption(driver, timeOutSeconds, facetName);		
	}
	
	public boolean verifyTempoRecordListFacetOptionIsPresent(String facetName) {
        return TempoRecordList.waitForFacetOption(driver, timeOutSeconds, facetName);
    }
	
	// Action
	public boolean clickOnTempoAction(String actionName) {
        if(!TempoAction.waitFor(driver, timeOutSeconds, actionName)) return false;
        
        return TempoAction.click(driver, timeOutSeconds, actionName);
    }
	
	public boolean verifyTempoActionCompleted() {
	    return TempoAction.isCompleted(driver, timeOutSeconds);
	}
	
	// Form
	public boolean waitUntilTempoFormIsLoaded(String formName) {
		try {
			(new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[starts-with(@class, 'appian-form-title') and contains(text(),'"+formName+"')]")));
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	/** TEMPO FIELDS **/
	
	// Generic Field
	public boolean populateTempoFieldWith(String fieldName, String[] fieldValues) {
	    if(!TempoField.waitFor(driver, timeOutSeconds, fieldName)) return false;
	    
	    return TempoField.populate(driver, timeOutSeconds, fieldName, fieldValues);
	}
	
	public boolean clearTempoFieldOf(String fieldName, String[] fieldValues) {
	    if(!TempoField.waitFor(driver, timeOutSeconds, fieldName)) return false;
        
        return TempoField.clearOf(driver, timeOutSeconds, fieldName, fieldValues);
	}
	
	public boolean verifyTempoFieldContains(String fieldName, String[] fieldValues) {
        if(!TempoField.waitFor(driver, timeOutSeconds, fieldName)) return false;
        
        return TempoField.contains(driver, timeOutSeconds, fieldName, fieldValues);
    }
	
	// Text Field
    public boolean populateTempoTextFieldWith(String fieldName, String fieldValue) {
        if(!TempoTextField.waitFor(driver, timeOutSeconds, fieldName)) return false;
        
        return TempoTextField.populate(driver, timeOutSeconds, fieldName, fieldValue);
    }
    
	// Paragraph Field
	public boolean populateTempoParagraphFieldWith(String fieldName, String fieldValue) {
	    if(!TempoParagraphField.waitFor(driver, timeOutSeconds, fieldName)) return false;
        
        return TempoParagraphField.populate(driver, timeOutSeconds, fieldName, fieldValue);
	}
	
	// Integer Field
    public boolean populateTempoIntegerFieldWith(String fieldName, String fieldValue) {
        if(!TempoIntegerField.waitFor(driver, timeOutSeconds, fieldName)) return false;
        
        return TempoIntegerField.populate(driver, timeOutSeconds, fieldName, fieldValue);
    }
    
    // Radio Field
    public boolean populateTempoRadioFieldWith(String fieldName, String fieldValue) {
        if(!TempoRadioField.waitFor(driver, timeOutSeconds, fieldName)) return false;
        
        return TempoRadioField.populate(driver, timeOutSeconds, fieldName, fieldValue);  
    }
    
    // Select Field
    public boolean populateTempoSelectFieldWith(String fieldName, String fieldValue) {
        if(!TempoSelectField.waitFor(driver, timeOutSeconds, fieldName)) return false;
        
        return TempoSelectField.populate(driver, timeOutSeconds, fieldName, fieldValue);  
    }
    
    // Group Picker Field   
    public boolean populateTempoUserPickerFieldWith(String fieldName, String[] fieldValues) { 
        if(!TempoUserPickerField.waitFor(driver, timeOutSeconds, fieldName)) return false;
        
        return TempoUserPickerField.populate(driver, timeOutSeconds, fieldName, fieldValues);      
    }
    
    // Datetime Field
    public boolean populateTempoDatetimeFieldWithDateAndTime(String fieldName, String[] fieldValues) {
        if(!TempoDatetimeField.waitFor(driver, timeOutSeconds, fieldName)) return false;
        
        return TempoDatetimeField.populate(driver, timeOutSeconds, fieldName, fieldValues);  
    }
    
    public boolean populateTempoDatetimeFieldWithDateAndTime(String fieldName, String dateValue, String timeValue) {
        if(!TempoDatetimeField.waitFor(driver, timeOutSeconds, fieldName)) return false;
        
        return TempoDatetimeField.populate(driver, timeOutSeconds, fieldName, new String[] {dateValue, timeValue});  
    }
    
    public boolean populateTempoDatetimeFieldWithNowPlusMinutes(String fieldName, String[] fieldValues) {
        if(!TempoDatetimeField.waitFor(driver, timeOutSeconds, fieldName)) return false;
        
        return TempoDatetimeField.populate(driver, timeOutSeconds, fieldName, fieldValues);  
    }
    
    // Editable Grid Field   
    public boolean populateTempoEditableGridColumnRowWith(String gridName, String columnName, String rowNum, String[] fieldValues) {
        if(!TempoEditableGrid.waitFor(driver, timeOutSeconds, gridName, columnName, rowNum)) return false;
        
        return TempoEditableGrid.populate(driver, timeOutSeconds, gridName, columnName, rowNum, fieldValues); 
    }
	
    // Link
    public boolean clickOnTempoLink(String linkName) {
        if(!TempoLinkField.waitFor(driver, timeOutSeconds, linkName)) return false;
        
        return TempoLinkField.click(driver, timeOutSeconds, linkName); 
    }
    
	// Button	
	public boolean clickOnTempoButton(String buttonName) {
		if (!TempoButton.waitFor(driver, timeOutSeconds, buttonName)) return false;
		
		return TempoButton.click(driver, timeOutSeconds, buttonName);
	}
	
	// News feed item 
	public boolean verifyNewsFeedContainingTextIsPresent(String newsText) {
	    return TempoNewsItem.refreshAndWaitFor(driver, timeOutSeconds, newsText);
	}
	
	public boolean verifyNewsFeedContainingTextIsNotPresent(String newsText) {
	    return !TempoNewsItem.waitFor(driver, timeOutSeconds, newsText);
    }
	
	public boolean toggleMoreInfoForNewsFeedContainingText(String newsText) {
        if(!TempoNewsItem.waitForMoreInfo(driver, timeOutSeconds, newsText)) return false;
        
        return TempoNewsItem.toggleMoreInfo(driver, timeOutSeconds, newsText);
	}
    
	public boolean verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(String newsText, String label, String value) {	    
	    return TempoNewsItem.waitForLabelAndValue(driver, timeOutSeconds, newsText, label, value);
	}
	
    public boolean verifyNewsFeedContainingTextAndMoreInfoWithLabelsAndValuesArePresent(String newsText, String[] labels, String[] values) {        
        return TempoNewsItem.waitForLabelsAndValues(driver, timeOutSeconds, newsText, labels, values);
    }
    
    public boolean verifyNewsFeedContainingTextTaggedWithIsPresent(String newsText, String newsTag) {
        return TempoNewsItem.waitForTag(driver, timeOutSeconds, newsText, newsTag);
    }
	
	public boolean verifyNewsFeedContainingTextCommentedWithIsPresent(String newsText, String newsComment) {
	    return TempoNewsItem.refreshAndWaitForComment(driver, timeOutSeconds, newsText, newsComment);
	}
	
	public boolean refreshUntilNewsFeedContainingTextCommentedWithIsPresent(String newsText, String newsComment) {
		return TempoNewsItem.refreshAndWaitForComment(driver, timeOutSeconds, newsText, newsComment);
	}
	

	public boolean logoutFromTempo() {
		if(!TempoLogin.waitForLogout(driver, timeOutSeconds)) return false;
		
		return TempoLogin.logout(driver, timeOutSeconds);
	}
	
}
 ;