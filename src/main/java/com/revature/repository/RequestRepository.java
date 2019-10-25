package com.revature.repository;

import java.util.List;

import com.revature.models.Request;

public interface RequestRepository {
	
	// get requests per employee to be viewed on login
	List<Request> getAllRequestsByEmployeeId(int employeeId);
	public void updateRequestInfo(int requestId, int waitingStatusId);
	public void updateMoreInfoRequest(int requestId, String moreInfo, int approvedStatusId);
	public boolean insertNewRequest(Request request);
	public void cancelRequest(int requestId);
	public List<Request> getAllPendingRequestsByEmployeeLevel(String currentEmployeeTitle);
	public void updateRejectedRequest(int requestId, int approvedStatusId);
	public void updateRequest(int requestId, int approvedStatusId, int waitingStatusId);
	public Request getRequestToApprove(int requestToApproveId);
	
}
