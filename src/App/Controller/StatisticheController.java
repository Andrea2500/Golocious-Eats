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
           veicolo = veicolo.replace("'m'","''");
        if(!auto)
            veicolo = veicolo.replace("'a'","''");
        if(!bici)
            veicolo = veicolo.replace("'b'","''a@test.it    abc123");
        if(!moto && !auto && !bici)
            veicolo = "('m','a','b')";

        System.out.println(veicolo);
        return this.elementoStatistiche.getStatisticheDB(daPrezzo,aPrezzo,veicolo,daData,aData,ristorante.getRistoranteId());
    }
}
