package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Cliente;
import App.Objects.Indirizzo;
import App.Objects.Ordine;
import App.Objects.Rider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.text.SimpleDateFormat;

public class ClienteDAO {

    /**********Attributi**********/

    private String tabella;
    private Cliente cliente;
    private ObservableList<Ordine> ordini;
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

    public String getNome(Integer clienteId) throws SQLException {
        String where = "clienteid = '"+clienteId+"'";
        ResultSet rs = this.db.queryBuilder(this.tabella,where);
        if(rs.next()) {
            return rs.getString("nome")+" "+rs.getString("cognome");
        } else {
            return "Utente non trovato";
        }
    }

    public String aggiungiIndirizzoConf(Indirizzo indirizzo) throws SQLException {
        try {
            this.db.setConnection();
            String sql = "insert into indirizzo values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, indirizzo.getCliente().getClienteId());
            pstmt.setString(2, indirizzo.getPaese());
            pstmt.setString(3, indirizzo.getProvincia());
            pstmt.setString(4, indirizzo.getCitta());
            pstmt.setString(5, indirizzo.getCap());
            pstmt.setString(6, indirizzo.getIndirizzoCivico());
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

    public String aggiornaIndirizzoAttivo(Integer indirizzoId) throws SQLException {
        try{
            String sql = "UPDATE "+this.tabella +" SET indirizzoattivo = "+((indirizzoId != null)?indirizzoId: "NULL" )+" WHERE clienteid = "+ Cliente.getInstance().getClienteId();
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

    public String diventaRider(int clienteId, String patente, String veicolo) throws SQLException {
        try {
            this.db.setConnection();
            String sql = "insert into rider values (?, ?, ?)";
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, clienteId);
            pstmt.setString(2, patente);
            pstmt.setString(3, veicolo.substring(0,1).toLowerCase());
            if(pstmt.executeUpdate() > 0){
                this.db.closeConnection();
                return "rider_aggiunto";
            }else{
                this.db.closeConnection();
                return "aggiunta_rider_fallita";
            }
        } catch(PSQLException e) {
            this.db.closeConnection();
            System.out.println(e.getMessage());
            return edb.getMessaggioErrore(e.getMessage());
        }
    }

    public ObservableList<Ordine> getOrdini(Integer clienteId) throws SQLException {
        this.ordini = FXCollections.observableArrayList();
        this.db.setConnection();
        String sql = "SELECT * FROM ordine o inner join carrello c on o.ordineid = c.carrelloid where clienteid = ?";
        PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, clienteId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            this.ordini.add(new Ordine(rs.getInt("ordineid"), rs.getInt("ristoranteid"),
                    new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp("dataordine")), rs.getFloat("totale"),
                    rs.getInt("riderid"),rs.getBoolean("consegnato")));
        }
        db.closeConnection();
        return this.ordini;
    }

}