package ir.map.g222;

public class ExpressionFactory {
    private static final ExpressionFactory instance = new ExpressionFactory();

    private ExpressionFactory() {
    }

    public static ExpressionFactory getInstance() {
        return instance;
    }

    public ComplexExpression createExpression(Operation operation, NumarComplex[] args) {
        if (operation == null) {
            return null;
        }
        switch (operation) {
            case ADD:
                return new ExpressionADD(args);
            case SUB:
                return new ExpressionSUB(args);
            case MUL:
                return new ExpressionMUL(args);
            case DIV:
                return new ExpressionDIV(args);
            default:
                throw new IllegalArgumentException("Operatia nu este gasita: " + operation);
        }
    }
}