package com.waterfy.projeto.tasks;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterfy.projeto.enums.TaskStatus;
import com.waterfy.projeto.exception.TaskNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TasksServices {
    
    private TasksRepository tasksRepository;

    public List<Task> getTasksWithParams(TaskStatus status) {
        if (status == null) {
            return tasksRepository.findAll();
        }
        return tasksRepository.findTaskByParameters(status);
    }

    public Task getTaskById(Long id) {
        return tasksRepository.findById(id)
                .stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    public Task saveTask(Task task) {
        return tasksRepository.save(task);
    }

    public int updateTask(Long id, Task task) {
        tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        return tasksRepository.updateTask(
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getFinishedAt(),
                task.getStatus(),
                id);
    }

    public void deleteTask(Long id) {
        tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        tasksRepository.deleteById(id);
    }

    public void deleteAllTasks() {
        tasksRepository.deleteAll();
    }

    public void deleteCompletedTasks() {
        tasksRepository.deleteCompletedTasks();
    }
}
