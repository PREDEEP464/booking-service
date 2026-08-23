package com.travelbooking.booking.client.hotel;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoomBookingRequest {

    private Long hotelId;

    private Long roomTypeId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    @Min(value = 1, message = "At least one seat is required")
    private Integer rooms;
}