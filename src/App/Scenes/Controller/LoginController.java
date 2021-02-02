package App.Scenes.Controller;

import App.Controller.AuthController;

import java.time.LocalDate;

public class LoginController extends BaseSceneController {

    /**********Attributi**********/

    private AuthController auth;

    /**********Metodi**********/

    /**********Metodi di bottoni**********/

    public void loginBtnClick() throws Exception {
        resetErrori();
        if(getVisibility("nomeField")){
            setField(false);
        } else {
            String email = getValue("emailField", "textfield");
            String password = getValue("passwordField", "passwordfield");
            if(email.length()>0 && password.length()>0){
                auth = new AuthController();
                String messaggio = auth.login(email,password);
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
                String messaggio = auth.register(password);
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

    /**********Metodi di supporto**********/

    public void setField(boolean Register){
        sceneController.setVisibile("nomeField", Register);
        sceneController.setVisibile("erroreNomeLabel", Register);
        sceneController.setVisibile("cognomeField", Register);
        sceneController.setVisibile("erroreCognomeLabel", Register);
        sceneController.setVisibile("telefonoField", Register);
        sceneController.setVisibile("erroreTelefonoLabel", Register);
        sceneController.setVisibile("datanascitaField", Register);
        sceneController.setVisibile("erroreDatanascitaLabel", Register);
    }

    /**********Metodi di ripristino e di errori**********/

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
            errore("erroreTelefonoLabel", "Inserisci un numero di telefono", true);
        }
        if(dataNascita==null){
            errore("erroreDatanascitaLabel", "Seleziona una data di nascita", true);
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

    private void setErroriDB(String messaggio) {
        switch (messaggio) {
            case "ck_email" -> errore("erroreEmailLabel", "Inserisci un'email valida", true);
            case "cliente_email_key" -> errore("erroreEmailLabel", "L'indirizzo email è gia registrato", true);
            case "ck_telefono_cliente" -> errore("erroreTelefonoLabel", "Inserisci un numero di telefono valido", true);
            case "cliente_telefono_key" -> errore("erroreTelefonoLabel", "Il numero di telefono è già registrato", true);
            case "signup_fallito", "troppo_lungo" -> errore("erroreLoginLabel", "Si è verificato un errore", false);
        }
    }

}