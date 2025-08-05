package com.HealthAppointmentBooking.Utils;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class CommonMethods {


    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalTime parseToLocalTime(String timeStr) {
        try {
            return LocalTime.parse(timeStr, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format. Expected format: HH:mm:ss", e);
        }
    }

    public static LocalDate parseToLocalDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd", e);
        }
    }
}
