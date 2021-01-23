package App.Controllers;

import App.Config.ErroriDB;
import App.Objects.Cliente;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AuthController extends Controller {

    Cliente cliente;


    public AuthController() throws SQLException {
        this.cliente = Cliente.getInstance();
    }

    public boolean Login(String email,String password) throws NoSuchAlgorithmException, SQLException {
        ResultSet rs = cliente.getClienteDAO().LoginConf(email, this.PasswordHash(password));
        if(rs.next()) {
            cliente = Cliente.getInstance();
            cliente.setNome(rs.getString("nome"));
            cliente.setCognome(rs.getString("cognome"));
            cliente.setEmail(email);
            cliente.setTelefono(rs.getString("telefono"));
            cliente.setDataNascita(rs.getDate("datadinascita").toLocalDate());
            cliente.setIndirizzoAttivo(rs.getInt("Indirizzoattivo"));
            cliente.setID(rs.getInt("clienteid"));
            cliente.setAuth(true);
            return true;
        } else {
            new ErroriDB().getErrorMessage("login_fallito");
            return false;
        }
    }

    public boolean Register(String nome, String cognome, String email, String password, String telefono, LocalDate dataNascita) throws NoSuchAlgorithmException, SQLException {
        if(cliente.getClienteDAO().RegisterConf(nome, cognome, email, this.PasswordHash(password), telefono, dataNascita)){
            this.Login(email, this.PasswordHash(password));
            return true;
        } else return false;
    }

    private String PasswordHash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encoded = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return this.bytesToHex(encoded);
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void Logout(){
        cliente.Reset();
        return;
    }

}