package edu.snhu;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class for managing Task objects with in-memory storage.
 * Provides CRUD operations (Create, Read, Update, Delete) for tasks
 * using a ConcurrentHashMap for storage with unique ID enforcement.
 * 
 * @author Rick Goshen
 * @version 1.0
 */
public class TaskService {
    private final Map<String, Task> tasks;
    
    /**
     * Constructs a new TaskService with an empty task storage.
     * Initializes ConcurrentHashMap with optimal initial capacity to minimize resizing.
     */
    public TaskService() {
        this.tasks = new ConcurrentHashMap<>(16, 0.75f);
    }
    
    /**
     * Adds a new task to the service.
     * The task ID must be unique - attempting to add a task with an existing ID will throw an exception.
     * 
     * @param task the task to add (must not be null and must have a unique ID)
     * @throws IllegalArgumentException if task is null or if a task with the same ID already exists
     */
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        
        String taskId = task.getTaskId();
        if (tasks.containsKey(taskId)) {
            throw new IllegalArgumentException("Task with ID '" + taskId + "' already exists");
        }
        
        tasks.put(taskId, task);
    }
    
    /**
     * Deletes a task from the service by its ID.
     * 
     * @param taskId the ID of the task to delete (must not be null)
     * @throws IllegalArgumentException if taskId is null or if no task with the given ID exists
     */
    public void deleteTask(String taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        
        if (!tasks.containsKey(taskId)) {
            throw new IllegalArgumentException("Task with ID '" + taskId + "' does not exist");
        }
        
        tasks.remove(taskId);
    }
    
    /**
     * Updates the name of an existing task.
     * 
     * @param taskId the ID of the task to update (must not be null)
     * @param taskName the new task name (must meet Task validation requirements)
     * @throws IllegalArgumentException if taskId is null, task doesn't exist, or taskName is invalid
     */
    public void updateTaskName(String taskId, String taskName) {
        Task task = getTaskById(taskId);
        task.setTaskName(taskName);
    }
    
    /**
     * Updates the description of an existing task.
     * 
     * @param taskId the ID of the task to update (must not be null)
     * @param taskDescription the new task description (must meet Task validation requirements)
     * @throws IllegalArgumentException if taskId is null, task doesn't exist, or taskDescription is invalid
     */
    public void updateTaskDescription(String taskId, String taskDescription) {
        Task task = getTaskById(taskId);
        task.setTaskDescription(taskDescription);
    }
    
    /**
     * Retrieves a task by its ID.
     * 
     * @param taskId the ID of the task to retrieve (must not be null)
     * @return the task with the specified ID
     * @throws IllegalArgumentException if taskId is null or if no task with the given ID exists
     */
    public Task getTask(String taskId) {
        return getTaskById(taskId);
    }
    
    /**
     * Returns the number of tasks currently stored in the service.
     * 
     * @return the number of tasks
     */
    public int getTaskCount() {
        return tasks.size();
    }
    
    /**
     * Checks if a task with the specified ID exists in the service.
     * 
     * @param taskId the ID to check (must not be null)
     * @return true if a task with the given ID exists, false otherwise
     * @throws IllegalArgumentException if taskId is null
     */
    public boolean taskExists(String taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        return tasks.containsKey(taskId);
    }
    
    /**
     * Helper method to retrieve a task by ID with proper validation.
     * 
     * @param taskId the ID of the task to retrieve
     * @return the task with the specified ID
     * @throws IllegalArgumentException if taskId is null or task doesn't exist
     */
    private Task getTaskById(String taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task with ID '" + taskId + "' does not exist");
        }
        
        return task;
    }
}