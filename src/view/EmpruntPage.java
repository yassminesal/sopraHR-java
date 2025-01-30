package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import controller.MainController;

public class EmpruntPage {

    private MainController mainController;

    public EmpruntPage(MainController mainController) {
        this.mainController = mainController;
    }

    public void display(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();


        Text headerLabel = new Text("Gestion des Emprunts");
        headerLabel.setFont(Font.font("Arial", 24));
        headerLabel.setId("headerText");
        borderPane.setTop(headerLabel);
        BorderPane.setAlignment(headerLabel, javafx.geometry.Pos.CENTER);


        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);


        Button btnAddEmprunt = new Button("Ajouter un Emprunt");
        Button btnDeleteEmprunt = new Button("Supprimer un Emprunt");
        Button btnListEmprunts = new Button("Lister les Emprunts");


        btnAddEmprunt.setOnAction(e -> mainController.handleAddEmprunt(primaryStage));
        btnDeleteEmprunt.setOnAction(e -> mainController.handleDeleteEmprunt(primaryStage));
        btnListEmprunts.setOnAction(e -> mainController.handleListEmprunts(primaryStage));


        vbox.getChildren().addAll(btnAddEmprunt, btnDeleteEmprunt, btnListEmprunts);
        borderPane.setCenter(vbox);


        Scene scene = new Scene(borderPane, 400, 300);
        scene.getStylesheets().add("file:src/view/client.css");
        primaryStage.setTitle("Gestion des Emprunts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
