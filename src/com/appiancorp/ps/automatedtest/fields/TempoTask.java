package com.appiancorp.ps.automatedtest.fields;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoTask extends TempoObject{
    
    private static final Logger LOG = Logger.getLogger(TempoTask.class);
    
    public static boolean click(String taskName) {
        WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'appian-feed-entry-author') and contains(text(),'" + taskName +"')]"));
        element.click();

        if(popupError()) {
            clickPopupError();
            refreshAndWaitFor(taskName);
            click(taskName);
        }

        return true;
    }
    
    public static boolean waitFor(String taskName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class, 'appian-feed-entry-author') and contains(text(),'" + taskName +"')]")));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    
    public static boolean refreshAndWaitFor(String taskName) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i > refreshTimes) return false;
            
            if (TempoTask.waitFor(taskName)) {
                present = true;
                break;
            };        

            driver.navigate().refresh();
            i++;
        }

        return true;
    }
    
    public static boolean hasDeadlineOf(String taskName, String deadline) {
        driver.findElement(By.xpath("//a[contains(@class, 'appian-feed-entry-author') and contains(text(),'"+taskName+"')]/parent::span/following-sibling::div[contains(@class, 'metadata')]/descendant::span[contains(@title, 'deadline')]/span[text() = '"+deadline+"']"));
        
        return true;
    }
    
    public static boolean popupError() {
        try {
            LOG.debug("Looking for popup error");
            (new WebDriverWait(driver, 1)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'popupContent')]")));
            LOG.debug("Found popup error");
            return true;
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            return false;
        }
    }
    
    public static void clickPopupError() {
        driver.findElement(By.xpath("//div[contains(@class, 'popupContent')]/descendant::button")).click();
    }
}
