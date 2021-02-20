package App.DAO;

import App.Config.Database;
import App.Objects.Articolo;
import App.Objects.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CarrelloDAO {

    /**********Attributi**********/

    Database db;
    String tabella = "carrello";

    /**********Metodi**********/

    /**********Costruttori**********/

    public CarrelloDAO() {
        this.db = new Database();
    }

    /**********Metodi di funzionalità**********/

    public int getCarrelloCliente() throws SQLException {
        ResultSet rs = this.db.queryBuilder(this.tabella, "clienteid = "+ Cliente.getInstance().getClienteId()+" AND ordinato = 'false'");
        if(rs.next()){
            return rs.getInt("carrelloId");
        }else{
            return this.nuovoCarrello(1);
        }
    }

    public int getRistoranteId(int carrelloId) throws SQLException {
        ResultSet rs = this.db.queryBuilder(this.tabella, "carrelloid = "+ carrelloId);
        if(rs.next()){
            return rs.getInt("ristoranteid");
        }else{
            return 0;
        }
    }

    private void setArticoliInCarrello(ObservableList<Articolo> articoliCarrello, int carrelloId) throws SQLException {
        String sql = "INSERT INTO articoloincarrello VALUES(?,?)";
        this.db.setConnection();
        for(Articolo articolo:articoliCarrello){
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, carrelloId);
            pstmt.setInt(2, articolo.getArticoloId());
            pstmt.executeUpdate();
        }
        this.db.closeConnection();
    }

    public ObservableList<Articolo> getArticoliNelCarrelloCliente(int carrelloId) throws SQLException {
        ObservableList<Articolo> articoliCliente = FXCollections.observableArrayList();
        ResultSet rs = this.db.queryBuilder("articoloincarrello","carrelloid = "+carrelloId);
        while (rs.next()){
            articoliCliente.add(new Articolo(rs.getInt("articoloid")));
        }
        return articoliCliente;
    }

    public void sincronizzaDB(ObservableList<Articolo> articoliInCarrello,int carrelloId) throws SQLException {
        this.setArticoliInCarrello(articoliInCarrello, carrelloId);
    }

    private Integer nuovoCarrello(int ristoranteId) throws SQLException {
            String sql = "INSERT INTO "+this.tabella+" VALUES(?,?)";
            this.db.setConnection();
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, Cliente.getInstance().getClienteId());
            pstmt.setInt(2, ristoranteId);
            if(pstmt.executeUpdate() > 0){
                this.db.closeConnection();
                ResultSet rs = pstmt.getGeneratedKeys();
                rs.next();
                return rs.getInt("carrelloid");
            }else{
                return null;
            }
    }

    public void pulisciCarrello(int carrelloId) throws SQLException {
        String sql = "DELETE FROM articoloincarrello WHERE carrelloid = ?";
        this.db.setConnection();
        PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, carrelloId);
        pstmt.executeUpdate();
        this.db.closeConnection();
    }

    public int aggiornaRistoranteId(int ristoranteId, int carrelloId) throws SQLException {
        pulisciCarrello(carrelloId);
        String sql = "DELETE from "+this.tabella+" WHERE carrelloid = ?";
        this.db.setConnection();
        PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, carrelloId);
        pstmt.executeUpdate();
        this.db.closeConnection();
        return this.nuovoCarrello(ristoranteId);
    }

}