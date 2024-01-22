package com.booking.app.model;

public class Seat {
	private String seatNumber;
	private boolean isBooked;
	private String ticketNumber = "";
	private String phoneNumber = "";

	public Seat(String seatNumber) {
		this.seatNumber = seatNumber;
		this.isBooked = false;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Seat [seatNumber=" + seatNumber + (!"".equals(ticketNumber) ? ", ticketNumber=" + ticketNumber : "") + (!"".equals(phoneNumber) ? ", phoneNumber=" + phoneNumber : "")
				+ "]";
	}

}
