package com.booking.app;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.booking.app.controller.service.BookingService;
import com.booking.app.controller.service.BookingServiceImpl;
import com.booking.app.exception.CancelBookingException;
import com.booking.app.exception.InvalidBookingException;
import com.booking.app.exception.ShowCreationException;
import com.booking.app.exception.ShowNotFoundExeption;
import com.booking.app.model.Show;
import com.booking.app.repository.BookedPhoneRepo;
import com.booking.app.repository.BookingRepo;
import com.booking.app.repository.ShowRepo;
import com.booking.app.repository.TicketRepo;
import com.booking.app.utils.BookingUtils;

public class BookingServiceTest {

	private final ShowRepo showRepo = new ShowRepo();
	private final BookingRepo bookingRepo = new BookingRepo();
	private final BookedPhoneRepo bookedPhoneRepo = new BookedPhoneRepo();
	private final TicketRepo ticketRepo = new TicketRepo();

	private final BookingService bookingService = new BookingServiceImpl(showRepo, bookingRepo, bookedPhoneRepo, ticketRepo);

	private final String[] bookedSeats = { "A1", "A2", "A3", "B1", "B2" };
	private final String[] invalidBookedSeats = { };
	private final String showNumber = "12345";
	private final String ticketNumber = "12345A1";
	private final String phoneNumber = "1234567";
	private final String phoneNumber2 = "2345678";
	private final String invalidPhoneNumber = "999999999";
	private final String invalidTicketNumber = "XXXXXX";
	
	private final Show validShow = BookingUtils.showInitializer("12345", 5, 5, 2);

	private final Show invalidShow1 = BookingUtils.showInitializer("12345", 27, 5, 2);
	private final Show invalidShow2 = BookingUtils.showInitializer("12345", 26, 11, 2);
	private final Show invalidShow3 = BookingUtils.showInitializer("12345", 26, 10, 1);

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void testCreateShowInvalidRows() throws ShowCreationException {
		exceptionRule.expect(ShowCreationException.class);
		exceptionRule.expectMessage("Maximum rows is 26.");
		bookingService.createShow(invalidShow1);
	}
	

	@Test
	public void testCreateShowInvalidSeatsPerRow() throws ShowCreationException {
		exceptionRule.expect(ShowCreationException.class);
		exceptionRule.expectMessage("Maximum seats per row is 10.");
		bookingService.createShow(invalidShow2);
	}
	
	@Test
	public void testCreateShowInvalidCancellationWindow() throws ShowCreationException {
		exceptionRule.expect(ShowCreationException.class);
		exceptionRule.expectMessage("Minimum time(in mins) to cancel the booking is 2 minutes.");
		bookingService.createShow(invalidShow3);
	}
	
	@Test
	public void testCreateShowAlreadyExist() throws ShowCreationException {
		exceptionRule.expect(ShowCreationException.class);
		exceptionRule.expectMessage("Show number already exists");
		bookingService.createShow(validShow);
		bookingService.createShow(validShow);

	}

	@Test
	public void testGetShowByShowNumber() throws ShowNotFoundExeption {
		exceptionRule.expect(ShowNotFoundExeption.class);
		exceptionRule.expectMessage("Show number does not exist.");
		bookingService.getShowWithAvailableSeats("999999");
	}

	@Test
	public void testGetShowWithAvailableSeats() throws ShowCreationException, ShowNotFoundExeption {
		bookingService.createShow(validShow);
		var show = bookingService.getShowWithAvailableSeats("12345");
		Assert.assertEquals(show.getShowNumber(), validShow.getShowNumber());
		Assert.assertEquals(show.getSeats().size(), validShow.getSeats().size());
		Assert.assertEquals(show.getNumRows(), validShow.getNumRows());
		Assert.assertEquals(show.getSeatsPerRow(), validShow.getSeatsPerRow());
		Assert.assertEquals(show.getCancellationWindowMinutes(), validShow.getCancellationWindowMinutes());
	}

	@Test
	public void testGetShowWithAllocatedSeats() throws ShowNotFoundExeption {
		exceptionRule.expect(ShowNotFoundExeption.class);
		exceptionRule.expectMessage("Show number does not exist.");
		bookingService.getShowWithAllocatedSeats("999999");
	}

	@Test
	public void testCreateBookingInvalidSelectedSeats() throws ShowNotFoundExeption, InvalidBookingException {
		exceptionRule.expect(InvalidBookingException.class);
		exceptionRule.expectMessage("Invalid seats information.");
		bookingService.createBooking(showNumber, phoneNumber, invalidBookedSeats);
	}
	
	@Test
	public void testCreateBookingPhoneAlreadyBooked() throws ShowNotFoundExeption, InvalidBookingException, ShowCreationException {
		exceptionRule.expect(InvalidBookingException.class);
		exceptionRule.expectMessage("Booking already exists for this phone number in the show.");
		bookingService.createShow(validShow);
		bookingService.createBooking(showNumber, phoneNumber, bookedSeats);
		bookingService.createBooking(showNumber, phoneNumber, bookedSeats);
	}

	@Test
	public void testCreateBookingWithInvalidPhone() throws ShowNotFoundExeption, InvalidBookingException, ShowCreationException {
		exceptionRule.expect(InvalidBookingException.class);
		exceptionRule.expectMessage("Show number does not exist.");
		bookingService.createBooking(showNumber, invalidPhoneNumber, bookedSeats);
	}
	
	@Test
	public void testCreateBookingWithSeatsAlreadyBooked() throws ShowNotFoundExeption, InvalidBookingException, ShowCreationException {
		exceptionRule.expect(InvalidBookingException.class);
		exceptionRule.expectMessage("List of seats are not all bookable. Please check again.");
		bookingService.createShow(validShow);
		bookingService.createBooking(showNumber, phoneNumber, bookedSeats);
		bookingService.createBooking(showNumber, phoneNumber2, bookedSeats);
	}
	
	@Test
	public void testCancelInvalidTicket() throws CancelBookingException, ShowCreationException {
		exceptionRule.expect(CancelBookingException.class);
		bookingService.createShow(validShow);
		exceptionRule.expectMessage("Ticket number does not exist.");
		bookingService.cancelTicket(invalidTicketNumber, phoneNumber);
	}
	
	@Test
	public void testCancelTicketWithInvalidPhoneNumber() throws CancelBookingException, ShowCreationException, InvalidBookingException {
		exceptionRule.expect(CancelBookingException.class);
		exceptionRule.expectMessage("Invalid phone number provided for the ticket booking.");
		bookingService.createShow(validShow);
		bookingService.createBooking(showNumber, phoneNumber, bookedSeats);
		bookingService.cancelTicket(ticketNumber, invalidPhoneNumber);
	}
	
}
