-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Lun 23 Février 2026 à 06:42
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `gestion_taches`
--

-- --------------------------------------------------------

--
-- Structure de la table `administrator`
--

CREATE TABLE IF NOT EXISTS `administrator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `isSuperAdmin` tinyint(1) DEFAULT '0',
  `dateCreation` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `actif` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `idx_email` (`email`),
  KEY `idx_super_admin` (`isSuperAdmin`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=24 ;

--
-- Contenu de la table `administrator`
--

INSERT INTO `administrator` (`id`, `nom`, `email`, `password`, `isSuperAdmin`, `dateCreation`, `actif`) VALUES
(1, 'Admin Principal', 'admin@company.com', '$2y$10$zM6mrdaFD4A6Go0n9j0KzO50daO.6HHoQSi4FH1BjWqX13g7ImuUW', 1, '2026-02-22 13:04:12', 1),
(2, 'Jean Dupont', 'jean.dupont@company.com', '$2y$10$xg.m2tORGtBBjqE/1gNck.Njt0qtF.KXLmskO2Dlj0uaEzrlVz8qm', 0, '2026-02-22 13:04:12', 1),
(3, 'Marie Martin', 'marie.martin@company.com', '$2y$10$1CXweGfU5AwOQpm.WI9IWe5RuNLVbjV4dsufO8W.jmqlp9OTR.js2', 0, '2026-02-22 13:04:12', 1),
(22, 'gaetan', 'gaetan@company.com', '$2a$10$UgNNd4cLBpQkRmkLetMsg.6f6GXiu0qFzrHFkC1Jb8.BzHdNhpnMO', 1, '2026-02-22 20:31:27', 1),
(23, 'ABESSOLO Sabrina', 'sabrina@company.com', '$2a$10$DSv.EGANp9yIxwr.I.V7WO4rvqgglMpZr/44NK3knNv88IyWDKhr2', 0, '2026-02-22 23:33:25', 1);

-- --------------------------------------------------------

--
-- Structure de la table `employee`
--

CREATE TABLE IF NOT EXISTS `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `service_id` int(11) NOT NULL,
  `poste` varchar(100) DEFAULT NULL,
  `dateEmbauche` date DEFAULT NULL,
  `actif` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `idx_service` (`service_id`),
  KEY `idx_email` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=8 ;

--
-- Contenu de la table `employee`
--

INSERT INTO `employee` (`id`, `nom`, `email`, `password`, `service_id`, `poste`, `dateEmbauche`, `actif`) VALUES
(1, 'Sophie Laurent', 'sophie.laurent@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', 1, 'Chef RH', '2020-03-15', 1),
(2, 'Thomas Petit', 'thomas.petit@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', 2, 'Développeur Senior', '2019-06-10', 1),
(3, 'Emma Dubois', 'emma.dubois@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', 2, 'Développeuse Junior', '2021-09-05', 1),
(4, 'Luc Bernard', 'luc.bernard@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', 3, 'Comptable', '2020-01-20', 1),
(5, 'Julie Moreau', 'julie.moreau@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', 4, 'Responsable Marketing', '2018-09-05', 1),
(6, 'Paul Leroy', 'paul.leroy@company.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjdQcVrPdvmadY1r2.hz./.UqHzrWS', 5, 'Responsable Production', '2021-02-15', 1),
(7, 'Tientcheu Christian', 'christian@company.com', '$2a$10$Co8qw0o7YyAsneAkpHd4p.hohiYIITsyJFKU7TKd4U28ATwEleRbe', 2, 'Analyste', '2026-02-23', 1);

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

