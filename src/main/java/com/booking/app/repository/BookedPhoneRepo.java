package com.booking.app.repository;

import java.util.HashMap;
import java.util.Map;

import com.booking.app.model.BookedPhone;

public class BookedPhoneRepo {

	private Map<String, BookedPhone> bookedPhones = new HashMap<>();

	public BookedPhone findById(String phneNumber) {
		return bookedPhones.get(phneNumber);
	}

	public boolean save(BookedPhone bookedPhone) {
		bookedPhones.put(bookedPhone.getPhoneNumber(), bookedPhone);
		return true;
	}

}
