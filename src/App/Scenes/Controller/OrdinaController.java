package App.Scenes.Controller;

import App.Objects.Cliente;

import java.sql.SQLException;

public class OrdinaController extends BaseSceneController {

    Cliente cliente;

    public OrdinaController() throws SQLException {
        this.cliente = Cliente.getInstance();
    }
}
