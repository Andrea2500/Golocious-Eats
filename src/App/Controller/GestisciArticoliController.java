package App.Controller;

import App.Objects.Articolo;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class GestisciArticoliController {

    /**********Attributi**********/

    Articolo articolo;
    Ristorante ristorante;

    /**********Metodi**********/

    /**********Metodi di funzionalità**********/

    public ObservableList<Articolo> getArticoliRistorante(Ristorante ristorante) throws SQLException {
        return new Ristorante(ristorante.getRistoranteId()).getArticoli();
    }

    public String switchDisponibilitaArticolo(boolean toggle, Ristorante ristorante, Articolo articolo) throws SQLException {
        return ristorante.switchDisponibilitaArticoloDB(toggle, ristorante.getRistoranteId(), articolo.getArticoloId());
    }

    public String eliminaArticoloDaMenu(Ristorante ristorante, Articolo articolo) throws SQLException {
       return ristorante.eliminaArticoloDaMenuDB(articolo.getArticoloId());
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/

}
