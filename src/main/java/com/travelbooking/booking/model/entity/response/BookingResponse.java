package com.travelbooking.booking.model.entity.response;

import com.travelbooking.booking.model.entity.BookingStatus;
import com.travelbooking.booking.model.entity.BookingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private String id;

    private String bookingReference;

    private BookingType bookingType;

    private Long flightId;

    private Integer seats;

    private Long hotelId;

    private Long roomTypeId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Integer rooms;

    private BigDecimal totalAmount;

    private BookingStatus status;

    private LocalDateTime createdAt;
}