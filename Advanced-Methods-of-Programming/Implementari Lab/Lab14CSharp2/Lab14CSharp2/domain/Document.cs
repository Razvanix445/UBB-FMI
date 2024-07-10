namespace Lab14CSharp.domain;

public class Document: Entity<string>
{
    public string Nume { get; set; }
    public DateTime DataEmitere { get; set; }

    public override string ToString()
    {
        return this.Nume + " " + this.DataEmitere;
    }
}