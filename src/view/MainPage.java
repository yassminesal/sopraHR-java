package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import controller.MainController;
import service.impl.ClientServiceImpl;

public class MainPage extends Application {

    @Override
    public void start(Stage primaryStage) {

        ClientServiceImpl clientService = new ClientServiceImpl();
        MainController mainController = new MainController(clientService);


        BorderPane borderPane = new BorderPane();

        VBox headerBox = new VBox();
        headerBox.setId("header");
        Text headerLabel = new Text("Gestion Bibliothèque");
        headerLabel.setId("headerText");
        headerBox.getChildren().add(headerLabel);
        borderPane.setTop(headerBox);


        VBox leftBox = new VBox(40);
        leftBox.setId("leftBox");
        leftBox.setAlignment(Pos.CENTER);

        Button btnClients = new Button("Gestion Clients");
        Button btnDocuments = new Button("Gestion Documents");
        Button btnEmprunts = new Button("Gestion Emprunts");


        btnClients.getStyleClass().add("button");
        btnDocuments.getStyleClass().add("button");
        btnEmprunts.getStyleClass().add("button");


        btnClients.setOnAction(e -> mainController.handleClients(primaryStage));
        btnDocuments.setOnAction(e -> mainController.handleDocuments(primaryStage));
        btnEmprunts.setOnAction(e -> mainController.handleEmprunts(primaryStage));

        leftBox.getChildren().addAll(btnClients, btnDocuments, btnEmprunts);
        borderPane.setLeft(leftBox);


        ImageView imageView = new ImageView(new Image("file:src/resources/images.jpg"));
        imageView.setFitHeight(800);
        imageView.setFitWidth(800);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("image");
        borderPane.setCenter(imageView);


        Scene scene = new Scene(borderPane, 1000, 500);
        scene.getStylesheets().add("file:src/view/style.css");
        primaryStage.setTitle("Page d'Accueil");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
