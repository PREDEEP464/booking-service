package com.travelbooking.booking.serviceImpl;

import com.travelbooking.booking.client.flight.FlightSeatRequest;
import com.travelbooking.booking.client.flight.FlightSeatResponse;
import com.travelbooking.booking.client.flight.FlightServiceClient;
import com.travelbooking.booking.client.hotel.HotelRoomBookingRequest;
import com.travelbooking.booking.client.hotel.HotelRoomBookingResponse;
import com.travelbooking.booking.client.hotel.HotelServiceClient;
import com.travelbooking.booking.dao.api.BookingRepository;
import com.travelbooking.booking.exception.BookingNotFoundException;
import com.travelbooking.booking.exception.InvalidBookingStateException;
import com.travelbooking.booking.model.entity.Booking;
import com.travelbooking.booking.model.entity.BookingStatus;
import com.travelbooking.booking.model.entity.BookingType;
import com.travelbooking.booking.model.entity.request.BookingRequest;
import com.travelbooking.booking.model.entity.response.FlightBookingResponse;
import com.travelbooking.booking.model.entity.response.HotelBookingResponse;
import com.travelbooking.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightServiceClient flightServiceClient;
    private final HotelServiceClient hotelServiceClient;

    @Override
    public FlightBookingResponse createFlightBooking(
            BookingRequest request
    ) {

        validateFlightRequest(request);

        FlightSeatRequest flightRequest =
                new FlightSeatRequest(
                        request.getFlightId(),
                        request.getSeats()
                );

        FlightSeatResponse flightResponse =
                flightServiceClient.reserveSeats(flightRequest);

        Booking booking = new Booking();

        booking.setBookingReference(generateBookingReference());
        booking.setBookingType(BookingType.FLIGHT);

        booking.setFlightId(request.getFlightId());
        booking.setSeats(request.getSeats());

        booking.setTotalAmount(
                flightResponse.getTotalAmount()
        );

        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());

        Booking savedBooking =
                bookingRepository.save(booking);

        return mapToFlightResponse(savedBooking);
    }

    @Override
    public List<FlightBookingResponse> getFlightBookings(
            BookingStatus status,
            String reference
    ) {

        List<Booking> bookings;

        if (status != null && reference != null && !reference.isBlank()) {

            bookings =
                    bookingRepository
                            .findByBookingTypeAndStatusAndBookingReferenceContainingIgnoreCase(
                                    BookingType.FLIGHT,
                                    status,
                                    reference
                            );

        } else if (status != null) {

            bookings =
                    bookingRepository
                            .findByBookingTypeAndStatus(
                                    BookingType.FLIGHT,
                                    status
                            );

        } else if (reference != null && !reference.isBlank()) {

            bookings =
                    bookingRepository
                            .findByBookingTypeAndBookingReferenceContainingIgnoreCase(
                                    BookingType.FLIGHT,
                                    reference
                            );

        } else {

            bookings =
                    bookingRepository
                            .findByBookingType(
                                    BookingType.FLIGHT
                            );
        }

        return bookings.stream()
                .map(this::mapToFlightResponse)
                .toList();
    }

    @Override
    public HotelBookingResponse createHotelBooking(
            BookingRequest request
    ) {

        validateHotelRequest(request);

        HotelRoomBookingRequest hotelRequest =
                new HotelRoomBookingRequest(
                        request.getHotelId(),
                        request.getRoomTypeId(),
                        request.getCheckInDate(),
                        request.getCheckOutDate(),
                        request.getRooms()
                );

        HotelRoomBookingResponse hotelResponse =
                hotelServiceClient.reserveRooms(hotelRequest);

        Booking booking = new Booking();

        booking.setBookingReference(generateBookingReference());
        booking.setBookingType(BookingType.HOTEL);

        booking.setHotelId(request.getHotelId());
        booking.setRoomTypeId(request.getRoomTypeId());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setRooms(request.getRooms());

        booking.setTotalAmount(
                hotelResponse.getTotalAmount()
        );

        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());

        Booking savedBooking =
                bookingRepository.save(booking);

        return mapToHotelResponse(savedBooking);
    }

    @Override
    public List<HotelBookingResponse> getHotelBookings(
            BookingStatus status,
            String reference
    ) {

        List<Booking> bookings;

        if (status != null && reference != null && !reference.isBlank()) {

            bookings =
                    bookingRepository
                            .findByBookingTypeAndStatusAndBookingReferenceContainingIgnoreCase(
                                    BookingType.HOTEL,
                                    status,
                                    reference
                            );

        } else if (status != null) {

            bookings =
                    bookingRepository
                            .findByBookingTypeAndStatus(
                                    BookingType.HOTEL,
                                    status
                            );

        } else if (reference != null && !reference.isBlank()) {

            bookings =
                    bookingRepository
                            .findByBookingTypeAndBookingReferenceContainingIgnoreCase(
                                    BookingType.HOTEL,
                                    reference
                            );

        } else {

            bookings =
                    bookingRepository
                            .findByBookingType(
                                    BookingType.HOTEL
                            );
        }

        return bookings.stream()
                .map(this::mapToHotelResponse)
                .toList();
    }

    @Override
    public FlightBookingResponse getFlightBookingById(
            String id
    ) {

        Booking booking =
                findBooking(id);

        validateBookingType(
                booking,
                BookingType.FLIGHT
        );

        return mapToFlightResponse(booking);
    }

    @Override
    public HotelBookingResponse getHotelBookingById(
            String id
    ) {

        Booking booking =
                findBooking(id);

        validateBookingType(
                booking,
                BookingType.HOTEL
        );

        return mapToHotelResponse(booking);
    }

    @Override
    public FlightBookingResponse getFlightBookingByReference(
            String bookingReference
    ) {

        Booking booking =
                findBookingByReference(bookingReference);

        validateBookingType(
                booking,
                BookingType.FLIGHT
        );

        return mapToFlightResponse(booking);
    }

    @Override
    public HotelBookingResponse getHotelBookingByReference(
            String bookingReference
    ) {

        Booking booking =
                findBookingByReference(bookingReference);

        validateBookingType(
                booking,
                BookingType.HOTEL
        );

        return mapToHotelResponse(booking);
    }

    @Override
    public FlightBookingResponse cancelFlightBooking(
            String id
    ) {

        Booking booking =
                findBooking(id);

        validateBookingType(
                booking,
                BookingType.FLIGHT
        );

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new InvalidBookingStateException(
                    "Booking is already cancelled"
            );
        }

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            throw new InvalidBookingStateException(
                    "Confirmed booking cannot be cancelled yet"
            );
        }

        FlightSeatRequest request =
                new FlightSeatRequest(
                        booking.getFlightId(),
                        booking.getSeats()
                );

        flightServiceClient.releaseSeats(request);

        booking.setStatus(BookingStatus.CANCELLED);

        Booking updatedBooking =
                bookingRepository.save(booking);

        return mapToFlightResponse(updatedBooking);
    }

    @Override
    public HotelBookingResponse cancelHotelBooking(
            String id
    ) {

        Booking booking =
                findBooking(id);

        validateBookingType(
                booking,
                BookingType.HOTEL
        );

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new InvalidBookingStateException(
                    "Booking is already cancelled"
            );
        }

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            throw new InvalidBookingStateException(
                    "Confirmed booking cannot be cancelled yet"
            );
        }

        HotelRoomBookingRequest request =
                new HotelRoomBookingRequest(
                        booking.getHotelId(),
                        booking.getRoomTypeId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getRooms()
                );

        hotelServiceClient.releaseRooms(request);

        booking.setStatus(BookingStatus.CANCELLED);

        Booking updatedBooking =
                bookingRepository.save(booking);

        return mapToHotelResponse(updatedBooking);
    }

    private Booking findBooking(String id) {

        return bookingRepository.findById(id)
                .orElseThrow(
                        () -> new BookingNotFoundException(
                                "Booking not found with id: " + id
                        )
                );
    }

    private Booking findBookingByReference(
            String bookingReference
    ) {

        return bookingRepository
                .findByBookingReference(bookingReference)
                .orElseThrow(
                        () -> new BookingNotFoundException(
                                "Booking not found with reference: "
                                        + bookingReference
                        )
                );
    }

    private void validateBookingType(
            Booking booking,
            BookingType expectedType
    ) {

        if (booking.getBookingType() != expectedType) {
            throw new InvalidBookingStateException(
                    "Booking is not a " +
                            expectedType.name().toLowerCase() +
                            " booking"
            );
        }
    }

    private void validateFlightRequest(
            BookingRequest request
    ) {

        if (request.getFlightId() == null) {
            throw new IllegalArgumentException(
                    "Flight ID is required"
            );
        }

        if (request.getSeats() == null
                || request.getSeats() < 1) {

            throw new IllegalArgumentException(
                    "At least one seat is required"
            );
        }
    }

    private void validateHotelRequest(
            BookingRequest request
    ) {

        if (request.getHotelId() == null) {
            throw new IllegalArgumentException(
                    "Hotel ID is required"
            );
        }

        if (request.getRoomTypeId() == null) {
            throw new IllegalArgumentException(
                    "Room type ID is required"
            );
        }

        if (request.getCheckInDate() == null
                || request.getCheckOutDate() == null) {

            throw new IllegalArgumentException(
                    "Check-in and check-out dates are required"
            );
        }

        if (!request.getCheckOutDate()
                .isAfter(request.getCheckInDate())) {

            throw new IllegalArgumentException(
                    "Check-out date must be after check-in date"
            );
        }

        if (request.getRooms() == null
                || request.getRooms() < 1) {

            throw new IllegalArgumentException(
                    "At least one room is required"
            );
        }
    }

    private String generateBookingReference() {

        return "TRV-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }

    private FlightBookingResponse mapToFlightResponse(
            Booking booking
    ) {

        return new FlightBookingResponse(
                booking.getId(),
                booking.getBookingReference(),
                booking.getBookingType(),
                booking.getFlightId(),
                booking.getSeats(),
                booking.getTotalAmount(),
                booking.getStatus(),
                booking.getCreatedAt()
        );
    }

    private HotelBookingResponse mapToHotelResponse(
            Booking booking
    ) {

        int nights = (int) ChronoUnit.DAYS.between(
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );

        return new HotelBookingResponse(
                booking.getId(),
                booking.getBookingReference(),
                booking.getBookingType(),
                booking.getHotelId(),
                booking.getRoomTypeId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getRooms(),
                nights,
                booking.getTotalAmount(),
                booking.getStatus(),
                booking.getCreatedAt()
        );
    }
}