package gestionnaire_taches.service;

import gestionnaire_taches.dao.interfaces.AdministratorDAO;
import gestionnaire_taches.dao.impl.AdministratorDAOImpl;
import gestionnaire_taches.model.Administrator;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.dao.interfaces.SubtaskDAO;
import gestionnaire_taches.util.ValidationUtils;
import java.util.List;

public class AdministratorService {
    
    private AdministratorDAO adminDAO;

    
    public AdministratorService() {
        this.adminDAO = new AdministratorDAOImpl();
    }
    
    /**
     * Crée un nouvel administrateur
     * @param admin l'administrateur à créer
     * @return true si la création réussit, false sinon
     */
    public boolean createAdministrator(Administrator admin) {
        if (admin == null) {
            System.err.println("L'administrateur ne peut pas être nul");
            return false;
        }
        
        if (!ValidationUtils.isNotNullOrEmpty(admin.getNom(), "Name")) {
            return false;
        }
        
        if (!ValidationUtils.isValidEmail(admin.getEmail())) {
            System.err.println("Format d'email invalide : " + admin.getEmail());
            return false;
        }
        
        // Check if email existe déjà
        if (adminDAO.findByEmail(admin.getEmail()).isPresent()) {
            System.err.println("Un administrateur avec l'email " + admin.getEmail() + " existe déjà");
            return false;
        }
        
        adminDAO.save(admin);
                return true;
    }
    
    /**
     * Met à jour un administrateur existant
     * @param admin l'administrateur à mettre à jour
     * @return true si la mise à jour réussit, false sinon
     */
    public boolean updateAdministrator(Administrator admin) {
        if (admin == null || admin.getId() <= 0) {
            System.err.println("L'ID de l'administrateur doit être positif");
            return false;
        }
        
        if (!ValidationUtils.isNotNullOrEmpty(admin.getNom(), "Name")) {
            return false;
        }
        
        if (!ValidationUtils.isValidEmail(admin.getEmail())) {
            System.err.println("Format d'email invalide : " + admin.getEmail());
            return false;
        }
        
        adminDAO.update(admin);
        return true;
    }
    
    /**
     * Supprime un administrateur par son ID
     * @param id l'ID de l'administrateur à supprimer
     * @return true si la suppression réussit, false sinon
     */
    public boolean deleteAdministrator(int id) {
        if (id <= 0) {
            System.err.println("L'ID de l'administrateur doit être positif");
            return false;
        }
        
        // Cannot delete the last administrator if it's a super admin
        List<Administrator> allAdmins = adminDAO.findAll();
        if (allAdmins.size() == 1 && allAdmins.get(0).getId() == id && allAdmins.get(0).isSuperAdmin()) {
            System.err.println("Impossible de supprimer le dernier super administrateur");
            return false;
        }
        
        adminDAO.delete(id);
                return true;
    }
    
    /**
     * Trouve un administrateur par son ID
     * @param id l'ID de l'administrateur à rechercher
     * @return l'administrateur s'il est trouvé, null sinon
     */
    public Administrator findAdministratorById(int id) {
        if (id <= 0) {
            System.err.println("L'ID de l'administrateur doit être positif");
            return null;
        }
        
        return adminDAO.findById(id).orElse(null);
    }
    
    /**
     * Trouve un administrateur par email
     * @param email l'email de l'administrateur à rechercher
     * @return l'administrateur s'il est trouvé, null sinon
     */
    public Administrator findAdministratorByEmail(String email) {
        if (!ValidationUtils.isValidEmail(email)) {
            System.err.println("Format d'email invalide : " + email);
            return null;
        }
        
        return adminDAO.findByEmail(email).orElse(null);
    }
    
    /**
     * Récupère tous les administrateurs
     * @return une liste de tous les administrateurs
     */
    public List<Administrator> getAllAdministrators() {
        return adminDAO.findAll();
    }
    
    /**
     * Promeut un administrateur au rang de super admin
     * @param id l'ID de l'administrateur à promouvoir
     * @return true si la promotion réussit, false sinon
     */
    public boolean promoteToSuperAdmin(int id) {
        Administrator admin = adminDAO.findById(id).orElse(null);
        if (admin == null) {
            System.err.println("Administrateur avec l'ID " + id + " non trouvé");
            return false;
        }
        
        admin.setSuperAdmin(true);
        adminDAO.update(admin);
        return true;
    }
    
    /**
     * Rétrograde un super admin au rang d'administrateur normal
     * @param id l'ID de l'administrateur à rétrograder
     * @return true si la rétrogradation réussit, false sinon
     */
    public boolean demoteFromSuperAdmin(int id) {
        Administrator admin = adminDAO.findById(id).orElse(null);
        if (admin == null) {
            System.err.println("Administrateur avec l'ID " + id + " non trouvé");
            return false;
        }
        
        // Cannot demote if this is the only super admin
        List<Administrator> superAdmins = getSuperAdministrators();
        if (superAdmins.size() == 1 && superAdmins.get(0).getId() == id) {
            System.err.println("Impossible de rétrograder le seul super administrateur");
            return false;
        }
        
        admin.setSuperAdmin(false);
        adminDAO.update(admin);
        return true;
    }
    
    /**
     * Récupère tous les super administrateurs
     * @return une liste de tous les super administrateurs
     */
    public List<Administrator> getSuperAdministrators() {
        return adminDAO.findAll().stream()
                .filter(Administrator::isSuperAdmin)
                .collect(java.util.stream.Collectors.toList());
    }
}