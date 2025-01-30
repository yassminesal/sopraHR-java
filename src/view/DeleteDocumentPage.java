package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.IDocumentService;
import controller.MainController;

public class DeleteDocumentPage {

    private IDocumentService documentService;
    private MainController mainController;  // Déclarer la variable mainController

    // Constructeur avec injection du service
    public DeleteDocumentPage(IDocumentService documentService, MainController mainController) {
        this.documentService = documentService;
        this.mainController = mainController;
    }

    public void display(Stage primaryStage) {
        // Créer un BorderPane
        BorderPane borderPane = new BorderPane();

        VBox headerBox = new VBox();
        headerBox.setId("header");
        Text Label = new Text("Supprimer Livre ou Magazine");
        Label.setId("headerText");
        headerBox.getChildren().add(Label);
        borderPane.setTop(headerBox);

        // Partie gauche : Image
        ImageView imageView = new ImageView(new Image("file:src/resources/livresupp.jpg"));
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);
        borderPane.setLeft(imageView);

        // Créer un StackPane pour l'image et appliquer une marge à droite
        StackPane imagePane = new StackPane();
        imagePane.getChildren().add(imageView);
        StackPane.setMargin(imageView, new Insets(50, 70, 70, 110)); // Marge à droite

        borderPane.setLeft(imagePane);

        // Partie droite : Formulaire
        VBox formBox = new VBox(15);
        formBox.setId("formBox");
        formBox.setMaxWidth(600);




        // Champ pour ID Document
        Label lblDoc= new Label("ID Livre ou Magazine :");
        TextField txtDocumentId = new TextField();
        txtDocumentId.setPromptText("ID Document");
        lblDoc.setPrefWidth(600);






        // Bouton d'envoi
        HBox buttonBox = new HBox(10);
        Button submitButton = new Button("Supprimer");
        submitButton.setOnAction(e -> {
            try {
                // Récupérer l'ID du document à supprimer
                int documentId = Integer.parseInt(txtDocumentId.getText().trim());

                // Appeler la méthode du service pour supprimer le document
                documentService.supprimerDocument(documentId);

                // Afficher un message de confirmation après suppression
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText("Document supprimé");
                alert.setContentText("Le document a été supprimé avec succès.");
                alert.showAndWait();

            } catch (NumberFormatException ex) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("ID Document invalide");
                alert.setContentText("Veuillez entrer un ID valide.");
                alert.showAndWait();
            }
        });
        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> {

            mainController.handleDocuments(primaryStage);
        });

        formBox.getChildren().addAll(lblDoc,txtDocumentId, submitButton,backButton);
        borderPane.setCenter(formBox);


        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setTitle("Supprimer Document");
        scene.getStylesheets().add("file:src/view/add.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
