package App.Objects;

import App.DAO.RistoranteDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;

public class Ristorante {

    /**********Attributi**********/

    private Integer ristoranteId;
    private String nome;
    private String indirizzo;
    private String telefono;
    private LocalDate dataDiApertura;
    private ObservableList<Articolo> articoli;
    private RistoranteDAO ristoranteDAO;

    /**********Metodi**********/

    /**********Costruttori**********/

    public Ristorante() {
        this.ristoranteDAO = new RistoranteDAO();
    }

    public Ristorante(Integer ristoranteId) throws SQLException {
        this.ristoranteDAO = new RistoranteDAO();
        this.aggiornaCampi(this.ristoranteDAO.getRistorante(ristoranteId));
    }

    public Ristorante(Integer ristoranteId, String nome, String indirizzo, String telefono, LocalDate dataDiApertura, ObservableList<Articolo> articoli) {
        this.ristoranteId = ristoranteId;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.dataDiApertura = dataDiApertura;
        this.ristoranteDAO = new RistoranteDAO();
        this.articoli = articoli;
    }

    public Ristorante(String nome, String indirizzo, String telefono, LocalDate dataDiApertura) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.dataDiApertura = dataDiApertura;
        this.ristoranteDAO = new RistoranteDAO();
    }

    /**********Getter e Setter**********/

    public Integer getRistoranteId() {
        return ristoranteId;
    }

    public void setRistoranteId(Integer ristoranteId) {
        this.ristoranteId = ristoranteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getDataDiApertura() {
        return dataDiApertura;
    }

    public void setDataDiApertura(LocalDate dataDiApertura) {
        this.dataDiApertura = dataDiApertura;
    }

    public ObservableList<Articolo> getArticoli() {
        return articoli;
    }

    public void setArticoli(ObservableList<Articolo> articoli) {
        this.articoli = articoli;
    }

    /**********Metodi di funzionalità**********/

    public String switchDisponibilitaArticoloDB(boolean toggle, int ristoranteid, int articoloid) throws SQLException {
        return this.ristoranteDAO.switchDisponibilita(toggle, ristoranteid, articoloid);
    }

    public String aggiungiArticoloEsistenteDB(int articoloId) throws SQLException {
        return this.ristoranteDAO.aggiungiArticoloEsistente(this.ristoranteId, articoloId);
    }

    public String eliminaArticoloDaMenuDB(int articoloid) throws SQLException {
        return this.ristoranteDAO.eliminaDaMenu(this.ristoranteId, articoloid);
    }

    public ObservableList<Articolo> getArticoliAltriRistorantiDB(Integer ristoranteId) throws SQLException {
        return this.ristoranteDAO.getArticoliAltriRistorantiDB(ristoranteId);
    }

    public ObservableList<Ristorante> getRistorantiDB(Integer ristoranteId) throws SQLException {
        return this.ristoranteDAO.getRistoranti(ristoranteId);
    }

    public int setRistoranteDB() throws Exception {
        try{
            return this.ristoranteDAO.setRistorante(this);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public ObservableList<Ristorante> getListaRistorantiDB() throws SQLException {
        return this.ristoranteDAO.getListaRistoranti();
    }


    /**********Metodi di supporto**********/


    public void aggiornaCampi(Ristorante ristorante){
        this.ristoranteId = ristorante.getRistoranteId();
        this.nome = ristorante.getNome();
        this.indirizzo = ristorante.getIndirizzo();
        this.telefono = ristorante.getTelefono();
        this.dataDiApertura = ristorante.getDataDiApertura();
        this.articoli = ristorante.getArticoli();
    }

    @Override
    public String toString() {
        return this.nome;
    }

}
