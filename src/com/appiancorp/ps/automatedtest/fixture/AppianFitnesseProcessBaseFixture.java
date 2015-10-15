package com.appiancorp.ps.automatedtest.fixture;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.appiancorp.ps.automatedtest.object.TempoLogin;
import com.appiancorp.ps.automatedtest.object.TempoObject;

import fitlibrary.DoFixture;

/**
 * This is the base class for Appian Fitnesse Fixture
 * This class contains fixture commands which are generic to Appian tests
 * @author henry.chandra
 * @author michael.chirlin
 *
 */
public class AppianFitnesseProcessBaseFixture extends DoFixture {
    
    private static final Logger LOG = Logger.getLogger(AppianFitnesseProcessBaseFixture.class);
    
	public String processId = null;
	public String url = null;
	public String dateFormatString = null;
	public String timeFormatString = null;
	public Date startDatetime = null;
	public String dataSourceName = null;
	public String masterWindowHandle = null;
	public WebDriver driver = null;
	public int timeoutSeconds = 5;
	public String screenshotPath = "C:\\AutomatedTesting\\screenshots";
	    
	Properties prop = new Properties();
	
	public AppianFitnesseProcessBaseFixture() {
		super();
		loadProperties();
		
		TempoObject.setTimeoutSeconds(timeoutSeconds);
		TempoObject.setStartDatetime(new Date());
	}
	
	public boolean setupSeleniumWebDriverWithBrowser(String browser) {
		if (browser.equals("FIREFOX")) {
			driver = new FirefoxDriver();
		} else if (browser.equals("IE")) {
			System.setProperty("webdriver.ie.driver", prop.getProperty("webdriver.ie.driver"));
			driver = new InternetExplorerDriver();
		} else if (browser.equals("PHANTOM")) {
			DesiredCapabilities dCaps = new DesiredCapabilities();
			dCaps.setJavascriptEnabled(true);
			dCaps.setCapability("takesScreenshot", true);
			dCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, prop.getProperty("phantomjs.binary.path"));
			driver = new PhantomJSDriver(dCaps);
		}
		
		this.masterWindowHandle = driver.getWindowHandle();
        
        TempoObject.setDriver(driver);
        TempoObject.setMasterWindowHandle(this.masterWindowHandle);
        
	    return true;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public boolean setAppianUrlTo(String url) {
		this.url = url;
		TempoObject.setUrl(this.url);
		
		return true;
	}
	
	public boolean setStartDatetime() {
	    this.startDatetime = new Date();
	    TempoObject.setStartDatetime(this.startDatetime);
        
        return true;
    }
	
	public boolean setDataSourceNameTo(String dataSourceName) {
		this.dataSourceName = dataSourceName;
		
		return true;
	}
	
	public boolean setDateFormatStringTo(String df) {
	    this.dateFormatString = df;
	    TempoObject.setDateFormatString(this.dateFormatString);
	    
	    return true;
	}

	public boolean setTimeFormatStringTo(String tf) {
        this.timeFormatString = tf;
        TempoObject.setTimeFormatString(this.timeFormatString);
        
        return true;
    }
	
	public boolean setTimeoutSecondsTo(String ts) {
        this.timeoutSeconds = Integer.valueOf(ts);
        TempoObject.setTimeoutSeconds(this.timeoutSeconds);
        
        return true;
    }
	
	public boolean setScreenshotPathTo(String path) {
	    this.screenshotPath = path;
	    
	    return true;
	}
	
	public boolean open(String url) {
		 driver.get(url);
		 return true;
	}
	
