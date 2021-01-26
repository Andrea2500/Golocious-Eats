package App.Objects;

import java.sql.SQLException;

public class Rider extends Cliente{

    Integer riderId;

    public Rider(Integer id) throws SQLException {
        this.riderId = id;
    }

}
