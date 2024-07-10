using Lab14CSharp.domain;

namespace Lab14CSharp.repository;

public class DocumentFileRepository: IRepository<string, Document>
{
    private readonly string fileName;

    public DocumentFileRepository(string fileName)
    {
        this.fileName = fileName;
    }

    public IEnumerable<Document> FindAll()
    {
        var lines = File.ReadAllLines(fileName);
        return lines.Select(line =>
        {
            var parts = line.Split(',');
            return new Document
            {
                Id = parts[0].Trim(),
                Nume = parts[1].Trim(),
                DataEmitere = DateTime.Parse(parts[2].Trim())
            };
        });
    }
}