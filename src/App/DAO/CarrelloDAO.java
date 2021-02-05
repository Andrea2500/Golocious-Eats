package App.DAO;

import App.Config.Database;
import App.Objects.Articolo;
import App.Objects.Cliente;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CarrelloDAO {

    Database db;

    public CarrelloDAO() {
        this.db = new Database();
    }

    public void sincronizzaDB(ObservableList<Articolo> articoliInCarrello,int carrelloId) throws SQLException {
        //this.pulisciCarrello(carrello.getCarrelloId());
        //this.updateRistoranteId(carrello.getCarrelloId(),carrello.getRistoranteId());
        this.setArticoliInCarrello(articoliInCarrello, carrelloId );
    }

    public int getCarrelloCliente() throws SQLException {
        ResultSet rs = this.db.queryBuilder("carrello", "clienteid = "+ Cliente.getInstance().getClienteId()+" AND ordinato = 'false'");
        if(rs.next()){
            return rs.getInt("carrelloId");
        }else{
            return this.nuovoCarrello(1);
        }

    }

    private Integer nuovoCarrello(int ristoranteId) throws SQLException {
            String sql = "INSERT INTO carrello VALUES(?,?)";
            this.db.setConnection();
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,Cliente.getInstance().getClienteId());
            pstmt.setInt(2,ristoranteId);
            if(pstmt.executeUpdate() > 0){
                this.db.closeConnection();
                ResultSet rs = pstmt.getGeneratedKeys();
                rs.next();
                return rs.getInt("carrelloid");
            }else{
                return null;
            }
    }

    private void pulisciCarrello(int carrelloId) throws SQLException {
        String sql = "DELETE FROM articoloincarrello WHERE carrelloid = ?";
        this.db.setConnection();
        PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1,carrelloId);
        pstmt.executeUpdate();
        this.db.closeConnection();
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

    public int updateRistoranteId(int ristoranteId,int carrelloId) throws SQLException {
        pulisciCarrello(carrelloId);
        String sql = "DELETE from carrello WHERE carrelloid = ?";
        this.db.setConnection();
        PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1,carrelloId);
        pstmt.executeUpdate();
        this.db.closeConnection();
        return this.nuovoCarrello(ristoranteId);
    }

}
