package gestionnaire_taches.controller;

import gestionnaire_taches.service.TaskService;
import gestionnaire_taches.service.EmployeeService;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.Employee;
import gestionnaire_taches.model.TaskStatus;
import gestionnaire_taches.model.Priority;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskController {
    
    private TaskService taskService;
    private EmployeeService employeeService;
    
    public TaskController() {
        this.taskService = new TaskService();
        this.employeeService = new EmployeeService();
    }
    
    // Task methods
    public boolean createTask(Task task) {
        return taskService.createTask(task);
    }
    
    public boolean updateTask(Task task) {
        return taskService.updateTask(task);
    }
    
    public boolean deleteTask(int id) {
        return taskService.deleteTask(id);
    }
    
    public Task findTaskById(int id) {
        return taskService.findTaskById(id);
    }
    
    public ObservableList<Task> getAllTasks() {
        return FXCollections.observableArrayList(taskService.getAllTasks());
    }
    
    public ObservableList<Task> getTasksByEmployee(int employeeId) {
        return FXCollections.observableArrayList(taskService.getTasksByEmployee(employeeId));
    }
    
    public ObservableList<Task> getTasksByService(int serviceId) {
        return FXCollections.observableArrayList(taskService.getTasksByService(serviceId));
    }
    
    public ObservableList<Task> getTasksByStatus(TaskStatus status) {
        return FXCollections.observableArrayList(taskService.getTasksByStatus(status));
    }
    
    public ObservableList<Task> getTasksByPriority(Priority priority) {
        return FXCollections.observableArrayList(taskService.getTasksByPriority(priority));
    }
    
    public ObservableList<Task> getOverdueTasks() {
        return FXCollections.observableArrayList(taskService.getOverdueTasks());
    }
    
    public ObservableList<Task> getTasksByEmployeeAndStatus(int employeeId, TaskStatus status) {
        return FXCollections.observableArrayList(taskService.getTasksByEmployeeAndStatus(employeeId, status));
    }
    
    public boolean updateTaskStatus(int taskId, TaskStatus newStatus) {
        return taskService.updateTaskStatus(taskId, newStatus);
    }
    
    public double getTaskCompletionPercentage(int taskId) {
        return taskService.getTaskCompletionPercentage(taskId);
    }
    
    public boolean isTaskOverdue(Task task) {
        return taskService.isTaskOverdue(task);
    }
    
    // Employee methods for task assignment
    public Employee findEmployeeById(int id) {
        return employeeService.findEmployeeById(id);
    }
    
    public ObservableList<Employee> getAllEmployees() {
        return FXCollections.observableArrayList(employeeService.getAllEmployees());
    }
    
    public ObservableList<Employee> getEmployeesByService(int serviceId) {
        return FXCollections.observableArrayList(employeeService.getEmployeesByService(serviceId));
    }
    
    public int countTasksByStatus(int serviceId, TaskStatus status) {
        return taskService.countTasksByStatus(serviceId, status);
    }
}