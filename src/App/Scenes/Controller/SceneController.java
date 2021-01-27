package App.Scenes.Controller;

import App.Objects.Cliente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class SceneController {

    /**********Attributi**********/

    private Stage window;

    /**********Metodi**********/

    /**********Costruttori**********/

    public static SceneController instance;
    public static SceneController getInstance() {
        if (instance == null)
            instance = new SceneController();
        return instance;
    }

    /**********Getter e setter**********/

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public Scene getScene() {
        return this.window.getScene();
    }


    public void setScene(String scene) throws IOException {
        Parent tableParent = FXMLLoader.load(getClass().getResource("../FXML/"+scene+".fxml"));
        Scene tableScene = new Scene(tableParent, 900, 480);
        this.window.setScene(tableScene);
    }

    /**********Metodi di cambio scene**********/

    public void login() throws IOException, SQLException {
        this.setScene("Ordina");
        setCliccatoBtn("ordinaBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void ordina() throws IOException, SQLException {
        this.setScene("Ordina");
        setCliccatoBtn("ordinaBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void ordiniEffettuati() throws IOException, SQLException {
        this.setScene("OrdiniEffettuati");
        setCliccatoBtn("ordiniEffettuatiBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void consegna() throws IOException, SQLException {
        this.setScene("Consegna");
        setCliccatoBtn("consegnaBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void consegneEffettuate() throws IOException, SQLException {
        this.setScene("ConsegneEffettuate");
        setCliccatoBtn("consegneEffettuateBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void gestisciRistorante() throws IOException, SQLException {
        this.setScene("GestisciRistorante");
        setCliccatoBtn("gestisciRistoranteBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void impostazioni() throws IOException, SQLException {
        this.setScene("Impostazioni");
        setCliccatoBtn("impostazioniBtn");
        setMenu(Cliente.getInstance().getRole());
    }

    public void logout() throws IOException, SQLException {
        Cliente.getInstance().reset();
        this.setScene("Login");
    }

    /**********Metodi di supporto**********/

    public void setVisibile(String id, boolean toggle) {
        this.getScene().lookup("#" + id).setVisible(toggle);
        this.getScene().lookup("#" + id).setManaged(toggle);
    }

    public void setCliccatoBtn(String id) {
        getScene().lookup("#"+id).setStyle("-fx-background-color: #e19a1f; -fx-cursor: pointer");
    }

    public void setMenu(String role){
        switch (role){
            case "gestore":
                this.setVisibile("gestisciRistoranteBtn",true);
                break;
            case "rider":
                this.setVisibile("consegnaBtn",true);
                this.setVisibile("consegneEffettuateBtn",true);
                break;
            default:
                break;
        }
    }

}