	public boolean takeScreenshot(String fileName) {
	    waitForWorking();
	    
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(screenshotPath + fileName + ".png"));
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return false;
        }   
        return true;
    }
	
	public boolean loginIntoWithUsernameAndPassword(String url, String userName, String password) {
		TempoLogin.navigateToLoginPage(url);
	    if (!TempoLogin.waitForLogin()) {
		    throw new MissingObjectException("Login");
		}
	    
		return TempoLogin.login(url, userName, password);
	}
	
	public boolean loginWithUsernameAndPassword(String userName, String password) {
	    TempoLogin.navigateToLoginPage(url);
        if (!TempoLogin.waitForLogin()) {
            throw new MissingObjectException("Login");
        }
        
        return TempoLogin.login(url, userName, password);
    }
	
	public boolean loginWithTermsWithUsernameAndPassword(String userName, String password) {
	    TempoLogin.navigateToLoginPage(url);
        if (!TempoLogin.waitForTerms()) {
            throw new MissingObjectException("Login Terms");
        }
        
        return TempoLogin.loginWithTerms(url, userName, password);
    }
	
	public boolean waitFor(String period) {
	    int periodNum = Integer.parseInt(period.replaceAll("[^0-9]", ""));
	    int noOfSeconds = 0;
	    if (period.contains("hour")) {
            noOfSeconds = periodNum * 60 * 24;
        } else if (period.contains("minute")) {
            noOfSeconds = periodNum * 60;
        } else {
	        noOfSeconds = periodNum;
        }
	    
		try {
			int i=0;
			while (true) {
				if (i >= noOfSeconds) {
					return true;
				}
				
				Thread.sleep(1000);
				i++;
					
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean waitForSeconds(String period) {
	    return waitFor(period + " seconds");
	}
	
	public boolean waitForMinutes(String period) {
        return waitFor(period + " minutes");
    }
	
	public boolean waitForHours(String period) {
        return waitFor(period + " hours");
    }
	
	public boolean waitForWorking() {
	    return TempoObject.waitForWorking();
	}
	
	public boolean waitUntil(String datetime) {
        datetime = TempoObject.calculateDate(datetime);
        
        try {
            Date endDatetime = DateUtils.parseDate(datetime, TempoObject.DATETIME_DISPLAY_FORMAT_STRING);
            Date nowDatetime = new Date();
            
            while (endDatetime.after(nowDatetime)) {
                //LOG.debug("now datetime: " + nowDatetime.toString() + " end datetime: " + endDatetime.toString());
                Thread.sleep(1000);
                nowDatetime = new Date();
            }
            return true;
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            return false;
        }
	}
	
	public boolean refresh() {
	    driver.navigate().refresh();
	    
	    return true;
	}
	
	/** PROCESS RELATED FIXTURES **/
		
	public boolean startProcessWithProcessModelUuId(String processModelUuid) {
		try {
			driver.get(url + "/suite/plugins/servlet/appianautomatedtest?operation=startProcessWithPMUuId&pmUuid=" + URLEncoder.encode(processModelUuid, "UTF-8"));
			String pageSource = driver.getPageSource();
			if (pageSource.contains("Exceptions occur")) {
				return false;
			} else {
				processId =  pageSource;
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	public boolean waitUntilTaskOfProcessModelUuidStartedRecentlyIsCompleted(String taskName, String pmUuid) {
		boolean completed = false;
		try {
			
			int i=0;
			String seconds = "120";
			while (!completed) {
				if (i > 120) {
					return false;
				}
				
				Thread.sleep(5000);
				
				Set<String> windows = driver.getWindowHandles();
				String mainHandle = driver.getWindowHandle();
				
				((JavascriptExecutor)driver).executeScript("window.open();");
				
				Set<String> completeWindow = driver.getWindowHandles();
				completeWindow.removeAll(windows);
				String completeHandle = ((String)completeWindow.toArray()[0]);

				driver.switchTo().window(completeHandle);
				driver.get(url + "/suite/plugins/servlet/appianautomatedtest?operation=queryIsTaskCompletedWithinSeconds&pmUuid=" + URLEncoder.encode(pmUuid, "UTF-8") + "&taskName=" + URLEncoder.encode(taskName, "UTF-8") + "&seconds=" + URLEncoder.encode(seconds, "UTF-8"));
				
				String pageSource = driver.getPageSource();
				driver.close();
				driver.switchTo().window(mainHandle);
				
				if (pageSource.contains("Task is completed")) {
					completed = true;
				} 
				
				i++;	
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return completed;
	}
	
	public boolean verifyDataInDatabaseWithQueryAndFields(String sqlQuery, String fields) {
		try {
			Set<String> windows = driver.getWindowHandles();
			String mainHandle = driver.getWindowHandle();
			
			((JavascriptExecutor)driver).executeScript("window.open();");
			
			Set<String> verifyWindow = driver.getWindowHandles();
			verifyWindow.removeAll(windows);
			String verifyHandle = ((String)verifyWindow.toArray()[0]);

			driver.switchTo().window(verifyHandle);
			driver.get(url + "/suite/plugins/servlet/appianautomatedtest?operation=verifyDataInDataBase&dataSource=" + URLEncoder.encode(dataSourceName, "UTF-8") + "&sqlQuery=" + URLEncoder.encode(sqlQuery, "UTF-8") + "&fields=" + URLEncoder.encode(fields, "UTF-8"));
			
			String pageSource = driver.getPageSource();
			String jsonSource = pageSource.substring(pageSource.indexOf("["),pageSource.indexOf("]")+1);
			
			driver.close();
			driver.switchTo().window(mainHandle);
			
			JSONArray resultArr = new JSONArray(jsonSource);
			
			return resultArr.length() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean verifyConstantHasValueOf(String constantName, String expectedConstantValue) {
		try {
			Set<String> windows = driver.getWindowHandles();
			String mainHandle = driver.getWindowHandle();
			
			((JavascriptExecutor)driver).executeScript("window.open();");
			
			Set<String> verifyWindow = driver.getWindowHandles();
			verifyWindow.removeAll(windows);
			String verifyHandle = ((String)verifyWindow.toArray()[0]);

			driver.switchTo().window(verifyHandle);
			driver.get(url + "/suite/plugins/servlet/appianautomatedtest?operation=verifyConstantHasValueOf&constantName=" + URLEncoder.encode(constantName, "UTF-8") + "&expectedConstantValue=" + URLEncoder.encode(expectedConstantValue, "UTF-8"));
			
			String pageSource = driver.getPageSource();
			driver.close();
			driver.switchTo().window(mainHandle);
			
			return pageSource.contains("Constant value is verified");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean tearDownSeleniumWebDriver() {
		driver.quit();
		return true;
	}
	
	private void loadProperties() {
		String propFile = "appianautomatedtest.properties";
		try {
			InputStream inputStream = this.getClass().getResourceAsStream(propFile);
			
			prop.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
