package com.booking.app.controller;

import com.booking.app.controller.service.BookingService;
import com.booking.app.exception.CancelBookingException;
import com.booking.app.exception.InvalidBookingException;
import com.booking.app.exception.InvalidShowException;
import com.booking.app.exception.PhoneAlreadyBookedException;
import com.booking.app.exception.ShowAlreadyExistsException;
import com.booking.app.exception.ShowNotFoundExeption;
import com.booking.app.model.Show;
import com.booking.app.utils.BookingUtils;

public class BookingController {

	private BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	public Show viewShowAvailableSeats(String showNumber) throws ShowNotFoundExeption {
		return bookingService.getShowWithAvailableSeats(showNumber);
	}

	public Show viewShowAllocatedSeats(String showNumber) throws ShowNotFoundExeption {
		return bookingService.getShowWithAllocatedSeats(showNumber);
	}

	public boolean createBooking(String showNumber, String phoneNumber, String[] selectedSeats)
			throws PhoneAlreadyBookedException, InvalidBookingException, ShowNotFoundExeption {

		bookingService.createBooking(showNumber, phoneNumber, selectedSeats);
		return true;
	}

	public boolean cancelTicket(String ticket, String phoneNumber) throws CancelBookingException {
		bookingService.cancelTicket(ticket, phoneNumber);
		return false;
	}

	public boolean createShow(String showNumber, int rows, int seatsPerRow, int cancelWindowInMins)
			throws ShowAlreadyExistsException, InvalidShowException {
		Show show = BookingUtils.showInitializer(showNumber, rows, seatsPerRow, cancelWindowInMins);
		bookingService.createShow(show);
		return true;
	}
}
