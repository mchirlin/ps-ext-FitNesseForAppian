package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class TempoLogin extends TempoObject {
    
    private static final Logger LOG = Logger.getLogger(TempoLogin.class);
    private static final String XPATH_SUBMIT_BUTTON = "//form[@id = 'loginForm']/descendant::input[contains(@class, 'btn')]";
    private static final String XPATH_AGREE_BUTTON = "//form[@id = 'notification']/descendant::input[contains(@class, 'btn')]";
    
    public static boolean logout() {
        LOG.debug("LOGGING OUT");
        ((JavascriptExecutor) driver).executeScript("document.evaluate(\"//div[@class='main_nav_bar']/descendant::a[contains(text(),'Sign Out')]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click()");
        
        return true;
    }
    
    public static boolean waitForLogout() {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'main_nav_bar')]")));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean login(String url, String userName, String password) {
        LOG.debug("LOGGING IN WITH : " + userName);
        
        WebElement userNameElement = driver.findElement(By.id("un"));
        userNameElement.clear();
        userNameElement.sendKeys(userName);
        
        WebElement passwordElement = driver.findElement(By.id("pw"));
        passwordElement.clear();
        passwordElement.sendKeys(password);
        
        /* Have to be specific as there is a hidden button for accepting terms */
        WebElement submitButton = driver.findElement(By.xpath(XPATH_SUBMIT_BUTTON));
        submitButton.click();
        
        waitForLogout();
        
        return true;
    }
    
    public static boolean loginWithTerms(String url, String userName, String password) {
        WebElement agreeButton = driver.findElement(By.xpath(XPATH_AGREE_BUTTON));
        agreeButton.click();
        
        waitForLogin();
        login(url, userName, password);
        
        return true;
    }
    
    public static boolean waitForLogin() {        
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_SUBMIT_BUTTON)));
            WebElement submitButton = driver.findElement(By.xpath(XPATH_SUBMIT_BUTTON));
            // Needs to align the button to top to prevent it by being covered by the copyright div
            scrollIntoView(submitButton, true);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitForTerms() {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_AGREE_BUTTON)));
            WebElement agreeButton = driver.findElement(By.xpath(XPATH_AGREE_BUTTON));
            // Needs to align the button to top to prevent it by being covered by the copyright div
            scrollIntoView(agreeButton, true);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static void navigateToLoginPage(String url) {
        driver.get(url);
    }
}
