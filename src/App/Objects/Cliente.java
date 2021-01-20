package App.Objects;

import App.DAO.ClienteDAO;

import java.sql.SQLException;
import java.util.Date;

public class Cliente {

    String nome,cognome,email,telefono;
    Integer id;
    Integer indirizzoAttivo;
    Date dataDiNascita;
    ClienteDAO cdao;

    public static Cliente instance;
    public static Cliente getInstance(Integer id) throws SQLException {
        if (instance == null)
            instance = new Cliente(id);
        return instance;
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

    public Cliente(Integer id) throws SQLException {
        this.cdao = new ClienteDAO();
        this.nome = "";
        this.cognome="";
        this.email="";
        this.telefono="";
        this.id=id;
        this.dataDiNascita = new Date();
        this.indirizzoAttivo=0;
        this.setData(id);
    }

    public void setData(Integer id) throws SQLException {
        cdao.getCliente(id,this);
        return;
    }

    public void printData(){
        System.out.println(this.nome+" "+this.cognome);
    }




}
