package App.Controllers;

import App.Objects.Indirizzo;

import java.sql.SQLException;

public class AggiungiIndirizzoController extends Controller{

    Indirizzo indirizzo;

    public AggiungiIndirizzoController(String paese, String provincia, String citta, String cap, String indirizzo) throws SQLException {
        this.indirizzo = new Indirizzo(paese, provincia, citta, cap, indirizzo);
    }

    public String aggiungiIndirizzo() throws SQLException {
        return this.indirizzo.getIndirizzoDAO().aggiungiIndirizzoConf(this.indirizzo.getPaese(), this.indirizzo.getProvincia(),
                this.indirizzo.getCitta(), this.indirizzo.getCap(), this.indirizzo.getIndirizzo(), indirizzo.getCliente());
    }

}
