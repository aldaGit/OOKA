package org.bonn.ia.kafka.pojo;

import java.io.Serializable;

public class Employee implements Serializable {

	public Employee(String firstName , String lastName , int employeeID , int leaderShipCompetence , String department) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeID = employeeID;
		this.leaderShipCompetence = leaderShipCompetence;
		this.department = department;
	}

	public Employee() {

	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	private static final long serialVersionUID = 1L;

	private int employeeID;

	private String firstName;

	private String lastName;

	private int leaderShipCompetence;

	private String department;

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getLeaderShipCompetence() {
		return leaderShipCompetence;
	}

	public void setLeaderShipCompetence(int leaderShipCompetence) {
		this.leaderShipCompetence = leaderShipCompetence;
	}
}
