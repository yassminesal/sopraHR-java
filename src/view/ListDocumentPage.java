package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import service.impl.DocumentServiceImpl;
import model.Document;
import model.Livre;
import model.Magazine;
import javafx.beans.property.SimpleStringProperty;
import controller.MainController;

import java.util.List;

public class ListDocumentPage {

    private DocumentServiceImpl documentService;
    private MainController mainController;  // Controller pour la navigation

    public ListDocumentPage(MainController mainController) {
        this.mainController = mainController;
        this.documentService = new DocumentServiceImpl();
    }

    public void display(Stage primaryStage) {
        // Créer un BorderPane pour structurer la page
        BorderPane borderPane = new BorderPane();

        // Créer le titre
        Text headerLabel = new Text("Liste des Documents");
        headerLabel.setFont(Font.font("Arial", 24));
        headerLabel.setId("headerText");
        borderPane.setTop(headerLabel);
        BorderPane.setAlignment(headerLabel, Pos.CENTER);

        // Créer une TableView pour afficher les documents
        TableView<DocumentWrapper> tableView = new TableView<>();

        // Créer les colonnes pour afficher les informations des documents
        TableColumn<DocumentWrapper, String> colTitre = new TableColumn<>("Titre");
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));

        TableColumn<DocumentWrapper, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<DocumentWrapper, String> colDisponible = new TableColumn<>("Disponible");
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        TableColumn<DocumentWrapper, String> colPeriodOrAuteur = new TableColumn<>("Périodicité/Auteur");
        colPeriodOrAuteur.setCellValueFactory(new PropertyValueFactory<>("periodOrAuteur"));

        // Ajouter les colonnes à la TableView
        tableView.getColumns().addAll(colTitre, colType, colDisponible, colPeriodOrAuteur);

        // Récupérer les documents depuis le service
        List<Document> documents = documentService.recupererTousLesDocuments();

        // Ajouter les documents à la table
        for (Document document : documents) {
            String periodOrAuteur = "";

            // Pour chaque document, déterminer si c'est un Magazine ou un Livre et ajouter les données appropriées
            if (document instanceof Livre) {
                periodOrAuteur = ((Livre) document).getAuteur();  // Récupère l'auteur du livre
            } else if (document instanceof Magazine) {
                periodOrAuteur = ((Magazine) document).getPeriodicite().toString();  // Périodicité du magazine
            }

            tableView.getItems().add(new DocumentWrapper(
                    document.getTitre(),
                    document.getTypeDocument(),
                    document.isDisponible() ? "Oui" : "Non",
                    periodOrAuteur
            ));
        }

        // Placer la TableView dans le centre du BorderPane
        borderPane.setCenter(tableView);

        // Ajouter un bouton retour en bas
        Button backButton = new Button("Retour");
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
            mainController.handleDocuments(primaryStage);  // Retourner à la page des documents
        });

        // Ajouter le bouton dans un HBox en bas de la page
        HBox hbox = new HBox(backButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));
        borderPane.setBottom(hbox);

        // Créer la scène et la fenêtre
        Scene scene = new Scene(borderPane, 600, 400); // Taille de la scène ajustée
        scene.getStylesheets().add("file:src/view/list.css");  // Ajouter le CSS
        primaryStage.setTitle("Liste des Documents");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Classe Wrapper pour lier les données aux cellules de la TableView
    public static class DocumentWrapper {
        private final SimpleStringProperty titre;
        private final SimpleStringProperty type;
        private final SimpleStringProperty disponible;
        private final SimpleStringProperty periodOrAuteur;

        public DocumentWrapper(String titre, String type, String disponible, String periodOrAuteur) {
            this.titre = new SimpleStringProperty(titre);
            this.type = new SimpleStringProperty(type);
            this.disponible = new SimpleStringProperty(disponible);
            this.periodOrAuteur = new SimpleStringProperty(periodOrAuteur);
        }

        public String getTitre() {
            return titre.get();
        }

        public String getType() {
            return type.get();
        }

        public String getDisponible() {
            return disponible.get();
        }

        public String getPeriodOrAuteur() {
            return periodOrAuteur.get();
        }
    }
}
