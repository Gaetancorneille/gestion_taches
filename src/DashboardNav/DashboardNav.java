public class DashboardNav {
    
}
package com.dashboard.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

private static Stage primaryStage;
private static BorderPane mainLayout;

@Override
public void start(Stage stage) {
primaryStage = stage;
primaryStage.setTitle("Dashboard - Gestion d'Entreprise");
primaryStage.setMinWidth(1200);
primaryStage.setMinHeight(700);

// Créer le layout principal
mainLayout = new BorderPane();

// Initialiser la vue de connexion
showLoginView();

Scene scene = new Scene(mainLayout);
scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

primaryStage.setScene(scene);
primaryStage.show();
}

public static void showLoginView() {
LoginView loginView = new LoginView();
mainLayout.setCenter(loginView.getView());
}

public static void showAdminDashboard() {
AdminDashboardView adminDashboard = new AdminDashboardView();
mainLayout.setCenter(adminDashboard.getView());
}

public static void showEmployeeDashboard(String employeeName) {
EmployeeDashboardView employeeDashboard = new EmployeeDashboardView(employeeName);
mainLayout.setCenter(employeeDashboard.getView());
}

public static void main(String[] args) {
launch(args);
}
}
package com.dashboard.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Service {
private final IntegerProperty id = new SimpleIntegerProperty();
private final StringProperty nom = new SimpleStringProperty();
private final StringProperty description = new SimpleStringProperty();
private final IntegerProperty nombreEmployes = new SimpleIntegerProperty();

public Service(int id, String nom, String description, int nombreEmployes) {
setId(id);
setNom(nom);
setDescription(description);
setNombreEmployes(nombreEmployes);
}

// Getters et Setters
public int getId() { return id.get(); }
public void setId(int value) { id.set(value); }
public IntegerProperty idProperty() { return id; }

public String getNom() { return nom.get(); }
public void setNom(String value) { nom.set(value); }
public StringProperty nomProperty() { return nom; }

public String getDescription() { return description.get(); }
public void setDescription(String value) { description.set(value); }
public StringProperty descriptionProperty() { return description; }

public int getNombreEmployes() { return nombreEmployes.get(); }
public void setNombreEmployes(int value) { nombreEmployes.set(value); }
public IntegerProperty nombreEmployesProperty() { return nombreEmployes; }
}

// Employee.java
package com.dashboard.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Employee {
private final IntegerProperty id = new SimpleIntegerProperty();
private final StringProperty nom = new SimpleStringProperty();
private final StringProperty prenom = new SimpleStringProperty();
private final StringProperty email = new SimpleStringProperty();
private final StringProperty poste = new SimpleStringProperty();
private final IntegerProperty serviceId = new SimpleIntegerProperty();
private final StringProperty serviceNom = new SimpleStringProperty();
private final ObjectProperty<LocalDate> dateEmbauche = new SimpleObjectProperty<>();
private final DoubleProperty salaire = new SimpleDoubleProperty();

public Employee(int id, String nom, String prenom, String email, String poste,
int serviceId, String serviceNom, LocalDate dateEmbauche, double salaire) {
setId(id);
setNom(nom);
setPrenom(prenom);
setEmail(email);
setPoste(poste);
setServiceId(serviceId);
setServiceNom(serviceNom);
setDateEmbauche(dateEmbauche);
setSalaire(salaire);
}

// Getters et Setters
public int getId() { return id.get(); }
public void setId(int value) { id.set(value); }
public IntegerProperty idProperty() { return id; }

public String getNom() { return nom.get(); }
public void setNom(String value) { nom.set(value); }
public StringProperty nomProperty() { return nom; }

public String getPrenom() { return prenom.get(); }
public void setPrenom(String value) { prenom.set(value); }
public StringProperty prenomProperty() { return prenom; }

public String getEmail() { return email.get(); }
public void setEmail(String value) { email.set(value); }
public StringProperty emailProperty() { return email; }

public String getPoste() { return poste.get(); }
public void setPoste(String value) { poste.set(value); }
public StringProperty posteProperty() { return poste; }

public int getServiceId() { return serviceId.get(); }
public void setServiceId(int value) { serviceId.set(value); }
public IntegerProperty serviceIdProperty() { return serviceId; }

public String getServiceNom() { return serviceNom.get(); }
public void setServiceNom(String value) { serviceNom.set(value); }
public StringProperty serviceNomProperty() { return serviceNom; }

public LocalDate getDateEmbauche() { return dateEmbauche.get(); }
public void setDateEmbauche(LocalDate value) { dateEmbauche.set(value); }
public ObjectProperty<LocalDate> dateEmbaucheProperty() { return dateEmbauche; }

public double getSalaire() { return salaire.get(); }
public void setSalaire(double value) { salaire.set(value); }
public DoubleProperty salaireProperty() { return salaire; }
}

