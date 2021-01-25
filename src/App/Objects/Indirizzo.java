package App.Objects;

public class Indirizzo {

    String paese;
    String provincia;
    String cap;
    String citta;
    String indirizzo;

    Cliente cliente;

    public Indirizzo(String paese, String provincia, String cap, String citta, String indirizzo) {
        this.paese = paese;
        this.provincia = provincia;
        this.cap = cap;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.cliente = Cliente.getInstance();
    }

}
