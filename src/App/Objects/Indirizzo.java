package App.Objects;

import App.DAO.IndirizzoDAO;

import java.sql.SQLException;

public class Indirizzo {

    Integer id;
    String paese;
    String provincia;
    String citta;
    String cap;
    String indirizzo;


    Cliente cliente;
    IndirizzoDAO indirizzoDAO;

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

    public IndirizzoDAO getIndirizzoDAO() {
        return indirizzoDAO;
    }

    @Override
    public String toString(){
        return this.indirizzo+", "+this.cap+", "+this.citta+", "+this.provincia+", "+this.paese;
    }

}
