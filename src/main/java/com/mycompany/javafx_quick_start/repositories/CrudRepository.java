package com.mycompany.javafx_quick_start.repositories;

import java.util.List;

public interface CrudRepository<T, ID> {
    int create(T entity);
    List<T> findById(ID id);
    List<T> findAll();
    int update(T entity);
    int delete(ID id);
}

