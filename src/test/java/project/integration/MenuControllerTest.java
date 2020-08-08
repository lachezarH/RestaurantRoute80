package project.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.model.entity.Category;
import project.model.entity.CategoryName;
import project.model.entity.Product;
import project.model.view.ProductViewModel;
import project.service.Impl.ProductServiceImpl;
import project.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="USER")
public class MenuControllerTest {


    @Autowired
    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @Mock
    ProductService productServiceTest;

    @BeforeEach
    void setUp() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void testMenuProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/menu"))
                .andExpect(status().isOk())
                .andExpect(view().name("menu"))
                .andExpect(model().attributeExists("salads", "salads"))
                .andExpect(model().attributeExists("appetizers", "appetizers"))
                .andExpect(model().attributeExists("soups", "soups"))
                .andExpect(model().attributeExists("dishes", "dishes"))
                .andExpect(model().attributeExists("desserts", "desserts"))
                .andExpect(model().attributeExists("alcoholic", "alcoholic"))
                .andExpect(model().attributeExists("nonAlcoholic", "nonAlcoholic"))
                .andExpect(model().attributeExists("nonAlcoholic", "nonAlcoholic"));

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
