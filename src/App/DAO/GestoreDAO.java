package App.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestoreDAO extends ClienteDAO{

    String table = "gestore";

    public List<Integer> getRistoranti(Integer id) throws SQLException {
        Connection conn = this.db.getConnection();
        List<Integer> ristoranti = new ArrayList<>();
        String sql = "SELECT ristoranteid FROM "+this.table+" WHERE clienteid = '"+id+"'";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while(rs.next()){
            ristoranti.add(rs.getInt(1));
        }
        return ristoranti;
    }


}
