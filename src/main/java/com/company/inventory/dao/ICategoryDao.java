/*
 * Permite el acceso a los datos de la base de datos
 */

package com.company.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.inventory.model.Category;
/* CrudRepository es una interfaz en la que se encuentran todos los métodos
 * necesarios para manipular los datos
 */
public interface ICategoryDao extends CrudRepository<Category, Long> {

}
