package App.Controller;

import App.Objects.ElementoStatistiche;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;


public class StatisticheController {

    /**********Attributi**********/

    ElementoStatistiche elementoStatistiche;

    /**********Metodi**********/

    /**********Costruttori**********/

    public StatisticheController() {
        this.elementoStatistiche = new ElementoStatistiche();
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<ElementoStatistiche> getStatistiche(Float daPrezzo, Float aPrezzo, boolean moto, boolean bici, boolean auto, LocalDate daData, LocalDate aData, Ristorante ristorante) throws SQLException {
        String veicolo = "('m','b','a')";
        if(!moto)
           veicolo = veicolo.replace("'m'","''");

        if(!bici)
            veicolo = veicolo.replace("'b'","''");
        if(!auto)
            veicolo = veicolo.replace("'a'","''");
        if(!moto && !auto && !bici)
            veicolo = "('m','a','b')";
        return this.elementoStatistiche.getStatisticheDB(daPrezzo, aPrezzo, veicolo, daData, aData, ristorante.getRistoranteId());
    }
}
