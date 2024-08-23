package ru.practicum.shareit.booking.dto;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ResponseBookingDtoTest {
    private final JacksonTester<ResponseBookingDto> json;
    private ResponseBookingDto booking;

    @BeforeEach
    void setUp() {
        LocalDateTime localDateTime = LocalDateTime.of(2000, 10, 20, 10, 20,
                10, 10);
        booking = ResponseBookingDto.builder()
                .id(1L)
                .start(localDateTime)
                .end(localDateTime.plusHours(1))
                .status(Status.APPROVED)
                .build();
    }

    @Test
    void testResponseBookingDto() throws Exception {
        JsonContent<ResponseBookingDto> result = json.write(booking);
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo("2000-10-20, 10:20:10");
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo("2000-10-20, 11:20:10");
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo(Status.APPROVED.name());
    }
}