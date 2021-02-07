package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Cliente;
import App.Objects.Indirizzo;
import org.postgresql.util.PSQLException;

import java.sql.*;

public class ClienteDAO {

    /**********Attributi**********/

    private String tabella;
    private Cliente cliente;
    protected Database db;
    private ErroriDB edb = new ErroriDB();

    /**********Metodi**********/

    /**********Costruttori**********/

    public ClienteDAO() {
        this.db = new Database();
        this.tabella = "Cliente";
    }

    /**********Metodi di funzionalità**********/

    public boolean loginConf(String email, String password) throws SQLException {
        this.db.setConnection();
        String sql = "select * from "+this.tabella +" where email = ? and password = ?";
        PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        this.db.closeConnection();
        if(rs.next()){
            this.cliente = Cliente.getInstance();
            cliente.setNome(rs.getString("nome"));
            cliente.setCognome(rs.getString("cognome"));
            cliente.setEmail(email);
            cliente.setTelefono(rs.getString("telefono"));
            cliente.setDataNascita(rs.getDate("datadinascita").toLocalDate());
            cliente.setClienteId(rs.getInt("clienteid"));
            cliente.setAuth(true);
            cliente.setRuolo(this.getRole(cliente.getClienteId()));
            return true;
        }
        return false;
    }

    public String registerConf(Cliente cliente, String password) throws SQLException {
        try {
            this.db.setConnection();
            String sql = "insert into " + this.tabella + " values (?, ?, ?, ?, ?, ?)";
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
            return edb.getMessaggioErrore(e.getMessage());
        }
    }

    /**********Getter e Setter**********/

    public String getRole(Integer id) throws SQLException {
        ResultSet role;
        String where = "clienteid = '"+id+"'";
        role = db.queryBuilder("gestore", where);
        if(role.next())
            return "gestore";
        where = "riderid = '"+id+"'";
        role = db.queryBuilder("rider", where);
        if(role.next())
            return "rider";
        return "cliente";
    }

    public String getNomeDB(Integer clienteId) throws SQLException {
        String where = "clienteid = '"+clienteId+"'";
        ResultSet rs = this.db.queryBuilder(this.tabella,where);
        if(rs.next()) {
            return rs.getString("nome")+" "+rs.getString("cognome");
        } else {
            return "Utente non trovato";
        }
    }

    public String aggiornaIndirizzoAttivo(Integer indirizzoId) throws SQLException {
        try{
            String sql = "UPDATE "+this.tabella +" SET indirizzoattivo = '"+ indirizzoId +"' WHERE clienteid = "+ Cliente.getInstance().getClienteId();
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
            return edb.getMessaggioErrore(e.getMessage());
        }
    }

    public int getIndirizzoAttivo(int clienteId) throws SQLException {
        String where = "clienteid = '"+clienteId+"'";
        ResultSet rs = this.db.queryBuilder(this.tabella, where);
        if(rs.next()) {
            return (rs.getInt("indirizzoattivo"));
        } else {
            return 0;
        }
    }
}