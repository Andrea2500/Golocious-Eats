package App.Objects;

import App.DAO.ClienteDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class Cliente {

    String nome,cognome,email,telefono;
    Integer id;
    Integer indirizzoAttivo;
    Date dataDiNascita;
    ClienteDAO cdao;

    public static Cliente instance;
    public static Cliente getInstance(String email) throws SQLException {
        if (instance == null)
            instance = new Cliente(email);
        return instance;
    }

    public Cliente(String email) throws SQLException {
        this.cdao = new ClienteDAO();
        this.email = email;
    }

    public boolean loginConf(String email, String password) throws SQLException {
        return this.cdao.loginConf(email, password);
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

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public void setData(Integer id) throws SQLException {
        cdao.getCliente(id,this);
    }

}
