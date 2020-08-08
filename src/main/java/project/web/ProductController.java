package project.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.model.binding.ProductAddBindingModel;
import project.model.entity.CategoryName;
import project.model.service.ProductServiceModel;
import project.service.ProductService;


import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }


    //go to product-add
    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String add(Model model) {

        if (!model.containsAttribute("productAddBindingModel")) {
            model.addAttribute("categoryNames", CategoryName.values());
            model.addAttribute("productAddBindingModel", new ProductAddBindingModel());
        }

        return "product-add";
    }

    //adding product in productRepository
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addConfirm(@Valid @ModelAttribute("productAddBindingModel")ProductAddBindingModel productAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){

        System.out.println();
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute("categoryNames", CategoryName.values());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

            return "redirect:add";
        }

        try {
            this.productService.addProduct(this.modelMapper.map(productAddBindingModel, ProductServiceModel.class));
        }catch (IllegalArgumentException | IOException e){
            redirectAttributes.addFlashAttribute("alreadyExist", true);
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute("categoryNames", CategoryName.values());


            return "redirect:add";

        }

        return "redirect:/menu";
    }

    //mapping for basic buy method
    @GetMapping("/buy/{id}")
    @PreAuthorize("hasRole('USER')")
    public String buy(@PathVariable("id") String  id){
        this.productService.buy(id);

        return "redirect:/";
    }


}
