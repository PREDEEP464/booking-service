package com.travelbooking.booking.client.flight;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSeatRequest {

    private Long flightId;

    @Min(value = 1, message = "At least one seat is required")
    private Integer seats;
}