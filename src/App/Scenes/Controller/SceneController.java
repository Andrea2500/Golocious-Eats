package App.Scenes.Controller;

import App.Controllers.Controller;
import App.Objects.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class SceneController extends Controller {

    Stage window;
    String Login = "Login", HomeCliente = "HomeCliente";

    public static SceneController instance;
    public static SceneController getInstance() {
        if (instance == null)
            instance = new SceneController();
        return instance;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public Stage getWindow() {
        return window;
    }

    public void setScene(String scene) throws IOException {
        Parent tableParent = FXMLLoader.load(getClass().getResource("../FXML/"+scene+".fxml"));
        Scene tableScene = new Scene(tableParent, 800, 450);
        this.window.setScene(tableScene);
    }

    public Scene getScene() {
        return this.window.getScene();
    }

    public void Login() throws IOException {
        this.setScene(HomeCliente);
    }

    public void Logout() throws IOException, SQLException {
        Cliente.getInstance().Reset();
        this.setScene(Login);
    }

}