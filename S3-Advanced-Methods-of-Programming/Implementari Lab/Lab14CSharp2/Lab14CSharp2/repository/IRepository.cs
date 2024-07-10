using Lab14CSharp.domain;

namespace Lab14CSharp.repository;

public interface IRepository<ID, E> where E: Entity<ID>
{
    IEnumerable<E> FindAll();
}