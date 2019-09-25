package com.revature.models;

public class ApprovedStatus {
	
	private int id;
	private String approvalStatus;
	public ApprovedStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ApprovedStatus(int id, String approvalStatus) {
		super();
		this.id = id;
		this.approvalStatus = approvalStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approvalStatus == null) ? 0 : approvalStatus.hashCode());
		result = prime * result + id;
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
		ApprovedStatus other = (ApprovedStatus) obj;
		if (approvalStatus == null) {
			if (other.approvalStatus != null)
				return false;
		} else if (!approvalStatus.equals(other.approvalStatus))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ApprovedStatus [id=" + id + ", approvalStatus=" + approvalStatus + "]";
	}
	
	
	
}
