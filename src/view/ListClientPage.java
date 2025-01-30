package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Client;
import service.IClientService;

import java.util.List;

public class ListClientPage {

    private IClientService clientService;

    // Constructeur qui prend le service client
    public ListClientPage(IClientService clientService) {
        this.clientService = clientService;
    }

    // Méthode pour afficher les clients dans la fenêtre
    public void display(Stage primaryStage) {
        // Créer une TableView pour afficher les clients
        TableView<Client> tableView = new TableView<>();

        // Créer des colonnes pour afficher les propriétés des clients
        TableColumn<Client, String> columnNom = new TableColumn<>("Nom");
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Client, String> columnEmail = new TableColumn<>("Email");
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Client, String> columnTelephone = new TableColumn<>("Téléphone");
        columnTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        TableColumn<Client, String> columnType = new TableColumn<>("Type");
        columnType.setCellValueFactory(new PropertyValueFactory<>("typeClient"));

        // Ajouter les colonnes à la TableView
        tableView.getColumns().add(columnNom);
        tableView.getColumns().add(columnEmail);
        tableView.getColumns().add(columnTelephone);
        tableView.getColumns().add(columnType);

        // Récupérer les clients à partir du service
        List<Client> clients = clientService.recupererTousLesClients();

        // Créer une ObservableList avec la liste des clients
        ObservableList<Client> observableClients = FXCollections.observableArrayList(clients);

        // Lier la liste observable à la TableView
        tableView.setItems(observableClients);

        // Créer et afficher la scène
        primaryStage.setTitle("Liste des Clients");
        primaryStage.setScene(new Scene(tableView, 600, 400));
        primaryStage.show();
    }
}
