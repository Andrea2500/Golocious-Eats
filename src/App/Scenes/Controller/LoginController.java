package App.Scenes.Controller;

import App.Controllers.AuthController;
import App.Objects.Cliente;

import java.sql.SQLException;

public class LoginController extends BaseSceneController {

    AuthController auth;

    public LoginController() throws SQLException {
        this.auth = new AuthController();
    }

    public void loginBtnClick() throws Exception {
        if(getVisibility("nomeField")){
            setVisibility("nomeField", false);
            setVisibility("cognomeField", false);
            setVisibility("telefonoField", false);
            setVisibility("dataNascitaPick", false);
        } else {
            String email = getValue("emailField", "textfield");
            String password = getValue("passwordField", "passwordfield");
            Cliente cliente = Cliente.getInstance();
            auth.Login(email,password);
            if(cliente.loginConf(email, password)) {
            this.sceneController.setScene("HomeCliente");
            } else {
                System.out.println("kitemmuort");
            }
        }
    }

    public void creaAccBtnClick() throws Exception {
        if (!getVisibility("nomeField")) {
            setVisibility("nomeField", true);
            setVisibility("cognomeField", true);
            setVisibility("telefonoField", true);
            setVisibility("dataNascitaPick", true);
        } else {
            //crea account
            this.sceneController.setScene("HomeCliente");
        }
    }

}
