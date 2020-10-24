package vn.agileviet.quoc2020;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vn.agileviet.quoc2020.models.Post;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("index");
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
//		registry.addViewController("/chat").setViewName("chat");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/bootstrap/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/3.3.7/");
		registry.addResourceHandler("/resources/jquery/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.1.1/");
		
		registry.addResourceHandler("/resources/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/resources/js/**").addResourceLocations("classpath:/static/js/");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}


}