CREATE TABLE IF NOT EXISTS `service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `description` text,
  `administrator_id` int(11) NOT NULL,
  `dateCreation` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `actif` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `idx_administrator` (`administrator_id`),
  KEY `idx_actif` (`actif`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `service`
--

INSERT INTO `service` (`id`, `nom`, `description`, `administrator_id`, `dateCreation`, `actif`) VALUES
(1, 'Ressources Humaines', 'Gestion du personnel et recrutement', 1, '2026-02-22 13:04:12', 1),
(2, 'Informatique', 'Développement et support IT', 2, '2026-02-22 13:04:12', 1),
(3, 'Finance', 'Comptabilité et gestion', 1, '2026-02-22 13:04:12', 1),
(4, 'Marketing', 'Communication et stratégie', 2, '2026-02-22 13:04:12', 1),
(5, 'Production', 'Fabrication et logistique', 3, '2026-02-22 13:04:12', 1);

-- --------------------------------------------------------

--
-- Structure de la table `subtask`
--

CREATE TABLE IF NOT EXISTS `subtask` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titre` varchar(200) NOT NULL,
  `description` text,
  `task_id` int(11) NOT NULL,
  `ordre` int(11) DEFAULT '0',
  `statut` enum('A_FAIRE','EN_COURS','TERMINEE') DEFAULT 'A_FAIRE',
  `dateCreation` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_task` (`task_id`),
  KEY `idx_ordre` (`ordre`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=10 ;

--
-- Contenu de la table `subtask`
--

INSERT INTO `subtask` (`id`, `titre`, `description`, `task_id`, `ordre`, `statut`, `dateCreation`) VALUES
(1, 'Recherche candidats', 'Identifier les profils correspondants', 1, 1, 'TERMINEE', '2026-02-22 13:04:12'),
(2, 'Publication annonce', 'Publier lannonce sur les plateformes', 1, 2, 'TERMINEE', '2026-02-22 13:04:12'),
(3, 'Entretiens préliminaires', 'Effectuer les entretiens initiaux', 1, 3, 'EN_COURS', '2026-02-22 13:04:12'),
(4, 'Entretien final', 'Finaliser les entretiens', 1, 4, 'A_FAIRE', '2026-02-22 13:04:12'),
(5, 'Analyse du code existant', 'Analyser le code du site actuel', 2, 1, 'A_FAIRE', '2026-02-22 13:04:12'),
(6, 'Conception maquettes', 'Créer les nouvelles maquettes', 2, 2, 'A_FAIRE', '2026-02-22 13:04:12'),
(7, 'Validation budget', 'Valider le budget avec la direction', 3, 1, 'TERMINEE', '2026-02-22 13:04:12'),
(8, 'Collecte données', 'Rassembler les données financières', 3, 2, 'EN_COURS', '2026-02-22 13:04:12'),
(9, 'Rédaction rapport', 'Rédiger le rapport final', 3, 3, 'A_FAIRE', '2026-02-22 13:04:12');

-- --------------------------------------------------------

--
-- Structure de la table `task`
--

CREATE TABLE IF NOT EXISTS `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titre` varchar(200) NOT NULL,
  `description` text,
  `employee_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `dateCreation` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `dateLimite` date DEFAULT NULL,
  `priorite` enum('BASSE','NORMALE','HAUTE','URGENTE') DEFAULT 'NORMALE',
  `statut` enum('A_FAIRE','EN_COURS','TERMINEE','ANNULEE') DEFAULT 'A_FAIRE',
  PRIMARY KEY (`id`),
  KEY `idx_employee` (`employee_id`),
  KEY `idx_service` (`service_id`),
  KEY `idx_statut` (`statut`),
  KEY `idx_priorite` (`priorite`),
  KEY `idx_date_limite` (`dateLimite`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=8 ;

--
-- Contenu de la table `task`
--

INSERT INTO `task` (`id`, `titre`, `description`, `employee_id`, `service_id`, `dateCreation`, `dateLimite`, `priorite`, `statut`) VALUES
(1, 'Recrutement Développeur', 'Rechercher un développeur Java expérimenté', 2, 2, '2026-02-22 13:04:12', '2023-12-15', 'HAUTE', 'EN_COURS'),
(2, 'Mise à jour site web', 'Mettre à jour la page d''accueil du site', 3, 2, '2026-02-22 13:04:12', '2023-12-10', 'NORMALE', 'A_FAIRE'),
(3, 'Préparation bilan annuel', 'Préparer le bilan financier de l''année', 4, 3, '2026-02-22 13:04:12', '2023-12-20', 'URGENTE', 'EN_COURS'),
(4, 'Campagne email marketing', 'Lancer la campagne email de fin d''année', 5, 4, '2026-02-22 13:04:12', '2023-12-05', 'HAUTE', 'TERMINEE'),
(5, 'Audit sécurité', 'Effectuer un audit de sécurité informatique', 2, 2, '2026-02-22 13:04:12', '2023-12-25', 'URGENTE', 'A_FAIRE'),
(6, 'Recrutement Commercial', 'Rechercher un commercial pour l''équipe', 1, 1, '2026-02-22 13:04:12', '2023-12-30', 'NORMALE', 'A_FAIRE'),
(7, 'Amélioration processus production', 'Optimiser les processus de fabrication', 6, 5, '2026-02-22 13:04:12', '2023-12-18', 'HAUTE', 'EN_COURS');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `fk_employee_service` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `service`
--
ALTER TABLE `service`
  ADD CONSTRAINT `fk_service_administrator` FOREIGN KEY (`administrator_id`) REFERENCES `administrator` (`id`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `subtask`
--
ALTER TABLE `subtask`
  ADD CONSTRAINT `fk_subtask_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `fk_task_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_task_service` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
