package edu.snhu;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AppointmentService provides CRUD operations for appointment management.
 * Uses in-memory storage with ConcurrentHashMap for thread-safe operations.
 * 
 * @author Rick Goshen
 * @version 1.0
 * @since 2025-07-31
 */
public class AppointmentService {
    private final ConcurrentHashMap<String, Appointment> appointments;

    /**
     * Creates a new AppointmentService with empty appointment storage.
     */
    public AppointmentService() {
        this.appointments = new ConcurrentHashMap<>();
    }

    /**
     * Adds a new appointment to the service.
     * 
     * @param appointmentId   unique identifier for the appointment
     * @param appointmentDate date of the appointment
     * @param description     description of the appointment
     * @throws IllegalArgumentException if appointment ID already exists or
     *                                  validation fails
     */
    public void addAppointment(String appointmentId, Date appointmentDate, String description) {
        // Validate appointment ID before checking for duplicates (null handling)
        if (appointmentId == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null");
        }

        // Check for duplicate ID
        if (appointments.containsKey(appointmentId)) {
            throw new IllegalArgumentException("Appointment ID already exists: " + appointmentId);
        }

        // Create new appointment (this will validate all parameters)
        Appointment appointment = new Appointment(appointmentId, appointmentDate, description);

        // Add to storage
        appointments.put(appointmentId, appointment);
    }

    /**
     * Retrieves an appointment by its ID.
     * 
     * @param appointmentId the unique identifier of the appointment
     * @return the appointment if found
     * @throws IllegalArgumentException if appointmentId is null or appointment does not exist
     */
    public Appointment getAppointment(String appointmentId) {
        // Validate appointment ID
        if (appointmentId == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null");
        }
        
        // Check if appointment exists
        Appointment appointment = appointments.get(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentId);
        }
        
        return appointment;
    }

    /**
     * Deletes an appointment by its ID.
     * 
     * @param appointmentId the unique identifier of the appointment to delete
     * @throws IllegalArgumentException if appointment ID is null or appointment
     *                                  does not exist
     */
    public void deleteAppointment(String appointmentId) {
        // Validate appointment ID
        if (appointmentId == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null");
        }

        // Check if appointment exists
        if (!appointments.containsKey(appointmentId)) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentId);
        }

        // Remove the appointment
        appointments.remove(appointmentId);
    }
}