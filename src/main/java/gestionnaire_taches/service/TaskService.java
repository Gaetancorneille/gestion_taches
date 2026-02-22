package gestionnaire_taches.service;

import gestionnaire_taches.dao.interfaces.TaskDAO;
import gestionnaire_taches.dao.impl.TaskDAOImpl;
import gestionnaire_taches.dao.impl.SubtaskDAOImpl;
import gestionnaire_taches.dao.interfaces.SubtaskDAO;
import gestionnaire_taches.model.Task;
import gestionnaire_taches.model.Subtask;
import gestionnaire_taches.model.TaskStatus;
import gestionnaire_taches.model.Priority;
import gestionnaire_taches.util.ValidationUtils;
import gestionnaire_taches.util.DateUtils;

import java.util.List;

public class TaskService {
    
    private TaskDAO taskDAO;
    private SubtaskDAO subtaskDAO;
    
    public TaskService() {
        this.taskDAO = new TaskDAOImpl();
        this.subtaskDAO = new SubtaskDAOImpl();
    }
    
    /**
     * Create a new task
     * @param task the task to create
     * @return true if creation is successful, false otherwise
     */
    public boolean createTask(Task task) {
        if (task == null) {
            System.err.println("La tâche ne peut pas être nulle");
            return false;
        }
        
        if (!ValidationUtils.isNotNullOrEmpty(task.getTitre(), "Task title")) {
            return false;
        }
        
        if (task.getEmployeeId() <= 0) {
            System.err.println("L'ID de l'employé doit être positif");
            return false;
        }
        
        if (task.getServiceId() <= 0) {
            System.err.println("L'ID du service doit être positif");
            return false;
        }
        
        // Set default values if not provided
        if (task.getPriorite() == null) {
            task.setPriorite(Priority.NORMALE);
        }
                
        taskDAO.save(task);
        return true;
    }
    
    /**
     * Update an existing task
     * @param task the task to update
     * @return true if update is successful, false otherwise
     */
    public boolean updateTask(Task task) {
        if (task == null || task.getId() <= 0) {
            System.err.println("L'ID de la tâche doit être positif");
            return false;
        }
        
        if (!ValidationUtils.isNotNullOrEmpty(task.getTitre(), "Titre de la tâche")) {
            return false;
        }
        
        if (task.getEmployeeId() <= 0) {
            System.err.println("L'ID de l'employé doit être positif");
            return false;
        }
        
        if (task.getServiceId() <= 0) {
            System.err.println("L'ID du service doit être positif");
            return false;
        }
        
        taskDAO.update(task);
        return true;
    }
    
    /**
     * Delete a task by ID
     * @param id the ID of the task to delete
     * @return true if deletion is successful, false otherwise
     */
    public boolean deleteTask(int id) {
        if (id <= 0) {
            System.err.println("L'ID de la tâche doit être positif");
            return false;
        }
        
        // First delete all subtasks associated with this task
        List<Subtask> subtasks = subtaskDAO.findByTaskId(id);
        for (Subtask subtask : subtasks) {
            subtaskDAO.delete(subtask.getId());
        }
        
        taskDAO.delete(id);
        return true;
    }
    
    /**
     * Find a task by ID
     * @param id the ID of the task to find
     * @return the task if found, null otherwise
     */
    public Task findTaskById(int id) {
        if (id <= 0) {
            System.err.println("L'ID de la tâche doit être positif");
            return null;
        }
        
        return taskDAO.findById(id).orElse(null);
    }
    
    /**
     * Get all tasks
     * @return a list of all tasks
     */
    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }
    
    /**
     * Get all tasks assigned to a specific employee
     * @param employeeId the ID of the employee
     * @return a list of tasks assigned to the employee
     */
    public List<Task> getTasksByEmployee(int employeeId) {
        if (employeeId <= 0) {
            System.err.println("L'ID de l'employé doit être positif");
            return null;
        }
        
        return taskDAO.findByEmployeeId(employeeId);
    }
    
    /**
     * Get all tasks in a specific service
     * @param serviceId the ID of the service
     * @return a list of tasks in the service
     */
    public List<Task> getTasksByService(int serviceId) {
        if (serviceId <= 0) {
            System.err.println("L'ID du service doit être positif");
            return null;
        }
        
        return taskDAO.findByServiceId(serviceId);
    }
    
    /**
     * Get all tasks with a specific status
     * @param status the status of the tasks to retrieve
     * @return a list of tasks with the specified status
     */
    public List<Task> getTasksByStatus(TaskStatus status) {
        if (status == null) {
            System.err.println("Le statut de la tâche ne peut pas être nul");
            return null;
        }
        
        return taskDAO.findByStatus(status);
    }
    
    /**
     * Get all tasks with a specific priority
     * @param priority the priority of the tasks to retrieve
     * @return a list of tasks with the specified priority
     */
    public List<Task> getTasksByPriority(Priority priority) {
        if (priority == null) {
            System.err.println("La priorité de la tâche ne peut pas être nulle");
            return null;
        }
        
        return taskDAO.findByPriority(priority);
    }
    
    /**
     * Get all overdue tasks
     * @return a list of all overdue tasks
     */
    public List<Task> getOverdueTasks() {
        return taskDAO.findOverdueTasks();
    }
    
    /**
     * Get tasks assigned to an employee with a specific status
     * @param employeeId the ID of the employee
     * @param status the status of the tasks
     * @return a list of tasks matching the criteria
     */
    public List<Task> getTasksByEmployeeAndStatus(int employeeId, TaskStatus status) {
        if (employeeId <= 0) {
            System.err.println("L'ID de l'employé doit être positif");
            return null;
        }
        
        if (status == null) {
            System.err.println("Le statut de la tâche ne peut pas être nul");
            return null;
        }
        
        return taskDAO.findByEmployeeAndStatus(employeeId, status);
    }
    
    /**
     * Count tasks by status in a service
     * @param serviceId the ID of the service
     * @param status the status to count
     * @return the number of tasks with the specified status in the service
     */
    public int countTasksByStatus(int serviceId, TaskStatus status) {
        if (serviceId <= 0) {
            System.err.println("L'ID du service doit être positif");
            return 0;
        }
        
        if (status == null) {
            System.err.println("Le statut de la tâche ne peut pas être nul");
            return 0;
        }
        
        return taskDAO.countTasksByStatus(serviceId, status);
    }
    
    /**
     * Update the status of a task
     * @param taskId the ID of the task
     * @param newStatus the new status
     * @return true if update is successful, false otherwise
     */
    public boolean updateTaskStatus(int taskId, TaskStatus newStatus) {
        Task task = taskDAO.findById(taskId).orElse(null);
        if (task == null) {
            System.err.println("Tâche avec l'ID " + taskId + " non trouvée");
            return false;
        }
        
        task.setStatut(newStatus);
        taskDAO.update(task);
        return true;
    }
    
    /**
     * Check if a task is overdue
     * @param task the task to check
     * @return true if the task is overdue, false otherwise
     */
    public boolean isTaskOverdue(Task task) {
        if (task == null) {
            return false;
        }
        
        return DateUtils.isOverdue(task.getDateLimite());
    }
    
    /**
     * Get the completion percentage of a task
     * @param taskId the ID of the task
     * @return the completion percentage of the task
     */
    public double getTaskCompletionPercentage(int taskId) {
        Task task = taskDAO.findById(taskId).orElse(null);
        if (task == null) {
            return 0.0;
        }
        
        return task.getCompletionPercentage();
    }
}