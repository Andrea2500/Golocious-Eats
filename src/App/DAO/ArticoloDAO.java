package App.DAO;

import App.Config.Database;
import App.Objects.Articolo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticoloDAO {

    /**********Attribbuti**********/

    String table;
    Database db;
    ObservableList<Articolo> articoli;

    /**********Metodi**********/

    /**********Costruttori**********/

    public ArticoloDAO() {
        this.table = "articolo";
        this.db = new Database();
    }

    /**********Metodi di funzionalità**********/

}