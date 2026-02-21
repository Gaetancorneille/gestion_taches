package gestionnaire_taches;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Connexion extends Application{ 
Label message =new Label();
 @Override
 public void start(Stage primaryStage){
 GridPane grid = new GridPane();
 grid.setAlignment(Pos.CENTER);
 grid.setHgap(10);
 grid.setVgap(10);
 grid.setPadding(new Insets(25,25,25,25));

 Label labelNomUtilisateur = new Label("Nom D'utilisateur :");
  grid.add(labelNomUtilisateur,0,0);

  TextField champNomUtilisateur = new TextField();
  grid.add(champNomUtilisateur,1,0); 

  Label labelEmail = new Label("Email :");
  grid.add(labelEmail, 0, 1);

  TextField champEmail = new TextField();
  grid.add(champEmail, 1 , 1);

  Label labelMotDePasse = new Label("Mot de Passe :");
  grid.add(labelMotDePasse,0,2);

  PasswordField champMotDepasse = new PasswordField();
  grid.add(champMotDepasse,1,2);

  

  Button boutonConnexion = new Button("Se Connecter");
  HBox hbBtn = new HBox(10);
  hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
  hbBtn.getChildren().add(boutonConnexion);
  grid.add(hbBtn,1 , 4);

  Scene scene =  new Scene(grid,300,300);
  primaryStage.setScene(scene);
  primaryStage.show();

  boutonConnexion.setOnAction(e->{String NomUtilisateur = champNomUtilisateur.getText();
  String MotDePasse= champMotDepasse.getText();

  if(NomUtilisateur.equals("admin")&& MotDePasse.equals("password")){
    System.out.println("Connexion reussie!");
  } else{
    System.out.println("Echec De Connexion!");
    message.setText("Email ou Mot de passe incorrect");
    message.setStyle("-fx-text-fill : red ;");
  }
  });
 }
 public static void main(String [] args){
  launch(args);
 }
}


