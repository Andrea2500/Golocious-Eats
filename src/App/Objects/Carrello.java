package App.Objects;

import App.DAO.CarrelloDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Carrello {

    CarrelloDAO carrelloDAO;
    ObservableList<Articolo> articoliCarrello;

    public Carrello() {
        this.carrelloDAO = carrelloDAO;
        this.articoliCarrello = FXCollections.observableArrayList();
    }

    public ObservableList<Articolo> getArticoliCarrello() {
        return articoliCarrello;
    }

    public void setArticoliCarrello(ObservableList<Articolo> articoliCarrello) {
        this.articoliCarrello = articoliCarrello;
    }

    public void aggiungiAlCarrello(Articolo articolo){
        this.articoliCarrello.add(articolo);
        //this.carrelloDAO.sincronizzaDB(this.articoliCarrello);
    }

}



