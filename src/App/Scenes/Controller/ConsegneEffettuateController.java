package App.Scenes.Controller;

import App.Controller.MostraConsegneEffettuateController;
import App.Objects.Ordine;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConsegneEffettuateController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    private MostraConsegneEffettuateController mostraConsegneEffettuateController;
    @FXML private TableView<Ordine> consegneEffettuateTable;
    @FXML private TableColumn<Ordine,Integer> idColonna;
    @FXML private TableColumn<Ordine,String> ristoranteColonna;
    @FXML private TableColumn<Ordine,String> dataColonna;

    /**********Costruttori**********/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColonna.setCellValueFactory(new PropertyValueFactory<>("ordineId"));
        ristoranteColonna.setCellValueFactory(new PropertyValueFactory<>("ristorante"));
        dataColonna.setCellValueFactory(new PropertyValueFactory<>("dataOrdine"));
        try{
            this.showOrder();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**********Metodi di funzionalità**********/

    private void showOrder() throws SQLException {
        this.mostraConsegneEffettuateController = new MostraConsegneEffettuateController();
        ObservableList<Ordine> data = this.mostraConsegneEffettuateController.getConsegne();
        consegneEffettuateTable.setItems(data);
    }

}
