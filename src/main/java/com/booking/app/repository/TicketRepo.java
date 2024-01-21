package com.booking.app.repository;

import java.util.HashMap;
import java.util.Map;

import com.booking.app.model.Ticket;

public class TicketRepo {

	private Map<String, Ticket> tickets = new HashMap<>();

	public boolean save(Ticket ticket) {
		tickets.put(ticket.getTicketNumber(), ticket);
		return true;
	}

	public Ticket findById(String ticketNumber) {
		return tickets.get(ticketNumber);
	}

	public void delete(String ticketNumber) {
		tickets.remove(ticketNumber);
	}

}
