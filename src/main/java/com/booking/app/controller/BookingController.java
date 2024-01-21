package com.booking.app.controller;

import com.booking.app.exception.CancelBookingException;
import com.booking.app.exception.InvalidBookingException;
import com.booking.app.exception.InvalidShowException;
import com.booking.app.exception.PhoneAlreadyBookedException;
import com.booking.app.exception.ShowAlreadyExistsException;
import com.booking.app.exception.ShowNotFoundExeption;
import com.booking.app.model.Show;

public interface BookingController {

	Show viewShowAvailableSeats(String showNumber) throws ShowNotFoundExeption;

	Show viewShowAllocatedSeats(String showNumber) throws ShowNotFoundExeption;

	boolean createBooking(String showNumber, String phoneNumber, String[] selectedSeats)
			throws PhoneAlreadyBookedException, InvalidBookingException, ShowNotFoundExeption;

	boolean cancelTicket(String ticket, String phoneNumber) throws CancelBookingException;

	boolean createShow(String showNumber, int rows, int seatsPerRow, int cancelWindowInMins)
			throws ShowAlreadyExistsException, InvalidShowException;

}