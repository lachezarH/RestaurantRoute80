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


    //go to home page
    @GetMapping("/")
    public String home(Model model) {
        return "/home";
    }

    //go to home page
    @GetMapping("/home")
    public String homeAbsolute(Model model) {
        return home(model);
    }

    //go to home page
    @PostMapping("/home")
    public String homePost() {
        return "redirect:/home";
    }
    //go to contacts page
    @GetMapping("/contacts")
    public String contacts(){

        return "contacts";
    }
    //go to about-us page
    @GetMapping("/about-us")
    public String aboutUs(){

        return "about-us";
    }
    //go to gallery page
    @GetMapping("/gallery")
    public String gallery(){

        return "gallery";
    }



}

