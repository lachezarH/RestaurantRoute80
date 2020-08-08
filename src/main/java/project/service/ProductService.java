package project.service;


import project.model.entity.CategoryName;
import project.model.service.ProductServiceModel;
import project.model.view.ProductViewModel;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void addProduct(ProductServiceModel productServiceModel) throws IOException;


    List<ProductViewModel> findAllProducts();
    List<ProductViewModel> findAllByCategory(String categoryName);


    long countOfProducts();

    void buy(String id);



    ProductViewModel findById(String id);


}
