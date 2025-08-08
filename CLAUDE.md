# CLAUDE.md - Enhanced CS-320 Project Guide

This file provides comprehensive guidance to Claude Code (claude.ai/code) when working with the CS-320 Software Test & QA Automation project, emphasizing Test-Driven Development (TDD), comprehensive testing strategies, and implementation gap analysis.

## Project Overview

This is a Java 23 Maven project implementing three back-end services for Grand Strand Systems using Test-Driven Development methodology. The project delivers Contact, Task, and Appointment services with comprehensive unit testing to meet customer requirements.

## Build and Test Commands

```bash
# Build the project
mvn compile

# Run all tests with coverage
mvn test

# Run specific test class
mvn test -Dtest=AppointmentTest
mvn test -Dtest=ContactTest
mvn test -Dtest=TaskTest
mvn test -Dtest=ContactServiceTest
mvn test -Dtest=TaskServiceTest
mvn test -Dtest=AppointmentServiceTest

# Generate test coverage report
mvn jacoco:report

# Package application
mvn package

# Clean build artifacts
mvn clean

# Verify all requirements with coverage check
mvn clean test jacoco:report
```

## TDD Workflow Protocol

### 🔴 RED - Write Failing Test First

When implementing any new feature or fixing gaps:

1. **Identify the requirement** from customer specifications
2. **Write a single, focused test** that should pass when requirement is met
3. **Run the test** - it should FAIL (red)
4. **Verify the test fails for the right reason**

### 🟢 GREEN - Write Minimal Implementation

5. **Write the smallest amount of code** to make the test pass
6. **Run the test** - it should PASS (green)
7. **Ensure no other tests break**

### 🔵 REFACTOR - Improve Code Quality

8. **Refactor implementation** while keeping tests green
9. **Run all tests** to ensure nothing breaks
10. **Repeat cycle** for next requirement

### TDD Example Workflow

```java
// Step 1: Write failing test
@Test
@DisplayName("Contact ID cannot be longer than 10 characters")
void testContactIdTooLong() {
    assertThrows(IllegalArgumentException.class, () -> {
        new Contact("12345678901", "John", "Doe", "1234567890", "123 Main St");
    });
}

// Step 2: Run test (should fail - RED)
// Step 3: Implement minimal code to pass test (GREEN)
// Step 4: Refactor if needed (REFACTOR)
```

## Project Architecture & Requirements Analysis

### Core Architecture Pattern

- **Entity Classes**: `Appointment`, `Contact`, `Task` - Immutable IDs with strict validation
- **Service Classes**: `AppointmentService`, `ContactService`, `TaskService` - CRUD operations with in-memory storage
- **Storage**: HashMap/ConcurrentHashMap for thread-safe operations
- **Package**: All classes in `edu.snhu` package

### Requirements Compliance Matrix

#### Contact Class Requirements ✅

| Requirement                                  | Implementation Status | Test Coverage           |
| -------------------------------------------- | --------------------- | ----------------------- |
| Unique ID ≤10 chars, not null, not updatable | Must verify           | Boundary tests needed   |
| firstName ≤10 chars, not null                | Must verify           | Null & length tests     |
| lastName ≤10 chars, not null                 | Must verify           | Null & length tests     |
| phone exactly 10 digits, not null            | Must verify           | Format validation tests |
| address ≤30 chars, not null                  | Must verify           | Boundary tests needed   |

#### ContactService Requirements ✅

| Requirement                                | Implementation Status | Test Coverage         |
| ------------------------------------------ | --------------------- | --------------------- |
| Add contacts with unique ID                | Must verify           | Duplicate ID tests    |
| Delete contacts per contactId              | Must verify           | Null/invalid ID tests |
| Update firstName, lastName, phone, address | Must verify           | Field update tests    |

#### Task Class Requirements ✅

| Requirement                                  | Implementation Status | Test Coverage         |
| -------------------------------------------- | --------------------- | --------------------- |
| Unique ID ≤10 chars, not null, not updatable | Must verify           | Boundary tests needed |
| name ≤20 chars, not null                     | Must verify           | Null & length tests   |
| description ≤50 chars, not null              | Must verify           | Boundary tests needed |

#### TaskService Requirements ✅

| Requirement              | Implementation Status | Test Coverage         |
| ------------------------ | --------------------- | --------------------- |
| Add tasks with unique ID | Must verify           | Duplicate ID tests    |
| Delete tasks per taskId  | Must verify           | Null/invalid ID tests |
| Update name, description | Must verify           | Field update tests    |

#### Appointment Class Requirements ✅

