package App.Controller;

import App.Objects.Articolo;
import App.Objects.Cliente;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class OrdinazioneController {

    /**********Attributi**********/

    Ristorante ristorante;
    Articolo articolo;
    Cliente cliente;

    /**********Metodi**********/

    /**********Costruttori**********/

    public OrdinazioneController() {
        this.ristorante = new Ristorante();
        this.articolo = new Articolo();
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Ristorante> getListaRistoranti() throws SQLException {
        return this.ristorante.getListaRistorantiDB();
    }

    public String effettuaOrdine(Cliente cliente) {
        return cliente.effettuaOrdine();
    }

}