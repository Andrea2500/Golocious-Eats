package App.Objects;

import App.DAO.CarrelloDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Carrello {

    CarrelloDAO carrelloDAO;
    int carrelloId;
    int ristoranteId;
    ObservableList<Articolo> articoliCarrello;

    public Carrello() throws SQLException {
        this.carrelloDAO = new CarrelloDAO();
        this.articoliCarrello = FXCollections.observableArrayList();
        this.carrelloId = this.carrelloDAO.getCarrelloCliente();
    }

    public ObservableList<Articolo> getArticoliCarrello() {
        return articoliCarrello;
    }

    public void setArticoliCarrello(ObservableList<Articolo> articoliCarrello) {
        this.articoliCarrello = articoliCarrello;
    }

    public int getRistoranteId() {
        return ristoranteId;
    }

    public void setRistoranteId(int ristoranteId) {
        this.ristoranteId = ristoranteId;
    }

    public int getCarrelloId() {
        return carrelloId;
    }

    public void setCarrelloId(int carrelloId) {
        this.carrelloId = carrelloId;
    }

    public void aggiungiAlCarrello(Articolo articolo) throws SQLException {
        this.articoliCarrello.add(articolo);
        this.carrelloId = this.carrelloDAO.updateRistoranteId(this.ristoranteId,this.carrelloId);
        this.carrelloDAO.sincronizzaDB(this.articoliCarrello,this.carrelloId);
    }

    public void pulisciCarrello(){
        this.articoliCarrello.clear();
    }

}



