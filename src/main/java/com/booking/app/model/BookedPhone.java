package com.booking.app.model;

public class BookedPhone {

	private String phoneNumber;
	private Long timeBooked;

	public BookedPhone(String phoneNumber, Long timeBooked) {
		super();
		this.phoneNumber = phoneNumber;
		this.timeBooked = timeBooked;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getTimeBooked() {
		return timeBooked;
	}

	public void setTimeBooked(Long timeBooked) {
		this.timeBooked = timeBooked;
	}

}
