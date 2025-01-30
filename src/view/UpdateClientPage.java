package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.impl.ClientServiceImpl;
import model.Client;
import model.ClientInterne;
import model.ClientExterne;

public class UpdateClientPage {

    private ClientServiceImpl clientService; // Déclaration de clientService

    // Constructeur qui prend le service pour la mise à jour
    public UpdateClientPage(ClientServiceImpl clientService) {
        this.clientService = clientService; // Initialisation de clientService
    }

    // La méthode display() pour afficher la page de mise à jour d'un client
    public void display(Stage primaryStage) {
        // Créer un BorderPane pour structurer la page
        BorderPane borderPane = new BorderPane();

        HBox headerBox = new HBox();
        headerBox.setStyle("-fx-background-color: #4CAF50; -fx-padding: 17px;");  // Fond vert et padding
        Label titleLabel = new Label("Mettre à jour Client");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-text-alignment: center;");
        headerBox.getChildren().add(titleLabel);
        headerBox.setAlignment(Pos.CENTER); // Centrer le titre dans la zone

        borderPane.setTop(headerBox);

        // Partie gauche : Image agrandie et déplacée vers la droite
        ImageView imageView = new ImageView(new Image("file:src/resources/update.png"));
        imageView.setFitHeight(300);  // Agrandir la hauteur de l'image
        imageView.setFitWidth(300);   // Agrandir la largeur de l'image
        imageView.setPreserveRatio(true);  // Maintenir les proportions
        imageView.setStyle("-fx-padding: 20px;");

        // Créer un StackPane pour l'image et appliquer une marge à droite
        StackPane imagePane = new StackPane();
        imagePane.getChildren().add(imageView);
        StackPane.setMargin(imageView, new Insets(0, 70, 70, 110)); // Marge à droite

        borderPane.setLeft(imagePane);  // Ajouter l'image au BorderPane à gauche

        // Partie droite : Formulaire
        VBox formBox = new VBox(15);
        formBox.setId("formBox");
        formBox.setMaxWidth(500);

        // Ligne 1 : ID Client
        HBox idBox = new HBox(10);
        Label lblClientId = new Label("ID Client :");
        TextField txtId = new TextField();
        txtId.setPromptText("ID");
        idBox.getChildren().addAll(lblClientId, txtId);
        idBox.setAlignment(Pos.CENTER);

        // Ligne 2 : Nom
        HBox nameBox = new HBox(10);
        Label lblNom = new Label("Nom :");
        TextField txtName = new TextField();
        txtName.setPromptText("Nom :");
        txtName.setPrefWidth(400); // Définir une largeur plus large pour les champs
        nameBox.getChildren().addAll(lblNom, txtName);
        nameBox.setAlignment(Pos.CENTER);  // Centrer l'HBox contenant le label et le champ

        // Ligne 3 : Email
        HBox emailBox = new HBox(10);
        Label lblEmail = new Label("Email :");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.setPrefWidth(400); // Définir une largeur plus large pour les champs
        emailBox.getChildren().addAll(lblEmail, txtEmail);
        emailBox.setAlignment(Pos.CENTER);  // Centrer l'HBox contenant le label et le champ

        // Ligne 4 : Téléphone
        HBox telBox = new HBox(10);
        Label lbltel = new Label("Téléphone :");
        TextField txtPhone = new TextField();
        txtPhone.setPromptText("Téléphone");
        txtPhone.setPrefWidth(400); // Définir une largeur plus large pour les champs
        telBox.getChildren().addAll(lbltel, txtPhone);
        telBox.setAlignment(Pos.CENTER);  // Centrer l'HBox contenant le label et le champ

        // Ligne 5 : Type Client
        HBox clientTypeBox = new HBox(10);
        Label lblClientType = new Label("Type Client:");
        ComboBox<String> clientTypeCombo = new ComboBox<>();
        clientTypeCombo.getItems().addAll("Interne", "Externe");
        clientTypeCombo.setPromptText("Type Client");
        clientTypeBox.getChildren().addAll(lblClientType, clientTypeCombo);
        clientTypeBox.setAlignment(Pos.CENTER);  // Centrer l'HBox contenant le label et le champ

        // Ligne 6 : Département ou Abonnement
        HBox conditionalBox = new HBox(10);

        // Label pour Département
        Label lblDepartment = new Label("Département :");
        ComboBox<String> departmentCombo = new ComboBox<>();
        departmentCombo.getItems().addAll("A", "B", "C");
        departmentCombo.setPromptText("Département");

        // Label pour Abonnement
        Label lblSubscription = new Label("Abonnement :");
        ComboBox<String> subscriptionCombo = new ComboBox<>();
        subscriptionCombo.getItems().addAll("MENSUEL", "TRIMESTRIEL", "ANNUEL");
        subscriptionCombo.setPromptText("Abonnement");

        // Pas de logique conditionnelle ici, on affiche les deux ComboBox
        departmentCombo.setVisible(true);  // Départements visibles
        subscriptionCombo.setVisible(true); // Abonnements visibles

        conditionalBox.getChildren().addAll(lblDepartment, departmentCombo, lblSubscription, subscriptionCombo);
        conditionalBox.setAlignment(Pos.CENTER);  // Centrer l'HBox contenant les ComboBox

        // Ajouter les boutons à l'HBox
        HBox buttonBox = new HBox(10);  // 10px d'espacement entre les boutons
        buttonBox.setAlignment(Pos.CENTER);  // Centrer les boutons

        // Bouton d'envoi pour mettre à jour le client
        Button submitButton = new Button("Mettre à jour");
        submitButton.setOnAction(e -> {
            // Récupérer les valeurs des champs
            String idStr = txtId.getText();
            String name = txtName.getText();
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            String clientType = clientTypeCombo.getValue();
            String department = departmentCombo.getValue();
            String subscription = subscriptionCombo.getValue();

            // Valider que l'ID est un nombre et qu'il n'est pas vide
            int id = 0;
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException ex) {
                    System.out.println("L'ID doit être un nombre entier.");
                    return; // Retourner si l'ID est invalide
                }
            }

            // Créer un objet Client approprié en fonction du type sélectionné
            Client client = null;

            if (clientType != null) {
                if (clientType.equals("Interne") && department != null) {
                    // Convertir la chaîne de département en type enum Departement
                    ClientInterne.Departement dept = ClientInterne.Departement.valueOf(department);
                    client = new ClientInterne(id, name, email, phone, dept); // Utilisation de l'ID
                } else if (clientType.equals("Externe") && subscription != null) {
                    // Convertir la chaîne d'abonnement en type enum Abonnement
                    ClientExterne.Abonnement ab = ClientExterne.Abonnement.valueOf(subscription);
                    client = new ClientExterne(id, name, email, phone, ab); // Utilisation de l'ID
                }
            }

            // Si le client est bien créé, mettre à jour via le service
            if (client != null) {
                clientService.mettreAJourClient(client);
                System.out.println("Client mis à jour : " + name);
            } else {
                System.out.println("Erreur : veuillez remplir tous les champs nécessaires.");
            }
        });

        // Bouton pour revenir à la page de gestion des clients
        Button backButton = new Button("Retour ");
        backButton.setOnAction(e -> navigateToClientPage(primaryStage)); // Navigation vers la page ClientPage

        // Ajouter les boutons à l'HBox
        buttonBox.getChildren().addAll(submitButton, backButton);

        // Ajouter tous les éléments au formulaire
        formBox.getChildren().addAll(idBox, nameBox, emailBox, telBox, clientTypeBox, conditionalBox, buttonBox);

        // Ajouter le formulaire dans le StackPane pour le centrer


        // Placer le StackPane dans le centre du BorderPane
        borderPane.setCenter(formBox);

        // Créer la scène et la fenêtre
        Scene scene = new Scene(borderPane, 600, 400);
        scene.getStylesheets().add("file:src/view/add.css");
        primaryStage.setTitle("Mettre à jour un Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode de navigation vers la page des clients
    private void navigateToClientPage(Stage primaryStage) {
        // Vérifiez que la classe ClientPage existe et que la méthode display() est implémentée
        ClientPage clientPage = new ClientPage();
        clientPage.display(primaryStage); // Afficher la page de gestion des clients
    }
}
