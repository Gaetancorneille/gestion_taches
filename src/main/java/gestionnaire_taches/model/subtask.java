package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask{

    private int id;
    private String titre;
    private String description;
    private int taskId;
    private int ordre;
    private TaskStatus statut;
    private LocalDateTime dateCreation;
    private Task task;


    //Constructeur vide
    public Subtask(){
        this.dateCreation = LocalDateTime.now();
        this.statut = TaskStatus.A_FAIRE;
    }
     
     //Constructeur principal
     public Subtask(String titre, String description, int taskId){
        this.titre = titre;
        this.description = description;
        this.taskId = taskId;
        this.dateCreation = LocalDateTime.now();
        this.statut = TaskStatus.A_FAIRE;
        this.ordre = 0;
     }

     // Getters et setters
     public int getId(){
        return id;
     }

     public void setId(int id){
        this.id = id;
     }

     public String getTitre(){
        return titre;
     }

     public void setTitre(String titre){
        this.titre = titre;
     }

     public String getDescription(){
        return description;
     }

     public void setDescription(String description){
        this.description = description;
     }

     public int getTaskId(){
        return taskId;
     }

     public void setTaskId(int taskId){
        this.taskId = taskId;
     }

     public int getOrdre(){
        return ordre;
     }

     public void setOrdre(int ordre){
        this.ordre = ordre;
     }

     public TaskStatus getStatut(){
        return statut;
     }

     public void setStatut(TaskStatus statut){
        this.statut = statut;
     }

     public LocalDateTime getDateCreation(){
        return dateCreation;
     }

     public void setDateCreation(LocalDateTime dateCreation){
        this.dateCreation = dateCreation;
     }

     public Task getTask(){
        return task;
     }

     public void setTask(Task task){
        this.task = task;
     }

     //Vérifier si la sous-tâche est terminée
     public boolean isCompleted(){
        return this.statut == TaskStatus.TERMINEE;
     }

     //toString
     @Override
     public String toString(){
        return "Subtask{" + "id=" + id + ", titre='" + titre + '\'' + ", statut=" + statut + ", ordre" + ordre + '}';
     }

     //equals() et hashcode() basés sur id
     @Override
     public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;
        Subtask subtask = (Subtask) o;
        return id == subtask.id;
     }

     @Override
     public int hashcode(){
        return Objects.hash(id);
     }
}