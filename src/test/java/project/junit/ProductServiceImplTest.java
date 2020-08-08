package project.junit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.model.binding.CommentsBindingModel;
import project.model.entity.Category;
import project.model.entity.CategoryName;
import project.model.entity.Comments;
import project.model.entity.Product;
import project.model.service.ProductServiceModel;
import project.model.view.ProductViewModel;
import project.repository.ProductRepository;
import project.service.CategoryService;
import project.service.CloudinaryService;
import project.service.Impl.ProductServiceImpl;
import project.service.ProductService;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    private ProductService productServiceTest;
    private ModelMapper modelMapper;

    @Mock
    ProductRepository mockProductRepository;

    @Mock
    CategoryService mockCategoryService;


    @Mock
    CloudinaryService mockCloudinaryService;


    @BeforeEach
    void setUp() {

        this.modelMapper = new ModelMapper();
        this.productServiceTest = new ProductServiceImpl(mockProductRepository,modelMapper, mockCategoryService, mockCloudinaryService);
    }

    @Test
    public void testAddProductToRepository() throws IOException {
        Product product = createProduct();
        ProductServiceModel model = this.modelMapper.map(product, ProductServiceModel.class);

        this.productServiceTest.addProduct(model);

        verify(this.mockProductRepository,times(1)).saveAndFlush(any());
    }

    @Test
    public void testFindAllProducts(){
        //Arrange

        Product product = createProduct();
        when(mockProductRepository.findAll()).thenReturn(List.of(product));
        //act
        List<ProductViewModel> models = this.productServiceTest.findAllProducts();

        //assert
        Assertions.assertEquals(1, models.size());

        ProductViewModel actualProduct = models.get(0);

        Assertions.assertEquals(product.getId(), actualProduct.getId());
        Assertions.assertEquals(product.getName(), actualProduct.getName());
        Assertions.assertEquals(product.getDescription(), actualProduct.getDescription());
        Assertions.assertEquals(product.getPrice(), actualProduct.getPrice());
        Assertions.assertEquals(product.getImgUrl(), actualProduct.getImgUrl());
    }

    @Test
    public void testProductCount(){
        long expectCount = 0;

        long actualCount = this.mockProductRepository.count();

        Assertions.assertEquals(expectCount, actualCount);
    }

    @Test
    public void testBasicBuyMethod(){
        Product product = createProduct();

        this.productServiceTest.buy(product.getId());

        verify(mockProductRepository, times(1)).deleteById(product.getId());
    }

    @Test
    public void testFindById(){
        Product product = createProduct();

        when(mockProductRepository.findById(product.getId())).thenReturn(Optional.of(product));

        ProductViewModel actualProduct = this.productServiceTest.findById(product.getId());

        Assertions.assertEquals(product.getId(), actualProduct.getId());
        Assertions.assertEquals(product.getName(), actualProduct.getName());
        Assertions.assertEquals(product.getPrice(), actualProduct.getPrice());
        Assertions.assertEquals(product.getDescription(), actualProduct.getDescription());
        Assertions.assertEquals(product.getImgUrl(), actualProduct.getImgUrl());

    }
    @Test
    public void testFindByCategory(){
        Product product = createProduct();

        when(mockProductRepository.findAllByCategory(product.getCategory())).thenReturn(List.of(product));

        List<ProductViewModel> actualProducts = this.productServiceTest.findAllByCategory(product.getCategory().getCategoryName().name());

        ProductViewModel actualProduct = actualProducts.get(0);
        actualProduct.setCategory(product.getCategory().getCategoryName().name());

        Assertions.assertEquals(product.getId(), actualProduct.getId());
        Assertions.assertEquals(product.getName(), actualProduct.getName());
        Assertions.assertEquals(product.getPrice(), actualProduct.getPrice());
        Assertions.assertEquals(product.getDescription(), actualProduct.getDescription());
        Assertions.assertEquals(product.getImgUrl(), actualProduct.getImgUrl());

    }



    private Product createProduct(){
        Product product = new Product();
        Category category = new Category(CategoryName.SALADS, "dadasfsaf");


        product.setId("1");
        product.setName("sladoled");
        product.setCategory(category);
        product.setDescription("mega qkiq sladoled");
        product.setPrice(BigDecimal.valueOf(12));
        product.setImgUrl(String.format("/img/%s.png",
                product.getCategory().getCategoryName().name().toLowerCase()));

        return product;
    }
}
