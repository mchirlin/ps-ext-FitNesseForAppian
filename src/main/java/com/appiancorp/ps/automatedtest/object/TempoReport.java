package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Metadata;

public class TempoReport extends TempoObject {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoReport.class);
    private static final String XPATH_ABSOLUTE_REPORT_LINK = Metadata.getByConstant("xpathAbsoluteReportLink");
    
    public static boolean click(String reportName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_REPORT_LINK, reportName)));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String reportName, Integer timeout) {
        try {
            (new WebDriverWait(driver, timeout)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_REPORT_LINK, reportName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_REPORT_LINK, reportName)));
            scrollIntoView(element);
        } catch (TimeoutException e) {
            return false;
        }

        return true;
    }
    
    public static boolean waitFor(String reportName) {
        return waitFor(reportName, timeoutSeconds);
    }
}
