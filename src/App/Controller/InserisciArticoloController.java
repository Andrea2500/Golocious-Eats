package App.Controller;

import App.Objects.Articolo;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class InserisciArticoloController {

    /**********Attributi**********/

    Ristorante ristorante;
    Articolo articolo;

    /**********Metodi**********/

    /**********Costruttori**********/

    public InserisciArticoloController() {
        this.ristorante = new Ristorante();
        this.articolo = new Articolo();
    }

    /**********Metodi di funzionalità**********/

    public String aggiungiEsistente(Ristorante ristorante, Articolo articolo) throws SQLException {
        return ristorante.aggiungiArticoloEsistenteDB(articolo.getArticoloId());
    }

    public String aggiungiManualmente(Ristorante ristorante, Articolo articolo) throws Exception {
        String messaggio = articolo.setArticoloDB();
        if(messaggio.equals("articolo_aggiunto")) {
            return ristorante.aggiungiArticoloEsistenteDB(articolo.getArticoloId());
        } else {
            return messaggio;
        }
    }

    /**********Metodi di supporto**********/

    public ObservableList<Articolo> getArticoliAltriRistoranti(Ristorante ristorante) throws SQLException {
        return this.ristorante.getArticoliAltriRistorantiDB(ristorante.getRistoranteId());
    }

}
