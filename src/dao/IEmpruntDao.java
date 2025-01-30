package dao;

import model.Emprunt;
import java.util.List;

public interface IEmpruntDao {
    void ajouterEmprunt(Emprunt emprunt);
    void supprimerEmprunt(int empruntId);
    List<Emprunt> recupererTousLesEmprunts();

    // Ajouter cette méthode
    boolean empruntExiste(int empruntId);  // Retourne true si l'emprunt existe, false sinon
}
