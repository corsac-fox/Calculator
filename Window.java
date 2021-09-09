package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    Container container = getContentPane();
    FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 3, 3);
    Calculator calculator = new Calculator(this);
    boolean isError;

    JButton[] buttons = new JButton[20];
    String[] names =
            {
                    "C", "" + (char)0x21D0, "" + (char)0x221A, "+", "1", "2", "3", "-", "4", "5", "6", "*", "7", "8",
                    "9", "" + (char)0xF7, ".", "0", "+/-", "="
            };

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

        for (int i = 0; i < buttons.length; i++)
        {
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
    public void actionPerformed(ActionEvent e)
    {
        if (!isError)
            switch(e.getActionCommand())
            {
                case "C":
                    calculator.cancel();
                    break;
                case ("" + (char)0x21D0)://стирание знаков
                    calculator.backspace();
                    break;
                case ("" + (char)0x221A)://извлечение корня
                    calculator.sqrt();
                    break;

                case "+":
                case "-":
                case "*":
                case ("" + (char)0xF7):
                    calculator.setOperator(e.getActionCommand());
                    break;

                case "=":
                    calculator.calculate();
                    break;

                case "+/-":
                    calculator.signChange();
                    break;

                default:
                    calculator.numberInput(e.getActionCommand());
            }
        else if (e.getActionCommand().equals("C")) calculator.cancel();
    }

    void error() {
        display.setText("error");
        isError = true;
    }
}
