package com.booking.app.controller.service;

import com.booking.app.exception.CancelBookingException;
import com.booking.app.exception.InvalidBookingException;
import com.booking.app.exception.InvalidShowException;
import com.booking.app.exception.PhoneAlreadyBookedException;
import com.booking.app.exception.ShowAlreadyExistsException;
import com.booking.app.exception.ShowNotFoundExeption;
import com.booking.app.model.Show;

public interface BookingService {

	boolean createShow(Show show) throws ShowAlreadyExistsException, InvalidShowException;

	Show getShowByShowNumber(String shoNumber) throws ShowNotFoundExeption;

	Show getShowWithAvailableSeats(String showNumber) throws ShowNotFoundExeption;

	Show getShowWithAllocatedSeats(String showNumber) throws ShowNotFoundExeption;

	boolean createBooking(String showNumber, String phoneNumber, String[] selectedSeats)
			throws PhoneAlreadyBookedException, ShowNotFoundExeption, InvalidBookingException;

	void cancelTicket(String ticketNumber, String phoneNumber) throws CancelBookingException;

}