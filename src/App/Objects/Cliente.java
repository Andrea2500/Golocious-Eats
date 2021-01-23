package App.Objects;

import App.DAO.ClienteDAO;
import java.sql.SQLException;
import java.time.LocalDate;

public class Cliente {

    String nome,cognome,email,telefono;
    LocalDate dataNascita;
    Integer indirizzoAttivo, id;
    boolean auth;

    ClienteDAO cdao;


    public static Cliente instance;
    public static Cliente getInstance() throws SQLException {
        if (instance == null)
            instance = new Cliente();
        return instance;
    }

    public Cliente() throws SQLException {
        this.cdao = new ClienteDAO();
        this.auth = false;
    }

    public ClienteDAO getClienteDAO() {
        return this.cdao;
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

    public void setIndirizzoAttivo(Integer indirizzoAttivo) {
        this.indirizzoAttivo = indirizzoAttivo;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public boolean isAuth() {
        return this.auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
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