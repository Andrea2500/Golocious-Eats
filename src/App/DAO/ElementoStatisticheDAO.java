package App.DAO;

import App.Config.Database;
import App.Objects.ElementoStatistiche;
import javafx.collections.ObservableList;

public class ElementoStatisticheDAO {

    /**********Attributi**********/

    Database db;
    ObservableList<ElementoStatistiche> elementiStatiche;

    /**********Metodi**********/

    /**********Costruttori**********/

    public ElementoStatisticheDAO() {
        this.db = new Database();
    }

    /**********Metodi di funzionalità**********/


}
