package com.travelbooking.booking.service;

import com.travelbooking.booking.model.entity.request.BookingRequest;
import com.travelbooking.booking.model.entity.response.FlightBookingResponse;
import com.travelbooking.booking.model.entity.response.HotelBookingResponse;

public interface BookingService {

    FlightBookingResponse createFlightBooking(BookingRequest request);

    HotelBookingResponse createHotelBooking(BookingRequest request);

    FlightBookingResponse getFlightBookingById(String id);

    HotelBookingResponse getHotelBookingById(String id);

    FlightBookingResponse getFlightBookingByReference(String bookingReference);

    HotelBookingResponse getHotelBookingByReference(String bookingReference);

    FlightBookingResponse cancelFlightBooking(String id);

    HotelBookingResponse cancelHotelBooking(String id);
}