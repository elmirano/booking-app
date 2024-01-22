# Booking App

Booking application for shows.

## Description

This project is created for booking shows with the functionality of setting up shows with seat allocations, checking show availability,
booking and canceling shows. 


## Getting Started

### Dependencies

- [Java](https://www.oracle.com/java/technologies/downloads/) version 8+

- [Gitbash](https://git-scm.com/downloads) - used for executing commands using a console.

- [Maven](https://maven.apache.org/download.cgi)

### Executing program

1. Setup Java, Maven and the environment variables in your machine.
2. Select a project folder in your directory on where to download the project.
3. Download files from this [repository](https://github.com/elmirano/booking-app) or run this command from a console from the selected project folder
	- git clone https://github.com/elmirano/booking-app.git
4. After the source has been downloaded you can run this command to generate the executable jar file
	- mvn clean package - this will generate an executable jar(booking-app-1.0.jar) located inside the target folder 
5. Locate the executable jar and run this command 
	- java -jar booking-app-1.0.jar
6. There are only 2 valid users initially. "admin" and "buyer"

### Application Functionality
	- For "admin" user
		- Setup/Configure show (setting up the number of seats per row and the cancellation time in minutes)
		- View the show details and allocation
	
	- For "buyer" user
		- View the seat availabilities of a show
		- Book a ticket by including the seat selections
		- Cancel a booking within a specified time period


## Assumptions

1. There are only two valid login users in this initial version
	- "admin" - this user is able to Setup and view the list of shows and seat allocations.
	- "buyer" - this user is able to retrieve list of available seats for a show, select 1 or more seats , buy and cancel tickets.  

2. This initial version needs to be refactored to catch invalid user inputs. Initial testing of this application assumes that the user inputs
are all valid(happy path).

## Version History

* 0.1
    * Initial Release
