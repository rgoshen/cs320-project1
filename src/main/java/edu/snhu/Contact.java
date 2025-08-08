package edu.snhu;

/**
 * Represents a contact with validation for CS320 requirements.
 * Contains immutable contact ID and mutable personal information fields.
 * All fields have strict validation rules to ensure data integrity.
 * 
 * @author Rick Goshen
 * @version 1.0
 */
public class Contact {
    private final String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    /**
     * Validates contact ID according to CS320 requirements.
     * 
     * @param contactId the contact ID to validate
     * @throws IllegalArgumentException if contactId is null or exceeds 10 characters
     */
    private static void validateContactId(String contactId) {
        if (contactId == null || contactId.length() > 10) {
            throw new IllegalArgumentException("Contact ID cannot be null and must be 10 characters or less");
        }
    }

    /**
     * Validates first name according to CS320 requirements.
     * 
     * @param firstName the first name to validate
     * @throws IllegalArgumentException if firstName is null or exceeds 10 characters
     */
    private static void validateFirstName(String firstName) {
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("First name cannot be null and must be 10 characters or less");
        }
    }

    /**
     * Validates last name according to CS320 requirements.
     * 
     * @param lastName the last name to validate
     * @throws IllegalArgumentException if lastName is null or exceeds 10 characters
     */
    private static void validateLastName(String lastName) {
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("Last name cannot be null and must be 10 characters or less");
        }
    }

    /**
     * Validates phone number according to CS320 requirements.
     * 
     * @param phone the phone number to validate
     * @throws IllegalArgumentException if phone is null, not 10 characters, or contains non-digits
     */
    private static void validatePhone(String phone) {
        if (phone == null || phone.length() != 10 || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number cannot be null and must be exactly 10 digits");
        }
    }

    /**
     * Validates address according to CS320 requirements.
     * 
     * @param address the address to validate
     * @throws IllegalArgumentException if address is null or exceeds 30 characters
     */
    private static void validateAddress(String address) {
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Address cannot be null and must be 30 characters or less");
        }
    }

    /**
     * Constructs a new Contact with all required fields.
     * All parameters are validated according to CS320 requirements.
     * 
     * @param contactId unique identifier, max 10 characters, not null, immutable
     * @param firstName contact's first name, max 10 characters, not null
     * @param lastName contact's last name, max 10 characters, not null
     * @param phone contact's phone number, exactly 10 digits, not null
     * @param address contact's address, max 30 characters, not null
     * @throws IllegalArgumentException if any parameter violates validation rules
     */
    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        validateContactId(contactId);
        validateFirstName(firstName);
        validateLastName(lastName);
        validatePhone(phone);
        validateAddress(address);

        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    /**
     * Gets the contact's unique identifier.
     * Contact ID is immutable once set during construction.
     * 
     * @return the contact ID (max 10 characters)
     */
    public String getContactId() {
        return contactId;
    }

    /**
     * Gets the contact's first name.
     * 
     * @return the first name (max 10 characters)
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the contact's last name.
     * 
     * @return the last name (max 10 characters)
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the contact's phone number.
     * 
     * @return the phone number (exactly 10 digits)
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets the contact's address.
     * 
     * @return the address (max 30 characters)
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the contact's first name with validation.
     * 
     * @param firstName the new first name, max 10 characters, not null
     * @throws IllegalArgumentException if firstName is null or exceeds 10 characters
     */
    public void setFirstName(String firstName) {
        validateFirstName(firstName);
        this.firstName = firstName;
    }

    /**
     * Sets the contact's last name with validation.
     * 
     * @param lastName the new last name, max 10 characters, not null
     * @throws IllegalArgumentException if lastName is null or exceeds 10 characters
     */
    public void setLastName(String lastName) {
        validateLastName(lastName);
        this.lastName = lastName;
    }

    /**
     * Sets the contact's phone number with validation.
     * 
     * @param phone the new phone number, exactly 10 digits, not null
     * @throws IllegalArgumentException if phone is null, not 10 characters, or contains non-digits
     */
    public void setPhone(String phone) {
        validatePhone(phone);
        this.phone = phone;
    }

    /**
     * Sets the contact's address with validation.
     * 
     * @param address the new address, max 30 characters, not null
     * @throws IllegalArgumentException if address is null or exceeds 30 characters
     */
    public void setAddress(String address) {
        validateAddress(address);
        this.address = address;
    }
}