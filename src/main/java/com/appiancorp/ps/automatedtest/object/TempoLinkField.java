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
    
    public static boolean click(String linkName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD, linkName)));
        element.click();
        
        return true;
    }
    
    public static boolean waitFor(String linkName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD, linkName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD, linkName)));
            scrollIntoView(element);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
