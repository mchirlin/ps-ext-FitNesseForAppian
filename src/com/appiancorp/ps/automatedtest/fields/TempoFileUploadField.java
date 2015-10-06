package com.appiancorp.ps.automatedtest.fields;

import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoFileUploadField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoFileUploadField.class);
    
    public static boolean populate(String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement fileUpload = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'gwt-FileUpload')]"));
        fileUpload.sendKeys(fieldValue);
        // Wait for file to upload
        try {
            Thread.sleep(1000);
            waitForWorking();
        } catch (InterruptedException e) {}
        
        LOG.debug("FILE UPLOAD field: " + fieldValue);
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/descendant::input")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        // For read-only
        try {
            return TempoField.contains(fieldLayout, fieldValue);
        } catch (Exception e) {}
        
        String compareString = fieldLayout.findElement(By.xpath(".//span[contains(@class, 'filename')]/span")).getText();
        fieldValue = Paths.get(fieldValue).getFileName().toString();
        LOG.debug("FILE UPLOAD FIELD COMPARISON : Field value (" + fieldValue + ") compared to Entered value (" + compareString + ")");
        
        return compareString.contains(fieldValue);
    }
    
    public static boolean clear(WebElement fieldLayout) {
        WebElement removeLink = fieldLayout.findElement(By.xpath(".//a[starts-with(text(), 'Remove')]"));
        removeLink.click();
        
        return true;
    }
}
