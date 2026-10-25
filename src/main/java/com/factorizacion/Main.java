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

        titulo.setFont(new Font("Arial", Font.BOLD, 17));
        principal.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(4, 2, 10, 10));

        formulario.add(new JLabel("coeficiente a (x²):"));
        formulario.add(campoA);
        formulario.add(new JLabel("coeficiente b (x):"));
        formulario.add(campoB);
        formulario.add(new JLabel("coeficiente c:"));
        formulario.add(campoC);

        JButton btnFactorizar = new JButton("factorizar");
        JButton btnGuardar = new JButton("guardar en Oracle");

        formulario.add(btnFactorizar);
        formulario.add(btnGuardar);

        resultado.setEditable(false);
        resultado.setLineWrap(true);
        resultado.setWrapStyleWord(true);
        resultado.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.add(formulario, BorderLayout.NORTH);
        centro.add(new JScrollPane(resultado), BorderLayout.CENTER);

        principal.add(centro, BorderLayout.CENTER);
        add(principal);

        btnFactorizar.addActionListener(e -> factorizar());
        btnGuardar.addActionListener(e -> guardarEnOracle());
    }   

    private void guardarEnOracle() {
    }

    private void factorizar() {
    }
}