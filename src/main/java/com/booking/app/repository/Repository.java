package com.booking.app.repository;

public interface Repository<T> {

	boolean save(T ticket);

	T findById(String ticketNumber);

	void delete(String ticketNumber);

}