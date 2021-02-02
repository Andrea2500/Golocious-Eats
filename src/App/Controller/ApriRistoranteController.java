package App.Controller;

import App.Objects.Cliente;
import App.Objects.Gestore;
import App.Objects.Ristorante;

public class ApriRistoranteController {

    /**********Attributi**********/

    Ristorante ristorante;
    Gestore gestore;

    /**********Metodi**********/

    /**********Costruttori**********/

    public ApriRistoranteController() {
        this.ristorante = new Ristorante();
        this.gestore = new Gestore();
    }

    /**********Metodi di funzionalità**********/

    public String apriRistorante(Ristorante ristorante) throws Exception {
       return this.gestore.apriRistorante(ristorante);
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/

}
