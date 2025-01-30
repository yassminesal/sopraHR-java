package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Livre;
import model.Magazine;

import service.impl.DocumentServiceImpl;
import service.IDocumentService;
import controller.MainController;

public class AddDocumentPage {

    private IDocumentService documentService;
    private MainController mainController;

    public AddDocumentPage(MainController mainController) {
        this.documentService = new DocumentServiceImpl();
        this.mainController = mainController;
    }



    public void display(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();


        VBox headerBox = new VBox();
        headerBox.setId("header");
        Text Label = new Text("Ajouter Document");
        Label.setId("headerText");
        headerBox.getChildren().add(Label);
        borderPane.setTop(headerBox);


        ImageView imageView = new ImageView(new Image("file:src/resources/doc.png"));
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);

        StackPane imagePane = new StackPane();
        imagePane.getChildren().add(imageView);
        StackPane.setMargin(imageView, new Insets(0, 70, 70, 110)); // Marge à droite
        borderPane.setLeft(imagePane);



        VBox formBox = new VBox(15);
        formBox.setId("formBox");
        formBox.setMaxWidth(500);


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
        periodiciteCombo.setPrefWidth(500);
        hboxPerType.getChildren().addAll(lblPerType, periodiciteCombo);

        HBox hboxAuteur = new HBox(10);
        Label lblAuteur = new Label("Auteur:");
        TextField Auteur = new TextField();
        Auteur.setPromptText("Nom de l'Auteur");
        txtTitre.setPrefWidth(700);
        hboxAuteur.getChildren().addAll(lblAuteur, Auteur);




        HBox hboxDispo = new HBox(10);
        Label lblDispo = new Label("Disponible:");
        ComboBox<String> dispoCombo = new ComboBox<>();
        dispoCombo.getItems().addAll("Oui", "Non");
        dispoCombo.setPromptText("Disponible");
        dispoCombo.setPrefWidth(500);
        hboxDispo.getChildren().addAll(lblDispo, dispoCombo);





        documentTypeCombo.setOnAction(e -> {
            if (documentTypeCombo.getValue().equals("Magazine")) {
                periodiciteCombo.setVisible(true);
                Auteur.setVisible(false);
            } else {
                periodiciteCombo.setVisible(false);
                Auteur.setVisible(true);
            }
        });


        HBox buttonBox = new HBox(10);
        Button submitButton = new Button("Ajouter");
        submitButton.setOnAction(e -> {
            String titre =  txtTitre.getText();
            boolean disponible = dispoCombo.getValue().equals("Oui");

            if (documentTypeCombo.getValue().equals("Magazine")) {

                Magazine.Periodicite periodicite = Magazine.Periodicite.valueOf(periodiciteCombo.getValue().toUpperCase());
                Magazine magazine = new Magazine(0, titre, periodicite, disponible);
                documentService.ajouterDocument(magazine);

            } else if (documentTypeCombo.getValue().equals("Livre")) {

                String auteur = Auteur.getText();
                Livre livre = new Livre(0, titre, auteur, disponible);
                documentService.ajouterDocument(livre);
            }


        });
        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> {mainController.handleDocuments(primaryStage);
        });



        buttonBox.getChildren().addAll(submitButton, backButton);

        formBox.getChildren().addAll(hboxTitre,
                hboxDocumentType,
                hboxPerType,
                hboxAuteur,
                hboxDispo,
                buttonBox
        );

        borderPane.setCenter(formBox);


        Scene scene = new Scene(borderPane, 600, 400);
        scene.getStylesheets().add("file:src/view/add.css");
        primaryStage.setTitle("Ajouter Document");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}
