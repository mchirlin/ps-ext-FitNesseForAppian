package com.appiancorp.ps.automatedtest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.appiancorp.ps.automatedtest.servlet.steps.AppianAutomatedTestSteps;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.services.WebServiceContextFactory;
import com.appiancorp.suiteapi.common.ServiceLocator;
import com.appiancorp.suiteapi.process.ProcessDesignService;
import com.appiancorp.suiteapi.process.ProcessExecutionService;
import com.appiancorp.suiteapi.process.analytics2.ProcessAnalyticsService;

public class AppianAutomatedTestServlet extends HttpServlet {
	private ServiceContext sc;
    private ProcessDesignService pds; 
	private ProcessExecutionService pes;
	private ProcessAnalyticsService pas;
	private Connection connection;
	
	private static final String OPERATION_PARAM = "operation";
	
	private static final String START_PROCESS_WITH_PM_UUID_OPERATION = "startProcessWithPMUuId";
	
	private static final String QUERY_IS_TASK_COMPLETED_OPERATION = "queryIsTaskCompletedWithinSeconds";
	private static final String PM_UUID_PARAM = "pmUuid";
	private static final String TASK_NAME_PARAM = "taskName";
	private static final String SECONDS_PARAM = "seconds";
	
	private static final String VERIFY_CONSTANT_HAS_VALUE_OPERATION = "verifyConstantHasValueOf";
	private static final String CONSTANT_NAME_PARAM = "constantName";
	private static final String EXPECTED_CONSTANT_VALUE_PARAM = "expectedConstantValue";
	
	private static final String VERIFY_DATA_IN_DB = "verifyDataInDataBase";
	private static final String DATA_SOURCE_PARAM = "dataSource";
	private static final String SQL_QUERY_PARAM = "sqlQuery";
	private static final String FIELDS_PARAM = "fields";
	
	private static final String RUN_EXPRESSION_OPERATION = "runExpression";
    private static final String EXPRESSION = "expression";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String operation = request.getParameter(OPERATION_PARAM);
		
		if (setupAppianConnection(request)) {
			if (operation.equals(START_PROCESS_WITH_PM_UUID_OPERATION)) {
				String processModelUuId = request.getParameter(PM_UUID_PARAM);
				
				out.write(AppianAutomatedTestSteps.startProcessWithPMUuId(pds, processModelUuId));
				
			} else if (operation.equals(QUERY_IS_TASK_COMPLETED_OPERATION)) {
				String processModelUuId = request.getParameter(PM_UUID_PARAM);
				String taskName = request.getParameter(TASK_NAME_PARAM);
				String seconds = request.getParameter(SECONDS_PARAM);
				
				out.write(AppianAutomatedTestSteps.queryIsTaskCompletedWithinSeconds(pds, pes, pas, processModelUuId, taskName, new Integer(seconds)));
				
			} else if (operation.equals(VERIFY_CONSTANT_HAS_VALUE_OPERATION)) {
				String constantName = request.getParameter(CONSTANT_NAME_PARAM);
				String expectedConstantValue = request.getParameter(EXPECTED_CONSTANT_VALUE_PARAM);
				
				out.write(AppianAutomatedTestSteps.verifyConstantHasValueOf(pds, constantName, expectedConstantValue));
				
			} else if (operation.equals(VERIFY_DATA_IN_DB)) {
				String dataSourceName = request.getParameter(DATA_SOURCE_PARAM);
				String sqlQuery = request.getParameter(SQL_QUERY_PARAM);
				String fieldsParam = request.getParameter(FIELDS_PARAM);
				
				if(setupDatabaseConnection(dataSourceName)) {
					out.write(AppianAutomatedTestSteps.verifyDataInDatabase(connection, sqlQuery, fieldsParam));
				}
				
			} else if (operation.equals(RUN_EXPRESSION_OPERATION)) {
			    String expression = request.getParameter(EXPRESSION);
			    
			    out.write(AppianAutomatedTestSteps.runExpression(sc, expression));
			}
		}
		
		out.flush();
		out.close();
	}
	
	private boolean setupAppianConnection(HttpServletRequest request) {
		sc = WebServiceContextFactory.getServiceContext(request);
		pds = ServiceLocator.getProcessDesignService(sc);
		pes = ServiceLocator.getProcessExecutionService(sc);
		pas = ServiceLocator.getProcessAnalyticsService2(sc);
		
		return true;
	}
	
	private boolean setupDatabaseConnection(String dataSourceName) {

		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dataSourceName);
			connection = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		
		return true;
	}
	
	

}
