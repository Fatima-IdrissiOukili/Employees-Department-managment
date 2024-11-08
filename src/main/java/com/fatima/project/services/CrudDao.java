package com.fatima.project.services;

import java.io.Serializable;
import java.util.List;

public interface CrudDao<T, ID extends Serializable> {
	void save(T entity);

	T getById(ID id);

	List<T> getAll();

	void update(T entity);

	void delete(T entity);
	
	List<T> search(String cmh);
	
}
