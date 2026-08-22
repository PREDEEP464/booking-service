package com.travelbooking.booking.dao.api;

import com.travelbooking.booking.model.entity.Booking;
import com.travelbooking.booking.model.entity.BookingStatus;
import com.travelbooking.booking.model.entity.BookingType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository
        extends MongoRepository<Booking, String> {

    Optional<Booking> findByBookingReference(
            String bookingReference
    );

    List<Booking> findByBookingType(
            BookingType bookingType
    );

    List<Booking> findByBookingTypeAndStatus(
            BookingType bookingType,
            BookingStatus status
    );

    List<Booking> findByBookingTypeAndBookingReferenceContainingIgnoreCase(
            BookingType bookingType,
            String bookingReference
    );

    List<Booking> findByBookingTypeAndStatusAndBookingReferenceContainingIgnoreCase(
            BookingType bookingType,
            BookingStatus status,
            String bookingReference
    );
}