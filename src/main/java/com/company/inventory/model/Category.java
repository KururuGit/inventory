package com.company.inventory.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data // Evita tener que definir el constructor, los Getters y los Setters para esta clase
@Entity // Indica que será una clase Entidad
@Table(name="category") // Indica que esta clase va a tener una tabla "category"
public class Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersioUID = -4310027227752446841L; // Ayuda a generar un ID
	
	@Id // indica que esta variable va a ser el ID del registro
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID se va a generar automáticamente.
	private long id;
	private String name;
	private String description;
	

}
