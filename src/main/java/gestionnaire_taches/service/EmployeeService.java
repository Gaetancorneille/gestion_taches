package gestionnaire_taches.service;

import gestionnaire_taches.dao.interfaces.EmployeeDAO;
import gestionnaire_taches.model.Employee;
import gestionnaire_taches.dao.impl.EmployeeDAOImpl;
import gestionnaire_taches.util.ValidationUtils;

import java.util.List;

public class EmployeeService {
    
    private EmployeeDAO employeeDAO;
    
    public EmployeeService() {
        this.employeeDAO = new EmployeeDAOImpl();
    }
    
    /**
     * Create a new employee
     * @param employee the employee to create
     * @return true if creation is successful, false otherwise
     */
    public boolean createEmployee(Employee employee) {
        if (employee == null) {
            System.err.println("L'employé ne peut pas être nul");
            return false;
        }
        
        if (!ValidationUtils.isNotNullOrEmpty(employee.getNom(), "Name")) {
            return false;
        }
        
        if (!ValidationUtils.isValidEmail(employee.getEmail())) {
            System.err.println("Format d'email invalide : " + employee.getEmail());
            return false;
        }
        
        if (employee.getServiceId() <= 0) {
            System.err.println("L'ID du service doit être positif");
            return false;
        }
        
        // Check if email existe déjà
        if (employeeDAO.findByEmail(employee.getEmail()) != null) {
            System.err.println("Un employé avec l'email " + employee.getEmail() + " existe déjà");
            return false;
        }
        
        employeeDAO.save(employee);
        return true;
    }
    
    /**
     * Update an existing employee
     * @param employee the employee to update
     * @return true if update is successful, false otherwise
     */
    public boolean updateEmployee(Employee employee) {
        if (employee == null || employee.getId() <= 0) {
            System.err.println("L'ID de l'employé doit être positif");
            return false;
        }
        
        if (!ValidationUtils.isNotNullOrEmpty(employee.getNom(), "Name")) {
            return false;
        }
        
        if (!ValidationUtils.isValidEmail(employee.getEmail())) {
            System.err.println("Format d'email invalide : " + employee.getEmail());
            return false;
        }
        
        if (employee.getServiceId() <= 0) {
            System.err.println("L'ID du service doit être positif");
            return false;
        }
        
        employeeDAO.update(employee);
        return true;
    }
    
    /**
     * Delete an employee by ID
     * @param id the ID of the employee to delete
     * @return true if deletion is successful, false otherwise
     */
    public boolean deleteEmployee(int id) {
        if (id <= 0) {
            System.err.println("L'ID de l'employé doit être positif");
            return false;
        }
        
        employeeDAO.delete(id);
        return true;
    }
    
    /**
     * Find an employee by ID
     * @param id the ID of the employee to find
     * @return the employee if found, null otherwise
     */
    public Employee findEmployeeById(int id) {
        if (id <= 0) {
            System.err.println("L'ID de l'employé doit être positif");
            return null;
        }
        
        return employeeDAO.findById(id).orElse(null);
    }
    
    /**
     * Find an employee by email
     * @param email the email of the employee to find
     * @return the employee if found, null otherwise
        */
        public Employee findEmployeeByEmail(String email) {
            if (!ValidationUtils.isValidEmail(email)) {
                System.err.println("Format d'email invalide : " + email);
                return null;
            }
            
            return employeeDAO.findByEmail(email).orElse(null);
        }
    
    /**
     * Get all employees
     * @return a list of all employees
     */
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }
    
    /**
     * Get all employees in a specific service
     * @param serviceId the ID of the service
     * @return a list of employees in the service
     */
    public List<Employee> getEmployeesByService(int serviceId) {
        if (serviceId <= 0) {
            System.err.println("L'ID du service doit être positif");
            return null;
        }
        
        return employeeDAO.findByServiceId(serviceId);
    }
    
    /**
     * Get all employees in a service by name
     * @param serviceName the name of the service
     * @return a list of employees in the service
     */
    public List<Employee> getEmployeesByServiceName(String serviceName) {
        if (!ValidationUtils.isNotNullOrEmpty(serviceName, "Service name")) {
            return null;
        }
        
        return employeeDAO.findByServiceName(serviceName);
    }
    
    /**
     * Activate an employee
     * @param id the ID of the employee to activate
     * @return true if activation is successful, false otherwise
     */
    public boolean activateEmployee(int id) {
        Employee employee = employeeDAO.findById(id).orElse(null);
        if (employee == null) {
            System.err.println("Employé avec l'ID " + id + " non trouvé");
            return false;
        }
        
        employee.setActif(true);
        employeeDAO.update(employee);
        return true;
    }
    
    /**
     * Deactivate an employee
     * @param id the ID of the employee to deactivate
     * @return true if deactivation is successful, false otherwise
     */
    public boolean deactivateEmployee(int id) {
        Employee employee = employeeDAO.findById(id).orElse(null);
        if (employee == null) {
            System.err.println("Employé avec l'ID " + id + " non trouvé");
            return false;
        }
        
        employee.setActif(false);
        employeeDAO.update(employee);
        return true;
    }
    
    /**
     * Change an employee's service
     * @param employeeId the ID of the employee
     * @param newServiceId the ID of the new service
     * @return true if the service change is successful, false otherwise
     */
    public boolean changeEmployeeService(int employeeId, int newServiceId) {
        Employee employee = employeeDAO.findById(employeeId).orElse(null);
        if (employee == null) {
            System.err.println("Employé avec l'ID " + employeeId + " non trouvé");
            return false;
        }
        
        if (newServiceId <= 0) {
            System.err.println("Le nouvel ID de service doit être positif");
            return false;
        }
        
        employee.setServiceId(newServiceId);
        employeeDAO.update(employee);
        return true;
    }
}