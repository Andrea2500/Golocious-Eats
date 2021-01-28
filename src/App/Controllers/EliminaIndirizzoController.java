package App.Controllers;

import App.Objects.Cliente;
import App.Objects.Indirizzo;

import java.sql.SQLException;

public class EliminaIndirizzoController {

    /**********Attributi**********/

    private Indirizzo indirizzo;
    private Cliente cliente;

    /**********Metodi**********/

    /**********Costruttori**********/

    public EliminaIndirizzoController() throws SQLException {
        this.indirizzo = new Indirizzo();
        this.cliente = Cliente.getInstance();
    }

    /**********Metodi di funzionalità**********/

    public String eliminaIndirizzo(Integer indirizzoId) throws SQLException {
        String messaggio = this.indirizzo.eliminaIndirizzo(indirizzoId);
        if(indirizzoId.equals(cliente.getIndirizzoAttivo()))
            cliente.setIndirizzoAttivo(null);
        return messaggio;
    }

}
