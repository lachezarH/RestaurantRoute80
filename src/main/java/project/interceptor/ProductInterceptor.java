package project.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import project.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ProductInterceptor implements HandlerInterceptor {

    private ProductService productService;

    public ProductInterceptor(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.productService.countOfProducts();
        return true;
    }
}
