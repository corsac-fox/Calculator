package calculator;

public class Calculator {

    Window owner;
    boolean useMemory, useFirst, startNew = true;
    double firstOperand, secondOperand, memory;
    int fractionalPart;
    char operator;

    Calculator (Window owner) {
        this.owner = owner;
    }

    void backspace() {
        if (startNew) cancel();

        if (fractionalPart > 1) {
            fractionalPart--;
            round();
        } else secondOperand = (int)secondOperand / 10;
        if (fractionalPart == 1) fractionalPart = 0;
        memory = secondOperand;
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
            case '÷':
                try {
                    secondOperand = firstOperand / secondOperand;
                } catch (ArithmeticException e) {
                    if (secondOperand == 0) owner.error();
                }
                break;
            case '×':
                secondOperand = firstOperand * secondOperand;
                break;
        }

        owner.check(secondOperand);

        firstOperand = 0;
        useMemory = true;
        useFirst = false;
        startNew = true;
    }

    void cancel() {
        firstOperand = 0;
        secondOperand = 0;
        useMemory = false;
        useFirst = false;
        memory = 0;
        operator = '\u0000';
        owner.isError = false;
        owner.display.setText("0");
        fractionalPart = 0;
    }

    void numberInput(String symbol) {

        if (startNew) cancel();

        if (symbol.equals(".")) {
            if (fractionalPart == 0) {
                fractionalPart++;
            }
        } else if (fractionalPart > 0) {
            secondOperand = secondOperand + (Integer.parseInt(symbol) / (Math.pow(10, fractionalPart)));

            if (Math.abs(Math.rint(secondOperand * Math.pow(10, fractionalPart)) - secondOperand * Math.pow(10,
                    fractionalPart)) > 4.9E-324)
                secondOperand = Math.rint(secondOperand * Math.pow(10, fractionalPart)) / Math.pow(10,
                        fractionalPart);

            fractionalPart++;
        } else
            secondOperand = secondOperand * 10 + Integer.parseInt(symbol);

        memory = secondOperand;
        useMemory = false;
        useFirst = false;
        startNew = false;
    }

    void setOperator(String operator) {

        if (firstOperand != 0) {
            calculate();
        }

        firstOperand = secondOperand;
        secondOperand = 0;
        fractionalPart = 0;

        this.operator = operator.charAt(0);
        useMemory = true;
        useFirst = true;
        startNew = false;
    }

    void signChange() {
        secondOperand = -secondOperand;
    }

    void sqrt() {
        secondOperand = Math.sqrt(secondOperand);
        memory = secondOperand;
        startNew = false;
    }

    void round() {
        double roundingMode = Math.pow(10, fractionalPart - 1);
        secondOperand = (int)(secondOperand * roundingMode) / roundingMode;
    }
}