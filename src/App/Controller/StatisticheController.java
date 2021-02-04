package App.Controller;

import App.Objects.ElementoStatistiche;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;


public class StatisticheController {

    ElementoStatistiche elementoStatistiche;

    public StatisticheController() {
        this.elementoStatistiche = new ElementoStatistiche();
    }

    public ObservableList<ElementoStatistiche> getStatistiche(Float daPrezzo, Float aPrezzo, boolean moto, boolean auto, boolean bici, LocalDate daData, LocalDate aData, Ristorante ristorante) throws SQLException {
        String veicolo = "('m','a','b')";
        if(!moto)
            veicolo.replace("'m',","");
        if(!auto)
            veicolo.replace("'a',","");
        if(!moto)
            veicolo.replace("'b',","");
        return this.elementoStatistiche.getStatisticheDB(daPrezzo,aPrezzo,veicolo,daData,aData,ristorante.getRistoranteId());
    }
}
