package App.Scenes.Controller;

import App.Controller.OrdinazioneController;
import App.Objects.Articolo;
import App.Objects.Carrello;
import App.Objects.Ristorante;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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


    public OrdinaController() throws SQLException {
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
            nomeRistorante.setStyle("-fx-font-weight: bolder");
            hBox.getChildren().addAll(nomeRistorante);
            hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.mostraArticoli(ristorante.getArticoli(), ristorante.getRistoranteId() ));
            hBox.alignmentProperty().set(Pos.CENTER_LEFT);
            hBox.getStyleClass().add("elementoOrdina");
            hBox.setStyle("-fx-pref-height: 70px");
            ristorantiVBox.getChildren().add(hBox);
        }
    }

    private void mostraArticoli(ObservableList<Articolo> articoli, int ristoranteId) {
        this.carrello.setRistoranteId(ristoranteId);
        this.carrello.pulisciCarrello();
        this.menuVBox.getChildren().clear();
        String categoria = "";
        for (Articolo articolo : articoli){

            HBox hBox = new HBox();
            if(categoria.equals("") || !articolo.getCategoria().equals(categoria)) {
                HBox categoriaHBox = new HBox();
                categoriaHBox.getChildren().add(new Label(articolo.getCategoria()));
                menuVBox.getChildren().add(categoriaHBox);
                categoria = articolo.getCategoria();
            }
            VBox vBox = new VBox();
            vBox.prefWidthProperty().set(200);
            Label nomeArticolo = new Label(articolo.getNome());
            Label ingredientiArticolo = new Label(articolo.getIngredienti());
            if(ingredientiArticolo.getText() == null) {
                ingredientiArticolo.setManaged(false);
            }
            nomeArticolo.wrapTextProperty().set(true);
            nomeArticolo.setStyle("-fx-font-weight: bolder");
            ingredientiArticolo.wrapTextProperty().set(true);
            vBox.getChildren().addAll(nomeArticolo,ingredientiArticolo);
            Label prezzoArticolo = new Label(articolo.getPrezzo());
            hBox.getChildren().addAll(vBox, prezzoArticolo);
            hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    this.aggiungiAlCarrello(articolo);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            vBox.alignmentProperty().set(Pos.CENTER_LEFT);
            hBox.alignmentProperty().set(Pos.CENTER_LEFT);
            hBox.getStyleClass().add("elementoOrdina");
            hBox.setStyle("-fx-pref-height: 120px");
            this.menuVBox.getChildren().addAll(hBox);
        }
    }

    private void aggiungiAlCarrello(Articolo articolo) throws SQLException {
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