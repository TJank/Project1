package com.revature.repository;

import java.util.List;

import com.revature.models.ApprovedStatus;
import com.revature.models.EmployeePosition;
import com.revature.models.EventType;
import com.revature.models.WaitingStatus;

public interface LookUpRepository {
	
	List<WaitingStatus> getWaitingStatuses();
	List<ApprovedStatus> getAllPendingApprovedStatuses();
	List<EmployeePosition> getAllEmployeePositions();
	List<EventType> getAllEventTypes();
	
}
