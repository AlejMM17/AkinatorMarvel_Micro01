/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package marvelkinator;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author alej
 */
public class Marvelkinator {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException{
        // TODO code application logic here
        ConnexionMySql c = new ConnexionMySql();
        try (Connection conexion = c.conectar()) {
            if (conexion != null) {
                System.out.println("La conexión es exitosa: " + conexion);
                JocMarvelkinator joc = new JocMarvelkinator();
                joc.iniciarJoc();
                joc.tancar();
            } else {
                System.out.println("La conexión falló.");
                return;
            }
        }

    }
    
}
