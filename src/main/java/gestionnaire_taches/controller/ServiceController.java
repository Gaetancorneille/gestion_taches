package gestionnaire_taches.controller;

import gestionnaire_taches.service.ServiceManagementService;
import gestionnaire_taches.service.EmployeeService;
import gestionnaire_taches.service.TaskService;
import gestionnaire_taches.model.Service;
import gestionnaire_taches.model.Employee;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.TaskStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServiceController {
    
    private ServiceManagementService serviceService;
    private EmployeeService employeeService;
    private TaskService taskService;
    
    public ServiceController() {
        this.serviceService = new ServiceManagementService();
        this.employeeService = new EmployeeService();
        this.taskService = new TaskService();
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
    
    // Employee methods for service management
    public ObservableList<Employee> getEmployeesByService(int serviceId) {
        return FXCollections.observableArrayList(employeeService.getEmployeesByService(serviceId));
    }
    
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
    
    // Task methods for service management
    public ObservableList<Task> getTasksByService(int serviceId) {
        return FXCollections.observableArrayList(taskService.getTasksByService(serviceId));
    }
    
    public ObservableList<Task> getTasksByServiceAndStatus(int serviceId, TaskStatus status) {
        return FXCollections.observableArrayList(
            taskService.getTasksByService(serviceId).stream()
                .filter(task -> task.getStatut() == status)
                .collect(java.util.stream.Collectors.toList())
        );
    }
    
    public int getEmployeeCountInService(int serviceId) {
        return serviceService.getEmployeeCountInService(serviceId);
    }
    
    public int getActiveTaskCountInService(int serviceId) {
        return serviceService.getActiveTaskCountInService(serviceId);
    }
    
    public boolean activateService(int id) {
        return serviceService.activateService(id);
    }
    
    public boolean deactivateService(int id) {
        return serviceService.deactivateService(id);
    }
}