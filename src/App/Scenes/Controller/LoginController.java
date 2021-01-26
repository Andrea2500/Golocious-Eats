package App.Scenes.Controller;

import App.Controllers.AuthController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;

public class LoginController extends BaseSceneController {

    AuthController auth;


    public LoginController() throws SQLException {
        this.auth = new AuthController();
    }

    public void loginBtnClick() throws Exception {
        resetErrori();
        if(getVisibility("nomeField")){
            setField(false);
        } else {
            String email = getValue("emailField", "textfield");
            String password = getValue("passwordField", "passwordfield");
            if(email.length()>0 && password.length()>0){
                String messaggio = auth.Login(email,password);
                if(messaggio.equals("login_autorizzato")) {
                    sceneController.login();
                } else {
                    errore("erroreLoginLabel", "Il login non è riuscito", false);
                }
            }else {
                if(email.length()==0){
                    errore("erroreEmailLabel", "Inserisci un'email", true);
                }
                if(password.length()==0){
                    errore("errorePasswordLabel", "Inserisci una password", true);
                }
            }
        }
    }

    public void registratiBtnClick() throws Exception {
        resetErrori();
        if (!getVisibility("nomeField")) {
            setField(true);
        } else {
            String nome = getValue("nomeField", "textfield");
            String cognome = getValue("cognomeField", "textfield");
            String email = getValue("emailField", "textfield");
            String password = getValue("passwordField", "passwordfield");
            String telefono = getValue("telefonoField", "textfield");
            LocalDate dataNascita = getValue("datanascitaField");
            if(nome.length()>0 && cognome.length()>0 && email.length()>0 && password.length()>0 && telefono.length()>0 && eta(dataNascita) >= 14) {
                String messaggio = auth.Register(nome, cognome, email, password, telefono, dataNascita);
                if (messaggio.equals("cliente_registrato")) {
                    sceneController.login();
                } else {
                    setErroriDB(messaggio);
                }
            } else {
                setErrori(nome, cognome, email, password, telefono, dataNascita);
            }
        }
    }

    private void setErroriDB(String messaggio) {
        switch (messaggio) {
            case "ck_email" -> errore("erroreEmailLabel", "Inserisci un'email valida", true);
            case "cliente_email_key" -> errore("erroreEmailLabel", "L'indirizzo email è gia registrato", true);
            case "ck_telefono" -> errore("erroreTelefonoLabel", "Inserisci un numero di telefono valido", true);
            case "cliente_telefono_key" -> errore("erroreTelefonoLabel", "Il numero di telefono è già registrato", true);
            case "signup_fallito" -> errore("erroreLoginLabel", "Si è verificato un errore", false);
        }
    }

    public void setErrori(String nome, String cognome, String email, String password, String telefono, LocalDate dataNascita) {
        if(nome.length()==0){
            errore("erroreNomeLabel", "Inserisci un nome", true);
        }
        if(cognome.length()==0){
            errore("erroreCognomeLabel", "Inserisci un cognome", true);
        }
        if(email.length()==0){
            errore("erroreEmailLabel", "Inserisci un'email", true);
        }
        if(password.length()==0){
            errore("errorePasswordLabel", "Inserisci una password", true);
        }
        if(telefono.length()==0){
            errore("erroreTelefonoLabel", "Inserisci un numero di telefono valido", true);
        }
        if(dataNascita==null){
            errore("erroreDatanascitaLabel", "Inserisci una data di nascita valida", true);
        } else if(eta(dataNascita)<14) {
            errore("erroreDatanascitaLabel", "Devi avere almeno 14 anni", true);
        }
    }


    public void resetErrori() {
        inizializzaLabel("erroreNomeLabel", true);
        inizializzaLabel("erroreCognomeLabel", true);
        inizializzaLabel("erroreEmailLabel", true);
        inizializzaLabel("errorePasswordLabel", true);
        inizializzaLabel("erroreTelefonoLabel", true);
        inizializzaLabel("erroreDatanascitaLabel", true);
        inizializzaLabel("erroreLoginLabel", false);
    }

    public void setField(boolean Register){
        setManagedAndVisible("nomeField", Register);
        setManagedAndVisible("erroreNomeLabel", Register);
        setManagedAndVisible("cognomeField", Register);
        setManagedAndVisible("erroreCognomeLabel", Register);
        setManagedAndVisible("telefonoField", Register);
        setManagedAndVisible("erroreTelefonoLabel", Register);
        setManagedAndVisible("datanascitaField", Register);
        setManagedAndVisible("erroreDatanascitaLabel", Register);
    }

}