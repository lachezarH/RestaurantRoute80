package project.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.service.ProductService;
import project.service.StatsService;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class StatsController {


  private final StatsService statsService;
  private final ProductService productService;

  public StatsController(StatsService statsService, ProductService productService) {
    this.statsService = statsService;
    this.productService = productService;
  }

  @GetMapping("/stats")
  public String stats(Model model) {

    model.addAttribute("requestCount", statsService.getRequestCount());
    model.addAttribute("productCount", productService.countOfProducts());
    model.addAttribute("startedOn", statsService.getStartedOn());

    return "stats";
  }

}
