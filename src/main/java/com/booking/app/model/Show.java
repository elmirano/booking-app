package com.booking.app.model;

import java.util.List;

public class Show {
	private String showNumber;
	private int numRows;
	private int seatsPerRow;
	private List<Seat> seats;

	private int cancellationWindowMinutes;

	public Show(String showNumber, int numRows, int seatsPerRow, int cancellationWindowMinutes, List<Seat> seats) {
		this.numRows = numRows;
		this.seatsPerRow = seatsPerRow;
		this.showNumber = showNumber;
		this.seats = seats;
		this.cancellationWindowMinutes = cancellationWindowMinutes;
	}

	public String getShowNumber() {
		return showNumber;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public int getCancellationWindowMinutes() {
		return cancellationWindowMinutes;
	}

	public int getSeatsPerRow() {
		return seatsPerRow;
	}

	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}

	public void setShowNumber(String showNumber) {
		this.showNumber = showNumber;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public void setCancellationWindowMinutes(int cancellationWindowMinutes) {
		this.cancellationWindowMinutes = cancellationWindowMinutes;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	@Override
	public String toString() {
		return "Show [showNumber=" + showNumber + ", numRows=" + numRows + ", seatsPerRow=" + seatsPerRow + ", seats="
				+ seats + ", cancellationWindowMinutes=" + cancellationWindowMinutes + "]";
	}

}