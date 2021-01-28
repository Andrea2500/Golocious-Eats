package App.Objects;

import App.DAO.IndirizzoDAO;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class Indirizzo {

    /**********Attributi**********/

    Integer id;
    String paese;
    String provincia;
    String citta;
    String cap;
    String indirizzo;
    Cliente cliente;
    IndirizzoDAO indirizzoDAO;

    /**********Metodi**********/
    
    /**********Costruttori**********/

    public Indirizzo() throws SQLException {
        this.cliente = Cliente.getInstance();
        this.indirizzoDAO = new IndirizzoDAO();
    }

    public Indirizzo(String paese, String provincia, String citta, String cap, String indirizzo) throws SQLException {
        this.paese = paese;
        this.provincia = provincia;
        this.citta = citta;
        this.cap = cap;
        this.indirizzo = indirizzo;
        this.cliente = Cliente.getInstance();
        this.indirizzoDAO = new IndirizzoDAO();
    }

    public Indirizzo(Integer id, String paese, String provincia, String citta, String cap, String indirizzo) throws SQLException {
        this.id = id;
        this.paese = paese;
        this.provincia = provincia;
        this.citta = citta;
        this.cap = cap;
        this.indirizzo = indirizzo;
        this.cliente = Cliente.getInstance();
        this.indirizzoDAO = new IndirizzoDAO();
    }

    /**********Getter e setter**********/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getIndirizzo() {
        return indirizzo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**********Metodi di funzionalità**********/

    public String aggiungiIndirizzo(Indirizzo indirizzo) throws SQLException {
        return this.indirizzoDAO.aggiungiIndirizzoConf(indirizzo);
    }

    public ObservableList<Indirizzo> getIndirizzi() throws SQLException {
        return this.indirizzoDAO.getIndirizziDelCliente(this.cliente.getId());
    }

    /**********Metodi di supporto**********/

    @Override
    public String toString(){
        return this.indirizzo+", "+this.cap+", "+this.citta+", "+this.provincia+", "+this.paese;
    }

    public String eliminaIndirizzo(Integer indirizzoid) throws SQLException {
        return this.indirizzoDAO.eliminaIndirizzo(indirizzoid);
    }
}
