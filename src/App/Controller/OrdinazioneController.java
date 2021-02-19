package App.Controller;

import App.Objects.Articolo;
import App.Objects.Cliente;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class OrdinazioneController {

    /**********Attributi**********/

    Ristorante ristorante;

    /**********Metodi**********/

    /**********Costruttori**********/

    public OrdinazioneController() {
        this.ristorante = new Ristorante();
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Ristorante> getListaRistoranti() throws SQLException {
        return this.ristorante.getListaRistorantiDB();
    }

}