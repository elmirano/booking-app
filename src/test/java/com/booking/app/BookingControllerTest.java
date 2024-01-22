package com.booking.app;

import org.junit.Assert;
import org.junit.Test;

import com.booking.app.controller.BookingControllerImpl;
import com.booking.app.controller.service.BookingService;
import com.booking.app.controller.service.BookingServiceImpl;
import com.booking.app.exception.CancelBookingException;
import com.booking.app.exception.InvalidBookingException;
import com.booking.app.exception.ShowCreationException;
import com.booking.app.exception.ShowAlreadyExistsException;
import com.booking.app.exception.ShowNotFoundExeption;
import com.booking.app.model.Show;
import com.booking.app.repository.BookedPhoneRepo;
import com.booking.app.repository.BookingRepo;
import com.booking.app.repository.ShowRepo;
import com.booking.app.repository.TicketRepo;
import com.booking.app.utils.BookingUtils;

public class BookingControllerTest {

	ShowRepo showRepo = new ShowRepo();
	BookingRepo bookingRepo = new BookingRepo();
	BookedPhoneRepo bookedPhoneRepo = new BookedPhoneRepo();
	TicketRepo ticketRepo = new TicketRepo();
	
	BookingService bookingService= new BookingServiceImpl(showRepo, bookingRepo, bookedPhoneRepo, ticketRepo);	
	BookingControllerImpl bookingController= new BookingControllerImpl(bookingService);
	
	Show showResult = BookingUtils.showInitializer("12345", 5, 5, 2);
	String [] bookedSeats = {"A1","A2","A3","B1","B2"};
	String ticketNumber = "12345A1";
	String phoneNumber = "1234567";

	@Test
	public void testCreateShow() throws ShowAlreadyExistsException, ShowCreationException {
			Assert.assertEquals(bookingController.createShow("12345", 5, 5, 2), true);
	}
	
	@Test
	public void testViewShowAvailableSeats() throws ShowNotFoundExeption, ShowAlreadyExistsException, ShowCreationException {
			bookingController.createShow("12345", 5, 5, 2);
			Show show = bookingController.viewShowAvailableSeats("12345");
			Assert.assertEquals(show.getShowNumber(), showResult.getShowNumber());
			Assert.assertEquals(show.getSeats().size(), showResult.getSeats().size());
			Assert.assertEquals(show.getNumRows(), showResult.getNumRows());
			Assert.assertEquals(show.getSeatsPerRow(), showResult.getSeatsPerRow());
			Assert.assertEquals(show.getCancellationWindowMinutes(), showResult.getCancellationWindowMinutes());
	}
	
	@Test
	public void testViewShowAllocatedSeats() throws ShowAlreadyExistsException, ShowCreationException, ShowNotFoundExeption{
		bookingController.createShow("12345", 5, 5, 2);
		Show show = bookingController.viewShowAllocatedSeats("12345");
		Assert.assertEquals(show.getShowNumber(), showResult.getShowNumber());
		Assert.assertEquals(show.getSeats().size(), 0);
		Assert.assertEquals(show.getNumRows(), showResult.getNumRows());
		Assert.assertEquals(show.getSeatsPerRow(), showResult.getSeatsPerRow());
		Assert.assertEquals(show.getCancellationWindowMinutes(), showResult.getCancellationWindowMinutes());
	}
	
	@Test
	public void testCreateBooking() throws ShowAlreadyExistsException, ShowCreationException, InvalidBookingException, ShowNotFoundExeption{
		bookingController.createShow("12345", 5, 5, 2);
		Assert.assertTrue(bookingController.createBooking("12345", "1234567", bookedSeats));
	}
	
	@Test
	public void testCancelTicket() throws ShowAlreadyExistsException, ShowCreationException, InvalidBookingException, ShowNotFoundExeption, CancelBookingException{
		bookingController.createShow("12345", 5, 5, 2);
		bookingController.createBooking("12345", "1234567", bookedSeats);
		Assert.assertTrue(bookingController.cancelTicket(ticketNumber, phoneNumber));		
	}


}
