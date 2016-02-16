package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoLogin extends TempoObject {
    
    private static final Logger LOG = Logger.getLogger(TempoLogin.class);
    private static final String XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON = Settings.getByConstant("xpathAbsoluteLoginSubmitButton");
    private static final String XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON = Settings.getByConstant("xpathAbsoluteLoginAgreeButton");
    private static final String XPATH_ABSOLUTE_LOGOUT_LINK = Settings.getByConstant("xpathAbsoluteLogoutLink");
    
    public static boolean logout(Settings s) {
        LOG.debug("LOGGING OUT");
        ((JavascriptExecutor) s.getDriver()).executeScript("document.evaluate(\"" + XPATH_ABSOLUTE_LOGOUT_LINK + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click()");
        
        return true;
    }
    
    public static boolean waitForLogout(Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'main_nav_bar')]")));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean login(String url, String userName, String password, Settings s) {
        LOG.debug("LOGGING IN WITH : " + userName);
        
        WebElement userNameElement = s.getDriver().findElement(By.id("un"));
        userNameElement.clear();
        userNameElement.sendKeys(userName);
        
        WebElement passwordElement = s.getDriver().findElement(By.id("pw"));
        passwordElement.clear();
        passwordElement.sendKeys(password);
        
        /* Have to be specific as there is a hidden button for accepting terms */
        WebElement submitButton = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON));
        submitButton.click();
        
        return waitForLogout(s);
    }
    
    public static boolean loginWithTerms(String url, String userName, String password, Settings s) {
        WebElement agreeButton = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON));
        agreeButton.click();
        
        waitForLogin(s);
        login(url, userName, password, s);
        
        return true;
    }
    
    public static boolean waitForLogin(Settings s) {        
        try {
            (new WebDriverWait(s.getDriver(), 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON)));
            WebElement submitButton = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON));
            // Needs to align the button to top to prevent it by being covered by the copyright div
            scrollIntoView(submitButton, true, s);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitForTerms(Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON)));
            WebElement agreeButton = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON));
            // Needs to align the button to top to prevent it by being covered by the copyright div
            scrollIntoView(agreeButton, true, s);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static void navigateToLoginPage(String url, Settings s) {
        s.getDriver().get(url);
    }
}
