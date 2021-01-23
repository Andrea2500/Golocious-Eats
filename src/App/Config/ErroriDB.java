package App.Config;

import App.Scenes.Controller.BaseSceneController;

public class ErroriDB {

    BaseSceneController baseSceneController = new BaseSceneController();

    public void getErrorMessage(String error) {
        if(isErrorKey(error,"ck_email")) {
            baseSceneController.errore("erroreEmailLabel", "Inserisci un'email valida", true);
        }
        if(isErrorKey(error,"cliente_email_key")) {
            baseSceneController.errore("erroreEmailLabel", "L'indirizzo email è gia registrato", true);
        }
        if(isErrorKey(error, "ck_telefono")) {
            baseSceneController.errore("erroreTelefonoLabel", "Inserisci un numero di telefono valido", true);
        }
        if(isErrorKey(error, "login_fallito")) {
            baseSceneController.errore("erroreLoginLabel", "Il login non è riuscito", false);
        }
        if(isErrorKey(error, "signup_fallito")) {
            baseSceneController.errore("erroreLoginLabel", "Si è verificato un errore!", false);
        }
    }

    private boolean isErrorKey(String error, String key) {
        return error.contains(key);
    }

}
