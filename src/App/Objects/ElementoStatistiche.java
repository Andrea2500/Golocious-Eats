package App.Objects;

import App.DAO.ElementoStatisticheDAO;

public class ElementoStatistiche {

    /**********Attributi**********/

    String nomeArticolo;
    String prezzoArticolo;
    int quantita;
    String totale;
    ElementoStatisticheDAO elementoStatisticheDAO;

    /**********Metodi**********/

    /**********Costruttori**********/

    public ElementoStatistiche(String nomeArticolo, Float prezzoArticolo, int quantita,Float totale) {
        this.nomeArticolo = nomeArticolo;
        this.prezzoArticolo = prezzoArticolo.toString().concat(" €");
        this.quantita = quantita;
        this.totale = totale.toString().concat(" €");
    }

    /**********Getter e Setter**********/

    public String getNomeArticolo() {
        return nomeArticolo;
    }

    public void setNomeArticolo(String nomeArticolo) {
        this.nomeArticolo = nomeArticolo;
    }

    public String getPrezzoArticolo() {
        return prezzoArticolo;
    }

    public void setPrezzoArticolo(String prezzoArticolo) {
        this.prezzoArticolo = prezzoArticolo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getTotale() {
        return totale;
    }

    public void setTotale(String totale) {
        this.totale = totale;
    }

    /**********Metodi di funzionalità**********/



}
