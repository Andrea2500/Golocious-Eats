package App.Objects;

import App.DAO.IndirizzoDAO;
import App.DAO.RiderDAO;
import java.sql.SQLException;

public class Rider extends Cliente {

    String patente;
    String veicolo;


    RiderDAO riderDAO;

    public Rider(Integer id) throws SQLException {
        super.id = id;
        // TODO this.patente = riderDAO.getPatente();
        // TODO this.veicolo = riderDAO.getVeicolo();
    }

    public Rider(Integer id, String patente, String veicolo) throws SQLException {
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

    public RiderDAO getRiderDAO() {
        return riderDAO;
    }

    public void setRiderDAO(RiderDAO riderDAO) {
        this.riderDAO = riderDAO;
    }

    //TODO diventa rider in impostazioni visualizzabile solo se non si è clienti o gestori
}
