package App.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestoreDAO extends ClienteDAO{



    String table = "gestore";
    //TODO Aggiungere lista ristoranti e modificare metodo getRistoranti
    public List<Integer> getRistoranti(Integer id) throws SQLException {
        this.db.setConnection();
        List<Integer> ristoranti = new ArrayList<>();
        String sql = "SELECT ristoranteid FROM "+this.table+" WHERE clienteid = '"+id+"'";
        ResultSet rs = this.db.getConnection().createStatement().executeQuery(sql);
        this.db.closeConnection();
        while(rs.next()){
            ristoranti.add(rs.getInt(1));
        }
        return ristoranti;
    }


}
