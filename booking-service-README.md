# Booking Service

The **Booking Service** is the central booking-management microservice in the Travel Booking System. It manages flight and hotel bookings, maintains booking states, communicates with the Flight and Hotel services, and exposes APIs for creating, viewing, searching, confirming, and cancelling bookings.

## Architecture

```text
UI
 ↓
Booking Service
 ↓
 ├── Flight Service
 └── Hotel Service
```

The Booking Service uses REST-based service-to-service communication with the Flight and Hotel services.

## Technology Stack

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data MongoDB
- Jakarta Validation
- Lombok
- Maven
- MongoDB
- RestTemplate

## Project Structure

```text
booking-service/
│
└── src/main/java/com/travelbooking/booking/
    │
    ├── TravelBookingApplication.java
    │
    ├── controller/
    │   └── BookingController.java
    │
    ├── service/
    │   └── BookingService.java
    │
    ├── serviceImpl/
    │   └── BookingServiceImpl.java
    │
    ├── dao/
    │   └── api/
    │       └── BookingRepository.java
    │
    ├── model/
    │   └── entity/
    │       ├── Booking.java
    │       ├── BookingStatus.java
    │       ├── BookingType.java
    │       │
    │       ├── request/
    │       │   └── BookingRequest.java
    │       │
    │       ├── response/
    │       │   ├── BookingResponse.java
    │       │   ├── FlightBookingResponse.java
    │       │   └── HotelBookingResponse.java
    │       │
    │       └── vo/
    │           └── ApiResponse.java
    │
    ├── client/
    │   ├── flight/
    │   │   └── FlightServiceClient.java
    │   │
    │   └── hotel/
    │       └── HotelServiceClient.java
    │
    └── exception/
        ├── GlobalExceptionHandler.java
        ├── BookingNotFoundException.java
        └── InvalidBookingStateException.java
```

## Database

The service uses **MongoDB**.

Bookings are maintained in a single collection because both flight and hotel bookings share common booking information.

Example:

```text
bookings
 ├── FLIGHT booking
 ├── FLIGHT booking
 ├── HOTEL booking
 └── HOTEL booking
```

The `bookingType` field identifies whether a document represents a flight or hotel booking.

## Booking Lifecycle

### Flight Booking

```text
Flight Service
     ↓
Reserve Seats
     ↓
Booking Service
     ↓
Create PENDING Booking
     ↓
Payment Service
     ↓
Payment SUCCESS
     ↓
Confirm Booking
     ↓
Flight Seats CONFIRMED
```

If payment fails:

```text
Payment FAILED
     ↓
Cancel Booking
     ↓
Release Flight Seats
```

### Hotel Booking

```text
Hotel Service
     ↓
Reserve Rooms
     ↓
Booking Service
     ↓
Create PENDING Booking
     ↓
Payment Service
     ↓
Payment SUCCESS
     ↓
Confirm Booking
     ↓
Rooms CONFIRMED
```

If payment fails:

```text
Payment FAILED
     ↓
Cancel Booking
     ↓
Release Hotel Rooms
```

## API Endpoints

Base URL:

```text
/api/bookings
```

### Create Booking

```http
POST /api/bookings
```

Creates a new flight or hotel booking in `PENDING` state after successfully reserving the required inventory.

### Get Booking

```http
GET /api/bookings/{bookingId}
```

Returns a booking by its ID.

### Get All Bookings

```http
GET /api/bookings
```

Supports pagination, sorting, and general booking retrieval.

### Get Flight Bookings

```http
GET /api/bookings/flights
```

Returns only flight bookings.

### Get Hotel Bookings

```http
GET /api/bookings/hotels
```

Returns only hotel bookings.

### Search Flight Bookings

```http
GET /api/bookings/flights/search
```

Supports filtering/searching of flight bookings.

### Search Hotel Bookings

```http
GET /api/bookings/hotels/search
```

Supports filtering/searching of hotel bookings.

### Confirm Flight Booking

```http
PATCH /api/bookings/flights/{bookingId}/confirm
```

Confirms a pending flight booking after successful payment.

### Confirm Hotel Booking

```http
PATCH /api/bookings/hotels/{bookingId}/confirm
```

Confirms a pending hotel booking after successful payment.

### Cancel Flight Booking

```http
PATCH /api/bookings/flights/{bookingId}/cancel
```

Cancels a flight booking and releases the reserved seats.

### Cancel Hotel Booking

```http
PATCH /api/bookings/hotels/{bookingId}/cancel
```

Cancels a hotel booking and releases the reserved rooms.

## Booking Status

The booking lifecycle uses states such as:

```text
PENDING
CONFIRMED
CANCELLED
```

`PENDING` indicates that the inventory has been reserved but payment has not yet been successfully completed.

`CONFIRMED` indicates that payment was successful and the inventory was confirmed.

`CANCELLED` indicates that the booking was cancelled and the reserved inventory was released.

## Service-to-Service Communication

The Booking Service communicates with:

- **Flight Service** for seat reservation, release, confirmation, and cancellation.
- **Hotel Service** for room reservation, release, confirmation, and cancellation.

Service URLs are configured using application properties:

```properties
flight.service.url=http://localhost:8081/api/flights
hotel.service.url=http://localhost:8082/api/hotels
```

The service uses `RestTemplate` for synchronous REST communication.

## Validation and Exception Handling

The service includes centralized exception handling through `GlobalExceptionHandler`.

Handled cases include:

- Booking not found
- Invalid booking state
- Invalid request data
- Invalid arguments
- Unexpected server errors
- Invalid or unavailable API endpoints

Responses use a common `ApiResponse` structure.

Example:

```json
{
  "message": "Booking not found",
  "data": null
}
```

## Configuration

Example `application.properties`:

```properties
spring.application.name=booking-service

spring.data.mongodb.uri=mongodb://localhost:27017/travel_booking

flight.service.url=http://localhost:8081/api/flights
hotel.service.url=http://localhost:8082/api/hotels

server.port=8083
```

Update the MongoDB connection and service URLs according to the local environment.

## Running the Service

### Prerequisites

- Java 21
- Maven
- MongoDB
- Flight Service running
- Hotel Service running

### Start MongoDB

Make sure MongoDB is running locally.

### Start the application

Using Maven:

```bash
mvn spring-boot:run
```

Or run:

```text
TravelBookingApplication.java
```

from IntelliJ IDEA.

The Booking Service runs on:

```text
http://localhost:8083
```

## Example Booking Flow

A typical flight booking flow is:

```text
1. Reserve flight seats
        ↓
2. Create booking
        ↓
3. Booking status = PENDING
        ↓
4. Initiate payment
        ↓
5. Process payment callback
        ↓
6. Verify payment amount
        ↓
7. Payment SUCCESS
        ↓
8. Confirm booking
        ↓
9. Flight seats CONFIRMED
```

The same architecture is used for hotel bookings with room inventory instead of flight seats.

## Scope

The Booking Service is intentionally focused on the core booking workflow required for the Travel Booking System.

A separate Search Service is not required for the current MVP. The frontend can directly consume the Flight and Hotel search APIs when displaying available booking options.

Future enhancements may include:

- Authentication and authorization
- Distributed tracing
- Circuit breakers and retries
- Advanced booking search
- Idempotency
- Booking expiration
- Event-driven booking updates
- Kafka-based notifications
