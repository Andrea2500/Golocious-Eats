package App.Controllers;

import App.Objects.Cliente;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class AuthController extends Controller {

    Cliente cliente;


    public AuthController() throws SQLException {
        this.cliente = Cliente.getInstance();
    }

    public void Login(String email,String password) throws NoSuchAlgorithmException {

    }

    private String PasswordHash(String pw) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encoded = digest.digest(pw.getBytes(StandardCharsets.UTF_8));
        String output = new String(encoded,StandardCharsets.UTF_8);
        return output;
    }

}
