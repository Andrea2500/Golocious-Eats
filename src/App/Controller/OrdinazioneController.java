package App.Controller;

import App.Objects.Articolo;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class OrdinazioneController {

    Ristorante ristorante;
    Articolo articolo;

    public OrdinazioneController() {
        this.ristorante = new Ristorante();
        this.articolo = new Articolo();
    }

    public ObservableList<Ristorante> getListaRistoranti() throws SQLException {
        return this.ristorante.getListaRistorantiDB();
    }
}
