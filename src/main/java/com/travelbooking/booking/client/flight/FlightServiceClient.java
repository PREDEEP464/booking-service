package com.travelbooking.booking.client.flight;

import com.travelbooking.booking.model.entity.vo.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class FlightServiceClient {

    private final RestTemplate restTemplate;

    @Value("${flight.service.url}")
    private String flightServiceUrl;

    public FlightSeatResponse reserveSeats(FlightSeatRequest request) {

        HttpEntity<FlightSeatRequest> entity =
                new HttpEntity<>(request);

        ApiResponse<FlightSeatResponse> response =
                restTemplate.exchange(
                        flightServiceUrl + "/reserve",
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<FlightSeatResponse>>() {
                        }
                ).getBody();

        return response.getData();
    }

    public FlightSeatResponse releaseSeats(FlightSeatRequest request) {

        HttpEntity<FlightSeatRequest> entity =
                new HttpEntity<>(request);

        ApiResponse<FlightSeatResponse> response =
                restTemplate.exchange(
                        flightServiceUrl + "/release",
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<FlightSeatResponse>>() {
                        }
                ).getBody();

        return response.getData();
    }

    public FlightSeatResponse confirmSeats(FlightSeatRequest request) {

        HttpEntity<FlightSeatRequest> entity =
                new HttpEntity<>(request);

        ApiResponse<FlightSeatResponse> response =
                restTemplate.exchange(
                        flightServiceUrl + "/confirm",
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<FlightSeatResponse>>() {
                        }
                ).getBody();

        return response.getData();
    }

    public FlightSeatResponse cancelConfirmedSeats(
            FlightSeatRequest request
    ) {

        HttpEntity<FlightSeatRequest> entity =
                new HttpEntity<>(request);

        ApiResponse<FlightSeatResponse> response =
                restTemplate.exchange(
                        flightServiceUrl + "/cancel-seats",
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<FlightSeatResponse>>() {
                        }
                ).getBody();

        return response.getData();
    }
}