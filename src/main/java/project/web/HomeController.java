package project.web;


import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.service.ProductService;

@Controller

public class HomeController {

    public HomeController() {

    }


    @GetMapping("/")
    public String home(Model model) {
        return "/home";
    }

    @GetMapping("/home")
    public String homeAbsolute(Model model) {
        return home(model);
    }

    @PostMapping("/home")
    public String homePost() {
        return "redirect:/home";
    }

    @GetMapping("/contacts")
    public String contacts(){

        return "contacts";
    }
    @GetMapping("/about-us")
    public String aboutUs(){

        return "about-us";
    }



}

