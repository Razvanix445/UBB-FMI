using System.Timers;
using Lab14CSharp.domain;

namespace Lab14CSharp.repository;

public class AchizitieFileRepository: IRepository<string, Achizitie>
{
    private readonly string fileName;
    private FacturaFileRepository _facturaFileRepository;
    
    public AchizitieFileRepository(string fileName, FacturaFileRepository facturaFileRepository)
    {
        this.fileName = fileName;
        this._facturaFileRepository = facturaFileRepository;
    }

    public IEnumerable<Achizitie> FindAll()
    {
        var lines = File.ReadAllLines(fileName);
        return lines.Select(line =>
        {
            var parts = line.Split(',');
            var facturaId = parts[4].Trim();
            Factura factura = _facturaFileRepository.FindAll().FirstOrDefault(factura => factura.Id == facturaId);
            return new Achizitie
            {
                Id = parts[0].Trim(),
                Produs = parts[1].Trim(),
                Cantitate = int.Parse(parts[2].Trim()),
                PretProdus = double.Parse(parts[3].Trim()),
                Factura = factura
            };
        });
    }
}