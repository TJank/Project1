package com.revature.models;

public class EventType {
	
	private int id;
	private String eventType;
	private String gradeFormat;
	private double rate;
	public EventType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EventType(int id, String eventType, String gradeFormat, double rate) {
		super();
		this.id = id;
		this.eventType = eventType;
		this.gradeFormat = gradeFormat;
		this.rate = rate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getGradeFormat() {
		return gradeFormat;
	}
	public void setGradeFormat(String gradeFormat) {
		this.gradeFormat = gradeFormat;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + ((gradeFormat == null) ? 0 : gradeFormat.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(rate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		EventType other = (EventType) obj;
		if (eventType == null) {
			if (other.eventType != null)
				return false;
		} else if (!eventType.equals(other.eventType))
			return false;
		if (gradeFormat == null) {
			if (other.gradeFormat != null)
				return false;
		} else if (!gradeFormat.equals(other.gradeFormat))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "EvenType [id=" + id + ", eventType=" + eventType + ", gradeFormat=" + gradeFormat + ", rate=" + rate
				+ "]";
	}
}
