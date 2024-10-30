package marvelkinator;

import javax.swing.*;

public class missatgeProva {
    public static int mostrarMissatge(String mensaje, String[] opciones) {
        return JOptionPane.showOptionDialog(null, mensaje, "Adivina el Objeto",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);
    }}
