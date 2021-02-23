package App.Scenes.Controller;

import App.Objects.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrdinaController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    @FXML VBox ristorantiVBox;
    @FXML VBox menuVBox;
    @FXML VBox carrelloVBox;
    @FXML Label indirizzoLabel;
    @FXML Label totaleLabel;
    Cliente cliente;
    Carrello carrello;
    ObservableList<Ristorante> ristoranti;
    Ristorante ristoranteSelezionato;
    ObservableList<Articolo> articoli;

    /**********Metodi**********/

    /**********Costruttori**********/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.cliente = Cliente.getInstance();
            this.carrello = this.cliente.getCarrello();
            this.ristoranti = new Ristorante().getListaRistorantiDB();
            this.ristoranteSelezionato = new Ristorante(this.carrello.getRistoranteId());
            mostraRistoranti();
            mostraArticoli(this.trovaRistorante(this.ristoranti, this.carrello.getRistoranteId()).getArticoli(), this.carrello.getRistoranteId());
            mostraCarrello();
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        Indirizzo indirizzoAttivo = null;
        try {
            indirizzoAttivo = this.cliente.getIndirizzoAttivo();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        if(indirizzoAttivo == null) {
            indirizzoLabel.setText("Imposta un indirizzo attivo prima di ordinare");
        } else {
            indirizzoLabel.setText("Indirizzo attivo: "+indirizzoAttivo);
        }
    }

    /**********Metodi di bottoni**********/

    public void svuotaCarrelloBtn() throws SQLException {
        this.carrello.svuotaCarrello();
        this.cliente.setCarrello(this.carrello);
        mostraCarrello();
        resetErrori();
    }

    public void ordinaOraBtn() throws SQLException {
        resetErrori();
        aggiornaTotale();
        if(this.cliente.getIndirizzoAttivo() != null && carrello.getArticoli().size()>0) {
            String messaggio = this.cliente.effettuaOrdine();
            if(messaggio.equals("ordine_effettuato")) {
                this.cliente.setCarrello(null);
                this.carrello = this.cliente.getCarrello();
                mostraCarrello();
                totaleLabel.setText("Grazie per averci scelto!");
            } else {
                setErroriDB(messaggio);
            }
        } else {
            setErrori();
        }
    }

    /**********Metodi di funzionalità**********/

    private void eliminaDalCarrello(int indice) throws SQLException {
        this.carrello.eliminaDalCarrello(indice);
        this.mostraCarrello();
        this.cliente.setCarrello(this.carrello);
    }

    private void aggiungiAlCarrello(Articolo articolo) throws SQLException {
        this.carrello.setRistoranteId(ristoranteSelezionato.getRistoranteId());
        this.carrello.aggiungiAlCarrello(articolo);
        this.mostraCarrello();
        this.cliente.setCarrello(this.carrello);
    }

    private void aggiornaTotale() {
        float totale = 0;
        ObservableList<Articolo> articoli = this.carrello.getArticoli();
        if(articoli.size()>0) {
            for(Articolo articolo : articoli) {
                totale += Float.parseFloat(articolo.getPrezzo().replace(" €", "").replace(",", "."));
            }
            totaleLabel.setText("Totale: "+String.format("%.2f", totale).concat(" €"));
        } else {
            totaleLabel.setText("Inserisci articoli nel carrello");
        }
    }

    /**********Metodi di supporto**********/

    private void mostraRistoranti() throws SQLException {
        for(Ristorante ristorante : this.ristoranti){
            HBox hBox = new HBox();
            Label nomeRistorante = new Label(ristorante.getNome());
            nomeRistorante.wrapTextProperty().set(true);
            nomeRistorante.setStyle("-fx-font-weight: bolder; -fx-font-size: 14px");
            hBox.alignmentProperty().set(Pos.CENTER_LEFT);
            hBox.getChildren().add(nomeRistorante);
            hBox.getStyleClass().add("elementoOrdina");
            hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    this.ristoranteSelezionato = ristorante;
                    this.articoli = ristorante.getArticoli();
                    this.mostraArticoli(this.articoli, ristorante.getRistoranteId());
                    this.mostraCarrello();
                    resetColoriRistorante(ristorantiVBox);
                    ((Node) event.getSource()).getStyleClass().add("ristoranteSelezionato");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
            if(ristorante.getRistoranteId() == this.carrello.getRistoranteId()) {
                hBox.getStyleClass().add("ristoranteSelezionato");
            }
            if(ristorante.equals(ristoranti.get(ristoranti.size()-1))) {
                hBox.setStyle("-fx-border-width: 0px");
            }
            ristorantiVBox.getChildren().add(hBox);
        }
    }

    private void mostraArticoli(ObservableList<Articolo> articoli, int ristoranteId) throws SQLException {
        if(ristoranteId != this.carrello.getRistoranteId()){
            this.carrello.svuotaCarrello();
        }
        this.carrello.setRistoranteId(ristoranteId);
        this.menuVBox.getChildren().clear();
        String categoria = "";
        for (Articolo articolo : articoli){
            if(!articolo.getCategoria().equals(categoria)) {
                HBox categoriaHBox = new HBox();
                Label categoriaLabel = new Label(articolo.getCategoria());
                categoriaHBox.setStyle("-fx-pref-height: 30px");
                categoriaHBox.getStyleClass().add("categoria");
                categoriaHBox.getChildren().add(categoriaLabel);
                menuVBox.getChildren().add(categoriaHBox);
                categoria = articolo.getCategoria();
            }
            HBox hBox = new HBox();
            VBox vBox1 = new VBox();
            VBox vBox2 = new VBox();
            Label nomeArticolo = new Label(articolo.getNome());
            Label ingredientiArticolo = new Label(articolo.getIngredienti());
            Label prezzoArticolo = new Label(articolo.getPrezzo());
            nomeArticolo.wrapTextProperty().set(true);
            nomeArticolo.setStyle("-fx-font-weight: bolder; -fx-font-size: 13px");
            ingredientiArticolo.wrapTextProperty().set(true);
            prezzoArticolo.setStyle("-fx-font-weight: bolder; -fx-font-size: 13px");
            vBox1.setStyle("-fx-pref-width: 190px; -fx-spacing: 5px");
            vBox1.alignmentProperty().set(Pos.CENTER_LEFT);
            vBox2.setStyle("-fx-pref-width: 45px");
            vBox2.alignmentProperty().set(Pos.CENTER_RIGHT);
            hBox.alignmentProperty().set(Pos.CENTER_LEFT);
            hBox.getStyleClass().add("elementoOrdina");
            if(articolo.isDisponibile()){
                hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    try {
                        this.aggiungiAlCarrello(articolo);
                        resetErrori();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                });
            }else{
                hBox.setStyle("-fx-cursor: pointer");
                hBox.setDisable(true);
            }


            vBox1.getChildren().add(nomeArticolo);
            if(articolo.getIngredienti() != null && !articolo.getIngredienti().equals("")) {
                vBox1.getChildren().add(ingredientiArticolo);
            }
                vBox2.getChildren().add(prezzoArticolo);
            if(articolo.getIngredienti() != null && !articolo.getIngredienti().equals("")) {
                hBox.setStyle("-fx-pref-height: 160px");
            }
            if(articolo.equals(articoli.get(articoli.size()-1))) {
                hBox.setStyle("-fx-border-width: 0px");
            }
            hBox.getChildren().addAll(vBox1, vBox2);
            this.menuVBox.getChildren().add(hBox);
        }
    }

    private void mostraCarrello() {
        this.carrelloVBox.getChildren().clear();
        if(carrello == null) {
            return;
        }
        ObservableList<Articolo> articoli = this.carrello.getArticoli();
        int indice = 0;
        for(Articolo articolo : articoli){
            HBox hBox = new HBox();
            VBox vBox1 = new VBox();
            VBox vBox2 = new VBox();
            Label nomeArticolo = new Label(articolo.getNome());
            Label prezzoArticolo = new Label(articolo.getPrezzo());
            nomeArticolo.wrapTextProperty().set(true);
            nomeArticolo.setStyle("-fx-font-weight: bolder; -fx-font-size: 12px");
            prezzoArticolo.setStyle("-fx-font-weight: bolder; -fx-font-size: 12px");
            vBox1.setStyle("-fx-pref-width: 195px");
            vBox2.setStyle("-fx-pref-width: 45px");
            vBox1.alignmentProperty().set(Pos.CENTER_LEFT);
            vBox2.alignmentProperty().set(Pos.CENTER_LEFT);
            hBox.alignmentProperty().set(Pos.CENTER_LEFT);
            int finalIndice = indice;
            hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    this.eliminaDalCarrello(finalIndice);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
            indice++;
            hBox.getStyleClass().add("elementoOrdina");
            vBox1.getChildren().add(nomeArticolo);
            vBox2.getChildren().add(prezzoArticolo);
            if(indice == articoli.size()) {
                hBox.setStyle("-fx-border-width: 0px");
            }
            hBox.getChildren().addAll(vBox1, vBox2);
            this.carrelloVBox.getChildren().add(hBox);
        }
        this.aggiornaTotale();
    }

    private Ristorante trovaRistorante(ObservableList<Ristorante> ristoranti, int ristoranteId) {
        for (Ristorante ristorante : ristoranti) {
            if (ristorante.getRistoranteId().equals(ristoranteId)) {
                return ristorante;
            }
        }
        return null;
    }

    /**********Metodi di ripristino e di errori**********/

    private void resetColoriRistorante(VBox ristorantiVBox) {
        ristorantiVBox.getChildren().forEach(node -> {
            node.getStyleClass().remove("ristoranteSelezionato");
            node.getStyleClass().add("elementoOrdina");
        });
    }

    private void setErrori() throws SQLException {
        if(this.cliente.getIndirizzoAttivo() == null) {
            this.indirizzoLabel.setStyle("-fx-text-fill: #ff0000");
        }
        if(this.carrello.getArticoli().size() <= 0) {
            this.totaleLabel.setStyle("-fx-text-fill: #ff0000");
        }
    }

    private void resetErrori() {
        indirizzoLabel.setStyle("-fx-text-fill: #fab338");
        totaleLabel.setStyle("-fx-text-fill: #fab338");
        sceneController.setVisibile("erroreLabel", false);
    }

    private void setErroriDB(String messaggio) {
        switch (messaggio) {
            case "nessun_rider" -> {
                errore("erroreLabel", "Nessun rider disponibile, riprova più tardi", false);
                sceneController.setVisibile("erroreLabel", true);
            }
            case "carrello_articoli_disponibili" -> {
                errore("erroreLabel", "Uno o più articoli nel carrello non sono più disponibili", false);
                sceneController.setVisibile("erroreLabel", true);
            }
        }
    }

}
