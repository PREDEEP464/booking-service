package com.travelbooking.booking.controller;

import java.util.List;

import com.travelbooking.booking.model.entity.request.BookingRequest;
import com.travelbooking.booking.model.entity.response.FlightBookingResponse;
import com.travelbooking.booking.model.entity.response.HotelBookingResponse;
import com.travelbooking.booking.model.entity.BookingStatus;
import com.travelbooking.booking.model.entity.vo.ApiResponse;
import com.travelbooking.booking.model.entity.vo.PagedResponseVo;
import com.travelbooking.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/flights")
    public ResponseEntity<ApiResponse<FlightBookingResponse>> createFlightBooking(
            @Valid @RequestBody BookingRequest request
    ) {

        FlightBookingResponse response =
                bookingService.createFlightBooking(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                "Flight booking created successfully",
                                response
                        )
                );
    }

    @GetMapping("/flights")
    public ResponseEntity<ApiResponse<PagedResponseVo<FlightBookingResponse>>> getFlightBookings(
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false) String reference,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {

        PagedResponseVo<FlightBookingResponse> response =
                bookingService.getFlightBookings(
                        status,
                        reference,
                        page,
                        size,
                        sortBy,
                        sortDir
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Flight bookings fetched successfully",
                        response
                )
        );
    }

    @PostMapping("/hotels")
    public ResponseEntity<ApiResponse<HotelBookingResponse>> createHotelBooking(
            @Valid @RequestBody BookingRequest request
    ) {

        HotelBookingResponse response =
                bookingService.createHotelBooking(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                "Hotel booking created successfully",
                                response
                        )
                );
    }

    @GetMapping("/hotels")
    public ResponseEntity<ApiResponse<PagedResponseVo<HotelBookingResponse>>> getHotelBookings(
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false) String reference,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {

        PagedResponseVo<HotelBookingResponse> response =
                bookingService.getHotelBookings(
                        status,
                        reference,
                        page,
                        size,
                        sortBy,
                        sortDir
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Hotel bookings fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<ApiResponse<FlightBookingResponse>> getFlightBooking(
            @PathVariable String id
    ) {

        FlightBookingResponse response =
                bookingService.getFlightBookingById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Flight booking fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<ApiResponse<HotelBookingResponse>> getHotelBooking(
            @PathVariable String id
    ) {

        HotelBookingResponse response =
                bookingService.getHotelBookingById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Hotel booking fetched successfully",
                        response
                )
        );
    }

    @PatchMapping("/flights/{id}/cancel")
    public ResponseEntity<ApiResponse<FlightBookingResponse>> cancelFlightBooking(
            @PathVariable String id
    ) {

        FlightBookingResponse response =
                bookingService.cancelFlightBooking(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Flight booking cancelled successfully",
                        response
                )
        );
    }

    @PatchMapping("/hotels/{id}/cancel")
    public ResponseEntity<ApiResponse<HotelBookingResponse>> cancelHotelBooking(
            @PathVariable String id
    ) {

        HotelBookingResponse response =
                bookingService.cancelHotelBooking(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Hotel booking cancelled successfully",
                        response
                )
        );
    }

    @PatchMapping("/flights/{id}/confirm")
    public ResponseEntity<ApiResponse<FlightBookingResponse>>
    confirmFlightBooking(
            @PathVariable String id
    ) {

        FlightBookingResponse response =
                bookingService.confirmFlightBooking(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Flight booking confirmed successfully",
                        response
                )
        );
    }

    @PatchMapping("/hotels/{id}/confirm")
    public ResponseEntity<ApiResponse<HotelBookingResponse>>
    confirmHotelBooking(
            @PathVariable String id
    ) {

        HotelBookingResponse response =
                bookingService.confirmHotelBooking(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Hotel booking confirmed successfully",
                        response
                )
        );
    }
}