package App.Scenes.Controller;

import App.Objects.Cliente;

public class LoginController extends BaseSceneController {

    public void loginBtnClick() throws Exception {
        if(getVisibility("nomeField")){
            setVisibility("nomeField", false);
            setVisibility("cognomeField", false);
            setVisibility("telefonoField", false);
            setVisibility("dataNascitaPick", false);
        } else {
            String email = getValue("emailField", "textfield");
            String password = getValue("passwordField", "passwordfield");
            Cliente cliente = Cliente.getInstance(email);
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
