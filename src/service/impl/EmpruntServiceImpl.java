package service.impl;

import dao.IDocumentDao;
import dao.IEmpruntDao;
import dao.impl.DocumentDaoImpl;
import dao.impl.EmpruntDaoImpl;
import model.Document;
import model.Emprunt;
import service.IEmpruntService;
import java.time.LocalDate;
import java.util.List;

public class EmpruntServiceImpl implements IEmpruntService {

    private final IEmpruntDao empruntDao;
    private final IDocumentDao documentDao;

    public EmpruntServiceImpl() {
        this.empruntDao = new EmpruntDaoImpl();
        this.documentDao = new DocumentDaoImpl();
    }

    @Override
    public void ajouterEmprunt(Emprunt emprunt) {
        empruntDao.ajouterEmprunt(emprunt);  // Ajouter un emprunt dans la base
    }

    @Override
    public void supprimerEmprunt(int empruntId) {
        if (empruntDao.empruntExiste(empruntId)) {  // Vérifier si l'emprunt existe
            empruntDao.supprimerEmprunt(empruntId);  // Supprimer l'emprunt si existe
            System.out.println("Emprunt supprimé avec succès.");
        } else {
            System.out.println("Aucun emprunt trouvé avec cet ID.");
        }
    }

    @Override
    public List<Emprunt> afficherTousLesEmprunts() {
        return empruntDao.recupererTousLesEmprunts();
    }

    @Override
    public void emprunterDocument(int clientId, int documentId) {
        Document document = documentDao.trouverDocumentParId(documentId);
        if (document == null) {
            System.out.println("Document introuvable " + documentId);
            return;
        }

        if (!document.isDisponible()) {
            System.out.println("Le document '" + document.getTitre() + "' n'est pas disponible.");
            return;
        }

        Emprunt emprunt = new Emprunt();
        emprunt.setClientId(clientId);
        emprunt.setDocumentId(documentId);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetour(LocalDate.now().plusWeeks(2));

        empruntDao.ajouterEmprunt(emprunt);
        document.setDisponible(false);
        documentDao.mettreAJourDocument(document);

        System.out.println("Document '" + document.getTitre() + "' emprunté avec succès.");
    }
}
