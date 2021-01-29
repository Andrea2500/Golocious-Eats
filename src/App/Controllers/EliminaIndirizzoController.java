package App.Controllers;

import App.Objects.Cliente;
import App.Objects.Indirizzo;

import java.sql.SQLException;

public class EliminaIndirizzoController {

    /**********Attributi**********/

    private Cliente cliente;

    /**********Metodi**********/

    /**********Costruttori**********/

    public EliminaIndirizzoController() {
        this.cliente = Cliente.getInstance();
    }

    /**********Metodi di funzionalità**********/

    public String eliminaIndirizzo(Integer indirizzoId) throws SQLException {
        String messaggio = new Indirizzo().eliminaIndirizzo(indirizzoId);
        if(indirizzoId.equals(cliente.getIndirizzoAttivo().getIndirizzoId()))
            cliente.setIndirizzoAttivo(null);
        return messaggio;
    }

}
