package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(
        name = "Task Management",
        description = "APIs for managing tasks with role-based security"
)
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET all tasks
    @Operation(
            summary = "Get all tasks",
            description = "Retrieve all tasks. Accessible by USER and ADMIN."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // GET task by ID
    @Operation(
            summary = "Get task by ID",
            description = "Retrieve a specific task using its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    // POST new task
    @Operation(
            summary = "Create a new task",
            description = "Create a task. Only ADMIN can perform this operation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied. ADMIN role required.", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.addTask(task));
    }

    // PUT update task
    @Operation(
            summary = "Update an existing task",
            description = "Update a task using its ID. Only ADMIN can update tasks."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied. ADMIN role required.", content = @Content)
    })
    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task task) {

        return taskService.updateTask(id, task);
    }

    // DELETE task
    @Operation(
            summary = "Delete a task",
            description = "Delete a task using its ID. Only ADMIN can delete tasks."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied. ADMIN role required.", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.ok("Task deleted successfully");
    }

    // GET tasks with pagination
    @Operation(
            summary = "Get tasks with pagination",
            description = "Retrieve tasks using page number and page size."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paginated tasks retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping("/page")
    public Page<Task> getTasksWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return taskService.getTasksWithPagination(page, size);
    }

    // Search tasks by title
    @Operation(
            summary = "Search tasks by title",
            description = "Retrieve tasks matching the provided title."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping("/search")
    public List<Task> searchTasks(@RequestParam String title) {
        return taskService.searchTasks(title);
    }
}