package marvelkinator;

import javax.swing.*;
import java.sql.*;

public class JocMarvelkinator {
    private Nodo raiz;
    private BaseDadesScripts baseDatos;

    public JocMarvelkinator() throws SQLException {
        this.raiz = obtenerRaizDelArbol();  // Obtiene la raíz desde la base de datos
    }

    public void iniciarJoc() {
        try {
            while (true) {
                boolean respuestaCorrecta = adivinarPersonaje(raiz);
                if (respuestaCorrecta) {
                    JOptionPane.showMessageDialog(null, "¡Adiviné el personaje!");
                }

                int respuesta = JOptionPane.showConfirmDialog(null, "¿Quieres jugar otra vez?", "Nuevo juego", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.NO_OPTION) {
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean adivinarPersonaje(Nodo nodoActual) throws SQLException {
        if (nodoActual == null) return false;

        if (nodoActual.esPersonaje()) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Es " + nodoActual.getPersonaje() + "?", "Adivina", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                return true;
            } else {
                String nuevoNombre = JOptionPane.showInputDialog("¿Qué personaje era?");
                String nuevaPregunta = JOptionPane.showInputDialog("Escribe una pregunta para diferenciar " + nuevoNombre);
                int respuestaNueva = JOptionPane.showConfirmDialog(null, "¿La respuesta para " + nuevoNombre + " sería sí?", "Nueva pregunta", JOptionPane.YES_NO_OPTION);
                boolean esIzquierda = (respuestaNueva == JOptionPane.YES_OPTION);
                Nodo nodoPadre = obtenerPadre(nodoActual);

                insertarPreguntaYPersonaje(nuevaPregunta, nuevoNombre, nodoActual, esIzquierda, nodoPadre);
                return false;
            }
        } else {
            int respuesta = JOptionPane.showConfirmDialog(null, nodoActual.getPregunta(), "Pregunta", JOptionPane.YES_NO_OPTION);
            Nodo siguienteNodo = respuesta == JOptionPane.YES_OPTION ? obtenerNodo(nodoActual.getId(), "izquierdo_id") : obtenerNodo(nodoActual.getId(), "derecho_id");
            return adivinarPersonaje(siguienteNodo);
        }
    }
    private Nodo obtenerNodo(int parentId, String lado) throws SQLException {
        baseDatos = new BaseDadesScripts();

        String query = "SELECT * FROM nodes WHERE id = (SELECT " + lado + " FROM nodes WHERE id = ?)";
        try (PreparedStatement stmt = baseDatos.getConexion().prepareStatement(query)) {
            stmt.setInt(1, parentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String pregunta = rs.getString("pregunta");
                String personaje = rs.getString("personaje");
                Nodo nodo = new Nodo(id, personaje, pregunta);
                return nodo;
            }
            cerrar();
        }
        return null;
    }

    private Nodo obtenerPadre(Nodo nodoActual) throws SQLException {
        baseDatos = new BaseDadesScripts();
        String query = "SELECT * FROM nodes WHERE id = ?";
        try (PreparedStatement stmt = baseDatos.getConexion().prepareStatement(query)) {
            stmt.setInt(1, nodoActual.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idPadre = rs.getInt("parent_id");
                String queryPadre = "SELECT * FROM nodes WHERE id = ?";
                try (PreparedStatement stmtDos = baseDatos.getConexion().prepareStatement(queryPadre)) {
                    stmtDos.setInt(1, idPadre);
                    ResultSet rsDos = stmt.executeQuery();
                    if (rsDos.next()) {
                        String pregunta = rsDos.getString("pregunta");
                        String personaje = rsDos.getString("personaje");
                        Nodo nodo = new Nodo(idPadre, pregunta, personaje);
                        cerrar();
                        return nodo;
                    }
                }
            }
            return null;
        }
    }

    private void insertarPreguntaYPersonaje(String nuevaPregunta, String nuevoPersonaje, Nodo nodoActual, boolean esIzquierda, Nodo nodoPadre) throws SQLException {
        baseDatos = new BaseDadesScripts();
        Nodo nuevaPreguntaNodo = new Nodo(0, nuevaPregunta);
        Nodo nuevoPersonajeNodo = new Nodo(0, nuevoPersonaje);

        try (Connection conexion = baseDatos.getConexion()) {
            // Insertar la nueva pregunta como nodo
            String queryPregunta = "INSERT INTO nodes (pregunta, parent_id, izquierdo_id, derecho_id, personaje) VALUES (?, ?, NULL, NULL, NULL)";
            try (PreparedStatement stmt = conexion.prepareStatement(queryPregunta, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nuevaPregunta);
                stmt.setInt(2, nodoPadre.getId());
                stmt.executeUpdate();

                // Obtener el ID generado para la nueva pregunta
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int nuevoIdPregunta = rs.getInt(1);
                    nuevaPreguntaNodo.setId(nuevoIdPregunta);

                    // Insertar el nuevo personaje como nodo hijo
                    String queryPersonaje = "INSERT INTO nodes (pregunta, parent_id, izquierdo_id, derecho_id, personaje) VALUES (NULL, ?, NULL, NULL, ?)";
                    try (PreparedStatement stmtPersonaje = conexion.prepareStatement(queryPersonaje, Statement.RETURN_GENERATED_KEYS)) {
                        stmtPersonaje.setInt(1, nuevoIdPregunta);
                        stmtPersonaje.setString(2, nuevoPersonaje);
                        stmtPersonaje.executeUpdate();

                        ResultSet rsPersonaje = stmtPersonaje.getGeneratedKeys();
                        if (rsPersonaje.next()) {
                            int nuevoIdPersonaje = rsPersonaje.getInt(1);
                            nuevoPersonajeNodo.setId(nuevoIdPersonaje);

                            // Asignar los IDs izquierdo y derecho en nuevaPreguntaNodo
                            if (esIzquierda) {
                                nuevaPreguntaNodo.setIzquierdo(nuevoPersonajeNodo);
                                nuevaPreguntaNodo.setDerecho(nodoActual);
                            } else {
                                nuevaPreguntaNodo.setDerecho(nuevoPersonajeNodo);
                                nuevaPreguntaNodo.setIzquierdo(nodoActual);
                            }

                            // Actualizar la base de datos con los nuevos IDs izquierdo/derecho del nodo pregunta
                            actualizarNodoPadrePersonage(nuevoIdPregunta, nodoActual);
                            actualizarRelaciones(nuevaPreguntaNodo, nodoPadre, esIzquierda);
                            actualizarNuevaPregunta(nuevoIdPregunta, nodoActual, nuevoIdPersonaje, esIzquierda);
                        }
                    }
                }
            }
        }
        cerrar();
    }
    private void actualizarNodoPadrePersonage(int nuevoIdPregunta, Nodo nodoActual) throws SQLException {
        baseDatos = new BaseDadesScripts();

        String queryUpdate = "UPDATE nodes SET parent_id = ? WHERE id = ?";

        try (Connection conexion = baseDatos.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(queryUpdate)) {
            stmt.setInt(1, nuevoIdPregunta);
            stmt.setInt(2, nodoActual.getId());
            stmt.executeUpdate();
        }
        cerrar();
    }

    private void actualizarNuevaPregunta(int nuevoIdPregunta, Nodo nodoActual, int nuevoIdPersonaje, boolean esIzquierda) throws SQLException {
        baseDatos = new BaseDadesScripts();

        String queryUpdate = esIzquierda
            ? "UPDATE nodes SET izquierdo_id = ?, derecho_id = ? WHERE id = ?"
            : "UPDATE nodes SET derecho_id = ?, izquierdo_id = ? WHERE id = ?";


        try (Connection conexion = baseDatos.getConexion();
            PreparedStatement stmt = conexion.prepareStatement(queryUpdate)) {
            stmt.setInt(1, nuevoIdPersonaje);
            stmt.setInt(2, nodoActual.getId());
            stmt.setInt(3, nuevoIdPregunta);
            stmt.executeUpdate();
        }
        cerrar();
    }

    private void actualizarRelaciones(Nodo nuevaPreguntaNodo, Nodo nodoPadre, boolean esIzquierda) throws SQLException {
        baseDatos = new BaseDadesScripts();
        String updateRelaciones = esIzquierda
                ? "UPDATE nodes SET izquierdo_id = ? WHERE id = ?"
                : "UPDATE nodes SET derecho_id = ? WHERE id = ?";

        try (Connection conexion = baseDatos.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(updateRelaciones)) {
            stmt.setInt(1, nuevaPreguntaNodo.getId());
            stmt.setInt(2, nodoPadre.getId());
            stmt.executeUpdate();
        }
        cerrar();
    }

    private Nodo obtenerRaizDelArbol() throws SQLException {
        baseDatos = new BaseDadesScripts();

        String query = "SELECT * FROM nodes WHERE parent_id IS NULL";
        try (Statement stmt = baseDatos.getConexion().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int id = rs.getInt("id");
                String pregunta = rs.getString("pregunta");
                String personaje = rs.getString("personaje");
                return new Nodo(id, personaje, pregunta);
            }
            cerrar();
        }
        return null;
    }
    public void cerrar() {
        try {
            baseDatos.cerrarConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

