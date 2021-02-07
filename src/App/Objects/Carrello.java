package App.Objects;

import App.DAO.CarrelloDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Carrello {

    /**********Attributi**********/

    int carrelloId;
    int ristoranteId;
    ObservableList<Articolo> articoli;
    CarrelloDAO carrelloDAO;

    /**********Metodi**********/

    /**********Costruttori**********/

    public Carrello() throws SQLException {
        this.carrelloDAO = new CarrelloDAO();
        this.carrelloId = this.carrelloDAO.getCarrelloCliente();
        this.articoli = this.carrelloDAO.getArticoliNelCarrelloCliente(this.carrelloId);
        this.ristoranteId = this.carrelloDAO.getRistoranteId(this.carrelloId);
    }

    /**********Getter e Setter**********/

    public int getCarrelloId() {
        return carrelloId;
    }

    public void setCarrelloId(int carrelloId) {
        this.carrelloId = carrelloId;
    }

    public int getRistoranteId() {
        return ristoranteId;
    }

    public void setRistoranteId(int ristoranteId) {
        this.ristoranteId = ristoranteId;
    }

    public ObservableList<Articolo> getArticoli() {
        return articoli;
    }

    public void setArticoli(ObservableList<Articolo> articoli) {
        this.articoli = articoli;
    }

    /**********Metodi di funzionalità**********/

    public void aggiungiAlCarrello(Articolo articolo) throws SQLException {
        this.articoli.add(articolo);
        this.carrelloId = this.carrelloDAO.aggiornaRistoranteId(this.ristoranteId,this.carrelloId);
        this.carrelloDAO.sincronizzaDB(this.articoli,this.carrelloId);
    }

    public void eliminaDalCarrello(int indice) throws SQLException {
        this.articoli.remove(indice);
        this.carrelloId = this.carrelloDAO.aggiornaRistoranteId(this.ristoranteId,this.carrelloId);
        this.carrelloDAO.sincronizzaDB(this.articoli,this.carrelloId);
    }

    /**********Metodi di supporto**********/

    public void svuotaCarrello() throws SQLException {
        this.articoli.clear();
        this.carrelloId = this.carrelloDAO.aggiornaRistoranteId(this.ristoranteId,this.carrelloId);
        this.carrelloDAO.sincronizzaDB(this.articoli,this.carrelloId);
    }

}



