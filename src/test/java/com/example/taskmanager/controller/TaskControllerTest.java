
package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    // Test 1: GET all tasks
    @Test
    void testGetAllTasks() throws Exception {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Java");
        task.setDescription("Practice Java");
        task.setStatus("PENDING");

        when(taskService.getAllTasks()).thenReturn(List.of(task));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Learn Java"));
    }

    // Test 2: GET task by ID
    @Test
    void testGetTaskById() throws Exception {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Spring Boot");
        task.setDescription("Practice Spring");
        task.setStatus("PENDING");

        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Learn Spring Boot"));
    }

    // Test 3: POST new task
    @Test
    void testAddTask() throws Exception {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Java");
        task.setDescription("Practice Java");
        task.setStatus("PENDING");

        when(taskService.addTask(org.mockito.ArgumentMatchers.any(Task.class)))
                .thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Learn Java",
                                    "description": "Practice Java",
                                    "status": "PENDING"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Learn Java"));
    }

    // Test 4: PUT update task
    @Test
    void testUpdateTask() throws Exception {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Advanced Java");
        task.setDescription("Practice advanced Java");
        task.setStatus("COMPLETED");

        when(taskService.updateTask(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any(Task.class)))
                .thenReturn(task);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Learn Advanced Java",
                                    "description": "Practice advanced Java",
                                    "status": "COMPLETED"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Learn Advanced Java"))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    // Test 5: DELETE task
    @Test
    void testDeleteTask() throws Exception {

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted successfully"));

        verify(taskService).deleteTask(1L);
    }

    // Test 6: GET tasks with pagination
    @Test
    void testGetTasksWithPagination() throws Exception {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Java");
        task.setDescription("Practice Java");
        task.setStatus("PENDING");

        Page<Task> page = new PageImpl<>(List.of(task));

        when(taskService.getTasksWithPagination(0, 5))
                .thenReturn(page);

        mockMvc.perform(get("/tasks/page")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Learn Java"));
    }

    // Test 7: Search tasks by title
    @Test
    void testSearchTasks() throws Exception {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Spring Boot");
        task.setDescription("Practice Spring");
        task.setStatus("PENDING");

        when(taskService.searchTasks("Spring"))
                .thenReturn(List.of(task));

        mockMvc.perform(get("/tasks/search")
                        .param("title", "Spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Learn Spring Boot"));
    }
}
