package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoTask extends TempoObject {
    
    private static final Logger LOG = Logger.getLogger(TempoTask.class);
    private static final String XPATH_ABSOLUTE_TASK_LINK = Settings.getByConstant("xpathAbsoluteTaskLink");
    private static final String XPATH_ABSOLUTE_TASK_DEADLINE = Settings.getByConstant("xpathAbsoluteTaskDeadline");
    private static final String XPATH_ABSOLUTE_TASK_REPORT_LINK = Settings.getByConstant("xpathAbsoluteTaskReportLink");
    
    public static boolean click(String taskName, Settings s) {
        WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_TASK_LINK, taskName)));
        element.click();

        if(popupError(s)) {
            clickPopupError(s);
            refreshAndWaitFor(taskName, s);
            click(taskName, s);
        }

        waitForWorking(s);
        
        return true;
    }
    
    public static boolean waitFor(String taskName, Integer timeout, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_TASK_LINK, taskName))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_TASK_LINK, taskName)));
            scrollIntoView(element, s);
        } catch (TimeoutException e) {
            return false;
        }

        return true;
    }
    
    public static boolean waitFor(String taskName, Settings s) {
        return waitFor(taskName, s.getTimeoutSeconds(), s);
    }
    
    public static boolean refreshAndWaitFor(String taskName, Settings s) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i > s.getRefreshTimes()) return false;
            
            if (TempoTask.waitFor(taskName, s)) {
                present = true;
                break;
            };        

            s.getDriver().navigate().refresh();
            i++;
        }

        return true;
    }
    
    public static boolean waitForTaskReport(String taskReport, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_TASK_REPORT_LINK, taskReport))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_TASK_REPORT_LINK, taskReport)));
            scrollIntoView(element, s);
        } catch (TimeoutException e) {
            return false;
        }

        return true;
    }
    
    public static boolean hasDeadlineOf(String taskName, String deadline, Settings s) {
        s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_TASK_DEADLINE, taskName, deadline)));
        
        return true;
    }
    
    public static boolean clickOnTaskReport(String taskReport, Settings s) {
        s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_TASK_REPORT_LINK, taskReport))).click();
        
        return true;
    }
    
    public static boolean popupError(Settings s) {
        try {
            LOG.debug("Looking for popup error");
            (new WebDriverWait(s.getDriver(), 1)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'popupContent')]")));
            LOG.debug("Found popup error");
            return true;
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            return false;
        }
    }
    
    public static void clickPopupError(Settings s) {
        s.getDriver().findElement(By.xpath("//div[contains(@class, 'popupContent')]/descendant::button")).click();
    }
}
