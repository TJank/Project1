package com.revature.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.ApprovedStatus;
import com.revature.models.Employee;
import com.revature.models.EventType;
import com.revature.models.Request;
import com.revature.models.WaitingStatus;
import com.revature.service.EmployeeService;
import com.revature.service.LookUpService;
import com.revature.service.RequestService;

public class FrontControllerHelper {

	public static Object processGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String uri = request.getRequestURI().replaceAll("/TJJProject1/main", "");
		HttpSession session = request.getSession(false);

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

		case "/getRequests/Pending/PositionLevel":
			if (session != null) {
				String currentEmployeeTitle = (String) session.getAttribute("employeeTitle");
				return getAllPendingRequestsByEmployeeLevel(currentEmployeeTitle);
			}
			return "Error getting Pending Requests";

		case "/requestToApprove":
			if (session != null) {
				Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("approveRequest")) {
						int requestToApproveId = Integer.parseInt(cookie.getValue());
						return getRequestToApprove(requestToApproveId);
					}
				}
			}
			return "error getting request with cookie";
			
			
		case "/requestToView" :
			if (session != null) {
				Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("viewRequest")) {
						int requestToViewOnlyId = Integer.parseInt(cookie.getValue());
						return getRequestToApprove(requestToViewOnlyId);
					}
				}
			}
			return "error viewing request with cookie";
			
		case "/requestToEdit":
			if (session != null) {
				Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("editRequest")) {
						int editRequestId = Integer.parseInt(cookie.getValue());
						return getRequestToApprove(editRequestId);
					}
				}
			}
			return "error viewing request with cookie";
		case "/eventTypes":
			if (session != null) {
				return getAllEventTypes();
			}
			return "Error getting Event Types";
			
			
		case "/logout":
			HttpSession session3 = request.getSession(false);
			if(session3 != null) {
				session3.invalidate();
			}
			return null;
			
		// this endpoint is the default if no match is found
		default:
			return "No such endpoint";
		}
	}

	public static Object processPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String uri = request.getRequestURI().replaceAll("/TJJProject1/", "");


		switch (uri) {
		// this end point should log the user in if supplied a username and password
		case "main":
			// User Login
			// first, compare username & password to login the user
			int employeeId = -1;
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			employeeId = authenticateUserEmailAndPass(email, password);
			if (employeeId != -1) {
				// the user login succeeded and we have the userId
				Employee currentEmployee = getEmployeeInfo(employeeId);
				if (currentEmployee != null) {
					// second, if login successful, create a session and add an Employee ID to
					// session
					HttpSession session = request.getSession();
					String empFullName = currentEmployee.getFirstName() + " " + currentEmployee.getLastName();
					session.setAttribute("employeeId", currentEmployee.getId());
					session.setAttribute("bossTitle", currentEmployee.getBossTitle());
					session.setAttribute("employeeTitleId", currentEmployee.getTitleId());
					session.setAttribute("employeeTitle", currentEmployee.getTitle());
					return currentEmployee;
				} else {
					// login failure. Need to send error message?
					return "Login error. Email or password incorrect";
				}
			}
			return "Login error. Email or password incorrect";

		// this endpoint is the default if no match is found

		case "views/main/submitRequest":
			HttpSession session = request.getSession(false);
			if (session != null) {
				// to do: get all parameter values from request, build a
				int currentEmployeeId = (int) session.getAttribute("employeeId");
				Employee currentEmployee = getEmployeeInfo(currentEmployeeId);
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

				Request submitRequest = new Request(currentEmployeeId, currentEmployeeTitle, eventTypeId,
						eventStartDate, eventStartTime, eventJustification, eventDescription, eventLocation, eventCost,
						projectedAmount, eventWorkMissed);
				
				
				return submitRequest(submitRequest, currentEmployee);
			}
			return "Error Submitting New Request";

		case "views/main/ApproveOrReject":
			HttpSession session2 = request.getSession(false);
			if (session2 != null) {
				int requestId = -1;
				String status = "";
				String currentEmployeeTitle = (String) session2.getAttribute("employeeTitle");
				Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("approveId")) {
						requestId = Integer.parseInt(cookie.getValue());
						status = "approve";
					} else if (cookie.getName().equals("requestInfoId")) {
						requestId = Integer.parseInt(cookie.getValue());
						status = "reqinfo";
					} else if(cookie.getName().equals("rejectId")) {
						requestId = Integer.parseInt(cookie.getValue());
						status = "rejected";
					}
				}
				return editRequestBasedOffStatus(requestId, status, currentEmployeeTitle);
			}
			return "Error, No session";
			
		case "views/main/EditRequest":
			HttpSession sess = request.getSession(false);
			if (sess != null) {
				int requestId = -1;
				String status = "";
				String bossTitle = (String) sess.getAttribute("bossTitle");
				Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("saveId")) {
						requestId = Integer.parseInt(cookie.getValue());
						status = "save";
						String moreInfo = request.getParameter("eventAdditionalInfo");
						return updateRequest(requestId, status, moreInfo, bossTitle);
					} else if (cookie.getName().equals("cancelRequestId")) {
						requestId = Integer.parseInt(cookie.getValue());
						status = "reqinfo";
						return cancelRequest(requestId);
					}
				}
			}
			return "Error, No session";
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

	private static List<Request> getAllPendingRequestsByEmployeeLevel(String currentEmployeeTitle) {
		RequestService reqService = new RequestService();
		return reqService.getAllPendingRequestsByEmployeeLevel(currentEmployeeTitle);
	}
	
	private static Request getRequestToApprove(int requestToApproveId) {
		RequestService reqService = new RequestService();
		return reqService.getRequestToApprove(requestToApproveId);
	}

	private static List<EventType> getAllEventTypes() {
		LookUpService lookUpService = new LookUpService();
		return lookUpService.getAllEventTypes();
	}

	private static List<ApprovedStatus> getAllPendingApprovedStatuses() {
		LookUpService lookUpService = new LookUpService();
		return lookUpService.getAllPendingApprovedStatuses();
	}

	private static List<WaitingStatus> getWaitingStatuses() {
		LookUpService lookUpService = new LookUpService();
		return lookUpService.getWaitingStatuses();
	}
	private static List<ApprovedStatus> getAllRejectedStatuses() {
		LookUpService lookUpService = new LookUpService();
		return lookUpService.getRejectedStatuses();
	}
	
	private static List<WaitingStatus> getAllReqInfoStatuses() {
		LookUpService lookUpService = new LookUpService();
		return lookUpService.getAllReqInfoStatuses();
	}

	private static boolean submitRequest(Request request, Employee currentEmployee) {
		EmployeeService empService = new EmployeeService();
		RequestService reqService = new RequestService();
		List<ApprovedStatus> pendingApprovedStatuses = getAllPendingApprovedStatuses();
		List<WaitingStatus> waitingStatuses = getWaitingStatuses();
		int employeeAvailableAmount = currentEmployee.getAvailableAmount();
		employeeAvailableAmount -= request.getProjectedAmount();

		LocalDate currentDate = LocalDate.now();
		LocalDate startDate = LocalDate.parse(request.getEventStartDate());

		// ok so now start setting up the approval chain.
		for (ApprovedStatus appStatus : pendingApprovedStatuses) {
			if (appStatus.getApprovalStatus().toLowerCase().contains(currentEmployee.getBossTitle().toLowerCase())) {
				request.setApprovalStatusId(appStatus.getId());
			}
		}

		for (WaitingStatus waitStatus : waitingStatuses) {
			if (waitStatus.getWaitingStatus().toLowerCase().contains(currentEmployee.getBossTitle().toLowerCase())) {
				request.setWaitingStatusId(waitStatus.getId());
			}
		}

		long days = ChronoUnit.DAYS.between(currentDate, startDate);
		if (days < 14) {
			request.setUrgent(true);
		}
		
		currentEmployee.setAvailableAmount(employeeAvailableAmount);
		empService.updateEmployee(currentEmployee);
		return reqService.insertNewRequest(request);
	}
	
	private static Object cancelRequest(int requestId) {
		RequestService reqService = new RequestService();
		reqService.cancelRequest(requestId);
		return "Request Canceled";
	}
	
	private static Object updateRequest(int requestId, String status, String moreInfo, String bossTitle) {
		RequestService reqService = new RequestService();
		List<ApprovedStatus> pendingStatuses = getAllPendingApprovedStatuses();
		int approvedStatusId = -1;
		
		for(ApprovedStatus appStatus : pendingStatuses) {
			if (appStatus.getApprovalStatus().toLowerCase().contains(bossTitle.toLowerCase())) {
				approvedStatusId = appStatus.getId();
			}
		}
		
		reqService.updateMoreInfoRequest(requestId, moreInfo, approvedStatusId);
		
		return "Added More info to request";
	}
	
	private static Object editRequestBasedOffStatus(int requestId, String status, String currentEmployeeTitle) {
		RequestService reqService = new RequestService();
		List<ApprovedStatus> pendingStatuses = getAllPendingApprovedStatuses();
		List<WaitingStatus> waitingStatuses = getWaitingStatuses();
		List<ApprovedStatus> rejectedStatuses = getAllRejectedStatuses();
		List<WaitingStatus> reqInfoStatuses = getAllReqInfoStatuses();
		int tempAppStatus = -1;
		int tempWaitStatus = -1;
		int approvedStatusId = -1;
		int waitingStatusId = -1;
		
		if(status.equals("approve")) {
			for(ApprovedStatus appStatus : pendingStatuses) {
				if (appStatus.getApprovalStatus().toLowerCase().contains(currentEmployeeTitle.toLowerCase())) {
					tempAppStatus = appStatus.getId();
				}
			}
			for(WaitingStatus waitStatus : waitingStatuses) {
				if (waitStatus.getWaitingStatus().toLowerCase().contains(currentEmployeeTitle.toLowerCase())) {
					tempWaitStatus = waitStatus.getId();
				}
			}
			
			waitingStatusId = tempWaitStatus + 1;
			approvedStatusId = tempAppStatus + 1;
			// now do an update to update both fields
			reqService.updateRequest(requestId, approvedStatusId, waitingStatusId);
		} else if (status.equals("reqinfo")) {
			for(WaitingStatus infoStatus : reqInfoStatuses) {
				if (infoStatus.getWaitingStatus().toLowerCase().contains(currentEmployeeTitle.toLowerCase())) {
					waitingStatusId = infoStatus.getId();
				}
			}
			// now do an update with only waiting status
			reqService.updateRequestInfo(requestId,waitingStatusId);
		} else if (status.equals("rejected")) {
			for(ApprovedStatus rejectedStatus : rejectedStatuses) {
				if (rejectedStatus.getApprovalStatus().toLowerCase().contains(currentEmployeeTitle.toLowerCase())) {
					approvedStatusId = rejectedStatus.getId();
				}
				
			}
			// now do an update with approved status
			reqService.updateRejectedRequest(requestId, approvedStatusId);
		}
		
		return "Updated Request";
	}
}
