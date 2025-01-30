package dao;

import model.Document;
import java.util.List;

public interface IDocumentDao {

        void ajouterDocument(Document document);

        void supprimerDocument(int documentId);

        void mettreAJourDocument(Document document);

        List<Document> recupererTousLesDocuments();

        Document trouverDocumentParId(int documentId);

        // Nouvelle méthode pour vérifier si un document existe en fonction de son ID
        boolean documentExists(int documentId);
}
