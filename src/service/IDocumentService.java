package service;

import model.Document;

import java.util.List;

public interface IDocumentService {
    void ajouterDocument(Document document);
    void supprimerDocument(int documentId);
    void mettreAJourDocument(Document document);
    List<Document> recupererTousLesDocuments();
    Document trouverDocumentParId(int documentId);
    List<String> afficherTitresLivresEtMagazines();
    int afficherNombreLivresEtMagazines();
}
