package App.Objects;

import App.DAO.ElementoStatisticheDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;
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

    public ElementoStatistiche(String nomeArticolo, Float prezzoArticolo, int quantita,Float totale) {
        this.nomeArticolo = nomeArticolo;
        this.prezzoArticolo = prezzoArticolo.toString().concat(" €");
        this.quantita = quantita;
        this.totale = totale.toString().concat(" €");
    }

    public ObservableList<ElementoStatistiche> getStatisticheDB(Float daPrezzo, Float aPrezzo, String veicolo, LocalDate daData, LocalDate aData, int ristoranteId) throws SQLException {
       return this.elementoStatisticheDAO.getStatistiche(daPrezzo,aPrezzo,veicolo,daData,aData,ristoranteId);
    }
}
