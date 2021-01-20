package App.Scenes.Controller;

import App.Objects.Cliente;

public class LoginController extends BaseSceneController {

    String email;
    String password;

    public void loginBtnClick() throws Exception {
        if(getVisibility("nomeField")){
            setVisibility("nomeField", false);
            setVisibility("cognomeField", false);
            setVisibility("telefonoField", false);
            setVisibility("dataNascitaPick", false);
        } else {
            this.email = getValue("emailField", "textfield");
            this.password = getValue("passwordField", "passwordfield");
            Cliente cliente = new Cliente(this.email);
            if(cliente.loginConf(this.email, this.password)) {
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
