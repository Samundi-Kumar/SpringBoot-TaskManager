
package com.example.taskmanager;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable int id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable int id) {
        boolean deleted = taskService.deleteTask(id);

        if (!deleted) {
            return "Task not found";
        }

        return "Task deleted successfully";
    }
}
