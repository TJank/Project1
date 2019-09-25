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
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		
		return pendingRequests;
	}

}
