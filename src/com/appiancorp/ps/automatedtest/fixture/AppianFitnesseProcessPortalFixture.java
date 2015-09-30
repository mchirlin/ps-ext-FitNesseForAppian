package com.appiancorp.ps.automatedtest.fixture;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppianFitnesseProcessPortalFixture extends AppianFitnesseProcessBaseFixture {
	
	String processId = null;
	String url = null;
	String masterWindowHandle = null;
	
	WebDriver driver = null;
	
	public AppianFitnesseProcessPortalFixture() {
		super();
	}
	
	/** PORTAL RELATED FIXTURES **/
	
	public boolean waitUntilPortalPageWithTitleIsLoaded(String uiPage) {
		try {
			driver.switchTo().frame("fContent");
		} catch (Exception e) {
			
		}
		if (uiPage.equals("Home")) {
			(new WebDriverWait(driver, 1000)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Sign Out")));
		} else {
			(new WebDriverWait(driver, 1000)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='pageTitle' and contains(text(),'" + uiPage + "')]")));
		}
		
		return true;
	}
	
	public boolean selectPortalApplication(String applicationName) {
		WebElement applicationSelectorElement = driver.findElement(By.id("applicationName"));
		applicationSelectorElement.click();
		
		WebElement applicationLinkElement = driver.findElement(By.xpath("//a[@class='yuimenuitemlabel' and contains(text(),'"+ applicationName +"')]"));
		applicationLinkElement.click();
		
		return true;
	}
	
	public boolean clickLink(String linkText) {
		WebElement linkElement = driver.findElement(By.linkText(linkText));
		linkElement.click();
		
		return true;
	}
	
	
	public boolean waitUntilLinkIsPresent(String linkText) {
		(new WebDriverWait(driver, 1000)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
		return true;
	}
	
	public boolean waitUntilPortalFieldIsPresent(String fieldLabelName) {
		(new WebDriverWait(driver, 1000)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='legend' and contains(text(),'"+ fieldLabelName +"')]")));
		return true;
	}	
	
	public boolean waitUntilPortalFieldIsEditable(String fieldLabelName) {
		WebElement textField = driver.findElement(By.xpath("//div[@class='legend' and contains(text(),'"+ fieldLabelName +"')]/ancestor::fieldset[1]//div[@class='fields']//input"));
		
		while (textField.getAttribute("readonly") != null && textField.getAttribute("readonly").equalsIgnoreCase("true")) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			textField = driver.findElement(By.xpath("//div[@class='legend' and contains(text(),'"+ fieldLabelName +"')]/ancestor::fieldset[1]//div[@class='fields']//input"));
		}

		return true;
	}
	
	public boolean populatePortalTextFieldWith(String fieldLabelName, String value) {
		WebElement textField = driver.findElement(By.xpath("//div[@class='legend' and contains(text(),'"+ fieldLabelName +"')]/ancestor::fieldset[1]//div[@class='fields']//input"));
		
		textField.clear();
		textField.sendKeys(value);
		
		return true;
		
	}
	
	public boolean populatePortalDateFieldWith(String fieldLabelName, String value) {
		WebElement dateField = driver.findElement(By.xpath("//div[@class='legend' and contains(text(),'"+ fieldLabelName +"')]/ancestor::fieldset[1]//div[@class='fields']//input"));
		dateField.clear();
		waitForSeconds("1");
		dateField.click();
		dateField.sendKeys(value);
		
		return true;
		
	}
	
	public boolean populatePortalSelectFieldWith(String fieldLabelName, String value) {
		WebElement selectField = driver.findElement(By.xpath("//div[@class='legend' and contains(text(),'"+ fieldLabelName +"')]/ancestor::fieldset[1]//div[@class='fields']//select"));
		Select select = new Select(selectField);
		select.selectByVisibleText(value);
		
		return true;
	}
	
	public boolean populatePortalParagraphFieldWith(String fieldLabelName, String value) {
		WebElement textAreaField = driver.findElement(By.xpath("//div[@class='legend' and contains(text(),'"+ fieldLabelName +"')]/ancestor::fieldset[1]//div[@class='fields']//textarea"));
		textAreaField.clear();
		textAreaField.sendKeys(value);
		
		return true;
	}
	
	public boolean populatePortalTextFieldInGridRowWith(String fieldLabelName, String rowNo, String value) {
		WebElement textAreaField = driver.findElement(By.xpath("//div[@class='legend' and contains(text(),'"+fieldLabelName+"')]/ancestor::table//tbody//tr["+rowNo+"]//td[count(//div[@class='legend' and contains(text(),'"+fieldLabelName+"')]/ancestor::th/preceding-sibling::*)+1]//input"));
		textAreaField.clear();
		textAreaField.sendKeys(value);
		
		return true;
	}
	
	public boolean selectPortalRadioFieldWith(String fieldLabelName, String value) {
		WebElement radioField = driver.findElement(By.xpath("//div[@class='legend' and contains(text(),'"+fieldLabelName+"')]/parent::fieldset/div[@class='fields']//input[@class='radio' and @valueid='"+value+"']"));
		radioField.click();
		
		return true;
	}
	
	public boolean populatePortalSelectFieldInGridRowWith(String fieldLabelName, String rowNo, String value) {
		WebElement selectField = driver.findElement(By.xpath("//div[@class='legend' and contains(text(),'"+fieldLabelName+"')]/ancestor::table//tbody//tr["+rowNo+"]//td[count(//div[@class='legend' and contains(text(),'"+fieldLabelName+"')]/ancestor::th/preceding-sibling::*)+1]//select"));
		Select select = new Select(selectField);
		select.selectByVisibleText(value);
		
		return true;
	}
	
	
	public boolean clickButton(String fieldButtonName) {
		fieldButtonName = fieldButtonName.replace("_", " ");
		WebElement buttonElement = driver.findElement(By.xpath("//input[@value='" + fieldButtonName + "']"));
		buttonElement.click();
		
		return true;
	}
	
	public boolean refreshPage() {
		driver.navigate().refresh();
		try {
			driver.switchTo().frame("fContent");
		} catch (Exception e) {
			
		}
		
		return true;
	}
	
	public boolean refreshUntilLinkIsPresent(String linkText) {
		boolean present = false;
		try {
			int i=0;
			while (!present) {
				if (i >= 120) {
					return false;
				}
				
				driver.navigate().refresh();
				WebElement linkElement = null;
				try {
					driver.switchTo().frame("fContent");
				} catch (Exception e) {
					
				}
				try {
					linkElement = driver.findElement(By.linkText(linkText));
				} catch (Exception e) {
					
				}
				
				if (linkElement!=null) {
					present = true;
					break;
				}
								
				Thread.sleep(5000);
				i++;
					
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean waitAndSwitchToPopUp() {
		(new WebDriverWait(driver, 1000)).until(new ExpectedCondition<Boolean>() {
		        @Override
		        public Boolean apply(WebDriver d) {
		            return (d.getWindowHandles().size() != 1);
		        }
		    });
	
		    for (String activeHandle : driver.getWindowHandles()) {
		        if (!activeHandle.equals(masterWindowHandle)) {
		            driver.switchTo().window(activeHandle);
		        }
		    }

		return true;
	}
	
	public boolean switchToMainWindow() {
		driver.switchTo().window(masterWindowHandle);
		
		return true;
	}
	
	public boolean logout() {
		try {
			driver.switchTo().frame("fContent");
		} catch (Exception e) {
			
		}
		
		WebElement signOutLink = driver.findElement(By.linkText("Sign Out"));
		signOutLink.click();
		return true;
	}
	
}
 ;