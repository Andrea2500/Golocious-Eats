package App.Controllers;

import App.Objects.Cliente;
import App.Objects.Indirizzo;

import java.sql.SQLException;

public class SelezionaIndirizzoController {

    /**********Attributi**********/

    private Cliente cliente;

    /**********Metodi**********/

    /**********Costruttori**********/

    public SelezionaIndirizzoController() {
        this.cliente = Cliente.getInstance();
    }

    /**********Metodi di funzionalità**********/

    public String setIndirizzoAttivo(Indirizzo indirizzo) throws SQLException {
        String messaggio = this.cliente.aggiornaIndirizzoAttivo(indirizzo.getIndirizzoId());
        if(messaggio.equals("indirizzo_aggiornato")) {
             cliente.setIndirizzoAttivo(indirizzo);
        }
        return messaggio;
    }

}
