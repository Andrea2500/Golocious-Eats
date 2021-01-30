package App.Controllers;

import App.Objects.Articolo;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class GestisciArticoloController {
    /**********Attributi**********/
    Articolo articolo;

    /**********Metodi**********/

    /**********Costruttori**********/

    public GestisciArticoloController() {
        this.articolo = new Articolo();
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Articolo> getArticoli(int ristoranteId) throws SQLException {
        return this.articolo.getArticoliDB(ristoranteId);
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/

}
