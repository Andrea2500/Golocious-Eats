package App.Objects;

import App.DAO.OrdineDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Ordine {

    /**********Attributi**********/

    Integer ordineId;
    Ristorante ristorante;
    String dataOrdine;
    String totale;
    Rider rider;
    String stato;
    //TODO Carrello carrello
    OrdineDAO ordineDAO;

    /**********Metodi**********/

    /**********Costruttori**********/

    public Ordine(Integer ordineId, Integer ristoranteId, String dataOrdine, Float totale, Integer riderId, boolean stato) throws SQLException {
        this.ordineId = ordineId;
        this.ristorante = new Ristorante(ristoranteId);
        this.dataOrdine = dataOrdine;
        this.totale = String.format("%.2f", totale).concat(" €");
        this.rider = new Rider(riderId, false, false);
        this.stato = (stato)?"Consegnato":"In consegna";
        this.ordineDAO = new OrdineDAO();
    }

    public Ordine(){
        this.ordineDAO = new OrdineDAO();
    }

    /**********Getter e Setter**********/

    public Integer getOrdineId() {
        return ordineId;
    }

    public void setOrdineId(Integer ordineId) {
        this.ordineId = ordineId;
    }

    public Ristorante getRistorante() {
        return ristorante;
    }

    public void setRistorante(Ristorante ristorante) {
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

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Ordine> getOrdini() throws SQLException {
       return this.ordineDAO.getOrdini(Cliente.getInstance().getClienteId());
    }

}
