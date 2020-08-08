package project.service.Impl;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.model.entity.Category;
import project.model.entity.CategoryName;
import project.model.service.CategoryServiceModel;
import project.repository.CategoryRepository;
import project.service.CategoryService;


import java.util.Arrays;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    //find category by enum CategoryName and return Category
    @Override
    public Category find(CategoryName categoryName) {
        return this.categoryRepository.findCategoriesByCategoryName(categoryName).orElse(null);
    }

    //inti all CategoryName.values() to categoryRepository
    //this is useless
    @Override
    public void initCategories() {
        if (this.categoryRepository.count() == 0) {
            Arrays.stream(CategoryName.values())
                    .forEach(categoryName -> {
                        this.categoryRepository.save(new Category(categoryName,
                                String.format("Description for %s",
                                        categoryName.name())));
                    });
        }
    }

    //find category by enum CategoryName and return CategoryServiceModel
    @Override
    public CategoryServiceModel findByName(CategoryName name) {
        Category category = this.categoryRepository.findCategoriesByCategoryName(name).orElse(null);

        return this.modelMapper.map(Objects.requireNonNull(category), CategoryServiceModel.class);
    }

    //creating Category by  enum CategoryName
    @Override
    public Category createByCategoryName(CategoryName categoryName) {

        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setDescription(String.format("Description for %s",
                categoryName.name()));

        return this.categoryRepository.save(category);
    }
}
