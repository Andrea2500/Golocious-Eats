package App.Objects;

import App.DAO.RiderDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Rider extends Cliente {

    /**********Attributi**********/

    private String patente;
    private String veicolo;
    private RiderDAO riderDAO;
    private ObservableList<Ordine> consegne;

    /**********Metodi**********/

    /**********Costruttori**********/

    public Rider(Integer riderId, boolean consegne, boolean attive) throws SQLException {
        super.clienteId = riderId;
        this.riderDAO = new RiderDAO();
        this.veicolo = this.riderDAO.getVeicolo(riderId);
        super.nome = super.getNomeDB(riderId);
        if(consegne)
            setConsegneDB(attive);
    }

    public Rider(Integer riderId, String patente, String veicolo) {
        super.clienteId = riderId;
        this.patente = patente;
        this.veicolo = veicolo;
        this.riderDAO = new RiderDAO();
    }

    /**********Getter e Setter**********/

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

    public ObservableList<Ordine> getConsegne() {
        return consegne;
    }

    public void setConsegne(ObservableList<Ordine> consegne) {
        this.consegne = consegne;
    }

    public void setConsegneDB(boolean attive) throws SQLException {
        this.consegne = FXCollections.observableArrayList();
        this.consegne = this.riderDAO.getConsegne(super.clienteId, attive);
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

    public void consegna(Integer ordineId) throws SQLException {
        this.riderDAO.consegna(ordineId);
    }

}
