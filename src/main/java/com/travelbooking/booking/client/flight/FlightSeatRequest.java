package com.travelbooking.booking.client.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSeatRequest {

    private Long flightId;

    private Integer seats;
}