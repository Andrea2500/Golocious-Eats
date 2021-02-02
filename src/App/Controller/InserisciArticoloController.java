package App.Controller;

import App.Objects.Articolo;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class InserisciArticoloController {

    /**********Attributi**********/

    Ristorante ristorante;

    /**********Metodi**********/

    /**********Costruttori**********/

    public InserisciArticoloController() {
        this.ristorante = new Ristorante();
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Articolo> getArticoliAltriRistoranti(Ristorante ristorante) throws SQLException {
        return this.ristorante.getArticoliAltriRistorantiDB(ristorante.getRistoranteId());
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/

}
