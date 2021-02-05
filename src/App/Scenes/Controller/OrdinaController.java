package App.Scenes.Controller;

import App.Controller.OrdinazioneController;
import App.Objects.Articolo;
import App.Objects.Carrello;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrdinaController extends BaseSceneController implements Initializable {

    @FXML VBox ristorantiVBox;
    @FXML VBox menuVBox;
    @FXML VBox carrelloVBox;
    Carrello carrello;
    OrdinazioneController ordinazioneController;


    public OrdinaController() {
        this.carrello = new Carrello();
        this.ordinazioneController = new OrdinazioneController();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getListaRistoranti();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void getListaRistoranti() throws SQLException {
        ObservableList<Ristorante> ristoranti = this.ordinazioneController.getListaRistoranti();
        for(Ristorante ristorante:ristoranti){
            HBox hBox = new HBox();
            Label nomeRistorante = new Label(ristorante.getNome());
            Button selezionaRistorante = new Button("Seleziona");
            selezionaRistorante.setOnAction(actionEvent -> mostraArticoli(ristorante.getArticoli()));
            hBox.getChildren().addAll(nomeRistorante,selezionaRistorante);
            ristorantiVBox.getChildren().add(hBox);
        }


    }

    private void mostraArticoli(ObservableList<Articolo> articoli) {
        this.menuVBox.getChildren().clear();
        for (Articolo articolo: articoli){
            HBox hbox = new HBox();
            VBox infoArticolo = new VBox();
            Label nomeArticolo = new Label(articolo.getNome());
            Label ingredientiArticolo = new Label(articolo.getIngredienti());
            infoArticolo.getChildren().addAll(nomeArticolo,ingredientiArticolo);
            Label prezzoArticolo = new Label(articolo.getPrezzo());
            Button aggiungiAlCarrello = new Button("Aggiungi al carrello");
            aggiungiAlCarrello.setOnAction(actionEvent -> this.aggiungiAlCarrello(articolo));
            this.menuVBox.getChildren().addAll(infoArticolo,prezzoArticolo,aggiungiAlCarrello);
        }
    }

    private void aggiungiAlCarrello(Articolo articolo) {
        this.carrello.aggiungiAlCarrello(articolo);
        this.aggiornaCarrello();
    }

    private void aggiornaCarrello() {
        this.carrelloVBox.getChildren().clear();
        for(Articolo articolo:this.carrello.getArticoliCarrello()){
            HBox hBox = new HBox();
            Label nomeArticolo = new Label(articolo.getNome());
            Label prezzoArticolo = new Label(articolo.getPrezzo());
            hBox.getChildren().addAll(nomeArticolo,prezzoArticolo);
            this.carrelloVBox.getChildren().add(hBox);
        }
    }


}
