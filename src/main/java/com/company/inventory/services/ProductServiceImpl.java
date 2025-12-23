package com.company.inventory.services;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.dao.IProductDao;
import com.company.inventory.model.Category;
import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.util.Util;

@Service
public class ProductServiceImpl implements IProductService {

	private ICategoryDao categoryDao;
	private IProductDao productDao;
	
	public ProductServiceImpl(ICategoryDao categoryDao, IProductDao productDao) {
		super();
		this.categoryDao = categoryDao;
		this.productDao = productDao;
	}

	
	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			
			/* Buscar categoria del producto */
			
			Optional<Category> category = categoryDao.findById(categoryId);
			
			if (category.isPresent()) {
				product.setCategory(category.get());
			} else {
				response.setMetadata("respuesta nok", "-1", "Categoria no encontrada");
				return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);
			}
			
			// guarda el producto
			Product productSaved = productDao.save(product);
			
			if (productSaved != null) {
				list.add(productSaved);
				response.getProduct().setProducts(list);
				response.setMetadata("respuesta ok", "00", "Producto guardado");
			}else {
				response.setMetadata("respuesta nok", "-1", "Producto no guardado");
				return new ResponseEntity<ProductResponseRest>(response,HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta nok", "-1", "Error al guardar el producto.");
			return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
	}


	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> searchById(Long id) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();

		try {
			
			Optional<Product> product = productDao.findById(id);
			
			if (product.isPresent()) {
				byte[] imageDecompressed = Util.decompressZLib(product.get().getPicture()); //descomprime el archivo
				product.get().setPicture(imageDecompressed); // reemplaza la imagen comprimida por la descomprimida en l objeto product
				
				list.add(product.get());
				response.getProduct().setProducts(list);
				
				response.setMetadata("Respuesta Ok!", "200", "Producto encontrado");
			} else {
				response.setMetadata("Respuesta nok!", "-1", "Producto no encotrado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
			
		} catch (Exception e) {
			
			response.setMetadata("Respuesta nok!", "-1", "Error al consultar por id");
			e.getStackTrace();
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}


	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> searchByName(String name) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		List<Product> listAux = new ArrayList<>();

		try {
			
			listAux = productDao.findByNameLike(name);
			
			if (listAux.size() > 0) {
				
				listAux.stream().forEach( (p) -> {
					byte[] imageDecompressed = Util.decompressZLib(p.getPicture()); //descomprime el archivo
					p.setPicture(imageDecompressed); // reemplaza la imagen comprimida por la descomprimida en l objeto product
					list.add(p);
				});
				
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta Ok!", "200", "Productos encontrados");
			} else {
				response.setMetadata("Respuesta nok!", "-1", "Productos no encotrados");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
			
		} catch (Exception e) {
			
			response.setMetadata("Respuesta nok!", "-1", "Error al consultar producto por nombre");
			e.getStackTrace();
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}
	

}
