package com.booking.app.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.booking.app.model.Show;

public class ShowRepo {

	private Map<String, Show> shows = new HashMap<>();

	public boolean save(Show show) {
		this.shows.put(show.getShowNumber(), show);
		return true;
	}

	public Optional<Show> findById(String showNumber) {
		var show = shows.get(showNumber);
		return Optional.ofNullable(show);
	}

}
