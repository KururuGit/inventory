package com.company.inventory.services;

import org.springframework.http.ResponseEntity;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;

public interface ICategoryService {
	/* ResponseEntity es una interfaz que permite dar una estructura de respuesta HTTP
	 * y englobar una respuesta 
	 */
	public ResponseEntity<CategoryResponseRest> search(); // Método para buscar una categoria
	public ResponseEntity<CategoryResponseRest> searchById(Long id);
	
	public ResponseEntity<CategoryResponseRest> save(Category category);
	
	public ResponseEntity<CategoryResponseRest> update(Category category, Long id);
	
	public ResponseEntity<CategoryResponseRest> deleteById(Long id);

}
