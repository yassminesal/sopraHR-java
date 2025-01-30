package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import service.impl.EmpruntServiceImpl;
import controller.MainController;

public class DeleteEmpruntPage {

    private EmpruntServiceImpl empruntService = new EmpruntServiceImpl();
    private MainController mainController;


    public DeleteEmpruntPage(MainController mainController) {
        this.mainController = mainController;
    }


    public void display(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();




        VBox headerBox = new VBox();
        headerBox.setId("header");
        Text Label = new Text("Supprimer un Emprunt");
        Label.setId("headerText");
        headerBox.getChildren().add(Label);
        borderPane.setTop(headerBox);


        ImageView imageView = new ImageView(new Image("file:src/resources/empruntsupp.png"));
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);

        StackPane imagePane = new StackPane();
        imagePane.getChildren().add(imageView);
        StackPane.setMargin(imageView, new Insets(0, 70, 70, 110));
        borderPane.setLeft(imagePane);

        VBox formBox = new VBox(15);
        formBox.setId("formBox");
        formBox.setMaxWidth(700);



        HBox idBox = new HBox(10);
        Label lblId = new Label("ID Client :");
        TextField txtEmpruntId = new TextField();
        txtEmpruntId.setPromptText("ID Emprunt");
        txtEmpruntId.setPrefWidth(700);
        idBox.getChildren().addAll(lblId, txtEmpruntId);

        Button submitButton = new Button("Supprimer");
        submitButton.setOnAction(e -> {
            String empruntIdText = txtEmpruntId.getText();
            if (!empruntIdText.isEmpty()) {
                try {
                    int empruntId = Integer.parseInt(empruntIdText);
                    empruntService.supprimerEmprunt(empruntId);
                } catch (NumberFormatException ex) {

                    System.out.println("Veuillez entrer un ID valide.");
                }
            } else {
                System.out.println("L'ID de l'emprunt est vide.");
            }
        });
        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> {

            mainController.handleEmprunts(primaryStage);
        });

        formBox.getChildren().addAll(idBox, submitButton,backButton);
        borderPane.setCenter(formBox);

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setTitle("Supprimer un Emprunt");
        scene.getStylesheets().add("file:src/view/add.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
