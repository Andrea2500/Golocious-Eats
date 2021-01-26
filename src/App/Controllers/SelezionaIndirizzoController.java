package App.Controllers;

import App.DAO.IndirizzoDAO;
import App.Objects.Cliente;
import App.Objects.Indirizzo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SelezionaIndirizzoController {

    IndirizzoDAO indirizzoDAO = new IndirizzoDAO();


    public ObservableList<Indirizzo> getIndirizzi(Integer id) throws SQLException {
        ObservableList<Indirizzo> data = FXCollections.observableArrayList();
        ResultSet result = this.indirizzoDAO.getIndirizziDelCliente(Cliente.getInstance().getId());
        while(result.next()){
            data.add(new Indirizzo(result.getInt("indirizzoid"), result.getString("paese"),result.getString("provincia"),result.getString("citta"),result.getString("cap"),result.getString("indirizzo")));
        }
        return data;
    }

    public String setIndirizzoAttivo(Integer indirizzoid) throws SQLException {
        return Cliente.getInstance().getClienteDAO().updateIndirizzoAttivo(indirizzoid);
    }




}
