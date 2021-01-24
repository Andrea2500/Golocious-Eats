package App.Scenes.Controller;

import App.Controllers.Controller;
import App.Objects.Cliente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class SceneController extends Controller {

    Stage window;
    String Login = "Login", Ordina = "Ordina", OrdiniEffettuati = "OrdiniEffettuati",
            Impostazioni = "Impostazioni";


    public static SceneController instance;
    public static SceneController getInstance() {
        if (instance == null)
            instance = new SceneController();
        return instance;
    }

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public void setScene(String scene) throws IOException {
        Parent tableParent = FXMLLoader.load(getClass().getResource("../FXML/"+scene+".fxml"));
        Scene tableScene = new Scene(tableParent, 900, 475);
        this.window.setScene(tableScene);
    }

    public Scene getScene() {
        return this.window.getScene();
    }


    public void login() throws IOException {
        this.setScene(Ordina);
        getScene().lookup("#ordinaBtn").setStyle("-fx-background-color: #e19a1f");
    }

    public void ordina() throws IOException {
        this.setScene(Ordina);
        getScene().lookup("#ordinaBtn").setStyle("-fx-background-color: #e19a1f");
    }

    public void ordiniEffettuati() throws IOException {
        this.setScene(OrdiniEffettuati);
        getScene().lookup("#ordiniEffettuatiBtn").setStyle("-fx-background-color: #e19a1f");
    }

    public void impostazioni() throws IOException {
        this.setScene(Impostazioni);
        getScene().lookup("#impostazioniBtn").setStyle("-fx-background-color: #e19a1f");
    }

    public void logout() throws IOException, SQLException {
        Cliente.getInstance().Reset();
        this.setScene(Login);
    }


}