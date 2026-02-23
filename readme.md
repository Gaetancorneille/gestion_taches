# 📋 TaskManager Pro — Application de Gestion de Tâches

Application complète de gestion de tâches développée en **Java avec JavaFX et MySQL**, conçue autour d'une architecture hiérarchique à cinq niveaux et d'une interface moderne clair & professionnelle.

---

## 🏗️ Architecture Hiérarchique

L'application repose sur une hiérarchie à cinq niveaux qui structure les droits et les responsabilités :

| Niveau | Entité | Rôle |
|--------|--------|------|
| 1 | **Administrateurs** | Super utilisateurs avec tous les privilèges |
| 2 | **Services** | Départements gérés par les administrateurs |
| 3 | **Employés** | Membres rattachés à un service |
| 4 | **Tâches** | Assignées à un employé dans un service |
| 5 | **Sous-tâches** | Composantes détaillées d'une tâche |

---

## 🧱 Architecture Technique

### Pattern MVC + DAO

L'application suit une architecture **MVC (Modèle-Vue-Contrôleur)** combinée au **pattern DAO (Data Access Object)** :

- **Modèle** (`model/`) — Les entités métier (User, Employee, Task, etc.)
- **Vue** (`view/`) — Les interfaces JavaFX affichées à l'utilisateur
- **Contrôleur** (`controller/`) — La logique de traitement des événements
- **DAO** (`dao/`) — L'abstraction de l'accès à la base de données MySQL

### AppShell — Layout Persistant

L'un des éléments clés de l'architecture visuelle est la classe **`AppShell`**.

Avant son introduction, chaque vue recréait sa propre barre latérale et sa propre barre supérieure, ce qui provoquait deux problèmes :
- La sidebar **disparaissait** lors de l'ouverture d'un formulaire
- Chaque navigation reconstruisait inutilement toute l'interface

`AppShell` résout cela en centralisant le layout de l'application dans un conteneur **persistant** qui ne se recrée jamais :

```
┌─────────────────────────────────────────────┐
│                  TOP BAR                    │  ← Toujours visible
├────────────┬────────────────────────────────┤
│            │                                │
│  SIDEBAR   │        CONTENT AREA            │  ← Seule cette zone change
│            │   (Dashboard, Listes,          │
│            │    Formulaires...)             │
│            │                                │
├────────────┴────────────────────────────────┤
│                   FOOTER                    │  ← Toujours visible
└─────────────────────────────────────────────┘
```

Pour naviguer depuis n'importe quelle vue ou formulaire, il suffit d'appeler :

```java
AppShell shell = gestionnaire_taches.Main.getAppShell();
shell.navigateTo(new TaskListView(...).getView());
```

Cela remplace l'ancien pattern `Main.getMainLayout().setCenter(...)` qui cassait la navigation.

---

## 📁 Structure du Projet

```
src/
├── main/
│   └── java/
│       └── gestionnaire_taches/
│           ├── model/                    # Entités métier
│           │   ├── User.java             # Classe abstraite parente
│           │   ├── Administrator.java
│           │   ├── Employee.java
│           │   ├── Service.java
│           │   ├── Task.java
│           │   ├── Subtask.java
│           │   ├── Priority.java         # Enum : BASSE, MOYENNE, HAUTE
│           │   └── TaskStatus.java       # Enum : EN_COURS, TERMINEE, etc.
│           │
│           ├── dao/                      # Couche d'accès aux données
│           │   ├── interfaces/           # Contrats DAO (GenericDAO + spécifiques)
│           │   │   ├── GenericDAO.java
│           │   │   ├── AdministratorDAO.java
│           │   │   ├── EmployeeDAO.java
│           │   │   ├── ServiceDAO.java
│           │   │   ├── TaskDAO.java
│           │   │   └── SubtaskDAO.java
│           │   └── impl/                 # Implémentations JDBC/MySQL
│           │       ├── AdministratorDAOImpl.java
│           │       ├── EmployeeDAOImpl.java
│           │       ├── ServiceDAOImpl.java
│           │       ├── TaskDAOImpl.java
│           │       └── SubtaskDAOImpl.java
│           │
│           ├── service/                  # Logique métier
│           │   ├── AuthenticationService.java
│           │   ├── AdministratorService.java
│           │   ├── ServiceManagementService.java
│           │   ├── EmployeeService.java
│           │   └── TaskService.java
│           │
│           ├── view/                     # Interfaces JavaFX
│           │   ├── AppShell.java         # ★ Layout persistant (sidebar + footer)
│           │   ├── LoginView.java        # Écran de connexion (2 colonnes)
│           │   ├── RegisterView.java     # Inscription administrateur
│           │   ├── AdminDashboardContent.java  # Contenu du dashboard admin
│           │   ├── EmployeeDashboardView.java
│           │   ├── ServiceListView.java
│           │   ├── EmployeeListView.java
│           │   ├── TaskListView.java
│           │   ├── TaskDetailsView.java
│           │   └── forms/               # Formulaires de création/édition
│           │       ├── ServiceFormView.java
│           │       ├── EmployeeFormView.java
│           │       ├── TaskFormView.java
│           │       └── SubtaskFormView.java
│           │
│           ├── controller/              # Gestion des événements
│           │   ├── AuthenticationController.java
│           │   ├── AdminController.java
│           │   ├── ServiceController.java
│           │   ├── EmployeeController.java
│           │   └── TaskController.java
│           │
│           ├── util/                    # Classes utilitaires
│           │   ├── DatabaseConnection.java  # Singleton de connexion MySQL
│           │   ├── SessionManager.java      # Gestion de l'utilisateur connecté
│           │   ├── ValidationUtils.java
│           │   ├── DateUtils.java
│           │   └── PasswordHasher.java
│           │
│           └── Main.java               # Point d'entrée JavaFX
│
└── database/
    ├── create_database.sql             # Schéma complet de la base
    └── insert_sample_data.sql          # Données de test
```

