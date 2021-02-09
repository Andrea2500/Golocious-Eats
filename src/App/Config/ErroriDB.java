package App.Config;

public class ErroriDB {

    /**********Metodi**********/

    /**********Metodi di funzionalità**********/

    public String getMessaggioErrore(String errore) {
        if(isErroreKey(errore,"ck_email")) {
            return "ck_email";
        }
        if(isErroreKey(errore,"cliente_email_key")) {
            return "cliente_email_key";
        }
        if(isErroreKey(errore, "ck_telefono_cliente")) {
            return "ck_telefono_cliente";
        }
        if(isErroreKey(errore, "cliente_telefono_key")) {
            return "cliente_telefono_key";
        }
        if(isErroreKey(errore, "login_fallito")) {
            return "login_fallito";
        }
        if(isErroreKey(errore, "signup_fallito")) {
            return "signup_fallito";
        }
        if(isErroreKey(errore, "ck_indirizzo_attivo_del_cliente")) {
            return "ck_indirizzo_attivo_del_cliente";
        }
        if(isErroreKey(errore, "ck_patente")) {
            return "ck_patente";
        }
        if(isErroreKey(errore, "troppo lungo")) {
            return "troppo_lungo";
        }
        if(isErroreKey(errore, "ck_eta_rider")){
            return "ck_eta_rider";
        }
        if(isErroreKey(errore, "rider_patente_key")) {
            return "rider_patente_key";
        }
        if(isErroreKey(errore, "uq_gestore")) {
            return "uq_gestore";
        }
        if(isErroreKey(errore, "ristorante_nome_key")) {
            return "ristorante_nome_key";
        }
        if(isErroreKey(errore, "ck_telefono_ristorante")) {
            return "ck_telefono_ristorante";
        }
        if(isErroreKey(errore, "uq_nome")) {
            return "uq_nome";
        }if(isErroreKey(errore, "ck_gestore_non_rider")) {
            return "ck_gestore_non_rider";
        }
        if(isErroreKey(errore, "null value in column \"riderid\"")) {
            return "nessun_rider";
        }
        if(isErroreKey(errore, "Il carrello presenta articoli non disponibili")) {
            return "ck_ordine_articoli_disponibili";
        }
        else return "errore_sconosciuto";
    }

    /**********Metodi di supporto**********/

    private boolean isErroreKey(String error, String key) {
        return error.contains(key);
    }

}