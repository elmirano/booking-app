package com.booking.app.utils;

import java.util.ArrayList;
import java.util.List;

import com.booking.app.model.Seat;
import com.booking.app.model.Show;

public class BookingUtils {

	private BookingUtils() {
	}

	public static Show showInitializer(String showNumber, int numRows, int seatsPerRow, int cancellationWindowMinutes) {

		var seats = initializeSeats(numRows, seatsPerRow);
		Show show = new Show(showNumber, numRows, seatsPerRow, cancellationWindowMinutes, seats);
		return show;
	}

	private static List<Seat> initializeSeats(int numRows, int seatsPerRow) {

		List<Seat> seats = new ArrayList<>();

		for (int row = 0; row < numRows; row++) {
			List<Seat> rowSeats = new ArrayList<>();
			for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
				String seatNumber = String.format("%c%d", 'A' + row, seatNum);
				seats.add(new Seat(seatNumber));
			}
		}

		return seats;
	}

}
