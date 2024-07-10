package ir.map.g222;

public class ExpressionADD extends ComplexExpression {
    public ExpressionADD(NumarComplex[] args) {
        super(Operation.ADD, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex numar1, NumarComplex numar2) {
        return numar1.adunare(numar2);
    }
}