package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import controller.MainController;

public class DocumentPage {


    private MainController mainController;


    public DocumentPage(MainController mainController) {

        this.mainController = mainController;
    }

    public void display(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();


        Text headerLabel = new Text("Gestion Documents");
        headerLabel.setFont(Font.font("Arial", 24));
        headerLabel.setId("headerText");
        borderPane.setTop(headerLabel);
        BorderPane.setAlignment(headerLabel, javafx.geometry.Pos.CENTER);


        VBox vbox = new VBox(20);
        vbox.setId("buttonBox");
        vbox.setAlignment(javafx.geometry.Pos.CENTER);


        HBox row1 = new HBox(20);
        row1.setAlignment(javafx.geometry.Pos.CENTER);
        Button btnAddDocument = new Button("Ajouter un Document");
        Button btnDeleteDocument = new Button("Supprimer un Document");
        row1.getChildren().addAll(btnAddDocument, btnDeleteDocument);

        HBox row2 = new HBox(20);
        row2.setAlignment(javafx.geometry.Pos.CENTER);
        Button btnUpdateDocument = new Button("Mettre à jour un Document");
        Button btnListDocuments = new Button("Lister les Documents");
        row2.getChildren().addAll(btnUpdateDocument, btnListDocuments);


        btnAddDocument.setOnAction(e -> mainController.handleAddDocument(primaryStage)); // Naviguer vers Ajouter Document
        btnDeleteDocument.setOnAction(e -> mainController.handleDeleteDocument(primaryStage)); // Naviguer vers Supprimer Document
        btnUpdateDocument.setOnAction(e -> mainController.handleUpdateDocument(primaryStage)); // Naviguer vers Mettre à jour Document
        btnListDocuments.setOnAction(e -> mainController.handleListDocuments(primaryStage)); // Naviguer vers Lister Documents


        vbox.getChildren().addAll(row1, row2);
        borderPane.setCenter(vbox);


        Scene scene = new Scene(borderPane, 400, 300);
        scene.getStylesheets().add("file:src/view/client.css");
        primaryStage.setTitle("Gestion des Documents");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
