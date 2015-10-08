package com.appiancorp.ps.automatedtest.fields;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class TempoLogin extends TempoObject {
    
    private static final Logger LOG = Logger.getLogger(TempoLogin.class);
    
    public static boolean logout() {   
        ((JavascriptExecutor) driver).executeScript("document.evaluate(\"//div[@class='main_nav_bar']/descendant::a[contains(text(),'Sign Out')]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click()");
        
        return true;
    }
    
    public static boolean waitForLogout() {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'main_nav_bar')]")));
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
