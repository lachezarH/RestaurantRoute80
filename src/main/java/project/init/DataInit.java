package project.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.service.CategoryService;
import project.service.UserService;

@Component
public class DataInit implements CommandLineRunner {

    private final UserService userService;
    private final CategoryService categoryService;

    public DataInit(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Override
    public void run(String... args) throws Exception {
      //  this.userService.initRoles();
        this.categoryService.initCategories();


    }
}
