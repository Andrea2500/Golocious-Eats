package App.DAO;

import App.Config.Database;
import App.Objects.ElementoStatistiche;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ElementoStatisticheDAO {

    Database db;
    ObservableList<ElementoStatistiche> elementiStatiche;

    public ElementoStatisticheDAO() {
        this.db = new Database();
    }

    public ObservableList<ElementoStatistiche> getStatistiche(Float daPrezzo, Float aPrezzo, String veicolo, LocalDate daData, LocalDate aData, int ristornateId) throws SQLException {
        String sql = "SELECT ar.articoloid, ar.nome, ar.prezzo, ar.articoloid, sum(ar.prezzo) totalearticolo, count(*) nvenduti" +
                " FROM rider r NATURAL JOIN ordine o inner join articoloincarrello ac on o.ordineid = ac.carrelloid NATURAL JOIN articolo ar" +
                " where o.ristoranteid = ?";
        if(daPrezzo != null){
            sql.concat(" AND ar.prezzo >= ? ");
        }
        if(aPrezzo != null){
            sql.concat(" AND ar.prezzo <= ?");
        }
        sql.concat(" AND r.veicolo in ?");
        if(daData != null){
            sql.concat(" AND o.dataordine >= ?");
        }
        if(aData != null){
            sql.concat(" AND o.dataordine <= ?");
        }
        sql.concat(" group by ar.articoloid;");
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
        pstmt.setString(index,veicolo);
        index++;
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

    }
}
