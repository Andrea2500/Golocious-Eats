package App.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController extends Controller{

    public void setHomeCliente(ActionEvent e) throws IOException {
        Parent tableParent = FXMLLoader.load(getClass().getResource("HomeCliente.fxml"));
        Scene tableScene = new Scene(tableParent, 800, 450);
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(tableScene);
    }

    public void setLogin(ActionEvent e) throws IOException {
        Parent tableParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene tableScene = new Scene(tableParent, 800, 450);
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(tableScene);
    }

}
