
package com.example.taskmanager;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task addTask(Task task) {
        task.setId(tasks.size() + 1);
        tasks.add(task);
        return task;
    }
    public Task getTaskById(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);
    }
    public Task updateTask(int id, Task updatedTask) {
        Task existingTask = getTaskById(id);

        if (existingTask == null) {
            return null;
        }

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());

        return existingTask;
    }
    public boolean deleteTask(int id) {
        Task task = getTaskById(id);

        if (task == null) {
            return false;
        }

        tasks.remove(task);
        return true;
    }


}
