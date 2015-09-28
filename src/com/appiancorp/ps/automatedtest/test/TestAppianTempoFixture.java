package com.appiancorp.ps.automatedtest.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.appiancorp.ps.automatedtest.fixture.AppianFitnesseProcessTempoFixture;

public class TestAppianTempoFixture {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AppianFitnesseProcessTempoFixture fixture = new AppianFitnesseProcessTempoFixture();
		
		fixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
		fixture.setAppianUrlTo("http://localhost:8080");
		fixture.setDataSourceNameTo("jdbc/AppianBusinessDS");
		WebDriver driver = fixture.getDriver();
		
		fixture.loginIntoWithUsernameAndPassword("http://localhost:8080/suite/tempo/", "mchirlin", "a");
		fixture.clickOnTempoMenu("Records");
		fixture.logoutFromTempo();
		
		fixture.tearDownSeleniumWebDriver();
	}
	
	private static void takeScreenshot(WebDriver driver, int ctr) {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File("C:\\Software\\fitnesse\\test" + ctr + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
