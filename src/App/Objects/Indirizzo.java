package App.Objects;

import App.DAO.IndirizzoDAO;

import java.sql.SQLException;

public class Indirizzo {

    String paese;
    String provincia;
    String citta;
    String cap;
    String indirizzo;

    Cliente cliente;
    IndirizzoDAO indirizzoDAO;

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

    public IndirizzoDAO getIndirizzoDAO() {
        return indirizzoDAO;
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

}
