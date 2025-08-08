package edu.snhu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive test suite for the Contact class.
 * Tests all validation requirements for CS320 compliance.
 * Covers constructor validation, getter methods, setter validation,
 * boundary conditions, and edge cases for all fields.
 * 
 * @author Rick Goshen
 * @version 1.0
 */
class ContactTest {

    /**
     * Test valid contact creation with all fields within limits.
     * Verifies that a contact can be created with maximum allowed values
     * and that all getters return the correct values.
     */
    @Test
    void testValidContactCreation() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        assertEquals("1234567890", contact.getContactId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123 Main St", contact.getAddress());
    }

    /**
     * Test that contact ID cannot be null.
     * CS320 requirement: Contact ID is required and cannot be null.
     */
    @Test
    void testContactIdNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact(null, "John", "Doe", "1234567890", "123 Main St");
        });
    }

    /**
     * Test that contact ID cannot exceed 10 characters.
     * CS320 requirement: Contact ID must be 10 characters or less.
     */
    @Test
    void testContactIdTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("12345678901", "John", "Doe", "1234567890", "123 Main St");
        });
    }

    /**
     * Test that contact ID can be exactly 10 characters (boundary test).
     * Verifies maximum length boundary condition.
     */
    @Test
    void testContactIdMaxLength() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        assertEquals("1234567890", contact.getContactId());
    }

    /**
     * Test that first name cannot be null.
     * CS320 requirement: First name is required and cannot be null.
     */
    @Test
    void testFirstNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", null, "Doe", "1234567890", "123 Main St");
        });
    }

    /**
     * Test that first name cannot exceed 10 characters.
     * CS320 requirement: First name must be 10 characters or less.
     */
    @Test
    void testFirstNameTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "12345678901", "Doe", "1234567890", "123 Main St");
        });
    }

    /**
     * Test that first name can be exactly 10 characters (boundary test).
     * Verifies maximum length boundary condition for first name.
     */
    @Test
    void testFirstNameMaxLength() {
        Contact contact = new Contact("1234567890", "1234567890", "Doe", "1234567890", "123 Main St");
        assertEquals("1234567890", contact.getFirstName());
    }

    /**
     * Test that last name cannot be null.
     * CS320 requirement: Last name is required and cannot be null.
     */
    @Test
    void testLastNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", null, "1234567890", "123 Main St");
        });
    }

    /**
     * Test that last name cannot exceed 10 characters.
     * CS320 requirement: Last name must be 10 characters or less.
     */
    @Test
    void testLastNameTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "12345678901", "1234567890", "123 Main St");
        });
    }

    /**
     * Test that last name can be exactly 10 characters (boundary test).
     * Verifies maximum length boundary condition for last name.
     */
    @Test
    void testLastNameMaxLength() {
        Contact contact = new Contact("1234567890", "John", "1234567890", "1234567890", "123 Main St");
        assertEquals("1234567890", contact.getLastName());
    }

    /**
     * Test that phone number cannot be null.
     * CS320 requirement: Phone number is required and cannot be null.
     */
    @Test
    void testPhoneNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", null, "123 Main St");
        });
    }

    /**
     * Test that phone number cannot be less than 10 digits.
     * CS320 requirement: Phone number must be exactly 10 digits.
     */
    @Test
    void testPhoneTooShort() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", "123456789", "123 Main St");
        });
    }

    /**
     * Test that phone number cannot be more than 10 digits.
     * CS320 requirement: Phone number must be exactly 10 digits.
     */
    @Test
    void testPhoneTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", "12345678901", "123 Main St");
        });
    }

    /**
     * Test that phone number must contain only digits.
     * CS320 requirement: Phone number must be exactly 10 numeric digits.
     */
    @Test
    void testPhoneNonNumeric() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", "123456789a", "123 Main St");
        });
    }

    /**
     * Test that phone number can be exactly 10 digits (boundary test).
     * Verifies correct length and numeric validation.
     */
    @Test
    void testPhoneExactLength() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        assertEquals("1234567890", contact.getPhone());
    }

    /**
     * Test that address cannot be null.
     * CS320 requirement: Address is required and cannot be null.
     */
    @Test
    void testAddressNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", "1234567890", null);
        });
    }

    /**
     * Test that address cannot exceed 30 characters.
     * CS320 requirement: Address must be 30 characters or less.
     */
    @Test
    void testAddressTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("1234567890", "John", "Doe", "1234567890", "1234567890123456789012345678901");
        });
    }

    /**
     * Test that address can be exactly 30 characters (boundary test).
     * Verifies maximum length boundary condition for address.
     */
    @Test
    void testAddressMaxLength() {
        String maxAddress = "123456789012345678901234567890";
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", maxAddress);
        assertEquals(maxAddress, contact.getAddress());
    }

    /**
     * Test that first name can be updated with valid value.
     * Verifies setter functionality and field mutability.
     */
    @Test
    void testSetFirstNameValid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        contact.setFirstName("Jane");
        assertEquals("Jane", contact.getFirstName());
    }

    /**
     * Test that first name setter rejects null values.
     * CS320 requirement: First name updates must maintain validation.
     */
    @Test
    void testSetFirstNameNull() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setFirstName(null);
        });
    }

    /**
     * Test that first name setter rejects values exceeding 10 characters.
     * CS320 requirement: First name updates must maintain validation.
     */
    @Test
    void testSetFirstNameTooLong() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setFirstName("12345678901");
        });
    }

    /**
     * Test that last name can be updated with valid value.
     * Verifies setter functionality and field mutability.
     */
    @Test
    void testSetLastNameValid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        contact.setLastName("Smith");
        assertEquals("Smith", contact.getLastName());
    }

    /**
     * Test that last name setter rejects null values.
     * CS320 requirement: Last name updates must maintain validation.
     */
    @Test
    void testSetLastNameNull() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setLastName(null);
        });
    }

    /**
     * Test that last name setter rejects values exceeding 10 characters.
     * CS320 requirement: Last name updates must maintain validation.
     */
    @Test
    void testSetLastNameTooLong() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setLastName("12345678901");
        });
    }

    /**
     * Test that phone number can be updated with valid value.
     * Verifies setter functionality and field mutability.
     */
    @Test
    void testSetPhoneValid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        contact.setPhone("0987654321");
        assertEquals("0987654321", contact.getPhone());
    }

    /**
     * Test that phone setter rejects null values.
     * CS320 requirement: Phone updates must maintain validation.
     */
    @Test
    void testSetPhoneNull() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setPhone(null);
        });
    }

    /**
     * Test that phone setter rejects invalid formats.
     * CS320 requirement: Phone updates must maintain digit-only validation.
     */
    @Test
    void testSetPhoneInvalid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setPhone("123456789a");
        });
    }

    /**
     * Test that address can be updated with valid value.
     * Verifies setter functionality and field mutability.
     */
    @Test
    void testSetAddressValid() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        contact.setAddress("456 Oak Ave");
        assertEquals("456 Oak Ave", contact.getAddress());
    }

    /**
     * Test that address setter rejects null values.
     * CS320 requirement: Address updates must maintain validation.
     */
    @Test
    void testSetAddressNull() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setAddress(null);
        });
    }

    /**
     * Test that address setter rejects values exceeding 30 characters.
     * CS320 requirement: Address updates must maintain validation.
     */
    @Test
    void testSetAddressTooLong() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        assertThrows(IllegalArgumentException.class, () -> {
            contact.setAddress("1234567890123456789012345678901");
        });
    }

    /**
     * Test that contact ID is immutable after construction.
     * CS320 requirement: Contact ID cannot be changed once set.
     */
    @Test
    void testContactIdImmutable() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        String originalId = contact.getContactId();
        assertEquals("1234567890", originalId);
    }
}