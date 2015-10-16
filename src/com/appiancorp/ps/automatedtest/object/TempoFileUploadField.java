package com.appiancorp.ps.automatedtest.object;

import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoFileUploadField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoFileUploadField.class);
    private static final String XPATH_ABSOLUTE_LABEL = "//label[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::input";
    private static final String XPATH_RELATIVE_INPUT = ".//input[contains(@class, 'gwt-FileUpload')]";
    private static final String XPATH_RELATIVE_FILENAME = ".//span[contains(@class, 'filename')]/span";
    private static final String XPATH_RELATIVE_REMOVE = ".//a[starts-with(text(), 'Remove')]";
    
    public static boolean populate(String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement fileUpload = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        fileUpload.sendKeys(fieldValue);
        // Wait for file to upload
        waitForWorking();
        
        LOG.debug("FILE UPLOAD FIELD POPULATION : " + fieldValue);
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_LABEL, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
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
        
        String compareString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_FILENAME)).getText();
        fieldValue = Paths.get(fieldValue).getFileName().toString();
        LOG.debug("FILE UPLOAD FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");
        
        return compareString.contains(fieldValue);
    }
    
    public static boolean clear(WebElement fieldLayout) {
        WebElement removeLink = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_REMOVE));
        removeLink.click();
        
        return true;
    }
}
