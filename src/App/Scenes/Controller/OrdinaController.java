package App.Scenes.Controller;

import App.Controller.OrdinazioneController;
import App.Objects.Articolo;
import App.Objects.Carrello;
import App.Objects.Cliente;
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
        this.carrello = Cliente.getInstance().getCarrello();
        this.ordinazioneController = new OrdinazioneController();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            mostraRistoranti();
            mostraCarrello();
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
            hBox.alignmentProperty().set(Pos.CENTER_LEFT);
            hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.mostraArticoli(ristorante.getArticoli(), ristorante.getRistoranteId()));
            hBox.getChildren().add(nomeRistorante);
            hBox.getStyleClass().add("elementoOrdina");
            hBox.setStyle("-fx-pref-height: 75px");
            if(ristorante.equals(ristoranti.get(ristoranti.size()-1))) {
                hBox.setStyle("-fx-border-width: 0px; -fx-pref-height: 75px");
            }
            ristorantiVBox.getChildren().add(hBox);
        }
    }

    private void mostraArticoli(ObservableList<Articolo> articoli, int ristoranteId) {
        this.carrello.setRistoranteId(ristoranteId);
        this.carrello.pulisciCarrello();
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
            if(ingredientiArticolo.getText() == null || ingredientiArticolo.getText().equals("")) {
                ingredientiArticolo.setManaged(false);
                hBox.setStyle("-fx-pref-height: 75px");
            }
            prezzoArticolo.setStyle("-fx-font-weight: bolder; -fx-font-size: 13px");
            vBox1.setStyle("-fx-pref-width: 190px; -fx-spacing: 5px");
            vBox1.alignmentProperty().set(Pos.CENTER_LEFT);
            vBox2.setStyle("-fx-pref-width: 45px");
            vBox2.alignmentProperty().set(Pos.CENTER_RIGHT);
            hBox.alignmentProperty().set(Pos.CENTER_LEFT);
            hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    this.aggiungiAlCarrello(articolo);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
            hBox.setStyle("-fx-pref-height: 160px");
            hBox.getStyleClass().add("elementoOrdina");
            vBox1.getChildren().addAll(nomeArticolo, ingredientiArticolo);
            vBox2.getChildren().add(prezzoArticolo);
            if(articolo.getIngredienti() == null || articolo.getIngredienti().equals("")) {
                hBox.setStyle("-fx-pref-height: 75px");
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
        ObservableList<Articolo> articoli = this.carrello.getArticoli();
        int indice = 0;
        for(Articolo articolo : articoli){
            indice++;
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
            hBox.alignmentProperty().set(Pos.CENTER_LEFT);
            /*TODO hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    this.eliminaDalCarrello(articolo);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });*/
            hBox.setStyle("-fx-pref-height: 30px");
            hBox.getStyleClass().add("elementoOrdina");
            vBox1.getChildren().add(nomeArticolo);
            vBox2.getChildren().add(prezzoArticolo);
            if(indice == articoli.size()) {
                hBox.setStyle("-fx-border-width: 0px");
            }
            hBox.getChildren().addAll(vBox1, vBox2);
            this.carrelloVBox.getChildren().add(hBox);
        }
    }


    private void aggiungiAlCarrello(Articolo articolo) throws SQLException {
        this.carrello.aggiungiAlCarrello(articolo);
        this.mostraCarrello();
    }

}