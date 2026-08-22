package com.travelbooking.booking.producer;

import com.travelbooking.booking.model.entity.BookingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent {

    private String eventType;

    private String bookingId;

    private String bookingReference;

    private BookingType bookingType;

    private BigDecimal totalAmount;
}