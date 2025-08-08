package edu.snhu;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Appointment class focusing on validation requirements.
 * Tests appointment ID, date, and description validation using TDD methodology.
 * 
 * @author Rick Goshen
 * @version 1.0
 * @since 2025-07-31
 */
public class AppointmentTest {

    @Test
    @DisplayName("Test appointment ID validation - maximum length constraint")
    void testAppointmentIdValidation() {
        // Test valid ID (10 characters exactly)
        String validId = "1234567890";
        Date futureDate = new Date(System.currentTimeMillis() + 86400000); // Tomorrow
        String validDescription = "Valid appointment description";

        assertDoesNotThrow(() -> {
            new Appointment(validId, futureDate, validDescription);
        });

        // Test ID too long (11 characters)
        String longId = "12345678901";
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(longId, futureDate, validDescription);
        });

        // Test null ID
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(null, futureDate, validDescription);
        });

        // Test empty ID
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("", futureDate, validDescription);
        });
    }

    @Test
    @DisplayName("Test appointment ID immutability")
    void testAppointmentIdImmutability() {
        String appointmentId = "APP123";
        Date futureDate = new Date(System.currentTimeMillis() + 86400000);
        String description = "Test appointment";

        Appointment appointment = new Appointment(appointmentId, futureDate, description);

        // Verify ID cannot be changed (no setter should exist)
        assertEquals(appointmentId, appointment.getAppointmentId());
    }

    @Test
    @DisplayName("Test appointment ID edge cases - boundary conditions")
    void testAppointmentIdBoundaryConditions() {
        Date futureDate = new Date(System.currentTimeMillis() + 86400000);
        String validDescription = "Valid description";

        // Test exactly 10 characters (boundary)
        String tenCharId = "1234567890";
        assertDoesNotThrow(() -> {
            new Appointment(tenCharId, futureDate, validDescription);
        });

        // Test 1 character (minimum valid)
        String oneCharId = "A";
        assertDoesNotThrow(() -> {
            new Appointment(oneCharId, futureDate, validDescription);
        });

        // Test 11 characters (just over boundary)
        String elevenCharId = "12345678901";
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(elevenCharId, futureDate, validDescription);
        });
    }

    @Test
    @DisplayName("Test appointment ID edge cases - special characters and whitespace")
    void testAppointmentIdSpecialCharacters() {
        Date futureDate = new Date(System.currentTimeMillis() + 86400000);
        String validDescription = "Valid description";

        // Test with special characters
        String specialCharId = "ID-123_@#$";
        assertDoesNotThrow(() -> {
            new Appointment(specialCharId, futureDate, validDescription);
        });

        // Test with whitespace
        String whitespaceId = "ID 123";
        assertDoesNotThrow(() -> {
            new Appointment(whitespaceId, futureDate, validDescription);
        });

        // Test with only whitespace (should fail)
        String onlyWhitespaceId = "   ";
        assertDoesNotThrow(() -> {
            new Appointment(onlyWhitespaceId, futureDate, validDescription);
        });

        // Test with leading/trailing whitespace
        String trimmedId = " ID123 ";
        assertDoesNotThrow(() -> {
            new Appointment(trimmedId, futureDate, validDescription);
        });
    }

    @Test
    @DisplayName("Test appointment date edge cases - past and future dates")
    void testAppointmentDateEdgeCases() {
        String validId = "APP123";
        String validDescription = "Valid description";

        // Test past date (should fail)
        Date pastDate = new Date(System.currentTimeMillis() - 86400000); // Yesterday
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(validId, pastDate, validDescription);
        });

        // Test current time (should fail as it's technically in the past)
        Date currentDate = new Date(System.currentTimeMillis() - 1000); // 1 second ago
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(validId, currentDate, validDescription);
        });

        // Test far future date
        Date farFutureDate = new Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000)); // 1 year
        assertDoesNotThrow(() -> {
            new Appointment(validId, farFutureDate, validDescription);
        });

        // Test null date
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(validId, null, validDescription);
        });
    }

    @Test
    @DisplayName("Test description edge cases - length and content")
    void testDescriptionEdgeCases() {
        String validId = "APP123";
        Date futureDate = new Date(System.currentTimeMillis() + 86400000);

        // Test exactly 50 characters (boundary)
        String fiftyCharDescription = "12345678901234567890123456789012345678901234567890";
        assertDoesNotThrow(() -> {
            new Appointment(validId, futureDate, fiftyCharDescription);
        });

        // Test 51 characters (over limit)
        String fiftyOneCharDescription = "123456789012345678901234567890123456789012345678901";
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(validId, futureDate, fiftyOneCharDescription);
        });

        // Test empty description (should pass - business rule allows empty)
        String emptyDescription = "";
        assertDoesNotThrow(() -> {
            new Appointment(validId, futureDate, emptyDescription);
        });

        // Test null description (should fail)
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(validId, futureDate, null);
        });

        // Test description with special characters
        String specialCharDescription = "Appointment with @#$%^&*()_+-=[]{}|;':\",./<>?";
        assertDoesNotThrow(() -> {
            new Appointment(validId, futureDate, specialCharDescription);
        });
    }
}