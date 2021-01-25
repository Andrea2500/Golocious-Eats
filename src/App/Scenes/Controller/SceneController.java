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

    public void setActiveBtn(String id) {
        getScene().lookup("#"+id).setStyle("-fx-background-color: #e19a1f; -fx-cursor: pointer");
    }

    public void login() throws IOException {
        this.setScene("Ordina");
        //if rider / gestore set visible e managed ai bottoni
        setActiveBtn("ordinaBtn");
    }

    public void ordina() throws IOException {
        this.setScene("Ordina");
        //if rider / gestore set visible e managed ai bottoni
        setActiveBtn("ordinaBtn");
     }

    public void ordiniEffettuati() throws IOException {
        this.setScene("OrdiniEffettuati");
        //if rider / gestore set visible e managed ai bottoni
        setActiveBtn("ordiniEffettuatiBtn");
    }

    public void consegna() throws IOException {
        this.setScene("Consegna");
        //if rider / gestore set visible e managed ai bottoni
        setActiveBtn("consegnaBtn");
    }

    public void consegneEffettuate() throws IOException {
        this.setScene("ConsegneEffettuate");
        //if rider / gestore set visible e managed ai bottoni
        setActiveBtn("consegneEffettuateBtn");
    }

    public void gestisciRistorante() throws IOException {
        this.setScene("GestisciRistorante");
        //if rider / gestore set visible e managed ai bottoni
        setActiveBtn("gestisciRistoranteBtn");
    }

    public void impostazioni() throws IOException {
        this.setScene("Impostazioni");
        //if rider / gestore set visible e managed ai bottoni
        setActiveBtn("impostazioniBtn");
    }

    public void logout() throws IOException, SQLException {
        Cliente.getInstance().Reset();
        this.setScene("Login");
    }

}