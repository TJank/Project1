package com.revature.repository;

import java.util.List;

import com.revature.models.Request;

public interface RequestRepository {
	
	// get requests per employee to be viewed on login
	List<Request> getAllRequestsByEmployeeId(int employeeId);
	
	public boolean insertNewRequest(Request request);
	
	public List<Request> getAllPendingRequestsByEmployeeLevel(String currentEmployeeTitle);
	
}
