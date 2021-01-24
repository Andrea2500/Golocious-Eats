package App.Scenes.Controller;

import java.io.IOException;
import java.sql.SQLException;

public class OrdiniEffettuatiController extends BaseSceneController {

    public void ordinaBtn() throws IOException {
        sceneController.ordina();
    }

    public void impostazioniBtn() throws IOException {
        sceneController.impostazioni();
    }

    public void logoutBtn() throws IOException, SQLException {
        sceneController.logout();
    }

}
