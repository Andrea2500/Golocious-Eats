package App.Controllers;

import App.Objects.Indirizzo;
import java.sql.SQLException;

public class aggiungiIndirizzoController {

    /**********Attributi**********/

    Indirizzo indirizzo;

    /**********Metodi**********/

    /**********Metodi di funzionalità**********/

    public aggiungiIndirizzoController(String paese, String provincia, String citta, String cap, String indirizzo) throws SQLException {
        this.indirizzo = new Indirizzo(paese, provincia, citta, cap, indirizzo);
    }

    public String aggiungiIndirizzo() throws SQLException {
        return this.indirizzo.aggiungiIndirizzo(this.indirizzo);
    }

}
