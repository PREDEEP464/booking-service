package com.travelbooking.booking.client.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoomBookingResponse {

    private Long hotelId;

    private Long roomTypeId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Integer rooms;

    private Integer nights;

    private BigDecimal totalAmount;
}