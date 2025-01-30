package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import service.impl.EmpruntServiceImpl;
import model.Emprunt;
import controller.MainController;

import java.util.List;

public class ListEmpruntPage {

    private EmpruntServiceImpl empruntService = new EmpruntServiceImpl();
    private MainController mainController;

    public ListEmpruntPage(MainController mainController) {
        this.mainController = mainController;
    }

    public void display(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setId("root");


        Text headerLabel = new Text("Liste des Emprunts");
        headerLabel.setFont(Font.font("Arial", 24));
        headerLabel.setId("headerText");
        borderPane.setTop(headerLabel);
        BorderPane.setAlignment(headerLabel, Pos.CENTER);


        TableView<Emprunt> tableView = new TableView<>();
        tableView.setId("tableView");


        TableColumn<Emprunt, String> colEmpruntId = new TableColumn<>("ID Emprunt");
        colEmpruntId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Emprunt, String> colClientId = new TableColumn<>("ID Client");
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));

        TableColumn<Emprunt, String> colDocumentId = new TableColumn<>("ID Document");
        colDocumentId.setCellValueFactory(new PropertyValueFactory<>("documentId"));

        TableColumn<Emprunt, String> colDateEmprunt = new TableColumn<>("Date Emprunt");
        colDateEmprunt.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));

        TableColumn<Emprunt, String> colDateRetour = new TableColumn<>("Date Retour");
        colDateRetour.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));

        tableView.getColumns().addAll(colEmpruntId, colClientId, colDocumentId, colDateEmprunt, colDateRetour);


        List<Emprunt> emprunts = empruntService.afficherTousLesEmprunts();


        tableView.getItems().addAll(emprunts);

        borderPane.setCenter(tableView);


        Button backButton = new Button("Retour");
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
            mainController.handleEmprunts(primaryStage);
        });

        HBox hbox = new HBox(backButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));
        borderPane.setBottom(hbox);


        Scene scene = new Scene(borderPane, 600, 400);
        scene.getStylesheets().add("file:src/view/list.css");
        primaryStage.setTitle("Liste des Emprunts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
