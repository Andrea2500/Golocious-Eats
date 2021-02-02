package App.Objects;

import App.DAO.ArticoloDAO;

public class Articolo {

    /**********Attributi**********/

    int articoloId;
    String nome;
    String prezzo;
    String categoria;
    String ingredienti;
    boolean disponibile;
    ArticoloDAO articoloDAO;


    /**********Metodi**********/

    /**********Costruttori**********/

    public Articolo() {
    }

    public Articolo(String nome, String prezzo, String categoria, String ingredienti) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.categoria = categoria;
        this.ingredienti = ingredienti;
        this.articoloDAO = new ArticoloDAO();
    }

    public Articolo(String nome, String prezzo, String categoria, String ingredienti, int articoloId, boolean disponibile) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.ingredienti = ingredienti;
        this.articoloId = articoloId;
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

    public int getArticoloId() {
        return articoloId;
    }

    public void setArticoloId(int articoloId) {
        this.articoloId = articoloId;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    /**********Metodi di funzionalità**********/

    public String setArticoloDB() {
        try {
            this.articoloId = this.articoloDAO.setArticolo(this);
            return "articolo_aggiunto";
        } catch (Exception e){
            return e.getMessage();
        }
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
            return this.nome+" - "+this.categoria+" - DISPONIBILE";
        else
            return this.nome+" - "+this.categoria+" - NON DISPONIBILE";
    }

}
