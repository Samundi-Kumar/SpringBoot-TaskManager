
package com.example.taskmanager.service;

import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class TaskService {

    // Logger declaration
    private static final Logger logger =
            LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    // Constructor
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        logger.debug("DEBUG: getAllTasks method started");
        logger.info("Fetching all tasks");

        return taskRepository.findAll();
    }

    // Get task by ID
    public Task getTaskById(Long id) {
        logger.info("Fetching task with ID: {}", id);

        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with ID: " + id
                        ));
    }

    // Add a new task
    public Task addTask(Task task) {
        logger.info("Adding a new task: {}", task.getTitle());

        task.setId(null);

        return taskRepository.save(task);
    }

    // Update task
    public Task updateTask(Long id, Task updatedTask) {
        logger.info("Updating task with ID: {}", id);

        Task existingTask = getTaskById(id);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());

        return taskRepository.save(existingTask);
    }

    // Delete task
    public void deleteTask(Long id) {
        logger.info("Deleting task with ID: {}", id);

        Task task = getTaskById(id);

        taskRepository.delete(task);
    }

    // Get tasks with pagination
    public Page<Task> getTasksWithPagination(int page, int size) {
        logger.info("Fetching tasks - page: {}, size: {}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        return taskRepository.findAll(pageable);
    }

    // Search tasks by title
    public List<Task> searchTasks(String title) {
        logger.info("Searching tasks with title: {}", title);

        return taskRepository.findByTitleContainingIgnoreCase(title);
    }
}
