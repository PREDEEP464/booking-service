package com.travelbooking.booking.model.entity.request;

import com.travelbooking.booking.model.entity.BookingType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotNull(message = "Booking type is required")
    private BookingType bookingType;

    // Flight details
    private Long flightId;

    @Min(value = 1, message = "At least one seat is required")
    private Integer seats;

    // Hotel details
    private Long hotelId;

    private Long roomTypeId;

    @Future(message = "Check-in date must be in the future")
    private LocalDate checkInDate;

    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "At least one room is required")
    private Integer rooms;
}