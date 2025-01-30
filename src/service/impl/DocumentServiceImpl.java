package service.impl;

import dao.IDocumentDao;
import dao.impl.DocumentDaoImpl;
import model.Document;
import service.IDocumentService;

import java.util.List;
import java.util.ArrayList;

public class DocumentServiceImpl implements IDocumentService {

    private IDocumentDao documentDao;

    public DocumentServiceImpl() {
        this.documentDao = new DocumentDaoImpl();
    }

    @Override
    public void ajouterDocument(Document document) {
        documentDao.ajouterDocument(document);
    }

    @Override
    public void supprimerDocument(int documentId) {
        // Vérifier si le document existe avant de tenter la suppression
        if (documentDao.documentExists(documentId)) {
            documentDao.supprimerDocument(documentId);
            System.out.println("Document supprimé avec succès.");
        } else {
            System.out.println("Aucun document trouvé avec cet ID.");
        }
    }

    @Override
    public void mettreAJourDocument(Document document) {
        documentDao.mettreAJourDocument(document);
    }

    @Override
    public List<Document> recupererTousLesDocuments() {
        return documentDao.recupererTousLesDocuments();
    }

    @Override
    public Document trouverDocumentParId(int documentId) {
        return documentDao.trouverDocumentParId(documentId);
    }

    @Override
    public List<String> afficherTitresLivresEtMagazines() {
        List<Document> documents = recupererTousLesDocuments();
        List<String> titres = new ArrayList<>();

        for (Document document : documents) {
            titres.add(document.getTitre());
        }

        return titres;
    }

    @Override
    public int afficherNombreLivresEtMagazines() {
        List<Document> documents = recupererTousLesDocuments(); // Récupère tous les documents
        return documents.size();
    }
}
