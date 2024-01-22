package com.booking.app.controller.service;

import com.booking.app.exception.CancelBookingException;
import com.booking.app.exception.InvalidBookingException;
import com.booking.app.exception.ShowCreationException;
import com.booking.app.exception.ShowAlreadyExistsException;
import com.booking.app.exception.ShowNotFoundExeption;
import com.booking.app.model.Show;

public interface BookingService {

	boolean createShow(Show show) throws ShowCreationException;

	Show getShowWithAvailableSeats(String showNumber) throws ShowNotFoundExeption;

	Show getShowWithAllocatedSeats(String showNumber) throws ShowNotFoundExeption;

	boolean createBooking(String showNumber, String phoneNumber, String[] selectedSeats)
			throws InvalidBookingException;

	void cancelTicket(String ticketNumber, String phoneNumber) throws CancelBookingException;

}