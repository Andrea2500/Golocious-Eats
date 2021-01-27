package App.Objects;

import App.DAO.OrdineDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Ordine {

    /**********Attributi**********/

    Integer id;
    String ristorante;
    String dataOrdine;
    String totale;
    String rider;
    boolean stato;
    OrdineDAO ordineDAO;

    /**********Metodi**********/

    /**********Costruttori**********/

    public Ordine(Integer id, String ristorante, String dataOrdine, String totale, String rider, boolean stato) {
        this.id = id;
        this.ristorante = ristorante;
        this.dataOrdine = dataOrdine;
        this.totale = totale;
        this.rider = rider;
        this.stato = stato;
        this.ordineDAO = new OrdineDAO();
    }

    public Ordine(){
        this.ordineDAO = new OrdineDAO();
    }

    /**********Getter e setter**********/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRistorante() {
        return ristorante;
    }

    public void setRistorante(String ristorante) {
        this.ristorante = ristorante;
    }

    public String getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(String dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public String getTotale() {
        return totale;
    }

    public void setTotale(String totale) {
        this.totale = totale;
    }

    public String getRider() {
        return rider;
    }

    public void setRider(String rider) {
        this.rider = rider;
    }

    public boolean getStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Ordine> getOrdini() throws SQLException {
       return this.ordineDAO.getOrdini(Cliente.getInstance().getId());
    }

    //TODO attributi Cliente cliente e Rider rider guarda TODO nel costruttore della classe Rider

}
