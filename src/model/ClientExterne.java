package model;

public class ClientExterne extends Client {
    public enum Abonnement {
        MENSUEL,
        TRIMESTRIEL,
        ANNUEL;
    }
        private Abonnement abonnement;

        public ClientExterne(int id, String nom, String email, String telephone, Abonnement abonnement) {
            super(id, nom, email, telephone,"EXTERNE");
            this.abonnement = abonnement;
        }

        public Abonnement getAbonnement() {
            return abonnement;
        }

        public void setAbonnement(Abonnement abonnement) {
            this.abonnement = abonnement;
        }
    @Override
    public String toString() {
        return super.toString() + ", Type: EXTERNE, Abonnement: " + abonnement;
    }
    }



