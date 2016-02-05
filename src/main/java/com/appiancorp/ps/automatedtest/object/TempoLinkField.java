package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Metadata;

public class TempoLinkField extends TempoField {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoLinkField.class);
    private static final String XPATH_ABSOLUTE_LINK_FIELD = Metadata.getByConstant("xpathAbsoluteLinkField");
    private static final String XPATH_ABSOLUTE_LINK_FIELD_INDEX = "(" + XPATH_ABSOLUTE_LINK_FIELD + ")[%d]";
    
    public static WebElement getLink(String linkName){
        if(isFieldIndex(linkName)){
            int lNum = getIndexFromFieldIndex(linkName);
            String lName = getFieldFromFieldIndex(linkName);
            return driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD_INDEX, lName, lNum)));
        }
        else {
           return driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD, linkName)));
        }

    }
    
    public static boolean click(String linkName) {
        WebElement element = getLink(linkName);
        element.click();
        return true;
    }
    
    public static boolean waitFor(String linkName) {
        try {
            if (isFieldIndex(linkName)) {
                int lNum = getIndexFromFieldIndex(linkName);
                String lName = getFieldFromFieldIndex(linkName);
                (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD_INDEX, lName, lNum))));
            }
            else{
                (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD, linkName))));
            }
            WebElement element = getLink(linkName);
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
