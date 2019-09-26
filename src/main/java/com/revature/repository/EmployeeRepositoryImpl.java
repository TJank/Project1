package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

import com.revature.models.Employee;
import com.revature.util.ConnectionClosers;
import com.revature.util.ConnectionFactory;

public class EmployeeRepositoryImpl implements EmployeeRepository {

	@Override
	// validate user login and return the employee id if valid
	public int accountLogin(String email, String password) {
		int employeeId = -1;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		String databasePassword = "";
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("select email, password, employeeid from user_account where email = ?");
			stmt.setString(1, email);
			set = stmt.executeQuery(); // this returns a result
			
			while(set.next()) {
				databasePassword = set.getString(2);
				if(password.equals(databasePassword)) {
						employeeId = set.getInt(3);
				}
				else {
					employeeId = -1;
				}
			}			
			
		} catch (PSQLException e) {
			employeeId = -1;
		}
		catch(SQLException e) {
			employeeId = -1;
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		
		return employeeId;
	}

	@Override
	// get all of the current employee's information based upon their employee ids
	public Employee getEmployeeInfo(int employeeId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet set = null;
		ResultSet set2 = null;
		Employee currentEmployee = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement(
					"select  "
					+ "UA.employeeid, UA.email, "
					+ "E.firstname, E.lastname, E.availableamount, "
					+ "PL.id, PL.title, E.bossid "
					+ "FROM employee as E "
					+ "inner join user_account as UA "
					+ "on E.id = UA.employeeid "
					+ "inner join employee_position as EP "
					+ "on E.id = EP.employeeid "
					+ "inner join position_level as PL "
					+ "on PL.id = EP.positionlevelid "
					+ "WHERE EP.positionlevelid = "
						+ "(select "
							+ "max(PL.id ) "
							+ "from position_level as PL "
							+ "inner join employee_position as EP "
							+ "on PL.id = EP.positionlevelid where EP.employeeid = ?)");
			stmt.setInt(1, employeeId);
			set = stmt.executeQuery(); // this returns all of the employee information
			
			while(set.next()) {
				currentEmployee = 
						new Employee(
								set.getInt(1), 
								set.getString(2), 
								set.getString(3), 
								set.getString(4), 
								set.getInt(5), 
								set.getInt(6), 
								set.getString(7),
								set.getInt(8));
			}
			
			stmt2 = conn.prepareStatement("select min(PL.title) "
					+ "from position_level as PL inner join employee_position "
					+ "as EP on PL.id = EP.positionlevelid where EP.employeeid = ?");
			stmt2.setInt(1, currentEmployee.getBossId());
			set2 = stmt2.executeQuery();
			
			while(set2.next()) {
				currentEmployee.setBossTitle(set2.getString(1));
			}
			
			
			
		} catch (PSQLException e) {
			currentEmployee = null;
		}
		catch(SQLException e) {
			currentEmployee = null;
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
			if(set2 != null) {
				ConnectionClosers.closeResultSet(set2);;
			}
			if(stmt2 != null) {
				ConnectionClosers.closeStatement(stmt2);
			}
		}
		
		
		return currentEmployee;
	}

	public void updateEmployee(Employee currentEmployee) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("update employee set availableamount = ? where id = ?;");
			stmt.setDouble(1, currentEmployee.getAvailableAmount());
			stmt.setInt(2, currentEmployee.getId());
			stmt.execute(); // this returns a result
			
			
		} catch (PSQLException e) {
		}
		catch(SQLException e) {
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
		}
		
	}
	
}
