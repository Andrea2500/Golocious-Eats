package App.DAO;

import App.Config.Database;
import App.Objects.Cliente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    Database db;
    Connection conn;
    String table;
    Cliente cliente;

    public ClienteDAO() throws SQLException {
        this.db = new Database();
        this.conn = db.getConnection();
        this.table = "Cliente";
    }

    public boolean loginConf(String email, String password) throws SQLException {
        ResultSet rs = this.db.getConnection().createStatement().executeQuery("select * from "+this.table);
        while(rs.next()) {
            if(rs.getString("email").equals(email) && rs.getString("password").equals(password)) {
                return true;
                //this.cliente = Cliente.getInstance(rs.getInt("clienteid"));
            }
        }
        return false;
    }


    public void getCliente(Integer id,Cliente obj) throws SQLException {
        ResultSet rs = this.db.getConnection().createStatement().executeQuery("SELECT * FROM " + this.table + " WHERE clienteid=" + id);
        if (rs.next()) {
            obj.setNome(rs.getString("nome"));
            obj.setCognome(rs.getString("cognome"));
            obj.setEmail(rs.getString("email"));
            obj.setTelefono(rs.getString("telefono"));
            obj.setIndirizzoAttivo(rs.getInt("indirizzoattivo"));
            obj.setDataDiNascita(rs.getDate("datadinascita"));
            obj.setDataDiNascita(rs.getDate("datadinascita"));
        } else {
            System.out.println("Utente non trovato");
        }
    }

}
