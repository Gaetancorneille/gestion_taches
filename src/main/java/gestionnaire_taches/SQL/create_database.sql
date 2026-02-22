#1.Création de la base de données
CREATE DATABASE IF NOT EXISTS gestion_taches; #création de la base à condition qu'elle n'existe pas déja pour eviter les erreurs
USE gestion_taches; #sélection de la base pour que les prochaines tables y soient créées

#Désactivation temporaire des vérifications de clés étrangères pour permettre la suppression
SET FOREIGN_KEY_CHECKS = 0;
#Suppression des tables dans ordre inverse de la hiérarchie
DROP TABLE IF EXISTS Subtask;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Service;
DROP TABLE IF EXISTS Administrator;
#Réactivation des vérifiation des clés étrangères
SET FOREIGN_KEY_CHECKS = 1;

#2.Création de la table Administrator (Niveau1: indépendante)
CREATE TABLE IF NOT EXISTS Administrator (
    id_admin INT PRIMARY KEY AUTO_INCREMENT,
    nom_admin VARCHAR(100) NOT NULL,
    email_admin VARCHAR(100) UNIQUE NOT NULL,
    password_admin VARCHAR(255) NOT NULL,
    isSuperAdmin BOOLEAN DEFAULT FALSE,
    dateCreation_admin TIMESTAMP DEFAULT CURRENT_TIMESTAMP, #enregistre automatiquement les date et heure de création 
    actif_admin BOOLEAN DEFAULT TRUE
  );

#3.Création de la table Service (Niveau 2:dépend de Administrator)
CREATE TABLE IF NOT EXISTS Service (
    id_service INT PRIMARY KEY AUTO_INCREMENT,
    nom_service VARCHAR(100) NOT NULL,
    description_service TEXT,
    administrator_id INT NULL, 
    dateCreation_service TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    actif_service BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_admin FOREIGN KEY (administrator_id) REFERENCES Administrator(id_admin) ON DELETE SET NULL
);

#4.Création de la table Employee (Niveau3:dépend de Service)
CREATE TABLE IF NOT EXISTS Employee(
    id_employee INT PRIMARY KEY AUTO_INCREMENT,
    nom_employee VARCHAR(100) NOT NULL,
    email_employee VARCHAR(100) UNIQUE NOT NULL,
    password_employee VARCHAR(255) NOT NULL,
    service_id INT NULL, #cette valeur devient null lorsque le service de cet employé a été supprimé
    poste_employee VARCHAR(100),
    dateEmbauche DATE,
    actif_employee BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_service FOREIGN KEY(service_id) REFERENCES Service(id_service) ON DELETE SET NULL #la suppression du service ne supprime pas ces employés
);

#5.Création de la table Task (Niveau4: dépend de Employé et du service)
CREATE TABLE IF NOT EXISTS Task(
    id_task INT PRIMARY KEY AUTO_INCREMENT,
    titre_task VARCHAR(200) NOT NULL,
    description_task TEXT,
    service_task_id INT NOT NULL,
    employee_id INT NULL,
    dateCreation_task TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dateLimite_task DATE,
    priorite_task ENUM('BASSE','NORMALE','HAUTE','URGENTE'),
    statut_task ENUM('A_FAIRE','EN_COURS','TERMINEE','ANNULEE'),
    CONSTRAINT fk_service_task FOREIGN KEY(service_task_id) REFERENCES Service(id_service) ON DELETE CASCADE,
    CONSTRAINT fk_employee FOREIGN KEY(employee_id) REFERENCES Employee(id_employee) ON DELETE SET NULL
);

#6.Creation de la table Subtask (Niveau 5: dépend de Task)
CREATE TABLE IF NOT EXISTS Subtask(
    id_subtask INT PRIMARY KEY AUTO_INCREMENT,
    titre_subtask VARCHAR(200) NOT NULL,
    description_subtask TEXT,
    task_id INT NOT NULL,
    ordre_subtask INT DEFAULT 0,
    statut_subtask ENUM('A_FAIRE','EN_COURS','TERMINEE','ANNULEE'),
    dateCreation_subtask TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dateLimite_subtask DATE,
    CONSTRAINT fk_task FOREIGN KEY(task_id) REFERENCES Task(id_task) ON DELETE CASCADE #propagation de la suppression de la tache sur ses sous-taches
);

#creation des indices sur les colonnes frequemment recherchées pour optimiser les recherchées
#indice sur email de employee(utile pour la connexion)
CREATE INDEX idx_employee_email ON Employee(email_employee);

#indice sur le statut de Task(pour les filtres du tableau de bord )
CREATE INDEX idx_task_statut ON Task(statut_task);

#indice sur la deadline de Task (pour gerer plus facilement les taches urgentes)
CREATE INDEX idx_task_deadline ON Task(dateLimite_task);
