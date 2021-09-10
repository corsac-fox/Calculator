package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class Window extends JFrame implements ActionListener {
    Container container = getContentPane();
    FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 3, 3);
    Calculator calculator = new Calculator(this);
    boolean isError;
    final int CHARS_MAX_NUMBER = 17;
    final double MAX_VALUE = 1.0E17;
    final double MIN_VALUE = -1.0E17;

    JButton[] buttons = new JButton[20];
    String[] names =
            {
                    "C", "" + (char)0x21D0, "" + (char)0x221A, "+", "1", "2", "3", "-", "4", "5", "6", "×", "7", "8",
                    "9", "÷", ".", "0", "+/-", "="
            };
    String operators = "-+÷×";

    JTextField display = new JTextField();

    Font fontT = new Font("Verdana", Font.PLAIN, 14);

    Color backgroundColor = new Color(41, 97, 97);
    Color displayColor = new Color(206, 233, 233);
    Color buttonColor = new Color(100, 176, 176);
    Color textColor = new Color(13, 63, 63);

    Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container.add(display);
        display.setFont(fontT);
        container.setLayout(layout);
        container.setBackground(backgroundColor);

        for (int i = 0; i < buttons.length; i++) {

            buttons[i] = new JButton(names[i]);
            buttons[i].setPreferredSize(new Dimension(42, 30));
            buttons[i].setBackground(buttonColor);
            buttons[i].setForeground(textColor);
            buttons[i].setBorder(null);
            buttons[i].setActionCommand(names[i]);
            buttons[i].addActionListener(this);
            buttons[i].setFocusPainted(false);

            container.add(buttons[i]);
        }

        display.setPreferredSize(new Dimension(177, 36));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(displayColor);
        display.setForeground(textColor);

        display.setText("0");

        setResizable(false);
        setVisible(true);
        setLocation(300, 200);

        container.setPreferredSize(new Dimension(183, 207));
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isError) {
            switch (e.getActionCommand()) {
                case "C" -> calculator.cancel();
                //стирание знаков
                case ("" + (char) 0x21D0) -> calculator.backspace();
                //извлечение корня
                case ("" + (char) 0x221A) -> calculator.sqrt();
                case "+", "-", "×", "÷" -> calculator.setOperator(e.getActionCommand());
                case "=" -> calculator.calculate();
                case "+/-" -> calculator.signChange();
                default -> calculator.numberInput(e.getActionCommand());
            }

            if (operators.contains(e.getActionCommand())) {
                print(e.getActionCommand());
            } else
                print(BigDecimal.valueOf(calculator.secondOperand).toPlainString());
        } else if (e.getActionCommand().equals("C")) calculator.cancel();
    }

    void error() {
        display.setText("error");
        isError = true;
    }

    void check(double d) {
        if (d > MAX_VALUE || d < MIN_VALUE) error();
    }

    void print(String s) {
        if (s.length() != 1) {
            if (calculator.fractionalPart < 1) s = s.substring(0, s.length() - 2);
            if (calculator.fractionalPart == 1) s = s.substring(0, s.length() - 1);
        }

        if (s.length() > CHARS_MAX_NUMBER) s = s.substring(0, CHARS_MAX_NUMBER + 1);

        display.setText(s);
    }
}
