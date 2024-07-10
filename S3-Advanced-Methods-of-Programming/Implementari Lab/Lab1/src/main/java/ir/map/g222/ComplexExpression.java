package ir.map.g222;

public abstract class ComplexExpression {
    protected Operation operation;
    protected NumarComplex[] args;

    public ComplexExpression(Operation operation, NumarComplex[] args) {
        this.operation = operation;
        this.args = args;
    }

    public NumarComplex execute() {
        NumarComplex rezultat = args[0];
        for (int i = 1; i < args.length; i++) {
            rezultat = executeOneOperation(rezultat, args[i]);
        }
        return rezultat;
    }

    protected abstract NumarComplex executeOneOperation(NumarComplex numar1, NumarComplex numar2);
}