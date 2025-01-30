package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import controller.MainController;

public class DeleteClientPage {

    private MainController mainController;


    public DeleteClientPage(MainController mainController) {
        this.mainController = mainController;
    }

    public void display(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();




        VBox headerBox = new VBox();
        headerBox.setId("header");
        Text Label = new Text("Supprimer un Client");
        Label.setId("headerText");
        headerBox.getChildren().add(Label);
        borderPane.setTop(headerBox);


        ImageView imageView = new ImageView(new Image("file:src/resources/delete.png"));
        imageView.setFitHeight(300);
        imageView.setFitWidth(280);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-padding: 20px;");


        StackPane imagePane = new StackPane();
        imagePane.getChildren().add(imageView);
        StackPane.setMargin(imageView, new Insets(50, 70, 70, 110));

        borderPane.setLeft(imagePane);


        VBox formBox = new VBox(15);
        formBox.setId("formBox");
        formBox.setMaxWidth(600);



        Label lblClientId = new Label("ID Client :");
        TextField txtClientId = new TextField();
        txtClientId.setPromptText("ID Client");
        lblClientId.setPrefWidth(600);





        Button submitButton = new Button("Supprimer");
        submitButton.setOnAction(e -> {
            //
            String clientIdText = txtClientId.getText();
            if (clientIdText.isEmpty()) {
                Alert("Erreur", "L'ID du client est vide.");
                return;
            }

            try {
                int clientId = Integer.parseInt(clientIdText);


                boolean success = mainController.deleteClient(clientId);
                if (success) {
                    Alert("Succès", "Client supprimé avec succès !");
                } else {
                    Alert("Erreur", "Le client n'a pas pu être supprimé. Vérifiez l'ID.");
                }
            } catch (NumberFormatException ex) {
                Alert("Erreur", "L'ID du client doit être un nombre entier.");
            }
        });


        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> navigateToClientPage(primaryStage));

        formBox.getChildren().addAll(lblClientId, txtClientId, submitButton, backButton);
        borderPane.setCenter(formBox);


        Scene scene = new Scene(borderPane, 600, 400);
        scene.getStylesheets().add("file:src/view/add.css");
        primaryStage.setTitle("Supprimer Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void navigateToClientPage(Stage primaryStage) {
        mainController.handleClients(primaryStage);
    }


    private void Alert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
