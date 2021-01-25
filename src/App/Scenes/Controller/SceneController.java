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
        Scene tableScene = new Scene(tableParent, 900, 480);
        this.window.setScene(tableScene);
    }

    public Scene getScene() {
        return this.window.getScene();
    }

    public void setVisibility(String id, boolean toggle) {
        this.getScene().lookup("#" + id).setVisible(toggle);
        this.getScene().lookup("#" + id).setManaged(toggle);
    }

    public void setActiveBtn(String id) {
        getScene().lookup("#"+id).setStyle("-fx-background-color: #e19a1f; -fx-cursor: pointer");
    }

    public void login() throws IOException {
        this.setScene("Ordina");
        setActiveBtn("ordinaBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void ordina() throws IOException {
        this.setScene("Ordina");
        setActiveBtn("ordinaBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void ordiniEffettuati() throws IOException {
        this.setScene("OrdiniEffettuati");
        setActiveBtn("ordiniEffettuatiBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void consegna() throws IOException {
        this.setScene("Consegna");
        setActiveBtn("consegnaBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void consegneEffettuate() throws IOException {
        this.setScene("ConsegneEffettuate");
        setActiveBtn("consegneEffettuateBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void gestisciRistorante() throws IOException {
        this.setScene("GestisciRistorante");
        setActiveBtn("gestisciRistoranteBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void impostazioni() throws IOException {
        this.setScene("Impostazioni");
        setActiveBtn("impostazioniBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void logout() throws IOException {
        Cliente.getInstance().Reset();
        this.setScene("Login");
    }

    public void setMenu(String role){
        switch (role){
            case "gestore":
                this.setVisibility("gestisciRistoranteBtn",true);
                break;
            case "rider":
                this.setVisibility("consegnaBtn",true);
                this.setVisibility("consegneEffettuateBtn",true);
                break;
            default:
                break;
        }
    }

}