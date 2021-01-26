package App.Config;

public class ErroriDB {

    public String getErrorMessage(String error) {
        if(isErrorKey(error,"ck_email")) {
            return "ck_email";
        }
        if(isErrorKey(error,"cliente_email_key")) {
            return "cliente_email_key";
        }
        if(isErrorKey(error, "ck_telefono")) {
            return "ck_telefono";
        }
        if(isErrorKey(error, "cliente_telefono_key")) {
            return "cliente_telefono_key";
        }
        if(isErrorKey(error, "login_fallito")) {
            return "login_fallito";
        }
        if(isErrorKey(error, "signup_fallito")) {
            return "signup_fallito";
        }
        if(isErrorKey(error, "ck_patente")) {
            return "ck_patente";
        }
        if(isErrorKey(error, "troppo lungo")) {
            return "troppo lungo";
        }
        else return "nessun_errore";
    }

    private boolean isErrorKey(String error, String key) {
        return error.contains(key);
    }

}