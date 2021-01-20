package App.Scenes.Controller;



import App.Objects.Cliente;

import java.sql.SQLException;

public class HomeClienteController extends BaseSceneController {

    public void backBtn() throws Exception {
        sceneController.setScene("Login");
    }

    public void mostraClienti() throws SQLException {
        Cliente test = Cliente.getInstance(1);
        test.printData();

    }

}
