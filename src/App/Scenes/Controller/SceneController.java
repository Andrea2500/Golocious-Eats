package App.Scenes.Controller;

import App.Objects.Cliente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

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

    public void login() throws IOException {
        this.setScene("Ordina");
        setCliccatoBtn("ordinaBtn");
        setMenu(Cliente.getInstance().getRuolo());
    }

    public void ordina() throws IOException {
        this.setScene("Ordina");
        setCliccatoBtn("ordinaBtn");
        setMenu(Cliente.getInstance().getRuolo());
    }

    public void ordiniEffettuati() throws IOException {
        this.setScene("OrdiniEffettuati");
        setCliccatoBtn("ordiniEffettuatiBtn");
        setMenu(Cliente.getInstance().getRuolo());
    }

    public void consegna() throws IOException {
        this.setScene("Consegna");
        setCliccatoBtn("consegnaBtn");
        setMenu(Cliente.getInstance().getRuolo());
    }

    public void consegneEffettuate() throws IOException {
        this.setScene("ConsegneEffettuate");
        setCliccatoBtn("consegneEffettuateBtn");
        setMenu(Cliente.getInstance().getRuolo());
    }

    public void gestisciRistorante() throws IOException {
        this.setScene("GestisciRistorante");
        setCliccatoBtn("gestisciRistoranteBtn");
        setMenu(Cliente.getInstance().getRuolo());
    }

    public void impostazioni() throws IOException {
        this.setScene("Impostazioni");
        setCliccatoBtn("impostazioniBtn");
        setMenu(Cliente.getInstance().getRuolo());
        if(Cliente.getInstance().getRuolo().equals("cliente"))
            this.setVisibile("diventaRiderBtn",true);
    }

    public void logout() throws IOException {
        Cliente.getInstance().reset();
        this.setScene("Login");
    }

    public void riderLogout() throws IOException {
        logout();
        ((Label) this.getScene().lookup("#erroreLoginLabel")).setText("Congratulazioni! Effettua il login per iniziare a consegnare");
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
        if(role != null) {
            switch (role) {
                case "gestore":
                    this.setVisibile("gestisciRistoranteBtn", true);
                    break;
                case "rider":
                    this.setVisibile("consegnaBtn", true);
                    this.setVisibile("consegneEffettuateBtn", true);
                    break;
                case "cliente":
                    break;
            }
        }
    }


}