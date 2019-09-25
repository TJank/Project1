package com.revature.service;

import java.util.List;

import com.revature.models.ApprovedStatus;
import com.revature.models.EmployeePosition;
import com.revature.models.EventType;
import com.revature.models.WaitingStatus;
import com.revature.repository.LookUpRepositoryImpl;

public class LoopUpService {
	
	public List<WaitingStatus> getAllWaitingStatuses() {
		return new LookUpRepositoryImpl().getAllWaitingStatuses();
	}
	
	public List<ApprovedStatus> getAllApprovedStatuses() {
		return new LookUpRepositoryImpl().getAllApprovedStatuses();
	}
	
	public List<EmployeePosition> getAllEmployeePositions() {
		return new LookUpRepositoryImpl().getAllEmployeePositions();
	}
	
	public List<EventType> getAllEventTypes() {
		return new LookUpRepositoryImpl().getAllEventTypes();
	}
	
}
