package project.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.model.binding.CommentsBindingModel;
import project.service.CommentsService;

import javax.validation.Valid;

@Controller
@RequestMapping("/comments")
public class CommentsController {

    private final CommentsService commentsService;


    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping
    public String comments(Model model) {
        model.addAttribute("active", "comments");
        model.addAttribute("comments",
                commentsService.findAll());
        return "comments";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('USER')")
    public String newAnnouncement(Model model) {
        model.addAttribute("active", "comments");
        model.addAttribute("formData", new CommentsBindingModel());
        return "new-comments";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("formData") CommentsBindingModel commentsBindingModel,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/comments/new";
        }

        commentsService.updateOrCreateComments(commentsBindingModel);


        return "redirect:/comments";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable("id") String  id) {

        commentsService.deleteComments(id);

        return "redirect:/comments";
    }
}
