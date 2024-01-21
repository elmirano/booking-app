package com.booking.app.repository;

import java.util.HashMap;
import java.util.Map;

import com.booking.app.model.Ticket;

public class TicketRepo implements Repository<Ticket> {

	private Map<String, Ticket> tickets = new HashMap<>();

	@Override
	public boolean save(Ticket ticket) {
		tickets.put(ticket.getTicketNumber(), ticket);
		return true;
	}

	@Override
	public Ticket findById(String ticketNumber) {
		return tickets.get(ticketNumber);
	}

	@Override
	public void delete(String ticketNumber) {
		tickets.remove(ticketNumber);
	}

}
