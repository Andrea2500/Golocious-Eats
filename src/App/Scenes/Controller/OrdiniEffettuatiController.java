package App.Scenes.Controller;

import App.Objects.Cliente;
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

    Cliente cliente;
    Ordine ordine;
    @FXML private TableView<Ordine> orderTable;
    @FXML private TableColumn<Ordine,Integer> idCol;
    @FXML private TableColumn<Ordine,String> ristoranteCol;
    @FXML private TableColumn<Ordine,String> dataCol;
    @FXML private TableColumn<Ordine,String> totaleCol;
    @FXML private TableColumn<Ordine,String> riderCol;
    @FXML private TableColumn<Ordine,String> statoCol;

    public OrdiniEffettuatiController() throws SQLException {
        this.cliente = Cliente.getInstance();
        this.ordine = new Ordine();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ristoranteCol.setCellValueFactory(new PropertyValueFactory<>("ristorante"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("dataOrdine"));
        totaleCol.setCellValueFactory(new PropertyValueFactory<>("totale"));
        riderCol.setCellValueFactory(new PropertyValueFactory<>("rider"));
        statoCol.setCellValueFactory(new PropertyValueFactory<>("stato"));
        try {
            this.showOrder(cliente.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private void showOrder(Integer id) throws SQLException {
        ObservableList<Ordine> data = this.ordine.getOrdini();
        orderTable.setItems(data);
    }


}
