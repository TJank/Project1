package com.revature.models;

import java.sql.Time;

public class Request {
	
	private int id;
	private int employeeId;
	private int eventTypeId;
	private String eventType;
	private String gradeFormat;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeeTitle;
	private String approvalStatus;
	private String waitingStatus;
	private String requestedDate;
	private String eventStartDate;
	private String eventStartTime;
	private boolean isUrgent;
	private String justification;
	private String description;
	private String eventLocation;
	private double eventCost;
	private double projectedAmount;
	private String workMissed;
	private String finalGrade;
	private String furtherInfo;
	private double awardedAmount;
	
	public Request() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Request(int employeeId, String employeeTitle, int eventTypeId, String eventStartDate, String eventStartTime, String justification,
			String description, String eventLocation, double eventCost, double projectedAmount, String workMissed) {
		super();
		this.employeeId = employeeId;
		this.employeeTitle = employeeTitle;
		this.eventTypeId = eventTypeId;
		this.eventStartDate = eventStartDate;
		this.eventStartTime = eventStartTime;
		this.justification = justification;
		this.description = description;
		this.eventLocation = eventLocation;
		this.eventCost = eventCost;
		this.projectedAmount = projectedAmount;
		this.workMissed = workMissed;
	}





	// constructor used when an employee is viewing their own request
	public Request(int id, String eventLocation, String eventType, String eventStartDate, double eventCost, 
			double projectedAmount, double awardedAmount, String waitingStatus, String approvalStatus, 
			   String finalGrade, boolean isUrgent) {
		super();
		this.id = id;
		this.eventType = eventType;
		this.approvalStatus = approvalStatus;
		this.waitingStatus = waitingStatus;
		this.eventStartDate = eventStartDate;
		this.eventLocation = eventLocation;
		this.eventCost = eventCost;
		this.projectedAmount = projectedAmount;
		this.finalGrade = finalGrade;
		this.awardedAmount = awardedAmount;
		this.isUrgent = isUrgent;
	}
	
	
	public int getEventTypeId() {
		return eventTypeId;
	}



	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}



	public String getWorkMissed() {
		return workMissed;
	}



	public void setWorkMissed(String workMissed) {
		this.workMissed = workMissed;
	}

	public String getEventStartTime() {
		return eventStartTime;
	}
	
	public void setEventStartTime(String eventStartTime) {
		this.eventStartTime = eventStartTime;
	}
	
	public double getProjectedAmount() {
		return projectedAmount;
	}



	public void setProjectedAmount(double projectedAmount) {
		this.projectedAmount = projectedAmount;
	}



	public String getFinalGrade() {
		return finalGrade;
	}



	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}



	public String getFurtherInfo() {
		return furtherInfo;
	}



	public void setFurtherInfo(String furtherInfo) {
		this.furtherInfo = furtherInfo;
	}



	public double getAwardedAmount() {
		return awardedAmount;
	}



	public void setAwardedAmount(double awardedAmount) {
		this.awardedAmount = awardedAmount;
	}

	public void setEventCost(double eventCost) {
		this.eventCost = eventCost;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public double getEventCost() {
		return eventCost;
	}

	public void setEventCost(int eventCost) {
		this.eventCost = eventCost;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getGradeFormat() {
		return gradeFormat;
	}

	public void setGradeFormat(String gradeFormat) {
		this.gradeFormat = gradeFormat;
	}

	public boolean isUrgent() {
		return isUrgent;
	}

	public void setUrgent(boolean isUrgent) {
		this.isUrgent = isUrgent;
	}

	public String getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getWaitingStatus() {
		return waitingStatus;
	}

	public void setWaitingStatus(String waitingStatus) {
		this.waitingStatus = waitingStatus;
	}

}
