package com.booking.app.model;

public class Booking {

	private String showNumber;
	private String phoneNumber;

	public Booking(String showNumber, String phoneNumber) {
		this.showNumber = showNumber;
		this.phoneNumber = phoneNumber;
	}

	public String getShowNumber() {
		return showNumber;
	}

	public void setShowNumber(String showNumber) {
		this.showNumber = showNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Booking [showNumber=" + showNumber + ", phoneNumber=" + phoneNumber + "]";
	}

}
