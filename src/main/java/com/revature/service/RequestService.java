package com.revature.service;

import java.util.List;

import com.revature.models.Request;
import com.revature.repository.RequestRepositoryImpl;

public class RequestService {
	public List<Request> getAllRequestsByEmployeeId(int employeeId) {
		return new RequestRepositoryImpl().getAllRequestsByEmployeeId(employeeId);
	}
}
