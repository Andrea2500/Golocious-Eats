package App.Objects;

import App.DAO.CarrelloDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Carrello {

    int carrelloId;
    int ristoranteId;
    ObservableList<Articolo> articoli;
    CarrelloDAO carrelloDAO;

    public Carrello() throws SQLException {
        this.carrelloDAO = new CarrelloDAO();
        this.articoli = FXCollections.observableArrayList();
        this.carrelloId = this.carrelloDAO.getCarrelloCliente();
    }

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

    public void aggiungiAlCarrello(Articolo articolo) throws SQLException {
        this.articoli.add(articolo);
        this.carrelloId = this.carrelloDAO.updateRistoranteId(this.ristoranteId,this.carrelloId);
        this.carrelloDAO.sincronizzaDB(this.articoli,this.carrelloId);
    }

    public void pulisciCarrello(){
        this.articoli.clear();
    }

}



