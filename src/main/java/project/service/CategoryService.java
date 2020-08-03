package project.service;


import project.model.entity.Category;
import project.model.entity.CategoryName;
import project.model.service.CategoryServiceModel;

public interface CategoryService {
    Category find(CategoryName categoryName);

    void initCategories();

    CategoryServiceModel findByName(CategoryName name);


}
