package com.revature.repository;

import java.util.List;

import com.revature.models.Request;

public interface RequestRepository {
	
	// get requests per employee to be viewed on login
	List<Request> getAllRequestsByEmployeeId(int employeeId);
	
	// to do -- the rest xD
	

}
