package App.Scenes.Controller;

import App.Controllers.AuthController;
import App.Objects.Cliente;
import java.time.LocalDate;

public class LoginController extends BaseSceneController {

    AuthController auth;


    public void loginBtnClick() throws Exception {
        resetErrori();
        if(getVisibility("nomeField")){
            setField(false);
        } else {
            String email = getValue("emailField", "textfield");
            String password = getValue("passwordField", "passwordfield");
            if(email.length()>0 && password.length()>0){
                auth = new AuthController();
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
                auth = new AuthController(nome, cognome, email, telefono, dataNascita);
                String messaggio = auth.Register(Cliente.getInstance(), password);
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
            case "signup_fallito", "troppo_lungo" -> errore("erroreLoginLabel", "Si è verificato un errore", false);
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
        sceneController.setVisibility("nomeField", Register);
        sceneController.setVisibility("erroreNomeLabel", Register);
        sceneController.setVisibility("cognomeField", Register);
        sceneController.setVisibility("erroreCognomeLabel", Register);
        sceneController.setVisibility("telefonoField", Register);
        sceneController.setVisibility("erroreTelefonoLabel", Register);
        sceneController.setVisibility("datanascitaField", Register);
        sceneController.setVisibility("erroreDatanascitaLabel", Register);
    }

}