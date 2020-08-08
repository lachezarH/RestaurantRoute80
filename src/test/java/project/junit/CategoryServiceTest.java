package project.junit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.model.entity.Category;
import project.model.entity.CategoryName;
import project.model.service.CategoryServiceModel;
import project.repository.CategoryRepository;
import project.service.CategoryService;
import project.service.Impl.CategoryServiceImpl;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    private CategoryService categoryServiceTest;
    private ModelMapper modelMapper;

    @Mock
    CategoryRepository mockCategoryRepository;

    @BeforeEach
    void setUp() {
        this.modelMapper = new ModelMapper();
        this.categoryServiceTest = new CategoryServiceImpl(mockCategoryRepository, modelMapper);
    }

    @Test
    public void testFindByCategory(){
        Category category = createCategory();

        when(mockCategoryRepository.findCategoriesByCategoryName(category.getCategoryName())).thenReturn(Optional.of(category));

        Category actualCategory = this.categoryServiceTest.find(category.getCategoryName());

        Assertions.assertEquals(category.getId(), actualCategory.getId());
        Assertions.assertEquals(category.getCategoryName(), actualCategory.getCategoryName());
        Assertions.assertEquals(category.getDescription(), actualCategory.getDescription());

    }
    @Test
    public void testFindByName(){
        Category category = createCategory();

        when(mockCategoryRepository.findCategoriesByCategoryName(category.getCategoryName())).thenReturn(Optional.of(category));

        CategoryServiceModel actualCategory = this.categoryServiceTest.findByName(category.getCategoryName());


        Assertions.assertEquals(category.getCategoryName(), actualCategory.getCategoryName());
        Assertions.assertEquals(category.getDescription(), actualCategory.getDescription());
    }

    @Test
    public void testCreateByCategoryName(){
        Category category = createCategory();

        this.categoryServiceTest.createByCategoryName(category.getCategoryName());

        verify(mockCategoryRepository, times(1)).save(any());
    }

    @Test
    public void testInitCategoriesMethod(){
        this.categoryServiceTest.initCategories();

        verify(mockCategoryRepository, times((int) Arrays.stream(CategoryName.values()).count())).save(any());


    }


    private Category createCategory(){
        Category category = new Category();
        category.setId("1");
        category.setCategoryName(CategoryName.SALADS);
        category.setDescription("salads salads");

        return  category;
    }
}
