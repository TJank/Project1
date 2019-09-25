package com.revature.models;

public class WaitingStatus {
	
	private int id;
	private String waitingStatus;
	public WaitingStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WaitingStatus(int id, String waitingStatus) {
		super();
		this.id = id;
		this.waitingStatus = waitingStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWaitingStatus() {
		return waitingStatus;
	}
	public void setWaitingStatus(String waitingStatus) {
		this.waitingStatus = waitingStatus;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((waitingStatus == null) ? 0 : waitingStatus.hashCode());
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
		WaitingStatus other = (WaitingStatus) obj;
		if (id != other.id)
			return false;
		if (waitingStatus == null) {
			if (other.waitingStatus != null)
				return false;
		} else if (!waitingStatus.equals(other.waitingStatus))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WaitingStatus [id=" + id + ", waitingStatus=" + waitingStatus + "]";
	}
}
