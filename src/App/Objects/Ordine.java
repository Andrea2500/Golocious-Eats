package App.Objects;

import App.DAO.OrdineDAO;
import java.sql.SQLException;

public class Ordine {

    /**********Attributi**********/

    Integer ordineId;
    Ristorante ristorante;
    Indirizzo indirizzo;
    String nomeCliente;
    String telefonoCliente;
    String dataOrdine;
    String totale;
    Rider rider;
    String stato;
    OrdineDAO ordineDAO;

    /**********Metodi**********/

    /**********Costruttori**********/

    public Ordine(Integer ordineId, Integer ristoranteId, Integer indirizzoId, String nomeCliente, String telefonoCliente, String dataOrdine, Float totale, Integer riderId, boolean stato) throws SQLException {
        this.ordineId = ordineId;
        this.ristorante = new Ristorante(ristoranteId);
        this.indirizzo = new Indirizzo(indirizzoId);
        this.nomeCliente = nomeCliente;
        this.telefonoCliente = telefonoCliente;
        this.dataOrdine = dataOrdine;
        this.totale = String.format("%.2f", totale).concat(" €");
        this.rider = new Rider(riderId, false, false);
        this.stato = (stato)?"Consegnato":"In consegna";
        this.ordineDAO = new OrdineDAO();
    }

    public Ordine(int ordineId) throws Exception {
        this.ordineDAO = new OrdineDAO();
        Ordine ordineCreato;
        try {
            ordineCreato = this.ordineDAO.creaOrdine(ordineId);
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
        if(ordineCreato != null)
            this.aggiornaCampi(ordineCreato);
    }

    public Ordine(Integer ordineId, Integer ristoranteId, String dataOrdine, Float totale, Integer riderId, boolean stato) throws SQLException {
        this.ordineId = ordineId;
        this.ristorante = new Ristorante(ristoranteId);
        this.dataOrdine = dataOrdine;
        this.totale = String.format("%.2f", totale).concat(" €");
        this.rider = new Rider(riderId, false, false);
        this.stato = (stato)?"Consegnato":"In consegna";
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

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
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

    /**********Metodi di supporto**********/

    private void aggiornaCampi(Ordine ordine) {
        this.ordineId = ordine.getOrdineId();
        this.ristorante = ordine.getRistorante();
        this.dataOrdine = ordine.getDataOrdine();
        this.totale = ordine.totale;
        this.rider = ordine.rider;
        this.stato = ordine.stato;
    }

}
