package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoLogin extends Login {
    
    public static boolean logout(WebDriver driver, int timeOutSeconds) {
        /*((JavascriptExecutor) driver).executeScript(
                "document.evaluate(\"//div[@class='main_nav_bar']/descendant::a[contains(text(),'Sign Out')]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click()");*/
                
        WebElement settings = driver.findElement(By.xpath("//div[contains(@class, 'settings-pull-down')]/a"));
        settings.click();
        
        WebElement signOut = driver.findElement(By.xpath("//div[contains(@class, 'settings-pull-down')]/descendant::a[starts-with(text(), 'Sign Out')]"));
        signOut.click();
        
        return true;
    }
    
    public static boolean waitForLogout(WebDriver driver, int timeOutSeconds) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'settings-pull-down')]/a")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
