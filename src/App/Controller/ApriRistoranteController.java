package App.Controller;

import App.Objects.Cliente;
import App.Objects.Gestore;
import App.Objects.Ristorante;

import java.sql.SQLException;

public class ApriRistoranteController {

    /**********Attributi**********/

    Ristorante ristorante;
    Gestore gestore;

    /**********Metodi**********/

    /**********Costruttori**********/

    public ApriRistoranteController(Gestore gestore) {
        this.ristorante = new Ristorante();
        this.gestore = gestore;
    }

    /**********Metodi di funzionalità**********/

    public String apriRistorante(Ristorante ristorante) throws Exception {
       return this.gestore.apriRistorante(ristorante);
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/

}
