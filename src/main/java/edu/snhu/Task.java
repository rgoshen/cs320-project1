package edu.snhu;

/**
 * Represents a task with a unique identifier, name, and description.
 * The task ID is immutable once set, while the name and description can be updated.
 * All fields have validation constraints to ensure data integrity.
 * 
 * @author Rick Goshen
 * @version 1.0
 */
public class Task {
    private final String taskId;
    private String taskName;
    private String taskDescription;
    
    /**
     * Constructs a new Task with the specified ID, name, and description.
     * 
     * @param taskId the unique identifier for the task (max 10 characters, not null or empty)
     * @param taskName the name of the task (max 20 characters, not null)
     * @param taskDescription the description of the task (max 50 characters, not null)
     * @throws IllegalArgumentException if any parameter is null, taskId is empty, or exceeds length limits
     */
    public Task(String taskId, String taskName, String taskDescription) {
        validateTaskId(taskId);
        validateTaskName(taskName);
        validateTaskDescription(taskDescription);
        
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }
    
    /**
     * Returns the unique identifier of this task.
     * 
     * @return the task ID
     */
    public String getTaskId() {
        return taskId;
    }
    
    /**
     * Returns the name of this task.
     * 
     * @return the task name
     */
    public String getTaskName() {
        return taskName;
    }
    
    /**
     * Sets the name of this task.
     * 
     * @param taskName the new task name (max 20 characters, not null)
     * @throws IllegalArgumentException if taskName is null or exceeds 20 characters
     */
    public void setTaskName(String taskName) {
        validateTaskName(taskName);
        this.taskName = taskName;
    }
    
    /**
     * Returns the description of this task.
     * 
     * @return the task description
     */
    public String getTaskDescription() {
        return taskDescription;
    }
    
    /**
     * Sets the description of this task.
     * 
     * @param taskDescription the new task description (max 50 characters, not null)
     * @throws IllegalArgumentException if taskDescription is null or exceeds 50 characters
     */
    public void setTaskDescription(String taskDescription) {
        validateTaskDescription(taskDescription);
        this.taskDescription = taskDescription;
    }
    
    /**
     * Validates the task ID against business rules.
     * 
     * @param taskId the task ID to validate
     * @throws IllegalArgumentException if taskId is null, empty, or exceeds 10 characters
     */
    private void validateTaskId(String taskId) {
        if (taskId == null || taskId.trim().isEmpty() || taskId.length() > 10) {
            throw new IllegalArgumentException("Task ID cannot be null, empty, or exceed 10 characters");
        }
    }
    
    /**
     * Validates the task name against business rules.
     * 
     * @param taskName the task name to validate
     * @throws IllegalArgumentException if taskName is null or exceeds 20 characters
     */
    private void validateTaskName(String taskName) {
        if (taskName == null || taskName.length() > 20) {
            throw new IllegalArgumentException("Task name cannot be null and must be 20 characters or less");
        }
    }
    
    /**
     * Validates the task description against business rules.
     * 
     * @param taskDescription the task description to validate
     * @throws IllegalArgumentException if taskDescription is null or exceeds 50 characters
     */
    private void validateTaskDescription(String taskDescription) {
        if (taskDescription == null || taskDescription.length() > 50) {
            throw new IllegalArgumentException("Task description cannot be null and must be 50 characters or less");
        }
    }
}