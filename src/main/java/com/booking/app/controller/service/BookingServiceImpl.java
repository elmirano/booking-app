package com.booking.app.controller.service;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.booking.app.exception.CancelBookingException;
import com.booking.app.exception.InvalidBookingException;
import com.booking.app.exception.InvalidShowException;
import com.booking.app.exception.PhoneAlreadyBookedException;
import com.booking.app.exception.ShowAlreadyExistsException;
import com.booking.app.exception.ShowNotFoundExeption;
import com.booking.app.model.BookedPhone;
import com.booking.app.model.Booking;
import com.booking.app.model.Seat;
import com.booking.app.model.Show;
import com.booking.app.model.Ticket;
import com.booking.app.repository.BookedPhoneRepo;
import com.booking.app.repository.BookingRepo;
import com.booking.app.repository.Repository;
import com.booking.app.repository.ShowRepo;
import com.booking.app.repository.TicketRepo;

public class BookingServiceImpl implements BookingService {

	private ShowRepo showRepo;
	private BookingRepo bookingRepo;
	private BookedPhoneRepo bookedPhoneRepo;
	private TicketRepo ticketRepo;

	public BookingServiceImpl(ShowRepo showRepo, BookingRepo bookingRepo, BookedPhoneRepo bookedPhoneRepo,
			TicketRepo ticketRepo) {
		this.showRepo = showRepo;
		this.bookingRepo = bookingRepo;
		this.bookedPhoneRepo = bookedPhoneRepo;
		this.ticketRepo = ticketRepo;
	}

	@Override
	public boolean createShow(Show show) throws ShowAlreadyExistsException, InvalidShowException {
		if (show.getNumRows() > 26) {
			throw new InvalidShowException("Maximum rows is 26.");
		}
		if (show.getSeatsPerRow() > 10) {
			throw new InvalidShowException("Maximum seats per row is 10.");
		}
		if (show.getCancellationWindowMinutes() < 2) {
			throw new InvalidShowException("Minimum time(in mins) to cancel the booking is 2 minutes.");
		}
		var fetchedShow = showRepo.findById(show.getShowNumber());
		if (fetchedShow != null) {
			throw new ShowAlreadyExistsException("Show number already exists");
		}
		return showRepo.save(show);
	}

	@Override
	public Show getShowByShowNumber(String shoNumber) throws ShowNotFoundExeption {
		var show = showRepo.findById(shoNumber);
		if (show == null) {
			throw new ShowNotFoundExeption("Show number does not exist.");
		}
		return show;
	}

	@Override
	public Show getShowWithAvailableSeats(String showNumber) throws ShowNotFoundExeption {
		var show = showRepo.findById(showNumber);
		if (show == null) {
			throw new ShowNotFoundExeption("Show number does not exist.");
		}
		var tempShow = new Show(show.getShowNumber(), show.getNumRows(), show.getSeatsPerRow(),
				show.getCancellationWindowMinutes(), null);

		var seats = show.getSeats().stream().filter(seat -> !seat.isBooked()).collect(Collectors.toList());
		tempShow.setSeats(seats);

		return tempShow;
	}

	@Override
	public Show getShowWithAllocatedSeats(String showNumber) throws ShowNotFoundExeption {
		var show = showRepo.findById(showNumber);
		if (show == null) {
			throw new ShowNotFoundExeption("Show number does not exist.");
		}

		var tempShow = new Show(show.getShowNumber(), show.getNumRows(), show.getSeatsPerRow(),
				show.getCancellationWindowMinutes(), null);
		var seats = show.getSeats().stream().filter(seat -> seat.isBooked()).collect(Collectors.toList());
		tempShow.setSeats(seats);

		return tempShow;
	}

	@Override
	public boolean createBooking(String showNumber, String phoneNumber, String[] selectedSeats)
			throws PhoneAlreadyBookedException, ShowNotFoundExeption, InvalidBookingException {

		if (selectedSeats.length == 0) {
			throw new InvalidBookingException("Invalid seats information.");
		}

		// validate phone if already been used in booking
		if (!isValidBookingPhone(showNumber, phoneNumber)) {
			throw new PhoneAlreadyBookedException("Booking already exists for this phone number in the show.");
		}

		var show = showRepo.findById(showNumber);
		if (show == null) {
			throw new ShowNotFoundExeption("Show number does not exist.");
		}

		var seatFilter = Arrays.asList(selectedSeats);
		var filteredSeats = show.getSeats().stream()
				.filter(seat -> (seatFilter.contains(seat.getSeatNumber()) && !seat.isBooked()))
				.collect(Collectors.toList());

		// validate availability of selected seats in the show
		if (filteredSeats.size() != seatFilter.size()) {
			throw new InvalidBookingException("List of seats are not all bookable. Please check again.");
		}

		long currentTime = System.currentTimeMillis();

		// save in bookingRepo
		bookingRepo.save(new Booking(showNumber, phoneNumber));

		// save in bookedPhoneRepo
		bookedPhoneRepo.save(new BookedPhone(phoneNumber, currentTime));

		// update seats in showRepo including ticketnumber, time booked and isbooked
		for (Seat seat : show.getSeats()) {
			if (seatFilter.contains(seat.getSeatNumber())) {
				seat.setBooked(true);
				seat.setTicketNumber(showNumber.concat(seat.getSeatNumber()));
				ticketRepo.save(new Ticket(seat.getTicketNumber(), phoneNumber, showNumber));
			}
		}
		showRepo.save(show);
		return false;
	}

	@Override
	public void cancelTicket(String ticketNumber, String phoneNumber) throws CancelBookingException {

		var ticket = ticketRepo.findById(ticketNumber);

		if (ticket == null) {
			throw new CancelBookingException("Ticket number does not exist.");
		}

		if (!ticket.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
			throw new CancelBookingException("Invalid phone number provided for the ticket booking.");
		}

		long currentTime = System.currentTimeMillis();
		var bookedPhone = bookedPhoneRepo.findById(phoneNumber);

		if (null == bookedPhone) {
			throw new CancelBookingException("Phone number does not exist.");
		}

		// retrieve the show that has the ticket number in the seat
		var show = showRepo.findById(ticket.getShowNumber());

		// verify if the ticket can be cancelled by checking the time window
		if (currentTime - bookedPhone.getTimeBooked() > show.getCancellationWindowMinutes() * 60 * 1000) {
			throw new CancelBookingException("Cancellation not allowed after the time window.");
		}

		// update the seat to be bookable again and remove the ticket number from the
		// seat
		show.getSeats().stream().filter(seat -> seat.getTicketNumber().equalsIgnoreCase(ticket.getTicketNumber()))
				.forEach(seat -> {
					seat.setTicketNumber("");
					seat.setBooked(false);
				});
		showRepo.save(show);

		// delete the ticket from the ticket repo
		ticketRepo.delete(ticketNumber);
	}

	private boolean isValidBookingPhone(String showNumber, String phoneNumber) {

		var bookings = bookingRepo.findBookingsById(showNumber);
		if (bookings == null) {
			return true;
		}
		bookings.removeIf(booking -> !phoneNumber.equalsIgnoreCase(booking.getPhoneNumber()));
		return bookings.size() == 0;

	}

}
