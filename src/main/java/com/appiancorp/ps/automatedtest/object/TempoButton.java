package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianVersions;

public class TempoButton extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoButton.class);
    private static final String XPATH_ABSOLUTE_BUTTON = AppianVersions.getByConstant("xpathAbsoluteButton");
    
    public static boolean click(String buttonName) {        
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_BUTTON, buttonName)));
        element.click();
        
        LOG.debug("BUTTON CLICK : " + buttonName);
        
        waitForWorking();
        return true;
    }
    
    public static boolean waitFor(String buttonName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_BUTTON, buttonName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_BUTTON, buttonName)));
            scrollIntoView(element);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
