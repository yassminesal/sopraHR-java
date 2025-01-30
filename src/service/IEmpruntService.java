package service;

import model.Emprunt;

import java.util.List;

public interface IEmpruntService {


    void ajouterEmprunt(Emprunt emprunt);


    void supprimerEmprunt(int empruntId);

    List<Emprunt> afficherTousLesEmprunts();

    void emprunterDocument(int clientId, int documentId);
}
