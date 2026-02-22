USE gestion_taches;

-- Insertion de données de test pour les administrateurs
INSERT INTO Administrator (nom, email, password, isSuperAdmin) VALUES
('Admin Principal', 'admin@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', TRUE), -- Mot de passe: admin123
('Jean Dupont', 'jean.dupont@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', FALSE), -- Mot de passe: admin123
('Marie Martin', 'marie.martin@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', FALSE); -- Mot de passe: admin123

-- Insertion de données de test pour les services
INSERT INTO Service (nom, description, administrator_id) VALUES
('Ressources Humaines', 'Gestion du personnel et recrutement', 1),
('Informatique', 'Développement et support IT', 2),
('Finance', 'Comptabilité et gestion', 1),
('Marketing', 'Communication et stratégie', 2),
('Production', 'Fabrication et logistique', 3);

-- Insertion de données de test pour les employés
INSERT INTO Employee (nom, email, password, service_id, poste, dateEmbauche) VALUES
('Sophie Laurent', 'sophie.laurent@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', 1, 'Chef RH', '2020-03-15'), -- Mot de passe: marie123
('Thomas Petit', 'thomas.petit@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', 2, 'Développeur Senior', '2019-06-10'),
('Emma Dubois', 'emma.dubois@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', 2, 'Développeuse Junior', '2021-09-05'),
('Luc Bernard', 'luc.bernard@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', 3, 'Comptable', '2020-01-20'),
('Julie Moreau', 'julie.moreau@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', 4, 'Responsable Marketing', '2018-09-05'),
('Paul Leroy', 'paul.leroy@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', 5, 'Responsable Production', '2021-02-15');

-- Insertion de données de test pour les tâches
INSERT INTO Task (titre, description, employee_id, service_id, dateLimite, priorite, statut) VALUES
('Recrutement Développeur', 'Rechercher un développeur Java expérimenté', 2, 2, '2023-12-15', 'HAUTE', 'EN_COURS'),
('Mise à jour site web', 'Mettre à jour la page d\'accueil du site', 3, 2, '2023-12-10', 'NORMALE', 'A_FAIRE'),
('Préparation bilan annuel', 'Préparer le bilan financier de l\'année', 4, 3, '2023-12-20', 'URGENTE', 'EN_COURS'),
('Campagne email marketing', 'Lancer la campagne email de fin d\'année', 5, 4, '2023-12-05', 'HAUTE', 'TERMINEE'),
('Audit sécurité', 'Effectuer un audit de sécurité informatique', 2, 2, '2023-12-25', 'URGENTE', 'A_FAIRE'),
('Recrutement Commercial', 'Rechercher un commercial pour l\'équipe', 1, 1, '2023-12-30', 'NORMALE', 'A_FAIRE'),
('Amélioration processus production', 'Optimiser les processus de fabrication', 6, 5, '2023-12-18', 'HAUTE', 'EN_COURS');

-- Insertion de données de test pour les sous-tâches
INSERT INTO Subtask (titre, description, task_id, ordre, statut) VALUES
('Recherche candidats', 'Identifier les profils correspondants', 1, 1, 'TERMINEE'),
('Publication annonce', 'Publier l\annonce sur les plateformes', 1, 2, 'TERMINEE'),
('Entretiens préliminaires', 'Effectuer les entretiens initiaux', 1, 3, 'EN_COURS'),
('Entretien final', 'Finaliser les entretiens', 1, 4, 'A_FAIRE'),
('Analyse du code existant', 'Analyser le code du site actuel', 2, 1, 'A_FAIRE'),
('Conception maquettes', 'Créer les nouvelles maquettes', 2, 2, 'A_FAIRE'),
('Validation budget', 'Valider le budget avec la direction', 3, 1, 'TERMINEE'),
('Collecte données', 'Rassembler les données financières', 3, 2, 'EN_COURS'),
('Rédaction rapport', 'Rédiger le rapport final', 3, 3, 'A_FAIRE');