package App.DAO;

import App.Config.Database;
import App.Objects.Indirizzo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IndirizzoDAO {

    /**********Attributi**********/

    String table;
    ObservableList<Indirizzo> listaIndirizzi;
    Database db;

    /**********Metodi**********/

    /**********Costruttori**********/

    public IndirizzoDAO() {
        this.db = new Database();
        this.table = "Indirizzo";
    }

    /**********Metodi di funzionalità**********/

    public String aggiungiIndirizzoConf(Indirizzo indirizzo) throws SQLException {
        try {
            this.db.setConnection();
            String sql = "insert into " + this.table + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, indirizzo.getCliente().getId());
            pstmt.setString(2, indirizzo.getPaese());
            pstmt.setString(3, indirizzo.getProvincia());
            pstmt.setString(4, indirizzo.getCitta());
            pstmt.setString(5, indirizzo.getCap());
            pstmt.setString(6, indirizzo.getIndirizzo());
            if(pstmt.executeUpdate() > 0){
                this.db.closeConnection();
                return "indirizzo_aggiunto";
            }else{
                this.db.closeConnection();
                return "aggiunta_indirizzo_fallita";
            }
        } catch(PSQLException e) {
            this.db.closeConnection();
            return "aggiunta_indirizzo_fallita";
        }
    }

    public ObservableList<Indirizzo> getIndirizziDelCliente(Integer clienteid) throws SQLException {
        this.listaIndirizzi = FXCollections.observableArrayList();
        String where = "clienteid = '"+clienteid+"'";
        ResultSet rs = this.db.queryBuilder(this.table,where);
        while(rs.next()){
            this.listaIndirizzi.add(new Indirizzo(rs.getInt("indirizzoid"), rs.getString("paese"),
                    rs.getString("provincia"),rs.getString("citta"),
                    rs.getString("cap"),rs.getString("indirizzo")));
        }
        return this.listaIndirizzi;
    }

}
