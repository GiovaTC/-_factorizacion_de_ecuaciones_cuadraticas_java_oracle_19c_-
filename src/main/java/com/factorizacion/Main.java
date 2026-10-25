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

    private void factorizar() {
        try {
            a = Double.parseDouble(campoA.getText().trim());
            b = Double.parseDouble(campoB.getText().trim());
            c = Double.parseDouble(campoC.getText().trim());

            if (!Double.isFinite(a) || !Double.isFinite(b) || !Double.isFinite(c))
            {
                throw new IllegalArgumentException("INGRESA valores numericos finitos!");
            }

            if (a == 0)
            {
                throw new IllegalArgumentException("EL coeficiente A debe ser diferente de cero!");
            }

            ecuacion = formato(a) + "x² " +
                    signo(b) + " " + formato(Math.abs(b)) + "x " +
                    signo(c) + " " + formato(Math.abs(c)) + " = 0";

            double discriminante = b * b - 4 * a * c;

            StringBuilder salida = new StringBuilder();
            salida.append("ECUACION:\n")
                    .append(ecuacion)
                    .append("\n\nDISCRIMINANTE: ")
                    .append(formato(discriminante))
                    .append("\n\n");

            if (discriminante > 0)
            {
                double x1 = (-b + Math.sqrt(discriminante)) / (2 * a);
                double x2 = (-b - Math.sqrt(discriminante)) / (2 * a);

                factorizacion = formato(a) +
                        "(x " + signo(-x1) + " " + formato(Math.abs(x1)) +
                        ")(x " + signo(-x2) + " " + formato(Math.abs(x2)) +
                        ") = 0";

                salida.append("DOS raices REALES distintas:\n")
                        .append("x1 = ").append(formato(x1))
                        .append("\nx2 = ").append(formato(x2))
                        .append("\n\nFactorizacion:\n")
                        .append(factorizacion);
            } else if (discriminante == 0)
            {
                double x = -b / (2 * a);

                factorizacion = formato(a) +
                        "(x " + signo(-x) + " " + formato(Math.abs(x)) +
                        ")² = 0";

                salida.append("UNA raiz REAL doble:\n")
                        .append("x = ").append(formato(x))
                        .append("\n\nFactorización:\n")
                        .append(factorizacion);
            } else {
                double parteReal = -b / (2 * a);
                double parteImaginaria =
                        Math.sqrt(-discriminante) / (2 * Math.abs(a));

                factorizacion = formato(a)
                        + "(x - (" + formato(parteReal) + " + "
                        + formato(parteImaginaria) + "i))"
                        + "(x - (" + formato(parteReal) + " - "
                        + formato(parteImaginaria) + "i)) = 0";

                salida.append("No existen raíces reales.\n")
                        .append("Raíces complejas:\n")
                        .append("x1 = ").append(formato(parteReal))
                        .append(" + ").append(formato(parteImaginaria))
                        .append("i\n")
                        .append("x2 = ").append(formato(parteReal))
                        .append(" - ").append(formato(parteImaginaria))
                        .append("i\n\nFactorización compleja:\n")
                        .append(factorizacion);
            }
            resultado.setText(salida.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa números válidos en los tres campos.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(), "Validación",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void guardarEnOracle() {
        if (factorizacion == null) {
            JOptionPane.showMessageDialog(this,
                    "Primero debes factorizar una ecuación.");
            return;
        }

        // Reemplaza estos valores por los datos de tu entorno Oracle.
        String url = "jdbc:oracle:thin:@localhost:1521/orcl";
        String usuario = "TU_USUARIO";
        String clave = "TU_CLAVE";

        String sql = """
                INSERT INTO FACTORIZACIONES
                (COEF_A, COEF_B, COEF_C, ECUACION, RESULTADO)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion =
                     DriverManager.getConnection(url, usuario, clave);
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDouble(1, a);
            ps.setDouble(2, b);
            ps.setDouble(3, c);
            ps.setString(4, ecuacion);
            ps.setString(5, factorizacion);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Factorización guardada correctamente en Oracle 19c.",
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible guardar en Oracle:\n" + ex.getMessage(),
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formato(double valor) {
        if (Math.abs(valor) < 0.0000000001) {
            valor = 0;
        }

        return String.format(Locale.US, "%.4f", valor)
                .replaceAll("0+$", "")
                .replaceAll("\\.$", "");
    }

    private String signo(double valor) {
        return valor < 0 ? "-" : "+";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception ignored) {
                // Se conserva el aspecto predeterminado de Swing.
            }

            new Main().setVisible(true);
        });
    }
}   