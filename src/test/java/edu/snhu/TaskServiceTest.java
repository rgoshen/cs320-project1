package edu.snhu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for the TaskService class.
 * Tests include CRUD operations, validation, edge cases, and boundary conditions
 * as per CS320 requirements for the task service implementation.
 * 
 * @author Rick Goshen
 * @version 1.0
 */
@DisplayName("TaskService Class Tests")
class TaskServiceTest {

    private TaskService taskService;
    private Task validTask;

    /**
     * Set up test fixtures before each test method.
     */
    @BeforeEach
    void setUp() {
        taskService = new TaskService();
        validTask = new Task("TASK001", "Test Task", "Test Description");
    }

    /**
     * Tests for TaskService constructor and initial state.
     */
    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        /**
         * Test that TaskService is created with empty storage.
         */
        @Test
        @DisplayName("Should create empty TaskService")
        void testTaskServiceCreation() {
            // Given a new TaskService
            TaskService newService = new TaskService();
            
            // Then it should be empty
            assertEquals(0, newService.getTaskCount());
        }
    }

    /**
     * Tests for adding tasks to the service.
     */
    @Nested
    @DisplayName("Add Task Tests")
    class AddTaskTests {

        /**
         * Test successful addition of a valid task.
         */
        @Test
        @DisplayName("Should successfully add valid task")
        void testAddValidTask() {
            // When adding a valid task
            taskService.addTask(validTask);
            
            // Then task should be added successfully
            assertEquals(1, taskService.getTaskCount());
            assertTrue(taskService.taskExists("TASK001"));
            assertEquals(validTask, taskService.getTask("TASK001"));
        }

        /**
         * Test adding multiple unique tasks.
         */
        @Test
        @DisplayName("Should add multiple unique tasks")
        void testAddMultipleTasks() {
            // Given multiple unique tasks
            Task task1 = new Task("TASK001", "First Task", "First Description");
            Task task2 = new Task("TASK002", "Second Task", "Second Description");
            Task task3 = new Task("TASK003", "Third Task", "Third Description");
            
            // When adding all tasks
            taskService.addTask(task1);
            taskService.addTask(task2);
            taskService.addTask(task3);
            
            // Then all tasks should be added
            assertEquals(3, taskService.getTaskCount());
            assertTrue(taskService.taskExists("TASK001"));
            assertTrue(taskService.taskExists("TASK002"));
            assertTrue(taskService.taskExists("TASK003"));
        }

        /**
         * Test that adding null task throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when adding null task")
        void testAddNullTaskThrowsException() {
            // When/Then adding null task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.addTask(null)
            );
            assertEquals("Task cannot be null", exception.getMessage());
            assertEquals(0, taskService.getTaskCount());
        }

        /**
         * Test that adding duplicate task ID throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when adding duplicate task ID")
        void testAddDuplicateTaskIdThrowsException() {
            // Given an existing task
            taskService.addTask(validTask);
            Task duplicateTask = new Task("TASK001", "Different Name", "Different Description");
            
            // When/Then adding task with same ID should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.addTask(duplicateTask)
            );
            assertEquals("Task with ID 'TASK001' already exists", exception.getMessage());
            assertEquals(1, taskService.getTaskCount());
        }
    }

    /**
     * Tests for deleting tasks from the service.
     */
    @Nested
    @DisplayName("Delete Task Tests")
    class DeleteTaskTests {

        /**
         * Test successful deletion of an existing task.
         */
        @Test
        @DisplayName("Should successfully delete existing task")
        void testDeleteExistingTask() {
            // Given an existing task
            taskService.addTask(validTask);
            assertEquals(1, taskService.getTaskCount());
            
            // When deleting the task
            taskService.deleteTask("TASK001");
            
            // Then task should be removed
            assertEquals(0, taskService.getTaskCount());
            assertFalse(taskService.taskExists("TASK001"));
        }

        /**
         * Test deleting one of multiple tasks.
         */
        @Test
        @DisplayName("Should delete specific task from multiple tasks")
        void testDeleteSpecificTask() {
            // Given multiple tasks
            Task task1 = new Task("TASK001", "First Task", "First Description");
            Task task2 = new Task("TASK002", "Second Task", "Second Description");
            taskService.addTask(task1);
            taskService.addTask(task2);
            
            // When deleting one task
            taskService.deleteTask("TASK001");
            
            // Then only the specified task should be removed
            assertEquals(1, taskService.getTaskCount());
            assertFalse(taskService.taskExists("TASK001"));
            assertTrue(taskService.taskExists("TASK002"));
        }

        /**
         * Test that deleting non-existent task throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when deleting non-existent task")
        void testDeleteNonExistentTaskThrowsException() {
            // When/Then deleting non-existent task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.deleteTask("NONEXISTENT")
            );
            assertEquals("Task with ID 'NONEXISTENT' does not exist", exception.getMessage());
        }

        /**
         * Test that deleting with null ID throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when deleting with null ID")
        void testDeleteNullIdThrowsException() {
            // When/Then deleting with null ID should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.deleteTask(null)
            );
            assertEquals("Task ID cannot be null", exception.getMessage());
        }
    }

    /**
     * Tests for updating task names.
     */
    @Nested
    @DisplayName("Update Task Name Tests")
    class UpdateTaskNameTests {

        /**
         * Test successful update of task name.
         */
        @Test
        @DisplayName("Should successfully update task name")
        void testUpdateTaskNameSuccess() {
            // Given an existing task
            taskService.addTask(validTask);
            String newName = "Updated Task Name";
            
            // When updating task name
            taskService.updateTaskName("TASK001", newName);
            
            // Then task name should be updated
            Task updatedTask = taskService.getTask("TASK001");
            assertEquals(newName, updatedTask.getTaskName());
            assertEquals("TASK001", updatedTask.getTaskId()); // ID should remain unchanged
            assertEquals("Test Description", updatedTask.getTaskDescription()); // Description should remain unchanged
        }

        /**
         * Test updating task name with maximum length.
         */
        @Test
        @DisplayName("Should update task name with maximum length")
        void testUpdateTaskNameMaxLength() {
            // Given an existing task
            taskService.addTask(validTask);
            String maxLengthName = "12345678901234567890"; // 20 characters
            
            // When updating with max length name
            taskService.updateTaskName("TASK001", maxLengthName);
            
            // Then update should be successful
            assertEquals(maxLengthName, taskService.getTask("TASK001").getTaskName());
        }

        /**
         * Test that updating non-existent task throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when updating non-existent task name")
        void testUpdateNonExistentTaskNameThrowsException() {
            // When/Then updating non-existent task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.updateTaskName("NONEXISTENT", "New Name")
            );
            assertEquals("Task with ID 'NONEXISTENT' does not exist", exception.getMessage());
        }

        /**
         * Test that updating with null task ID throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when updating with null task ID")
        void testUpdateTaskNameNullIdThrowsException() {
            // When/Then updating with null ID should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.updateTaskName(null, "New Name")
            );
            assertEquals("Task ID cannot be null", exception.getMessage());
        }

        /**
         * Test that updating with invalid task name throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when updating with invalid task name")
        void testUpdateTaskNameInvalidNameThrowsException() {
            // Given an existing task
            taskService.addTask(validTask);
            String tooLongName = "123456789012345678901"; // 21 characters
            
            // When/Then updating with invalid name should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.updateTaskName("TASK001", tooLongName)
            );
            assertEquals("Task name cannot be null and must be 20 characters or less", exception.getMessage());
        }
    }

    /**
     * Tests for updating task descriptions.
     */
    @Nested
    @DisplayName("Update Task Description Tests")
    class UpdateTaskDescriptionTests {

        /**
         * Test successful update of task description.
         */
        @Test
        @DisplayName("Should successfully update task description")
        void testUpdateTaskDescriptionSuccess() {
            // Given an existing task
            taskService.addTask(validTask);
            String newDescription = "Updated task description";
            
            // When updating task description
            taskService.updateTaskDescription("TASK001", newDescription);
            
            // Then task description should be updated
            Task updatedTask = taskService.getTask("TASK001");
            assertEquals(newDescription, updatedTask.getTaskDescription());
            assertEquals("TASK001", updatedTask.getTaskId()); // ID should remain unchanged
            assertEquals("Test Task", updatedTask.getTaskName()); // Name should remain unchanged
        }

        /**
         * Test updating task description with maximum length.
         */
        @Test
        @DisplayName("Should update task description with maximum length")
        void testUpdateTaskDescriptionMaxLength() {
            // Given an existing task
            taskService.addTask(validTask);
            String maxLengthDescription = "12345678901234567890123456789012345678901234567890"; // 50 characters
            
            // When updating with max length description
            taskService.updateTaskDescription("TASK001", maxLengthDescription);
            
            // Then update should be successful
            assertEquals(maxLengthDescription, taskService.getTask("TASK001").getTaskDescription());
        }

        /**
         * Test that updating non-existent task throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when updating non-existent task description")
        void testUpdateNonExistentTaskDescriptionThrowsException() {
            // When/Then updating non-existent task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.updateTaskDescription("NONEXISTENT", "New Description")
            );
            assertEquals("Task with ID 'NONEXISTENT' does not exist", exception.getMessage());
        }

        /**
         * Test that updating with null task ID throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when updating with null task ID")
        void testUpdateTaskDescriptionNullIdThrowsException() {
            // When/Then updating with null ID should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.updateTaskDescription(null, "New Description")
            );
            assertEquals("Task ID cannot be null", exception.getMessage());
        }

        /**
         * Test that updating with invalid task description throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when updating with invalid task description")
        void testUpdateTaskDescriptionInvalidDescriptionThrowsException() {
            // Given an existing task
            taskService.addTask(validTask);
            String tooLongDescription = "123456789012345678901234567890123456789012345678901"; // 51 characters
            
            // When/Then updating with invalid description should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.updateTaskDescription("TASK001", tooLongDescription)
            );
            assertEquals("Task description cannot be null and must be 50 characters or less", exception.getMessage());
        }
    }

    /**
     * Tests for retrieving tasks from the service.
     */
    @Nested
    @DisplayName("Get Task Tests")
    class GetTaskTests {

        /**
         * Test successful retrieval of existing task.
         */
        @Test
        @DisplayName("Should successfully retrieve existing task")
        void testGetExistingTask() {
            // Given an existing task
            taskService.addTask(validTask);
            
            // When retrieving the task
            Task retrievedTask = taskService.getTask("TASK001");
            
            // Then correct task should be returned
            assertNotNull(retrievedTask);
            assertEquals(validTask, retrievedTask);
            assertEquals("TASK001", retrievedTask.getTaskId());
            assertEquals("Test Task", retrievedTask.getTaskName());
            assertEquals("Test Description", retrievedTask.getTaskDescription());
        }

        /**
         * Test that getting non-existent task throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when getting non-existent task")
        void testGetNonExistentTaskThrowsException() {
            // When/Then getting non-existent task should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.getTask("NONEXISTENT")
            );
            assertEquals("Task with ID 'NONEXISTENT' does not exist", exception.getMessage());
        }

        /**
         * Test that getting task with null ID throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when getting task with null ID")
        void testGetTaskNullIdThrowsException() {
            // When/Then getting task with null ID should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.getTask(null)
            );
            assertEquals("Task ID cannot be null", exception.getMessage());
        }
    }

    /**
     * Tests for utility methods and edge cases.
     */
    @Nested
    @DisplayName("Utility Method Tests")
    class UtilityMethodTests {

        /**
         * Test task count functionality.
         */
        @Test
        @DisplayName("Should accurately track task count")
        void testTaskCount() {
            // Given empty service
            assertEquals(0, taskService.getTaskCount());
            
            // When adding tasks
            taskService.addTask(new Task("TASK001", "Task 1", "Description 1"));
            assertEquals(1, taskService.getTaskCount());
            
            taskService.addTask(new Task("TASK002", "Task 2", "Description 2"));
            assertEquals(2, taskService.getTaskCount());
            
            // When deleting task
            taskService.deleteTask("TASK001");
            assertEquals(1, taskService.getTaskCount());
            
            taskService.deleteTask("TASK002");
            assertEquals(0, taskService.getTaskCount());
        }

        /**
         * Test task existence checking.
         */
        @Test
        @DisplayName("Should correctly check task existence")
        void testTaskExists() {
            // Given empty service
            assertFalse(taskService.taskExists("TASK001"));
            
            // When adding task
            taskService.addTask(validTask);
            assertTrue(taskService.taskExists("TASK001"));
            assertFalse(taskService.taskExists("TASK002"));
            
            // When deleting task
            taskService.deleteTask("TASK001");
            assertFalse(taskService.taskExists("TASK001"));
        }

        /**
         * Test that taskExists with null ID throws IllegalArgumentException.
         */
        @Test
        @DisplayName("Should throw exception when checking existence with null ID")
        void testTaskExistsNullIdThrowsException() {
            // When/Then checking existence with null ID should throw IllegalArgumentException
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.taskExists(null)
            );
            assertEquals("Task ID cannot be null", exception.getMessage());
        }
    }

    /**
     * Tests for edge cases and complex scenarios.
     */
    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        /**
         * Test adding, updating, and deleting same task.
         */
        @Test
        @DisplayName("Should handle complete lifecycle of a task")
        void testTaskLifecycle() {
            // Given a task to be managed through its lifecycle
            String taskId = "LIFECYCLE";
            Task task = new Task(taskId, "Original Name", "Original Description");
            
            // When adding task
            taskService.addTask(task);
            assertTrue(taskService.taskExists(taskId));
            assertEquals(1, taskService.getTaskCount());
            
            // When updating task name
            taskService.updateTaskName(taskId, "Updated Name");
            assertEquals("Updated Name", taskService.getTask(taskId).getTaskName());
            
            // When updating task description
            taskService.updateTaskDescription(taskId, "Updated Description");
            assertEquals("Updated Description", taskService.getTask(taskId).getTaskDescription());
            
            // When deleting task
            taskService.deleteTask(taskId);
            assertFalse(taskService.taskExists(taskId));
            assertEquals(0, taskService.getTaskCount());
        }

        /**
         * Test operations on empty service.
         */
        @Test
        @DisplayName("Should handle operations on empty service gracefully")
        void testOperationsOnEmptyService() {
            // Given empty service
            assertEquals(0, taskService.getTaskCount());
            
            // When performing operations on empty service
            assertFalse(taskService.taskExists("ANYID"));
            
            // Then all operations should behave predictably
            assertThrows(IllegalArgumentException.class, () -> taskService.getTask("ANYID"));
            assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask("ANYID"));
            assertThrows(IllegalArgumentException.class, () -> taskService.updateTaskName("ANYID", "Name"));
            assertThrows(IllegalArgumentException.class, () -> taskService.updateTaskDescription("ANYID", "Description"));
        }

        /**
         * Test with edge case task IDs.
         */
        @Test
        @DisplayName("Should handle edge case task IDs correctly")
        void testEdgeCaseTaskIds() {
            // Given tasks with edge case IDs
            Task singleCharTask = new Task("A", "Single Char", "Single character ID");
            Task maxLengthTask = new Task("1234567890", "Max Length", "Maximum length ID");
            
            // When adding and retrieving tasks
            taskService.addTask(singleCharTask);
            taskService.addTask(maxLengthTask);
            
            // Then operations should work correctly
            assertEquals(2, taskService.getTaskCount());
            assertTrue(taskService.taskExists("A"));
            assertTrue(taskService.taskExists("1234567890"));
            assertEquals(singleCharTask, taskService.getTask("A"));
            assertEquals(maxLengthTask, taskService.getTask("1234567890"));
        }
    }
}