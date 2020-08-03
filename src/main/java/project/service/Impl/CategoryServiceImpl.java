package project.service.Impl;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.model.entity.Category;
import project.model.entity.CategoryName;
import project.model.service.CategoryServiceModel;
import project.repository.CategoryRepository;
import project.service.CategoryService;


import java.util.Arrays;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Category find(CategoryName categoryName) {
        return this.categoryRepository.findCategoriesByCategoryName(categoryName).orElse(null);
    }

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

    @Override
    public CategoryServiceModel findByName(CategoryName name) {

        return this.modelMapper.map(this.categoryRepository.findCategoriesByCategoryName(name).orElse(null), CategoryServiceModel.class);
    }
}
