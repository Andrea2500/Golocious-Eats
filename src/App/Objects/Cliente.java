package App.Objects;

import App.DAO.ClienteDAO;
import java.sql.SQLException;
import java.time.LocalDate;

public class Cliente {

    String nome,cognome,email,telefono;
    LocalDate dataNascita;
    Integer indirizzoAttivo, id;
    boolean auth;

    ClienteDAO clienteDAO;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public Integer getIndirizzoAttivo() {
        return indirizzoAttivo;
    }

    public void setIndirizzoAttivo(Integer indirizzoAttivo) {
        this.indirizzoAttivo = indirizzoAttivo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

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



    public void Reset(){
        this.nome = null;
        this.cognome = null;
        this.email = null;
        this.telefono = null;
        this.dataNascita = null;
        this.id = null;
        this.setAuth(false);
    }

}