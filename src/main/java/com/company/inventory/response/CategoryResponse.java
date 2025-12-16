package com.company.inventory.response;

import com.company.inventory.model.Category;

import java.util.List;
import lombok.Data;

@Data
public class CategoryResponse {
	private List<Category> category;
}
