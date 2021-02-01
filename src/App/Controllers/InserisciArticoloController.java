package App.Controllers;

import App.Objects.Articolo;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class InserisciArticoloController {

    /**********Attributi**********/

    Articolo articolo;

    /**********Metodi**********/

    /**********Costruttori**********/

    public InserisciArticoloController() {
        this.articolo = new Articolo();
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Articolo> getArticoliAltriRistoranti(int ristoranteId) throws SQLException {
        return this.articolo.getArticoliAltriRistorantiDB(ristoranteId);
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/

}
