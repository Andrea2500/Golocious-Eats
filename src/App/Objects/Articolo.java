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
        this.categoria = selezionaCategoriaDB(categoria);
        this.ingredienti = ingredienti;
        this.articoloDAO = new ArticoloDAO();
    }

    public Articolo(String nome, Float prezzo, String categoria, String ingredienti, int articoloId, boolean disponibile) {
        this.nome = nome;
        this.prezzo = String.format("%.2f", prezzo).concat(" €");
        this.ingredienti = ingredienti;
        this.articoloId = articoloId;
        this.categoria = selezionaCategoria(categoria);
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
        this.categoria = selezionaCategoria(categoria);
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

    public String selezionaCategoria(String categoria){
        switch (categoria){
            case "a" -> {
                return "Antipasti";
            }
            case "b" -> {
                return "Hamburger";
            }
            case "p" -> {
                return "Pizze";
            }
            case "t" -> {
                return "Pizze in teglia";
            }
            case "d" -> {
                return "Dolci";
            }
            case "v" -> {
                return "Bevande";
            }
            case "w" -> {
                return "Vino";
            }
            default -> {
                return "Categoria inesistente";
            }
        }
    }

    public String selezionaCategoriaDB(String categoria){
        switch (categoria){
            case "Antipasti" -> {
                return "a";
            }
            case "Hamburger" -> {
                return "b";
            }
            case "Pizze" -> {
                return "p";
            }
            case "Pizze in teglia" -> {
                return "t";
            }
            case "Dolci" -> {
                return "d";
            }
            case "Bevande" -> {
                return "v";
            }
            case "Vino" -> {
                return "w";
            }
            default -> {
                return "Categoria inesistente";
            }
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
