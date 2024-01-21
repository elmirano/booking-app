package com.booking.app.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.booking.app.model.Booking;

public class BookingRepo {

	private Map<String, List<Booking>> bookingList = new HashMap<>();

	public List<Booking> findById(String showNumber) {
		return bookingList.get(showNumber);
	}

	public boolean save(Booking booking) {
		var bookings = bookingList.get(booking.getShowNumber());
		if (bookings == null) {
			List<Booking> newList = new ArrayList<>();
			newList.add(booking);
			bookingList.put(booking.getShowNumber(), newList);
		} else {
			bookings.add(booking);
		}
		return true;
	}
}
