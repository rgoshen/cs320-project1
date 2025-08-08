package edu.snhu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for the Task class.
 * Tests include validation of constructor parameters, getter/setter methods,
 * edge cases, and boundary conditions as per CS320 requirements.
 * 
 * @author Rick Goshen
 * @version 1.0
 */
@DisplayName("Task Class Tests")
class TaskTest {

    /**
     * Tests for the Task constructor with valid and invalid parameters.
     */
    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        /**
         * Test the successful creation of a Task with valid parameters.
         */
        @Test
        @DisplayName("Should create task with valid parameters")
        void testValidTaskCreation() {
            // Given valid parameters
            String taskId = "TASK001";
            String taskName = "Test Task";
            String taskDescription = "This is a test task description";
            
            // When creating a new task
            Task task = new Task(taskId, taskName, taskDescription);
            
            // Then task should be created successfully with correct values
            assertEquals(taskId, task.getTaskId());
            assertEquals(taskName, task.getTaskName());
            assertEquals(taskDescription, task.getTaskDescription());
        }

        /**
         * Test task creation with maximum allowed field lengths.
         */
        @Test
        @DisplayName("Should create task with maximum length fields")
        void testTaskCreationWithMaxLengthFields() {
            // Given parameters at maximum allowed lengths
            String taskId = "1234567890"; // 10 characters
            String taskName = "12345678901234567890"; // 20 characters
            String taskDescription = "12345678901234567890123456789012345678901234567890"; // 50 characters
            
            // When creating a new task
            Task task = new Task(taskId, taskName, taskDescription);
            
            // Then task should be created successfully
            assertNotNull(task);
            assertEquals(taskId, task.getTaskId());
            assertEquals(taskName, task.getTaskName());
            assertEquals(taskDescription, task.getTaskDescription());
        }

