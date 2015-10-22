package com.appiancorp.ps.automatedtest.object;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoEditableGrid extends TempoField {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoEditableGrid.class);
    private static final String XPATH_NUM_FIELD_LAYOUT = "//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/tbody/tr[%s]/td[%s]/descendant::div[contains(@class, 'aui_FieldLayout')]";
    private static final String XPATH_NAME_FIELD_LAYOUT = "//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/tbody/tr[%s]/td[count(//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/thead/tr/th[.='%s']/preceding-sibling::th)+1]/descendant::div[contains(@class, 'aui_FieldLayout')]";
    private static final String XPATH_NUM_GRID_CELL = "//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/tbody/tr[%s]/td[%s]";
    private static final String XPATH_NAME_GRID_CELL = "//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/tbody/tr[%s]/td[count(//span[contains(text(), '%s')]/parent::div/following-sibling::div/descendant::table/thead/tr/th[.='%s']/preceding-sibling::th)+1]";
    
    public static WebElement getFieldLayout(WebDriver driver, int timeOutSeconds, String gridName, String columnName, String rowNum) {
        // Using a columnNum
        if (StringUtils.isNumeric(columnName)) {
            int columnNum = Integer.parseInt(columnName);
            return driver.findElement(By.xpath(String.format(XPATH_NUM_FIELD_LAYOUT, gridName, columnNum, rowNum)));
        }
        
        // Using columnName
        return driver.findElement(By.xpath(String.format(XPATH_NAME_FIELD_LAYOUT, gridName, rowNum, gridName, columnName)));
    }
    
    public static boolean populate(String gridName, String columnName, String rowNum, String[] fieldValues) {
        WebElement fieldLayout = getFieldLayout(driver, timeoutSeconds, gridName, columnName, rowNum);
        
        return populate(fieldLayout, null, fieldValues);
    }
    
    public static boolean waitFor(WebDriver driver, int timeOutSeconds, String gridName, String columnName, String rowNum) {
        try {
            if (StringUtils.isNumeric(columnName)) {
                int columnNum = Integer.parseInt(columnName);
                (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_NUM_GRID_CELL, gridName, rowNum, columnNum))));
            } else {
                (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_NAME_GRID_CELL, gridName, rowNum, gridName, columnName))));
            }
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
}