// Task.java
package com.dashboard.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Task {
public enum Statut { EN_ATTENTE, EN_COURS, TERMINEE, BLOQUEE }
public enum Priorite { BASSE, MOYENNE, HAUTE, CRITIQUE }

private final IntegerProperty id = new SimpleIntegerProperty();
private final StringProperty titre = new SimpleStringProperty();
private final StringProperty description = new SimpleStringProperty();
private final ObjectProperty<Statut> statut = new SimpleObjectProperty<>();
private final ObjectProperty<Priorite> priorite = new SimpleObjectProperty<>();
private final IntegerProperty employeId = new SimpleIntegerProperty();
private final StringProperty employeNom = new SimpleStringProperty();
private final ObjectProperty<LocalDate> dateCreation = new SimpleObjectProperty<>();
private final ObjectProperty<LocalDate> dateEcheance = new SimpleObjectProperty<>();
private final DoubleProperty progression = new SimpleDoubleProperty();

public Task(int id, String titre, String description, Statut statut, Priorite priorite,
int employeId, String employeNom, LocalDate dateCreation, LocalDate dateEcheance, double progression) {
setId(id);
setTitre(titre);
setDescription(description);
setStatut(statut);
setPriorite(priorite);
setEmployeId(employeId);
setEmployeNom(employeNom);
setDateCreation(dateCreation);
setDateEcheance(dateEcheance);
setProgression(progression);
}

// Getters et Setters
public int getId() { return id.get(); }
public void setId(int value) { id.set(value); }
public IntegerProperty idProperty() { return id; }

public String getTitre() { return titre.get(); }
public void setTitre(String value) { titre.set(value); }
public StringProperty titreProperty() { return titre; }

public String getDescription() { return description.get(); }
public void setDescription(String value) { description.set(value); }
public StringProperty descriptionProperty() { return description; }

public Statut getStatut() { return statut.get(); }
public void setStatut(Statut value) { statut.set(value); }
public ObjectProperty<Statut> statutProperty() { return statut; }

public Priorite getPriorite() { return priorite.get(); }
public void setPriorite(Priorite value) { priorite.set(value); }
public ObjectProperty<Priorite> prioriteProperty() { return priorite; }

public int getEmployeId() { return employeId.get(); }
public void setEmployeId(int value) { employeId.set(value); }
public IntegerProperty employeIdProperty() { return employeId; }

public String getEmployeNom() { return employeNom.get(); }
public void setEmployeNom(String value) { employeNom.set(value); }
public StringProperty employeNomProperty() { return employeNom; }

public LocalDate getDateCreation() { return dateCreation.get(); }
public void setDateCreation(LocalDate value) { dateCreation.set(value); }
public ObjectProperty<LocalDate> dateCreationProperty() { return dateCreation; }

public LocalDate getDateEcheance() { return dateEcheance.get(); }
public void setDateEcheance(LocalDate value) { dateEcheance.set(value); }
public ObjectProperty<LocalDate> dateEcheanceProperty() { return dateEcheance; }

public double getProgression() { return progression.get(); }
public void setProgression(double value) { progression.set(value); }
public DoubleProperty progressionProperty() { return progression; }
}
package com.dashboard.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginView {

public VBox getView() {
VBox loginBox = new VBox(20);
loginBox.setAlignment(Pos.CENTER);
loginBox.setPadding(new Insets(40));
loginBox.getStyleClass().add("login-container");

// Logo et titre
Text title = new Text("Gestion d'Entreprise");
title.getStyleClass().add("login-title");

Label subtitle = new Label("Connectez-vous pour accéder au tableau de bord");
subtitle.getStyleClass().add("login-subtitle");

// Formulaire de connexion
GridPane form = new GridPane();
form.setHgap(15);
form.setVgap(15);
form.setAlignment(Pos.CENTER);

Label userLabel = new Label("Nom d'utilisateur:");
userLabel.getStyleClass().add("form-label");
TextField userField = new TextField();
userField.setPromptText("admin ou employee");
userField.getStyleClass().add("form-field");

Label passLabel = new Label("Mot de passe:");
passLabel.getStyleClass().add("form-label");
PasswordField passField = new PasswordField();
passField.setPromptText("••••••••");
passField.getStyleClass().add("form-field");

ComboBox<String> roleBox = new ComboBox<>();
roleBox.getItems().addAll("Administrateur", "Employé");
roleBox.setValue("Administrateur");
roleBox.getStyleClass().add("form-field");

form.add(userLabel, 0, 0);
form.add(userField, 1, 0);
form.add(passLabel, 0, 1);
form.add(passField, 1, 1);
form.add(new Label("Rôle:"), 0, 2);
form.add(roleBox, 1, 2);

// Boutons
HBox buttonBox = new HBox(15);
buttonBox.setAlignment(Pos.CENTER);

Button loginBtn = new Button("Se connecter");
loginBtn.getStyleClass().addAll("button", "primary-button");
loginBtn.setOnAction(e -> {
if ("admin".equals(userField.getText()) || "Administrateur".equals(roleBox.getValue())) {
Main.showAdminDashboard();
} else {
Main.showEmployeeDashboard(userField.getText());
}
});

Button cancelBtn = new Button("Annuler");
cancelBtn.getStyleClass().addAll("button", "secondary-button");

buttonBox.getChildren().addAll(loginBtn, cancelBtn);

loginBox.getChildren().addAll(title, subtitle, form, buttonBox);

return loginBox;
}
}
package com.dashboard.view;

