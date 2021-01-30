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

    public ObservableList<Articolo> getArticoli(int ristoranteId) throws SQLException {
        this.articoli = FXCollections.observableArrayList();
        String from = this.table+" NaTURAL JOIN menu";
        String where = "ristoranteid = '"+ristoranteId+"'";
        ResultSet rs = this.db.queryBuilder(from,where);
        while(rs.next()) {
            this.articoli.add(new Articolo(rs.getString("nome"), rs.getString("prezzo"), rs.getString("categoria"),
                    rs.getString("ingredienti"), rs.getInt("articoloid"), rs.getBoolean("disponibile")));
        }
        return this.articoli;
    }

}