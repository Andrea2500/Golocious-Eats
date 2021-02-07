package App.DAO;

import App.Config.Database;
import App.Objects.ElementoStatistiche;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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

    public ObservableList<ElementoStatistiche> getStatistiche(Float daPrezzo, Float aPrezzo, String veicolo, LocalDate daData, LocalDate aData, int ristornateId) throws SQLException {
        try{
            String sql = "SELECT ar.articoloid, ar.nome, ar.prezzo, ar.articoloid, sum(ar.prezzo) totalearticolo, count(*) nvenduti" +
                    " FROM rider r NATURAL JOIN ordine o inner join articoloincarrello ac on o.ordineid = ac.carrelloid NATURAL JOIN articolo ar" +
                    " where o.ristoranteid = ?";
            if(daPrezzo != null){
                sql = sql.concat(" AND ar.prezzo >= ? ");
            }
            if(aPrezzo != null){
                sql =sql.concat(" AND ar.prezzo <= ?");
            }
            sql = sql.concat(" AND r.veicolo in "+veicolo);
            if(daData != null){
               sql = sql.concat(" AND o.dataordine >= ?");
            }
            if(aData != null){
                sql = sql.concat(" AND o.dataordine <= ?");
            }
            sql = sql.concat(" GROUP BY ar.articoloid");
            this.db.setConnection();
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql);
            int index = 2;
            pstmt.setInt(1,ristornateId);
            if(daPrezzo != null){
                pstmt.setFloat(index,daPrezzo);
                index++;
            }
            if(aPrezzo != null){
                pstmt.setFloat(index,aPrezzo);
                index++;
            }
            if(daData != null){
                pstmt.setDate(index, Date.valueOf(daData));
                index++;
            }
            if(aData != null){
                pstmt.setDate(index, Date.valueOf(aData));
                index++;
            }
            ResultSet rs = pstmt.executeQuery();
            this.elementiStatiche = FXCollections.observableArrayList();
            while(rs.next()){
                this.elementiStatiche.add(new ElementoStatistiche(rs.getString("nome"),rs.getFloat("prezzo"),
                        rs.getInt("nvenduti"),rs.getFloat("totalearticolo")));
            }
            return this.elementiStatiche;
        }catch (PSQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
