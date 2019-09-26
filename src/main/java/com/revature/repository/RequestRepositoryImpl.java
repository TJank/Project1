package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PSQLException;

import com.revature.models.Request;
import com.revature.util.ConnectionClosers;
import com.revature.util.ConnectionFactory;

public class RequestRepositoryImpl implements RequestRepository {

	@Override
	public List<Request> getAllRequestsByEmployeeId(int employeeId) {
		List<Request> pendingRequests = new ArrayList<Request>();
		Request tempRequest = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(
					"select "
						+ "R.id, R.eventlocation, "
						+ "ET.eventtype, R.eventstartdate, "
						+ "R.eventcost, R.projectedamount, "
						+ "R.awardedamount, WS.waitingstatus, "
						+ "APS.approvalstatus, R.finalgrade, R.isurgent "
					+ "from "
						+ "request as R "
						+ "inner join "
						+ "event_type as ET "
							+ "on R.eventtypeid = ET.id "
						+ "inner join "
						+ "approved_status as APS "
							+ "on APS.id = R.approvedstatusid "
						+ "inner join waiting_status as WS "
							+ "on WS.id = R.waitingstatusid "
					+ "where "
						+ "R.employeeid = ?  ;"
					);
			stmt.setInt(1, employeeId);
			set = stmt.executeQuery(); 
			
			while(set.next()) {
				tempRequest = new Request(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getDouble(5),
						set.getDouble(6),
						set.getDouble(7),
						set.getString(8),
						set.getString(9),
						set.getString(10),
						set.getBoolean(11)
						);
				pendingRequests.add(tempRequest);
			}			
			
		} catch (PSQLException e) {
			pendingRequests = null;
		}
		catch(SQLException e) {
			pendingRequests = null;
		} finally {
			if(conn != null) {
				ConnectionClosers.closeConnection(conn);
			}
			if(stmt != null) {
				ConnectionClosers.closeStatement(stmt);
			}
			if(set != null) {
				ConnectionClosers.closeResultSet(set);
			}
		}
		
