package App.Objects;

import App.DAO.RiderDAO;

import java.sql.SQLException;

public class Rider extends Cliente {

    String patente;
    String veicolo;
    RiderDAO riderDAO;


    public Rider(Integer id) {
        super.id = id;
        this.riderDAO = new RiderDAO();
        // TODO this.patente = riderDAO.getPatente();
        // TODO this.veicolo = riderDAO.getVeicolo();
    }

    public Rider(Integer id, String patente, String veicolo) {
        super.id = id;
        this.patente = patente;
        this.veicolo = veicolo;
        this.riderDAO = new RiderDAO();
    }

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

    public String diventaRider(Rider rider) throws SQLException {
        return this.riderDAO.diventaRiderConf(rider);
    }


    //TODO diventa rider in impostazioni visualizzabile solo se non si è clienti o gestori

}
