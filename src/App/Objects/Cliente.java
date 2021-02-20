package App.Objects;

import App.DAO.ClienteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.time.LocalDate;

public class Cliente {

    /**********Attributi**********/

    Integer clienteId;
    String nome;
    String cognome;
    String email;
    String telefono;
    String ruolo;
    LocalDate dataNascita;
    Indirizzo indirizzoAttivo;
    boolean auth;
    Carrello carrello;
    ObservableList<Ordine> ordini;
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
        this.ruolo = "cliente";
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

    public Indirizzo getIndirizzoAttivo() throws SQLException {
        if(this.indirizzoAttivo == null) {
            int indirizzoAttivoId = this.clienteDAO.getIndirizzoAttivo(this.clienteId);
            if(indirizzoAttivoId != 0) {
                this.indirizzoAttivo = new Indirizzo(indirizzoAttivoId);
            }
        }
        return indirizzoAttivo;
    }

    public void setIndirizzoAttivo(Indirizzo indirizzoAttivo) throws SQLException {
        this.indirizzoAttivo = indirizzoAttivo;
        if(indirizzoAttivo == null)
            this.clienteDAO.aggiornaIndirizzo(null);
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

    public ObservableList<Indirizzo> getIndirizzi() {
        return indirizzi;
    }

    public void setIndirizzi(ObservableList<Indirizzo> indirizzi) {
        this.indirizzi = indirizzi;
    }

    public Carrello getCarrello() throws SQLException {
        if(this.carrello == null)
            this.carrello = new Carrello();
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public ObservableList<Ordine> getOrdini() {
        if(this.ordini == null)
            this.ordini = FXCollections.observableArrayList();
        return ordini;
    }

    public void setOrdini(ObservableList<Ordine> ordini) {
        this.ordini = ordini;
    }

    /**********Metodi di funzionalità**********/

    public boolean login(String email, String password) throws SQLException {
        return this.clienteDAO.loginConf(email, password);
    }

    public String registra(Cliente cliente, String passwordHash) throws SQLException {
        return this.clienteDAO.registerConf(cliente, passwordHash);
    }

    public String aggiungiIndirizzo(Indirizzo indirizzo) throws SQLException {
        return indirizzo.aggiungiIndirizzoDB(this);
    }

    public String aggiornaIndirizzoAttivoDB(Integer indirizzoId) throws SQLException {
        return this.clienteDAO.aggiornaIndirizzo(indirizzoId);
    }

    public String effettuaOrdine() {
        try {
            getOrdini().add(new Ordine(this.getCarrello().getCarrelloId()));
            return "ordine_effettuato";
        } catch(Exception e) {
            return e.getMessage();
        }
    }

    public String diventaRider(String patente, String veicolo) throws SQLException {
        return this.clienteDAO.diventaRider(this.getClienteId(), patente, veicolo);
    }

    public ObservableList<Ordine> getOrdiniDB() throws SQLException {
        return this.clienteDAO.getOrdini(this.clienteId);
    }

    public ObservableList<Indirizzo> getIndirizziDB() throws SQLException {
        return this.clienteDAO.getIndirizzi(this.clienteId);
    }

    public void reset(){
        instance = null;
    }

    /**********Metodi di supporto**********/

    protected String getNomeDB(Integer id) throws SQLException {
        return this.clienteDAO.getNome(id);
    }

}