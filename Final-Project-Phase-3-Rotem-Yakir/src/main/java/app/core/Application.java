package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;

import app.core.auth.filters.AdminAuthorizationFilter;
import app.core.auth.filters.AuthenticationFilter;
import app.core.auth.filters.CompanyAuthorizationFilter;
import app.core.auth.filters.CustomerAuthorizationFilter;
import app.core.auth.jwt.JwtUtilUser;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@EnableScheduling
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	FilterRegistrationBean<AuthenticationFilter> authenticationFilter(JwtUtilUser jwtUtilUser){
		FilterRegistrationBean<AuthenticationFilter> regBean = new FilterRegistrationBean<>();
		regBean.setFilter(new AuthenticationFilter(jwtUtilUser));
		regBean.addUrlPatterns("/api/*");
		regBean.setOrder(1);
		return regBean;
	}
	
	@Bean
	FilterRegistrationBean<AdminAuthorizationFilter> adminAuthFilter(){
		FilterRegistrationBean<AdminAuthorizationFilter> regBean = new FilterRegistrationBean<>();
		regBean.setFilter(new AdminAuthorizationFilter());
		regBean.addUrlPatterns("/api/admin/*");
		regBean.setOrder(2);
		return regBean;
	}
	
	@Bean
	FilterRegistrationBean<CompanyAuthorizationFilter> companyAuthFilter(){
		FilterRegistrationBean<CompanyAuthorizationFilter> regBean = new FilterRegistrationBean<>();
		regBean.setFilter(new CompanyAuthorizationFilter());
		regBean.addUrlPatterns("/api/company/*");
		regBean.setOrder(2);
		return regBean;
	}
	
	@Bean
	FilterRegistrationBean<CustomerAuthorizationFilter> customerAuthFilter(){
		FilterRegistrationBean<CustomerAuthorizationFilter> regBean = new FilterRegistrationBean<>();
		regBean.setFilter(new CustomerAuthorizationFilter());
		regBean.addUrlPatterns("/api/customer/*");
		regBean.setOrder(2);
		return regBean;
	}
	
	
	// for swagger authorization
		@Bean
		OpenAPI customOpenAPI() {
			return new OpenAPI().info(new Info().title("title").version("version").description("description"))
					.addSecurityItem(new SecurityRequirement().addList("my security"))
					.components(new Components().addSecuritySchemes("my security",
							new SecurityScheme().name("my security").type(SecurityScheme.Type.HTTP).scheme("bearer")));
		}

}
