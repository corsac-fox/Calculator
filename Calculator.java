package calculator;

public class Calculator {

    Window owner;
    boolean useMemory, useFirst;
    double firstOperand, secondOperand, memory;
    int fractionalPart;
    char operator = '$';

    Calculator (Window owner) {
        this.owner = owner;
    }

    void backspace() {
        if (fractionalPart > 1) {
            fractionalPart--;
            round();
        } else secondOperand = (int) secondOperand / 10;
        if (fractionalPart == 1) fractionalPart = 0;
        owner.display.setText(String.valueOf(secondOperand));
    }

    void calculate() {
        if (useMemory) {
            if (!useFirst)
                firstOperand = secondOperand;

            secondOperand = memory;
        }

        switch (operator) {
            case '+':
                secondOperand = firstOperand + secondOperand;
                break;
            case '-':
                secondOperand = firstOperand - secondOperand;
                break;
            case 0xF7:
                try
                {
                    secondOperand = firstOperand / secondOperand;
                }
                catch (ArithmeticException e)
                {
                    if (secondOperand == 0) owner.error();
                    //else firstOperand = firstOperand.divide(secondOperand, 16, RoundingMode.HALF_UP);
                }
                break;
            case '*':
                secondOperand = firstOperand * secondOperand;
                break;
        }

        firstOperand = 0;
        useMemory = true;
        useFirst = false;

        owner.display.setText(String.valueOf(secondOperand));
        //if (0 < firstOperand.compareTo(MAX_VALUE)) error();
    }

    void cancel() {
        firstOperand = 0;
        secondOperand = 0;
        useMemory = false;
        useFirst = false;
        memory = 0;
        owner.display.setText("0");
    }

    void numberInput(String symbol) {

        if (symbol.equals(".")) {
            if (fractionalPart == 0)
            {
                fractionalPart++;
            }
        } else if (fractionalPart > 0) {
            secondOperand = secondOperand + (Integer.parseInt(symbol) / (Math.pow(10, fractionalPart)));
            fractionalPart++;
        } else
            secondOperand = secondOperand * 10 + Integer.parseInt(symbol);

        owner.display.setText(String.valueOf(secondOperand));

        memory = secondOperand;
        useMemory = false;
        useFirst = false;
    }

    void setOperator(String operator) {

        if (firstOperand != 0) {
            calculate();
            owner.display.setText((secondOperand + operator));
        }

        firstOperand = secondOperand;
        secondOperand = 0;
        fractionalPart = 0;

        this.operator = operator.charAt(0);

        owner.display.setText(operator);
        useMemory = true;
        useFirst = true;
    }

    void signChange() {
        secondOperand = -secondOperand;
        owner.display.setText(String.valueOf(secondOperand));
    }

    void sqrt() {
        secondOperand = Math.sqrt(secondOperand);
        owner.display.setText(String.valueOf(secondOperand));
    }

    void round() {
        double roundingMode = Math.pow(10, fractionalPart - 1);
        secondOperand = (int)(secondOperand * roundingMode) / roundingMode;

    }
}