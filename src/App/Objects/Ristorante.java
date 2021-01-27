package App.Objects;

import App.DAO.RistoranteDAO;
import java.sql.SQLException;
import java.time.LocalDate;

public class Ristorante {

    /**********Attributi**********/

    private Integer ristoranteId;
    private String nome;
    private Indirizzo indirizzo;
    private String telefono;
    private LocalDate dataDiApertura;
    private RistoranteDAO ristoranteDAO;

    /**********Metodi**********/

    /**********Costruttori**********/

    public Ristorante(Integer ristoranteId) throws SQLException {
        this.ristoranteDAO = new RistoranteDAO();
        this.updateFields(this.ristoranteDAO.getRistorante(ristoranteId));
    }

    public Ristorante(String nome, Indirizzo indirizzo, String telefono, LocalDate dataDiApertura) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.dataDiApertura = dataDiApertura;
        this.ristoranteDAO = new RistoranteDAO();
    }

    /**********Getter e setter**********/

    public Integer getRistoranteId() {
        return ristoranteId;
    }

    public void setRistoranteId(Integer ristoranteId) {
        this.ristoranteId = ristoranteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) { this.nome = nome;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
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

    /**********Metodi di supporto**********/

    public void updateFields(Ristorante ristorante){
        this.ristoranteId = ristorante.getRistoranteId();
        this.nome = ristorante.getNome();
        this.indirizzo = ristorante.getIndirizzo();
        this.telefono = ristorante.getTelefono();
        this.dataDiApertura = ristorante.getDataDiApertura();
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
