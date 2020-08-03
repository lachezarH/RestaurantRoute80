package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.entity.Category;
import project.model.entity.CategoryName;

import project.model.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    Optional<Product> findByName(String name);



    List<Product> findAllByCategory(Category category);


}
