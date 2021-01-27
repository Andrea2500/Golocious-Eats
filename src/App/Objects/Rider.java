package App.Objects;

import App.DAO.RiderDAO;

import java.sql.SQLException;

public class Rider extends Cliente {

    /**********Attributi**********/

    private String patente;
    private String veicolo;
    private RiderDAO riderDAO;

    /**********Metodi**********/

    /**********Costruttori**********/

    public Rider(Integer riderId) throws SQLException {
        super.id = riderId;
        this.riderDAO = new RiderDAO();
        this.veicolo = this.riderDAO.getVeicolo(riderId);
        super.nome = super.getNomeDB(riderId);
    }

    public Rider(Integer riderId, String patente, String veicolo) {
        super.id = riderId;
        this.patente = patente;
        this.veicolo = veicolo;
        this.riderDAO = new RiderDAO();
    }

    /**********Getter e setter**********/

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(String veicolo) {
        this.veicolo = veicolo;
    }

    /**********Metodi di funzionalità**********/

    public String diventaRider(Rider rider) throws SQLException {
        return this.riderDAO.diventaRiderConf(rider);
    }

    /**********Metodi di supporto**********/

    @Override
    public String toString() {
        return super.nome;
    }

}
