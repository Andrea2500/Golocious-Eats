package App.Scenes.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController extends BaseSceneController {

    public void loginBtnClick() throws Exception{
        if(getVisibility("nomeField")){
            setVisibility("nomeField", false);
            setVisibility("cognomeField", false);
            setVisibility("telefonoField", false);
            setVisibility("dataNascitaPick", false);

        } else{
            //crea account
            this.sceneController.setScene("HomeCliente");
        }
    }

    public void creaAccBtnClick() throws Exception {
        if (!getVisibility("nomeField")) {
            setVisibility("nomeField", true);
            setVisibility("cognomeField", true);
            setVisibility("telefonoField", true);
            setVisibility("dataNascitaPick", true);
        } else{
            //crea account
            this.sceneController.setScene("HomeCliente");
        }
    }

}
