package com.travelbooking.booking.dao.api;

import com.travelbooking.booking.model.entity.Booking;
import com.travelbooking.booking.model.entity.BookingStatus;
import com.travelbooking.booking.model.entity.BookingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository
        extends MongoRepository<Booking, String> {

    Optional<Booking> findByBookingReference(
            String bookingReference
    );

    // Existing methods
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

    // Pageable methods

    Page<Booking> findByBookingType(
            BookingType bookingType,
            Pageable pageable
    );

    Page<Booking> findByBookingTypeAndStatus(
            BookingType bookingType,
            BookingStatus status,
            Pageable pageable
    );

    Page<Booking> findByBookingTypeAndBookingReferenceContainingIgnoreCase(
            BookingType bookingType,
            String bookingReference,
            Pageable pageable
    );

    Page<Booking> findByBookingTypeAndStatusAndBookingReferenceContainingIgnoreCase(
            BookingType bookingType,
            BookingStatus status,
            String bookingReference,
            Pageable pageable
    );
}