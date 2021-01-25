package App.Scenes.Controller;

import App.DAO.OrdineDAO;
import App.Objects.Cliente;
import App.Objects.Ordine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrdiniEffettuatiController extends BaseSceneController implements Initializable {

    Cliente cliente;
    @FXML private TableView<Ordine> orderTable;
    @FXML private TableColumn<Ordine,Integer> idCol;
    @FXML private TableColumn<Ordine,String> ristoranteCol;
    @FXML private TableColumn<Ordine,String> dataCol;
    @FXML private TableColumn<Ordine,String> totaleCol;
    @FXML private TableColumn<Ordine,String> riderCol;
    @FXML private TableColumn<Ordine,String> statoCol;

    public OrdiniEffettuatiController() throws SQLException {
        this.cliente = Cliente.getInstance();
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
        ObservableList<Ordine> data = FXCollections.observableArrayList();
        OrdineDAO ordineDao = new OrdineDAO();
        ResultSet ordini = ordineDao.getOrders(id);
        while (ordini.next()){

            data.add(new Ordine(ordini.getInt("ordineid"),((Integer) ordini.getInt("ristoranteid")).toString(),ordini.getDate("dataordine").toString(), ordini.getString("totale"), ((Integer) ordini.getInt("riderid")).toString(),ordini.getBoolean("consegnato")));

        }
        orderTable.setItems(data);
    }


}
