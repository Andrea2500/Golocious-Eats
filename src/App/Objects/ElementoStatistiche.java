package App.Objects;

import App.DAO.ElementoStatisticheDAO;

import java.time.LocalDate;

public class ElementoStatistiche {

    String nomeArticolo;
    String prezzoArticolo;
    int quantita;
    String totale;
    ElementoStatisticheDAO elementoStatisticheDAO;

    public ElementoStatistiche() {
        elementoStatisticheDAO = new ElementoStatisticheDAO();
    }

    public ElementoStatistiche(String nomeArticolo, Float prezzoArticolo, int quantita) {
        this.nomeArticolo = nomeArticolo;
        this.prezzoArticolo = prezzoArticolo.toString().concat("€");
        this.quantita = quantita;
        Float tot = prezzoArticolo*quantita;
        this.totale = tot.toString().concat("€");
    }

    public void getStatisticheDB(Float daPrezzo, Float aPrezzo, boolean moto, boolean auto, boolean bici, LocalDate daData, LocalDate aData) {
        this.elementoStatisticheDAO.getStatistiche(daPrezzo,aPrezzo,moto,auto,bici,daData,aData);
    }
}
