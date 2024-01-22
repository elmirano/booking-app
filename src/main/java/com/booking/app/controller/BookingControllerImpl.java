package com.booking.app.controller;

import com.booking.app.controller.service.BookingService;
import com.booking.app.exception.CancelBookingException;
import com.booking.app.exception.InvalidBookingException;
import com.booking.app.exception.ShowCreationException;
import com.booking.app.exception.ShowAlreadyExistsException;
import com.booking.app.exception.ShowNotFoundExeption;
import com.booking.app.model.Show;
import com.booking.app.utils.BookingUtils;

public class BookingControllerImpl implements BookingController {

	private BookingService bookingService;

	public BookingControllerImpl(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@Override
	public Show viewShowAvailableSeats(String showNumber) throws ShowNotFoundExeption {
		return bookingService.getShowWithAvailableSeats(showNumber);
	}

	@Override
	public Show viewShowAllocatedSeats(String showNumber) throws ShowNotFoundExeption {
		return bookingService.getShowWithAllocatedSeats(showNumber);
	}

	@Override
	public boolean createBooking(String showNumber, String phoneNumber, String[] selectedSeats)
			throws InvalidBookingException, ShowNotFoundExeption {

		bookingService.createBooking(showNumber, phoneNumber, selectedSeats);
		return true;
	}

	@Override
	public boolean cancelTicket(String ticket, String phoneNumber) throws CancelBookingException {
		bookingService.cancelTicket(ticket, phoneNumber);
		return true;
	}

	@Override
	public boolean createShow(String showNumber, int rows, int seatsPerRow, int cancelWindowInMins)
			throws ShowAlreadyExistsException, ShowCreationException {
		Show show = BookingUtils.showInitializer(showNumber, rows, seatsPerRow, cancelWindowInMins);
		bookingService.createShow(show);
		return true;
	}
}
