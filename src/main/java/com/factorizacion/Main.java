package com.factorizacion;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Locale;

public class Main extends JFrame {

    private final JTextField campoA = new JTextField("1", 8);
    private final JTextField campoB = new JTextField("-5", 8);
    private final JTextField campoC = new JTextField("6", 8);

    private final JTextArea resultado = new JTextArea(8, 38);

    private double a, b, c;
    private String ecuacion;
    private String factorizacion;

    public Main() {
        setTitle("Factorización de Ecuaciones - Oracle 19c");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 440);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout(12, 12));
        principal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("FACTORIZACION DE ECUACIONES CUADRATICAS",SwingConstants.CENTER);
        

    }
}