| Requirement                                       | Implementation Status | Test Coverage         |
| ------------------------------------------------- | --------------------- | --------------------- |
| Unique ID ≤10 chars, not null, not updatable      | Must verify           | Boundary tests needed |
| appointmentDate future, not null (java.util.Date) | Must verify           | Past date tests       |
| description ≤50 chars, not null                   | Must verify           | Boundary tests needed |

#### AppointmentService Requirements ✅

| Requirement                           | Implementation Status | Test Coverage         |
| ------------------------------------- | --------------------- | --------------------- |
| Add appointments with unique ID       | Must verify           | Duplicate ID tests    |
| Delete appointments per appointmentId | Must verify           | Null/invalid ID tests |

## Comprehensive Testing Strategy

### 1. Test Case Design Methodologies

#### Boundary Value Analysis

```java
// Example: Testing string length boundaries
@ParameterizedTest
@ValueSource(strings = {"", "a", "1234567890", "12345678901"})
@DisplayName("Contact ID boundary value testing")
void testContactIdBoundaries(String id) {
    if (id.length() == 0 || id.length() > 10) {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(id, "John", "Doe", "1234567890", "123 Main St"));
    } else {
        assertDoesNotThrow(() ->
            new Contact(id, "John", "Doe", "1234567890", "123 Main St"));
    }
}
```

#### Equivalence Partitioning

```java
// Valid partition: IDs 1-10 characters
// Invalid partitions: null, empty, >10 characters
@Test
@DisplayName("Valid ID partition - should accept")
void testValidIdPartition() {
    // Test valid IDs from each partition
    assertDoesNotThrow(() -> new Contact("1", "John", "Doe", "1234567890", "123 Main St"));
    assertDoesNotThrow(() -> new Contact("12345", "John", "Doe", "1234567890", "123 Main St"));
    assertDoesNotThrow(() -> new Contact("1234567890", "John", "Doe", "1234567890", "123 Main St"));
}
```

#### Error Guessing & Edge Cases

```java
@Test
@DisplayName("Edge case - special characters in phone number")
void testPhoneNumberSpecialCharacters() {
    assertThrows(IllegalArgumentException.class, () ->
        new Contact("123", "John", "Doe", "123-456-789", "123 Main St"));
}
```

### 2. Test Coverage Requirements

#### Minimum Coverage Targets

- **Line Coverage**: ≥90% (project requirement)
- **Branch Coverage**: ≥85% (recommended)
- **Method Coverage**: 100% (all public methods)

#### Coverage Analysis Command

```bash
mvn clean test jacoco:report
# View report at: target/site/jacoco/index.html
```

### 3. Test Categories & Organization

#### Entity Class Tests (Contact, Task, Appointment)

```java
@Nested
@DisplayName("Constructor Validation Tests")
class ConstructorValidationTests {
    // ID validation tests
    // Field validation tests
    // Null handling tests
}

@Nested
@DisplayName("Getter Method Tests")
class GetterMethodTests {
    // Verify immutability
    // Verify correct values returned
}

@Nested
@DisplayName("Setter Method Tests")
class SetterMethodTests {
    // Valid input tests
    // Invalid input tests
    // Boundary condition tests
}
```

#### Service Class Tests (ContactService, TaskService, AppointmentService)

```java
@Nested
@DisplayName("Add Operation Tests")
class AddOperationTests {
    // Successful addition tests
    // Duplicate ID tests
    // Null object tests
}

@Nested
@DisplayName("Delete Operation Tests")
class DeleteOperationTests {
    // Successful deletion tests
    // Non-existent ID tests
    // Null ID tests
}

@Nested
@DisplayName("Update Operation Tests")
class UpdateOperationTests {
    // Successful update tests
    // Invalid field tests
    // Non-existent ID tests
}
```

## Implementation Gap Analysis Protocol

### 1. Requirements vs Implementation Audit

When analyzing the codebase, systematically check:

#### For Each Entity Class:

```java
// Verification checklist:
// ✅ ID field is final (immutable)
// ✅ All fields have proper validation
// ✅ Constructor validates all inputs
// ✅ Setters validate inputs (where applicable)
// ✅ Getters return defensive copies (for Date objects)
// ✅ ToString method implemented (optional but helpful)
```

#### For Each Service Class:

```java
// Verification checklist:
// ✅ Uses appropriate data structure (HashMap/ConcurrentHashMap)
// ✅ Add method checks for duplicate IDs
// ✅ Delete method handles non-existent IDs gracefully
// ✅ Update methods validate inputs before updating
// ✅ All methods handle null inputs appropriately
// ✅ Thread safety considerations addressed
```

### 2. Gap Identification Process

#### Step 1: Requirement Mapping

