package project.service;


import project.model.entity.CategoryName;
import project.model.service.ProductServiceModel;
import project.model.view.ProductViewModel;

import java.util.List;

public interface ProductService {
    void addProduct(ProductServiceModel productServiceModel);


    List<ProductViewModel> findAllProducts();
    List<ProductViewModel> findAllByCategory(String categoryName);

   // BigDecimal sumOfPrice(List<ProductServiceModel> productServiceModels);

    long countOfProducts();

    void buy(String id);

    void buyAll();
}
