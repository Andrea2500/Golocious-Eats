package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Articolo;
import App.Objects.Indirizzo;
import App.Objects.Ristorante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;

import java.sql.*;

public class RistoranteDAO {

    /**********Attributi**********/

    private String table;
    private Database db;
    ErroriDB edb;
    private ObservableList<Ristorante> ristoranti;
    private ObservableList<Articolo> articoli;
    private Indirizzo indirizzo;

    /**********Metodi**********/

    /**********Costruttori**********/

    public RistoranteDAO() {
        this.db = new Database();
        this.edb = new ErroriDB();
        this.table = "Ristorante";
    }

    /**********Metodi di supporto**********/

    public Ristorante getRistorante(Integer ristoranteId) throws SQLException {
        String where ="ristoranteid = '"+ristoranteId+"'";
        ResultSet rs = this.db.queryBuilder(this.table,where);
        if(rs.next()) {
            return new Ristorante(rs.getInt("ristoranteid"), rs.getString("nome"),
                    rs.getString("indirizzo"),rs.getString("telefono"),
                    rs.getDate("datadiapertura").toLocalDate(),getArticoli(rs.getInt("ristoranteid")));
        } else {
            return null;
        }
    }

    public ObservableList<Ristorante> getRistoranti(Integer clienteId) throws SQLException {
        this.db.setConnection();
        ristoranti = FXCollections.observableArrayList();
        ResultSet rs = db.queryBuilder(this.table+" NATURAL JOIN Gestore", "clienteid = "+clienteId);
        this.db.closeConnection();
        while(rs.next()) {
            ristoranti.add(new Ristorante(rs.getInt("ristoranteid"), rs.getString("nome"),
                    rs.getString("indirizzo"),rs.getString("telefono"),
                    rs.getDate("datadiapertura").toLocalDate(), getArticoli(rs.getInt("ristoranteid"))));
        }
        return ristoranti;
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

    public String eliminaDaMenu(Integer ristoranteId, int articoloId) throws SQLException {
        try {
            String sql = "DELETE FROM menu WHERE ristoranteid = ? AND articoloid = ?";
            this.db.setConnection();
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,ristoranteId);
            pstmt.setInt(2,articoloId);
            if(pstmt.executeUpdate() > 0) {
                this.db.closeConnection();
                return "articolo_eliminato";
            } else {
                this.db.closeConnection();
                return "eliminazione_articolo_fallita";
            }
        } catch(PSQLException e) {
            this.db.closeConnection();
            return "eliminazione_articolo_fallita";
        }
    }

    public ObservableList<Articolo> getArticoliAltriRistorantiDB(int ristoranteId) throws SQLException {
        String sql = "SELECT * FROM (" +
                "SELECT DISTINCT articolo.articoloid , nome, prezzo, categoria, ingredienti " +
                "FROM articolo left outer join menu on articolo.articoloid = menu.articoloid " +
                "EXCEPT (SELECT articolo.articoloid, nome, prezzo, categoria, ingredienti " +
                "FROM articolo left outer join menu on articolo.articoloid = menu.articoloid " +
                "WHERE ristoranteid = "+ristoranteId+")" +
                ") t "+
                "ORDER BY CASE " +
                "WHEN t.categoria ='a' THEN 1 " +
                "WHEN t.categoria ='b' THEN 2 " +
                "WHEN t.categoria ='p' THEN 3 " +
                "WHEN t.categoria ='t' THEN 4 " +
                "WHEN t.categoria ='d' THEN 5 "+
                "WHEN categoria ='v' THEN 6 " +
                "WHEN categoria ='w' THEN 7 " +
                "END";
        this.articoli = FXCollections.observableArrayList();
        this.db.setConnection();
        ResultSet rs = this.db.getConnection().createStatement().executeQuery(sql);
        while(rs.next()) {
            this.articoli.add(new Articolo(rs.getString("nome"), rs.getFloat("prezzo"), rs.getString("categoria"),
                    rs.getString("ingredienti"), rs.getInt("articoloid"),true));
        }
        return this.articoli;
    }

    public ObservableList<Articolo> getArticoli(int ristoranteId) throws SQLException {
        this.articoli = FXCollections.observableArrayList();
        String sql = "SELECT * FROM (SELECT * FROM articolo NATURAL JOIN menu WHERE ristoranteid = "+ristoranteId+") t "+
                "ORDER BY CASE " +
                "WHEN t.categoria ='a' THEN 1 " +
                "WHEN t.categoria ='b' THEN 2 " +
                "WHEN t.categoria ='p' THEN 3 " +
                "WHEN t.categoria ='t' THEN 4 " +
                "WHEN t.categoria ='d' THEN 5 "+
                "WHEN categoria ='v' THEN 6 " +
                "WHEN categoria ='w' THEN 7 " +
                "END";
        this.db.setConnection();
        ResultSet rs = this.db.getConnection().createStatement().executeQuery(sql);
        this.db.closeConnection();
        while(rs.next()) {
            this.articoli.add(new Articolo(rs.getString("nome"), rs.getFloat("prezzo"), rs.getString("categoria"),
                    rs.getString("ingredienti"), rs.getInt("articoloid"), rs.getBoolean("disponibile")));
        }
        return this.articoli;
    }

    public int setRistorante(Ristorante ristorante) throws Exception {
        try {
            String sql = "INSERT INTO "+this.table+" VALUES (?, ?, ?, ?)";
            this.db.setConnection();
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, ristorante.getNome());
            pstmt.setString(2, ristorante.getIndirizzo());
            pstmt.setString(3, ristorante.getTelefono());
            pstmt.setDate(4, Date.valueOf(ristorante.getDataDiApertura()));
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            this.db.closeConnection();
            if(rs.next()) {
                return rs.getInt("ristoranteid");
            } else {
                return 0;
            }
        } catch (PSQLException e) {
            this.db.closeConnection();
            throw new Exception(this.edb.getMessaggioErrore(e.getMessage()));
        }
    }

    public String aggiungiArticoloEsistente(int ristoranteId, int articoloId) throws SQLException{
        try {
            String sql = "INSERT INTO menu VALUES (?, ?)";
            this.db.setConnection();
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql);
            pstmt.setInt(1, articoloId);
            pstmt.setInt(2, ristoranteId);
            if(pstmt.executeUpdate() > 0) {
                this.db.closeConnection();
                return "articolo_aggiunto";
            } else {
                this.db.closeConnection();
                return "articolo_non_aggiunto";
            }
        } catch(PSQLException e) {
            this.db.closeConnection();
            return "articolo_non_aggiunto";
        }
    }

    public ObservableList<Ristorante> getListaRistoranti() throws SQLException {
        this.ristoranti = FXCollections.observableArrayList();
        ResultSet rs = this.db.queryBuilder(this.table,"1 > 0");
        while (rs.next()){
            this.ristoranti.add(new Ristorante(rs.getInt("ristoranteid"), rs.getString("nome"),
                    rs.getString("indirizzo"),rs.getString("telefono"),
                    rs.getDate("datadiapertura").toLocalDate(),getArticoli(rs.getInt("ristoranteid"))));
        }
        return this.ristoranti;
    }
}
