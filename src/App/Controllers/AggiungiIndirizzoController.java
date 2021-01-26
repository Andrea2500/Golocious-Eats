package App.Controllers;

import App.Objects.Cliente;
import App.Objects.Indirizzo;

import java.sql.SQLException;

public class AggiungiIndirizzoController extends Controller{

    Indirizzo indirizzo;

    public AggiungiIndirizzoController(String paese, String provincia, String cap, String citta, String indirizzo) throws SQLException {
        this.indirizzo = new Indirizzo(paese, provincia, cap, citta, indirizzo);
    }

    public String aggiungiIndirizzo() throws SQLException {
        return this.indirizzo.getIndirizzoDAO().aggiungiIndirizzoConf(this.indirizzo.getPaese(), this.indirizzo.getProvincia(),
                this.indirizzo.getCap(), this.indirizzo.getCitta(), this.indirizzo.getIndirizzo(), indirizzo.getCliente());
    }
}
