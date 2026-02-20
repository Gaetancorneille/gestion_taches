package gestionnaire_taches.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Task {

    private int id;
    private String titre;
    private String description;
    private TaskStatus statut;
    private LocalDateTime dateCreation;
    private List<subtask> subtasks;

    public Task() {
        this.dateCreation = LocalDateTime.now();
        this.statut = TaskStatus.A_FAIRE;
        this.subtasks = new ArrayList<>();
    }

    public Task(String titre, String description) {
        this.titre = titre;
        this.description = description;
        this.dateCreation = LocalDateTime.now();
        this.statut = TaskStatus.A_FAIRE;
        this.subtasks = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TaskStatus getStatut() { return statut; }
    public void setStatut(TaskStatus statut) { this.statut = statut; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public List<subtask> getSubtasks() { return subtasks; }
    public void setSubtasks(List<subtask> subtasks) { this.subtasks = subtasks; }

    public void addSubtask(subtask s) {
        if (s != null) {
            this.subtasks.add(s);
            s.setTask(this);
        }
    }

    public boolean isCompleted() {
        return this.statut == TaskStatus.TERMINEE;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", statut=" + statut +
                ", subtasks=" + (subtasks == null ? 0 : subtasks.size()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}