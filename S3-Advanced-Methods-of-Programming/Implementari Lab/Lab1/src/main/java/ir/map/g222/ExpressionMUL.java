package ir.map.g222;

public class ExpressionMUL extends ComplexExpression {
    public ExpressionMUL(NumarComplex[] args) {
        super(Operation.MUL, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex numar1, NumarComplex numar2) {
        return numar1.inmultire(numar2);
    }
}