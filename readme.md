# Système de Gestion de Tâches

Application JavaFX pour la gestion de tâches avec base de données MySQL.

## Installation

### 1. Prérequis
- Java JDK 23+
- Maven 3.6+
- MySQL 8.0+

### 2. Cloner le Projet
```bash
git clone https://github.com/Gaetancorneille/gestion_taches
cd gestion_taches
```

### 3. Configurer MySQL
Créez la base de données :
```sql
CREATE DATABASE gestion_taches;
```

Créez un fichier `.env` à la racine :
```
DB_HOST=localhost
DB_PORT=3306
DB_NAME=gestion_taches
DB_USER=root
DB_PASSWORD=votre_mot_de_passe
```

### 4. Installer les Dépendances
```bash
mvn clean install
```

### 5. Lancer l'Application
```bash
mvn javafx:run
```

## Dépendances

| Dépendance | Version |
|------------|----------|
| MySQL Connector | 8.3.0 |
| JavaFX Controls | 21.0.2 |
| JavaFX FXML | 21.0.2 |
| Dotenv Java | 3.0.0 |
| JUnit Jupiter | 5.11.0 |