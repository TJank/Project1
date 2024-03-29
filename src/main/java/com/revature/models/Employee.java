package com.revature.models;

public class Employee {
	
	private int id;
	private String email;
	private String firstName;
	private String lastName;
	private int availableAmount;
	private int titleId;
	private String title;
	private int bossId;
	private String bossTitle;
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Employee(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}


	public Employee(int id, String email, String firstName, String lastName, int availableAmount, int titleId,
			String title, int bossId) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.availableAmount = availableAmount;
		this.titleId = titleId;
		this.title = title;
		this.bossId = bossId;
	}

	public int getBossId() {
		return bossId;
	}

	public void setBossId(int bossId) {
		this.bossId = bossId;
	}

	public String getBossTitle() {
		return bossTitle;
	}

	public void setBossTitle(String bossTitle) {
		this.bossTitle = bossTitle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(int availableAmount) {
		this.availableAmount = availableAmount;
	}

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + availableAmount;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + titleId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (availableAmount != other.availableAmount)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (titleId != other.titleId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", availableAmount=" + availableAmount + ", titleId=" + titleId + ", title=" + title + "]";
	}
}
