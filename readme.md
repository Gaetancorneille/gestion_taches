# Application de Gestion de Tâches

Application complète de gestion de tâches avec une architecture hiérarchique à cinq niveaux développée en Java avec JavaFX et MySQL.

## Architecture Hiérarchique
- **Niveau 1**: Administrateurs (super utilisateurs avec tous les privilèges)
- **Niveau 2**: Services (départements gérés par les administrateurs)
- **Niveau 3**: Employés (membres des services)
- **Niveau 4**: Tâches (assignées aux employés)
- **Niveau 5**: Sous-tâches (composantes des tâches)

## Technologies Utilisées
- Java 8+
- JavaFX (pour l'interface graphique)
- MySQL (pour la base de données)
- Git (pour la gestion de version)
- Architecture orientée objet avec pattern DAO et MVC

## Structure du Projet
```
src/
├── main/
│   └── java/
│       └── gestionnaire_taches/
│           ├── model/          # Classes métier (entités)
│           │   ├── User.java (classe abstraite)
│           │   ├── Administrator.java
│           │   ├── Employee.java
│           │   ├── Service.java
│           │   ├── Task.java
│           │   ├── Subtask.java
│           │   ├── Priority.java
│           │   └── TaskStatus.java
│           ├── dao/            # Accès aux données
│           │   ├── interfaces/
│           │   │   └── GenericDAO.java
│           │   │   └── AdministratorDAO.java
│           │   │   └── EmployeeDAO.java
│           │   │   └── ServiceDAO.java
│           │   │   └── TaskDAO.java
│           │   │   └── SubtaskDAO.java
│           │   └── impl/
│           │       └── AdministratorDAOImpl.java
│           │       └── EmployeeDAOImpl.java
│           │       └── ServiceDAOImpl.java
│           │       └── TaskDAOImpl.java
│           │       └── SubtaskDAOImpl.java
│           ├── service/        # Logique métier
│           │   ├── AuthenticationService.java
│           │   ├── AdministratorService.java
│           │   ├── ServiceManagementService.java
│           │   ├── EmployeeService.java
│           │   └── TaskService.java
│           ├── view/           # Interfaces JavaFX
│           │   ├── LoginView.java
│           │   ├── RegisterView.java
│           │   ├── AdminDashboardView.java
│           │   ├── EmployeeDashboardView.java
│           │   ├── ServiceListView.java
│           │   ├── EmployeeListView.java
│           │   ├── TaskListView.java
│           │   ├── TaskDetailsView.java
│           │   └── forms/
│           │       ├── ServiceFormView.java
│           │       ├── EmployeeFormView.java
│           │       ├── TaskFormView.java
│           │       └── SubtaskFormView.java
│           ├── controller/     # Gestion des événements
│           │   ├── AuthenticationController.java
│           │   ├── AdminController.java
│           │   ├── ServiceController.java
│           │   ├── EmployeeController.java
│           │   └── TaskController.java
│           ├── util/           # Classes utilitaires
│           │   ├── DatabaseConnection.java
│           │   ├── SessionManager.java
│           │   ├── ValidationUtils.java
│           │   ├── DateUtils.java
│           │   └── PasswordHasher.java
│           └── Main.java       # Point d'entrée de l'application
└── database/
    ├── create_database.sql
    └── insert_sample_data.sql
```

## Configuration Requise
- JDK 23 ou supérieur
- MySQL Server 8.0 ou supérieur
- Maven (géré automatiquement par le projet)
- Git

## Installation

1. Clonez le dépôt:
```bash
git clone https://github.com/Gaetancorneille/gestion_taches.git
cd gestion_taches
```

2. Créez la base de données:
```bash
mysql -u root -p < database/create_database.sql
mysql -u root -p < database/insert_sample_data.sql
```

3. Configurez la connexion à la base de données dans `src/main/java/gestionnaire_taches/util/DatabaseConnection.java`

4. Compilez le projet:
```bash
mvn clean install
```

5. Exécutez l'application:
```bash
mvn javafx:run
```

## Comptes de Test
- **Super Admin**: 
  - Email: admin@company.com
  - Mot de passe: admin123
- **Administrateur**: 
  - Email: jean.dupont@company.com
  - Mot de passe: admin123
- **Employé**: 
  - Email: sophie.laurent@company.com
  - Mot de passe: marie123

## Fonctionnalités
- Authentification et gestion des sessions
- Interface administrateur avec gestion complète
- Interface employé pour la gestion des tâches
- Gestion des services, employés, tâches et sous-tâches
- Statistiques et reporting
- Navigation hiérarchique (Admins → Services → Employés → Tâches)
- Contrôle d'accès basé sur les rôles

## Workflow Git
- Branche `main`: Version stable
- Branche `dev`: Développement
- Branches `feature/*`: Fonctionnalités
- Branches `bugfix/*`: Corrections de bugs

## Bonnes Pratiques Implémentées
- Architecture MVC
- Pattern DAO
- Validation des entrées utilisateur
- Hashage des mots de passe avec BCrypt
- Gestion des exceptions
- Code propre et commenté
- Respect des conventions de nommage Java

## Équipe de Développement
- 17 personnes réparties en 7 équipes spécialisées
- Développement parallèle et collaboratif
- Revue de code systématique
- Tests et qualité assurés

Ce projet démontre une application complète de gestion de tâches avec une architecture bien structurée, une sécurité renforcée et une interface utilisateur conviviale.