package com.appiancorp.ps.automatedtest.fields;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoSelectField extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoSelectField.class);
    
    public static boolean populate(String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement selectField = fieldLayout.findElement(By.xpath(".//select"));
        Select select = new Select(selectField);
        select.selectByVisibleText(fieldValue);

        LOG.debug("SELECT FIELD POPULATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(WebDriver driver, String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/descendant::div/select")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldName, String fieldValue) {
        // For read-only
        try {
            return TempoField.contains(fieldLayout, fieldValue);
        } catch (Exception e) {}

        // For editable
        WebElement selectField = driver.findElement(By.xpath("//span[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/descendant::div/select"));
        Select select = new Select(selectField);
        String compareString = select.getFirstSelectedOption().getText();
        
        LOG.debug("SELECT FIELD COMPARISON : Field value (" + fieldValue + ") compared to Entered value (" + compareString + ")");
        
        return compareString.contains(fieldValue);
    }
}
