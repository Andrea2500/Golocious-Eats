package App.Controllers;

import App.Objects.Articolo;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class GestisciArticoliController {

    /**********Attributi**********/

    Articolo articolo;
    Ristorante ristorante;

    /**********Metodi**********/

    /**********Costruttori**********/

    public GestisciArticoliController() {
        this.articolo = new Articolo();
        this.ristorante = new Ristorante();
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Articolo> getArticoliRistorante(int ristoranteId) throws SQLException {
        return this.articolo.getArticoliDB(ristoranteId);
    }

    public String switchDisponibilita(boolean toggle,int ristoranteid,int articoloid) throws SQLException {
        return this.articolo.switchDisponibilita(toggle,ristoranteid,articoloid);
    }

    public String eliminaDaMenu(Ristorante ristorante, int articoloid) throws SQLException {
       return ristorante.eliminaDaMenuDB(articoloid);
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/

}
