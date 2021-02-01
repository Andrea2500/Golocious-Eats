package App.Controllers;

import App.Objects.Articolo;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class GestisciArticoliController {

    /**********Attributi**********/

    Ristorante ristorante;

    /**********Metodi**********/

    /**********Costruttori**********/

    public GestisciArticoliController(Integer ristoranteId) throws SQLException {
        this.ristorante = new Ristorante(ristoranteId);
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Articolo> getArticoliRistorante() {
        return this.ristorante.getArticoli();
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/

}
