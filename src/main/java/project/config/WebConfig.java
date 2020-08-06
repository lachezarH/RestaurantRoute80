package project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import project.interceptor.ProductInterceptor;
import project.interceptor.StatsInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements WebMvcConfigurer {


    private StatsInterceptor statsInterceptor;
    private ProductInterceptor productInterceptor;


    public WebConfig(StatsInterceptor statsInterceptor, ProductInterceptor productInterceptor) {
        this.statsInterceptor = statsInterceptor;
        this.productInterceptor = productInterceptor;

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(statsInterceptor);
        registry.addInterceptor(productInterceptor);
    }
}
