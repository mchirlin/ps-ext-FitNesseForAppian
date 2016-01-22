package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoRadioButtonOption extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoRadioButtonOption.class);
    private static final String XPATH_ELEMENT= "//label[text() = '%s']/preceding-sibling::input[@type = 'radio']";
    
    public static boolean click(String optionName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ELEMENT, optionName)));
        element.click();
        unfocus();
        
        LOG.debug("RADIO BUTTON OPTION CLICK : " + optionName);
        
        return true;
    }
    
    public static boolean waitFor(String optionName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ELEMENT, optionName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ELEMENT, optionName)));
            scrollIntoView(element);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
