package com.revature.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.Employee;
import com.revature.models.EventType;
import com.revature.models.Request;
import com.revature.service.EmployeeService;
import com.revature.service.LoopUpService;
import com.revature.service.RequestService;

public class FrontControllerHelper {

	public static Object processGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String uri = request.getRequestURI().replaceAll("/TJJProject1/main", "");
		HttpSession session = request.getSession(false);
		System.out.println(uri + "  === URI in Process Get");

		switch (uri) {
		case "/employeeInfo":
			// retrieve employee id from session

			if (session != null) {
				// get employee info to display on home page
				int employeeId = (int) session.getAttribute("employeeId");
				Employee currentEmployee = getEmployeeInfo(employeeId);
				if (currentEmployee != null) {
					return currentEmployee;
				}
			}

			return "Error No current session";
		case "/getRequests/Completed/EmployeeId":
			// retrieve employee requests based off of session employee id
			if (session != null) {
				int employeeId = (int) session.getAttribute("employeeId");
				return getAllRequestsByEmployeeId(employeeId);
			}
			return "Error getting Requests";

		case "/eventTypes":
			if (session != null) {
				return getAllEventTypes();
			}
			return "Error getting Event Types";

		// this endpoint is the default if no match is found
		default:
			return "No such endpoint";
		}
	}

	public static Object processPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String uri = request.getRequestURI().replaceAll("/TJJProject1/", "");
		
		System.out.println(uri + "  == URI in Process Post");
		
		switch(uri) 
		{
		// this end point should log the user in if supplied a username and password
		case "main":
			// User Login
			// first, compare username & password to login the user
			int employeeId = -1;
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			employeeId = authenticateUserEmailAndPass(email, password);
			if(employeeId != -1) {
				// the user login succeeded and we have the userId
				Employee currentEmployee = getEmployeeInfo(employeeId);
				if(currentEmployee != null) {
					// second, if login successful, create a session and add an Employee ID to session					
					HttpSession session = request.getSession();
					String empFullName = currentEmployee.getFirstName() + " " + currentEmployee.getLastName();
					session.setAttribute("employeeId", currentEmployee.getId());
					session.setAttribute("employeeName", empFullName);
					session.setAttribute("employeeTitle", currentEmployee.getTitle());
					return currentEmployee;
				} else {
					// login failure. Need to send error message?
					return "Login error. Email or password incorrect";
				}
			}
			return "Login error. Email or password incorrect";
					
		// this endpoint is the default if no match is found
			
		case "views/main/submitRequest" :
			HttpSession session = request.getSession(false);
			if(session != null) {
				// to do: get all parameter values from request, build a
				int currentEmployeeId = (int) session.getAttribute("employeeId");
				int eventTypeId = Integer.parseInt(request.getParameter("eventTypeSelect"));
				String currentEmployeeTitle = (String) session.getAttribute("employeeTitle");
				String eventLocation = request.getParameter("eventLocation");
				String eventStartDate = request.getParameter("eventStartDate");
				String eventStartTime = request.getParameter("eventStartTime");
				String eventWorkMissed = request.getParameter("eventWorkMissed");
				String eventDescription = request.getParameter("eventDescription");
				double eventCost = Double.parseDouble(request.getParameter("eventCost"));
				double projectedAmount = Double.parseDouble(request.getParameter("eventProjectedReimbursement"));
				String eventJustification = request.getParameter("eventJustification");

				System.out.println("EmpID = " + currentEmployeeId);
				System.out.println("EmpTitleId = " + currentEmployeeTitle);
				System.out.println("EventTypeID = " + eventTypeId);
				System.out.println("EventStartDate = " + eventStartDate);
				
				Request submitRequest = new Request(currentEmployeeId,currentEmployeeTitle,  
						eventTypeId, eventStartDate, eventStartTime, eventJustification, eventDescription, 
						eventLocation, eventCost, projectedAmount, eventWorkMissed);
				
				return submitRequest(submitRequest);
			}
			return "Error Submitting New Request";
			
		default: 
			return "No such endpoint";
		}
		
	}

	private static int authenticateUserEmailAndPass(String email, String password) {
		EmployeeService empService = new EmployeeService();
		return empService.accountLogin(email, password);
	}

	private static Employee getEmployeeInfo(int employeeId) {
		EmployeeService empService = new EmployeeService();
		return empService.getEmployeeInfo(employeeId);
	}

	private static List<Request> getAllRequestsByEmployeeId(int employeeId) {
		RequestService reqService = new RequestService();
		return reqService.getAllRequestsByEmployeeId(employeeId);
	}

	private static List<EventType> getAllEventTypes() {
		LoopUpService lookUpService = new LoopUpService();
		return lookUpService.getAllEventTypes();
	}
	
	private static boolean submitRequest(Request request) {
		boolean success = false;
		LocalDate currentDate = LocalDate.now();
		LocalDate startDate = LocalDate.parse(request.getEventStartDate());
		
		System.out.println("CurrentDate = " + currentDate);
		System.out.println("StartDate = " + startDate);
		long days = ChronoUnit.DAYS.between(currentDate, startDate);
		if(days < 14) {
			System.out.println("Is URGENT!!");
			request.setUrgent(true);
		}
		
		// ok so now start setting up the approval chain.
			// either by using current employee's title
			// or can add employee "boss" information
			// AND get the employee info and set status
			// occording to the "boss's" title
				
		return success;
	}

}
