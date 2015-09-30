package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoGrid extends TempoField {
    
    public static WebElement getCell(String gridName, String columnName, String rowNum) {
        // Using a columnNum
        try {
            int columnNum = Integer.parseInt(columnName);
            return driver.findElement(By.xpath("//span[contains(text(), '"+gridName+"')]/parent::div/following-sibling::div/descendant::table/tbody/tr["+rowNum+"]/td["+columnNum+"]"));
        } catch (Exception e) {}
        
        // Using columnName
        return driver.findElement(By.xpath("//span[contains(text(), '"+gridName+"')]/parent::div/following-sibling::div/descendant::table/tbody/tr["+rowNum+"]/td[count(//span[contains(text(), '"+gridName+"')]/parent::div/following-sibling::div/descendant::table/thead/tr/th[.='"+columnName+"']/preceding-sibling::th)+1]"));
    }
    
    public static boolean populate(String gridName, String columnName, String rowNum, String[] fieldValues) {
        WebElement cell = getCell(gridName, columnName, rowNum);
        
        // TODO Handle group picker in a grid
        return populate(cell, null, fieldValues);
    }
    
    public static boolean contains(String gridName, String columnName, String rowNum, String[] fieldValues) {
        WebElement cell = getCell(gridName, columnName, rowNum);
        
        // TODO Handle group picker in a grid
        return contains(cell, null, fieldValues);
    }
    
    public static boolean waitFor(String gridName, String columnName, String rowNum) {
        // Using a columnNum
        try {
            int columnNum = Integer.parseInt(columnName);
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), '"+gridName+"')]/parent::div/following-sibling::div/descendant::table/tbody/tr["+rowNum+"]/td["+columnNum+"]")));
        } catch (Exception e) {}
        
        // Using a columnName
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), '"+gridName+"')]/parent::div/following-sibling::div/descendant::table/tbody/tr["+rowNum+"]/td[count(//span[contains(text(), '"+gridName+"')]/parent::div/following-sibling::div/descendant::table/thead/tr/th[.='"+columnName+"']/preceding-sibling::th)+1]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
}