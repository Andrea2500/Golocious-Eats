package App.Controller;

import App.Objects.Gestore;
import App.Objects.Ristorante;

import java.sql.SQLException;

public class AggiungiGestoreController {

    /**********Attributi**********/

    Gestore gestore;

    /**********Metodi**********/

    /**********Costruttori**********/

    public AggiungiGestoreController() {
        this.gestore = new Gestore();
    }

    /**********Metodi di funzionalità**********/

    public String rendiGestore(String email, Ristorante ristorante) throws SQLException {
        return this.gestore.rendiGestore(email,ristorante.getRistoranteId());
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/


}
