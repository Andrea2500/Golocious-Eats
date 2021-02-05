package App.Scenes.Controller;

import App.Controller.OrdinazioneController;
import App.Objects.Articolo;
import App.Objects.Carrello;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
            mostraRistoranti();
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void mostraRistoranti() throws SQLException {
        ObservableList<Ristorante> ristoranti = this.ordinazioneController.getListaRistoranti();
        for(Ristorante ristorante : ristoranti){
            HBox hBox = new HBox();
            Label nomeRistorante = new Label(ristorante.getNome());
            nomeRistorante.wrapTextProperty().set(true);
            nomeRistorante.setStyle("-fx-font-weight: bolder; -fx-font-size: 14px");
            hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.mostraArticoli(ristorante.getArticoli()));
            hBox.alignmentProperty().set(Pos.CENTER_LEFT);
            hBox.getChildren().addAll(nomeRistorante);
            hBox.getStyleClass().add("elementoOrdina");
            hBox.setStyle("-fx-pref-height: 75px");
            ristorantiVBox.getChildren().add(hBox);
        }
    }

    private void mostraArticoli(ObservableList<Articolo> articoli) {
        this.menuVBox.getChildren().clear();
        String categoria = "";
        for (Articolo articolo : articoli){
            if(!articolo.getCategoria().equals(categoria)) {
                HBox categoriaHBox = new HBox();
                Label categoriaLabel = new Label(articolo.getCategoria());
                categoriaHBox.getStyleClass().add("categoria");
                categoriaHBox.setStyle("-fx-pref-height: 30px");
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
            hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.aggiungiAlCarrello(articolo));
            hBox.alignmentProperty().set(Pos.CENTER_LEFT);
            hBox.setStyle("-fx-pref-height: 160px");
            hBox.getStyleClass().add("elementoOrdina");
            if(ingredientiArticolo.getText() == null || ingredientiArticolo.getText().equals("")) {
                ingredientiArticolo.setManaged(false);
                hBox.setStyle("-fx-pref-height: 75px");
            }
            vBox1.getChildren().addAll(nomeArticolo, ingredientiArticolo);
            vBox2.getChildren().addAll(prezzoArticolo);
            hBox.getChildren().addAll(vBox1, vBox2);
            this.menuVBox.getChildren().addAll(hBox);
        }
    }

    private void aggiungiAlCarrello(Articolo articolo) {
        this.carrello.aggiungiAlCarrello(articolo);
        this.mostraCarrello();
    }

    private void mostraCarrello() {
        this.carrelloVBox.getChildren().clear();
        for(Articolo articolo : this.carrello.getArticoliCarrello()){
            HBox hBox = new HBox();
            Label nomeArticolo = new Label(articolo.getNome());
            Label prezzoArticolo = new Label(articolo.getPrezzo());
            hBox.getChildren().addAll(nomeArticolo,prezzoArticolo);
            this.carrelloVBox.getChildren().add(hBox);
        }
    }

}