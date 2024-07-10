using System.Collections.Generic;
using ProiectRezervariCurse.domain;

namespace ProiectRezervariCurse.repository
{
    public interface IRepository<ID, E> where E: Entity<ID>
    {
        /*
         * Find the entity with the given id
         * @param id
         */
        E FindOne(ID id);
    
        /*
         * @return all entities
         */
        IEnumerable<E> FindAll();
    
        /*
         * Save an entity in the repository
         * @param entity
         */
        E Save(E entity);
    
        /*
         * Delete the entity with the given id
         * @param id
         */
        E Delete(ID id);
    
        /*
         * Update the entity
         * @param entity
         */
        E Update(E entity);
    }
}