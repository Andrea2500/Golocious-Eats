package App.Controller;

import App.Objects.Indirizzo;

import java.sql.SQLException;

public class AggiungiIndirizzoController {

    /**********Metodi**********/

    /**********Metodi di funzionalità**********/

    public String aggiungiIndirizzo(Indirizzo indirizzo) throws SQLException {
        return new Indirizzo().aggiungiIndirizzo(indirizzo);
    }

}
