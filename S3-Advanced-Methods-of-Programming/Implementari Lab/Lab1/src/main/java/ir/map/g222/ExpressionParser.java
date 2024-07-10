package ir.map.g222;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {
    private static final String complexNumberPattern = "([+-]?\\d+(\\.\\d+)?)?[+-]?\\d+(\\.\\d+)?\\*i";
    private static final String operatorPattern = "\\+|\\-|\\*|\\/";

    public ComplexExpression parse(String expression) {
        Pattern numberPattern = Pattern.compile(complexNumberPattern);
        Pattern operatorsPattern = Pattern.compile(operatorPattern);

        Matcher numberMatcher = numberPattern.matcher(expression);
        Matcher operatorsMatcher = operatorsPattern.matcher(expression);

        NumarComplex[] numbers = new NumarComplex[expression.length()];
        Operation[] operators = new Operation[expression.length()];
        int numIndex = 0;
        int opIndex = 0;

        while (numberMatcher.find()) {
            String numberStr = numberMatcher.group();
            String operatorStr = operatorsMatcher.find() ? operatorsMatcher.group() : null;

            double re = 0.0;
            double im = 0.0;

            if (numberStr.contains("*i")) {
                //este un numar complex de forma a+bi
                String[] parts = numberStr.split("[*i]");
                if (parts.length > 0) {
                    re = Double.parseDouble(parts[0]);
                }
                im = Double.parseDouble(parts[1]);
            }
            else {
                //este un numar real
                re = Double.parseDouble(numberStr);
            }

            numbers[numIndex++] = new NumarComplex(re, im);

            if (operatorStr != null) {
                operators[opIndex++] = getOperationFromString(operatorStr);
            }
        }

        ComplexExpression expressionObj = createComplexExpression(numbers, operators);
        return expressionObj;
    }

    private Operation getOperationFromString(String operatorStr) {
        switch (operatorStr) {
            case "+":
                return Operation.ADD;
            case "-":
                return Operation.SUB;
            case "*":
                return Operation.MUL;
            case "/":
                return Operation.DIV;
            default:
                throw new IllegalArgumentException("Operator invalid: " + operatorStr);
        }
    }

    private ComplexExpression createComplexExpression(NumarComplex[] numbers, Operation[] operators) {
        if (operators[0] == null) {
            throw new IllegalArgumentException("Expresia trebuie sa inceapa cu un numar complex!");
        }

        ExpressionFactory factory = ExpressionFactory.getInstance();
        ComplexExpression expression = factory.createExpression(operators[0], numbers);

        for (int i = 1; i < operators.length; i++) {
            expression = factory.createExpression(operators[i], new NumarComplex[]{ expression.execute(), numbers[i] });
        }
        return expression;
    }
}