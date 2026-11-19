
package com.example.taskmanager.service;

import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    // Test 1: Get all tasks
    @Test
    void testGetAllTasks() {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Spring Boot");

        when(taskRepository.findAll())
                .thenReturn(List.of(task));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(1, tasks.size());
        assertEquals("Learn Spring Boot", tasks.get(0).getTitle());

        verify(taskRepository, times(1)).findAll();
    }

    // Test 2: Add a new task
    @Test
    void testAddTask() {

        Task task = new Task();
        task.setTitle("Learn Java");

        when(taskRepository.save(task)).thenReturn(task);

        Task savedTask = taskService.addTask(task);

        assertNotNull(savedTask);
        assertEquals("Learn Java", savedTask.getTitle());

        verify(taskRepository, times(1)).save(task);
    }

    // Test 3: Get task by ID
    @Test
    void testGetTaskById() {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Spring Boot");

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Learn Spring Boot", result.getTitle());

        verify(taskRepository, times(1)).findById(1L);
    }

    // Test 4: Update a task
    @Test
    void testUpdateTask() {

        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Old Title");
        existingTask.setDescription("Old Description");
        existingTask.setStatus("PENDING");

        Task updatedTask = new Task();
        updatedTask.setTitle("New Title");
        updatedTask.setDescription("New Description");
        updatedTask.setStatus("COMPLETED");

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(existingTask));

        when(taskRepository.save(existingTask))
                .thenReturn(existingTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertEquals("New Title", result.getTitle());
        assertEquals("New Description", result.getDescription());
        assertEquals("COMPLETED", result.getStatus());

        verify(taskRepository, times(1)).save(existingTask);
    }

    // Test 5: Delete a task
    @Test
    void testDeleteTask() {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task to Delete");

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).delete(task);
    }

    // Test 6: Task not found
    @Test
    void testGetTaskByIdNotFound() {

        when(taskRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.getTaskById(99L)
        );

        verify(taskRepository, times(1)).findById(99L);
    }
}
