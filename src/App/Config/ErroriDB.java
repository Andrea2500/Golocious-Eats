package App.Config;

public class ErroriDB {

    /**********Metodi**********/

    /**********Metodi di funzionalità**********/

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
        if(isErrorKey(error, "ck_indirizzo_attivo_del_cliente")) {
            return "ck_indirizzo_attivo_del_cliente";
        }
        if(isErrorKey(error, "ck_patente")) {
            return "ck_patente";
        }
        if(isErrorKey(error, "troppo lungo")) {
            return "troppo_lungo";
        }
        if(isErrorKey(error, "ck_eta_rider")){
            return "ck_eta_rider";
        }
        if(isErrorKey(error, "rider_patente_key")) {
            return "rider_patente_key";
        }
        if(isErrorKey(error, "uq_gestore")) {
            return "uq_gestore";
        }
        if(isErrorKey(error, "ristorante_nome_key")) {
            return "ristorante_nome_key";
        }
        if(isErrorKey(error, "uq_telefono_ristorante")) {
            return "uq_telefono_ristorante";
        }
        if(isErrorKey(error, "uq_menu")) {
            return "uq_menu";
        }
        else return "nessun_errore";
    }

    /**********Metodi di supporto**********/

    private boolean isErrorKey(String error, String key) {
        return error.contains(key);
    }

}