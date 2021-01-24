package App.Scenes.Controller;

import App.Controllers.AuthController;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoginController extends BaseSceneController {

    AuthController auth;


    public LoginController() throws SQLException {
        this.auth = new AuthController();
    }

    public void loginBtnClick() throws Exception {
        if(getVisibility("nomeField")){
            ResetErrori();
            setField(false);
        } else {
            String email = getValue("emailField", "textfield");
            String password = getValue("passwordField", "passwordfield");
            if(email.length() >0 && password.length() >0){
                ResetErrori();
                if(auth.Login(email,password))
                    sceneController.login();
            }else {
                ResetErrori();
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
        if (!getVisibility("nomeField")) {
            ResetErrori();
            setField(true);
        } else {
            ResetErrori();

            String nome = getValue("nomeField", "textfield");
            String cognome = getValue("cognomeField", "textfield");
            String email = getValue("emailField", "textfield");
            String password = getValue("passwordField", "passwordfield");
            String telefono = getValue("telefonoField", "textfield");
            LocalDate dataNascita = getValue("datanascitaField");

            if(nome.length()>0 && cognome.length()>0 && email.length()>0 && password.length()>0 && telefono.length()>0 && eta(dataNascita) >= 14) {
                if (auth.Register(nome, cognome, email, password, telefono, dataNascita)) {
                    sceneController.login();
                }
            } else {
                ResetErrori();

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
        }
    }

    public void setField(boolean Register){
        setVisibility("nomeField", Register);
        setVisibility("erroreNomeLabel", Register);
        setVisibility("cognomeField", Register);
        setVisibility("erroreCognomeLabel", Register);
        setVisibility("telefonoField", Register);
        setVisibility("erroreTelefonoLabel", Register);
        setVisibility("datanascitaField", Register);
        setVisibility("erroreDatanascitaLabel", Register);
    }

}