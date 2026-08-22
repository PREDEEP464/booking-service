package com.travelbooking.booking.client.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSeatResponse {

    private Long flightId;

    private Integer seats;

    private Integer availableSeats;

    private Integer reservedSeats;

    private Integer confirmedSeats;

    private Integer releasedSeats;

    private BigDecimal totalAmount;
}