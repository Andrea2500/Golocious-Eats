package App.DAO;

import App.Config.Database;
import App.Objects.Articolo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public ObservableList<Articolo> getArticoliAltriRistorantiDB(int ristoranteId) throws SQLException {
        this.articoli = FXCollections.observableArrayList();
        this.db.setConnection();
        ResultSet rs = this.db.getConnection().createStatement().executeQuery("SELECT DISTINCT articoloid, nome, prezzo, categoria, ingredienti, disponibile FROM "+this.table+" NATURAL JOIN menu EXCEPT " +
                "(SELECT articoloid, nome, prezzo, categoria, ingredienti, disponibile FROM "+this.table+" NATURAL JOIN menu WHERE ristoranteid = '"+ristoranteId+"')");
        while(rs.next()) {
            this.articoli.add(new Articolo(rs.getString("nome"), rs.getString("prezzo"), rs.getString("categoria"),
                    rs.getString("ingredienti"), rs.getInt("articoloid"), rs.getBoolean("disponibile")));
        }
        return this.articoli;
    }

    public ObservableList<Articolo> getArticoli(int ristoranteId) throws SQLException {
        this.articoli = FXCollections.observableArrayList();
        String from = this.table+" NATURAL JOIN menu";
        String where = "ristoranteid = '"+ristoranteId+"'";
        ResultSet rs = this.db.queryBuilder(from,where);
        while(rs.next()) {
            this.articoli.add(new Articolo(rs.getString("nome"), rs.getString("prezzo"), rs.getString("categoria"),
                    rs.getString("ingredienti"), rs.getInt("articoloid"), rs.getBoolean("disponibile")));
        }
        return this.articoli;
    }

    public String switchDisponibilita(boolean toogle,int ristoranteid,int articoloid) throws SQLException {
        try{
            this.db.setConnection();
            String sql = "UPDATE menu SET disponibile = ? WHERE ristoranteid = ? AND articoloid = ?";
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setBoolean(1,toogle);
            pstmt.setInt(2,ristoranteid);
            pstmt.setInt(3,articoloid);
            if(pstmt.executeUpdate() > 0){
                this.db.closeConnection();
                return "disponibilita_aggiornata";
            }else{
                this.db.closeConnection();
                return "disponibilita_fallita";
            }
        } catch(PSQLException e) {
            this.db.closeConnection();
            return "disponibilita_fallita";
        }
    }
}