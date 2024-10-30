package marvelkinator;

import java.sql.*;

public class BaseDadesScripts {
    private Connection connection;

    public BaseDadesScripts() throws SQLException{
        this.connection = new ConnexionMySql().conectar();
    }
    
    public void cerrarConexion() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
