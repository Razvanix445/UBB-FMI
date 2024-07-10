package ir.map.g222;

public class ExpressionDIV extends ComplexExpression {
    public ExpressionDIV(NumarComplex[] args) {
        super(Operation.DIV, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex numar1, NumarComplex numar2) {
        return numar1.impartire(numar2);
    }
}