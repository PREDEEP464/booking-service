package com.travelbooking.booking.client.hotel;

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
public class HotelServiceClient {

    private final RestTemplate restTemplate;

    @Value("${hotel.service.url}")
    private String hotelServiceUrl;

    public HotelRoomBookingResponse reserveRooms(
            HotelRoomBookingRequest request
    ) {

        HttpEntity<HotelRoomBookingRequest> entity =
                new HttpEntity<>(request);

        ApiResponse<HotelRoomBookingResponse> response =
                restTemplate.exchange(
                        hotelServiceUrl + "/reserve",
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<HotelRoomBookingResponse>>() {
                        }
                ).getBody();

        return response.getData();
    }

    public HotelRoomBookingResponse releaseRooms(
            HotelRoomBookingRequest request
    ) {

        HttpEntity<HotelRoomBookingRequest> entity =
                new HttpEntity<>(request);

        ApiResponse<HotelRoomBookingResponse> response =
                restTemplate.exchange(
                        hotelServiceUrl + "/release",
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<HotelRoomBookingResponse>>() {
                        }
                ).getBody();

        return response.getData();
    }

    public HotelRoomBookingResponse confirmRooms(
            HotelRoomBookingRequest request
    ) {

        return restTemplate.postForObject(
                hotelServiceUrl + "/rooms/confirm",
                request,
                HotelRoomBookingResponse.class
        );
    }

    public HotelRoomBookingResponse cancelConfirmedRooms(
            HotelRoomBookingRequest request
    ) {

        return restTemplate.postForObject(
                hotelServiceUrl + "/rooms/cancel-rooms",
                request,
                HotelRoomBookingResponse.class
        );
    }
}