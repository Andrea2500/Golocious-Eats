package App.Objects;

import App.DAO.IndirizzoDAO;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class Indirizzo {

    /**********Attributi**********/

    Integer indirizzoId;
    String paese;
    String provincia;
    String citta;
    String cap;
    String indirizzoCivico;
    Cliente cliente;
    IndirizzoDAO indirizzoDAO;

    /**********Metodi**********/
    
    /**********Costruttori**********/

    public Indirizzo() throws SQLException {
        this.cliente = Cliente.getInstance();
        this.indirizzoDAO = new IndirizzoDAO();
    }

    public Indirizzo(int indirizzoId) throws SQLException {
        this.indirizzoDAO = new IndirizzoDAO();
        this.aggiornaCampi(this.indirizzoDAO.getIndirizzo(indirizzoId));
    }

    public Indirizzo(String paese, String provincia, String citta, String cap, String indirizzoCivico) throws SQLException {
        this.paese = paese;
        this.provincia = provincia;
        this.citta = citta;
        this.cap = cap;
        this.indirizzoCivico = indirizzoCivico;
        this.cliente = Cliente.getInstance();
        this.indirizzoDAO = new IndirizzoDAO();
    }

    public Indirizzo(Integer indirizzoId, String paese, String provincia, String citta, String cap, String indirizzoCivico) throws SQLException {
        this.indirizzoId = indirizzoId;
        this.paese = paese;
        this.provincia = provincia;
        this.citta = citta;
        this.cap = cap;
        this.indirizzoCivico = indirizzoCivico;
        this.cliente = Cliente.getInstance();
        this.indirizzoDAO = new IndirizzoDAO();
    }

    /**********Getter e Setter**********/

    public Integer getIndirizzoId() {
        return indirizzoId;
    }

    public void setIndirizzoId(Integer indirizzoId) {
        this.indirizzoId = indirizzoId;
    }

    public String getPaese() {
        return paese;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCap() {
        return cap;
    }

    public String getCitta() {
        return citta;
    }

    public String getIndirizzoCivico() {
        return indirizzoCivico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**********Metodi di funzionalità**********/

    public String aggiungiIndirizzoDB(Cliente cliente) throws SQLException {
        this.cliente = cliente;
        return this.indirizzoDAO.aggiungiIndirizzo(this);
    }

    public String eliminaIndirizzo() throws SQLException {
        return this.indirizzoDAO.eliminaIndirizzo(this.indirizzoId);
    }

    /**********Metodi di supporto**********/

    @Override
    public String toString(){
        return this.indirizzoCivico +", "+this.cap+", "+this.citta+", "+this.provincia+", "+this.paese;
    }

    private void aggiornaCampi(Indirizzo indirizzo) {
        this.indirizzoId = indirizzo.getIndirizzoId();
        this.paese = indirizzo.getPaese();
        this.provincia = indirizzo.getProvincia();
        this.citta = indirizzo.getCitta();
        this.cap = indirizzo.getCap();
        this.indirizzoCivico = indirizzo.getIndirizzoCivico();
    }

}
