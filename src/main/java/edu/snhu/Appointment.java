package edu.snhu;

import java.util.Date;

/**
 * Appointment entity class that represents a scheduled appointment with
 * validation.
 * Implements immutable appointment ID, future date validation, and description
 * constraints.
 * 
 * @author Rick Goshen
 * @version 1.0
 * @since 2025-07-31
 */
public class Appointment {
    private final String appointmentId;
    private final Date appointmentDate;
    private final String description;

    /**
     * Creates a new Appointment with validation.
     * 
     * @param appointmentId   unique identifier, max 10 characters, cannot be null
     * @param appointmentDate appointment date, cannot be in the past or null
     * @param description     appointment description, max 50 characters, cannot be
     *                        null
     * @throws IllegalArgumentException if any validation fails
     */
    public Appointment(String appointmentId, Date appointmentDate, String description) {
        // Validate appointment ID
        if (appointmentId == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null");
        }
        if (appointmentId.isEmpty()) {
            throw new IllegalArgumentException("Appointment ID cannot be empty");
        }
        if (appointmentId.length() > 10) {
            throw new IllegalArgumentException("Appointment ID cannot exceed 10 characters");
        }

        // Validate appointment date
        if (appointmentDate == null) {
            throw new IllegalArgumentException("Appointment date cannot be null");
        }
        if (appointmentDate.before(new Date())) {
            throw new IllegalArgumentException("Appointment date cannot be in the past");
        }

        // Validate description
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (description.length() > 50) {
            throw new IllegalArgumentException("Description cannot exceed 50 characters");
        }

        this.appointmentId = appointmentId;
        this.appointmentDate = new Date(appointmentDate.getTime()); // Defensive copy
        this.description = description;
    }

    /**
     * Gets the appointment ID.
     * 
     * @return the immutable appointment ID
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Gets the appointment date.
     * 
     * @return a copy of the appointment date
     */
    public Date getAppointmentDate() {
        return new Date(appointmentDate.getTime()); // Defensive copy
    }

    /**
     * Gets the appointment description.
     * 
     * @return the appointment description
     */
    public String getDescription() {
        return description;
    }
}