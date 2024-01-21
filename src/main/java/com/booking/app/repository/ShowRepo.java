package com.booking.app.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.booking.app.model.Show;
import com.booking.app.model.Ticket;

public class ShowRepo implements Repository<Show>{

	private Map<String, Show> shows = new HashMap<>();

	public boolean save(Show show) {
		this.shows.put(show.getShowNumber(), show);
		return true;
	}

	public Show findById(String showNumber) {
		return shows.get(showNumber);
	}

	@Override
	public void delete(String ticketNumber) {
		// not implemented		
	}

}
