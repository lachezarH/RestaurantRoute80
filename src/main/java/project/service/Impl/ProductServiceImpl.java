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
import project.service.CloudinaryService;
import project.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;


    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryService categoryService, CloudinaryService cloudinaryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.cloudinaryService = cloudinaryService;
    }



    //adding products to repository by ProductServiceModel
    @Override
    @Transactional
    public void addProduct(ProductServiceModel productServiceModel) throws IOException {

        Product product = this.modelMapper.map(productServiceModel, Product.class);
        Category category = this.categoryService.createByCategoryName(productServiceModel.getCategory().getCategoryName());

        String imgUrl = this.cloudinaryService.uploadImage(productServiceModel.getImg());

        product.setImgUrl(imgUrl);

        product.setCategory(category);

        this.productRepository.saveAndFlush(product);
    }

    //findAll products return List<ProductViewModel> models
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

    //findAll products by Category return List<ProductViewModel> models
    @Override
    public List<ProductViewModel> findAllByCategory(String categoryName) {
        Category category = this.modelMapper.map(this.categoryService.findByName(CategoryName.valueOf(categoryName)), Category.class);

        List<ProductViewModel> products = this.productRepository.findAllByCategory(category).stream()
                .map(product ->
                        this.modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());

        return products;
    }

    //count of products in productRepository
    @Override
    public long countOfProducts() {
        return this.productRepository.count();
    }

    //basic buy method who just delete product from productRepository
    //TODO: should implements real buy method
    @Override
    public void buy(String id) {
        this.productRepository.deleteById(id);
    }


    //find ProductViewModel by ID
    @Override
    public ProductViewModel findById(String id) {
        Optional<Product> product = this.productRepository.findById(id);
        ProductViewModel model = this.modelMapper.map(product.orElse(null), ProductViewModel.class);
        ProductServiceModel modelservice = this.modelMapper.map(product.orElse(null), ProductServiceModel.class);

        System.out.println();
        return model;
    }



}
