package gestionnaire_taches.interface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class register extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label nomLabel = new Label("Nom :");
        grid.add(nomLabel, 0, 0);

        TextField nomField = new TextField();
        grid.add(nomField, 1, 0);

        Label prenomLabel = new Label("Prénom :");
        grid.add(prenomLabel, 0, 1);

        TextField prenomField = new TextField();
        grid.add(prenomField, 1, 1);

        Label emailLabel = new Label("Email :");
        grid.add(emailLabel, 0, 2);

        TextField emailField = new TextField();
        grid.add(emailField, 1, 2);

        Label motDePasseLabel = new Label("Mot de passe :");
        grid.add(motDePasseLabel, 0, 3);

        PasswordField motDePasseField = new PasswordField();
        grid.add(motDePasseField, 1, 3);

        Label confirmerMotDePasseLabel = new Label("Confirmer mot de passe :");
        grid.add(confirmerMotDePasseLabel, 0, 4);

        PasswordField confirmerMotDePasseField = new PasswordField();
        grid.add(confirmerMotDePasseField, 1, 4);

        Button inscrireButton = new Button("S'inscrire");
        grid.add(inscrireButton, 1, 5);

        inscrireButton.setOnAction(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String motDePasse = motDePasseField.getText();
            String confirmerMotDePasse = confirmerMotDePasseField.getText();

            // Valider les champs
            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || confirmerMotDePasse.isEmpty()) {
                System.out.println("Tous les champs sont obligatoires !");
            } else if (!motDePasse.equals(confirmerMotDePasse)) {
                System.out.println("Les mots de passe ne correspondent pas !");
            } else {
                // Enregistrer l'utilisateur dans la base de données
                System.out.println("Inscription réussie !");
            }
        });

        Scene scene = new Scene(grid, 300, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}