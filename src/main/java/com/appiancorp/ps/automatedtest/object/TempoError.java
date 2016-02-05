package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Metadata;
import com.appiancorp.ps.automatedtest.object.TempoObject;

public class TempoError extends TempoObject {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoError.class);
    private static final String XPATH_ERROR = Metadata.getByConstant("xpathAbsoluteError");
    
    public static boolean waitFor() {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ERROR)));
        } catch (TimeoutException e) {
            return false;
        }
    
        return true;
    }
}
