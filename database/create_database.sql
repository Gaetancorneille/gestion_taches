-- Création de la base de données
CREATE DATABASE IF NOT EXISTS gestion_taches CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gestion_taches;

-- Table Administrator
CREATE TABLE Administrator (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    isSuperAdmin BOOLEAN DEFAULT FALSE,
    dateCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    actif BOOLEAN DEFAULT TRUE,
    INDEX idx_email (email),
    INDEX idx_super_admin (isSuperAdmin)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table Service
CREATE TABLE Service (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    description TEXT,
    administrator_id INT NOT NULL,
    dateCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    actif BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_service_administrator 
        FOREIGN KEY (administrator_id) 
        REFERENCES Administrator(id) 
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_administrator (administrator_id),
    INDEX idx_actif (actif)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table Employee
CREATE TABLE Employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    service_id INT NOT NULL,
    poste VARCHAR(100),
    dateEmbauche DATE,
    actif BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_employee_service 
        FOREIGN KEY (service_id) 
        REFERENCES Service(id) 
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_service (service_id),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table Task
CREATE TABLE Task (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titre VARCHAR(200) NOT NULL,
    description TEXT,
    employee_id INT NOT NULL,
    service_id INT NOT NULL,
    dateCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dateLimite DATE,
    priorite ENUM('BASSE', 'NORMALE', 'HAUTE', 'URGENTE') DEFAULT 'NORMALE',
    statut ENUM('A_FAIRE', 'EN_COURS', 'TERMINEE', 'ANNULEE') DEFAULT 'A_FAIRE',
    CONSTRAINT fk_task_employee 
        FOREIGN KEY (employee_id) 
        REFERENCES Employee(id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_task_service 
        FOREIGN KEY (service_id) 
        REFERENCES Service(id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_employee (employee_id),
    INDEX idx_service (service_id),
    INDEX idx_statut (statut),
    INDEX idx_priorite (priorite),
    INDEX idx_date_limite (dateLimite)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table Subtask
CREATE TABLE Subtask (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titre VARCHAR(200) NOT NULL,
    description TEXT,
    task_id INT NOT NULL,
    ordre INT DEFAULT 0,
    statut ENUM('A_FAIRE', 'EN_COURS', 'TERMINEE') DEFAULT 'A_FAIRE',
    dateCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_subtask_task 
        FOREIGN KEY (task_id) 
        REFERENCES Task(id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_task (task_id),
    INDEX idx_ordre (ordre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;