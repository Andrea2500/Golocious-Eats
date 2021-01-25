package App.Scenes.Controller;


public class ImpostazioniController extends BaseSceneController{

    public void inserisciIndirizzoBtn() {
        resetBtnColor();
        resetVBoxManagedAndVisible();
        setManagedAndVisible("inserisciIndirizzoHBox", true);
        sceneController.setActiveBtn("inserisciIndirizzoBtn");
    }

    public void cambiaIndirizzoAttivoBtn() {
        resetBtnColor();
        resetVBoxManagedAndVisible();
        setManagedAndVisible("cambiaIndirizzoAttivoVBox", true);
        sceneController.setActiveBtn("cambiaIndirizzoAttivoBtn");
    }

    public void diventaRiderBtn() {
        resetBtnColor();
        resetVBoxManagedAndVisible();
        setManagedAndVisible("diventaRiderHBox", true);
        sceneController.setActiveBtn("diventaRiderBtn");
    }

    public void aggiungiBtn() {
        resetErrori();
        String paese = getValue("errorePaeseLabel", "label");
        String provincia = getValue("erroreProvinciaLabel", "label");
        String cap = getValue("erroreCapLabel", "label");
        String citta = getValue("erroreCittaLabel", "label");
        String indirizzo = getValue("erroreIndirizzoLabel", "label");
        if(paese.length()>0 && provincia.length()>0 && cap.length()>0 && citta.length()>0 && indirizzo.length()>0){
            //aggiungi indirizzo
        } else {
            setErrori(paese, provincia, cap, citta, indirizzo);
        }
    }

    public void iniziaAConsegnareBtn() {
    }

    public void resetVBoxManagedAndVisible() {
        getElementById("vuotoVBox").setManaged(false);
        getElementById("vuotoVBox").setVisible(false);
        getElementById("inserisciIndirizzoHBox").setManaged(false);
        getElementById("inserisciIndirizzoHBox").setVisible(false);
        getElementById("cambiaIndirizzoAttivoVBox").setManaged(false);
        getElementById("cambiaIndirizzoAttivoVBox").setVisible(false);
        getElementById("diventaRiderHBox").setManaged(false);
        getElementById("diventaRiderHBox").setVisible(false);
    }

    public void resetBtnColor(){
        if(getElementById("inserisciIndirizzoHBox").isVisible()){
            getElementById("inserisciIndirizzoBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("cambiaIndirizzoAttivoVBox").isVisible()){
            getElementById("cambiaIndirizzoAttivoBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if (getElementById("diventaRiderHBox").isVisible()) {
            getElementById("diventaRiderBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        }
    }

    public void resetErrori() {
        inizializzaLabel("errorePaeseLabel", true);
        inizializzaLabel("erroreProvinciaLabel", true);
        inizializzaLabel("erroreCapLabel", true);
        inizializzaLabel("erroreCittaLabel", true);
        inizializzaLabel("erroreIndirizzoLabel", true);
    }

    public void setErrori(String paese, String provincia, String cap, String citta, String indirizzo) {
        if(paese.length()==0){
            errore("errorePaeseLabel", "Inserisci un Paese", true);
        }
        if(provincia.length()==0){
            errore("erroreProvinciaLabel", "Inserisci una provincia", true);
        }
        if(cap.length()==0){
            errore("erroreCapLabel", "Inserisci un CAP", true);
        }
        if(citta.length()==0){
            errore("erroreCittaLabel", "Inserisci una città", true);
        }
        if(indirizzo.length()==0){
            errore("erroreIndirizzoLabel", "Inserisci un indirizzo", true);
        }
    }

}
