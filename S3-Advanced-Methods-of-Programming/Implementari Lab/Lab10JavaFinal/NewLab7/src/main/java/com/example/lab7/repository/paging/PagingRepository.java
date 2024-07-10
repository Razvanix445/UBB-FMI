package com.example.lab7.repository.paging;


import com.example.lab7.domain.Entity;
import com.example.lab7.repository.Repository;

public interface PagingRepository<ID,
        E extends Entity<ID>>
        extends Repository<ID, E> {

    Page<E> findAll(Pageable pageable);
}
