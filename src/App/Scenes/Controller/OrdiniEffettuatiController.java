package App.Scenes.Controller;

import App.Controllers.MostraOrdiniEffettuatiController;
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

public class OrdiniEffettuatiController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    MostraOrdiniEffettuatiController mostraOrdiniEffettuatiController;
    @FXML private TableView<Ordine> orderTable;
    @FXML private TableColumn<Ordine,Integer> idCol;
    @FXML private TableColumn<Ordine,String> ristoranteCol;
    @FXML private TableColumn<Ordine,String> dataCol;
    @FXML private TableColumn<Ordine,String> totaleCol;
    @FXML private TableColumn<Ordine,String> riderCol;
    @FXML private TableColumn<Ordine,String> statoCol;

    /**********Costruttori**********/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ristoranteCol.setCellValueFactory(new PropertyValueFactory<>("ristorante"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("dataOrdine"));
        totaleCol.setCellValueFactory(new PropertyValueFactory<>("totale"));
        riderCol.setCellValueFactory(new PropertyValueFactory<>("rider"));
        statoCol.setCellValueFactory(new PropertyValueFactory<>("stato"));
        try {
            this.showOrder();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**********Metodi di funzionalità**********/

    private void showOrder() throws SQLException {
        this.mostraOrdiniEffettuatiController = new MostraOrdiniEffettuatiController();
        ObservableList<Ordine> data = this.mostraOrdiniEffettuatiController.getOrdini();
        orderTable.setItems(data);
    }

}
