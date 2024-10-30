package marvelkinator;

import javax.swing.*;
import java.sql.*;

public class JocMarvelkinator {
    private BaseDadesScripts baseDades;

    public JocMarvelkinator() throws SQLException {
        baseDades = new BaseDadesScripts();
    }
    public void iniciarJoc() {
        try {
            boolean bucle = true;
            while (bucle) {
                int respuesta = missatgeProva.mostrarMissatge("¿Estás pensando en un personaje?", new String[]{"Sí", "No"});
                if (respuesta == 1) {
                    bucle = false;
                    break;
                }

                Preguntes preguntaActual = baseDades.obtenerPreguntaAleatoria();
                while (preguntaActual != null && (preguntaActual.nodeEsquerra != null || preguntaActual.nodeDreta != null)) {
                    if (missatgeProva.mostrarMissatge(preguntaActual.pregunta, new String[]{"Sí", "No"}) == 0) {
                        preguntaActual = baseDades.obtenerPreguntaPorId(preguntaActual.nodeEsquerra);
                    } else {
                        preguntaActual = baseDades.obtenerPreguntaPorId(preguntaActual.nodeDreta);
                    }
                }

                if (preguntaActual != null) {
                    Personatges personaje = baseDades.obtenerPersonajePorNodo(preguntaActual.id);
                    if (personaje != null && missatgeProva.mostrarMissatge("¿Es " + personaje.name + "?", new String[]{"Sí", "No"}) == 0) {
                        JOptionPane.showMessageDialog(null, "¡He adivinado correctamente!");
                        continue;
                    }
                }

                String nuevoNombre = JOptionPane.showInputDialog("¿Qué personaje era?");
                String nuevaPregunta = JOptionPane.showInputDialog("Escribe una pregunta para diferenciar " + nuevoNombre);

                String indicador = "¿Si el personaje fuera " + nuevoNombre + ", cuál sería la respuesta?";
                if (missatgeProva.mostrarMissatge(indicador, new String[]{"Sí", "No"}) == 0) {
                    // Lógica para guardar nueva pregunta y personaje aquí
                    JOptionPane.showMessageDialog(null, "Nuevo personaje y pregunta añadidos.");
                } else {
                    // Lógica para guardar nueva pregunta y personaje con "No" aquí
                    JOptionPane.showMessageDialog(null, "Nuevo personaje y pregunta añadidos.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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