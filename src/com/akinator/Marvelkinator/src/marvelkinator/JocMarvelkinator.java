package marvelkinator;

import java.sql.*;

public class JocMarvelkinator {
    private BaseDadesScripts baseDades;

    public JocMarvelkinator() throws SQLException {
        baseDades = new BaseDadesScripts();
    }
    public void iniciarJoc() {
        boolean bucle = true;
        while (bucle) {
            int respuesta = missatgeProva.mostrarMissatge("¿Estás pensando en un personaje de Marvel?", new String[]{"Sí", "No"});
            if (respuesta == 1) {
                bucle = false;
                break;
            }
        }
    }
    public void tancar() {
        try {
            baseDades.cerrarConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}