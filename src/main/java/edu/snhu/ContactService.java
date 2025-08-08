package edu.snhu;

import java.util.HashMap;
import java.util.Map;

/**
 * Contact service for managing contacts with CS320 requirements.
 * Provides CRUD operations for contacts using in-memory HashMap storage.
 * Enforces unique contact ID constraints and field validation.
 * 
 * @author Rick Goshen
 * @version 1.0
 */
public class ContactService {
    private final Map<String, Contact> contacts;

    /**
     * Constructs a new ContactService with empty contact storage.
     * Initializes the internal HashMap for contact management.
     */
    public ContactService() {
        this.contacts = new HashMap<>();
    }

    /**
     * Validates that a contact ID is not null.
     * 
     * @param contactId the contact ID to validate
     * @throws IllegalArgumentException if contactId is null
     */
    private static void validateContactId(String contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("Contact ID cannot be null");
        }
    }

    /**
     * Validates that a contact exists in the service.
     * 
     * @param contactId the contact ID to check
     * @throws IllegalArgumentException if contact not found
     */
    private void validateContactExists(String contactId) {
        if (!contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Contact not found");
        }
    }

    /**
     * Retrieves a contact by ID with validation.
     * 
     * @param contactId the contact ID to retrieve
     * @return the contact with the specified ID
     * @throws IllegalArgumentException if contactId is null or contact not found
     */
    private Contact getValidatedContact(String contactId) {
        validateContactId(contactId);
        validateContactExists(contactId);
        return contacts.get(contactId);
    }

    /**
     * Adds a new contact to the service.
     * Contact ID must be unique across all stored contacts.
     * 
     * @param contact the contact to add, must not be null
     * @throws IllegalArgumentException if contact is null or ID already exists
     */
    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        if (contacts.containsKey(contact.getContactId())) {
            throw new IllegalArgumentException("Contact ID already exists");
        }
        contacts.put(contact.getContactId(), contact);
    }

    /**
     * Adds a new contact to the service using individual string parameters.
     * Creates a Contact object internally and delegates to addContact(Contact).
     * Contact ID must be unique across all stored contacts.
     * 
     * @param contactId unique identifier, max 10 characters, not null, immutable
     * @param firstName contact's first name, max 10 characters, not null
     * @param lastName contact's last name, max 10 characters, not null
     * @param phone contact's phone number, exactly 10 digits, not null
     * @param address contact's address, max 30 characters, not null
     * @throws IllegalArgumentException if any parameter violates validation rules or ID already exists
     */
    public void addContact(String contactId, String firstName, String lastName, String phone, String address) {
        Contact contact = new Contact(contactId, firstName, lastName, phone, address);
        addContact(contact);
    }

    /**
     * Deletes a contact by its unique ID.
     * 
     * @param contactId the ID of the contact to delete, must not be null
     * @throws IllegalArgumentException if contactId is null or contact not found
     */
    public void deleteContact(String contactId) {
        validateContactId(contactId);
        validateContactExists(contactId);
        contacts.remove(contactId);
    }

    /**
     * Updates the first name of an existing contact.
     * 
     * @param contactId the ID of the contact to update, must not be null
     * @param firstName the new first name, must meet validation requirements
     * @throws IllegalArgumentException if contactId is null, contact not found, or firstName invalid
     */
    public void updateFirstName(String contactId, String firstName) {
        Contact contact = getValidatedContact(contactId);
        contact.setFirstName(firstName);
    }

    /**
     * Updates the last name of an existing contact.
     * 
     * @param contactId the ID of the contact to update, must not be null
     * @param lastName the new last name, must meet validation requirements
     * @throws IllegalArgumentException if contactId is null, contact not found, or lastName invalid
     */
    public void updateLastName(String contactId, String lastName) {
        Contact contact = getValidatedContact(contactId);
        contact.setLastName(lastName);
    }

    /**
     * Updates the phone number of an existing contact.
     * 
     * @param contactId the ID of the contact to update, must not be null
     * @param phone the new phone number, must meet validation requirements
     * @throws IllegalArgumentException if contactId is null, contact not found, or phone invalid
     */
    public void updatePhone(String contactId, String phone) {
        Contact contact = getValidatedContact(contactId);
        contact.setPhone(phone);
    }

    /**
     * Updates the address of an existing contact.
     * 
     * @param contactId the ID of the contact to update, must not be null
     * @param address the new address, must meet validation requirements
     * @throws IllegalArgumentException if contactId is null, contact not found, or address invalid
     */
    public void updateAddress(String contactId, String address) {
        Contact contact = getValidatedContact(contactId);
        contact.setAddress(address);
    }

    /**
     * Retrieves a contact by its unique ID.
     * 
     * @param contactId the ID of the contact to retrieve
     * @return the contact with the specified ID
     * @throws IllegalArgumentException if contactId is null or contact not found
     */
    public Contact getContact(String contactId) {
        return getValidatedContact(contactId);
    }

    /**
     * Gets the total number of contacts in the service.
     * 
     * @return the number of stored contacts
     */
    public int getContactCount() {
        return contacts.size();
    }
}