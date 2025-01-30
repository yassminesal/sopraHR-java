package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import controller.MainController;
import service.impl.ClientServiceImpl;

public class ClientPage {

    public void display(Stage primaryStage) {

        ClientServiceImpl clientService = new ClientServiceImpl();
        MainController mainController = new MainController(clientService);


        BorderPane borderPane = new BorderPane();


        VBox headerBox = new VBox();
        headerBox.setId("header");
        Text Label = new Text("Gestion Clients");
        Label.setId("headerText");
        headerBox.getChildren().add(Label);
        borderPane.setTop(headerBox);



        VBox vbox = new VBox(30);
        vbox.setId("buttonBox");

        HBox ligne1 = new HBox(50);
        Button btnAddClient = new Button("Ajouter un Client");
        Button btnDeleteClient = new Button("Supprimer un Client");
        ligne1.getChildren().addAll(btnAddClient, btnDeleteClient);

        HBox ligne2 = new HBox(50);
        Button btnUpdateClient = new Button("Mettre à jour un Client");
        Button btnListClients = new Button("Lister les Clients");
        ligne2.getChildren().addAll(btnUpdateClient, btnListClients);

        btnAddClient.setOnAction(e -> mainController.handleAddClient(primaryStage));
        btnDeleteClient.setOnAction(e -> mainController.handleDeleteClient(primaryStage));
        btnUpdateClient.setOnAction(e -> mainController.handleUpdateClient(primaryStage));
        btnListClients.setOnAction(e -> mainController.handleListClients(primaryStage));

        vbox.getChildren().addAll(ligne1, ligne2);
        borderPane.setCenter(vbox);


        Scene scene = new Scene(borderPane, 400, 300);
        scene.getStylesheets().add("file:src/view/client.css");
        primaryStage.setTitle("Gestion des Clients");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
