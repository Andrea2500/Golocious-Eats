package App.Objects;

import App.DAO.ClienteDAO;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Cliente {

    /**********Attributi**********/

    Integer clienteId;
    String nome;
    String cognome;
    String email;
    String telefono;
    String ruolo = "cliente";
    LocalDate dataNascita;
    Indirizzo indirizzoAttivo;
    boolean auth;
    //todo ObservableList<Carrello> carrelli;
    ObservableList<Indirizzo> indirizzi;
    ClienteDAO clienteDAO;

    /**********Metodi**********/

    /**********Costruttori**********/

    public static Cliente instance;
    public static Cliente getInstance() {
        if (instance == null)
            instance = new Cliente();
        return instance;
    }

    public Cliente() {
        this.clienteDAO = new ClienteDAO();
        this.auth = false;
        this.indirizzi = FXCollections.observableArrayList();
    }

    /**********Getter e Setter**********/

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

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public Indirizzo getIndirizzoAttivo() {
        return indirizzoAttivo;
    }

    public void setIndirizzoAttivo(Indirizzo indirizzoAttivo) {
        this.indirizzoAttivo = indirizzoAttivo;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public ObservableList<Indirizzo> getIndirizziDB() throws SQLException {
        return new Indirizzo().getIndirizziDB();
    }

    public ObservableList<Indirizzo> getIndirizzi() {
        return indirizzi;
    }

    public void setIndirizzi(ObservableList<Indirizzo> indirizzi) {
        this.indirizzi = indirizzi;
    }

    /**********Metodi di funzionalità**********/

    public boolean login(String email, String password) throws SQLException {
        return this.clienteDAO.loginConf(email, password);
    }

    public String registra(Cliente cliente, String passwordHash) throws SQLException {
        return this.clienteDAO.registerConf(cliente, passwordHash);
    }

    public String aggiornaIndirizzoAttivo(Integer indirizzoId) throws SQLException {
        return this.clienteDAO.aggiornaIndirizzoAttivo(indirizzoId);
    }

    /**********Metodi di supporto**********/

    public void reset(){
        this.nome = null;
        this.cognome = null;
        this.email = null;
        this.telefono = null;
        this.dataNascita = null;
        this.clienteId = null;
        this.setAuth(false);
        this.ruolo = null;
    }

    public String getNomeDB(Integer id) throws SQLException {
        return this.clienteDAO.getNomeDB(id);
    }

    public void addIndirizzo(Indirizzo indirizzo){
        this.indirizzi.add(indirizzo);
    }

    public void removeIndirizzo(Indirizzo indirizzo) {
        this.indirizzi.remove(indirizzo);
    }
}