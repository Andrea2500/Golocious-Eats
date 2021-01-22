package App.Config;

import App.Scenes.AlertBox;

public class DBErrors {
    AlertBox alert = new AlertBox();

    public String getErrorMessage(String error){

        if(isErrorKey(error,"ck_email")){
            return "Inserisci un indirizzo email valido";
        }
        if(isErrorKey(error,"ck_eta_cliente")){
            return "L'età minima di iscrizione è di 14 anni";
        }
        if(isErrorKey(error,"cliente_email_key")){
            return "L'indirizzo email è gia registrato!";
        }


        else
            return error;//"Si è verificato un errore! Riprova";

    }

    private boolean isErrorKey(String error,String key){
        return error.contains(key);
    }

    public void showError(String errore){
        alert.display("Errore",errore);
    }


}
