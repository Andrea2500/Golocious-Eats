package App.Controller;

import App.Objects.ElementoStatistiche;

import java.time.LocalDate;


public class StatisticheController {

    ElementoStatistiche elementoStatistiche;

    public StatisticheController() {
        this.elementoStatistiche = new ElementoStatistiche();
    }

    public void getStatistiche(Float daPrezzo, Float aPrezzo, boolean moto, boolean auto, boolean bici, LocalDate daData, LocalDate aData) {
        this.elementoStatistiche.getStatisticheDB(daPrezzo,aPrezzo,moto,auto,bici,daData,aData);
    }
}
