package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoTask extends TempoObject{
    
    public static boolean click(String taskName) {
        WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'appian-feed-entry-author') and contains(text(),'" + taskName +"')]"));
        element.click();
        
        // If the task is old and throws and error wait for another task with the same name
        if (waitForPopupError()) refreshAndWaitFor(taskName);

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
        try {
            int i = 0;
            while (!present) {
                if (i > refreshTimes) return false;
                
                if (TempoTask.waitFor(taskName)) {
                    present = true;
                    break;
                };        

                Thread.sleep(refreshTimeOutSeconds);
                driver.navigate().refresh();
                i++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    public static boolean hasDeadlineOf(String taskName, String deadline) {
        driver.findElement(By.xpath("//a[contains(@class, 'appian-feed-entry-author') and contains(text(),'"+taskName+"')]/parent::span/following-sibling::div[contains(@class, 'metadata')]/descendant::span[contains(@title, 'deadline')]/span[text() = '"+deadline+"']"));
        
        return true;
    }
    
    public static boolean waitForPopupError() {
        try {
            (new WebDriverWait(driver, 500)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'popupContent')]/button")));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    
    public static boolean clickPopupError() {
        driver.findElement(By.xpath("//div[contains(@class, 'popupContent')]/button")).click();
        
        return true;
    }
}
