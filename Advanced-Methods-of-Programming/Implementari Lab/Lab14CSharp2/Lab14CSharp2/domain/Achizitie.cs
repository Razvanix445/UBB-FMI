namespace Lab14CSharp.domain;

public class Achizitie: Entity<string>
{
    public string Produs { get; set; }
    public int Cantitate { get; set; }
    public double PretProdus { get; set; }
    public Factura Factura { get; set; }

    public override string ToString()
    {
        return this.Produs + " " + this.Cantitate + " " + this.PretProdus + " " + this.Factura;
    }
}