		return pendingRequests;
	}

	public boolean insertNewRequest(Request request) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean success = false;
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("insert into request(" +
					"id, employeeid, eventtypeid, " + 
					"approvedstatusid, waitingstatusid, "+ 
					"eventstartdate, eventstarttime, " + 
					"isurgent, justification, " + 
					"description, eventlocation," + 
					"eventcost, projectedamount, timemissed) values(" + 
					"default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			stmt.setInt(1, request.getEmployeeId());
			System.out.println("set one value");
			stmt.setInt(2, request.getEventTypeId());
			stmt.setInt(3, request.getApprovalStatusId());
			stmt.setInt(4, request.getWaitingStatusId());
			stmt.setString(5, request.getEventStartDate());
			stmt.setString(6, request.getEventStartTime());
			stmt.setBoolean(7, request.isUrgent());
			stmt.setString(8, request.getJustification());
			stmt.setString(9, request.getDescription());
			stmt.setString(10, request.getEventLocation());
			stmt.setDouble(11, request.getEventCost());
			stmt.setDouble(12, request.getProjectedAmount());
			stmt.setString(13, request.getWorkMissed());
			
			success = stmt.execute();
			
		} catch (PSQLException e) {
//			e.printStackTrace();
			success = false;
		}
		catch(SQLException e) {
//			e.printStackTrace();
			success = false;
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
		}
		
		return success;
	}

	public List<Request> getAllPendingRequestsByEmployeeLevel(String currentEmployeeTitle) {
		List<Request> pendingRequests = new ArrayList<Request>();
		Request tempRequest = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		String setString = "%" + currentEmployeeTitle + "%";
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("select "
						+ "R.id, R.eventlocation, R.eventcost, R.isurgent, "
						+ "E.firstname, E.lastname, ET.eventtype, "
						+ "APS.approvalstatus, WS.waitingstatus "
					+ "from "
						+ "request as R "
						+ "inner join "
						+ "employee as E "
							+ "on E.id = R.employeeid "
						+ "inner join "
						+ "event_type as ET "
							+ "on ET.id = R.eventtypeid "
						+ "inner join "
						+ "approved_status as APS "
							+ "on APS.id = R.approvedstatusid "
						+ "inner join waiting_status as WS "
							+ "on WS.id = R.waitingstatusid "
					+ "where "
						+ "WS.waitingstatus like ? or APS.approvalstatus like ?;");
			stmt.setString(1, setString);
			stmt.setString(2, setString);
			set = stmt.executeQuery(); 
			
			while(set.next()) {
				tempRequest = new Request(
						set.getInt(1), 
						set.getString("eventtype"), 
						set.getString("firstname"), 
						set.getString("lastname"), 
						set.getString("approvalstatus"), 
						set.getString("waitingstatus"), 
						set.getBoolean("isurgent"), 
						set.getString("eventlocation"), 
						set.getDouble("eventcost")
						);				
				pendingRequests.add(tempRequest);
			}			
			
		} catch (PSQLException e) {
			e.printStackTrace();
			pendingRequests = null;
		}
		catch(SQLException e) {
			e.printStackTrace();
			pendingRequests = null;
		} finally {
			if(conn != null) {
				ConnectionClosers.closeConnection(conn);
			}
			if(stmt != null) {
				ConnectionClosers.closeStatement(stmt);
			}
			if(set != null) {
				ConnectionClosers.closeResultSet(set);
			}
		}
		
		return pendingRequests;
	}

	public Request getRequestToApprove(int requestToApproveId) {
		Request request = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(""
					+ "select "
						+ "R.id, R.eventlocation, R.eventstartdate, R.eventstarttime, "
						+ "R.timemissed, R.description, R.eventcost, R.projectedamount, "
						+ "R.justification, R.furtherinformation, E.firstname, E.lastname, "
						+ "ET.eventtype, ET.gradeformat, ET.rate "
					+ "from "
						+ "request as R "
						+ "inner join "
						+ "employee as E "
							+ "on E.id = R.employeeid "
						+ "inner join "
						+ "event_type as ET "
							+ "on ET.id = R.eventtypeid "
					+ "where R.id = ?;");
			stmt.setInt(1, requestToApproveId);
			set = stmt.executeQuery(); 
			
			while(set.next()) {
				request = new Request(
						set.getInt("id"), 
						set.getString("eventtype"), 
						set.getString("gradeformat"), 
						set.getDouble("rate"), 
						set.getString("firstname"), 
						set.getString("lastname"), 
						set.getString("eventstartdate"), 
						set.getString("eventstarttime"), 
						set.getString("justification"), 
						set.getString("description"), 
						set.getString("eventlocation"), 
						set.getDouble("eventcost"), 
						set.getDouble("projectedamount"), 
						set.getString("timemissed"), 
						set.getString("furtherinformation")
						);
			}			
			
		} catch (PSQLException e) {
			request = null;
		}
		catch(SQLException e) {
			request = null;
		} finally {
			if(conn != null) {
				ConnectionClosers.closeConnection(conn);
			}
			if(stmt != null) {
				ConnectionClosers.closeStatement(stmt);
			}
			if(set != null) {
				ConnectionClosers.closeResultSet(set);
			}
		}
		
		return request;
	}

	public void updateRequest(int requestId, int approvedStatusId, int waitingStatusId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("update request "
					+ "set approvedstatusid = ?, waitingstatusid = ? where id = ?");
			stmt.setInt(1, approvedStatusId);
			stmt.setInt(2, waitingStatusId);
			stmt.setInt(3, requestId);
			stmt.execute(); 
			
			
		} catch (PSQLException e) {
		}
		catch(SQLException e) {
		} finally {
			if(conn != null) {
				ConnectionClosers.closeConnection(conn);
			}
			if(stmt != null) {
				ConnectionClosers.closeStatement(stmt);
			}
			if(set != null) {
				ConnectionClosers.closeResultSet(set);
			}
		}
	}

	public void updateRequestInfo(int requestId, int waitingStatusId) {
		System.out.println("made it into update request info");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("update request "
					+ "set waitingstatusid = ? where id = ?");
			stmt.setInt(1, waitingStatusId);
			stmt.setInt(2, requestId);
			stmt.execute(); 
			
			
		} catch (PSQLException e) {
		}
		catch(SQLException e) {
		} finally {
			if(conn != null) {
				ConnectionClosers.closeConnection(conn);
			}
			if(stmt != null) {
				ConnectionClosers.closeStatement(stmt);
			}
			if(set != null) {
				ConnectionClosers.closeResultSet(set);
			}
		}
	}

	public void updateRejectedRequest(int requestId, int approvedStatusId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("update request "
					+ "set approvedstatusid = ? where id = ?");
			stmt.setInt(1, approvedStatusId);
			stmt.setInt(2, requestId);
			stmt.execute(); 
			
			
		} catch (PSQLException e) {
		}
		catch(SQLException e) {
		} finally {
			if(conn != null) {
				ConnectionClosers.closeConnection(conn);
			}
			if(stmt != null) {
				ConnectionClosers.closeStatement(stmt);
			}
			if(set != null) {
				ConnectionClosers.closeResultSet(set);
			}
		}
	}

}
