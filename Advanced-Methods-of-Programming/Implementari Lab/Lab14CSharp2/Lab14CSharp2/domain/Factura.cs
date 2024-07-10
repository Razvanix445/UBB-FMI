namespace Lab14CSharp.domain;

public class Factura: Document
{
    public DateTime DataScadenta { get; set; }
    public List<Achizitie> Achizitii { get; set; }
    public Category Categorie { get; set; }

    public override string ToString()
    {
        return this.DataScadenta + " " + this.Achizitii + " " + this.Categorie;
    }
}