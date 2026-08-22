package com.travelbooking.booking.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelbooking.booking.model.entity.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventProducer {

    private static final String TOPIC = "travel-booking-events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishBookingCreated(Booking booking) {

        publishEvent(
                "BOOKING_CREATED",
                booking
        );
    }

    public void publishBookingConfirmed(Booking booking) {

        publishEvent(
                "BOOKING_CONFIRMED",
                booking
        );
    }

    public void publishBookingCancelled(Booking booking) {

        publishEvent(
                "BOOKING_CANCELLED",
                booking
        );
    }

    private void publishEvent(
            String eventType,
            Booking booking
    ) {

        BookingEvent event = new BookingEvent(
                eventType,
                booking.getId(),
                booking.getBookingReference(),
                booking.getBookingType(),
                booking.getTotalAmount()
        );

        try {

            String message =
                    objectMapper.writeValueAsString(event);

            kafkaTemplate.send(
                    TOPIC,
                    booking.getId(),
                    message
            );

            System.out.println(
                    "Kafka event published: " + message
            );

        } catch (JsonProcessingException e) {

            throw new RuntimeException(
                    "Failed to serialize booking event",
                    e
            );
        }
    }
}