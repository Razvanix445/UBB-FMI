using Lab14CSharp.domain;

namespace Lab14CSharp.repository;

public class FacturaFileRepository: IRepository<string, Factura>
{
    private readonly string fileName;

    public FacturaFileRepository(string fileName)
    {
        this.fileName = fileName;
    }

    public IEnumerable<Factura> FindAll()
    {
        var lines = File.ReadAllLines(fileName);
        return lines.Select(line =>
        {
            var parts = line.Split(',');
            return new Factura
            {
                Id = parts[0].Trim(),
                DataScadenta = DateTime.Parse(parts[1].Trim()),
                Categorie = (Category)Enum.Parse(typeof(Category), parts[2].Trim())
            };
        });
    }
}