import com.dashboard.model.*;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminDashboardView {

private BorderPane mainView;
private HBox topBar;
private VBox sideMenu;
private StackPane contentArea;
private Label currentUserLabel;

// Données simulées
private ObservableList<Service> services = FXCollections.observableArrayList();
private ObservableList<Employee> employees = FXCollections.observableArrayList();
private ObservableList<Task> tasks = FXCollections.observableArrayList();

public AdminDashboardView() {
initializeData();
createView();
}

private void initializeData() {
// Services
services.addAll(
new Service(1, "Ressources Humaines", "Gestion du personnel et recrutement", 8),
new Service(2, "Développement", "Développement logiciel et maintenance", 15),
new Service(3, "Commercial", "Ventes et relation client", 12),
new Service(4, "Marketing", "Stratégie marketing et communication", 6),
new Service(5, "Finance", "Gestion comptable et financière", 5)
);

// Employés
employees.addAll(
new Employee(1, "Dupont", "Jean", "jean.dupont@email.com", "Chef RH", 1, "Ressources Humaines", LocalDate.of(2020, 3, 15), 45000),
new Employee(2, "Martin", "Sophie", "sophie.martin@email.com", "Développeuse Senior", 2, "Développement", LocalDate.of(2019, 6, 10), 52000),
new Employee(3, "Bernard", "Pierre", "pierre.bernard@email.com", "Commercial", 3, "Commercial", LocalDate.of(2021, 1, 20), 38000),
new Employee(4, "Petit", "Marie", "marie.petit@email.com", "Responsable Marketing", 4, "Marketing", LocalDate.of(2018, 9, 5), 48000),
new Employee(5, "Robert", "Luc", "luc.robert@email.com", "Analyste Financier", 5, "Finance", LocalDate.of(2022, 2, 1), 42000)
);

// Tâches
tasks.addAll(
new Task(1, "Recrutement Développeur", "Rechercher un développeur Java", Task.Statut.EN_COURS, Task.Priorite.HAUTE, 1, "Jean Dupont", LocalDate.now(), LocalDate.now().plusDays(15), 45),
new Task(2, "Mise à jour site web", "Mettre à jour la page d'accueil", Task.Statut.EN_ATTENTE, Task.Priorite.MOYENNE, 2, "Sophie Martin", LocalDate.now(), LocalDate.now().plusDays(7), 0),
new Task(3, "Présentation client", "Préparer présentation pour nouveau client", Task.Statut.TERMINEE, Task.Priorite.HAUTE, 3, "Pierre Bernard", LocalDate.now().minusDays(5), LocalDate.now(), 100),
new Task(4, "Campagne email", "Lancer campagne email marketing", Task.Statut.BLOQUEE, Task.Priorite.BASSE, 4, "Marie Petit", LocalDate.now(), LocalDate.now().plusDays(10), 30),
new Task(5, "Rapport trimestriel", "Préparer rapport financier Q2", Task.Statut.EN_COURS, Task.Priorite.CRITIQUE, 5, "Luc Robert", LocalDate.now(), LocalDate.now().plusDays(5), 60)
);
}

private void createView() {
mainView = new BorderPane();
mainView.getStyleClass().add("main-container");

// Créer la barre supérieure
createTopBar();
mainView.setTop(topBar);

// Créer le menu latéral
createSideMenu();
mainView.setLeft(sideMenu);

// Créer la zone de contenu
contentArea = new StackPane();
contentArea.getStyleClass().add("content-area");
mainView.setCenter(contentArea);

// Afficher le tableau de bord par défaut
showDashboard();
}

private void createTopBar() {
topBar = new HBox();
topBar.setAlignment(Pos.CENTER_RIGHT);
topBar.setPadding(new Insets(15, 30, 15, 30));
topBar.getStyleClass().add("top-bar");

// Titre de la page
Label pageTitle = new Label("Tableau de bord administrateur");
pageTitle.getStyleClass().add("page-title");
HBox.setHgrow(pageTitle, Priority.ALWAYS);

// Profil utilisateur
HBox profileBox = new HBox(15);
profileBox.setAlignment(Pos.CENTER_RIGHT);

Label userName = new Label("Admin Principal");
userName.getStyleClass().add("user-name");

Button logoutBtn = new Button("Déconnexion");
logoutBtn.getStyleClass().addAll("button", "logout-button");
logoutBtn.setOnAction(e -> Main.showLoginView());

profileBox.getChildren().addAll(userName, logoutBtn);

topBar.getChildren().addAll(pageTitle, profileBox);
}

private void createSideMenu() {
sideMenu = new VBox(10);
sideMenu.setPadding(new Insets(30, 20, 30, 20));
sideMenu.setMinWidth(250);
sideMenu.setMaxWidth(250);
sideMenu.getStyleClass().add("side-menu");

// Logo
Label logo = new Label("📊 GestionPro");
logo.getStyleClass().add("logo");

// Menu items
Button dashboardBtn = createMenuItem("🏠 Tableau de bord", "dashboard");
Button servicesBtn = createMenuItem("🏢 Services", "services");
Button employeesBtn = createMenuItem("👥 Employés", "employees");
Button tasksBtn = createMenuItem("✅ Tâches", "tasks");
Button statsBtn = createMenuItem("📈 Statistiques", "stats");
Button settingsBtn = createMenuItem("⚙️ Paramètres", "settings");

VBox menuBox = new VBox(5);
menuBox.getChildren().addAll(dashboardBtn, servicesBtn, employeesBtn, tasksBtn, statsBtn);

VBox bottomBox = new VBox();
bottomBox.setAlignment(Pos.BOTTOM_CENTER);
VBox.setVgrow(bottomBox, Priority.ALWAYS);
bottomBox.getChildren().add(settingsBtn);

sideMenu.getChildren().addAll(logo, menuBox, bottomBox);
}

private Button createMenuItem(String text, String action) {
Button btn = new Button(text);
btn.getStyleClass().add("menu-item");
btn.setMaxWidth(Double.MAX_VALUE);
btn.setAlignment(Pos.CENTER_LEFT);

btn.setOnAction(e -> {
// Retirer la classe active de tous les items
sideMenu.getChildren().stream()
.filter(node -> node instanceof VBox)
.flatMap(node -> ((VBox) node).getChildren().stream())
.filter(node -> node instanceof Button)
.forEach(node -> node.getStyleClass().remove("menu-item-active"));

btn.getStyleClass().add("menu-item-active");

switch(action) {
case "dashboard": showDashboard(); break;
case "services": showServices(); break;
case "employees": showEmployees(); break;
case "tasks": showTasks(); break;
case "stats": showStats(); break;
case "settings": showSettings(); break;
}
});

return btn;
}

private void showDashboard() {
VBox dashboard = new VBox(20);
dashboard.setPadding(new Insets(20));
dashboard.getStyleClass().add("dashboard-view");

// Statistiques en cartes
HBox statsCards = new HBox(20);
statsCards.setAlignment(Pos.CENTER);

statsCards.getChildren().addAll(
createStatCard("🏢 Services", String.valueOf(services.size()), "Total services", "#4CAF50"),
createStatCard("👥 Employés", String.valueOf(employees.size()), "Total employés", "#2196F3"),
createStatCard("✅ Tâches", String.valueOf(tasks.size()), "Total tâches", "#FF9800"),
createStatCard("📊 Progression", "68%", "Tâches complétées", "#9C27B0")
);

// Graphiques
HBox chartsBox = new HBox(20);
chartsBox.setAlignment(Pos.CENTER);

// Camembert - Répartition par service
PieChart serviceChart = new PieChart();
serviceChart.setTitle("Employés par service");
serviceChart.setData(FXCollections.observableArrayList(
new PieChart.Data("RH", 8),
new PieChart.Data("Développement", 15),
new PieChart.Data("Commercial", 12),
new PieChart.Data("Marketing", 6),
new PieChart.Data("Finance", 5)
));
serviceChart.getStyleClass().add("chart");

// Diagramme à barres - Tâches par statut
CategoryAxis xAxis = new CategoryAxis();
NumberAxis yAxis = new NumberAxis();
BarChart<String, Number> taskChart = new BarChart<>(xAxis, yAxis);
taskChart.setTitle("Tâches par statut");

XYChart.Series<String, Number> series = new XYChart.Series<>();
series.setName("Tâches");
series.getData().add(new XYChart.Data<>("En attente", 5));
series.getData().add(new XYChart.Data<>("En cours", 8));
series.getData().add(new XYChart.Data<>("Terminées", 12));
series.getData().add(new XYChart.Data<>("Bloquées", 3));

taskChart.getData().add(series);
taskChart.getStyleClass().add("chart");

chartsBox.getChildren().addAll(serviceChart, taskChart);

// Tâches récentes
VBox recentTasksBox = new VBox(10);
recentTasksBox.getStyleClass().add("recent-tasks-box");

Label tasksTitle = new Label("Tâches récentes");
tasksTitle.getStyleClass().add("section-title");

TableView<Task> recentTasksTable = createTasksTable();
recentTasksTable.setItems(FXCollections.observableArrayList(tasks.subList(0, Math.min(3, tasks.size()))));

recentTasksBox.getChildren().addAll(tasksTitle, recentTasksTable);

dashboard.getChildren().addAll(statsCards, chartsBox, recentTasksBox);

setContentWithAnimation(dashboard);
}

private VBox createStatCard(String title, String value, String subtitle, String color) {
VBox card = new VBox(5);
card.getStyleClass().add("stat-card");
card.setStyle("-fx-background-color: " + color + ";");

Label titleLabel = new Label(title);
titleLabel.getStyleClass().add("stat-card-title");

Label valueLabel = new Label(value);
valueLabel.getStyleClass().add("stat-card-value");

Label subtitleLabel = new Label(subtitle);
subtitleLabel.getStyleClass().add("stat-card-subtitle");

card.getChildren().addAll(titleLabel, valueLabel, subtitleLabel);

return card;
}

private void showServices() {
ServiceListView serviceView = new ServiceListView(services, this::showEmployeesForService);
setContentWithAnimation(serviceView.getView());
}

private void showEmployees() {
EmployeeListView employeeView = new EmployeeListView(employees, services, this::showTasksForEmployee);
setContentWithAnimation(employeeView.getView());
}

private void showTasks() {
TaskListView taskView = new TaskListView(tasks, employees);
setContentWithAnimation(taskView.getView());
}

private void showStats() {
VBox statsView = new VBox(20);
statsView.setPadding(new Insets(20));

Label title = new Label("Statistiques détaillées");
title.getStyleClass().add("page-title");

HBox chartsBox = new HBox(20);

// Graphiques supplémentaires
PieChart progressChart = new PieChart();
progressChart.setTitle("Progression globale");
progressChart.setData(FXCollections.observableArrayList(
new PieChart.Data("Complété", 45),
new PieChart.Data("En cours", 35),
new PieChart.Data("À venir", 20)
));

BarChart<String, Number> performanceChart = createPerformanceChart();

chartsBox.getChildren().addAll(progressChart, performanceChart);

statsView.getChildren().addAll(title, chartsBox);

setContentWithAnimation(statsView);
}

private BarChart<String, Number> createPerformanceChart() {
CategoryAxis xAxis = new CategoryAxis();
NumberAxis yAxis = new NumberAxis();
BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
chart.setTitle("Performance par employé");

XYChart.Series<String, Number> series = new XYChart.Series<>();
series.setName("Tâches complétées");

for (Employee emp : employees) {
series.getData().add(new XYChart.Data<>(emp.getPrenom(), (int)(Math.random() * 20 + 5)));
}

chart.getData().add(series);
return chart;
}

private void showSettings() {
VBox settingsView = new VBox(20);
settingsView.setPadding(new Insets(20));

Label title = new Label("Paramètres");
title.getStyleClass().add("page-title");

settingsView.getChildren().add(title);

setContentWithAnimation(settingsView);
}

private void showEmployeesForService(Service service) {
ObservableList<Employee> filteredEmployees = FXCollections.observableArrayList(
employees.filtered(e -> e.getServiceId() == service.getId())
);
EmployeeListView employeeView = new EmployeeListView(filteredEmployees, services, this::showTasksForEmployee);
setContentWithAnimation(employeeView.getView());
}

private void showTasksForEmployee(Employee employee) {
ObservableList<Task> filteredTasks = FXCollections.observableArrayList(
tasks.filtered(t -> t.getEmployeId() == employee.getId())
);
TaskListView taskView = new TaskListView(filteredTasks, employees);
setContentWithAnimation(taskView.getView());
}

private TableView<Task> createTasksTable() {
TableView<Task> table = new TableView<>();
table.getStyleClass().add("data-table");

TableColumn<Task, String> titreCol = new TableColumn<>("Titre");
titreCol.setCellValueFactory(cellData -> cellData.getValue().titreProperty());

TableColumn<Task, Task.Statut> statutCol = new TableColumn<>("Statut");
statutCol.setCellValueFactory(cellData -> cellData.getValue().statutProperty());

TableColumn<Task, Task.Priorite> prioriteCol = new TableColumn<>("Priorité");
prioriteCol.setCellValueFactory(cellData -> cellData.getValue().prioriteProperty());

TableColumn<Task, String> employeCol = new TableColumn<>("Employé");
employeCol.setCellValueFactory(cellData -> cellData.getValue().employeNomProperty());

TableColumn<Task, Double> progressionCol = new TableColumn<>("Progression");
progressionCol.setCellValueFactory(cellData -> cellData.getValue().progressionProperty().asObject());
progressionCol.setCellFactory(col -> new TableCell<Task, Double>() {
@Override
protected void updateItem(Double item, boolean empty) {
super.updateItem(item, empty);
if (empty || item == null) {
setText(null);
setGraphic(null);
} else {
ProgressBar progressBar = new ProgressBar(item / 100);
progressBar.setPrefWidth(80);
setGraphic(progressBar);
setText(String.format("%.0f%%", item));
}
}
});

table.getColumns().addAll(titreCol, statutCol, prioriteCol, employeCol, progressionCol);
return table;
}

private void setContentWithAnimation(Node content) {
content.setOpacity(0);
contentArea.getChildren().setAll(content);

FadeTransition ft = new FadeTransition(Duration.millis(300), content);
ft.setFromValue(0);
ft.setToValue(1);
ft.play();
}

public Node getView() {
return mainView;
}
}
package com.dashboard.view;

