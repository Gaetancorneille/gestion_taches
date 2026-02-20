import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Task{

    private int id;
    private String titre;
    private String description;
    private int employeeId;
    private int serviceId;
    private LocalDateTime dateCreation;
    private LocalDate dateLimite;
    private Priority priorite;
    private TaskStatus statut;
    private Employee employee;
    private Service service;
    private List subtasks;

    // constructeur par defaut

    public Task(){
        thsis.dateCreation= LocalDateTime.now();
        this.subtask=now ArrayList<>();
    }  

    // constructeur avec les parametres
    public Task(String titre, String description, int employeeId, int serviceId){
        this.titre=titre;
        this.description=description;
        this.employeeId=employeeId;
        this.serviceId=serviceId;
        this.dateCreation=LocalDateTime.now(); // ca permet a ce que la tache ait automatiquemet une date de creation sinon elle sera nulle
        this.subtasks=new ArrayList<>(); // ca permet d'initialiser la liste vide des la creation ie la tache commence avec une liste vide de sous-taches
    }

    // getters and setters
     public int getId(){
         return this.id;
    }
     public void setId(int id){
        this.id=id;
    }
     public String getTitre(){
        return this.titre;
    }
     public void setTitre(String titre){
        this.titre=titre;
    }
     public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description=description;
    }
     public int getEmployeeId(){
         return this.employeeId;
    }
    public void setEmployeeId(int employeeId){
        this.employeeId=employeeId;
    }
     public int getServiceId(){
         return this.serviceId;
    }
     public void setServiceId(int serviceId){
        this.serviceId=serviceId;
    }
     public LocalDateTime getDateCreation(){
        return this.dateCreation;
    }
     public void setDateCreation(LocalDateTime dateCreation){
        this.dateCreation=dateCreation;
    }
     public LocalDate getDateLimite(){
        return this.dateLimite;
    }
     public void setDateLimite( LocalDate dateLimite){
        this.dateLimite=dateLimite;
    }
     public Priority getPriorite(){
        return this.priorite;
    }
     public void setPriorite(Priority priorite){
       this.priorite=priorite;
    }
     public TaskStatus getStatut(){
        return this.statut;
     }
     public void setStatut(TaskStatus statut){
       this.statut=statut;
     }
      public Employee getEmployee(){
        return this.employee;
    }
      public void setEmployee(Employee employee){
        this.employee=employee;
    }
      public Service getService(){
        return this.service;
    }
      public void setService(Service service){
        this.service-service;
    }
      public List getSubtasks(){
        return this.subtasks;
    }
    
    // gestion des sous-taches(ajout et suppression des sous-taches)
    public void addSubtask(Subtask subtask){
        this.subtasks.add(subtask);
    }
    public void removeSubtask(Subtask subtask){
        this.subtasks.remove(subtask);
    }

    // pourcentage de completion
    public double getCompletionPercentage(){
        if(subtasks.isEmpty())
        return 0.0;
        long completed=subtasks.stream().filter(Subtask::isCompleted).count();
        return(completed *100.0)/subtasks.size();
    }

    //
    public boolean isOverdue(){
        if(dateLimite==null)
        return false;
        return LocalDate.now().isAfter(dateLimite)&&statut!= TaskStatus.COMPLETED;
    } 
}     