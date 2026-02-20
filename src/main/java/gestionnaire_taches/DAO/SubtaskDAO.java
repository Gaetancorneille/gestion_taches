package DAO;

import model.subtask;
import java.util.List;

public interface SubtaskDAO{

    //Créer une sous-tâche
    void create(Subtask subtask);

    //Trouver une sous-tâche par son id
    Substask findById(int id);

    //Obtenir toutes les sous-tâches d'une tâche
    List<Substask> getSubtasksByTask(int taskId);

    //Mettre à jour une sous-tâche
    void update(Subtask subtask);

    //Supprimer une sous-tâche
    void delete(int id);
}