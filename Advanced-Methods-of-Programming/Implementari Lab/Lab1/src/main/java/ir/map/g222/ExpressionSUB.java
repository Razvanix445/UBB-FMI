package ir.map.g222;

public class ExpressionSUB extends ComplexExpression {
    public ExpressionSUB(NumarComplex[] args) {
        super(Operation.SUB, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex numar1, NumarComplex numar2) {
        return numar1.scadere(numar2);
    }
}