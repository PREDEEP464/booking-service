package com.travelbooking.booking.service;

import com.travelbooking.booking.model.entity.BookingStatus;
import com.travelbooking.booking.model.entity.request.BookingRequest;
import com.travelbooking.booking.model.entity.response.FlightBookingResponse;
import com.travelbooking.booking.model.entity.response.HotelBookingResponse;
import com.travelbooking.booking.model.entity.vo.PagedResponseVo;

public interface BookingService {

    FlightBookingResponse createFlightBooking(
            BookingRequest request
    );

    HotelBookingResponse createHotelBooking(
            BookingRequest request
    );

    FlightBookingResponse getFlightBookingById(
            String id
    );

    HotelBookingResponse getHotelBookingById(
            String id
    );

    FlightBookingResponse getFlightBookingByReference(
            String bookingReference
    );

    HotelBookingResponse getHotelBookingByReference(
            String bookingReference
    );

    FlightBookingResponse cancelFlightBooking(
            String id
    );

    HotelBookingResponse cancelHotelBooking(
            String id
    );

    FlightBookingResponse confirmFlightBooking(
            String id
    );

    HotelBookingResponse confirmHotelBooking(
            String id
    );

    PagedResponseVo<FlightBookingResponse> getFlightBookings(
            BookingStatus status,
            String reference,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    PagedResponseVo<HotelBookingResponse> getHotelBookings(
            BookingStatus status,
            String reference,
            int page,
            int size,
            String sortBy,
            String sortDir
    );
}