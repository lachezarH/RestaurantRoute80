package project.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    private CategoryName categoryName;
    private String description;


    public Category() {
    }
    public Category(CategoryName categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;

    }

    @Enumerated(value = EnumType.STRING)
    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
