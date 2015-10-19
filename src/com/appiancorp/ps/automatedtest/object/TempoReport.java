package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoReport extends TempoObject{
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoReport.class);
    private static final String XPATH_ELEMENT = "//a[contains(@class, 'appian-feed-entry-author') and contains(text(),'%s')]";
    
    public static boolean click(String reportName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ELEMENT, reportName)));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String reportName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ELEMENT, reportName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ELEMENT, reportName)));
            scrollIntoView(element);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
