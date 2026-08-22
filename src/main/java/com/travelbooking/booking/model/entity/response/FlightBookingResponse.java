package com.travelbooking.booking.model.entity.response;

import com.travelbooking.booking.model.entity.BookingStatus;
import com.travelbooking.booking.model.entity.BookingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightBookingResponse {

    private String id;

    private String bookingReference;

    private BookingType bookingType;

    private Long flightId;

    private Integer seats;

    private BigDecimal totalAmount;

    private BookingStatus status;

    private LocalDateTime createdAt;
}