package gestionnaire_taches.controller;

import gestionnaire_taches.service.TaskService;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.TaskStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeController {
    
    private TaskService taskService;
    
    public EmployeeController() {
        this.taskService = new TaskService();
    }
    
    // Task methods for employees
    public ObservableList<Task> getMyTasks(int employeeId) {
        return FXCollections.observableArrayList(taskService.getTasksByEmployee(employeeId));
    }
    
    public ObservableList<Task> getMyTasksByStatus(int employeeId, TaskStatus status) {
        return FXCollections.observableArrayList(taskService.getTasksByEmployeeAndStatus(employeeId, status));
    }
    
    public boolean updateTaskStatus(int taskId, TaskStatus newStatus) {
        return taskService.updateTaskStatus(taskId, newStatus);
    }
    
    public Task findTaskById(int id) {
        return taskService.findTaskById(id);
    }
    
    public double getTaskCompletionPercentage(int taskId) {
        return taskService.getTaskCompletionPercentage(taskId);
    }
    
    public boolean isTaskOverdue(Task task) {
        return taskService.isTaskOverdue(task);
    }
}