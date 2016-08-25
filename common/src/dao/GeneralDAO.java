package dao;

import java.util.List;

import utils.PaginationFilter;

public interface GeneralDAO<T> {
	
    void add(T t);

    void delete(Object obj);
    
    void delete(Long id);

    List<T> find();
    
    List<T> find(PaginationFilter pagination);
    
    T find(Long id);

    void update(T t); 
    
    int countAll();

}
