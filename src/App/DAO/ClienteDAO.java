package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Cliente;
import org.postgresql.util.PSQLException;
import java.sql.*;

public class ClienteDAO {

    String table;

    Cliente cliente;
    Database db;
    ErroriDB edb = new ErroriDB();


    public ClienteDAO() {
        this.db = new Database();
        this.table = "Cliente";
    }

    public boolean loginConf(String email, String password) throws SQLException {
        this.db.setConnection();
        String sql = "select * from "+this.table+" where email = ? and password = ?";
        PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        this.db.closeConnection();
        if(rs.next()){
            this.cliente = Cliente.getInstance();
            cliente = Cliente.getInstance();
            cliente.setNome(rs.getString("nome"));
            cliente.setCognome(rs.getString("cognome"));
            cliente.setEmail(email);
            cliente.setTelefono(rs.getString("telefono"));
            cliente.setDataNascita(rs.getDate("datadinascita").toLocalDate());
            cliente.setIndirizzoAttivo(rs.getInt("Indirizzoattivo"));
            cliente.setId(rs.getInt("clienteid"));
            cliente.setAuth(true);
            cliente.setRole(this.getRole(cliente.getId()));
            cliente.setObject();
            return true;
        }
        return false;
    }

    public String registerConf(Cliente cliente, String password) throws SQLException {
        try {
            this.db.setConnection();
            String sql = "insert into " + this.table + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCognome());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, password);
            pstmt.setString(5, cliente.getTelefono());
            pstmt.setDate(6, Date.valueOf(cliente.getDataNascita()));
            if(pstmt.executeUpdate() > 0){
                this.db.closeConnection();
                return "cliente_registrato";
            }else{
                this.db.closeConnection();
                return "signup_fallito";
            }
        } catch(PSQLException e) {
            this.db.closeConnection();
            return edb.getErrorMessage(e.getMessage());
        }
    }

    public String getRole(Integer id) throws SQLException {
        ResultSet role;
        String where = "clienteid = '"+id+"'";
        role = db.queryBuilder("gestore",where);
        if(role.next())
            return "gestore";
        where = "riderid = '"+id+"'";
        role = db.queryBuilder("rider",where);
        if(role.next())
            return "rider";
        return "cliente";
    }

    public String updateIndirizzoAttivo(Integer id) throws SQLException {
        try{
            String sql = "UPDATE "+this.table+" SET indirizzoattivo = '"+id+"' WHERE clienteid = "+ Cliente.getInstance().getId();
            this.db.setConnection();
            if(this.db.getConnection().createStatement().executeUpdate(sql)==1){
                this.db.closeConnection();
                return "indirizzo_aggiornato";
            }else{
                this.db.closeConnection();
                return "errore_aggiornamento_indirizzo";
            }
        }catch(PSQLException e){
            this.db.closeConnection();
            return edb.getErrorMessage(e.getMessage());
        }
    }


}