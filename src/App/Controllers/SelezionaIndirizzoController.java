package App.Controllers;

import App.Objects.Cliente;
import App.Objects.Indirizzo;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class SelezionaIndirizzoController {

    Indirizzo indirizzo;
    Cliente cliente;

    public SelezionaIndirizzoController() throws SQLException {
        this.indirizzo = new Indirizzo();
        this.cliente = Cliente.getInstance();
    }

    public ObservableList<Indirizzo> getIndirizzi() throws SQLException {
        return this.indirizzo.getIndirizzoDAO().getIndirizziDelCliente(Cliente.getInstance().getId());
    }

    public String setIndirizzoAttivo(Integer indirizzoid) throws SQLException {
        String messaggio = this.cliente.getClienteDAO().updateIndirizzoAttivo(indirizzoid);
        if(messaggio.equals("indirizzo_aggiornato")) {
             cliente.setIndirizzoAttivo(indirizzoid);
        }
        return messaggio;
    }

}
