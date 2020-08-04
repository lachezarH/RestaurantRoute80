package project.service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.model.entity.Category;
import project.model.entity.CategoryName;
import project.model.entity.Product;
import project.model.service.ProductServiceModel;
import project.model.view.ProductViewModel;
import project.repository.ProductRepository;
import project.service.CategoryService;
import project.service.ProductService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }



    @Override
    @Transactional
    public void addProduct(ProductServiceModel productServiceModel) {

        Product product = this.modelMapper.map(productServiceModel, Product.class);
        Category category = this.modelMapper.map(this.categoryService.findByName(productServiceModel.getCategory().getCategoryName()), Category.class);

        product.setCategory(category);

        this.productRepository.saveAndFlush(product);
    }

    @Override
    public List<ProductViewModel> findAllProducts() {
        return this.productRepository
                .findAll()
                .stream()
                .map(product -> {
                    ProductViewModel productViewModel = this.modelMapper
                            .map(product, ProductViewModel.class);

                    productViewModel.setCategory(product.getCategory().getCategoryName().name());

                    productViewModel.setImgUrl(String.format("/img/%s.png",
                            product.getCategory().getCategoryName().name().toLowerCase()));
                    return productViewModel;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> findAllByCategory(String categoryName) {
        Category category = this.modelMapper.map(this.categoryService.findByName(CategoryName.valueOf(categoryName)), Category.class);

        List<ProductViewModel> products = this.productRepository.findAllByCategory(category).stream()
                .map(product ->
                        this.modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());

        return products;
    }

    @Override
    public long countOfProducts() {
        return this.productRepository.count();
    }

    @Override
    public void buy(String id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public void buyAll() {
        this.productRepository.deleteAll();
    }

    @Override
    public ProductServiceModel findById(String id) {
        Optional<Product> product = this.productRepository.findById(id);
        ProductViewModel model = this.modelMapper.map(product.orElse(null), ProductViewModel.class);
        ProductServiceModel modelservice = this.modelMapper.map(product.orElse(null), ProductServiceModel.class);

        System.out.println();
        return modelservice;
    }
/*
    @Override
    public List<ProductViewModel> findAllByCategoryHouseholds() {
        return this.productRepository.findAllByCategory_CategoryName_Household()
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(
                            product, ProductViewModel.class);
                    return productViewModel;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> findAllByCategoryDrinks() {
        return this.productRepository.findAllByCategory_CategoryName_Drink()
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(
                            product, ProductViewModel.class);
                    return productViewModel;
                }).collect(Collectors.toList());
    }


    @Override
    public List<ProductViewModel> findAllByCategoryFoods() {
        return this.productRepository.findAllByCategory_CategoryName_Food()
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(
                            product, ProductViewModel.class);
                    return productViewModel;
                }).collect(Collectors.toList());
    }


    @Override
    public List<ProductViewModel> findAllByCategoryOthers() {
        return this.productRepository.findAllByCategory_CategoryName_Other()
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(
                            product, ProductViewModel.class);
                    return productViewModel;
                }).collect(Collectors.toList());
    }*/

   /* @Override
    public List<ProductServiceModel> findByCategory(CategoryName category) {
        return this.productRepository.findAllByCategory_CategoryName(category)
                .stream().map(product -> {
                    ProductServiceModel productServiceModel = this.modelMapper
                            .map(product, ProductServiceModel.class);

                    return productServiceModel;
                }).collect(Collectors.toList());
    }*/


}
