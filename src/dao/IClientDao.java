package dao;

import model.Client;

import java.util.List;

public interface IClientDao {

        void ajouterClient(Client client);

        void supprimerClient(int clientId);

        void mettreAJourClient(Client client);

        List<Client> recupererTousLesClients();

        Client trouverClientParId(int clientId);


        boolean clientExists(int clientId);


}
