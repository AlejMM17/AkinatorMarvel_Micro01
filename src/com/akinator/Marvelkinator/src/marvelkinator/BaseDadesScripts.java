package marvelkinator;

import java.sql.*;

public class BaseDadesScripts {
    private Connection connection;

    public BaseDadesScripts() throws SQLException{
        this.connection = new ConnexionMySql().conectar();
    }
    public Preguntes obtenerPreguntaPorId(int id) throws SQLException {
        String query = "SELECT * FROM questions WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return construirPregunta(resultSet);
        }
        return null;
    }

    public Personatges obtenerPersonajePorNodo(int nodeId) throws SQLException {
        String query = "SELECT * FROM characters WHERE node_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, nodeId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return construirPersonaje(resultSet);
        }
        return null;
    }

    public Preguntes obtenerPreguntaAleatoria() throws SQLException {
        String query = "SELECT * FROM questions ORDER BY RAND() LIMIT 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            return construirPregunta(resultSet);
        }
        return null;
    }

    private Preguntes construirPregunta(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String questionText = resultSet.getString("questions");
        Integer yesNodeId = (Integer) resultSet.getObject("yes_node_id");
        Integer noNodeId = (Integer) resultSet.getObject("no_node_id");

        return new Preguntes(id, questionText, yesNodeId, noNodeId);
    }

    private Personatges construirPersonaje(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int nodeId = resultSet.getInt("node_id");

        return new Personatges(id, name, nodeId);
    }
    public void cerrarConexion() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
