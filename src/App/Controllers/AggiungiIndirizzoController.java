package App.Controllers;

import App.Objects.Indirizzo;
import java.sql.SQLException;

public class AggiungiIndirizzoController {

    /**********Attributi**********/

    private Indirizzo indirizzo;

    /**********Metodi**********/

    /**********Costruttori**********/

    public AggiungiIndirizzoController(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**********Metodi di funzionalitào**********/

    public String aggiungiIndirizzo() throws SQLException {
        return this.indirizzo.aggiungiIndirizzo(this.indirizzo);
    }

}
