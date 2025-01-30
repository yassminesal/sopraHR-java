package view;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import model.Document;
import model.Livre;
import model.Magazine;
import service.impl.DocumentServiceImpl;

public class UpdateDocumentPage {

    private DocumentServiceImpl documentService = new DocumentServiceImpl();
    private MainController mainController;

    // Initialisation du service
    public UpdateDocumentPage(MainController mainController) {
        this.mainController = mainController;
    }

    public void display(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        VBox headerBox = new VBox();
        headerBox.setId("header");
        Text label = new Text("Mettre à jour Document");
        label.setId("headerText");
        headerBox.getChildren().add(label);
        borderPane.setTop(headerBox);

        ImageView imageView = new ImageView(new Image("file:src/resources/update.png"));
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);

        StackPane imagePane = new StackPane();
        imagePane.getChildren().add(imageView);
        StackPane.setMargin(imageView, new Insets(0, 70, 70, 110)); // Marge à droite
        borderPane.setLeft(imagePane);

        // Créer une VBox pour disposer les lignes de boutons
        VBox formBox = new VBox(15);
        formBox.setId("formBox");
        formBox.setMaxWidth(500);

        HBox hboxId = new HBox(10);
        Label lblId = new Label("Id Document:");
        TextField txtDocumentId = new TextField();
        txtDocumentId.setPromptText("ID du Document");
        txtDocumentId.setPrefWidth(700);
        hboxId.getChildren().addAll(lblId, txtDocumentId);

        HBox hboxTitre = new HBox(10);
        Label lblTitre = new Label("Titre:");
        TextField txtTitre = new TextField();
        txtTitre.setPromptText("Titre");
        txtTitre.setPrefWidth(700);
        hboxTitre.getChildren().addAll(lblTitre, txtTitre);

        HBox hboxDocumentType = new HBox(10);
        Label lblDocumentType = new Label("Type Document:");
        ComboBox<String> documentTypeCombo = new ComboBox<>();
        documentTypeCombo.getItems().addAll("Magazine", "Livre");
        documentTypeCombo.setPromptText("Type Document");
        documentTypeCombo.setPrefWidth(500);
        hboxDocumentType.getChildren().addAll(lblDocumentType, documentTypeCombo);

        HBox hboxPerType = new HBox(10);
        Label lblPerType = new Label("Périodicité:");
        ComboBox<String> periodiciteCombo = new ComboBox<>();
        periodiciteCombo.getItems().addAll("Annuel", "Mensuel", "Trimestriel");
        periodiciteCombo.setPromptText("Périodicité");
        periodiciteCombo.setPrefWidth(500); // Agrandir la largeur du ComboBox
        hboxPerType.getChildren().addAll(lblPerType, periodiciteCombo);

        HBox hboxAuteur = new HBox(10);
        Label lblAuteur = new Label("Auteur:");
        TextField Auteur = new TextField();
        Auteur.setPromptText("Nom de l'Auteur");
        txtTitre.setPrefWidth(700); // Agrandir la largeur du TextField
        hboxAuteur.getChildren().addAll(lblAuteur, Auteur);



        // Champ pour la disponibilité
        HBox hboxDispo = new HBox(10);
        Label lblDispo = new Label("Disponible:");
        ComboBox<String> dispoCombo = new ComboBox<>();
        dispoCombo.getItems().addAll("Oui", "Non");
        dispoCombo.setPromptText("Disponible");
        dispoCombo.setPrefWidth(500); // Agrandir la largeur du ComboBox
        hboxDispo.getChildren().addAll(lblDispo, dispoCombo);


        // Hide/Show conditionnel pour Periodicité ou Auteur
        documentTypeCombo.setOnAction(e -> {
            String selectedType = documentTypeCombo.getValue();
            if (selectedType != null) {
                if (selectedType.equals("Magazine")) {
                    periodiciteCombo.setVisible(true);
                    Auteur.setVisible(false);
                } else {
                    periodiciteCombo.setVisible(false);
                    Auteur.setVisible(true);
                }
            }
        });

        // Bouton d'envoi
        Button submitButton = new Button("Mettre à jour");
        submitButton.setOnAction(e -> {
            // Récupération des données saisies
            String idText = txtDocumentId.getText();
            String title = txtTitre.getText();
            String documentType = documentTypeCombo.getValue();
            boolean available = dispoCombo.getValue() != null && dispoCombo.getValue().equals("Oui");
            Document documentToUpdate = null;

            // Vérification de l'ID
            if (idText.isEmpty() || !idText.matches("\\d+")) {
                Error("Veuillez entrer un ID valide.");
                return;
            }
            int documentId = Integer.parseInt(idText);

            // Vérification du type de document
            if (documentType == null) {
                Error("Veuillez choisir un type de document.");
                return;
            }

            // Création du document à mettre à jour
            if (documentType.equals("Magazine")) {
                String periodicite = periodiciteCombo.getValue();
                if (periodicite == null) {
                    Error("Veuillez choisir la périodicité.");
                    return;
                }
                Magazine.Periodicite periodicityEnum = Magazine.Periodicite.valueOf(periodicite.toUpperCase());
                documentToUpdate = new Magazine(documentId, title, periodicityEnum, available);
            } else if (documentType.equals("Livre")) {
                String author = Auteur.getText();
                if (author.isEmpty()) {
                    Error("Veuillez entrer le nom de l'auteur.");
                    return;
                }
                documentToUpdate = new Livre(documentId, title, author, available);
            }

            // Appel à la méthode pour mettre à jour le document
            if (documentToUpdate != null) {
                documentService.mettreAJourDocument(documentToUpdate);
                showSuccess("Document mis à jour avec succès !");
            }
        });

        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> {
            // Appel de la méthode handleDocuments du MainController pour revenir à la page DocumentPage
            mainController.handleDocuments(primaryStage);  // Passer le Stage pour garder la même fenêtre
        });

        formBox.getChildren().addAll(
                hboxId,
                hboxTitre,
                hboxDocumentType,
                hboxPerType,
                hboxAuteur,
                hboxDispo,
                submitButton,
                backButton
        );
        borderPane.setCenter(formBox);

        // Créer la scène et afficher
        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setTitle("Mettre à jour un Document");
        scene.getStylesheets().add("file:src/view/add.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour afficher un message d'erreur
    private void Error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher un message de succès
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
