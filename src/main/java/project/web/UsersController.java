package project.web;


import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
import project.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsersController(UserService userService, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    //go to login page
    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    //this catching errors when we try to log
    @PostMapping("/login-error")
    public ModelAndView onLoginError(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String user, BindingResult bindingResult) {



        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors() || modelAndView.isEmpty()) {


            modelAndView.addObject("error", "bad_credentials");
            modelAndView.addObject("user", user);

            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    //go to registration page
    @GetMapping("/registration")
    public ModelAndView showRegister(ModelAndView model) {
        model.addObject("formData", new UserRegisterBindingModel());
        model.setViewName("registration");
        return model;
    }

    //registration and  redirect to login
    @PostMapping("/registration")
    public ModelAndView register(@Valid @ModelAttribute("formData") UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult, ModelAndView modelAndView ) {

        if (bindingResult.hasErrors()) {

            modelAndView.setViewName("/registration");

            return modelAndView;
        }

        if (userService.exists(userRegisterBindingModel.getUsername())) {
            modelAndView.addObject("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            modelAndView.addObject("user", userRegisterBindingModel);

            bindingResult.rejectValue("username",
                    "error.username",
                    "An account already exists for this username.");
            modelAndView.setViewName("/registration");
            return modelAndView;
           }else if (userService.existsByEmail(userRegisterBindingModel.getEmail())){
            modelAndView.addObject("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            modelAndView.addObject("user", userRegisterBindingModel);
            bindingResult.rejectValue("email",
                    "error.email",
                    "An account already exists for this email.");
            modelAndView.setViewName("/registration");
            return modelAndView;
        }

        userService.registerAndLoginUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        modelAndView.setViewName("/home");

        return modelAndView;

    }

    //this is useless
    @GetMapping("/info")
    @PreAuthorize("hasRole('ADMIN')")
    public String infoUser(){

        return "info-user";
    }


}
