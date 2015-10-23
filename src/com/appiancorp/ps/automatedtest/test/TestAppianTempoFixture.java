package com.appiancorp.ps.automatedtest.test;

import com.appiancorp.ps.automatedtest.exception.MissingObjectException;
import com.appiancorp.ps.automatedtest.fixture.AppianFitnesseProcessTempoFixture;

public class TestAppianTempoFixture {

	/**
	 * @param args
	 * @throws MissingObjectException 
	 */
	public static void main(String[] args) throws Exception {
		AppianFitnesseProcessTempoFixture fixture = new AppianFitnesseProcessTempoFixture();
		
		fixture.setupSeleniumWebDriverWithBrowser("FIREFOX");
		fixture.setAppianUrlTo("http://localhost:8080");
		fixture.setDataSourceNameTo("jdbc/AppianBusinessDS");
		
		fixture.loginIntoWithUsernameAndPassword("http://localhost:8080/suite/tempo/", "mchirlin", "a");
		fixture.clickOnMenu("Records");
		fixture.logoutFromTempo();
		
		fixture.tearDownSeleniumWebDriver();
	}
}
