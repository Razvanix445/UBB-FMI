package ir.map.g222;

public class NumarComplex {

    @Override
    public String toString() {
        if (this.im >= 0)
            return this.re + " + " +  this.im + " * i";
        else
            return this.re + " " +  this.im + " * i";

    }
    private double re;
    private double im;

    public NumarComplex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public void setRe(double re) {
        this.re = re;
    }

    public void setIm(double im) {
        this.im = im;
    }

    public double getIm() {
        return im;
    }

    public NumarComplex adunare(NumarComplex numar) {
        return new NumarComplex(this.re + numar.re, this.im + numar.im);
    }
    public NumarComplex scadere(NumarComplex numar) {
        return new NumarComplex(this.re - numar.re, this.im - numar.im);
    }
    public NumarComplex inmultire(NumarComplex numar) {
        return new NumarComplex(this.re * numar.re - this.im * numar.im, this.re * numar.im + this.im * numar.re);
    }
    public NumarComplex impartire(NumarComplex numar) {
        NumarComplex numarator = inmultire(conjugatul(numar));
        Double numitor = numar.re * numar.re + numar.im * numar.im;
        return new NumarComplex(numarator.re/numitor, numarator.im/numitor);
    }
    public NumarComplex conjugatul(NumarComplex numar) {
        return new NumarComplex(numar.re, numar.im * (-1));
    }
}