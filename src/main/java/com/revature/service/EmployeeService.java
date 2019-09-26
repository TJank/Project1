package com.revature.service;

import com.revature.models.Employee;
import com.revature.repository.EmployeeRepositoryImpl;

public class EmployeeService {
	
	public int accountLogin(String email, String password) {
		return new EmployeeRepositoryImpl().accountLogin(email, password);
	}
	
	public Employee getEmployeeInfo(int employeeId) {
		return new EmployeeRepositoryImpl().getEmployeeInfo(employeeId);
	}

	public void updateEmployee(Employee currentEmployee) {
		new EmployeeRepositoryImpl().updateEmployee(currentEmployee);
	}

}
