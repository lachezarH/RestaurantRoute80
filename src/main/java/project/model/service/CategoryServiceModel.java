package project.model.service;


import org.hibernate.validator.constraints.UniqueElements;
import project.model.entity.CategoryName;

import javax.validation.constraints.NotNull;

public class CategoryServiceModel {

    private CategoryName categoryName;
    private String description;

    public CategoryServiceModel() {
    }

    @NotNull
    @UniqueElements
    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
