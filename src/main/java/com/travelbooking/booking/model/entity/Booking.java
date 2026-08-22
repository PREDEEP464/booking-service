package com.travelbooking.booking.model.entity;

import com.travelbooking.booking.model.entity.BookingStatus;
import com.travelbooking.booking.model.entity.BookingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;

    private String bookingReference;

    private BookingType bookingType;

    // Flight details
    private Long flightId;
    private Integer seats;

    // Hotel details
    private Long hotelId;
    private Long roomTypeId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer rooms;

    private BigDecimal totalAmount;

    private BookingStatus status;

    private LocalDateTime createdAt;
}