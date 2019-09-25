package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PSQLException;

import com.revature.models.ApprovedStatus;
import com.revature.models.EmployeePosition;
import com.revature.models.EventType;
import com.revature.models.WaitingStatus;
import com.revature.util.ConnectionClosers;
import com.revature.util.ConnectionFactory;

public class LookUpRepositoryImpl implements LookUpRepository {

	@Override
	public List<WaitingStatus> getAllWaitingStatuses() {
		List<WaitingStatus> waitingStatuses = new ArrayList<WaitingStatus>();
		WaitingStatus tempStatus = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("select * from waiting_status;");
			set = stmt.executeQuery(); 
			
			while(set.next()) {
				tempStatus = new WaitingStatus(
						set.getInt(1),
						set.getString(2)
						);
				waitingStatuses.add(tempStatus);
			}
			
		} catch (PSQLException e) {
			waitingStatuses = null;
		}
		catch(SQLException e) {
			waitingStatuses = null;
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		
		return waitingStatuses;
	}

	@Override
	public List<ApprovedStatus> getAllApprovedStatuses() {
		List<ApprovedStatus> approvedStatuses = new ArrayList<ApprovedStatus>();
		ApprovedStatus tempStatus = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("select * from approved_status;");
			set = stmt.executeQuery(); 
			
			while(set.next()) {
				tempStatus = new ApprovedStatus(
						set.getInt(1),
						set.getString(2)
						);
				approvedStatuses.add(tempStatus);
			}			
			
		} catch (PSQLException e) {
			approvedStatuses = null;
		}
		catch(SQLException e) {
			approvedStatuses = null;
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		
		return approvedStatuses;
	}

	@Override
	public List<EmployeePosition> getAllEmployeePositions() {
		List<EmployeePosition> employeePositions = new ArrayList<EmployeePosition>();
		EmployeePosition tempPosition = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("select * from position_level;");
			set = stmt.executeQuery(); 
			
			while(set.next()) {
				tempPosition = new EmployeePosition(
						set.getInt(1),
						set.getString(2)
						);
				employeePositions.add(tempPosition);
			}			
			
		} catch (PSQLException e) {
			employeePositions = null;
		}
		catch(SQLException e) {
			employeePositions = null;
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		
		return employeePositions;
	}

	@Override
	public List<EventType> getAllEventTypes() {
		List<EventType> eventTypes = new ArrayList<EventType>();
		EventType tempEvent = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("select * from event_type;");
			set = stmt.executeQuery(); 
			
			while(set.next()) {
				tempEvent = new EventType(
						set.getInt(1), 
						set.getString(2), 
						set.getString(3), 
						set.getDouble(4)
						);
				eventTypes.add(tempEvent);
			}			
			
		} catch (PSQLException e) {
			eventTypes = null;
		}
		catch(SQLException e) {
			eventTypes = null;
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		
		return eventTypes;
	}

}
