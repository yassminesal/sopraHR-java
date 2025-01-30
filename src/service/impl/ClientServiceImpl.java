package service.impl;

import dao.IClientDao;
import dao.impl.ClientDaoImpl;
import model.Client;
import service.IClientService;

import java.util.List;

public class ClientServiceImpl implements IClientService {

    private IClientDao clientDao;

    public ClientServiceImpl() {
        this.clientDao = new ClientDaoImpl();
    }

    @Override
    public void ajouterClient(Client client) {
        clientDao.ajouterClient(client);
    }

    @Override
    public void supprimerClient(int clientId) {
        // Vérifier si le client existe avant de le supprimer
        if (clientDao.clientExists(clientId)) {
            clientDao.supprimerClient(clientId);
            System.out.println("Client supprimé avec succès.");
        } else {
            System.out.println("Aucun client trouvé avec cet ID.");
        }
    }

    public void mettreAJourClient(Client client) {
        // Vérifier que l'ID du client n'est pas nul ou 0 (cela signifie une mise à jour existante)
        if (client.getId() == 0) {
            System.out.println("Erreur: L'ID du client est invalide.");
            return;
        }

        // Appeler la méthode DAO pour effectuer la mise à jour
        clientDao.mettreAJourClient(client);
    }

    public List<Client> recupererTousLesClients() {
        // Appel à la méthode DAO pour récupérer tous les clients
        return clientDao.recupererTousLesClients();
    }

    @Override
    public Client trouverClientParId(int clientId) {
        return clientDao.trouverClientParId(clientId);
    }

    // Implémentation de la méthode clientExists
    @Override
    public boolean clientExists(int clientId) {
        return clientDao.clientExists(clientId);
    }
}
