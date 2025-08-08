package edu.snhu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

/**
 * Unit tests for AppointmentService class focusing on CRUD operations.
 * Tests appointment management functionality using TDD methodology.
 * 
 * @author Rick Goshen
 * @version 1.0
 * @since 2025-07-31
 */
public class AppointmentServiceTest {

    private AppointmentService appointmentService;
    private Date futureDate;

    @BeforeEach
    public void setUp() {
        appointmentService = new AppointmentService();
        futureDate = new Date(System.currentTimeMillis() + 86400000); // Tomorrow
    }

    @Test
    @DisplayName("Test add appointment with valid data")
    public void testAddAppointment() {
        String appointmentId = "APP123";
        String description = "Doctor appointment";

        // Should not throw exception for valid appointment
        assertDoesNotThrow(() -> {
            appointmentService.addAppointment(appointmentId, futureDate, description);
        });

        // Should be able to retrieve the added appointment
        Appointment retrievedAppointment = appointmentService.getAppointment(appointmentId);
        assertNotNull(retrievedAppointment);
        assertEquals(appointmentId, retrievedAppointment.getAppointmentId());
        assertEquals(description, retrievedAppointment.getDescription());
    }

    @Test
    @DisplayName("Test add appointment with duplicate ID throws exception")
    public void testAddDuplicateAppointment() {
        String appointmentId = "APP123";
        String description = "Doctor appointment";

        // Add first appointment
        assertDoesNotThrow(() -> {
            appointmentService.addAppointment(appointmentId, futureDate, description);
        });

        // Adding same ID should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment(appointmentId, futureDate, "Different description");
        });
    }

    @Test
    @DisplayName("Test get non-existent appointment returns null")
    public void testGetNonExistentAppointment() {
        String nonExistentId = "NOTFOUND";

        Appointment appointment = appointmentService.getAppointment(nonExistentId);
        assertNull(appointment);
    }

    @Test
    @DisplayName("Test add appointment with invalid data throws exception")
    public void testAddAppointmentInvalidData() {
        // Test null appointment ID
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment(null, futureDate, "Valid description");
        });

        // Test empty appointment ID
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment("", futureDate, "Valid description");
        });

        // Test ID too long (11 characters)
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment("12345678901", futureDate, "Valid description");
        });

        // Test null date
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment("APP123", null, "Valid description");
        });

        // Test past date
        Date pastDate = new Date(System.currentTimeMillis() - 86400000);
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment("APP123", pastDate, "Valid description");
        });

        // Test null description
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment("APP123", futureDate, null);
        });

        // Test description too long (51 characters)
        String longDescription = "123456789012345678901234567890123456789012345678901";
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment("APP123", futureDate, longDescription);
        });
    }

    @Test
    @DisplayName("Test get appointment with edge case IDs")
    public void testGetAppointmentEdgeCases() {
        // Test with null ID
        Appointment appointment = appointmentService.getAppointment(null);
        assertNull(appointment);

        // Test with empty ID
        appointment = appointmentService.getAppointment("");
        assertNull(appointment);

        // Test with whitespace ID
        appointment = appointmentService.getAppointment("   ");
        assertNull(appointment);
    }

    @Test
    @DisplayName("Test appointment service thread safety simulation")
    public void testAppointmentServiceConcurrency() {
        // Add multiple appointments to test ConcurrentHashMap behavior
        String baseId = "THREAD";

        for (int i = 0; i < 10; i++) {
            String appointmentId = baseId + i;
            String description = "Thread test appointment " + i;

            assertDoesNotThrow(() -> {
                appointmentService.addAppointment(appointmentId, futureDate, description);
            });
        }

        // Verify all appointments were added
        for (int i = 0; i < 10; i++) {
            String appointmentId = baseId + i;
            Appointment appointment = appointmentService.getAppointment(appointmentId);
            assertNotNull(appointment);
            assertEquals(appointmentId, appointment.getAppointmentId());
        }
    }

    @Test
    @DisplayName("Test appointment service boundary conditions")
    public void testAppointmentServiceBoundaryConditions() {
        // Test with maximum valid ID length (10 characters)
        String maxLengthId = "1234567890";
        assertDoesNotThrow(() -> {
            appointmentService.addAppointment(maxLengthId, futureDate, "Valid description");
        });

        // Verify it was added correctly
        Appointment appointment = appointmentService.getAppointment(maxLengthId);
        assertNotNull(appointment);
        assertEquals(maxLengthId, appointment.getAppointmentId());

        // Test with maximum valid description length (50 characters)
        String maxDescription = "12345678901234567890123456789012345678901234567890";
        assertDoesNotThrow(() -> {
            appointmentService.addAppointment("DESC50", futureDate, maxDescription);
        });

        // Verify description was stored correctly
        Appointment descriptionAppointment = appointmentService.getAppointment("DESC50");
        assertNotNull(descriptionAppointment);
        assertEquals(maxDescription, descriptionAppointment.getDescription());
    }

    @Test
    @DisplayName("Test delete appointment functionality")
    public void testDeleteAppointment() {
        String appointmentId = "DELETE123";
        String description = "Appointment to be deleted";

        // Add appointment first
        assertDoesNotThrow(() -> {
            appointmentService.addAppointment(appointmentId, futureDate, description);
        });

        // Verify it exists
        assertNotNull(appointmentService.getAppointment(appointmentId));

        // Delete the appointment
        assertDoesNotThrow(() -> {
            appointmentService.deleteAppointment(appointmentId);
        });

        // Verify it no longer exists
        assertNull(appointmentService.getAppointment(appointmentId));
    }

    @Test
    @DisplayName("Test delete non-existent appointment throws exception")
    public void testDeleteNonExistentAppointment() {
        String nonExistentId = "NOTFOUND";

        // Attempting to delete non-existent appointment should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.deleteAppointment(nonExistentId);
        });
    }
}