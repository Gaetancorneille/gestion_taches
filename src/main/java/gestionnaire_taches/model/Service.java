package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Service{

    private int id;
    private String nom;
    private String description;
    private int administratorId;
    private LocalDateTime dateCreation;
    private boolean actif;

    //relations
    private Administrator administrator;
    private List<Employee> employee;
    private List<Task> tasks;

    //Constructeur vide
    public Service(){
        this.dateCreation = LocalDateTime.now();
        this.actif = true;
        this.employees = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    // Constructeur principal
    public Service(String nom, String description, int administratorId){
        this.nom = nom;
        this.description = description;
        this.administratorId = administratorId;
        this.dateCreation = LocalDateTime.now();
        this.actif = true;
        this.employees = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    // Getters & Setters

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNom(){
        return nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getAdministratorId(){
        return administratorId;
    }

    public void setAdministratorId(String administratorId){
        this.administratorId = administratorId;
    }

    public LocalDateTime getDateCreation(){
        return dateCreation;
    }

    public void setDateCreation(String dateCreation){
        this.dateCreation = dateCreation;
    }

    public boolean isActif(){
        return actif;
    }

    public void setActif(boolean actif){
        this.actif = actif;
    }

    public Administrator getAdministrator(){
        return administrator;
    }

    public void setAdministrator(Administrator administrator){
        this.administrator = administrator;
    }

    public List<Employee> getEmployees(){
        return employees;
    }

    public void addEmployee(Employee employee){
        this.employees.add(employee);
    }

    public void removeEmployee(Employee employee){
        this.employees.remove(employee);
    }

    public List<Task> getTasks(){
        return tasks;
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public int getEmployeeCount(){
        return (int) tasks.stream().filter(t -> t.getStatut() != TaskSatuts.TERMINEE).count();
    }

    @Override
    public String toString(){
        return "Service{" + "id=" + id + ", nom='" + nom + '\'' + ", actif=" + actif + '}';
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(!(o instanceof Service)) return false;
        Service service = (Service) o;
        return id == service.id;
    }

    @Override
    public int hashcode(){
        return Objects.hash(id);
    }
}