```bash
# For each requirement, verify:
1. Is there a test that validates this requirement?
2. Does the implementation enforce this requirement?
3. Are edge cases covered?
4. Is error handling appropriate?
```

#### Step 2: Missing Test Identification

```java
// Common gaps to check for:
@Test void testNullId() { /* Test null ID handling */ }
@Test void testEmptyId() { /* Test empty ID handling */ }
@Test void testMaxLengthId() { /* Test boundary conditions */ }
@Test void testImmutableId() { /* Test ID cannot be changed */ }
@Test void testConcurrentAccess() { /* Test thread safety */ }
```

#### Step 3: TDD Gap Resolution

```java
// For each identified gap:
// 1. Write failing test first
@Test
@DisplayName("Contact phone must be exactly 10 digits")
void testPhoneMustBeExactly10Digits() {
    // Test 9 digits
    assertThrows(IllegalArgumentException.class, () ->
        new Contact("123", "John", "Doe", "123456789", "123 Main St"));

    // Test 11 digits
    assertThrows(IllegalArgumentException.class, () ->
        new Contact("123", "John", "Doe", "12345678901", "123 Main St"));
}

// 2. Run test (should fail)
// 3. Implement validation in Contact constructor
// 4. Run test (should pass)
// 5. Refactor if needed
```

## Industry Best Practices Implementation

### 1. Test Naming Conventions

```java
// Pattern: test[MethodName][Scenario][ExpectedResult]
@Test
@DisplayName("addContact with duplicate ID should throw IllegalArgumentException")
void testAddContactDuplicateIdShouldThrowException() {
    // Test implementation
}
```

### 2. Test Data Management

```java
// Use Test Data Builders for complex objects
public class ContactTestDataBuilder {
    private String id = "defaultId";
    private String firstName = "John";
    private String lastName = "Doe";
    private String phone = "1234567890";
    private String address = "123 Main St";

    public ContactTestDataBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public Contact build() {
        return new Contact(id, firstName, lastName, phone, address);
    }
}
```

### 3. Assertion Best Practices

```java
// Use specific assertions
assertThat(contact.getId()).isEqualTo("123");
assertThat(contacts).hasSize(1);
assertThat(exception.getMessage()).contains("ID cannot be null");

// Group related assertions
assertAll("Contact validation",
    () -> assertNotNull(contact.getId()),
    () -> assertEquals("John", contact.getFirstName()),
    () -> assertEquals("Doe", contact.getLastName())
);
```

### 4. Test Organization & Documentation

```java
@DisplayName("Contact Class Comprehensive Test Suite")
class ContactTest {

    @BeforeEach
    void setUp() {
        // Common test setup
    }

    @AfterEach
    void tearDown() {
        // Common cleanup
    }

    @Nested
    @DisplayName("Constructor Validation")
    class ConstructorValidation {
        // Group related tests
    }
}
```

## Automated Testing Pipeline

### 1. Continuous Integration Checks

```bash
# Pre-commit verification
mvn clean compile test

# Coverage verification
mvn jacoco:report
# Fail build if coverage < 90%

# Static analysis (optional)
mvn spotbugs:check
```

### 2. Test Execution Strategy

```java
// Fast unit tests - run frequently
@Tag("unit")
class ContactUnitTest { }

// Integration tests - run before commits
@Tag("integration")
class ContactServiceIntegrationTest { }

// Run specific test categories
mvn test -Dgroups="unit"
mvn test -Dgroups="integration"
```

## Performance Testing Considerations

### 1. Service Performance Tests

```java
@Test
@DisplayName("ContactService should handle 1000 contacts efficiently")
void testContactServicePerformance() {
    ContactService service = new ContactService();

    long startTime = System.nanoTime();

    for (int i = 0; i < 1000; i++) {
        service.addContact(new Contact(
            String.valueOf(i), "First" + i, "Last" + i,
            "1234567890", "Address " + i));
    }

    long endTime = System.nanoTime();
    long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds

    assertThat(duration).isLessThan(1000); // Should complete in < 1 second
    assertThat(service.getAllContacts()).hasSize(1000);
}
```

### 2. Memory Usage Tests

```java
@Test
@DisplayName("Service should not have memory leaks")
void testMemoryUsage() {
    ContactService service = new ContactService();

    // Add many contacts
    for (int i = 0; i < 10000; i++) {
        service.addContact(createTestContact(String.valueOf(i)));
    }

    // Delete all contacts
    for (int i = 0; i < 10000; i++) {
        service.deleteContact(String.valueOf(i));
    }

    // Force garbage collection
    System.gc();

    // Verify service is empty
    assertThat(service.getAllContacts()).isEmpty();
}
```

