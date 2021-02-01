package App.Controllers;

import App.Objects.Articolo;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class GestisciArticoliController {

    /**********Attributi**********/

    Articolo articolo;

    /**********Metodi**********/

    /**********Costruttori**********/

    public GestisciArticoliController() {
        this.articolo = new Articolo();
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Articolo> getArticoliRistorante(int ristoranteId) throws SQLException {
        return this.articolo.getArticoliDB(ristoranteId);
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/

}
