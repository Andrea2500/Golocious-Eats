package App.Objects;

import App.DAO.ClienteDAO;

import java.sql.SQLException;
import java.time.LocalDate;

public class Cliente {

    String nome;
    String cognome;
    String email;
    String telefono;
    String role;
    LocalDate dataNascita;
    Integer indirizzoAttivo, id;
    boolean auth;
    Gestore gestore;
    Rider rider;

    ClienteDAO clienteDAO;

    public static Cliente instance;
    public static Cliente getInstance() throws SQLException {
        if (instance == null)
            instance = new Cliente();
        return instance;
    }

    public Cliente() throws SQLException {
        this.clienteDAO = new ClienteDAO();
        this.auth = false;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public void setIndirizzoAttivo(Integer indirizzoAttivo) {
        this.indirizzoAttivo = indirizzoAttivo;
    }

    public Integer getIndirizzoAttivo() {
        return indirizzoAttivo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public void Reset(){
        this.nome = null;
        this.cognome = null;
        this.email = null;
        this.telefono = null;
        this.dataNascita = null;
        this.id = null;
        this.setAuth(false);
        this.role = null;
    }

    public void setObject() throws SQLException {
        switch (this.role){
            case "gestore":
                this.gestore = new Gestore(this.id);
                break;
            case "rider":
                this.rider = new Rider(this.id);
                break;
            default:
                break;
        }
    }

}