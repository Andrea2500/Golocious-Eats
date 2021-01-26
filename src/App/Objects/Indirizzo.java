package App.Objects;

import App.DAO.IndirizzoDAO;

import java.sql.SQLException;

public class Indirizzo {

    String paese;
    String provincia;
    String cap;
    String citta;
    String indirizzo;

    Cliente cliente;
    IndirizzoDAO indirizzoDAO;

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public IndirizzoDAO getIndirizzoDAO() {
        return indirizzoDAO;
    }

    public void setIndirizzoDAO(IndirizzoDAO indirizzoDAO) {
        this.indirizzoDAO = indirizzoDAO;
    }

    public Indirizzo(String paese, String provincia, String cap, String citta, String indirizzo) throws SQLException {
        this.paese = paese;
        this.provincia = provincia;
        this.cap = cap;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.cliente = Cliente.getInstance();
        this.indirizzoDAO = new IndirizzoDAO();
    }

}
