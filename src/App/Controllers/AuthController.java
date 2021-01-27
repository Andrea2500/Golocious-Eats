package App.Controllers;

import App.Objects.Cliente;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AuthController {

    /**********Attributi**********/

    Cliente cliente;

    /**********Metodi**********/

    /**********Costruttori**********/

    public AuthController() {
        this.cliente = Cliente.getInstance();
    }

    public AuthController(String nome, String cognome, String email, String telefono, LocalDate dataNascita) {
        this.cliente = Cliente.getInstance();
        cliente.setNome(nome);
        cliente.setCognome(cognome);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setDataNascita(dataNascita);
    }

    /**********Metodi di funzionalità**********/

    public String login(String email, String password) throws NoSuchAlgorithmException, SQLException {
        if(cliente.login(email, this.passwordHash(password))) {
            return "login_autorizzato";
        } else {
            return "login_fallito";
        }
    }

    public String register(Cliente cliente, String password) throws NoSuchAlgorithmException, SQLException {
        String message = cliente.registra(cliente, this.passwordHash(password));
        if(message.equals("cliente_registrato")) {
            this.login(cliente.getEmail(), this.passwordHash(password));
        }
        return message;
    }

    /**********Metodi di supporto**********/

    private String passwordHash(String password) throws NoSuchAlgorithmException {
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

}