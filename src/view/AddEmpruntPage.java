package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import service.impl.EmpruntServiceImpl;
import java.time.LocalDate;
import controller.MainController;

public class AddEmpruntPage {

    private EmpruntServiceImpl empruntService;
    private MainController mainController;// Instance du service d'emprunt

    public AddEmpruntPage(MainController mainController) {
        this.mainController = mainController;
        this.empruntService = new EmpruntServiceImpl();  // Création de l'instance
    }

    public void display(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        // Créer le titre
        VBox headerBox = new VBox();
        headerBox.setId("header");
        Text Label = new Text("Ajouter Emprunt");
        Label.setId("headerText");
        headerBox.getChildren().add(Label);
        borderPane.setTop(headerBox);


        ImageView imageView = new ImageView(new Image("file:src/resources/emprunt.jpg"));
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);

        StackPane imagePane = new StackPane();
        imagePane.getChildren().add(imageView);
        StackPane.setMargin(imageView, new Insets(0, 70, 70, 110));
        borderPane.setLeft(imagePane);


        VBox formBox = new VBox(15);
        formBox.setId("formBox");
        formBox.setMaxWidth(500);

        HBox idBox = new HBox(10);
        Label lblClientId = new Label("ID Client :");
        TextField txtId = new TextField();
        txtId.setPromptText("ID Client");
        txtId.setPrefWidth(700);
        idBox.getChildren().addAll(lblClientId, txtId);


        HBox hboxId = new HBox(10);
        Label lblId = new Label("Id Document:");
        TextField txtDocumentId = new TextField();
        txtDocumentId.setPromptText("ID Document");
        txtDocumentId.setPrefWidth(700);
        hboxId.getChildren().addAll(lblId, txtDocumentId);

        HBox hboxemprunt = new HBox(10);
        Label lblEmprunt = new Label("Date Emprunt:");
        DatePicker dateEmprunt = new DatePicker();
        dateEmprunt.setPromptText("Date Emprunt");
        hboxemprunt.getChildren().addAll(lblEmprunt, dateEmprunt);


        HBox hboxRetour = new HBox(10);
        Label lblRetour = new Label("Date Retour:");
        DatePicker dateRetour = new DatePicker();
        dateRetour.setPromptText("Date Retour");
        hboxRetour.getChildren().addAll(lblRetour, dateRetour);


        Button submitButton = new Button("Ajouter");
        submitButton.setOnAction(e -> {
            try {

                int clientId = Integer.parseInt(txtId.getText());
                int documentId = Integer.parseInt(txtDocumentId.getText());
                LocalDate dateEmpruntValue = dateEmprunt.getValue();
                LocalDate dateRetourValue = dateRetour.getValue();


                if (dateEmpruntValue != null && dateRetourValue != null && !dateRetourValue.isBefore(dateEmpruntValue)) {
                    empruntService.emprunterDocument(clientId, documentId);
                } else {
                    System.out.println("La date de retour invalide");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Veuillez entrer des valeurs valides pour l'ID Client et l'ID Document.");
            }
        });
        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> {mainController.handleEmprunts(primaryStage);
        });

        formBox.getChildren().addAll(idBox, hboxId, hboxemprunt,hboxRetour , submitButton,backButton);
        borderPane.setCenter(formBox);


        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setTitle("Ajouter un Emprunt");
        scene.getStylesheets().add("file:src/view/add.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
