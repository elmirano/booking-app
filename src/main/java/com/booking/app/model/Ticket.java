package com.booking.app.model;

public class Ticket {

	private String ticketNumber;
	private String phoneNumber;
	private String showNumber;

	public Ticket(String ticketNumber, String phoneNumber, String showNumber) {
		super();
		this.ticketNumber = ticketNumber;
		this.phoneNumber = phoneNumber;
		this.showNumber = showNumber;
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

	public String getShowNumber() {
		return showNumber;
	}

	public void setShowNumber(String showNumber) {
		this.showNumber = showNumber;
	}

	@Override
	public String toString() {
		return "Ticket [ticketNumber=" + ticketNumber + ", phoneNumber=" + phoneNumber + ", showNumber=" + showNumber
				+ "]";
	}
}
