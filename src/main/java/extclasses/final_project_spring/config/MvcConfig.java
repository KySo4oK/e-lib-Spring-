package extclasses.final_project_spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("main");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/prospectus").setViewName("prospectus");
        registry.addViewController("/orders").setViewName("orders");
        registry.addViewController("/book").setViewName("bookManagePage");
        registry.addViewController("/user").setViewName("user");
    }
}