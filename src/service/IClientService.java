package service;

import model.Client;

import java.util.List;

public interface IClientService {
    void ajouterClient(Client client);
    void supprimerClient(int clientId);
    void mettreAJourClient(Client client);
    List<Client> recupererTousLesClients();
    Client trouverClientParId(int clientId);

    // Méthode pour vérifier si un client existe
    boolean clientExists(int clientId);
}