        /**
         * Test task creation with minimum valid field lengths.
         */
        @Test
        @DisplayName("Should create task with minimum length fields")
        void testTaskCreationWithMinLengthFields() {
            // Given parameters with minimum valid lengths
            String taskId = "1"; // 1 character
            String taskName = "T"; // 1 character
            String taskDescription = "D"; // 1 character
            
            // When creating a new task
            Task task = new Task(taskId, taskName, taskDescription);
            
            // Then task should be created successfully
            assertNotNull(task);
            assertEquals(taskId, task.getTaskId());
            assertEquals(taskName, task.getTaskName());
            assertEquals(taskDescription, task.getTaskDescription());
        }
    }

    /**
     * Tests for constructor parameter validation and exception handling.
     */
    @Nested
    @DisplayName("Constructor Validation Tests")
    class ConstructorValidationTests {

        /**
         * Test that null task ID throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception for null task ID")
        void testNullTaskIdThrowsException() {
            // Given null task ID
            String taskId = null;
            String taskName = "Valid Name";
            String taskDescription = "Valid Description";
            
            // When/Then a creating task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Task(taskId, taskName, taskDescription)
            );
            assertEquals("Task ID cannot be null, empty, or exceed 10 characters", exception.getMessage());
        }

        /**
         * Test that task ID exceeding 10 characters throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception for task ID exceeding 10 characters")
        void testTaskIdTooLongThrowsException() {
            // Given task ID with 11 characters
            String taskId = "12345678901"; // 11 characters
            String taskName = "Valid Name";
            String taskDescription = "Valid Description";
            
            // When/Then creating task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Task(taskId, taskName, taskDescription)
            );
            assertEquals("Task ID cannot be null, empty, or exceed 10 characters", exception.getMessage());
        }

        /**
         * Test that a null task name throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception for null task name")
        void testNullTaskNameThrowsException() {
            // Given null task name
            String taskId = "TASK001";
            String taskName = null;
            String taskDescription = "Valid Description";
            
            // When/Then a creating task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Task(taskId, taskName, taskDescription)
            );
            assertEquals("Task name cannot be null and must be 20 characters or less", exception.getMessage());
        }

        /**
         * Test that task name exceeding 20 characters throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception for task name exceeding 20 characters")
        void testTaskNameTooLongThrowsException() {
            // Given task name with 21 characters
            String taskId = "TASK001";
            String taskName = "123456789012345678901"; // 21 characters
            String taskDescription = "Valid Description";
            
            // When/Then creating task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Task(taskId, taskName, taskDescription)
            );
            assertEquals("Task name cannot be null and must be 20 characters or less", exception.getMessage());
        }

        /**
         * Test that null task description throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception for null task description")
        void testNullTaskDescriptionThrowsException() {
            // Given null task description
            String taskId = "TASK001";
            String taskName = "Valid Name";
            String taskDescription = null;
            
            // When/Then creating task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Task(taskId, taskName, taskDescription)
            );
            assertEquals("Task description cannot be null and must be 50 characters or less", exception.getMessage());
        }

        /**
         * Test that task description exceeding 50 characters throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception for task description exceeding 50 characters")
        void testTaskDescriptionTooLongThrowsException() {
            // Given task description with 51 characters
            String taskId = "TASK001";
            String taskName = "Valid Name";
            String taskDescription = "123456789012345678901234567890123456789012345678901"; // 51 characters
            
            // When/Then a creating task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Task(taskId, taskName, taskDescription)
            );
            assertEquals("Task description cannot be null and must be 50 characters or less", exception.getMessage());
        }
    }

    /**
     * Tests for getter methods to ensure proper field access.
     */
    @Nested
    @DisplayName("Getter Method Tests")
    class GetterMethodTests {

        /**
         * Test that getTaskId returns the correct task ID.
         */
        @Test
        @DisplayName("Should return correct task ID")
        void testGetTaskId() {
            // Given a task with specific ID
            String expectedTaskId = "TASK123";
            Task task = new Task(expectedTaskId, "Test Name", "Test Description");
            
            // When getting task ID
            String actualTaskId = task.getTaskId();
            
            // Then returned ID should match expected
            assertEquals(expectedTaskId, actualTaskId);
        }

        /**
         * Test that getTaskName returns the correct task name.
         */
        @Test
        @DisplayName("Should return correct task name")
        void testGetTaskName() {
            // Given a task with a specific name
            String expectedTaskName = "Important Task";
            Task task = new Task("TASK001", expectedTaskName, "Test Description");
            
            // When getting a task name
            String actualTaskName = task.getTaskName();
            
            // Then the returned name should match expected
            assertEquals(expectedTaskName, actualTaskName);
        }

        /**
         * Test that getTaskDescription returns the correct task description.
         */
        @Test
        @DisplayName("Should return correct task description")
        void testGetTaskDescription() {
            // Given a task with a specific description
            String expectedDescription = "This is an important task to complete";
            Task task = new Task("TASK001", "Test Name", expectedDescription);
            
            // When getting task description
            String actualDescription = task.getTaskDescription();
            
            // Then the returned description should match expected
            assertEquals(expectedDescription, actualDescription);
        }
    }

    /**
     * Tests for setter methods and their validation.
     */
    @Nested
    @DisplayName("Setter Method Tests")
    class SetterMethodTests {

        /**
         * Test successful update of task name with valid value.
         */
        @Test
        @DisplayName("Should successfully update task name with valid value")
        void testSetTaskNameWithValidValue() {
            // Given an existing task
            Task task = new Task("TASK001", "Original Name", "Original Description");
            String newTaskName = "Updated Name";
            
            // When updating the task name
            task.setTaskName(newTaskName);
            
            // Then the task name should be updated
            assertEquals(newTaskName, task.getTaskName());
        }

        /**
         * Test that setting a null task name throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when setting null task name")
        void testSetTaskNameWithNullThrowsException() {
            // Given an existing task
            Task task = new Task("TASK001", "Original Name", "Original Description");
            
            // When/Then setting a null task name should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> task.setTaskName(null)
            );
            assertEquals("Task name cannot be null and must be 20 characters or less", exception.getMessage());
        }

        /**
         * Test that setting the task name exceeding 20 characters throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when setting task name exceeding 20 characters")
        void testSetTaskNameTooLongThrowsException() {
            // Given an existing task
            Task task = new Task("TASK001", "Original Name", "Original Description");
            String tooLongName = "123456789012345678901"; // 21 characters
            
            // When/Then setting too long task name should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> task.setTaskName(tooLongName)
            );
            assertEquals("Task name cannot be null and must be 20 characters or less", exception.getMessage());
        }

        /**
         * Test successful update of task description with valid value.
         */
        @Test
        @DisplayName("Should successfully update task description with valid value")
        void testSetTaskDescriptionWithValidValue() {
            // Given an existing task
            Task task = new Task("TASK001", "Task Name", "Original Description");
            String newDescription = "Updated task description";
            
            // When updating task description
            task.setTaskDescription(newDescription);
            
            // Then the task description should be updated
            assertEquals(newDescription, task.getTaskDescription());
        }

        /**
         * Test that setting a null task description throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when setting null task description")
        void testSetTaskDescriptionWithNullThrowsException() {
            // Given an existing task
            Task task = new Task("TASK001", "Task Name", "Original Description");
            
            // When/Then setting a null task description should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> task.setTaskDescription(null)
            );
            assertEquals("Task description cannot be null and must be 50 characters or less", exception.getMessage());
        }

        /**
         * Test that setting task description exceeding 50 characters throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when setting task description exceeding 50 characters")
        void testSetTaskDescriptionTooLongThrowsException() {
            // Given an existing task
            Task task = new Task("TASK001", "Task Name", "Original Description");
            String tooLongDescription = "123456789012345678901234567890123456789012345678901"; // 51 characters
            
            // When/Then setting too long task description should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> task.setTaskDescription(tooLongDescription)
            );
            assertEquals("Task description cannot be null and must be 50 characters or less", exception.getMessage());
        }

        /**
         * Test that task ID remains immutable (no setter method exists).
         */
        @Test
        @DisplayName("Should maintain task ID immutability")
        void testTaskIdImmutability() {
            // Given a task with a specific ID
            String originalTaskId = "TASK001";
            Task task = new Task(originalTaskId, "Task Name", "Task Description");
            
            // When checking task ID after creation
            String taskId = task.getTaskId();
            
            // Then task ID should remain unchanged and no setter should exist
            assertEquals(originalTaskId, taskId);
            
            // Verify no setTaskId method exists through reflection
            boolean hasSetTaskIdMethod = false;
            try {
                task.getClass().getDeclaredMethod("setTaskId", String.class);
                hasSetTaskIdMethod = true;
            } catch (NoSuchMethodException e) {
                // Expected - no setTaskId method should exist
            }
            assertFalse(hasSetTaskIdMethod, "Task ID should be immutable - no setTaskId method should exist");
        }
    }

    /**
     * Tests for edge cases and boundary conditions.
     */
    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        /**
         * Test that empty taskId throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception for empty task ID")
        void testEmptyTaskIdThrowsException() {
            // Given empty task ID
            String taskId = "";
            String taskName = "Valid Name";
            String taskDescription = "Valid Description";
            
            // When/Then creating task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Task(taskId, taskName, taskDescription)
            );
            assertEquals("Task ID cannot be null, empty, or exceed 10 characters", exception.getMessage());
        }

        /**
         * Test that whitespace-only taskId throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception for whitespace-only task ID")
        void testWhitespaceTaskIdThrowsException() {
            // Given whitespace-only task ID
            String taskId = "   ";
            String taskName = "Valid Name";
            String taskDescription = "Valid Description";
            
            // When/Then creating task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Task(taskId, taskName, taskDescription)
            );
            assertEquals("Task ID cannot be null, empty, or exceed 10 characters", exception.getMessage());
        }

        /**
         * Test task creation and updates with empty strings for name and description.
         */
        @Test
        @DisplayName("Should handle empty strings for name and description appropriately")
        void testEmptyStringHandling() {
            // Given empty strings for name and description (valid as they're not null and within length limits)
            String taskId = "TASK001";
            String emptyTaskName = "";
            String emptyTaskDescription = "";
            
            // When creating a task with empty name and description
            Task task = new Task(taskId, emptyTaskName, emptyTaskDescription);
            
            // Then a task should be created successfully
            assertNotNull(task);
            assertEquals(taskId, task.getTaskId());
            assertEquals(emptyTaskName, task.getTaskName());
            assertEquals(emptyTaskDescription, task.getTaskDescription());
            
            // When updating with empty strings
            task.setTaskName("");
            task.setTaskDescription("");
            
            // Then updates should be successful
            assertEquals("", task.getTaskName());
            assertEquals("", task.getTaskDescription());
        }

        /**
         * Test multiple sequential updates to task fields.
         */
        @Test
        @DisplayName("Should handle multiple sequential updates correctly")
        void testMultipleSequentialUpdates() {
            // Given an existing task
            Task task = new Task("TASK001", "Original Name", "Original Description");
            
            // When performing multiple updates
            task.setTaskName("First Update");
            task.setTaskDescription("First Description Update");
            task.setTaskName("Second Update");
            task.setTaskDescription("Second Description Update");
            
            // Then final values should reflect last updates
            assertEquals("Second Update", task.getTaskName());
            assertEquals("Second Description Update", task.getTaskDescription());
            assertEquals("TASK001", task.getTaskId()); // ID should remain unchanged
        }
    }
}