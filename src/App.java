import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class App extends JFrame {

    private JTextField display;
    private JTextArea historyArea;
    private ArrayList<String> history = new ArrayList<>();

    public App() {
        super("Dark Calculator");

        // ----- DARK MODE COLORS -----
        Color bg = new Color(20, 20, 20);
        Color panelBg = new Color(30, 30, 30);
        Color btnBg = new Color(45, 45, 45);
        Color btnHover = new Color(70, 70, 70);
        Color textColor = new Color(230, 230, 230);

        setSize(420, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bg);

        // ----- DISPLAY -----
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Segoe UI", Font.BOLD, 28));
        display.setBackground(panelBg);
        display.setForeground(textColor);
        display.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(display, BorderLayout.NORTH);

        // ----- HISTORY PANEL -----
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setBackground(panelBg);
        historyArea.setForeground(textColor);
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(historyArea);
        scroll.setPreferredSize(new Dimension(150, 0));
        add(scroll, BorderLayout.EAST);

        // ----- BUTTON PANEL -----
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(bg);

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "(", ")", "⌫"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFocusable(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 22));
            btn.setBackground(btnBg);
            btn.setForeground(textColor);
            btn.setBorder(BorderFactory.createLineBorder(panelBg));

            // Button hover effect
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(btnHover);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(btnBg);
                }
            });

            btn.addActionListener(e -> buttonPressed(btn.getText()));

            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void buttonPressed(String text) {
        switch (text) {
            case "C":
                display.setText("");
                break;
            case "⌫":
                String current = display.getText();
                if (!current.isEmpty()) {
                    display.setText(current.substring(0, current.length() - 1));
                }
                break;
            case "=":
                try {
                    String expr = display.getText();
                    double result = evaluate(expr);
                    display.setText("" + result);

                    // Add to history
                    String entry = expr + " = " + result;
                    history.add(entry);
                    historyArea.append(entry + "\n");

                } catch (Exception ex) {
                    display.setText("Error");
                }
                break;
            default:
                display.setText(display.getText() + text);
        }
    }

    // Expression Evaluator
    public static double evaluate(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ')
                    nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length())
                    throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+'))
                        x += parseTerm();
                    else if (eat('-'))
                        x -= parseTerm();
                    else
                        return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*'))
                        x *= parseFactor();
                    else if (eat('/'))
                        x /= parseFactor();
                    else
                        return x;
                }
            }

            double parseFactor() {
                if (eat('+'))
                    return parseFactor();
                if (eat('-'))
                    return -parseFactor();

                double x;
                int startPos = this.pos;

                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.')
                        nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected character");
                }
                return x;
            }
        }.parse();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
