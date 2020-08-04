package project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import project.model.service.ProductServiceModel;
import project.model.view.ProductViewModel;
import project.service.ProductService;
import project.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MenuController {

    private final ProductService productService;

    public MenuController(ProductService productService) {
        this.productService = productService;

    }


    @GetMapping("/menu")
    ModelAndView menu(ModelAndView modelAndView){
        List<ProductViewModel> productViewModels = this.productService.findAllProducts();
        //    SALADS, APPETIZERS, SOUPS, DISHES, DESSERTS,ALCOHOLIC,NON_ALCOHOLIC
        List<ProductViewModel> salads = productViewModels.stream().filter(p -> p.getCategory().equals("SALADS")).collect(Collectors.toList());
        List<ProductViewModel> appetizers = productViewModels.stream().filter(p -> p.getCategory().equals("APPETIZERS")).collect(Collectors.toList());
        List<ProductViewModel> soups = productViewModels.stream().filter(p -> p.getCategory().equals("SOUPS")).collect(Collectors.toList());
        List<ProductViewModel> dishes = productViewModels.stream().filter(p -> p.getCategory().equals("DISHES")).collect(Collectors.toList());
        List<ProductViewModel> desserts = productViewModels.stream().filter(p -> p.getCategory().equals("DESSERTS")).collect(Collectors.toList());
        List<ProductViewModel> alcoholic = productViewModels.stream().filter(p -> p.getCategory().equals("ALCOHOLIC")).collect(Collectors.toList());
        List<ProductViewModel> nonAlcoholic = productViewModels.stream().filter(p -> p.getCategory().equals("NON_ALCOHOLIC")).collect(Collectors.toList());

        modelAndView.addObject("salads", salads);
        modelAndView.addObject("appetizers", appetizers);
        modelAndView.addObject("soups", soups);
        modelAndView.addObject("dishes", dishes);
        modelAndView.addObject("desserts", desserts);
        modelAndView.addObject("alcoholic", alcoholic);
        modelAndView.addObject("nonAlcoholic", nonAlcoholic);

        modelAndView.setViewName("menu");


        return modelAndView;
    }

    @GetMapping("/buy/{id}")
    public String buy(@PathVariable("id") String  id){
        this.productService.buy(id);

        return "redirect:/";
    }

    @GetMapping("/details/{id}")
    public ModelAndView details (@PathVariable("id") String  id ,ModelAndView modelAndView){
        ProductServiceModel model = this.productService.findById(id);
        modelAndView.addObject("model", model);
        modelAndView.setViewName("details");

        return modelAndView;
    }
}
