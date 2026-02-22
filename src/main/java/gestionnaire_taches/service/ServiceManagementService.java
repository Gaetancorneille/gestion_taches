package gestionnaire_taches.service;

import gestionnaire_taches.dao.interfaces.ServiceDAO;
import gestionnaire_taches.model.Service;
import gestionnaire_taches.dao.interfaces.SubtaskDAO;
import gestionnaire_taches.dao.impl.SubtaskDAOImpl;
import gestionnaire_taches.dao.impl.ServiceDAOImpl;
import gestionnaire_taches.util.ValidationUtils;

import java.util.List;

public class ServiceManagementService {
    
    private ServiceDAO serviceDAO;
    private SubtaskDAO subtaskDAO;
    
    public ServiceManagementService() {
        this.serviceDAO = new ServiceDAOImpl();
        this.subtaskDAO = new SubtaskDAOImpl(); // Changed from SubtaskDAOImpl to SubtaskDAO
    }
    
    /**
     * Crée un nouveau service
     * @param service le service à créer
     * @return true si la création réussit, false sinon
     */
    public boolean createService(Service service) {
        if (service == null) {
            System.err.println("Le service ne peut pas être nul");
            return false;
        }
        
        if (!ValidationUtils.isNotNullOrEmpty(service.getNom(), "Nom")) {
            return false;
        }
        
        if (service.getAdministratorId() <= 0) {
            System.err.println("L'ID de l'administrateur doit être positif");
            return false;
        }
        
        // Check if service name existe déjà
        List<Service> allServices = serviceDAO.findAll();
        for (Service existingService : allServices) {
            if (existingService.getNom().equalsIgnoreCase(service.getNom())) {
                System.err.println("Un service avec le nom " + service.getNom() + " existe déjà");
                return false;
            }
        }
        
        serviceDAO.save(service);
        return true;
    }
    
    /**
     * Met à jour un service existant
     * @param service le service à mettre à jour
     * @return true si la mise à jour réussit, false sinon
     */
    public boolean updateService(Service service) {
        if (service == null || service.getId() <= 0) {
            System.err.println("L'ID du service doit être positif");
            return false;
        }
        
        if (!ValidationUtils.isNotNullOrEmpty(service.getNom(), "Nom")) {
            return false;
        }
        
        if (service.getAdministratorId() <= 0) {
            System.err.println("L'ID de l'administrateur doit être positif");
            return false;
        }
        
        serviceDAO.update(service);
        return true;
    }
    
    /**
     * Supprime un service par son ID
     * @param id l'ID du service à supprimer
     * @return true si la suppression réussit, false sinon
     */
    public boolean deleteService(int id) {
        if (id <= 0) {
            System.err.println("L'ID du service doit être positif");
            return false;
        }
        
        // Check if service has employés assignés
        int employeeCount = serviceDAO.countEmployeesInService(id);
        if (employeeCount > 0) {
            System.err.println("Impossible de supprimer le service avec " + employeeCount + " employés assignés");
            return false;
        }
        
        serviceDAO.delete(id);
        return true;
    }
    
    /**
     * Trouve un service par son ID
     * @param id l'ID du service à rechercher
     * @return le service s'il est trouvé, null sinon
     */
    public Service findServiceById(int id) {
        if (id <= 0) {
            System.err.println("L'ID du service doit être positif");
            return null;
        }
        
        return serviceDAO.findById(id).orElse(null);
    }
    
    /**
     * Récupère tous les services
     * @return une liste de tous les services
     */
    public List<Service> getAllServices() {
        return serviceDAO.findAll();
    }
    
    /**
     * Récupère tous les services pour un administrateur spécifique
     * @param administratorId l'ID de l'administrateur
     * @return une liste des services gérés par l'administrateur
     */
    public List<Service> getServicesByAdministrator(int administratorId) {
        if (administratorId <= 0) {
            System.err.println("L'ID de l'administrateur doit être positif");
            return null;
        }
        
        return serviceDAO.findByAdministratorId(administratorId);
    }
    
    /**
     * Récupère tous les services pour un administrateur spécifique par nom
     * @param administratorName le nom de l'administrateur
     * @return une liste des services gérés par l'administrateur
     */
    public List<Service> getServicesByAdministratorName(String administratorName) {
        if (!ValidationUtils.isNotNullOrEmpty(administratorName, "Nom de l'administrateur")) {
            return null;
        }
        
        return serviceDAO.findByAdministratorName(administratorName);
    }
    
    /**
     * Compte le nombre d'employés dans un service
     * @param serviceId l'ID du service
     * @return le nombre d'employés dans le service
     */
    public int getEmployeeCountInService(int serviceId) {
        if (serviceId <= 0) {
            System.err.println("L'ID du service doit être positif");
            return 0;
        }
        
        return serviceDAO.countEmployeesInService(serviceId);
    }
    
    /**
     * Compte le nombre de tâches actives dans un service
     * @param serviceId l'ID du service
     * @return le nombre de tâches actives dans le service
     */
    public int getActiveTaskCountInService(int serviceId) {
        if (serviceId <= 0) {
            System.err.println("L'ID du service doit être positif");
            return 0;
        }
        
        return serviceDAO.countActiveTasksInService(serviceId);
    }
    
    /**
     * Active un service
     * @param id l'ID du service à activer
     * @return true si l'activation réussit, false sinon
     */
    public boolean activateService(int id) {
        Service service = serviceDAO.findById(id).orElse(null);
        if (service == null) {
            System.err.println("Service avec l'ID " + id + " non trouvé");
            return false;
        }
        
        service.setActif(true);
        serviceDAO.update(service);
        return true;
    }
    
    /**
     * Désactive un service
     * @param id l'ID du service à désactiver
     * @return true si la désactivation réussit, false sinon
     */
    public boolean deactivateService(int id) {
        if (id <= 0) {
            System.err.println("L'ID du service doit être positif");
            return false;
        }
        
        // Check if service has active employees
        int employeeCount = serviceDAO.countEmployeesInService(id);
        if (employeeCount > 0) {
            System.err.println("Impossible de désactiver le service avec des employés actifs");
            return false;
        }
        
        Service service = serviceDAO.findById(id).orElse(null);
        if (service == null) {
            return false;
        }
        service.setActif(false);
        serviceDAO.update(service);
        return true;
    }
}