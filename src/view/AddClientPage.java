package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.impl.ClientServiceImpl;
import model.Client;
import model.ClientExterne;
import model.ClientInterne;
import javafx.geometry.Insets;

public class AddClientPage {

    private ClientServiceImpl clientService;

    public AddClientPage(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    public void display(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();



        VBox headerBox = new VBox();
        headerBox.setId("header");
        Text Label = new Text("Ajouter Client");
        Label.setId("headerText");
        headerBox.getChildren().add(Label);
        borderPane.setTop(headerBox);


        ImageView imageView = new ImageView(new Image("file:src/resources/add.png"));
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        StackPane imagePane = new StackPane();
        imagePane.getChildren().add(imageView);
        StackPane.setMargin(imageView, new Insets(0, 70, 70, 110));

        borderPane.setLeft(imagePane);


        VBox formBox = new VBox(15);
        formBox.setId("formBox");
        formBox.setMaxWidth(500);


        HBox hboxName = new HBox(10);
        Label lblName = new Label("Nom:");
        TextField txtName = new TextField();
        txtName.setPromptText("Nom");
        txtName.setPrefWidth(600);
        hboxName.getChildren().addAll(lblName, txtName);

        HBox hboxEmail = new HBox(10);
        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.setPrefWidth(600);
        hboxEmail.getChildren().addAll(lblEmail, txtEmail);

        HBox hboxPhone = new HBox(10);
        Label lblPhone = new Label("Téléphone:");
        TextField txtPhone = new TextField();
        txtPhone.setPromptText("Téléphone");
        txtPhone.setPrefWidth(500);
        hboxPhone.getChildren().addAll(lblPhone, txtPhone);

        HBox hboxClientType = new HBox(10);
        Label lblClientType = new Label("Type Client:");
        ComboBox<String> clientTypeCombo = new ComboBox<>();
        clientTypeCombo.getItems().addAll("Interne", "Externe");
        clientTypeCombo.setPromptText("Type Client");
        clientTypeCombo.setPrefWidth(500);
        hboxClientType.getChildren().addAll(lblClientType, clientTypeCombo);

        HBox hboxDepartment = new HBox(10);
        Label lblDepartment = new Label("Département:");
        ComboBox<String> departmentCombo = new ComboBox<>();
        departmentCombo.getItems().addAll("A", "B", "C");
        departmentCombo.setPromptText("Département");
        departmentCombo.setPrefWidth(500);
        hboxDepartment.getChildren().addAll(lblDepartment, departmentCombo);

        HBox hboxAbonnement = new HBox(10);
        Label lblAbonnement = new Label("Abonnement:");
        ComboBox<String> AbonnementCombo = new ComboBox<>();
        AbonnementCombo.getItems().addAll(
                ClientExterne.Abonnement.MENSUEL.name(),
                ClientExterne.Abonnement.TRIMESTRIEL.name(),
                ClientExterne.Abonnement.ANNUEL.name()
        );
        AbonnementCombo.setPromptText("Abonnement");
        AbonnementCombo.setPrefWidth(500);
        hboxAbonnement.getChildren().addAll(lblAbonnement, AbonnementCombo);


        clientTypeCombo.setOnAction(e -> {
            String clientType = clientTypeCombo.getValue();
            if (clientType != null) {
                if (clientType.equals("Interne")) {
                    departmentCombo.setVisible(true);
                    AbonnementCombo.setVisible(false);
                } else {
                    departmentCombo.setVisible(false);
                    AbonnementCombo.setVisible(true);
                }
            }
        });


        HBox buttonBox = new HBox(10);
        Button submitButton = new Button("Ajouter");
        submitButton.setOnAction(e -> {

            String nom = txtName.getText();
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            String clientType = clientTypeCombo.getValue();
            Client client = null;


            if (clientType != null) {
                if (clientType.equals("Interne")) {
                    String department = departmentCombo.getValue();
                    if (department != null) {
                        client = new ClientInterne(0, nom, email, phone, ClientInterne.Departement.valueOf(department));
                    } else {
                        Alert("Erreur", "Le département doit être sélectionné .");
                        return;
                    }
                } else if (clientType.equals("Externe")) {
                    String subscription = AbonnementCombo.getValue();
                    if (subscription != null) {
                        ClientExterne.Abonnement abonnement = ClientExterne.Abonnement.valueOf(subscription);
                        client = new ClientExterne(0, nom, email, phone, abonnement);
                    } else {
                        Alert("Erreur", "L'abonnement doit être sélectionné .");
                        return;
                    }
                }

                if (client != null) {
                    clientService.ajouterClient(client);
                    Alert("Succès", "Client ajouté avec succès!");
                }
            } else {
                Alert("Erreur", "Le type de client doit être sélectionné.");
            }
        });


        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> navigateToClientPage(primaryStage));


        buttonBox.getChildren().addAll(submitButton, backButton);


        formBox.getChildren().addAll(hboxName, hboxEmail, hboxPhone, hboxClientType, hboxDepartment, hboxAbonnement, buttonBox);

        borderPane.setCenter(formBox);


        Scene scene = new Scene(borderPane, 600, 400);
        scene.getStylesheets().add("file:src/view/add.css");
        primaryStage.setTitle("Ajouter Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void navigateToClientPage(Stage primaryStage) {
        ClientPage clientPage = new ClientPage();
        clientPage.display(primaryStage);
    }


    private void Alert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
