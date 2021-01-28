package App.Objects;

import App.DAO.ClienteDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;

public class Cliente {

    /**********Attributi**********/

    String nome;
    String cognome;
    String email;
    String telefono;
    String role = "cliente";
    LocalDate dataNascita;
    //Todo rendere di tipo indirizzo e modificare dove serve
    Integer indirizzoAttivo;
    Integer id;
    boolean auth;
    Gestore gestore;
    Rider rider;
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
    }

    /**********Getter e setter**********/

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public Gestore getGestore() {
        return gestore;
    }

    public void setGestore(Gestore gestore) {
        this.gestore = gestore;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public ObservableList<Indirizzo> getIndirizziDB() throws SQLException {
        return this.clienteDAO.getIndirizziDB();
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

    public String aggiornaIndirizzoAttivo(Integer indirizzoid) throws SQLException {
        return this.clienteDAO.updateIndirizzoAttivo(indirizzoid);
    }

    /**********Metodi di supporto**********/

    public void reset(){
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
            case "gestore" -> this.gestore = new Gestore(this.id);
            case "rider" -> this.rider = new Rider(this.id);
        }
    }

    public String getNomeDB(Integer id) throws SQLException {
        return this.clienteDAO.getNomeDB(id);
    }

    public void addIndirizzo(Indirizzo indirizzo){
        this.indirizzi.add(indirizzo);
    }

    public void rimuoviIndirizzo(Indirizzo indirizzo) {
        this.indirizzi.remove(indirizzo);
    }
}