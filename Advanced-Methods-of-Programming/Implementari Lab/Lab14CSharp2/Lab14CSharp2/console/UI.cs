using System.ComponentModel.Design.Serialization;
using Lab14CSharp.domain;
using Lab14CSharp.repository;
using Lab14CSharp.service;

namespace Lab14CSharp2.console;

public class UI
{
    private static string documentsFileName = "C:/Users/razva/RiderProjects/Lab14CSharp2/Lab14CSharp2/documents.txt";
    private static string facturiFileName = "C:/Users/razva/RiderProjects/Lab14CSharp2/Lab14CSharp2/facturi.txt";
    private static string achizitiiFileName = "C:/Users/razva/RiderProjects/Lab14CSharp2/Lab14CSharp2/achizitii.txt";
    
    private DocumentFileRepository documentRepository = new DocumentFileRepository(documentsFileName);
    private FacturaFileRepository facturaRepository = new FacturaFileRepository(facturiFileName);
    private AchizitieFileRepository achizitieRepository;

    private Service service;

    public UI()
    {
        achizitieRepository = new AchizitieFileRepository(achizitiiFileName, facturaRepository);
        service = new Service(documentRepository, facturaRepository, achizitieRepository);
    }
    
    public void Run()
    {
        IEnumerable<Document> documents = service.GetAllDocuments();
        IEnumerable<Factura> facturi = service.GetAllFacturi();
        IEnumerable<Achizitie> achizitii = service.GetAllAchizitii();

        /* DOCUMENTE */
        Console.WriteLine("DOCUMENTE:");
        foreach (var document in documents)
        {
            Console.WriteLine(document.ToString());
        }
        
        /* FACTURI */
        Console.WriteLine("\nFACTURI:");
        foreach (var factura in facturi)
        {
            Console.WriteLine(factura.ToString());
        }
        
        /* ACHIZITII */
        Console.WriteLine("\nACHIZITII:");
        foreach (var achizitie in achizitii)
        {
            Console.WriteLine(achizitie.ToString());
        }
        
        /* 1. Toate documentele emise in anul 2023 */
        DisplayAllDocumentsFrom2023();
        
        /* 2. Toate facturile scadente in luna curenta */
        DisplayAllFacturiScadenteCurrentMonth();
            
        /* 3. Toate facturile cu cel putin 3 produse achizitionate (se considera si cantitatea) */
        DisplayAllFacturiWithAtLeast3Produse();
        
        /* 4. Toate achizitiile din categoria Utilities */
        DisplayAllAchizitiiWithCategoryUtilities();
        
        /* 5. Se returneaza categoria de facturi care a inregistrat cele mai multe cheltuieli */
        DisplayCategoryWithMostCheltuieli();
    }

    private void DisplayAllDocumentsFrom2023()
    {
        Console.WriteLine("\nAll Documents From 2023:");
        IEnumerable<Document> documents = service.GetAllDocumentsFrom2023();
        foreach (Document document in documents)
        {
            Console.WriteLine("Name: " + document.Nume + ", DataEmitere: " + document.DataEmitere);
        }
    }

    private void DisplayAllFacturiScadenteCurrentMonth()
    {
        Console.WriteLine("\nAll Facturi With DataScadenta Equal to Current Month:");
        IEnumerable<Factura> facturi = service.GetAllFacturiScadenteCurrentMonth();
        foreach (Factura factura in facturi)
        {
            Console.WriteLine("Name: " + factura.Nume + ", DataScadenta: " + factura.DataScadenta);
        }
    }

    private void DisplayAllFacturiWithAtLeast3Produse()
    {
        Console.WriteLine("\nAll Facturi With At Least 3 Products:");
        IEnumerable<Factura> facturi = service.GetAllFacturiWithAtLeast3Produse();
        foreach (Factura factura in facturi)
        {
            Console.WriteLine("Name: " + factura.Nume + ", No Of Products: " + factura.Achizitii.Count);
        }
    }

    private void DisplayAllAchizitiiWithCategoryUtilities()
    {
        Console.WriteLine("\nAll Achizitii With Category Utilities:");
        IEnumerable<Achizitie> achizities = service.GetAllAchizitiiWithCategoryUtilities();
        foreach (Achizitie achizitie in achizities)
        {
            Console.WriteLine("Product: " + achizitie.Produs + ", Factura Name: " + achizitie.Factura.Nume);
        }
    }

    private void DisplayCategoryWithMostCheltuieli()
    {
        Console.WriteLine("\nThe Category With The Most Cheltuieli:");
        Category category = service.GetCategoryWithMostCheltuieli();
        Console.WriteLine(category);
    }
}