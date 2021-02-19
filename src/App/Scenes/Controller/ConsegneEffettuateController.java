package App.Scenes.Controller;

import App.Objects.Cliente;
import App.Objects.Indirizzo;
import App.Objects.Ordine;
import App.Objects.Rider;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConsegneEffettuateController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    private Rider rider;
    @FXML private TableView<Ordine> consegneEffettuateTable;
    @FXML private TableColumn<Ordine,Integer> idColonna;
    @FXML private TableColumn<Ordine,String> ristoranteColonna;
    @FXML private TableColumn<Ordine,String> dataColonna;
    @FXML private TableColumn<Ordine, Indirizzo> indirizzoColonna;
    @FXML private Label totaleConsegneLabel;

    /**********Costruttori**********/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.rider = new Rider(Cliente.getInstance().getClienteId(), true, false);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        idColonna.setCellValueFactory(new PropertyValueFactory<>("ordineId"));
        ristoranteColonna.setCellValueFactory(new PropertyValueFactory<>("ristorante"));
        dataColonna.setCellValueFactory(new PropertyValueFactory<>("dataOrdine"));
        indirizzoColonna.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
        try {
            this.showOrder();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**********Metodi di funzionalità**********/

    private void showOrder() throws SQLException {
        ObservableList<Ordine> data = this.rider.getConsegne();
        int count = (int) data.stream().count();
        if(count > 0) {
            this.totaleConsegneLabel.setText("Numero di consegne effettuate: "+count);
        }
        consegneEffettuateTable.setItems(data);
    }

}
