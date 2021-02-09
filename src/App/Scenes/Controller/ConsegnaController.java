package App.Scenes.Controller;

import App.Controller.EffettuaConsegnaController;
import App.Objects.Ordine;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConsegnaController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    @FXML private VBox consegnaVBox;
    EffettuaConsegnaController effettuaConsegnaController;
    ObservableList<Ordine> consegneAttive;

    /**********Metodi**********/

    /**********Costruttori**********/

    public ConsegnaController() throws SQLException {
        this.effettuaConsegnaController = new EffettuaConsegnaController();
        this.consegneAttive = this.effettuaConsegnaController.getConsegneAttive();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showConsegneAttive();
    }

    /**********Metodi di funzionalità**********/

    private void showConsegneAttive() {
        this.consegnaVBox.getChildren().clear();
        if(consegneAttive.size()>0){
            for (Ordine consegna : consegneAttive) {
                HBox hBox = new HBox();
                VBox vBox1 = new VBox();
                VBox vBox2 = new VBox();
                Label ristorante = new Label("Ristorante di ritiro: " + consegna.getRistorante().toString());
                Label indirizzo = new Label("Indirizzo di consegna: " + consegna.getIndirizzo().toString());
                Label nomeCliente = new Label("Nome cliente: " + consegna.getNomeCliente());
                Label telefonoCliente = new Label("Telefono cliente: " + consegna.getTelefonoCliente());
                Button consegnaBtn = new Button("Termina consegna");
                consegnaBtn.setOnAction(actionEvent -> {
                    try {
                        this.effettuaConsegnaController.consegna(consegna.getOrdineId());
                        this.effettuaConsegnaController.aggiornaConsegne();
                        this.consegneAttive.clear();
                        this.consegneAttive = this.effettuaConsegnaController.getConsegneAttive();
                        this.showConsegneAttive();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                vBox1.alignmentProperty().set(Pos.CENTER_LEFT);
                vBox2.alignmentProperty().set(Pos.CENTER_LEFT);
                hBox.alignmentProperty().set(Pos.CENTER_LEFT);
                vBox2.setStyle("-fx-pref-width: 245px");
                hBox.getStyleClass().add("elementoConsegna");
                vBox1.getChildren().addAll(ristorante, indirizzo);
                vBox2.getChildren().addAll(nomeCliente, telefonoCliente);
                hBox.getChildren().addAll(vBox1, vBox2, consegnaBtn);
                this.consegnaVBox.getChildren().add(hBox);
            }
        } else {
            consegnaVBox.setAlignment(Pos.CENTER);
            Label nessunaConsegna = new Label("Non hai consegne attive al momento");
            nessunaConsegna.setStyle("-fx-font-size: 16px");
            this.consegnaVBox.getChildren().add(nessunaConsegna);
        }
    }

}
