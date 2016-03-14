package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoLinkField extends TempoField {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoLinkField.class);
    private static final String XPATH_ABSOLUTE_LINK_FIELD = Settings.getByConstant("xpathAbsoluteLinkField");
    private static final String XPATH_ABSOLUTE_LINK_FIELD_INDEX = "(" + XPATH_ABSOLUTE_LINK_FIELD + ")[%d]";
    
    public static WebElement getLink(String linkName, Settings s){
        if(isFieldIndex(linkName)){
            int lNum = getIndexFromFieldIndex(linkName);
            String lName = getFieldFromFieldIndex(linkName);
            return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD_INDEX, lName, lNum)));
        }
        else {
           return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD, linkName)));
        }

    }
    
    public static boolean click(String linkName, Settings s) {
        WebElement element = getLink(linkName, s);
        element.click();
        
        LOG.debug("LINK CLICK : " + linkName);
        
        waitForWorking(s);
        return true;
    }
    
    public static boolean waitFor(String linkName, Settings s) {
        try {
            if (isFieldIndex(linkName)) {
                int lNum = getIndexFromFieldIndex(linkName);
                String lName = getFieldFromFieldIndex(linkName);
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD_INDEX, lName, lNum))));
            }
            else{
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_LINK_FIELD, linkName))));
            }
            WebElement element = getLink(linkName, s);
            scrollIntoView(element, false, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
