package com.booking.app;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.booking.app.controller.BookingController;
import com.booking.app.controller.BookingControllerImpl;
import com.booking.app.controller.service.BookingService;
import com.booking.app.controller.service.BookingServiceImpl;
import com.booking.app.exception.InvalidShowException;
import com.booking.app.exception.ShowAlreadyExistsException;
import com.booking.app.model.Show;
import com.booking.app.repository.BookedPhoneRepo;
import com.booking.app.repository.BookingRepo;
import com.booking.app.repository.ShowRepo;
import com.booking.app.repository.TicketRepo;

public class BookingControllerTest {

	ShowRepo showRepo = new ShowRepo();
	BookingRepo bookingRepo = new BookingRepo();
	BookedPhoneRepo bookedPhoneRepo = new BookedPhoneRepo();
	TicketRepo ticketRepo = new TicketRepo();
	BookingService bookingService = new BookingServiceImpl(showRepo, bookingRepo, bookedPhoneRepo, ticketRepo);
//	Show show = new Show("12345", 5, 5, 2, null);
	BookingController bookingController = new BookingControllerImpl(bookingService);

	@Test
	public void testCreateShow() {
		try {
			bookingController.createShow("12345", 5, 5, 2);
			Assert.assertEquals(bookingController.createShow("12345", 5, 5, 2), true);
		} catch (ShowAlreadyExistsException | InvalidShowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
