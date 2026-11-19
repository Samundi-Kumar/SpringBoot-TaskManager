
package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    // Test 1: Save a task
    @Test
    void testSaveTask() {

        Task task = new Task();
        task.setTitle("Learn Java");
        task.setDescription("Practice Java programming");
        task.setStatus("PENDING");

        Task savedTask = taskRepository.save(task);

        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo("Learn Java");
    }

    // Test 2: Find task by ID
    @Test
    void testFindTaskById() {

        Task task = new Task();
        task.setTitle("Learn Spring Boot");
        task.setDescription("Practice Spring Boot");
        task.setStatus("PENDING");

        Task savedTask = taskRepository.save(task);

        Optional<Task> foundTask =
                taskRepository.findById(savedTask.getId());

        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getTitle())
                .isEqualTo("Learn Spring Boot");
    }

    // Test 3: Find all tasks
    @Test
    void testFindAllTasks() {

        Task task1 = new Task();
        task1.setTitle("Learn Java");
        task1.setDescription("Java practice");
        task1.setStatus("PENDING");

        Task task2 = new Task();
        task2.setTitle("Learn Spring");
        task2.setDescription("Spring practice");
        task2.setStatus("COMPLETED");

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findAll();

        assertThat(tasks).hasSize(2);
    }

    // Test 4: Search tasks by title
    @Test
    void testSearchTasksByTitle() {

        Task task = new Task();
        task.setTitle("Learn Spring Boot");
        task.setDescription("Practice Spring");
        task.setStatus("PENDING");

        taskRepository.save(task);

        List<Task> tasks =
                taskRepository.findByTitleContainingIgnoreCase("spring");

        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTitle())
                .isEqualTo("Learn Spring Boot");
    }

    // Test 5: Delete a task
    @Test
    void testDeleteTask() {

        Task task = new Task();
        task.setTitle("Learn SQL");
        task.setDescription("Practice SQL");
        task.setStatus("PENDING");

        Task savedTask = taskRepository.save(task);

        taskRepository.deleteById(savedTask.getId());

        Optional<Task> deletedTask =
                taskRepository.findById(savedTask.getId());

        assertThat(deletedTask).isEmpty();
    }
}
