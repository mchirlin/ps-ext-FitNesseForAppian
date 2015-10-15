package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoParagraphField extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoTextField.class);
    protected static final String XPATH_ABSOLUTE_LABEL = "//label[contains(text(),'%s')]/parent::span/following-sibling::div/div/textarea";
    protected static final String XPATH_RELATIVE_INPUT = ".//textarea[contains(@class, 'aui-TextAreaInput')]";
    
    public static boolean populate(String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement textAreaField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        textAreaField.clear();
        textAreaField.sendKeys(fieldValue);
        
        LOG.debug("PARAGRAPH FIELD POPULATION : " + fieldValue);

        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_LABEL, fieldName))));
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
        
        // For editable
        String compareString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT)).getAttribute("value");
        LOG.debug("PARAGRAPH FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");
        
        return compareString.contains(fieldValue);
    }
    
    public static boolean clear(WebElement fieldLayout) {
        WebElement textAreaField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        textAreaField.clear();
        
        return true;
    }
}
