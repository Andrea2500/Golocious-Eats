package App.Controller;

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

    public String eliminaIndirizzo(Indirizzo indirizzo) throws SQLException {
        String messaggio = new Indirizzo().eliminaIndirizzo(indirizzo.getIndirizzoId());
        if(cliente.getIndirizzoAttivo() != null){
            if (indirizzo.getIndirizzoId().equals(cliente.getIndirizzoAttivo().getIndirizzoId()))
                cliente.setIndirizzoAttivo(null);
        }
        return messaggio;
    }

}
