package com.revature.service;

import java.util.List;

import com.revature.models.Request;
import com.revature.repository.RequestRepositoryImpl;

public class RequestService {
	public List<Request> getAllRequestsByEmployeeId(int employeeId) {
		return new RequestRepositoryImpl().getAllRequestsByEmployeeId(employeeId);
	}

	public boolean insertNewRequest(Request request) {
		return new RequestRepositoryImpl().insertNewRequest(request);
	}

	public List<Request> getAllPendingRequestsByEmployeeLevel(String currentEmployeeTitle) {
		return new RequestRepositoryImpl().getAllPendingRequestsByEmployeeLevel(currentEmployeeTitle);
	}

	public Request getRequestToApprove(int requestToApproveId) {
		return new RequestRepositoryImpl().getRequestToApprove(requestToApproveId);
	}

	public void updateRequest(int requestId, int approvedStatusId, int waitingStatusId) {
		new RequestRepositoryImpl().updateRequest(requestId, approvedStatusId, waitingStatusId);
	}

	public void updateRequestInfo(int requestId, int waitingStatusId) {
		new RequestRepositoryImpl().updateRequestInfo(requestId, waitingStatusId);
	}

	public void updateRejectedRequest(int requestId, int approvedStatusId) {
		new RequestRepositoryImpl().updateRejectedRequest(requestId, approvedStatusId);
	}
}
