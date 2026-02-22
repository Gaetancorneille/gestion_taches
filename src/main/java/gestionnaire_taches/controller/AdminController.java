package gestionnaire_taches.controller;

import gestionnaire_taches.service.*;
import gestionnaire_taches.model.*;
import gestionnaire_taches.dao.interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminController {
    
    private AdministratorService adminService;
    private ServiceManagementService serviceService;
    private EmployeeService employeeService;
    private TaskService taskService;
    
    public AdminController() {
        this.adminService = new AdministratorService();
        this.serviceService = new ServiceManagementService();
        this.employeeService = new EmployeeService();
        this.taskService = new TaskService();
    }
    
    // Administrator methods
    public boolean createAdministrator(Administrator admin) {
        return adminService.createAdministrator(admin);
    }
    
    public boolean updateAdministrator(Administrator admin) {
        return adminService.updateAdministrator(admin);
    }
    
    public boolean deleteAdministrator(int id) {
        return adminService.deleteAdministrator(id);
    }
    
    public Administrator findAdministratorById(int id) {
        return adminService.findAdministratorById(id);
    }
    
    public ObservableList<Administrator> getAllAdministrators() {
        return FXCollections.observableArrayList(adminService.getAllAdministrators());
    }
    
    // Service methods
    public boolean createService(Service service) {
        return serviceService.createService(service);
    }
    
    public boolean updateService(Service service) {
        return serviceService.updateService(service);
    }
    
    public boolean deleteService(int id) {
        return serviceService.deleteService(id);
    }
    
    public Service findServiceById(int id) {
        return serviceService.findServiceById(id);
    }
    
    public ObservableList<Service> getAllServices() {
        return FXCollections.observableArrayList(serviceService.getAllServices());
    }
    
    public ObservableList<Service> getServicesByAdministrator(int adminId) {
        return FXCollections.observableArrayList(serviceService.getServicesByAdministrator(adminId));
    }
    
    // Employee methods
    public boolean createEmployee(Employee employee) {
        return employeeService.createEmployee(employee);
    }
    
    public boolean updateEmployee(Employee employee) {
        return employeeService.updateEmployee(employee);
    }
    
    public boolean deleteEmployee(int id) {
        return employeeService.deleteEmployee(id);
    }
    
    public Employee findEmployeeById(int id) {
        return employeeService.findEmployeeById(id);
    }
    
    public ObservableList<Employee> getAllEmployees() {
        return FXCollections.observableArrayList(employeeService.getAllEmployees());
    }
    
    public ObservableList<Employee> getEmployeesByService(int serviceId) {
        return FXCollections.observableArrayList(employeeService.getEmployeesByService(serviceId));
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
    
    public ObservableList<Task> getOverdueTasks() {
        return FXCollections.observableArrayList(taskService.getOverdueTasks());
    }
}