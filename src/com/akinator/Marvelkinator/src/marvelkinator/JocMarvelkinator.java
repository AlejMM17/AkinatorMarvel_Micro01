package marvelkinator;

import javax.swing.*;
import java.sql.*;

public class JocMarvelkinator {
    private Nodo raiz;
    private BaseDadesScripts baseDatos;

    public JocMarvelkinator() throws SQLException {
        baseDatos = new BaseDadesScripts();
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

                insertarPreguntaYPersonaje(nuevaPregunta, nuevoNombre, nodoActual, esIzquierda);
                return false;
            }
        } else {
            int respuesta = JOptionPane.showConfirmDialog(null, nodoActual.getPregunta(), "Pregunta", JOptionPane.YES_NO_OPTION);
            Nodo siguienteNodo = respuesta == JOptionPane.YES_OPTION ? obtenerNodo(nodoActual.getId(), "izquierdo_id") : obtenerNodo(nodoActual.getId(), "derecho_id");
            return adivinarPersonaje(siguienteNodo);
        }
    }
    private Nodo obtenerNodo(int parentId, String lado) throws SQLException {
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
        }
        return null;
    }

    private void insertarPreguntaYPersonaje(String nuevaPregunta, String nuevoPersonaje, Nodo nodoPadre, boolean esIzquierda) throws SQLException {
        Nodo nuevaPreguntaNodo = new Nodo(0, nuevaPregunta);
        Nodo nuevoPersonajeNodo = new Nodo(0, nuevoPersonaje, nuevaPregunta);

        try (Connection conexion = baseDatos.getConexion()) {
            String queryPregunta = "INSERT INTO nodes (pregunta, parent_id, izquierdo_id, derecho_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conexion.prepareStatement(queryPregunta, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nuevaPregunta);
                stmt.setInt(2, nodoPadre.getId());
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.INTEGER);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int nuevoIdPregunta = rs.getInt(1);
                    nuevaPreguntaNodo.setId(nuevoIdPregunta);
                }
            }

            String queryPersonaje = "INSERT INTO nodes (pregunta, parent_id, izquierdo_id, derecho_id, personaje) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conexion.prepareStatement(queryPersonaje, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, "");
                stmt.setInt(2, nodoPadre.getId());
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.INTEGER);
                stmt.setString(5, nuevoPersonaje);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int nuevoIdPersonaje = rs.getInt(1);
                    nuevoPersonajeNodo.setId(nuevoIdPersonaje);
                }
            }

            if (esIzquierda) {
                nuevaPreguntaNodo.setIzquierdo(nuevoPersonajeNodo);
                nuevaPreguntaNodo.setDerecho(nodoPadre);
            } else {
                nuevaPreguntaNodo.setDerecho(nuevoPersonajeNodo);
                nuevaPreguntaNodo.setIzquierdo(nodoPadre);
            }

            actualizarRelaciones(nuevaPreguntaNodo, nodoPadre, esIzquierda);
        }
    }

    private void actualizarRelaciones(Nodo nuevaPreguntaNodo, Nodo nodoPadre, boolean esIzquierda) throws SQLException {
        try (Connection conexion = baseDatos.getConexion()) {
            String updateRelaciones = "UPDATE nodes SET izquierdo_id = ?, derecho_id = ? WHERE id = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(updateRelaciones)) {
                if (esIzquierda) {
                    stmt.setInt(1, nuevaPreguntaNodo.getId());
                    stmt.setInt(2, nodoPadre.getId());
                } else {
                    stmt.setInt(1, nodoPadre.getId());
                    stmt.setInt(2, nuevaPreguntaNodo.getId());
                }
                stmt.setInt(3, nodoPadre.getId());
                stmt.executeUpdate();
            }
        }
    }

    private Nodo obtenerRaizDelArbol() throws SQLException {
        String query = "SELECT * FROM nodes WHERE parent_id IS NULL";
        try (Statement stmt = baseDatos.getConexion().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int id = rs.getInt("id");
                String pregunta = rs.getString("pregunta");
                String personaje = rs.getString("personaje");
                return new Nodo(id, personaje, pregunta);
            }
        }
        return null;
    }

    private void construirArbol(Nodo nodo) throws SQLException {
        String queryIzquierda = "SELECT * FROM nodes WHERE parent_id = ? AND izquierdo_id IS NOT NULL";
        String queryDerecha = "SELECT * FROM nodes WHERE parent_id = ? AND derecho_id IS NOT NULL";

        try (PreparedStatement stmtIzquierda = baseDatos.getConexion().prepareStatement(queryIzquierda);
             PreparedStatement stmtDerecha = baseDatos.getConexion().prepareStatement(queryDerecha)) {

            stmtIzquierda.setInt(1, nodo.getId());
            stmtDerecha.setInt(1, nodo.getId());

            ResultSet rsIzquierda = stmtIzquierda.executeQuery();
            if (rsIzquierda.next()) {
                String preguntaIzquierda = rsIzquierda.getString("pregunta");
                Nodo nodoIzquierda = new Nodo(preguntaIzquierda);
                nodo.setIzquierdo(nodoIzquierda);
                construirArbol(nodoIzquierda);
            }

            ResultSet rsDerecha = stmtDerecha.executeQuery();
            if (rsDerecha.next()) {
                String preguntaDerecha = rsDerecha.getString("pregunta");
                Nodo nodoDerecho = new Nodo(preguntaDerecha);
                nodo.setDerecho(nodoDerecho);
                construirArbol(nodoDerecho);
            }
        }
    }
    public void cerrar() {
        try {
            baseDatos.cerrarConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
