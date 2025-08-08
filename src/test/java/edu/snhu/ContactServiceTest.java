package edu.snhu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive test suite for the ContactService class.
 * Tests all CRUD operations and validation requirements for CS320 compliance.
 * Covers add, delete, update operations, unique ID enforcement,
 * and error handling for all service methods.
 * 
 * @author Rick Goshen
 * @version 1.0
 */
class ContactServiceTest {
    
    private ContactService contactService;
    private Contact testContact;

    @BeforeEach
    void setUp() {
        contactService = new ContactService();
        testContact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St");
    }

    @Test
    void testAddContact() {
        contactService.addContact(testContact);
        assertEquals(testContact, contactService.getContact("1234567890"));
        assertEquals(1, contactService.getContactCount());
    }

    @Test
    void testAddContactNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact(null);
        });
    }

    @Test
    void testAddContactDuplicateId() {
        contactService.addContact(testContact);
        Contact duplicateContact = new Contact("1234567890", "Jane", "Smith", "0987654321", "456 Oak Ave");
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact(duplicateContact);
        });
    }

    @Test
    void testAddMultipleContacts() {
        Contact contact2 = new Contact("0987654321", "Jane", "Smith", "0987654321", "456 Oak Ave");
        
        contactService.addContact(testContact);
        contactService.addContact(contact2);
        
        assertEquals(2, contactService.getContactCount());
        assertEquals(testContact, contactService.getContact("1234567890"));
        assertEquals(contact2, contactService.getContact("0987654321"));
    }

    /**
     * Test valid contact addition using string parameters.
     * Verifies overloaded method creates contact and stores it correctly.
     */
    @Test
    void testAddContactWithStringParameters() {
        contactService.addContact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        
        Contact retrieved = contactService.getContact("1234567890");
        assertEquals("1234567890", retrieved.getContactId());
        assertEquals("John", retrieved.getFirstName());
        assertEquals("Doe", retrieved.getLastName());
        assertEquals("1234567890", retrieved.getPhone());
        assertEquals("123 Main St", retrieved.getAddress());
        assertEquals(1, contactService.getContactCount());
    }

    /**
     * Test that overloaded addContact prevents duplicate IDs.
     * CS320 requirement: Contact IDs must be unique across the service.
     */
    @Test
    void testAddContactWithStringParametersDuplicateId() {
        contactService.addContact("1234567890", "John", "Doe", "1234567890", "123 Main St");
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("1234567890", "Jane", "Smith", "0987654321", "456 Oak Ave");
        });
    }

    /**
     * Test that overloaded addContact validates contact ID parameter.
     * CS320 requirement: Contact ID cannot be null and must be 10 characters or less.
     */
    @Test
    void testAddContactWithStringParametersInvalidContactId() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact(null, "John", "Doe", "1234567890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("12345678901", "John", "Doe", "1234567890", "123 Main St");
        });
    }

    /**
     * Test that overloaded addContact validates first name parameter.
     * CS320 requirement: First name cannot be null and must be 10 characters or less.
     */
    @Test
    void testAddContactWithStringParametersInvalidFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("1234567890", null, "Doe", "1234567890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("1234567890", "12345678901", "Doe", "1234567890", "123 Main St");
        });
    }

    /**
     * Test that overloaded addContact validates last name parameter.
     * CS320 requirement: Last name cannot be null and must be 10 characters or less.
     */
    @Test
    void testAddContactWithStringParametersInvalidLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("1234567890", "John", null, "1234567890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("1234567890", "John", "12345678901", "1234567890", "123 Main St");
        });
    }

    /**
     * Test that overloaded addContact validates phone parameter.
     * CS320 requirement: Phone number cannot be null and must be exactly 10 digits.
     */
    @Test
    void testAddContactWithStringParametersInvalidPhone() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("1234567890", "John", "Doe", null, "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("1234567890", "John", "Doe", "123456789", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("1234567890", "John", "Doe", "12345678901", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("1234567890", "John", "Doe", "123456789a", "123 Main St");
        });
    }

    /**
     * Test that overloaded addContact validates address parameter.
     * CS320 requirement: Address cannot be null and must be 30 characters or less.
     */
    @Test
    void testAddContactWithStringParametersInvalidAddress() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("1234567890", "John", "Doe", "1234567890", null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact("1234567890", "John", "Doe", "1234567890", "1234567890123456789012345678901");
        });
    }

    @Test
    void testDeleteContact() {
        contactService.addContact(testContact);
        contactService.deleteContact("1234567890");
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.getContact("1234567890");
        });
        assertEquals(0, contactService.getContactCount());
    }

    @Test
    void testDeleteContactNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.deleteContact(null);
        });
    }

    @Test
    void testDeleteContactNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.deleteContact("nonexistent");
        });
    }

    @Test
    void testUpdateFirstName() {
        contactService.addContact(testContact);
        contactService.updateFirstName("1234567890", "Jane");
        
        assertEquals("Jane", contactService.getContact("1234567890").getFirstName());
    }

    @Test
    void testUpdateFirstNameNullId() {
        contactService.addContact(testContact);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateFirstName(null, "Jane");
        });
    }

    @Test
    void testUpdateFirstNameContactNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateFirstName("nonexistent", "Jane");
        });
    }

    @Test
    void testUpdateFirstNameInvalid() {
        contactService.addContact(testContact);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateFirstName("1234567890", null);
        });
    }

    @Test
    void testUpdateLastName() {
        contactService.addContact(testContact);
        contactService.updateLastName("1234567890", "Smith");
        
        assertEquals("Smith", contactService.getContact("1234567890").getLastName());
    }

    @Test
    void testUpdateLastNameNullId() {
        contactService.addContact(testContact);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateLastName(null, "Smith");
        });
    }

    @Test
    void testUpdateLastNameContactNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateLastName("nonexistent", "Smith");
        });
    }

    @Test
    void testUpdateLastNameInvalid() {
        contactService.addContact(testContact);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateLastName("1234567890", null);
        });
    }

    @Test
    void testUpdatePhone() {
        contactService.addContact(testContact);
        contactService.updatePhone("1234567890", "0987654321");
        
        assertEquals("0987654321", contactService.getContact("1234567890").getPhone());
    }

    @Test
    void testUpdatePhoneNullId() {
        contactService.addContact(testContact);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updatePhone(null, "0987654321");
        });
    }

    @Test
    void testUpdatePhoneContactNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updatePhone("nonexistent", "0987654321");
        });
    }

    @Test
    void testUpdatePhoneInvalid() {
        contactService.addContact(testContact);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updatePhone("1234567890", "123");
        });
    }

    @Test
    void testUpdateAddress() {
        contactService.addContact(testContact);
        contactService.updateAddress("1234567890", "456 Oak Ave");
        
        assertEquals("456 Oak Ave", contactService.getContact("1234567890").getAddress());
    }

    @Test
    void testUpdateAddressNullId() {
        contactService.addContact(testContact);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateAddress(null, "456 Oak Ave");
        });
    }

    @Test
    void testUpdateAddressContactNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateAddress("nonexistent", "456 Oak Ave");
        });
    }

    @Test
    void testUpdateAddressInvalid() {
        contactService.addContact(testContact);
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateAddress("1234567890", null);
        });
    }

    @Test
    void testGetContact() {
        contactService.addContact(testContact);
        Contact retrieved = contactService.getContact("1234567890");
        
        assertEquals(testContact, retrieved);
    }

    @Test
    void testGetContactNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.getContact("nonexistent");
        });
    }

    @Test
    void testGetContactNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.getContact(null);
        });
    }

    @Test
    void testGetContactCountEmpty() {
        assertEquals(0, contactService.getContactCount());
    }

    @Test
    void testGetContactCountMultiple() {
        Contact contact2 = new Contact("0987654321", "Jane", "Smith", "0987654321", "456 Oak Ave");
        
        contactService.addContact(testContact);
        assertEquals(1, contactService.getContactCount());
        
        contactService.addContact(contact2);
        assertEquals(2, contactService.getContactCount());
        
        contactService.deleteContact("1234567890");
        assertEquals(1, contactService.getContactCount());
    }
}