---

## 🎨 Interface Utilisateur

### Thème : Clair & Professionnel

L'application utilise une palette cohérente sur toutes les vues :

| Rôle | Couleur |
|------|---------|
| Primaire (bleu corporate) | `#1565C0` |
| Fond général | `#F5F7FA` |
| Cartes / panneaux | `#FFFFFF` |
| Sidebar & Footer | `#1E2A3A` |
| Texte principal | `#1E2A3A` |
| Texte secondaire | `#7F8C8D` |
| Succès | `#2E7D32` |
| Danger | `#E74C3C` |

### Vues principales

- **LoginView / RegisterView** — Disposition en deux colonnes : panneau bleu de branding à gauche, formulaire blanc à droite. Messages d'erreur inline, connexion avec la touche Entrée.
- **AppShell (Admin / Employé)** — Contient la topbar avec avatar, la sidebar de navigation et le footer avec les contributeurs. Persiste pendant toute la session.
- **AdminDashboardContent** — Tableau de bord avec cartes de statistiques dynamiques (nb services, employés, tâches, admins) chargées en temps réel depuis la base.
- **Formulaires** — Cartes blanches avec ombres douces, validation visuelle, boutons colorés par action.

---

## ⚙️ Technologies Utilisées

- **Java 8+** (compatible JDK 23)
- **JavaFX** — Interface graphique
- **MySQL 8.0+** — Base de données relationnelle
- **BCrypt** (jBCrypt) — Hashage sécurisé des mots de passe
- **Maven** — Gestion des dépendances et build
- **Git** — Gestion de version

---

## 🚀 Installation

### 1. Cloner le dépôt

```bash
git clone https://github.com/Gaetancorneille/gestion_taches.git
cd gestion_taches
```

### 2. Créer la base de données

```bash
mysql -u root -p < database/gestion_taches.sql
```

### 3. Configurer la connexion

Ouvrir `src/main/java/gestionnaire_taches/util/DatabaseConnection.java` et renseigner :

```java
private static final String URL      = "jdbc:mysql://localhost:3306/gestion_taches";
private static final String USER     = "votre_utilisateur";
private static final String PASSWORD = "votre_mot_de_passe";
```

### 4. Compiler et lancer

```bash
mvn clean install
mvn javafx:run
```

---

## 🔐 Comptes de Test

| Rôle | Email | Mot de passe |
|------|-------|-------------|
| Super Admin | gaetan@company.com | pomognetemI12 |
| Administrateur | sabrina@company.com | Sabrina123 |
| Employé | christian@company.com | Christian123 |

---

## ✅ Fonctionnalités

- **Authentification** — Connexion sécurisée avec BCrypt, gestion de session via `SessionManager`
- **Tableau de bord Admin** — Statistiques en temps réel, accès rapide aux modules
- **Gestion des Services** — Création, édition, suppression de départements
- **Gestion des Employés** — CRUD complet, rattachement à un service
- **Gestion des Tâches** — Assignation, priorités, statuts, dates limites
- **Sous-tâches** — Décomposition fine des tâches
- **Navigation persistante** — Sidebar toujours visible grâce à `AppShell`
- **Contrôle d'accès** — Les vues s'adaptent selon le rôle (Admin / Employé)

---

## 🌿 Workflow Git

| Branche | Usage |
|---------|-------|
| `main` | Version stable, déployable |
| `dev` | Intégration continue du développement |
| `feature/*` | Nouvelles fonctionnalités |
| `bugfix/*` | Corrections de bugs |

---

## 👥 Équipe de Développement

14 membres répartis en 7 équipes spécialisées :

| | | |
|---|---|---|
| EMBONG Gaetan | MABONG Verane | DOUANLA Loreille |
| KAMCHIE Megane | DJOFANG Paul | HESSEL Anne Gloria |
| ATANGANA Adrienne | DACLEU Dimitri | DONGMO Duhamel |
| EKWA Charles | NYANGONO Armand | DIFFO Delor |
| ABESSOLO Sabrina | TIENTCHEU Christian | |

---

## 🏛️ Bonnes Pratiques Implémentées

- **Architecture MVC** — Séparation claire des responsabilités
- **Pattern DAO** — Accès aux données isolé et interchangeable
- **AppShell persistant** — Navigation fluide sans recréation du layout
- **BCrypt** — Mots de passe jamais stockés en clair
- **Null-safety** — Vérifications systématiques des valeurs nulles (ex: `dateEmbauche`, `dateCreation`)
- **Validation des entrées** — Côté vue avant toute interaction base de données
- **Gestion des exceptions** — Erreurs SQL capturées et loguées proprement
- **Code commenté** — Javadoc sur les méthodes et classes publiques
- **Conventions Java** — Nommage, packages, structure respectés