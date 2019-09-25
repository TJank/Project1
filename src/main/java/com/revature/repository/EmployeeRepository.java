package com.revature.repository;

import com.revature.models.Employee;

public interface EmployeeRepository {
	
	int accountLogin(String email, String password); // handle the login request
	Employee getEmployeeInfo(int employeeId); // get all of the employee information
	
	
}
