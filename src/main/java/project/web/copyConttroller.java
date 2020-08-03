/*
package project.web;


import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.model.binding.UserLoginBindingModel;
import project.model.binding.UserRegisterBindingModel;
import project.model.service.UserServiceModel;
import project.model.view.ProductViewModel;
import project.service.ProductService;
import project.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


public class copyConttroller {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    //  private final ItemService itemService;
////ItemService itemService
    public copyConttroller(ProductService productService, ModelMapper modelMapper) {
        //this.itemService = itemService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

   */
/* @GetMapping("/")*//*

    public ModelAndView home(HttpSession httpSession, ModelAndView modelAndView) {
        System.out.println();
      */
/*  if (httpSession.getAttribute("user") == null) {
       //     modelAndView.addObject("products", this.productService.findAllProducts());
            modelAndView.setViewName("index");
        } else {*//*

        //    modelAndView.addObject("products", this.productService.findAllProducts());
        modelAndView.setViewName("home");



      */
/*  public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }*//*


        // Getting all lists of items

        //    SALADS, APPETIZERS, SOUPS, DISHES, DESSERTS,ALCOHOLIC,NON_ALCOHOLIC

        if (this.productService.countOfProducts() != 0) {
            List<ProductViewModel> salads = this.productService.findAllByCategory("SALADS")
                    .stream().map(product -> {
                        ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                        productViewModel.setImgUrl(String.format("/img/%s.png", product.getCategory().getCategoryName()));
                        return productViewModel;
                    })
                    .collect(Collectors.toList());
            List<ProductViewModel> appetizers = this.productService.findAllByCategory("APPETIZERS")
                    .stream().map(product -> {
                        ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                        productViewModel.setImgUrl(String.format("/img/%s.png", product.getCategory().getCategoryName()));
                        return productViewModel;
                    })
                    .collect(Collectors.toList());

            List<ProductViewModel> soups = this.productService.findAllByCategory("SOUPS")
                    .stream().map(product -> {
                        ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                        productViewModel.setImgUrl(String.format("/img/%s.png", product.getCategory().getCategoryName()));
                        return productViewModel;
                    })
                    .collect(Collectors.toList());

            List<ProductViewModel> dishes = this.productService.findAllByCategory("DISHES")
                    .stream().map(product -> {
                        ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                        productViewModel.setImgUrl(String.format("/img/%s.png", product.getCategory().getCategoryName()));
                        return productViewModel;
                    })
                    .collect(Collectors.toList());

            List<ProductViewModel> desserts = this.productService.findAllByCategory("DESSERTS")
                    .stream().map(product -> {
                        ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                        productViewModel.setImgUrl(String.format("/img/%s.png", product.getCategory().getCategoryName()));
                        return productViewModel;
                    })
                    .collect(Collectors.toList());

            List<ProductViewModel> alcoholic = this.productService.findAllByCategory("ALCOHOLIC")
                    .stream().map(product -> {
                        ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                        productViewModel.setImgUrl(String.format("/img/%s.png", product.getCategory().getCategoryName()));
                        return productViewModel;
                    })
                    .collect(Collectors.toList());

            List<ProductViewModel> nonAlcoholic = this.productService.findAllByCategory("NON_ALCOHOLIC")
                    .stream().map(product -> {
                        ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                        productViewModel.setImgUrl(String.format("/img/%s.png", product.getCategory().getCategoryName()));
                        return productViewModel;
                    })
                    .collect(Collectors.toList());

            modelAndView.addObject("salads", salads);
            modelAndView.addObject("appetizers", appetizers);
            modelAndView.addObject("dishes", dishes);
            modelAndView.addObject("soups", soups);
            modelAndView.addObject("desserts", desserts);
            modelAndView.addObject("alcoholic", alcoholic);
            modelAndView.addObject("non_alcoholic", nonAlcoholic);
        }



        return modelAndView;
    }
   // @GetMapping("/contacts")
    public String contacts(){

        return "contacts";
    }
  //  @GetMapping("/about-us")
    public String aboutUs(){

        return "about-us";
    }
}


*/







/*



package project.web;


        import org.modelmapper.ModelMapper;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.validation.BindingResult;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.ModelAttribute;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;
        import project.model.binding.UserLoginBindingModel;
        import project.model.binding.UserRegisterBindingModel;
        import project.model.service.UserServiceModel;
        import project.service.UserService;

        import javax.servlet.http.HttpSession;
        import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsersController(UserService userService, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    //org.springframework" +
//                    ".validation.BindingResult.userRegisterBindingModel
    @GetMapping("/login")
    public String login(Model model) {

        if (!model.containsAttribute("userLoginBindingModel")) {
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
            model.addAttribute("notFound", false);
        }


        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@Valid @ModelAttribute("userLoginBindingModel")
                                       UserLoginBindingModel userLoginBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes,
                               HttpSession httpSession) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);

            return "redirect:login";

        }

        //  userLoginBindingModel.setPassword(passwordEncoder.encode(userLoginBindingModel.getPassword()));

        UserServiceModel user = this.userService.findByUsername(userLoginBindingModel.getUsername());
        this.userService.login(userLoginBindingModel.getUsername());

        if (user == null || passwordEncoder.matches(user.getPassword(), userLoginBindingModel.getPassword())){
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("notFound", true);
            return "redirect:login";
        }

        httpSession.setAttribute("user", user);


        return "home";

    }

    @GetMapping("/register")
    public String register(Model model) {

        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }

        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel")
                                          UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        System.out.println();
        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword()
                .equals(userRegisterBindingModel.getConfirmPassword())) {


            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework" +
                    ".validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:register";
        }
        this.userService.register(this.modelMapper.map(userRegisterBindingModel,
                UserServiceModel.class));


        return "redirect:login";

    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }
}


*/