import com.dashboard.model.*;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.time.LocalDate;

public class EmployeeDashboardView {

private BorderPane mainView;
private String employeeName;
private ObservableList<Task> employeeTasks;

public EmployeeDashboardView(String employeeName) {
this.employeeName = employeeName != null ? employeeName : "Employé";
initializeData();
createView();
}

private void initializeData() {
employeeTasks = FXCollections.observableArrayList(
new Task(1, "Mise à jour documentation", "Mettre à jour la documentation technique",
Task.Statut.EN_COURS, Task.Priorite.MOYENNE, 1, employeeName,
LocalDate.now(), LocalDate.now().plusDays(3), 45),
new Task(2, "Réunion d'équipe", "Préparer présentation pour réunion",
Task.Statut.EN_ATTENTE, Task.Priorite.HAUTE, 1, employeeName,
LocalDate.now(), LocalDate.now().plusDays(1), 0),
new Task(3, "Correction bug", "Corriger le bug d'affichage",
Task.Statut.TERMINEE, Task.Priorite.CRITIQUE, 1, employeeName,
LocalDate.now().minusDays(2), LocalDate.now(), 100),
new Task(4, "Formation Java", "Suivre formation en ligne",
Task.Statut.EN_COURS, Task.Priorite.BASSE, 1, employeeName,
LocalDate.now(), LocalDate.now().plusDays(10), 30)
);
}

private void createView() {
mainView = new BorderPane();
mainView.getStyleClass().add("main-container");

// Barre supérieure
HBox topBar = new HBox();
topBar.setAlignment(Pos.CENTER_RIGHT);
topBar.setPadding(new Insets(15, 30, 15, 30));
topBar.getStyleClass().add("top-bar");

Label pageTitle = new Label("Tableau de bord employé");
pageTitle.getStyleClass().add("page-title");
HBox.setHgrow(pageTitle, Priority.ALWAYS);

HBox profileBox = new HBox(15);
profileBox.setAlignment(Pos.CENTER_RIGHT);

Label userName = new Label(employeeName);
userName.getStyleClass().add("user-name");

Button logoutBtn = new Button("Déconnexion");
logoutBtn.getStyleClass().addAll("button", "logout-button");
logoutBtn.setOnAction(e -> Main.showLoginView());

profileBox.getChildren().addAll(userName, logoutBtn);
topBar.getChildren().addAll(pageTitle, profileBox);

// Menu latéral simplifié
VBox sideMenu = new VBox(10);
sideMenu.setPadding(new Insets(30, 20, 30, 20));
sideMenu.setMinWidth(200);
sideMenu.getStyleClass().add("side-menu");

Label logo = new Label("📊 Mon Espace");
logo.getStyleClass().add("logo");

Button dashboardBtn = createMenuItem("🏠 Tableau de bord", "dashboard");
Button tasksBtn = createMenuItem("✅ Mes tâches", "tasks");
Button profileBtn = createMenuItem("👤 Mon profil", "profile");

VBox menuBox = new VBox(5);
menuBox.getChildren().addAll(dashboardBtn, tasksBtn, profileBtn);

sideMenu.getChildren().addAll(logo, menuBox);

// Zone de contenu
StackPane contentArea = new StackPane();
contentArea.getStyleClass().add("content-area");

// Afficher le tableau de bord par défaut
showEmployeeDashboard(contentArea);

// Actions des boutons
dashboardBtn.setOnAction(e -> {
resetMenuStyles(dashboardBtn, tasksBtn, profileBtn);
dashboardBtn.getStyleClass().add("menu-item-active");
showEmployeeDashboard(contentArea);
});

tasksBtn.setOnAction(e -> {
resetMenuStyles(dashboardBtn, tasksBtn, profileBtn);
tasksBtn.getStyleClass().add("menu-item-active");
showEmployeeTasks(contentArea);
});

profileBtn.setOnAction(e -> {
resetMenuStyles(dashboardBtn, tasksBtn, profileBtn);
profileBtn.getStyleClass().add("menu-item-active");
showEmployeeProfile(contentArea);
});

mainView.setTop(topBar);
mainView.setLeft(sideMenu);
mainView.setCenter(contentArea);
}

private Button createMenuItem(String text, String action) {
Button btn = new Button(text);
btn.getStyleClass().add("menu-item");
btn.setMaxWidth(Double.MAX_VALUE);
btn.setAlignment(Pos.CENTER_LEFT);
return btn;
}

private void resetMenuStyles(Button... buttons) {
for (Button btn : buttons) {
btn.getStyleClass().remove("menu-item-active");
}
}

private void showEmployeeDashboard(StackPane contentArea) {
VBox dashboard = new VBox(20);
dashboard.setPadding(new Insets(20));

// Statistiques personnelles
HBox statsCards = new HBox(20);
statsCards.setAlignment(Pos.CENTER);

long tasksEnCours = employeeTasks.stream().filter(t -> t.getStatut() == Task.Statut.EN_COURS).count();
long tasksTerminees = employeeTasks.stream().filter(t -> t.getStatut() == Task.Statut.TERMINEE).count();

statsCards.getChildren().addAll(
createStatCard("📋 Mes tâches", String.valueOf(employeeTasks.size()), "

