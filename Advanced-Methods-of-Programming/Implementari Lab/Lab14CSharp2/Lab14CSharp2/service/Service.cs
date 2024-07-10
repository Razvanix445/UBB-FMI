using Lab14CSharp.domain;
using Lab14CSharp.repository;

namespace Lab14CSharp.service;

public class Service
{
    private readonly DocumentFileRepository _documentFileRepository;
    private readonly FacturaFileRepository _facturaFileRepository;
    private readonly AchizitieFileRepository _achizitieFileRepository;

    public Service(DocumentFileRepository documentFileRepository, FacturaFileRepository facturaFileRepository,
        AchizitieFileRepository achizitieFileRepository)
    {
        this._documentFileRepository = documentFileRepository;
        this._facturaFileRepository = facturaFileRepository;
        this._achizitieFileRepository = achizitieFileRepository;
    }

    public List<Factura> PopulareFacturiCuAchizitii()
    {
        List<Factura> facturiCuAchizitii = new List<Factura>();
        IEnumerable<Factura> facturi = GetAllFacturi();
        IEnumerable<Achizitie> achizitii = GetAllAchizitii();

        foreach (Factura factura in facturi)
        {
            Document document = GetAllDocuments().FirstOrDefault(document => document.Id == factura.Id);
            if (document != null)
            {
                factura.Nume = document.Nume;
                factura.DataEmitere = document.DataEmitere;
            }
            factura.Achizitii = new List<Achizitie>();
            factura.Achizitii.AddRange(achizitii.Where(achizitie => achizitie.Factura.Id == factura.Id));
            facturiCuAchizitii.Add(factura);
        }

        return facturiCuAchizitii;
    }

    public List<Achizitie> PopulareAchizitiiCuFacturi()
    {
        List<Factura> facturi = PopulareFacturiCuAchizitii();
        IEnumerable<Achizitie> achizitii = GetAllAchizitii();
        
        List<Achizitie> achizitiiCuFactura = achizitii
            .Where(achizitie => facturi.Any(factura => achizitie.Factura.Id == factura.Id))
            .Select(achizitie =>
            {
                Factura factura = facturi.First(f => f.Id == achizitie.Factura.Id);
                achizitie.Factura.Nume = factura.Nume;
                achizitie.Factura.DataEmitere = factura.DataEmitere;
                return achizitie;
            })
            .ToList();

        return achizitiiCuFactura;
    }

    public IEnumerable<Document> GetAllDocuments()
    {
        return _documentFileRepository.FindAll();
    }

    public IEnumerable<Factura> GetAllFacturi()
    {
        return _facturaFileRepository.FindAll();
    }

    public IEnumerable<Achizitie> GetAllAchizitii()
    {
        return _achizitieFileRepository.FindAll();
    }

    /* 1. Toate documentele emise in anul 2023 */
    public IEnumerable<Document> GetAllDocumentsFrom2023()
    {
        DateTime startOf2023 = new DateTime(2023, 1, 1);
        DateTime endOf2023 = new DateTime(2023, 12, 31, 23, 59, 59);
        return _documentFileRepository.FindAll().Where(document =>
            document.DataEmitere >= startOf2023 && document.DataEmitere <= endOf2023);
    }

    /* 2. Toate facturile scadente in luna curenta */
    public IEnumerable<Factura> GetAllFacturiScadenteCurrentMonth()
    {
        DateTime currentDate = DateTime.Now;
        int currentMonth = currentDate.Month;
        return PopulareFacturiCuAchizitii().Where(factura => factura.DataScadenta.Month == currentMonth);
    }

    /* 3. Toate facturile cu cel putin 3 produse achizitionate (se considera si cantitatea) */
    public IEnumerable<Factura> GetAllFacturiWithAtLeast3Produse()
    {
        return PopulareFacturiCuAchizitii().Where(factura => factura.Achizitii.Count >= 3);
    }
    
    /* 4. Toate achizitiile din categoria Utilities */
    public IEnumerable<Achizitie> GetAllAchizitiiWithCategoryUtilities()
    {
        IEnumerable<Factura> facturi = PopulareFacturiCuAchizitii();
        IEnumerable<Achizitie> achizitii = PopulareAchizitiiCuFacturi();
        return achizitii.Where(achizitie => achizitie.Factura.Categorie == Category.Utilities);
    }
    
    /* 5. Se returneaza categoria de facturi care a inregistrat cele mai multe cheltuieli */
    public Category GetCategoryWithMostCheltuieli()
    {
        var cheltuieliByCategory = PopulareFacturiCuAchizitii()
            .GroupBy(factura => factura.Categorie)
            .Select(group => new
            {
                Category = group.Key,
                cheltuieliTotale = group.Sum(factura =>
                    factura.Achizitii.Sum(achizitie => achizitie.Cantitate * achizitie.PretProdus))
            })
            .OrderByDescending(group => group.cheltuieliTotale)
            .FirstOrDefault();
        return cheltuieliByCategory?.Category ?? Category.Utilities;
    }
}