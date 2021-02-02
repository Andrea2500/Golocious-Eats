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

    public String switchDisponibilita(boolean toggle, Ristorante ristorante, Articolo articolo) throws SQLException {
        return ristorante.switchDisponibilita(toggle, ristorante.getRistoranteId(), articolo.getArticoloId());
    }

    public String eliminaDaMenu(Ristorante ristorante, Articolo articolo) throws SQLException {
       return ristorante.eliminaDaMenuDB(articolo.getArticoloId());
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/

}
