package App.Objects;

import App.DAO.ArticoloDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Articolo {

    /**********Attributi**********/

    String nome;
    String prezzo;
    String categoria;
    String ingredienti;
    int articoloid;
    boolean disponibile;
    ArticoloDAO articoloDAO;


    /**********Metodi**********/

    /**********Costruttori**********/

    public Articolo() {
        this.articoloDAO = new ArticoloDAO();
    }

    public Articolo(String nome, String prezzo, String categoria, String ingredienti, int articoloid, boolean disponibile) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.ingredienti = ingredienti;
        this.articoloid = articoloid;
        selezionaCategoria(categoria);
        this.disponibile = disponibile;
    }

    /**********Getter e Setter**********/

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        selezionaCategoria(categoria);
    }

    public String getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(String ingredienti) {
        this.ingredienti = ingredienti;
    }

    public int getArticoloid() {
        return articoloid;
    }

    public void setArticoloid(int articoloid) {
        this.articoloid = articoloid;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    /**********Metodi di supporto**********/

    public void selezionaCategoria(String categoria){
        switch (categoria){
            case "a" -> this.categoria = "Antipasti";
            case "b" -> this.categoria = "Hamburger";
            case "p" -> this.categoria = "Pizze";
            case "t" -> this.categoria = "Pizze in teglia";
            case "d" -> this.categoria = "Dolci";
            case "v" -> this.categoria = "Bevande";
            case "w" -> this.categoria = "Vino";
            default -> this.categoria = "Categoria inesistente";
        }
    }

    @Override
    public String toString() {
        if(this.disponibile)
            return this.nome+" - DISPONIBILE";
        else
            return this.nome+" - NON DISPONIBILE";
    }

}
