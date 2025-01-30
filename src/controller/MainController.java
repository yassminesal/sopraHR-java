package controller;
import view.AddDocumentPage;
import view.DeleteDocumentPage;
import view.UpdateDocumentPage;
import view.ListDocumentPage;
import view.ClientPage;
import view.DocumentPage;
import view.EmpruntPage;
import view.AddClientPage;
import view.DeleteClientPage;
import view.UpdateClientPage;
import view.ListClientPage;
import view.AddEmpruntPage;
import view.DeleteEmpruntPage;
import view.ListEmpruntPage;
import javafx.stage.Stage;
import service.impl.ClientServiceImpl;
import model.Client;
import service.impl.DocumentServiceImpl;
import service.IDocumentService;

public class MainController {

    private IDocumentService documentService;

    private ClientServiceImpl clientService;

    // Constructeur avec injection de service
    public MainController(ClientServiceImpl clientService) {
        this.clientService = clientService;
        this.documentService = new DocumentServiceImpl();
    }
    // Gérer la navigation vers la gestion des clients
    public void handleClients(Stage primaryStage) {
        ClientPage clientPage = new ClientPage();
        clientPage.display(primaryStage);
    }

    public void handleDocuments(Stage primaryStage) {
        // Passer le contrôleur (this) lors de l'instanciation de DocumentPage
        DocumentPage documentPage = new DocumentPage(this);  // Utilisation de 'this' pour passer le contrôleur
        documentPage.display(primaryStage);
    }

    public void handleAddDocument(Stage primaryStage) {
        // Passer le contrôleur (this) lors de l'instanciation de AddDocumentPage
        AddDocumentPage addDocumentPage = new AddDocumentPage(this);  // Utilisation de 'this' pour passer le contrôleur
        addDocumentPage.display(primaryStage);
    }

    // Gérer la navigation vers la gestion des emprunts
    public void handleEmprunts(Stage primaryStage) {
        EmpruntPage empruntPage = new EmpruntPage(this);
        empruntPage.display(primaryStage);
    }




    // Gérer la navigation vers la page "Ajouter Client"


    public void handleAddClient(Stage primaryStage) {
        // Créer une instance du service ClientServiceImpl
        ClientServiceImpl clientService = new ClientServiceImpl();
        AddClientPage addClientPage = new AddClientPage(clientService); // Passer le service à la page
        addClientPage.display(primaryStage);
    }


    // Gérer la suppression du client
    public boolean deleteClient(int clientId) {
        // Appel à la méthode supprimerClient de ClientServiceImpl
        clientService.supprimerClient(clientId);

        // Vérifier si le client a bien été supprimé
        Client client = clientService.trouverClientParId(clientId);
        return client == null; // Si le client est null, cela signifie qu'il a été supprimé
    }



    // Gérer la navigation vers la page "Supprimer Client"
    public void handleDeleteClient(Stage primaryStage) {
        DeleteClientPage deleteClientPage = new DeleteClientPage(this);
        deleteClientPage.display(primaryStage);
    }

    public void handleUpdateClient(Stage primaryStage) {
        UpdateClientPage updateClientPage = new UpdateClientPage(clientService); // Créer la page de mise à jour avec le service
        updateClientPage.display(primaryStage); // Afficher la page
    }

    // Gérer la navigation vers la page "Lister Clients"
    public void handleListClients(Stage primaryStage) {
        // Créer une instance de ListClientPage et passer le service ClientServiceImpl
        ListClientPage listClientPage = new ListClientPage(clientService); // Passe clientService au constructeur
        listClientPage.display(primaryStage);  // Affiche la page de liste des clients
    }








    public void handleDeleteDocument(Stage primaryStage) {
        DeleteDocumentPage deleteDocumentPage = new DeleteDocumentPage(documentService, this);  // Passer 'this' pour le contrôleur
        deleteDocumentPage.display(primaryStage);
    }


    // Gérer la navigation vers la page "Mettre à jour Document"
    public void handleUpdateDocument(Stage primaryStage) {
        UpdateDocumentPage updateDocumentPage = new UpdateDocumentPage(this);  // Passer le contrôleur
        updateDocumentPage.display(primaryStage);
    }

    // Gérer la navigation vers la page "Lister Documents"
    public void handleListDocuments(Stage primaryStage) {
        ListDocumentPage listDocumentPage = new ListDocumentPage(this);  // Passer le contrôleur
        listDocumentPage.display(primaryStage);
    }


    // Gérer la navigation vers la page "Ajouter Emprunt"
    public void handleAddEmprunt(Stage primaryStage) {
        AddEmpruntPage addEmpruntPage = new AddEmpruntPage(this);
        addEmpruntPage.display(primaryStage);
    }

    // Gérer la navigation vers la page "Supprimer Emprunt"
    public void handleDeleteEmprunt(Stage primaryStage) {
        DeleteEmpruntPage deleteEmpruntPage = new DeleteEmpruntPage(this);
        deleteEmpruntPage.display(primaryStage);
    }

    // Gérer la navigation vers la page "Lister Emprunts"
    public void handleListEmprunts(Stage primaryStage) {
        ListEmpruntPage listEmpruntPage = new ListEmpruntPage(this);
        listEmpruntPage.display(primaryStage);
    }
}
