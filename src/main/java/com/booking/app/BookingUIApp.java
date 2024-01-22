package com.booking.app;

import java.util.Scanner;

public class BookingUIApp {

	public static ShowBookingBackendApp bookingApp;

	public static void main(String[] args) {
		bookingApp = ShowBookingBackendApp.getInstance();

		boolean validUser = false;
		String cmd = "";

		while (true) {
			printSeparator();
			System.out.println("Please type the word 'QUIT' to exit the application.");
			if (!validUser) {
				System.out.println("Enter your username: ");

			}
			Scanner scanner = new Scanner(System.in);
			printSeparator();

			cmd = scanner.nextLine();
			if ("quit".equals(cmd)) {
				exit();
			}
			readUser(cmd);
		}
	}

	private static void readUser(String userName) {
		boolean invalidSelection = false;
		int selection = 0;

		Scanner scanner = new Scanner(System.in);

		if (userName.equalsIgnoreCase("admin")) {
			invalidSelection = false;
			while (true) {
				printSeparator();
				if (invalidSelection) {
					System.out.println("Invalid selection.\n");
				}
				System.out.println("Logged in as admin. Please select option:");
				System.out.println("1 - Setup/Configure show");
				System.out.println("2 - View show details and seat allocations\n");
				System.out.println("3 - Logout\n");
				System.out.println("Please enter your selection.");
				printSeparator();

				selection = scanner.nextInt();
				if ((selection == 1 || selection == 2)) {
					readAdminSelection(selection);
				} else if (selection == 3) {
					System.out.println("You are logged out.\n");
					return;
				} else {
					invalidSelection = true;
				}
			}
		} else if (userName.equalsIgnoreCase("buyer")) {
			invalidSelection = false;
			while (true) {
				printSeparator();
				if (invalidSelection) {
					System.out.println("Invalid selection.\n");
				}
				System.out.println("Logged in as system user. Please select option:");
				System.out.println("1 - Check seat availability. Provide show number.");
				System.out.println("2 - Book a ticket. Provide show number, phone # and seats.");
				System.out.println("3 - Cancel ticket. Provide ticket # and phone #\n");
				System.out.println("4 - Logout\n");
				System.out.println("Please enter your selection.");
				printSeparator();

				selection = scanner.nextInt();
				if ((selection == 1 || selection == 2 || selection == 3)) {
					readUserSelection(selection);
				} else if (selection == 4) {
					System.out.println("You are logged out.\n");
					return;
				} else {
					invalidSelection = true;
				}
			}

		} else {
			System.out.println("You are not authorized to use this app. Please enter a valid user.\n");
		}

		return;

	}

	private static void readAdminSelection(int selection) {
		String cmd = "";
		Scanner scanner = new Scanner(System.in);

		while (true) {
			printSeparator();
			switch (selection) {
			case 1:
				System.out.println("Setup/Configure show. Type in the details.");
				System.out.println(
						"<Show Number> <Number of Rows> <Number of seats per row>  <Cancellation window in minutes>");
				System.out.println("Example : 12345 5 5 2\n");
				break;
			case 2:
				System.out.println("View show details. Type the show number.");
				System.out.println("<Show Number>");
				System.out.println("Example : 12345\n");
				break;
			default:
				break;
			}

			System.out.println("\nPlease type 'B' to go back to the previous menu.\n");
			printSeparator();

			cmd = scanner.nextLine();
			if ("b".equalsIgnoreCase(cmd)) {
				break;
			}
			String[] lineCommand = cmd.split(" ");
			if (selection == 1 && lineCommand.length == 4) {
				System.out.println("Creating show");
				String showNumber = lineCommand[0];
				Integer rows = Integer.valueOf(lineCommand[1]);
				Integer seatsPerRow = Integer.valueOf(lineCommand[2]);
				Integer cancelWindowInMins = Integer.valueOf(lineCommand[3]);
				bookingApp.addShow(showNumber, rows, seatsPerRow, cancelWindowInMins);
			} else if (selection == 2 && lineCommand.length == 1) {
				String showNumber = lineCommand[0];
				bookingApp.viewAllocatedSeatsPerShow(showNumber);
			} else {
				System.out.println("Invalid command. Please retry.");
			}
		}
	}

	private static void readUserSelection(int selection) {
		String cmd = "";
		Scanner scanner = new Scanner(System.in);

		while (true) {
			printSeparator();
			switch (selection) {
			case 1:
				System.out.println("Check seat availability. Type the show number.");
				System.out.println("Example : 12345\n");
				break;
			case 2:
				System.out.println("Book a ticket. Type the show number, phone # and seats.");
				System.out.println("<Show Number> <Phone#> <Comma separated list of seats>");
				System.out.println("Example : 12345 1234567 A1,A2,A3,B1,B2");
				break;
			case 3:
				System.out.println("Cancel ticket. Provide ticket # and phone #");
				System.out.println("<Ticket#>  <Phone#>");
				System.out.println("Example : 12345A1 1234567");

			default:
				break;
			}

			System.out.println("\nPlease type 'B' to go back to the previous menu.\n");
			printSeparator();

			cmd = scanner.nextLine();
			if ("b".equalsIgnoreCase(cmd)) {
				return;
			}
			String[] lineCommand = cmd.split(" ");
			if (selection == 1 && lineCommand.length == 1) {
				String showNumber = lineCommand[0];
				bookingApp.viewAvailableSeatsPerShow(showNumber);
			} else if (selection == 2 && lineCommand.length == 3) {
				String showNumber = lineCommand[0];
				String phoneNumber = lineCommand[1];
				String[] seats = lineCommand[2].split(",");
				bookingApp.bookShow(showNumber, phoneNumber, seats);
			} else if (selection == 3 && lineCommand.length == 2) {
				String ticketNumber = lineCommand[0];
				String phoneNumber = lineCommand[1];
				bookingApp.cancelTicket(ticketNumber, phoneNumber);
			} else {
				System.out.println("Invalid command. Please retry.");
			}
		}
	}

	public static void exit() {
		System.out.println("Quitting application");
		System.exit(0);
	}

	public static void printSeparator() {
		System.out.println("===========================================");
	}

}