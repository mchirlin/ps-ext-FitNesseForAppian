package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoLogin extends TempoObject {
    
    public static boolean logout() {       
        WebElement settings = driver.findElement(By.xpath("//div[contains(@class, 'settings-pull-down')]/a"));
        settings.click();
        
        WebElement signOut = driver.findElement(By.xpath("//div[contains(@class, 'settings-pull-down')]/descendant::a[starts-with(text(), 'Sign Out')]"));
        signOut.click();
        
        return true;
    }
    
    public static boolean waitForLogout() {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'settings-pull-down')]/a")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean login(String url, String userName, String password) {
        WebElement userNameElement = driver.findElement(By.id("un"));
        userNameElement.clear();
        userNameElement.sendKeys(userName);
        
        WebElement passwordElement = driver.findElement(By.id("pw"));
        passwordElement.clear();
        passwordElement.sendKeys(password);
        
        /* Have to be specific as there is a hidden button for accepting terms */
        WebElement submitButton = driver.findElement(By.cssSelector("#loginForm > div.button_box > div.button_box_content > div.button_box_buttons > input.btn.primary"));
        submitButton.click();
        
        return true;
    }
    
    public static boolean waitForLogin(String url) {
        driver.get(url);
        driver.navigate().refresh();
        
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.id("un")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