## Code Quality & Maintenance

### 1. Code Review Checklist

- [ ] All requirements have corresponding tests
- [ ] Tests follow TDD methodology
- [ ] Boundary conditions are tested
- [ ] Error conditions are tested
- [ ] Code coverage ≥ 90%
- [ ] Test names are descriptive
- [ ] No hardcoded values in tests
- [ ] Tests are independent and repeatable

### 2. Refactoring Guidelines

```java
// Before refactoring: Ensure all tests pass
mvn test

// After refactoring: Ensure tests still pass
mvn test

// Verify coverage maintained
mvn jacoco:report
```

### 3. Documentation Standards

```java
/**
 * Tests the Contact class constructor validation.
 *
 * Covers requirements:
 * - Contact ID ≤10 characters, not null, not updatable
 * - firstName ≤10 characters, not null
 * - lastName ≤10 characters, not null
 * - phone exactly 10 digits, not null
 * - address ≤30 characters, not null
 */
@Test
@DisplayName("Constructor should validate all required fields")
void testConstructorValidation() {
    // Test implementation with clear documentation
}
```

### 4. Commit Message Standards

#### Standard Commit Format

```bash
# Format: type(scope): description
feat(contact): add phone number validation
test(appointment): add boundary value tests
fix(task): resolve null pointer in update method
docs(readme): update usage examples
refactor(service): improve error handling logic
```

#### ⚠️ Important Commit Guidelines

- **NO AI attribution needed**: Do not mention Claude, AI assistance, or code generation tools
- **NO co-author attribution**: Commits should be authored by the developer only
- **Focus on WHAT changed**: Describe the functional change, not how it was created
- **Use conventional format**: type(scope): description pattern
- **Keep descriptions concise**: 50 characters or less for subject line

#### Examples of Proper Commits

```bash
✅ GOOD:
feat(contact): validate phone number format
test(appointment): add future date validation tests
fix(task): handle null description in constructor

❌ AVOID:
feat(contact): add phone validation with Claude assistance
test(appointment): add tests generated by AI
fix(task): Claude helped fix null pointer issue
Co-authored-by: Claude AI <claude@anthropic.com>
```

#### Why This Approach?

- **Professional standards**: Industry commits focus on functionality, not tooling
- **Clean history**: Git history should reflect business logic changes
- **Academic integrity**: Work should be attributed to the student developer
- **Portfolio quality**: Professional-looking commit history for career purposes

## Troubleshooting & Common Issues

### 1. Test Failures

```bash
# Run single test for debugging
mvn test -Dtest=ContactTest#testConstructorValidation

# Run tests with verbose output
mvn test -Dtest=ContactTest -Dsurefire.printSummary=true

# Skip tests temporarily
mvn compile -DskipTests
```

### 2. Coverage Issues

```bash
# Generate detailed coverage report
mvn clean test jacoco:report

# Check specific class coverage
# Look at target/site/jacoco/edu.snhu/Contact.html
```

### 3. Common Implementation Gaps

- Missing null checks in constructors
- Inadequate input validation
- Missing defensive copying for Date objects
- Insufficient error messages
- Missing edge case handling

## Final Verification Protocol

Before project submission:

1. **Run complete test suite**: `mvn clean test`
2. **Verify coverage**: `mvn jacoco:report` (≥90% required)
3. **Check all requirements**: Use Requirements Compliance Matrix
4. **Validate TDD approach**: Ensure tests were written before implementation
5. **Review test quality**: Verify boundary testing, equivalence partitioning
6. **Confirm documentation**: All tests have clear @DisplayName annotations

## Project Deliverables Checklist

### ContactService Module

- [ ] Contact.java - Entity with full validation
- [ ] ContactService.java - CRUD operations
- [ ] ContactTest.java - Comprehensive entity tests
- [ ] ContactServiceTest.java - Service operation tests

### TaskService Module

- [ ] Task.java - Entity with full validation
- [ ] TaskService.java - CRUD operations
- [ ] TaskTest.java - Comprehensive entity tests
- [ ] TaskServiceTest.java - Service operation tests

### AppointmentService Module

- [ ] Appointment.java - Entity with full validation
- [ ] AppointmentService.java - CRUD operations
- [ ] AppointmentTest.java - Comprehensive entity tests
- [ ] AppointmentServiceTest.java - Service operation tests

### Quality Assurance

- [ ] All tests pass: `mvn test`
- [ ] Coverage ≥80%: `mvn jacoco:report`
- [ ] TDD methodology followed throughout
- [ ] Industry best practices implemented
- [ ] Comprehensive documentation provided
