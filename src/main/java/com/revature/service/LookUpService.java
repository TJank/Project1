package com.revature.service;

import java.util.List;

import com.revature.models.ApprovedStatus;
import com.revature.models.EmployeePosition;
import com.revature.models.EventType;
import com.revature.models.WaitingStatus;
import com.revature.repository.LookUpRepositoryImpl;

public class LookUpService {
	
	public List<WaitingStatus> getWaitingStatuses() {
		return new LookUpRepositoryImpl().getWaitingStatuses();
	}
	
	public List<ApprovedStatus> getAllPendingApprovedStatuses() {
		return new LookUpRepositoryImpl().getAllPendingApprovedStatuses();
	}
	
	public List<EmployeePosition> getAllEmployeePositions() {
		return new LookUpRepositoryImpl().getAllEmployeePositions();
	}
	
	public List<EventType> getAllEventTypes() {
		return new LookUpRepositoryImpl().getAllEventTypes();
	}

	public List<ApprovedStatus> getRejectedStatuses() {
		return new LookUpRepositoryImpl().getRejectedStatuses();
	}

	public List<WaitingStatus> getAllReqInfoStatuses() {
		return new LookUpRepositoryImpl().getAllReqInfoStatuses();
	}
	
}
