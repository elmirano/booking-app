package com.booking.app;

import com.booking.app.controller.BookingController;
import com.booking.app.controller.service.BookingService;
import com.booking.app.exception.CancelBookingException;
import com.booking.app.exception.InvalidBookingException;
import com.booking.app.exception.InvalidShowException;
import com.booking.app.exception.PhoneAlreadyBookedException;
import com.booking.app.exception.ShowAlreadyExistsException;
import com.booking.app.exception.ShowNotFoundExeption;
import com.booking.app.repository.BookedPhoneRepo;
import com.booking.app.repository.BookingRepo;
import com.booking.app.repository.ShowRepo;
import com.booking.app.repository.TicketRepo;

/*
 * This component serves as the main component for the back end services
 * containing the controller, service and the repository objects
 */
public class ShowBookingBackendApp {

	private static ShowBookingBackendApp instance;
	private BookingController bookingController;

	private ShowBookingBackendApp() {
	}

	public static ShowBookingBackendApp getInstance() {
		if (instance == null) {
			instance = initializeApp();
		}
		return instance;
	}

	// Initialize the component's dependencies
	public static ShowBookingBackendApp initializeApp() {
		ShowRepo showRepoInstance = new ShowRepo();
		BookingRepo bookingRepoInstance = new BookingRepo();
		BookedPhoneRepo bookedPhoneRepoInstance = new BookedPhoneRepo();
		TicketRepo ticketRepoInstance = new TicketRepo();
		BookingService bookingServiceIstance = new BookingService(showRepoInstance, bookingRepoInstance,
				bookedPhoneRepoInstance, ticketRepoInstance);
		BookingController bookingController = new BookingController(bookingServiceIstance);
		ShowBookingBackendApp appInstance = new ShowBookingBackendApp();
		appInstance.setBookingController(bookingController);
		return appInstance;
	}

	private void setBookingController(BookingController bookingController) {
		this.bookingController = bookingController;
	}

	public void addShow(String showNumber, int numRows, int seatsPerRow, int cancellationWindowMinutes) {
		try {
			bookingController.createShow(showNumber, numRows, seatsPerRow, cancellationWindowMinutes);
			System.out.println("Show " + showNumber + " added successfully.");

		} catch (ShowAlreadyExistsException | InvalidShowException e) {
			System.out.printf("Invalid show number. %s\n", e.getMessage());
//			e.printStackTrace();
		}
	}

	public void viewAllocatedSeatsPerShow(String showNumber) {
		try {
			var show = bookingController.viewShowAllocatedSeats(showNumber);
			System.out.println("Show Number: " + show.getShowNumber());
			System.out.println("Seat allocations:");
			show.getSeats().stream().forEach(System.out::println);

		} catch (ShowNotFoundExeption e) {
			System.out.printf("Invalid show number. %s\n", e.getMessage());
//			e.printStackTrace();
		}
	}

	public void viewAvailableSeatsPerShow(String showNumber) {
		try {
			var show = bookingController.viewShowAvailableSeats(showNumber);
			System.out.println("Show Number: " + show.getShowNumber());
			System.out.println("Available seats:");
			show.getSeats().stream().forEach(System.out::println);

		} catch (ShowNotFoundExeption e) {
			System.out.printf("Invalid show number. %s\n", e.getMessage());
//			e.printStackTrace();
		}
	}

	public void bookShow(String showNumber, String phoneNumber, String[] selectedSeats) {

		try {
			bookingController.createBooking(showNumber, phoneNumber, selectedSeats);
			System.out.println("Booking created successfully.");
		} catch (PhoneAlreadyBookedException | InvalidBookingException | ShowNotFoundExeption e) {
			System.out.printf("Invalid booking. %s\n", e.getMessage());
//			e.printStackTrace();
		}
	}

	public void cancelTicket(String ticketNumber, String phoneNumber) {

		try {
			bookingController.cancelTicket(ticketNumber, phoneNumber);
			System.out.println("Ticket cancelled successfully.");
		} catch (CancelBookingException e) {
			System.out.printf("Invalid booking. %s\n", e.getMessage());
//			e.printStackTrace();
		}
	}
}
