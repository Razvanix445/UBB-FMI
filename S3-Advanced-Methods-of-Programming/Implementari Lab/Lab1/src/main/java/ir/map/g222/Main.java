package ir.map.g222;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) {
        System.out.println(new NumarComplex(2, -5).adunare(new NumarComplex(3, 2)));
        System.out.println(new NumarComplex(2, 3).inmultire(new NumarComplex(3, 2)));
        System.out.println(new NumarComplex(2, 3).impartire(new NumarComplex(3, 2)));
        //Utilizare: java Main "2 + 3 * i + 5 - 6 * i + -2 * i"
        System.out.println(args[0]);
        System.out.println(args[1]);
        System.out.println(args[2]);
        System.out.println(args[3]);
        System.out.println(args[4]);

        if (args.length != 1) {
            System.err.println("java Main 2 + 3 * i + 5 - 6 * i + -2 * i Eroare.");
            System.exit(1);
        }

        String expressionStr = args[0];

        ExpressionParser parser = new ExpressionParser();
        ComplexExpression expression = parser.parse(expressionStr);

        NumarComplex rezultat = expression.execute();

        System.out.println("Rezultat: " + rezultat.toString());
    }
}






/*

enum Operation {
    ADD,
    SUB,
    MUL,
    DIV
}



public abstract class ComplexExpression {
    private Operation op;
    private ComplexNumber[] cn;
    public ComplexExpression(Operation op, ComplexNumber[] cn) {
        this.op = op;
        this.cn = cn;
    }
    public ComplexNumber execute(Operation op, ComplexNumber[] cn) {
        ComplexNumber result = cn[0];
        for (int i = 1; i < cn.length; i++) {
            result = executeOneOperation(op, cn[i]);
            return result;
        }
    }
    public abstract ComplexNumber executeOneOperation(CN c1, CN c2);
}
//apoi o clasa pentru fiecare operatie
//factory va decide pe ce operatie sa mearga, vom avea o metoda cu if-uri, sa folosim singleton, vom avea 2 metode: getInstance, createExpression(operatie, toateArgumentele)
//in parser ar trebui sa decidem operatia, sa verificam daca este valid numarul



public class CEAdd extends CE {
    public CEAdd(CN cn[]) {
        super(Operation.ADD, cn);
    }
    @Override
    public CN executeOneOperation(CN c1, CN c2) {
        return c1.add(c2);
    }
}

//factory = o metoda care poate sa decida
//parser va fi in main, va valida, va vedea ce operatie am, va vedea numerele
//la sfarsit de parser, va trebui sa avem operatia si lista de numere
//in main, ar trebui sa executam factory.createExpression
//operatia o obtinem din argumentul 1

public class EF {
    public EF instance=null;
    private EF() {
    }
    public EF getInstance() {
        if (instance == null)
            this.instance = new EF();
        return this.instance;
    }
    public CE createExpression(Operation op, CN[] cn) {
        if (op == null)
            return null;
        if (op == Operation.ADD)
            return new CEAdd(cn);
        ...urmatoarele if-uri cu urmatoarele operatii
    }
}
*/