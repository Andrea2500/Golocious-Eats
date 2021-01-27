package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Indirizzo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;
import java.sql.*;

public class IndirizzoDAO {

    Connection conn;
    String table;


    Database db;


    public IndirizzoDAO() {
        this.db = new Database();
        this.table = "Indirizzo";
    }

    public String aggiungiIndirizzoConf(Indirizzo indirizzo) throws SQLException {
        try {
            this.conn = db.getConnection();
            String sql = "insert into " + this.table + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, indirizzo.getCliente().getId());
            pstmt.setString(2, indirizzo.getPaese());
            pstmt.setString(3, indirizzo.getProvincia());
            pstmt.setString(4, indirizzo.getCitta());
            pstmt.setString(5, indirizzo.getCap());
            pstmt.setString(6, indirizzo.getIndirizzo());
            if(pstmt.executeUpdate() > 0){
                db.closeConnection(conn);
                return "indirizzo_aggiunto";
            }else{
                db.closeConnection(conn);
                return "aggiunta_indirizzo_fallita";
            }
        } catch(PSQLException e) {
            db.closeConnection(conn);
            return "aggiunta_indirizzo_fallita";
        }
    }

    public ObservableList<Indirizzo> getIndirizziDelCliente(Integer id) throws SQLException {
        ObservableList<Indirizzo> data = FXCollections.observableArrayList();
        String where = "clienteid = '"+id+"'";
        ResultSet rs = this.db.queryBuilder(this.table,where);
        while(rs.next()){
            data.add(new Indirizzo(rs.getInt("indirizzoid"), rs.getString("paese"),
                    rs.getString("provincia"),rs.getString("citta"),
                    rs.getString("cap"),rs.getString("indirizzo")));
        }
        return data;
    }

}
